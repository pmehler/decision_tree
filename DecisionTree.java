import java.util.ArrayList;


public class DecisionTree {
	TreeNode root = null;

	//Example[] pos/neg are boolean arrays corresponding to whether or not the example
	//has the feature. These arrays are set in the driver TestClassifier.
	public void train(Example[] positive, Example [] negative){
		root = new TreeNode();
		root.setPos(positive);
		root.setNeg(negative);
		root.setParent(null);
		root.setDecision();
		root.setIsLeaf();

		if(!root.isLeaf){
			train(root);
		}
	}

	//The recursive train method that builds a tree at node
	private void train(TreeNode node){
		Example[] pos = node.getPos();
		Example[] neg = node.getNeg();

		int feature = findNextFeature(node);

		if(feature!=-1){
			node.setFeature(feature);
			createSubChildren(node);
			if (!node.trueChild.isLeaf){
				train(node.trueChild);
			}
			if (!node.falseChild.isLeaf){
				train(node.falseChild);
			}
		}
		else{ // is leaf, no more features
			node.isLeaf = true;
			node.setDecision();
		}
	}

	private int findNextFeature(TreeNode node){
		node.setFeaturesUsed();  // Add featuresUsed array to current node
		double maxInfo = -1;
		int maxInfoIndex = -1;
		// for feature nodes, entropy calculations require the number of times a feature is present in both positive and negative decisions
		double entropy = getEntropy(node.getPos().length, node.getNeg().length);
		for(int i=0; i<node.featuresUsed.length; i++){
			if (node.featuresUsed[i]==false) {  // hasn't been added to the tree yet
				double infoGain = entropy - getRemainingInfo(i, node);
				if (infoGain>maxInfo) {
					maxInfo = infoGain;
					maxInfoIndex = i;
				}
			}
		}
		return maxInfoIndex;  // -1 if no more features
	}

	//Creates the true and false children of node
	private void createSubChildren(TreeNode node){
		Example[] pos = node.getPos();
		Example[] neg = node.getNeg();

		TreeNode trueSubChild = new TreeNode();
		TreeNode falseSubChild = new TreeNode();

		node.trueChild = trueSubChild;
		node.falseChild = falseSubChild;

		trueSubChild.setParent(node);
		falseSubChild.setParent(node);

		ArrayList<Example> truePos = new ArrayList<>();
		ArrayList<Example> falsePos = new ArrayList<>();
		ArrayList<Example> trueNeg = new ArrayList<>();
		ArrayList<Example> falseNeg = new ArrayList<>();

		for(int i=0; i<pos.length; i++){
			if(pos[i].get(node.getFeature())==true){
				truePos.add(pos[i]);
			}
			else{ // falsePos
				falsePos.add(pos[i]);
			}
		}

		for(int i=0; i<neg.length; i++){
			if(neg[i].get(node.getFeature())==true){
				trueNeg.add(neg[i]);
			}
			else{ // falsePos
				falseNeg.add(neg[i]);
			}
		}

		trueSubChild.setPos(truePos.toArray(new Example[truePos.size()]));
		trueSubChild.setNeg(trueNeg.toArray(new Example[trueNeg.size()]));

		falseSubChild.setPos(falsePos.toArray(new Example[falsePos.size()]));
		falseSubChild.setNeg(falseNeg.toArray(new Example[falseNeg.size()]));

		trueSubChild.setIsLeaf();
		falseSubChild.setIsLeaf();

		trueSubChild.setDecision();
		falseSubChild.setDecision();
	}

	//Computes and returns the remaining info needed if feature is chosen
	//at node.
	private double getRemainingInfo(int feature, TreeNode node){
		/*		-Assuming feature is chosen for node
		-Calculate entropy of each possible branch value (true/false), sum with weights of proportion of cases that have that true/false value for given feature
			-Ex. 8/12 Entropy(True) + 4/12 Entropy(False) = Remaining Info
			*/

		int trueBranchNumPos = 0;
		int trueBranchNumNeg = 0;
		int falseBranchNumPos = 0;
		int falseBranchNumNeg = 0;
		Example[] pos = node.getPos();
		Example[] neg = node.getNeg();
		for (int i = 0; i < pos.length; i++){
			if (pos[i].get(feature)){
				trueBranchNumPos++;
			} else {
				falseBranchNumPos++;
			}
		}
		for (int j = 0; j < neg.length; j++){
			if (neg[j].get(feature)){
				trueBranchNumNeg++;
			} else {
				falseBranchNumNeg++;
			}
		}

		int totalNumTrue = trueBranchNumPos+ trueBranchNumNeg;
		int totalNumFalse = falseBranchNumPos + falseBranchNumNeg;
		double trueWeight = (double)totalNumTrue / (totalNumTrue + totalNumFalse);
		double falseWeight = (double)totalNumFalse / (totalNumTrue + totalNumFalse);

		double remainingInfo = trueWeight * getEntropy(trueBranchNumPos, trueBranchNumNeg)
			+ falseWeight * getEntropy(falseBranchNumPos, falseBranchNumNeg);

		return remainingInfo;
	}

	//Computes and returns the entropy given the number of positive and
	//negative examples.
	private double getEntropy(double numPos, double numNeg){
		double probPos = numPos / (numPos+numNeg);
		double probNeg = numNeg / (numPos+numNeg);
		double entropy = (-1 * probPos * log2(probPos)) + (-1 * probNeg * log2(probNeg));
		return entropy;
	}

	//Computes and returns the log (base 2) of d. Used by the getEntropy method.
	private double log2(double d){
		if (d==0){ return 0; }
		return Math.log(d)/Math.log(2);
	}

	//Uses the tree to classify the given example as positive (true) or negative (false).
	public boolean classify(Example e){
		TreeNode currentNode = root;
		while(currentNode.isLeaf==false){
			if (e.get(currentNode.getFeature())==true){
				currentNode = currentNode.trueChild;
			}
			else{ // example has false for this node's feature
				currentNode = currentNode.falseChild;
			}
		}
		//Uses the tree to classify the given example as positive (true) or negative (false).
		return currentNode.decision;
	}


	//Prints the decision tree.
	public void print(){
		printTree(root, 0);
	}


	//Called by print() to print the decision tree.
	private void printTree(TreeNode node, int indent){
		if(node== null)
			return;
		if(node.isLeaf){
			if(node.decision)
				System.out.println("Positive");
			else
				System.out.println("Negative");
		}
		else{
			System.out.println();
			doIndents(indent);
			System.out.print("Feature "+node.getFeature() + " = True:");
			printTree(node.trueChild, indent+1);
			doIndents(indent);
			System.out.print("Feature "+node.getFeature() + " = False:");
			printTree(node.falseChild, indent+1);
		}
	}

	//Called by printTree to print out indentations.
	private void doIndents(int indent){
		for(int i=0; i<indent; i++)
			System.out.print("\t");
	}
}
