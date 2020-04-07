
public class TreeNode {
	
	private TreeNode parent;
	private Example[] pos, neg;
	boolean isLeaf, decision;
	int feature;
	TreeNode trueChild;
	TreeNode falseChild;
	boolean[] featuresUsed;
	
	public void setDecision(){
		if(pos.length >= neg.length)
			decision = true;
		else
			decision = false;
	}
	
	public void setIsLeaf(){
		if(pos.length ==0 || neg.length == 0)
			isLeaf = true;
		else
			isLeaf = false;
		
	}
	
	public void setFeaturesUsed(){
		if(parent==null){
			featuresUsed = new boolean[pos[0].getSize()];
			for(int i=0; i<featuresUsed.length; i++)
				featuresUsed[i] = false;
		}else{
			featuresUsed = new boolean[parent.featuresUsed.length];
			for(int i =0; i<featuresUsed.length; i++){
				featuresUsed[i]=parent.featuresUsed[i];
			}
		}
	}
		
	public void setFeature(int f){
		feature = f;
		featuresUsed[f] = true;
	}
	
	public int getFeature(){
		return feature;
	}
	
	public void setParent(TreeNode p){
		parent = p;
		setFeaturesUsed();
	}
	
	public void setPos(Example[] p)
	{
		pos = p;
	}
	
	public void setNeg(Example[] n)
	{
		neg = n;
	}
	
	public Example[] getPos()
	{
		return pos;
	}
	
	public Example[] getNeg()
	{
		return neg;
	}
	
	
}
