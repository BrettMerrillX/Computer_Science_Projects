


public class pQueue<E extends Comparable> {
    private MaxHeap<E> myHeap;

    public pQueue (int s) {
        // Build the Constructor
    	myHeap = new MaxHeap<E>(s);
    	
    }

    public void insert(E data){
        myHeap.insert(data);
    }

    public Comparable<E> maximum(){
        return myHeap.maximum();
    }

    public Comparable<E> extractMax(){
        return myHeap.extractMax();
    }

    public boolean isEmpty(){
		return myHeap.getLength() == 0;
        // Return true when the priority queue is empty
        // Hint: Do the actual printing in your lab3.java
    }

	public void build(E[] arr){
    	// used for the extra credit
		myHeap.buildHeap(arr);
    }
    
    public void print(){
        // print out "Current Queue:" 
        // followed by each element separated by a comma. 
        // Do not add spaces between your elements.
    	for (int i=1 ; i<= myHeap.getLength() ; i++){
    		System.out.print(myHeap.getArray()[i]);
    		if (i <= myHeap.getLength()-1){
    			System.out.print(",");
    		}
    	}
    	System.out.println("");
    }
}
