package informationRetrieval.firstProject;

import java.util.List;

public class IDF {
	public double calculateIDF(List<String[]> allTerms, String termToCheck) {
		double numberOfSentencesWithtermToCheckInIt = 0;
		double totalNumberOfSentence = allTerms.size();
		for (String[] ss : allTerms) {
			for (String s : ss) {
				if (s.equalsIgnoreCase(termToCheck)) {
					numberOfSentencesWithtermToCheckInIt++;
					break;
				}
			}
		}
		return Math.log(totalNumberOfSentence / numberOfSentencesWithtermToCheckInIt);
	}
}
