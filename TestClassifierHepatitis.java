// TestClassifier.java
// Lisa Torrey
// Trains and tests a decision tree classifier.

import java.util.*;
import java.io.*;

public class TestClassifierHepatitis {

    // Random number generator
    static Random rand = new Random();

    // Constants to use
    static int featureCount = 22;
    static int trainexampleCount = 102;
    static int testexampleCount = 52;
    static int train_poscount = 88;
    static int train_negcount = 14;
    static int test_poscount = 42;
    static int test_negcount = 10;
    static Example[] trainPos, trainNeg;
    static Example[] testPos, testNeg;
 
    // Process arguments.
    public static void main(String[] args) throws FileNotFoundException {

    	trainPos = new Example[train_poscount];
    	trainNeg = new Example[train_negcount];
    	
    	testPos = new Example[test_poscount];
    	testNeg = new Example[test_negcount];
    	
    	testClassifier();
    	
        }


    // Train and test.
    private static void testClassifier() throws FileNotFoundException {
	
    	loadTrainExamples("train-hepatitis.txt");
    
    /*
    for(int i=0; i<trainPos.length; i++){
		for(int j=0; j<trainPos[i].size; j++){
			System.out.print(trainPos[i].get(j)+" ");
		}
		
		System.out.println();
	}
    
    	 
     for(int i=0; i<trainNeg.length; i++){
    	for(int j=0; j<trainNeg[i].size; j++){
    		System.out.print(trainNeg[i].get(j)+" ");
    	}
    		
    	System.out.println();
    }
    */
	
	// Train the tree
	DecisionTree tree = new DecisionTree();
	tree.train(trainPos, trainNeg);
	tree.print();

	// Read or generate tuning examples
	/*
	//Example[] tunePos = makeExamples(true, dataset);
	//Example[] tuneNeg = makeExamples(false, dataset);
	*/

    
	loadTestExamples("test-hepatitis.txt");

	
    /*for(int i=0; i<testPos.length; i++){
		for(int j=0; j<testPos[i].getSize(); j++){
			System.out.print(testPos[i].get(j)+" ");
		}
		
		System.out.println();
	}*/
	
	
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
	
    /*
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

	*/
    }

    /*********************************************
     * Methods to generate sets of fake examples.
     * @throws FileNotFoundException 
     */

    private static void loadTrainExamples(String file) throws FileNotFoundException
    {
    
    	Scanner scan = new Scanner(new File(file));
    	
    	int numpos=0, numneg=0;
    	
    	//read first line
    	scan.nextLine();
    	
    	for(int i=0; i<trainexampleCount; i++){
    		
    		//System.out.println("i: "+i);
    		
    		//read patient number
    		//System.out.println(scan.next());
    		scan.next();
    		
    		//read whether postive or negative
    		String n = scan.next();
    		//System.out.print(n+" ");
    		if (n.equals("positive")){
    			
    			trainPos[numpos] = new Example(featureCount);		
    			
	    		for(int j=0; j<featureCount; j++){
	    			if(scan.hasNextBoolean()){
	    				boolean b = scan.nextBoolean();
	    				//System.out.print(b+" ");
	    				//System.out.println("setting trainPos["+i+"]."+j+" to "+b);
	    				trainPos[numpos].set(j, b);
	    			}
	    		}
	    		numpos++;
    		}
    		else if (n.equals("negative")){
    			
    			trainNeg[numneg] = new Example(featureCount);		
    			
	    		for(int j=0; j<featureCount; j++){
	    			if(scan.hasNextBoolean()){
	    				boolean b = scan.nextBoolean();
	    				//System.out.print(b+" ");
	    				//System.out.println("setting trainNeg["+i+"]."+j+" to "+b);
	    				trainNeg[numneg].set(j, b);
	    			}
	    		}
	    		numneg++;
    		}
    		else{
    			System.out.println("ERROR: "+n);
    		}
    		//System.out.println();
    	}
    	
    	//System.out.println("numpos: "+numpos+", numneg: "+numneg);
    	scan.close();
    	 
    }
    
    
    private static void loadTestExamples(String file) throws FileNotFoundException
    {
    
    	Scanner scan = new Scanner(new File(file));
    	
    	int numpos=0, numneg=0;
    	
    	//read first line
    	scan.nextLine();
    	
    	for(int i=0; i<testexampleCount; i++){
    		
    		//System.out.println("i: "+i);
    		
    		//read patient number
    		//System.out.println(scan.next());
    		scan.next();
    		
    		//read whether postive or negative
    		String n = scan.next();
    		//System.out.print(n+" ");
    		if (n.equals("positive")){
    			
    			testPos[numpos] = new Example(featureCount);		
    			
	    		for(int j=0; j<featureCount; j++){
	    			if(scan.hasNextBoolean()){
	    				boolean b = scan.nextBoolean();
	    				//System.out.print(b+" ");
	    				//System.out.println("setting testPos["+i+"]."+j+" to "+b);
	    				testPos[numpos].set(j, b);
	    			}
	    		}
	    		numpos++;
    		}
    		else if (n.equals("negative")){
    			
    			testNeg[numneg] = new Example(featureCount);		
    			
	    		for(int j=0; j<featureCount; j++){
	    			if(scan.hasNextBoolean()){
	    				boolean b = scan.nextBoolean();
	    				//System.out.print(b+" ");
	    				//System.out.println("setting testNeg["+i+"]."+j+" to "+b);
	    				testNeg[numneg].set(j, b);
	    			}
	    		}
	    		numneg++;
    		}
    		else{
    			System.out.println("ERROR: "+n);
    		}
    		//System.out.println();
    	}
    	
    	System.out.println("numpos: "+numpos+", numneg: "+numneg);
    	scan.close();
    	 
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
