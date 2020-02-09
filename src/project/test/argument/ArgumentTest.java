package project.test.argument;

public class ArgumentTest {
	
	private String trainName;
	private String testName;
	
	private String argumentKey;
	private Double argumentVal;
	
	private Double percent;
	
	public ArgumentTest(String trainName, String testName, String argumentKey, Double argumentVal, Double percent) {
		this.trainName = trainName;
		this.testName = testName;
		this.argumentKey = argumentKey;
		this.argumentVal = argumentVal;
		this.percent = percent;
	}

	public String getTrainName() {
		return trainName;
	}

	public String getTestName() {
		return testName;
	}

	public String getArgumentKey() {
		return argumentKey;
	}

	public Double getArgumentVal() {
		return argumentVal;
	}

	public Double getPercent() {
		return percent;
	}
	
	@Override
	public String toString() {
		return getTrainName() + "-" + getTestName() + "   " + getArgumentKey() + "=" + getArgumentVal() + "   " + getPercent();
	}
}
