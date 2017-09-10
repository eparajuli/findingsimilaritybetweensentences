package informationRetrieval.firstProject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVParser {

	private List<String[]> totalWordsPerSentence = new ArrayList<String[]>();
	private List<String> allUniqueWordsInAsentence = new ArrayList<String>(); // to hold unique terms of both sentences
	private List<double[]> tfidfSentencesVector = new ArrayList<double[]>();
	private ArrayList<String> similarCosinesValue = new ArrayList<String>();
	private ArrayList<Double> listOfCosineValuesForWhichTwoSentenceAreSimilar = new ArrayList<Double>();

	public static void main(String[] args) {
		CSVParser parser = new CSVParser();
		parser.parseTrainCSV();
	}

	public void parseTrainCSV(){
		String trainingSet = "/home/aaa/Documents/Information Retrieval/train.csv";
		String testSet = "/home/aaa/Documents/Information Retrieval/test.csv";
		String cvsSplitBy = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
		String line = "";
		int count = 0;
		String[] readlinebyline = null;

		try (BufferedReader br = new BufferedReader(new FileReader(trainingSet))) {
			while ((line = br.readLine()) != null) {
				// comma to check if each line is a csv file and count>0 to exclude the headers
				if (line.contains(",") && count > 0) {
					// Break down each lines of csv file into an array
					readlinebyline = line.split(cvsSplitBy);
					System.out.println(readlinebyline[3]);
					for (int i = 3; i <= 4; i++) {
						// Break the sentence into tokens/arrays
						String[] tokenizedTerms = readlinebyline[i].trim().replaceAll("[\\W&&[^\\s]]", "").split("\\W+"); // tokenized term contains all the words in sentence
						for (String term : tokenizedTerms) {
							if (!allUniqueWordsInAsentence.contains(term)) { // avoid duplicate entry
								allUniqueWordsInAsentence.add(term);
							}
						}
						totalWordsPerSentence.add(tokenizedTerms);
					}
					tfIdfCalculator();
					//If isSimilar is set to 1 then add to list
					if(readlinebyline[5].equals("1"))
					{
						listOfCosineValuesForWhichTwoSentenceAreSimilar.add(getCosineSimilarity());
					}					
					// for (String[] ss : termsDocsArray) {
					// for (String s : ss) {
					// System.out.println(s);
					// }
					// }
				}
				allUniqueWordsInAsentence.clear();
				totalWordsPerSentence.clear();
				tfidfSentencesVector.clear();
				count++;
				System.out.println("Error at line "+count);
			}
		} catch (IOException e) {
			System.out.println("inside catch");
			e.printStackTrace();
		}
		// System.out.println("Total number of records is : " + count);

	}

	
	public void tfIdfCalculator() {
		double tf; // term frequency
		double idf; // inverse document frequency
		double tfidf; // term requency inverse document frequency
		
		//totalWordsInASentence contains the list of arrays of words of both sentence. 
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
				+ new CosineSimilarity().cosineSimilarity(tfidfSentencesVector.get(0), tfidfSentencesVector.get(1)));
		return new CosineSimilarity().cosineSimilarity(tfidfSentencesVector.get(0), tfidfSentencesVector.get(1));
	}
}
