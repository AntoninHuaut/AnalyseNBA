package project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import project.test.Test;
import project.test.argument.ArgumentUtils;
import project.test.kernel.KernelUtils;

public class Main {

	private Utils utils = new Utils();

	public Main() {
		ArrayList<String> listNames = utils.getFilesNames();
		String trainName = listNames.get(0);

		startSpreadsheet();
		startTests(listNames, trainName);
	}

	public void startSpreadsheet() {
		Spreadsheet tableur = new Spreadsheet(utils);

		try {
			tableur.generateCSV();
			tableur.generateExcel();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void startTests(ArrayList<String> listNames, String trainName) {
		List<Test> tests = Arrays.asList(
				new ArgumentUtils(utils),
				new KernelUtils(utils)
				);

		for (Test test : tests) {
			try {
				test.test(listNames, trainName);
				test.checkResult(listNames, trainName);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new Main();
	}

}
