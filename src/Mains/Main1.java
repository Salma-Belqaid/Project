package Mains;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import Models.ResultsFF;

public class Main1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<ResultsFF> res = readBooksFromCSV("results1.csv");

		//System.out.println("************See everything************");
	//	res.stream().collect(Collectors.toList())
		//					 .forEach(e -> System.out.println(e));

		
        System.out.println("First element :" + res.stream().collect(Collectors.toList()).get(1));

        System.out.println("List  Results where Vietnam is Home team and home score is higher than 2 ");
        res.stream().filter(e -> e.getHome_team().equals("Vietnam")).filter(e -> e.getHome_score()>2).forEach(e -> System.out.println(e));
        System.out.println("___________________________________________________________________ ");	
        
        System.out.println("List  Results where Hong kong is Home team and Hong Kong Won ");
        res.stream().filter(e -> e.getHome_team().equals("Hong Kong"))
        .filter(e -> e.getHome_score()>e.getAway_score()).forEach(e -> System.out.println(e));
        System.out.println("___________________________________________________________________ ");		
        
        System.out.println("List  Results where Hong kong is Home team and Hong kong Won , in Hong Kong : "+
        res.stream().filter(e -> e.getHome_team().equals("Hong Kong"))
        .filter(e -> e.getHome_score()>e.getAway_score()).filter(e -> e.getCountry().equals("Hong Kong")).count());
        System.out.println("___________________________________________________________________ ");		

        System.out.println("List  Results where Hong kong is Home team and Hong kong Won ,not in Hong Kong : "+
        res.stream().filter(e -> e.getHome_team().equals("Hong Kong"))
        .filter(e -> e.getHome_score()>e.getAway_score()).filter(e ->!e.getCountry().equals("Hong Kong")).count());
        System.out.println("___________________________________________________________________ ");		
        		
        		
        System.out.println("List results which home score is 2 or more points higher than away score and list only names of teams and their scores : ");
        res.stream().filter(e -> e.getHome_score()-e.away_score >2).limit(5)
        .collect(Collectors.toList())
        .forEach(e -> System.out.println(e.home_team+" "+e.home_score+ " "+e.getAway_team()+" "+e.away_score));
        System.out.println("___________________________________________________________________ ");		
  		
        
        System.out.println("Number of matches won by Italy , in a neutral ground , chamionship = Euro , in 1969 : ");
        res.stream().filter(e -> e.getHome_team().equals("Italy"))
        .filter(e -> e.getHome_score()>e.getAway_score() && e.getTournament().equals("Euro") && e.getDate().contains("1969"))
        .forEach(e -> System.out.println(e));

                System.out.println("___________________________________________________________________ ");	
        
		
         System.out.println("Name of teams , scores , and dates played in  1977 in a neutral ..");
         res.stream().filter(e -> e.isNeutral() && e.getDate().contains("1977"))
         .collect(Collectors.toList())
         .forEach(e -> System.out.println(e.home_team+" :"+e.home_score+ " "+e.getAway_team()+" :"+e.away_score +" date :"+ e.getDate()+ " Is entral :"+ e.isNeutral()));
         System.out.println("___________________________________________________________________ ");		
          		
		
	}
	
	
	private static List<ResultsFF> readBooksFromCSV(String fileName) {
		List<ResultsFF> reslist = new ArrayList<>();
		Path pathToFile = Paths.get(fileName);
		// create an instance of BufferedReader
		// using try with resource, Java 7 feature to close resources
		try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.UTF_8)) {
			// read the first line from the text file
			String line = br.readLine();
			// loop until all lines are read
			while (line != null) {
				// use string.split to load a string array with the values from // each line of
				// // the file, using a comma as the delimiter
				String[] attributes = line.split(",");
				ResultsFF res = createResults(attributes);
				// System.out.println(wine.toString());
				// adding book into ArrayList
				reslist.add(res);
				// read next line before looping // if end of file reached, line would be null
				line = br.readLine();
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return reslist;
	}

	public static ResultsFF createResults(String[] metadata) {
		

        String date = metadata[0];
		String home_team = metadata[1];
		String away_team = metadata[2];
		
		
		 int home_score = 0;
		 int away_score = 0;
		try{
			  home_score = Integer.parseInt(metadata[3]);
			  away_score = Integer.parseInt(metadata[4]);
			    } catch(NumberFormatException ex){ // handle your exception
		   System.out.println(ex);
		}
	   
	    String tournament = metadata[5];
		
	    String city = metadata[6];
	    String country = metadata[7];
		boolean neutral= Boolean.parseBoolean(metadata[8]);
				
 return new ResultsFF(date,home_team,away_team,home_score,away_score,tournament,city,country,neutral);
	
	}

	
	
	
	
	

}
