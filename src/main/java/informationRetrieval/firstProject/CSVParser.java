package informationRetrieval.firstProject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class CSVParser {

	public static void main(String[] args) {
		CSVParser parser = new CSVParser();
		parser.parseCSV();
	
		
	}

	public ArrayList<String> parseCSV() {
		String trainingSet = "/home/aaa/Documents/Information Retrieval/train.csv";
		String testSet = "/home/aaa/Documents/Information Retrieval/test.csv";
		String line = "";
		int count = 0;
		String[] readlinebyline;
		// String cvsSplitBy = "\\s*,\\s*";
		String cvsSplitBy = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
		ArrayList<String> list = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(trainingSet))) {
			while ((line = br.readLine()) != null) {
				// use comma as separator
				if (line.contains(",")) {
					readlinebyline = line.split(cvsSplitBy);
					
					count++;					
					
					// System.out.println("Question1 = " + readlinebyline[3] + "\n" + " , Question2
					// =" + readlinebyline[4] + " , Isduplicate =" + readlinebyline[5]);
//					System.out.println(readlinebyline[1]);
				}
			}

		} catch (IOException e) {
			System.out.println("inside catch");
			e.printStackTrace();
		}
//		System.out.println("Total number of records is : " + count);
		return list;
	}
}
