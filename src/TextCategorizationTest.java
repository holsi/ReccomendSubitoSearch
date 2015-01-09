import weka.core.*;
import weka.core.converters.*;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.trees.*;
import weka.filters.*;
import weka.filters.unsupervised.attribute.*;

import java.io.*;

/**
 * Example class that converts HTML files stored in a directory structure into 
 * and ARFF file using the TextDirectoryLoader converter. It then applies the
 * StringToWordVector to the data and feeds a J48 classifier with it.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 */
public class TextCategorizationTest {

  /**
   * Expects the first parameter to point to the directory with the text files.
   * In that directory, each sub-directory represents a class and the text
   * files in these sub-directories will be labeled as such.
   *
   * @param args        the commandline arguments
   * @throws Exception  if something goes wrong
   */
  public static void main(String[] args) throws Exception {
    // convert the directory into a dataset
    TextDirectoryLoader loader = new TextDirectoryLoader();
    loader.setDirectory(new File("text_example/"));
    Instances dataRaw = loader.getDataSet();
    //System.out.println("\n\nImported data:\n\n" + dataRaw);

    // apply the StringToWordVector
    // (see the source code of setOptions(String[]) method of the filter
    // if you want to know which command-line option corresponds to which
    // bean property)
    StringToWordVector filter = new StringToWordVector();
    filter.setInputFormat(dataRaw);
    Instances dataFiltered = Filter.useFilter(dataRaw, filter);
    //System.out.println("\n\nFiltered data:\n\n" + dataFiltered);

    // train J48 and output model
    NaiveBayes classifier = new NaiveBayes();
    classifier.buildClassifier(dataFiltered);
    //System.out.println("\n\nClassifier model:\n\n" + classifier);
    
    File file = new File("filename.txt");
    
	// if file doesnt exists, then create it
	if (!file.exists()) {
		file.createNewFile();
	}

	FileWriter fw = new FileWriter(file.getAbsoluteFile());
	BufferedWriter bw = new BufferedWriter(fw);
	bw.write("\n\nClassifier model:\n\n" + classifier);
	bw.close();

    
  }
}