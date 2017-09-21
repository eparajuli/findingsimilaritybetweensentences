package informationRetrieval.firstProject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.tartarus.snowball.ext.PorterStemmer;

public class Application {

//	private List<TrainingSet> trainingRecords = new ArrayList<TrainingSet>();
//	private List<TestSet> testRecords = new ArrayList<TestSet>();
	private List<String[]> totalWordsPerQuestion = new ArrayList<String[]>();
	private List<String> allUniqueWordsInAsentence = new ArrayList<String>(); // to hold unique terms of both sentences
	private List<double[]> tfidfSentencesVector = new ArrayList<double[]>();
	private List<Double> cosinesValuesForWhichTwoSentencesAreDuplicates =new ArrayList<Double>();
	private  LinkedHashMap<String,String> hm = new LinkedHashMap<String, String>();
	private List<OutputSet> wr = new ArrayList<OutputSet>();
	private final CharArraySet stopWords = EnglishAnalyzer.getDefaultStopSet();


	public static void main(String[] args) throws IOException {
		Application a = new Application();		 
		a.forTrainingSet();
		a.forTestSet();
		a.exportCSV();
	}
	
	public void forTrainingSet() throws IOException {
		// Variables		 
		List<TrainingSet> trainingRecords = new CSVParser().parseTrainCSV("/home/aaa/Documents/Information Retrieval/train.csv");
		// Clear Variables
		hm.clear();
		cosinesValuesForWhichTwoSentencesAreDuplicates.clear();
		for (TrainingSet s : trainingRecords) {
			clearVariables();
			// System.out.println(s.getId() + " " + s.getQid1() + " " + s.getQid2() + " " +
			// s.getQuestion1() + " " + s.getQuestion2() + " " + s.getIsDuplicate());

			// Tokenize question1
			String[] tokenizedWordsOfQuestion1 = stemList(s.getQuestion1().trim().replaceAll("[\\W&&[^\\s]]", "").split("\\W+"));
			
			
			
			for (String word : tokenizedWordsOfQuestion1) {
				if (!allUniqueWordsInAsentence.contains(word) && !stopWords.contains(word)) {
					allUniqueWordsInAsentence.add(word);
				}
			}
			totalWordsPerQuestion.add(tokenizedWordsOfQuestion1);
			totalWordsPerQuestion.removeAll(stopWords);

			// Tokenize question 2
			String[] tokenizedWordsOfQuestion2 = stemList(s.getQuestion2().trim().replaceAll("[\\W&&[^\\s]]", "").split("\\W+"));
			for (String word : tokenizedWordsOfQuestion2) {
				if (!allUniqueWordsInAsentence.contains(word) && !stopWords.contains(word)) {
					allUniqueWordsInAsentence.add(word);
				}
			}
			totalWordsPerQuestion.add(tokenizedWordsOfQuestion2);
			totalWordsPerQuestion.removeAll(stopWords);

			// Calculate TFIDF
			getTfIdf();

			// Calculate Cosine Similarity
			getCosineValueBetweenTwoQuestions();
			if (s.getIsDuplicate().equals("1")) {
				cosinesValuesForWhichTwoSentencesAreDuplicates.add(getCosineValueBetweenTwoQuestions());
			}
		}
		
	}
	
	
	public void forTestSet() throws IOException {
		List<TestSet> testRecords = new CSVParser().parseTestCSV("/home/aaa/Documents/Information Retrieval/test.csv");	
		System.out.println(testRecords.size());
		double sum =0.0;
		double mean =0.0;
		for(TestSet t : testRecords) {
			clearVariables();
			// System.out.println(s.getId() + " " + s.getQid1() + " " + s.getQid2() + " " +
			// s.getQuestion1() + " " + s.getQuestion2() + " " + s.getIsDuplicate());

			// Tokenize question1
			String[] tokenizedWordsOfQuestion1 = stemList(t.getQuestion1().trim().replaceAll("[\\W&&[^\\s]]", "").split("\\W+"));
			for (String word : tokenizedWordsOfQuestion1) {
				if (!allUniqueWordsInAsentence.contains(word) && !stopWords.contains(word)) {
					allUniqueWordsInAsentence.add(word);
				}
			}
			totalWordsPerQuestion.add(tokenizedWordsOfQuestion1);
			totalWordsPerQuestion.removeAll(stopWords);

			// Tokenize question 2
			String[] tokenizedWordsOfQuestion2 = stemList(t.getQuestion2().trim().replaceAll("[\\W&&[^\\s]]", "").split("\\W+"));
			for (String word : tokenizedWordsOfQuestion2) {
				if (!allUniqueWordsInAsentence.contains(word) && !stopWords.contains(word)) {
					allUniqueWordsInAsentence.add(word);
				}
			}
			totalWordsPerQuestion.add(tokenizedWordsOfQuestion2);
			totalWordsPerQuestion.removeAll(stopWords);
			// Calculate TFIDF
			getTfIdf();
			 
			for(double d :cosinesValuesForWhichTwoSentencesAreDuplicates) {
				sum = sum+d;
			}
			mean = sum/cosinesValuesForWhichTwoSentencesAreDuplicates.size();
			mean = 0.4825946502170073;
			// Calculate Cosine Similarity
			System.out.println(getCosineValueBetweenTwoQuestions());
			if((getCosineValueBetweenTwoQuestions() - mean) >=0) {
				hm.put(t.getId(), "1");
				
			}
			else {
				hm.put(t.getId(), "0");
			}
			
		}

	}

	public void getTfIdf() {
		double tf; // term frequency
		double idf; // inverse document frequency
		double tfIdf; // term requency inverse document frequency

		// totalWordsInASentence contains the list of arrays of words of both sentence.
		for (String[] arrayOfWordsInEachSentence : totalWordsPerQuestion) {
			int count = 0;
			double[] tfidfvectors = new double[allUniqueWordsInAsentence.size()];
			for (String eachWord : allUniqueWordsInAsentence) {
				tf = new TF().calculateTF(arrayOfWordsInEachSentence, eachWord);
				idf = new IDF().calculateIDF(totalWordsPerQuestion, eachWord);
				tfIdf = tf * idf;
				tfidfvectors[count] = tfIdf;
				count++;
			}
			tfidfSentencesVector.add(tfidfvectors); // storing document vectors;
		}
	}

	public double getCosineValueBetweenTwoQuestions() {
		 System.out.println("Cosine value between two sentence is " + " = "
		 + new Cosines().cosineSimilarity(tfidfSentencesVector.get(0),
		 tfidfSentencesVector.get(1)));	
		return new Cosines().cosineSimilarity(tfidfSentencesVector.get(0), tfidfSentencesVector.get(1));
	}

	public void clearVariables() {
		allUniqueWordsInAsentence.clear();
		totalWordsPerQuestion.clear();
		tfidfSentencesVector.clear();		
	}
	
	public String stemTerm (String term) {
	    PorterStemmer stemmer = new PorterStemmer();
	    stemmer.setCurrent(term);
		stemmer.stem();
		return  stemmer.getCurrent().toString();
	}
	
	public String[] stemList (String[] terms) {
	    PorterStemmer stemmer = new PorterStemmer();
	    ArrayList<String> stemList = new ArrayList<String>();
	    for(String term : terms) {
	    	stemmer.setCurrent(term);
			stemmer.stem();
			stemList.add(stemmer.getCurrent());
	    }
	    
		return  stemList.toArray(new String[stemList.size()]);
	}

	public void exportCSV() throws IOException {
		//Delimiter used in CSV file
	     String COMMA_DELIMITER = ",";
		 String NEW_LINE_SEPARATOR = "\n";
		 String FILE_HEADER = "id,is_duplicate";
		 FileWriter fileWriter = null;
		 int indexline =0;
		 
		 try {
			 fileWriter = new FileWriter("/home/aaa/Documents/Information Retrieval/t1.csv");
			 for (Entry<String, String> entry : hm.entrySet()) {
				 	indexline++;
					System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
					fileWriter.append(entry.getKey());
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(entry.getValue());
					fileWriter.append(NEW_LINE_SEPARATOR);
					
					System.out.println("Index at :"+indexline);
				}
			 System.out.println("CSV output created successfully");
			 System.out.println("TOtal rows in CSV output is : "+indexline);
			 System.out.println("hashmap size is : "+ hm.size());
		 }
		 
		 catch(Exception e) {
			 System.out.println("Issue with csv writing");
			 System.out.println(e.getStackTrace());
		 }
		 fileWriter.close();

	}
	


}
