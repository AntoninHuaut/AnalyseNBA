package project.test.kernel;

import weka.classifiers.functions.supportVector.Kernel;

public class KernelTest {
	
	private String trainName;
	private String testName;
	
	private Kernel kernel;
	
	private Double percent;
	
	public KernelTest(String trainName, String testName, Kernel kernel, Double percent) {
		this.trainName = trainName;
		this.testName = testName;
		this.kernel = kernel;
		this.percent = percent;
	}

	public String getTrainName() {
		return trainName;
	}

	public String getTestName() {
		return testName;
	}

	public Kernel getKernel() {
		return kernel;
	}

	public Double getPercent() {
		return percent;
	}
	
	@Override
	public String toString() {
		return getTrainName() + "-" + getTestName() + "   " + getKernel().getClass().getSimpleName() + "   " + getPercent();
	}
}
