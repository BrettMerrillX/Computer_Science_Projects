import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;

public class lab1 {
	public static void main(String[] args){
		// Create a Scanner that reads system input
		// Loop over the scanner's input 
		// For each line of the input, send it to isPalindrome() 
		// If isPalindrome returns true, print "This is a Palindrome." 
		// Otherwise print "Not a Palindrome."
		// Close the Scanner

		Scanner scan;
		scan = new Scanner(System.in);
		String input; 
		scan.nextLine();
		while (scan.hasNext()){
			input = scan.nextLine();
			if (isPalindrome(input)){
				System.out.println("This is a Palindrome.");
			}else{
				System.out.println("Not a Palindrome.");
			}
			
		} 
		scan.close();
		
	} 
	
	public static boolean isPalindrome(String s){
		// Create a stack and a Queue of chars that represents the passed in string
		// Hint: While you loop through the given string, push the same char onto your stack
		//  that you enqueue into your Queue. This way you can use dequeue to get
		//  the string from left to right, but you pop the string from right to left
		// Compare your Queue and Stack to see if the input String was a Palindrome or not 
		int len = s.length();
		Queue<Character> queue = new Queue<Character>();       
		Stack<Character> stack = new Stack<Character>();  
		for (char ch: s.toCharArray()){
			queue.enqueue(ch);
			stack.push(ch);
		}
		boolean check = true;
		for (int i = 0; i< len; i++){
			Node<Character> checkQueue = queue.dequeue();
			Node<Character> checkStack = stack.pop();
			//System.out.println(checkQueue.getData());
			//System.out.println(checkStack.getData());
			
			if(!(Objects.equals(checkQueue.getData(), checkStack.getData()))){
				 check = false;
				 break;
			}
		}
		return check;
	}
}