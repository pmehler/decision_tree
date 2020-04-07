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
		
	}
	
	//Creates the true and false children of node
	private void createSubChildren(TreeNode node){
	
	}
	
	//Computes and returns the remaining info needed if feature is chosen
	//at node.
	private double getRemainingInfo(int feature, TreeNode node){
		
		
	}
	
	//Computes and returns the entropy given the number of positive and 
	//negative examples.
	private double getEntropy(double numPos, double numNeg){
	
		
	}
	
	//Computes and returns the log (base 2) of d. Used by the getEntropy method.
	private double log2(double d){
		return Math.log(d)/Math.log(2);
	}
	
	//Uses the tree to classify the given example as positive (true) or negative (false).
	public boolean classify(Example e){
		
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
