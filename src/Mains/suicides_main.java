package Mains;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import Models.Suicides;

public class suicides_main {
	
	public static void main(String[] arg) throws FileNotFoundException {
		
		System.out.println("----------------------------------- Hello :) ------------------------------------------");
		System.out.println("");

		List<Suicides> suicides = readSuicidesFromCSV("Suicides_in_India .csv");
		
		System.out.println("***************** display how many suicides inside of the dataset *****************");
		System.out.println("there are: " + suicides.stream().count() + " results in this file");
		System.out.println("");
		
		System.out.println( "****************** Display all type_major of suicides ***************************");
		Map<String, Long> Suic = suicides.stream().collect(Collectors.groupingBy(Suicides::getType_major, Collectors.counting()));
		System.out.println(Suic.toString().replaceAll(",", "\n"));
		System.out.println("");
		
		System.out.println( "****************** Display all type_minor of suicides ***************************");
		String Suic2 = suicides.stream().collect(Collectors.groupingBy(Suicides::getType_minor, Collectors.counting())).toString();
		System.out.println(Suic2.toString().replaceAll(",", "\n"));
		System.out.println("");
		
		System.out.println( "****************** nombre of results of women who suicides in 2001 ***************************");
		long Resul = suicides.stream()
				.filter(x -> x.getGender().equals("Female") && x.getYear()==2001 )
				.count();
		System.out.printf("There are %d results %n", Resul);
		System.out.println("");
		
		System.out.println("********************* display the state that have the maximal nomber of suicides ho hase the age groupe = 15-29************************");
		System.out.println(suicides.stream()
				.filter(x -> x.getAge_group().equals("15-29"))
				.max(Comparator.comparing(Suicides::getTotal)));
		System.out.println("");

		
		System.out.println("********************* Sorted nombre of suicides in male in 2004 ho have type minor = By DRowing ************************");
		suicides.stream()
		.filter(x -> x.getGender().equals("Male") && x.getYear()==2004 && x.getType_minor().equals("By Drowning"))
		.sorted(Comparator.comparing(Suicides::getTotal))
		.forEach(x -> System.out.println("State: "+ x.getState() + "  " + " Year: " + x.getYear() + " Gender: " + x.getGender() + " " + "Total : " + x.getTotal()));
		System.out.println("");
		
		System.out.println("********************* display the state that have the minimal nomber of suicides in 2012 in male bitween 30 and 44************************");
		System.out.println(suicides.stream()
				.filter(x -> x.getAge_group().equals("30-44") && x.getYear()==2012 && x.getGender().equals("Male"))
				.min(Comparator.comparing(Suicides::getTotal)));
		System.out.println("");


	}
	
	private static List<Suicides> readSuicidesFromCSV(String fileName) {
		List<Suicides> suicides = new ArrayList<>();
		Path pathToFile = Paths.get(fileName);

		try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.UTF_8)) {
			String line = br.readLine();
			while (line != null) {

				String[] attributes = line.split(",");
				Suicides suicides1 = creatSuicides(attributes);

				suicides.add(suicides1);
				line = br.readLine();
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return suicides;
	}
	
	public static Suicides creatSuicides(String[] metadata) {
		
		String state=metadata[0];
		int year=0;
		String type_major=metadata[2];
		String type_minor=metadata[3];
		String gender=metadata[4];
		String age_group=metadata[5];
		int total =0;
		try {
			 year=Integer.parseInt(metadata[1]);
			 total=Integer.parseInt(metadata[6]);
		} catch (NumberFormatException e) {

	    }

        return new Suicides(state,year,type_major,type_minor,gender,age_group,total);
	
	}

}
