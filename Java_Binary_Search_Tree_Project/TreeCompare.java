import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TreeCompare {

	public static boolean compareTrees(Node<Integer> tree1, Node<Integer> tree2){
		
			if (tree1 == null && tree2 == null){
				return true;
			}
			
			if (tree1 != null && tree2 != null) {
				return (tree1.getData() == tree2.getData()
						&& compareTrees(tree1.getLeftChild(), tree2.getLeftChild())
						&& compareTrees(tree1.getRightChild(), tree2.getRightChild()));
			}
			return false;
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		BST<Integer> tree1 = new BST<Integer>();
		BST<Integer> tree2 = new BST<Integer>();
		int treeCount = 1;
		BST<Integer> treeVersion = tree1;
		
		//File file = new File(System.in);
		Scanner scan = new Scanner(System.in);
		
		while(scan.hasNext()){
			String[] scanInput = scan.nextLine().split(" ");
			String command = scanInput[0];
			if (command == "6"){
				command = "newTree";
			}
			switch (command){
				case "insert":
					treeVersion.insert(Integer.parseInt(scanInput[1]));
					break;
				case "delete":
					treeVersion.delete(Integer.parseInt(scanInput[1]));
					break;
				case "find":
					Node<Integer> findTest = treeVersion.find(Integer.parseInt(scanInput[1]));
					if (findTest != null){
					System.out.println("found " + findTest.getData());
					}else {
						System.out.println("Could not find");
					}
					break;
				case "inorder":
					treeVersion.traverse("inorder", treeVersion.getRoot());
					System.out.println();
					break;
				case "preorder":
					treeVersion.traverse("preorder",treeVersion.getRoot());
					System.out.println();
					break;
				case "postorder":
					treeVersion.traverse("postorder", treeVersion.getRoot());
					System.out.println();
					break; 
				case "5":
					if(treeCount == 2){
						treeVersion = tree2;
					}
					treeCount++;
				}
					
			}
		
		if (compareTrees(tree1.getRoot(), tree2.getRoot()))
            System.out.println("Both trees are identical");
        else
            System.out.println("Trees are not identical");
		scan.close();

	} 
}


