package Mains;
import java.io.BufferedReader;
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


import Models.Results;

public class results_main {
	
	public static void	main(String[] args)  {
		
		System.out.println("----------------------------------- Hello :) ------------------------------------------");
		System.out.println("");

			
		List<Results> results = readResultsFromCSV("results.csv");

		
		System.out.println("***************** Display how many result inside of the dataset *****************");
		System.out.println("there are: " + results.stream().count() + " results in this file");
		System.out.println("");
		
		System.out.println("************ Filter results by home_team = Abkhazia ************");
		results.stream().filter(e -> e.getHome_team().equals("Abkhazia"))
								.collect(Collectors.toList())
								.forEach(e -> System.out.println(e));
		System.out.println("");

		
		System.out.println("******************* Display the results of match ho hase home_score=Abkhazia, sorted by away_score **************************");
		results.stream()
				.filter(x -> x.getHome_team().equals("Abkhazia"))
				.sorted(Comparator.comparing(Results::getAway_score))
				.forEach(x -> System.out.println("Date: "+ x.getDate() + "  " + " Away_team: " + x.getAway_team() + " Away_score: " + x.getAway_score()));
		System.out.println("");
		
		System.out.println("******************* Count the results of match ho hase home_team=Germany, and home_score=0 **************************");
		long count =results.stream()
				.filter(x -> x.getHome_score().equals("0") && x.getHome_team().equals("Germany"))
				.count();
				System.out.printf("There are %d results %n", count);
		System.out.println("");
		
		System.out.println( "****************** Count, for each Home_Team, the number of results ***************************");
		Map<String, Long> nbResul = results.stream().collect(Collectors.groupingBy(Results::getHome_team, Collectors.counting()));
		System.out.println(nbResul.toString().replaceAll(",", "\n"));
		System.out.println("");
		
		System.out.println( "****************** count Results ho hase neutra(Whether the match took place at a neutral venue or not)=True ***************************");
		Map<Boolean, Long> Resul = results.stream().collect(Collectors.groupingBy(Results::getNeutral, Collectors.counting()));
		System.out.println(Resul);
		System.out.println("");
		
		System.out.println("********************* Display the Home_team that have the highest score in Germany ************************");

		System.out.println(results.stream()
				.filter(x -> x.getCountry().equals("Germany"))
				.max(Comparator.comparing(Results::getHome_score)));
	}
	
	
	private static List<Results> readResultsFromCSV(String fileName) {
		List<Results> results = new ArrayList<>();
		Path pathToFile = Paths.get(fileName);

		try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.UTF_8)) {
			String line = br.readLine();
			while (line != null) {

				String[] attributes = line.split(",");
				Results result = createResults(attributes);

				results.add(result);
				line = br.readLine();
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return results;
	}

	public static Results createResults(String[] metadata) {
		
		String date=metadata[0];
	    String home_team=metadata[1];
		String away_team=metadata[2];
		String home_score=metadata[3];
		String away_score=metadata[4];
		String tournament=metadata[5];
		String city=metadata[6];
		String country=metadata[7];
		boolean neutral=Boolean.parseBoolean(metadata[8]);

       
        return new Results(date,home_team,away_team,home_score,away_score,tournament,city,country,neutral);
	
	}
	

}
