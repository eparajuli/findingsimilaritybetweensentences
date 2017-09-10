package informationRetrieval.firstProject;

public class TF {
	
	public double calculateTF(String totalWordsInASentence[],String wordToFindTF) {
		/*Define variables and initialize them */
		double tf = 0.0;
		//The total number of terms in a sentence
		double totalNumberOfTermsInASentence = totalWordsInASentence.length; 
		
		//The number of times the termToCheck occured in a sentence
		double numberOfTimesWordToFindTFOccuredInAsentence = 0;
		
		//Check the number of terms the termToCheck occured in a sentence
		for(String s: totalWordsInASentence) {
			if(s.equalsIgnoreCase(wordToFindTF)) {
				numberOfTimesWordToFindTFOccuredInAsentence++;
			}
		}
		tf=  numberOfTimesWordToFindTFOccuredInAsentence/totalNumberOfTermsInASentence;
		return tf;
	}
}
