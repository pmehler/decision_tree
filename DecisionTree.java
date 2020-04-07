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
		pos = node.getPos();
		neg = node.getNos();


		/*pos = node.getPos(); neg = node.getNeg();
		Find the next feature to split on, i.e. the feature with the most information gain
		Call node.setFeaturesUsed() already written in TreeNode
		Look through not used features - find the one with max infogain  by calling entropy - remaining info for each one
		if a feature, f, was found in Step 2:
		node.setFeature(f)
		createSubChildren(node)
		if (this node’s true child is not a leaf)
		 train(this node’s true child)
		if(this node’s false child is not a leaf)
		train(this node’s false child)
		else// no more features
		set this node as a leaf 
		set this node’s decision as majority of node’s examples - ie, pos if pos.size > neg.size, or reverse - node.setDecision()*/
	}

	private int findNextFeature(Treenode node){
		node.setFeaturesUsed();  // Add featuresUsed array to current node
		double maxInfo = 0;	
		int maxInfoIndex = -1;
		// for feature nodes, entropy calculations require the number of times a feature is present in both positive and negative decisions
		double entropy = getEntropy(node.getPos().length, node.getNeg().length);	
		for(int i=0; i<node.featuresUsed.size(); i++){
			if (featuresUsed[i]==False) {  // hasn't been added to the tree yet
				double remainingInfo = getRemainingInfo(i, node);
				if (remainingInfo>=maxInfo) {
					maxInfo = remainingInfo;
					maxInfoIndex = i;
				}	
			}
		}
		return maxInfoIndex;  // -1 if no more features
	}
	
	//Creates the true and false children of node
	private void createSubChildren(TreeNode node){
/*		Create true and false children - new TreeNodes
		Set to appropriate global vars as children of node
		Set parent to node
		Set pos and neg of both children
		Idea: Loop through pos, neg. Take examples that fit true, add to trueChild’s pos/neg. Take examples that fit false, add to falseChild’s pos/neg. 
		Call children.isLeaf() to decide if leaf
		Call children.setDecision //this just determines the majority of examples that fit*/

	}
	
	//Computes and returns the remaining info needed if feature is chosen
	//at node.
	private double getRemainingInfo(int feature, TreeNode node){
		/*		-Assuming feature is chosen for node
		-Calculate entropy of each possible branch value (true/false), sum with weights of proportion of cases that have that true/false value for given feature
			-Ex. 8/12 Entropy(True) + 4/12 Entropy(False) = Remaining Info
			
		Int trueBranchNumPos, trueBranchNumNeg = 0
		Int falseBranchNumPos, falseBranchNumNeg = 0
		loop through node’s pos
		if feature is true, add to trueBranchNumPos
		if feature if false, add to falseBranchNumPos
		loop through node’s neg
		if feature is true, add to trueBranchNumNeg
		if feature if false, add to falseBranchNumNeg
		Int totalNumPos = trueBranchNumPos+ falseBranchNumPos
		Int totalNumNeg = trueBranchNumNeg + falseBranchNumNeg
		posWeight = totalNumPos / (totalNumPos + totalNumNeg)
		negWeight = totalNumNeg / (totalNumPos + totalNumNeg)
		remainingInfo = posWeight * getEntropy(trueBranchNumPos, trueBranchNumNeg)  + 
		negWeight * getEntropy(falseBranchNumPos, falseBranchNumNeg) 
		return remainingInfo*/

		
	}
	
	//Computes and returns the entropy given the number of positive and 
	//negative examples.
	private double getEntropy(double numPos, double numNeg){
		/*-Num_pos_examples (in which this feature or value is present) / total examples present = probabilty positive
		Num_neg_examples (in which this feature or value is present) / total examples present = probabilty negative

		YES = numPos / (numPos + numNeg)
		NO = numNeg / (numPos + numNeg)
		–Pr(YES)log2(Pr(YES)) – Pr(NO)log2(Pr(NO)) of a given Node*/

		
	}
	
	//Computes and returns the log (base 2) of d. Used by the getEntropy method.
	private double log2(double d){
		return Math.log(d)/Math.log(2);
	}
	
	//Uses the tree to classify the given example as positive (true) or negative (false).
	public boolean classify(Example e){
		//Uses the tree to classify the given example as positive (true) or negative (false).
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
