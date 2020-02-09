package project;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import weka.classifiers.functions.supportVector.PolyKernel;

public class Spreadsheet {

	private Utils utils;

	public Spreadsheet(Utils utils) {
		this.utils = utils;
	}

	private final String NODE_SCRIPT_PATH = ".\\node\\index.js";
	private boolean isCSVGenerate = false;

	public void generateCSV() throws IOException {
		System.out.println("CSV generation ...");

		ArrayList<String> listNames = utils.getFilesNames();

		String csv = ",";

		for (int i = 0; i < listNames.size(); i++)
			csv += (i == 0 ? "" : ",") + listNames.get(i);

		for (int i = 0; i < listNames.size(); i++) {
			String trainName = listNames.get(i);
			String line = trainName;

			for (int j = 0; j < listNames.size(); j++) {
				String testName = listNames.get(j);

				String res = utils.startComparison(trainName, testName, new String[] {}, new PolyKernel());
				line += "," + utils.getPercent(res);
			}

			csv += "\n" + line;
			System.out.println("Progress : " + ((double) (i+1)/listNames.size() * 100) + " %");
		}

		Path resPath = utils.createResFolder();
		Files.write(Paths.get(resPath.toString(), "res.csv"), csv.getBytes());
		System.out.println("Generated csv file");
		isCSVGenerate = true;
	}

	public void generateExcel() {
		if (!isCSVGenerate) return;

		try {
			ProcessBuilder processBuilder = new ProcessBuilder();
			processBuilder.command("node", NODE_SCRIPT_PATH);
			Process process = processBuilder.start();
			int waitRes = process.waitFor();

			if (waitRes == 0)
				System.out.println("Generated excel file");
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

}
