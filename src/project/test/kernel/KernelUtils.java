package project.test.kernel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import project.Utils;
import project.test.Test;
import weka.classifiers.functions.supportVector.Kernel;
import weka.classifiers.functions.supportVector.NormalizedPolyKernel;
import weka.classifiers.functions.supportVector.PolyKernel;
import weka.classifiers.functions.supportVector.Puk;
import weka.classifiers.functions.supportVector.RBFKernel;

public class KernelUtils implements Test {

	private Utils utils;

	public KernelUtils(Utils utils) {
		this.utils = utils;
	}

	private HashMap<KernelTest, Double> percentResKernelTest = new HashMap<KernelTest, Double>();

	public void test(ArrayList<String> listNames, String trainName) {
		List<Kernel> kernels = Arrays.asList(
				new NormalizedPolyKernel(),
				new PolyKernel(),
				//new PrecomputedKernelMatrixKernel(),
				new Puk(),
				new RBFKernel()
				//new StringKernel()
				);

		System.out.println("2. " + trainName);

		for (int j = 0; j < listNames.size(); j++) {
			String testName = listNames.get(j);
			System.out.println("  " + testName);

			for (Kernel kernel : kernels) {
				String res = utils.startComparison(trainName, testName, new String[] {}, kernel);
				String percent = utils.getPercent(res);

				if (percent != null) {
					double percentD = Double.valueOf(percent);
					percentResKernelTest.put(new KernelTest(trainName, testName, kernel, percentD), percentD);
				}

				System.out.println("    " + kernel.getClass().getSimpleName() + " " + percent);
			}
		}
	}

	public void checkResult(ArrayList<String> listNames, String trainName) throws IOException {
		String res = trainName;

		for (int j = 0; j < listNames.size(); j++) {
			String testName = listNames.get(j);

			res += "\n  " + testName;

			List<KernelTest> argsTest = percentResKernelTest.keySet().stream()
					.filter(argTest -> argTest.getTestName().equals(testName))
					.sorted(Comparator.comparing(argTest -> ((KernelTest) argTest).getKernel().getClass().getSimpleName()))
					.collect(Collectors.toList());

			for (KernelTest argTest : argsTest)
				res += "\n    " + argTest.getKernel().getClass().getSimpleName() + "   " + argTest.getPercent();
		}

		String bestRes = "\n\nBEST\n";

		for (int j = 0; j < listNames.size(); j++) {
			String testName = listNames.get(j);

			KernelTest best = percentResKernelTest.keySet().stream()
					.filter(argTest -> argTest.getTestName().equals(testName))
					.sorted(Comparator.comparing(argTest -> ((KernelTest) argTest).getKernel().getClass().getSimpleName()))
					.max(Comparator.comparing(argTest -> ((KernelTest) argTest).getPercent()))
					.get();

			bestRes += "\n" + best.toString();
		}

		System.out.println(bestRes);

		res += bestRes;

		Path resPath = utils.createResFolder();
		Files.write(Paths.get(resPath.toString(), "resKernel.txt"), res.getBytes());
	}
}
