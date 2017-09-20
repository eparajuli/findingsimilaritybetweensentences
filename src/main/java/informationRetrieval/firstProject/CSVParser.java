package informationRetrieval.firstProject;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.opencsv.CSVReader;

public class CSVParser {

	List<TrainingSet> trainingRecords = new ArrayList<TrainingSet>();
	List<TestSet> testRecords = new ArrayList<TestSet>();

	public List<TrainingSet> parseTrainCSV(String trainingFileLocation) throws IOException {
		int count = 0;
		CSVReader trainingSetReader = new CSVReader(new FileReader(trainingFileLocation), ',');
		String[] record;
		while ((record = trainingSetReader.readNext()) != null) {
			// To skip the first header line we check count
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
		trainingSetReader.close();

		// }
		return trainingRecords;

	}

	public List<TestSet> parseTestCSV(String testFileLocation) throws IOException {
		int count = 0;
		CSVReader testSetReader = new CSVReader(new FileReader(testFileLocation), ',');
		String[] record;
		while ((record = testSetReader.readNext()) != null) {
			// To skip the first header line
			if (count > 0) {
				TestSet ts = new TestSet();
				ts.setId(record[0].trim());
				ts.setQid1(record[1].trim());
				ts.setQid2(record[2].trim());
				ts.setQuestion1(record[3].trim());
				ts.setQuestion2(record[4].trim());
				testRecords.add(ts);
			}
			count++;
		}
		testSetReader.close();
		return testRecords;
	}
}
