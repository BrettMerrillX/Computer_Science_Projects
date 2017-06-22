

public class MaxHeap<E extends Comparable> {
    private E[] myArray;
    private int maxSize;
    private int length;

    
	@SuppressWarnings("unchecked")
	public MaxHeap(int s){
    	// Build the constructor
		maxSize = s;
    	length = 0;
		myArray = (E[]) new Comparable[maxSize + 1];
    	
    }

	// helper functions
    public E[] getArray(){
        return myArray;
    }

    public void setArray(E[] newArray){
    	if (newArray.length > maxSize+1){
    		return;
    	}
        myArray = newArray;
        length = newArray.length-1;
    }

    public int getMaxSize(){
        return maxSize;
    }

    public void setMaxSize(int ms){
        maxSize = ms;
    }

    public int getLength(){
        return length;
    }

    public void setLength(int l){
        length = l;
    }

    // Other Methods
    @SuppressWarnings("unchecked")
	public void insert(E data){
    
    	// Insert an element into your heap.
    	
    	// When adding a node to your heap, remember that for every node n, 
    	// the value in n is less than or equal to the values of its children, 
    	// but your heap must also maintain the correct shape.
		// (ie there is at most one node with one child, and that child is the left child.)
		// (Additionally, that child is farthest left possible.)
	    length++;
	    	
	    int i =  length;
	    myArray[i] = data;
	    while((i>1) && (myArray[i/2].compareTo(myArray[i])<0)){
	    	E temp = myArray[i/2];
	    	myArray[i/2] = myArray[i];
	    	myArray[i] = temp;
	    	i = i / 2;
	    	}
    	}

    @SuppressWarnings("unchecked")
	public Comparable<E> maximum(){
        // return the maximum value in the heap
		return myArray[1];
    }

    @SuppressWarnings("unchecked")
	public Comparable<E> extractMax(){
        // remove and return the maximum value in the heap
    	E temp = myArray[1];
        myArray[1] = myArray[length];
        length--;
        heapify(1);
        return temp;
		
    }
    
    @SuppressWarnings("unchecked")
	public void heapify(int i){
    	// helper function for reshaping the array
    	int l = i*2;        // Left Child
    	int r = (i*2)+1;	// Right Child
    	int largest = i;
    	if ((l <= length) &&  ((int)myArray[l] > (int)myArray[largest])){ //Checks if left child is bigger than parent
    		largest = l;
    	}
    	if(r <= length &&  (int) myArray[r] > (int)myArray[largest]){ // Checks if right child is biggest
    		largest = r; 
    	}
    	if( largest != i){
    		E temp = myArray[largest];
    		myArray[largest] = myArray[i];
    		myArray[i] = temp;
    		heapify(largest);
    	}
    	
    }
    
    public void buildHeap(E[] newArr){
		// used for the extra credit
    	setArray(newArr);
        for(int i = length/2; i > 0; i--){
        	heapify(i);
        }
    }
    	
}

