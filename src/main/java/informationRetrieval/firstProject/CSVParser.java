package informationRetrieval.firstProject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.opencsv.CSVReader;

public class CSVParser {

	List<TrainingSet> trainingRecords = new ArrayList<TrainingSet>();
	private List<String[]> totalWordsPerSentence = new ArrayList<String[]>();
	private List<String> allUniqueWordsInAsentence = new ArrayList<String>(); // to hold unique terms of both sentences
	private List<double[]> tfidfSentencesVector = new ArrayList<double[]>();
	private ArrayList<String> similarCosinesValue = new ArrayList<String>();
	private ArrayList<Double> listOfCosineValuesForWhichTwoSentenceAreSimilar = new ArrayList<Double>();
	private HashMap<String, String> outputMap = new HashMap<String, String>();

	public List<TrainingSet> parseTrainCSV(String trainingFileLocation) throws IOException {
		
		String testSet = "/home/aaa/Documents/Information Retrieval/test.csv";

		int count = 0;
	
		CSVReader reader = new CSVReader(new FileReader(trainingFileLocation), ',');

		listOfCosineValuesForWhichTwoSentenceAreSimilar.clear();
		String[] record;
		while ((record = reader.readNext()) != null) {
			// To skip the first header line
			if (count > 0) {
				TrainingSet ts = new TrainingSet();
				ts.setId(record[0].trim());
				ts.setQid1(record[1].trim());
				ts.setQid2(record[2].trim());
				ts.setQuestion1(record[3].trim());
				ts.setQuestion2(record[4].trim());
				ts.setIsDuplicate(record[5].trim());
				trainingRecords.add(ts);
			}
			count++;
		}
//		for (TrainingSet s : trainingRecords) {
//			System.out.println(s.getId() + " " + s.getQid1() + " " + s.getQid2() + " " + s.getQuestion1() + " "
//					+ s.getQuestion2() + " " + s.getIsDuplicate());
//		}
		return trainingRecords;


	}

	public void parseTestCSV(String testFileLocation) {
		
	}
	// public void parseTestCSV() {
	// String testSet = "/home/aaa/Documents/Information Retrieval/test.csv";
	// String cvsSplitBy = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
	// String line = "";
	// int count = 0;
	// String[] readlinebyline = null;
	// // Find the maximum range of cosines values from
	// double rangeFrom =
	// Collections.min(listOfCosineValuesForWhichTwoSentenceAreSimilar);
	// double rangeTo =
	// Collections.max(listOfCosineValuesForWhichTwoSentenceAreSimilar);
	// try (BufferedReader br = new BufferedReader(new FileReader(testSet))) {
	//
	// while ((line = br.readLine()) != null) {
	// clearVariables();
	// // comma to check if each line is a csv file and count>0 to exclude the
	// headers
	// if (line.contains(",") && count > 0) {
	// // Break down each lines of csv file into an array
	// readlinebyline = line.split(cvsSplitBy);
	// System.out.println(readlinebyline[3]);
	// for (int i = 3; i <= 4; i++) {
	// // Break the sentence into tokens/arrays
	// String[] tokenizedTerms =
	// readlinebyline[i].trim().replaceAll("[\\W&&[^\\s]]", "")
	// .split("\\W+"); // tokenized term contains all the words in sentence
	// for (String term : tokenizedTerms) {
	// if (!allUniqueWordsInAsentence.contains(term)) { // avoid duplicate entry
	// allUniqueWordsInAsentence.add(term);
	// }
	// }
	// totalWordsPerSentence.add(tokenizedTerms);
	// }
	// tfIdfCalculator();
	// getCosineSimilarity();
	// // If isSimilar is set to 1 then add to list
	// // if (getCosineSimilarity() >= rangeFrom || getCosineSimilarity() <=
	// rangeTo) {
	// // outputMap.put(readlinebyline[0], "1");
	// // } else {
	// // outputMap.put(readlinebyline[0], "0");
	// // }
	// }
	//
	// count++;
	// System.out.println("Error at line " + count);
	// }
	// } catch (IOException e) {
	// System.out.println("inside catch");
	// e.printStackTrace();
	// }
	// // System.out.println("Total number of records is : " + count);
	//
	// }

	public void tfIdfCalculator() {
		double tf; // term frequency
		double idf; // inverse document frequency
		double tfidf; // term requency inverse document frequency

		// totalWordsInASentence contains the list of arrays of words of both sentence.
		for (String[] arrayOfWordsInEachSentence : totalWordsPerSentence) {
			double[] tfidfvectors = new double[allUniqueWordsInAsentence.size()];
			int count = 0;
			for (String eachWord : allUniqueWordsInAsentence) {
				tf = new TF().calculateTF(arrayOfWordsInEachSentence, eachWord);
				idf = new IDF().calculateIDF(totalWordsPerSentence, eachWord);
				tfidf = tf * idf;
				tfidfvectors[count] = tfidf;
				count++;
			}
			tfidfSentencesVector.add(tfidfvectors); // storing document vectors;
		}
	}

	public double getCosineSimilarity() {
		System.out.println("between first and second sentence  " + "  =  "
				+ new Cosines().cosineSimilarity(tfidfSentencesVector.get(0), tfidfSentencesVector.get(1)));
		return new Cosines().cosineSimilarity(tfidfSentencesVector.get(0),tfidfSentencesVector.get(1));
	}

	public void clearVariables() {
		allUniqueWordsInAsentence.clear();
		totalWordsPerSentence.clear();
		tfidfSentencesVector.clear();
	}

	public void printCosinesValues() {
		System.out.println("Number of items in list :" + listOfCosineValuesForWhichTwoSentenceAreSimilar.size());
		for (double d : listOfCosineValuesForWhichTwoSentenceAreSimilar) {
			System.out.println(d);
			System.out.println("\n");
		}
	}

	public void exportToCSV() {

	}
}
