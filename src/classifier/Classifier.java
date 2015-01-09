package classifier;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;

import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.bayes.NaiveBayesMultinomial;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instances;

import weka.core.*;

public class Classifier {



	/**
	 * String that stores the text to classify
	 */
	String text;
	/**
	 * Object that stores the instance.
	 */
	Instances instances;
	/**
	 * Object that stores the classifier.
	 */
	FilteredClassifier classifier;
	/**
	 * This method loads the text to be classified.
	 * @param fileName The name of the file that stores the text.
	 */
	public void load(String fileName) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			String line;
			text = "";
			while ((line = reader.readLine()) != null) {
				text = text + " " + line;
			}
			System.out.println("===== Loaded text data: " + fileName + " =====");
			reader.close();
			System.out.println(text);
		}
		catch (IOException e) {
			System.out.println("Problem found when reading: " + fileName);
		}
	}
	/**
	 * This method loads the model to be used as classifier.
	 * @param fileName The name of the file that stores the text.
	 */
	public void loadModel(String fileName) {
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream("/home/alex/git/ReccomendSubitoSearch/"+fileName));
			Object tmp = in.readObject();
			classifier = (FilteredClassifier) tmp;
			in.close();
			System.out.println("===== Loaded model: " + fileName + " =====");
		}
		catch (Exception e) {
			// Given the cast, a ClassNotFoundException must be caught along with the IOException
			e.printStackTrace();
			System.out.println("Problem found when reading: " + fileName);
		}
	}
	/**
	 * This method creates the instance to be classified, from the text that has been read.
	 */
	public void makeInstance() {
		// Create the attributes, class and text
		FastVector fvNominalVal = new FastVector(2);
		fvNominalVal.addElement("si");
		fvNominalVal.addElement("no");
		Attribute attribute1 = new Attribute("class", fvNominalVal);
		Attribute attribute2 = new Attribute("text",(FastVector) null);
		// Create list of instances with one element
		FastVector fvWekaAttributes = new FastVector(2);
		fvWekaAttributes.addElement(attribute1);
		fvWekaAttributes.addElement(attribute2);
		instances = new Instances("relation", fvWekaAttributes, 1);
		// Set class index
		instances.setClassIndex(0);
		// Create and add the instance
		Instance instance = new Instance(2);
		instance.setValue(attribute2, text);
		// Another way to do it:
		// instance.setValue((Attribute)fvWekaAttributes.elementAt(1), text);
		instances.add(instance);
		
	}
	/**
	 * This method performs the classification of the instance.
	 * Output is done at the command-line.
	 */
	public String classify() {
		try {
			double pred = classifier.classifyInstance(instances.instance(0));
	
			return instances.classAttribute().value((int) pred);
		} 
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("Problem found when classifying the text");
			return "none";
		}
	}
	
	public String classifyText(String text,String category){
		this.text = text;
		loadModel(category);
		makeInstance();
		return classify();
	}
	
	/**
	 * Main method. It is an example of the usage of this class.
	 * @param args Command-line arguments: fileData and fileModel.
	 */
	public static void main (String[] args) {
		Classifier classifier;
		
			classifier = new Classifier();
			classifier.load("sms_test");
			classifier.classifyText(classifier.text,"garanzia");
		
	}
}
