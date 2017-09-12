package informationRetrieval.firstProject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Application {

	private List<TrainingSet> trainingRecords = new ArrayList<TrainingSet>();
	private List<String[]> totalWordsPerQuestion = new ArrayList<String[]>();
	private List<String> allUniqueWordsInAsentence = new ArrayList<String>(); // to hold unique terms of both sentences
	private List<double[]> tfidfSentencesVector = new ArrayList<double[]>();
	
	public static void main(String[] args) throws IOException {
		Application a = new Application();
		a.mainApplication();
	}

	public void mainApplication() throws IOException {
		trainingRecords = new CSVParser().parseTrainCSV("/home/aaa/Documents/Information Retrieval/train.csv");
		for (TrainingSet s : trainingRecords) {
			clearVariables();
			// System.out.println(s.getId() + " " + s.getQid1() + " " + s.getQid2() + " " +
			// s.getQuestion1() + " " + s.getQuestion2() + " " + s.getIsDuplicate());

			// Tokenize question1
			String[] tokenizedWordsOfQuestion1 = s.getQuestion1().trim().replaceAll("[\\W&&[^\\s]]", "").split("\\W+");
			for (String word : tokenizedWordsOfQuestion1) {
				if (!allUniqueWordsInAsentence.contains(word)) {
					allUniqueWordsInAsentence.add(word);
				}
			}
			totalWordsPerQuestion.add(tokenizedWordsOfQuestion1);

			// Tokenize question 2
			String[] tokenizedWordsOfQuestion2 = s.getQuestion2().trim().replaceAll("[\\W&&[^\\s]]", "").split("\\W+");
			for (String word : tokenizedWordsOfQuestion2) {
				if (!allUniqueWordsInAsentence.contains(word)) {
					allUniqueWordsInAsentence.add(word);
				}
			}
			totalWordsPerQuestion.add(tokenizedWordsOfQuestion2);

			// Calculate TFIDF
			calculatetfIdf();

			// Calculate Cosine Similarity
			getCosineValueBetweenTwoQuestions();
			if(s.getIsDuplicate().equals("1")){
				System.out.println("Hurray");
			}
		}
	}
	
	public void calculatetfIdf() {
		double tf; // term frequency
		double idf; // inverse document frequency
		double tfidf; // term requency inverse document frequency

		// totalWordsInASentence contains the list of arrays of words of both sentence.
		for (String[] arrayOfWordsInEachSentence : totalWordsPerQuestion) {
			int count = 0;
			double[] tfidfvectors = new double[allUniqueWordsInAsentence.size()];			
			for (String eachWord : allUniqueWordsInAsentence) {
				tf = new TF().calculateTF(arrayOfWordsInEachSentence, eachWord);
				idf = new IDF().calculateIDF(totalWordsPerQuestion, eachWord);
				tfidf = tf * idf;
				tfidfvectors[count] = tfidf;
				count++;
			}
			tfidfSentencesVector.add(tfidfvectors); // storing document vectors;
		}
	}

	public double getCosineValueBetweenTwoQuestions() {
		System.out.println("Cosine value between two sentence is  " + "  =  "
				+ new Cosines().cosineSimilarity(tfidfSentencesVector.get(0), tfidfSentencesVector.get(1)));
		// return new CosineSimilarity().cosineSimilarity(tfidfSentencesVector.get(0),
		// tfidfSentencesVector.get(1));
		return new Cosines().cosineSimilarity(tfidfSentencesVector.get(0), tfidfSentencesVector.get(1));
	}

	public void clearVariables() {
		allUniqueWordsInAsentence.clear();
		totalWordsPerQuestion.clear();
		tfidfSentencesVector.clear();
	}

	

}
