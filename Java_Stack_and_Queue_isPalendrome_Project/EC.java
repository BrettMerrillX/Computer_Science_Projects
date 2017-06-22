import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;

public class EC {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//File file = new File("inSample.txt");
		Scanner scan;
		scan = new Scanner(System.in);
		String input; 
		scan.nextLine();
		while (scan.hasNext()){
			input = scan.nextLine();
			if (isPalindromeEC(input)){
				System.out.println("This is a Palindrome.");
			}else{
				System.out.println("Not a Palindrome.");
			}
			
		} 
		scan.close();
		
		
	} 
	
	public static boolean isPalindromeEC(String s){
		// Create a stack and a Queue of chars that represents the passed in string
		// Hint: While you loop through the given string, push the same char onto your stack
		//  that you enqueue into your Queue. This way you can use dequeue to get
		//  the string from left to right, but you pop the string from right to left
		// Compare your Queue and Stack to see if the input String was a Palindrome or not 
		int len = s.length();
		TwoStackQueue<Character> queue = new TwoStackQueue<Character>();  
		Stack<Character> stack = new Stack<Character>();
		for (char ch: s.toCharArray()){
			queue.enqueue(ch);
			stack.push(ch);
		}
		boolean check = true;
		
		for (int i = 0; i<len ; i++){
			Node<Character> checkQueue = queue.dequeue();
			Node<Character> checkStack = stack.pop();
			
			//System.out.println(checkQueue.getData());
			//System.out.println(checkStack.getData());
			
			if(!(Objects.equals(checkQueue.getData(),checkStack.getData()))){
				 check = false;
				 //break;
			}
		}
		return check;
	}

}
