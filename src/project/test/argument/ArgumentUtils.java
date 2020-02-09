package project.test.argument;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import project.Utils;
import project.test.Test;
import weka.classifiers.functions.supportVector.PolyKernel;

public class ArgumentUtils implements Test {
	
	private Utils utils;
	
	public ArgumentUtils(Utils utils) {
		this.utils = utils;
	}
	
	private HashMap<ArgumentTest, Double> percentResArgsTest = new HashMap<ArgumentTest, Double>();

	public void test(ArrayList<String> listNames, String trainName) {
		System.out.println("1. " + trainName);

		for (int j = 0; j < listNames.size(); j++) {
			String testName = listNames.get(j);
			System.out.println("  " + testName);

			String argKey = "C";

			testRandomDouble(BigDecimal.valueOf(0), BigDecimal.valueOf(10), BigDecimal.valueOf(0.5), (nbGet) -> {
				String res = utils.startComparison(trainName, testName, getOptions(argKey, nbGet.doubleValue()), new PolyKernel());
				String percent = utils.getPercent(res);

				if (percent != null) {
					double percentD = Double.valueOf(percent);
					percentResArgsTest.put(new ArgumentTest(trainName, testName, argKey, nbGet.doubleValue(), percentD), percentD);
				}

				System.out.println("    " + nbGet + " " + percent);
			});
		}
	}
	
	public void checkResult(ArrayList<String> listNames, String trainName) throws IOException {
		String res = trainName;

		for (int j = 0; j < listNames.size(); j++) {
			String testName = listNames.get(j);

			res += "\n  " + testName;

			List<ArgumentTest> argsTest = percentResArgsTest.keySet().stream()
					.filter(argTest -> argTest.getTestName().equals(testName))
					.sorted(Comparator.comparing(argTest -> ((ArgumentTest) argTest).getArgumentVal()))
					.collect(Collectors.toList());

			for (ArgumentTest argTest : argsTest)
				res += "\n    " + argTest.getArgumentKey() + "=" + argTest.getArgumentVal() + "   " + argTest.getPercent();
		}

		String bestRes = "\n\nBEST\n";

		for (int j = 0; j < listNames.size(); j++) {
			String testName = listNames.get(j);

			ArgumentTest best = percentResArgsTest.keySet().stream()
					.filter(argTest -> argTest.getTestName().equals(testName))
					.sorted(Comparator.comparing(argTest -> ((ArgumentTest) argTest).getArgumentVal()))
					.max(Comparator.comparing(argTest -> ((ArgumentTest) argTest).getPercent()))
					.get();

			bestRes += "\n" + best.toString();
		}

		System.out.println(bestRes);

		res += bestRes;

		Path resPath = utils.createResFolder();
		Files.write(Paths.get(resPath.toString(), "resArgs.txt"), res.getBytes());
	}
	
	private String[] getOptions(String opt, double val) {
		return (String[]) Arrays.asList(
				"-" + opt,
				"" + val
				).toArray();
	}

	private void testRandomDouble(BigDecimal min, BigDecimal max, BigDecimal step, Consumer<BigDecimal> callback) {
		for (BigDecimal i = min.add(step); i.compareTo(max) < 0; i = i.add(step))
			callback.accept(i);
	}
}
