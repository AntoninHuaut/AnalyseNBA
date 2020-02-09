package project.data;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;
import weka.core.OptionHandler;

public class TestClassifier {;

	private Classifier classifier;
	private Instances train;
	private Instances test;
	private String[] options;

	public TestClassifier(Classifier classifier, Instances train, Instances test, int classAttribut, String[] options) {
		this.classifier = classifier;
		this.train = train;
		this.test = test;
		this.options = options;
		
		setup(classAttribut);
	}

	private void setup(int classAttribut) {
		this.train.setClassIndex(classAttribut);
		this.test.setClassIndex(classAttribut);
	}

	public String getSummary() throws Exception {
		if (classifier instanceof OptionHandler)
			((OptionHandler) classifier).setOptions(options);
		
		classifier.buildClassifier(train);
		Evaluation eval = new Evaluation(train);
		eval.evaluateModel(classifier, test);

		return eval.toSummaryString();
	}
}
