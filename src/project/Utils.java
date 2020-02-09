package project;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import project.data.PrepareData;
import project.data.TestClassifier;
import weka.classifiers.functions.SMO;
import weka.classifiers.functions.supportVector.Kernel;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class Utils {

	private int classAtribut = 1;
	private String[] splitters = new String[] {"               ", " %"};
	
	public String startComparison(String trainName, String testName, String[] options, Kernel kernel) {
		try {
			Instances train = new PrepareData(new DataSource("data/" + trainName + ".arff")).getInstances();
			Instances test = new PrepareData(new DataSource("data/" + testName + ".arff")).getInstances();

			SMO classifier = new SMO();
			classifier.setKernel(kernel);

			return new TestClassifier(classifier, train, test, classAtribut, options).getSummary();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public String getPercent(String summary) {
		return summary == null ? "Error" : summary.split(splitters[0])[1].split(splitters[1])[0];
	}
	
	public Path createResFolder() throws IOException {
		Path path = Paths.get("res/");
		Files.createDirectories(path);
		return path;
	}

	public ArrayList<String> getFilesNames() {
		File folder = new File("data/");
		File[] filesList = folder.listFiles();
		ArrayList<String> namesList = new ArrayList<String>();

		for (int i = 0; i < filesList.length; i++)
			namesList.add(filesList[i].getName().split("\\.")[0]);

		return namesList;
	}
}
