import java.util.ArrayList;

//Emma Neary, Peter Mehler
//All group members were present and contributing during all work on this project
//We have neither given nor received unauthorized aid on this assignment

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
		int feature = findNextFeature(node);
		if(feature!=-1){ //-1 indicates no feature found
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


	//Finds the feature for node that would provide the greatest info gain
	private int findNextFeature(TreeNode node){
		node.setFeaturesUsed();  //set node's featuresUsed based on parent's
		double maxInfo = -1;     //store the max info gain
		int maxInfoIndex = -1;   //index of feature which provided max info gain

		// calculate the entropy at current node based on
		// the number of positive and negative examples it has
		double entropy = getEntropy(node.getPos().length, node.getNeg().length);

		//calculate info gain of each potential feature
		for(int i=0; i<node.featuresUsed.length; i++){
			if (!node.featuresUsed[i]) { //not yet used in current tree path
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

		//Generate appropriate pos and neg sets for each child
		ArrayList<Example> truePos = new ArrayList<>();
		ArrayList<Example> falsePos = new ArrayList<>();
		ArrayList<Example> trueNeg = new ArrayList<>();
		ArrayList<Example> falseNeg = new ArrayList<>();
		for(int i=0; i<pos.length; i++){
			if(pos[i].get(node.getFeature())){
				truePos.add(pos[i]);
			}
			else{ // falsePos
				falsePos.add(pos[i]);
			}
		}
		for(int i=0; i<neg.length; i++){
			if(neg[i].get(node.getFeature())){
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
		// Count the size of current node's examples sorted into appropriate
		// pos and neg sets for both true and false branches
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

		//Calculate weights of each child branch based on number of examples
		int totalNumTrue = trueBranchNumPos+ trueBranchNumNeg;
		int totalNumFalse = falseBranchNumPos + falseBranchNumNeg;
		double trueWeight = (double)totalNumTrue / (totalNumTrue + totalNumFalse);
		double falseWeight = (double)totalNumFalse / (totalNumTrue + totalNumFalse);

		//Sum entropy of children nodes to get remaining info
		double remainingInfo = trueWeight * getEntropy(trueBranchNumPos, trueBranchNumNeg)
			+ falseWeight * getEntropy(falseBranchNumPos, falseBranchNumNeg);

		return remainingInfo;
	}


	//Computes and returns the entropy given the number of positive and
	//negative examples.
	private double getEntropy(double numPos, double numNeg){
		if (numPos+numNeg==0) { return 0; } //avoid NaN division by 0
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
