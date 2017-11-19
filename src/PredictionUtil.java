import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import weka.classifiers.Classifier;
import weka.classifiers.functions.LinearRegression;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.trees.RandomForest;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SelectedTag;
import weka.core.converters.ConverterUtils.DataSource;

public class PredictionUtil {
	public void buildClassificationModel(String name, String dataPath) throws Exception {
		Classifier cls = new RandomForest();

		Instances inst = new Instances(new BufferedReader(new FileReader(dataPath)));
		inst.setClassIndex(inst.numAttributes() - 1);
		cls.buildClassifier(inst);

		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(name));
		oos.writeObject(cls);
		oos.flush();
		oos.close();
	}

	public void buildRegressionModel(String name, String dataPath) throws Exception {
		Classifier cls = new MultilayerPerceptron();

		Instances inst = new Instances(new BufferedReader(new FileReader(dataPath)));
		inst.setClassIndex(inst.numAttributes() - 1);
		cls.buildClassifier(inst);

		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(name));
		oos.writeObject(cls);
		oos.flush();
		oos.close();
	}

	public String buildRegressionModel(String dataPath) throws Exception {
		LinearRegression linearRegression= new LinearRegression();
		linearRegression.setAttributeSelectionMethod(new SelectedTag(LinearRegression.SELECTION_NONE, LinearRegression.TAGS_SELECTION));
		linearRegression.setEliminateColinearAttributes(false);
		Classifier cls = linearRegression;
		
		DataSource source = new DataSource(dataPath);
		Instances inst = source.getDataSet();
		inst.setClassIndex(inst.numAttributes() - 1);
		cls.buildClassifier(inst);

		return cls.toString();
	}
	public double predictRegression(String name, Map<String, String> nameValue) throws Exception {
		Classifier classifier = (Classifier) weka.core.SerializationHelper.read(name);

		FastVector attributes = null;
		FastVector attribute;
		Instances testing;
		for (String key : nameValue.keySet()) {
			attribute = new FastVector();
			attribute.addElement(nameValue.get(key));
			attributes.addElement(new Attribute(key, attribute));
		}
		;
		testing = new Instances("Test dataset", attributes, 0);
		// 1. set up attributes
		double[] vals = new double[testing.numAttributes()];
		for (int i = 0; i < vals.length; i++) {
			vals[i] = i;
		}
		testing.add(new Instance(1.0, vals));
		double myValue = classifier.classifyInstance(testing.firstInstance());
		return myValue;
	}

	public String predictClassification(String name, Map<String, String> nameValue) throws Exception {
		Classifier classifier = (Classifier) weka.core.SerializationHelper.read(name);

		FastVector attributes = null;
		FastVector attribute;
		Instances testing;
		for (String key : nameValue.keySet()) {
			attribute = new FastVector();
			attribute.addElement(nameValue.get(key));
			attributes.addElement(new Attribute(key, attribute));
		}
		;
		testing = new Instances("Test dataset", attributes, 0);
		// 1. set up attributes
		double[] vals = new double[testing.numAttributes()];
		for (int i = 0; i < vals.length; i++) {
			vals[i] = i;
		}
		testing.add(new Instance(1.0, vals));
		double myValue = classifier.classifyInstance(testing.firstInstance());
		String prediction = testing.classAttribute().value((int) myValue);
		return prediction;
	}

	public List<String> predictClassification(String modelPath, String dataPath, String className) throws Exception {
		Classifier classifier = (Classifier) weka.core.SerializationHelper.read(modelPath);
		Instances test = new Instances(new BufferedReader(new FileReader(dataPath)));
		test.setClassIndex(test.numAttributes() - 1);
		List<String> classes = new ArrayList<String>();
		for (int i = 0; i < test.numInstances(); i++) {
			double pred = classifier.classifyInstance(test.instance(i));
			classes.add(test.classAttribute().value((int) pred));
		}
		return classes;
	}

	public List<Double> predictRegression(String modelPath, String dataPath, String className) throws Exception {
		Classifier classifier = (Classifier) weka.core.SerializationHelper.read(modelPath);
		Instances test = new Instances(new BufferedReader(new FileReader(dataPath)));
		test.setClassIndex(test.numAttributes() - 1);
		List<Double> values = new ArrayList<Double>();
		for (int i = 0; i < test.numInstances(); i++) {
			double pred = classifier.classifyInstance(test.instance(i));
			values.add(pred);
		}
		return values;
	}

	public List<Double> predictClassificationProbability(String modelPath, String dataPath, String className)
			throws Exception {
		Classifier classifier = (Classifier) weka.core.SerializationHelper.read(modelPath);
		Instances test = new Instances(new BufferedReader(new FileReader(dataPath)));
		test.setClassIndex(test.numAttributes() - 1);
		List<Double> classProbability = new ArrayList<Double>();
		for (int i = 0; i < test.numInstances(); i++) {
			double[] pred = classifier.distributionForInstance(test.instance(i));
			classProbability.add(pred[0]);
		}
		return classProbability;
	}
}
