package informationRetrieval.firstProject;

public class Cosines {
	public Double cosineSimilarity(double[] sentence1Vector, double[] sentence2Vector) {	
		
		//Define variables
		double dotProduct = 0.0;
		double magnitudeOfSentence1 = 0.0;
		double magnitudeOfSentence2 = 0.0;
		double cosineValue = 0.0;
		
		for (int i = 0; i < sentence1Vector.length; i++) 
		{
			dotProduct += sentence1Vector[i] * sentence2Vector[i]; // a.b
			magnitudeOfSentence1 += Math.pow(sentence1Vector[i], 2); // (a^2)
			magnitudeOfSentence2 += Math.pow(sentence2Vector[i], 2); // (b^2)
		}

		magnitudeOfSentence1 = Math.sqrt(magnitudeOfSentence1);// sqrt(a^2)
		magnitudeOfSentence2 = Math.sqrt(magnitudeOfSentence2);// sqrt(b^2)

		if (magnitudeOfSentence1 != 0.0 | magnitudeOfSentence2 != 0.0) {
			cosineValue = dotProduct / (magnitudeOfSentence1 * magnitudeOfSentence2);
		} else {
			return 0.0;
		}
		return cosineValue;
	}

}
