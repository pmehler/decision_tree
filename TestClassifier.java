// TestClassifier.java
// Lisa Torrey
// Trains and tests a decision tree classifier.

import java.util.*;
import java.io.*;

public class TestClassifier {

    // Random number generator
    static Random rand = new Random();

    // Constants to use
    static int exampleCount;
    static int featureCount;
 
    // Process arguments.
    public static void main(String[] args) throws FileNotFoundException {

    	if (args.length == 2 && args[0].equals("set1")){
    		if(args[1].equals("small")){
    			exampleCount = 10;
    			featureCount = 8;
    			testClassifier("1", "small");
    		}
    		else if(args[1].equals("big")){
    			exampleCount = 100;
    			featureCount = 10;
    			testClassifier("1", "big");
    		}
    		else
    			System.out.println("Usage: java TestClassifier set1|set2 small|big");
    	}
    	else if (args.length == 2 && args[0].equals("set2")){
    		if(args[1].equals("small")){
    			exampleCount = 10;
    			featureCount = 8;
    			testClassifier("2", "small");
    		}
    		else if(args[1].equals("big")){
    			exampleCount = 100;
    			featureCount = 10;
    			testClassifier("2", "big");
    		}
    		else
    			System.out.println("Usage: java TestClassifier set1|set2 small|big");
    	}
    	else
    	    System.out.println("Usage: java TestClassifier set1|set2 small|big");
        }


    // Train and test.
    private static void testClassifier(String dataset, String size) throws FileNotFoundException {

	// Generate training examples
	//Example[] trainPos = makeExamples(true, dataset);
	//Example[] trainNeg = makeExamples(false, dataset);
	
    Example[] trainPos = loadExamples("train_pos"+dataset+"-"+size+".txt");
    Example[] trainNeg = loadExamples("train_neg"+dataset+"-"+size+".txt");
    
	
	// Train the tree
	DecisionTree tree = new DecisionTree();
	tree.train(trainPos, trainNeg);
	tree.print();

	// Read or generate tuning examples
	/*
	//Example[] tunePos = makeExamples(true, dataset);
	//Example[] tuneNeg = makeExamples(false, dataset);
	*/

	// Read or generate testing examples
	//Example[] testPos = makeExamples(true, dataset);
	//Example[] testNeg = makeExamples(false, dataset);
	
	Example[] testPos = loadExamples("test_pos"+dataset+"-"+size+".txt");
	Example[] testNeg = loadExamples("test_neg"+dataset+"-"+size+".txt");

	// Evaluate on positives
	int correct = 0;
	for (Example e : testPos)
	    if (tree.classify(e))
		correct++;
	System.out.println("Positive examples correct: "+correct+" out of "+testPos.length);

	// Evaluate on negatives
	correct = 0;
	for (Example e : testNeg)
	    if (!tree.classify(e))
		correct++;
	System.out.println("Negative examples correct: "+correct+" out of "+testNeg.length);
	System.out.println();
	
	 //Prune and display the tree
	 /*tree.prune(tunePos, tuneNeg);
	 System.out.println("PRUNNED TREE");
	 tree.print();
	 */
	
	// Evaluate on positives
		correct = 0;
		for (Example e : testPos)
		    if (tree.classify(e))
			correct++;
		System.out.println("Positive examples correct: "+correct+" out of "+testPos.length);

		// Evaluate on negatives
		correct = 0;
		for (Example e : testNeg)
		    if (!tree.classify(e))
			correct++;
		System.out.println("Negative examples correct: "+correct+" out of "+testNeg.length);
		System.out.println();

	
    }

    /*********************************************
     * Methods to generate sets of fake examples.
     * @throws FileNotFoundException 
     */

    private static Example[] loadExamples(String file) throws FileNotFoundException
    {
    	Example[] exs = new Example[exampleCount];
    	
    	Scanner scan = new Scanner(new File(file));
    
    	for(int i=0; i<exampleCount; i++){
    		
    		exs[i] = new Example(featureCount);
    		
    		for(int j=0; j<featureCount; j++){
    			if(scan.hasNextBoolean())
    				exs[i].set(j, scan.nextBoolean());
    		}
    	}
    	
    	scan.close();
    	
    	return exs; 
    }
    
    
    
    // Make an array of fake examples.
    private static Example[] makeExamples(boolean positive, String dataset) {

	Example[] examples = new Example[exampleCount];
	for (int i=0; i<exampleCount; i++) {
	    if (positive)
		examples[i] = makePositive();
	    else
		examples[i] = makeNegative(dataset);
	}

	return examples;
    }

    // Make a single positive example.
    private static Example makePositive() {

	// Set all features randomly first
	Example e = new Example(featureCount);
	for (int j=0; j<featureCount; j++)
	    e.set(j, rand.nextBoolean());
	
	// Create the positive pattern: feature 3, 7, or 4 is true
	// Always do this for both datasets
	int r = rand.nextInt(3);
	if (r == 0)
	    e.set(3, true);
	else if (r == 1)
	    e.set(7, true);
	else
	    e.set(4, true);

	return e;
    }

    // Make a single negative example.
    private static Example makeNegative(String dataset) {

	// Set all features randomly first
	Example e = new Example(featureCount);
	for (int j=0; j<featureCount; j++)
	    e.set(j, rand.nextBoolean());

	// Create the negative pattern: feature 3, 7, and 4 are false
	// Always do this for Set 1 so it has no noise
	if (dataset.equals("set1")) {
	    e.set(3, false);
	    e.set(7, false);
	    e.set(4, false);
	}

	// Do this only most of the time for Set 2
	if (dataset.equals("set2") && rand.nextDouble() < 0.9) {
	    e.set(3, false);
	    e.set(7, false);
	    e.set(4, false);
	}

	return e;
    }
    
    
}
