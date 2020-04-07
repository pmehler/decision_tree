// Example.java
// A simple container for a set of boolean feature values.

public class Example {

    private final int size;
    private boolean[] values;


    // Make room for this many feature values.
    public Example(int size) {
		this.size = size;
		values = new boolean[size];
    }

    // Set a feature value.
    public void set(int feature, boolean value) {
    	values[feature] = value;
    }

    // Access a feature value.
    public boolean get(int feature) {
    	return values[feature];
    }
    
    public int getSize()
    {
    	return size;
    }
}