package informationRetrieval.firstProject;

import java.util.List;

public class IDF {
	public double calculateIDF(List<String[]> totalWordsPerQuestion, String eachWord) {
		double numberOfSentencesWithtermToCheckInIt = 0;
		double totalNumberOfSentence = totalWordsPerQuestion.size();
		for (String[] ss : totalWordsPerQuestion) {
			for (String s : ss) {
				if (s.equalsIgnoreCase(eachWord)) {
					numberOfSentencesWithtermToCheckInIt++;
					break;
				}
			}
		}
		return (1 + Math.log(totalNumberOfSentence / numberOfSentencesWithtermToCheckInIt));
	}
}
