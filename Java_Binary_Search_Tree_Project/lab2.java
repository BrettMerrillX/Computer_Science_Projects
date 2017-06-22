import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class lab2 {
	
	public static void main(String[] args) throws FileNotFoundException {
        // Loop over the Scanner        
		// Split each line into the task and the corresponding number (if one exists)        
		// Depending on what the input task was, preform the corresponding function         
		//      (ie) insert, delete, find, inoder, preorder, or postorder       
		// Hint: Use a switch-case statement
        // Don't forget to close your Scanner!
		
		BST<Integer> tree = new BST<Integer>();
		
		//File file = new File("sample.txt");
		Scanner scan = new Scanner(System.in);
		
		while(scan.hasNext()){
			String[] scanInput = scan.nextLine().split(" ");
			String command = scanInput[0];
			switch (command){
				case "insert":
					tree.insert(Integer.parseInt(scanInput[1]));
					break;
				case "delete":
					tree.delete(Integer.parseInt(scanInput[1]));
					break;
				case "find":
					Node<Integer> findTest = tree.find(Integer.parseInt(scanInput[1]));
					if (findTest != null){
					System.out.println("found " + findTest.getData());
					}else {
						System.out.println("Could not find");
					}
					break;
				case "inorder":
					tree.traverse("inorder", tree.getRoot());
					System.out.println();
					break;
				case "preorder":
					tree.traverse("preorder", tree.getRoot());
					System.out.println();
					break;
				case "postorder":
					tree.traverse("postorder", tree.getRoot());
					System.out.println();
					break;
			}
		}
		
		scan.close();

	} 
}