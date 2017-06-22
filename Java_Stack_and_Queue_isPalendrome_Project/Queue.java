
public class Queue<E> { 
	private Node<E> head; 
	private Node<E> tail;
	public Queue(){
		// We want to initialize our Queue to be empty 
		// (ie) set head and tail to be null
		head = null;
		tail = null;
	}
	public void enqueue(E newData){
		// Create a new node whose data is newData and whose next node is null
		// Update head and tail 
		// Hint: Think about what's different for the first node added to the Queue 
		Node<E> newNode = new Node<E>(newData, null);
		if(isEmpty()){
			head = newNode;
		}else if (tail == null){
			tail = newNode;
			head.setNext(tail);
		}else{
			tail.setNext(newNode);
			tail = newNode;
			
			
		}
	}
	public Node<E> dequeue(){
		// Return the head of the Queue 
		// Update head 
		// Hint: The order you implement the above 2 tasks matters, so use a temporary node
		//      to hold important information 	
		// Hint: Return null on a empty Queue
		if(isEmpty()){
			return null;
		}else{
			Node<E> headHolder = head;
			head = head.getNext();
			return headHolder;
		}
	}
	public boolean isEmpty(){ 
		// Check if the Queue is empty 
		return(head == null);
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