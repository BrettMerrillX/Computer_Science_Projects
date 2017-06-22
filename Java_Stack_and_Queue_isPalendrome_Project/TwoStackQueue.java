
public class TwoStackQueue<E> { 
	private Node<E> head; 
	private Node<E> tail;
	Stack<E> stack1;  
	Stack<E> stack2;  
	public TwoStackQueue(){
		// We want to initialize our Queue to be empty 
		// (ie) set head and tail to be null
		stack1 = new Stack<E>(); 
		stack2 = new Stack<E>(); 
		head = null;
		tail = null;
	}
	public void enqueue(E newData){
		// Create a new node whose data is newData and whose next node is null
		// Update head and tail 
		// Hint: Think about what's different for the first node added to the Queue 
		stack1.push(newData);
	}
	public Node<E> dequeue(){
		// Return the head of the Queue 
		// Update head 
		// Hint: The order you implement the above 2 tasks matters, so use a temporary node
		//      to hold important information 	
		// Hint: Return null on a empty Queue
		if(stack2.isEmpty()){
			while(!stack1.isEmpty()){
				stack2.push(stack1.pop().getData());
			}	
		}
		Node<E> temp = null;
		if(!stack2.isEmpty()){
			temp = stack2.pop();	
		}
		return temp;
	}
	public boolean isEmpty(){ 
		// Check if the Queue is empty 
		return(stack1.isEmpty() && stack2.isEmpty());
	}
	public void printQueue(){
		// Loop through your queue and print each Node's data
		Node<E> temphead = head;
		while(temphead != null){
			System.out.println(temphead.getData());
			temphead = temphead.getNext();
			
		}
		
	}
}