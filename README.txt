Decision Tree
Peter Mehler and Emma Neary

Names of files required to run your program:
	- DecisionTree.java
	- TreeNode.java
	- Example.java
	- TestClassifier.java

for testing hepatitis classifier:
	- TestClassifierHepatitis.java


3.   Final Evaluation prints out twice.  InfoGain() occasionally evaluates to NaN, but this does not seem to affect performance.

4.   The discussion of results and data asked for in Part2 and Part3. 

---------- Part 2 ----------
Running Decision Tree on big set one evaluates to:

Positive examples correct: 100 out of 100
Negative examples correct: 100 out of 100

This suggests that the training set created a tree which performed perfectly on the test set.  The previous data must have matched well with the test data. 


Running Decision Tree on big set two evaluates to:

Positive examples correct: 98 out of 100
Negative examples correct: 95 out of 100

This suggests that the train data fits fairly well with the test data, but is not perfect.   Decision Tree classifiers are known to suffer from overfitting, because they learn perfect ly from previous data.  This means that each data point example in the training set will be represented in the tree.  Although this bodes well for data which mirrors the training set, new examples may be hard to classify.  

--------- Part 3 ----------
https://www.kaggle.com/cherngs/heart-disease-cleveland-uci