package informationRetrieval.firstProject;

public class TF {
	
	public double calculateTF(String allTerms[],String termToCheck) {
		/*Define variables and initialize them */
		double tf = 0.0;
		//The total number of terms in a sentence
		double totalNumberOfTermsInASentence = allTerms.length; 
		
		//The number of times the termToCheck occured in a sentence
		double numberOfTimesTermOccuredInAsentence = 0;
		
		//Check the number of terms the termToCheck occured in a sentence
		for(String s: allTerms) {
			if(s.equalsIgnoreCase(termToCheck)) {
				numberOfTimesTermOccuredInAsentence++;
			}
		}
		tf=  numberOfTimesTermOccuredInAsentence/totalNumberOfTermsInASentence;
		return tf;
	}
}
