# decision_tree

Decision Tree
Peter Mehler and Emma Neary
CS311 Homework 3

Names of files required to run your program:
	- DecisionTree.java
	- TreeNode.java
	- Example.java
	- TestClassifier.java

	for testing hepatitis classifier:
	- TestClassifierHepatitis.java

Known Bugs: Final evaluation prints out twice due to repetition in TestClassifier.java.

---------- Part 2 ----------
Running the TestClassifier on big set one evaluates to:

	Positive examples correct: 100 out of 100
	Negative examples correct: 100 out of 100

	This suggests that the training set created a tree which performed perfectly on the test set. The previous data must have matched well with the test data.


Running the TestClassifier on big set two evaluates to:

	Positive examples correct: 98 out of 100
	Negative examples correct: 95 out of 100

	This suggests that the train data fits fairly well with the test data, but is not perfect. Decision Tree classifiers are known to suffer from overfitting, because they learn perfectly from previous data. This means that each data point example in the training set will be represented in the tree. Although this bodes well for data which mirrors the training set, new examples may be harder to classify.


Running the TestClassifierHepatitis on hepatitis dataset evaluates to:

	Positive examples correct: 38 out of 42
	Negative examples correct: 7 out of 10
	Percentage of examples correctly classified: 86.5%
	False positives: 3
	False negatives: 4


	The decision tree first classifies patients first by whether they have varices, which is a late stage symptom of the disease, so likely provides significant information about how severe it is. If this symptom is present, the tree considers a number of other test results such as sgot levels, liver size, and bilirubin levels. Depending on if a feature is true or false, the tree either continues looking at other features of a patient or it classifies them as surviving (positive) or dying (negative). When a feature is false, tree is likely to immediately classify patients as surviving, though not always. If a feature is true then the tree tends to consider other features before making its final classification.

	Overall, the tree considers the most decisive symptoms first to determine whether patients survive or not (features which were exclusively shared by most surviving patients or by most deceased patients). Then, to further classify patients, it split them into groups based on the combined presence of certain symptoms and demographics, until these groups’ statuses could be predicted based on the given training data.


--------- Part 3 ----------

Dataset name: Heart Disease Cleveland UCI

Dataset source:
	Kaggle Dataset taken from Cleveland UCI archive
	https://www.kaggle.com/cherngs/heart-disease-cleveland-uci

Dataset description:
	The Heart Disease Cleveland UCI dataset is a collection of patient records with symptoms related to cardiovascular disease.   Each feature is either related to the demographics of the patient or a measurement of their body related to the cardiovascular system.  The outcomes predicted are:  Positive, has heart disease, and Negative, does not have heart disease.

Number of examples in the dataset: 272

Number of features for each example: 13

Tree Performance:

Running the TestClassifier on the heart disease evaluates to:

	Positive examples correct: 57 out of 68
	Negative examples correct: 46 out of 68
	Percentage of examples correctly classified: 76%
	False positives: 22
	False negatives: 11

The decision tree performs fairly well on the data by correctly classifying 76% of 100 test examples. The data was originally not binary, so some features were converted to true/false values rather than specific numeric values. Thus if the  classifier were non-binary, it is likely that the classifications would have been even more accurate. The first feature considered relates to the ST segment, a part of the cardiac cycle, and then the tree considers other physical indicators of heart health as well as demographics and general health indicators such as cholesterol.
