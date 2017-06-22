import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class lab4 {    
	public static void main(String[] args) throws FileNotFoundException{  
		//File file = new File("inSample.txt");
		//Scanner sc = new Scanner(file);
		Scanner sc = new Scanner(System.in);     
		String input;       
		String task;      
		int num;       
		RBT<Integer> myRBT = new RBT<Integer>();
		WrongTree<Integer> ecWrongTree = new WrongTree<Integer>(20);			// for extra credit
		RBT<Integer> ecRBT = new RBT<Integer>();// for extra credit
		

        while (sc.hasNextLine()) {    
        	input = sc.nextLine();        
        	String[] phrases = input.split(" ");    
        	task = phrases[0];   
        	switch(task) {           
        	case "insert" :           
        		num = Integer.parseInt(phrases[1]);      
        		myRBT.insert(num);                 
        		break;               
        	case "delete" :          
        		num = Integer.parseInt(phrases[1]);         
        		myRBT.delete(num);                   
        		break;              
        	case "search" :               
        		num = Integer.parseInt(phrases[1]);      
        		Node<Integer> found = myRBT.search(num);    
        		if (found == null){                     
        			System.out.println("Not Found");         
        		} else {        
        			System.out.println("Found");             
        			}              
        		break;            
        	case "traverse" :              
        		myRBT.traverse("preorder", myRBT.getRoot());          
        		System.out.println();               
        		break;           
        	case "exit" :      
        		// TODO:                   
        		System.out.println("Successful Exit");
        		
        		break;               
        	case "test" :            
        		// TODO:       
       			// Implement for EC purposes 
        		ecRBT.isRBT(ecWrongTree.getRoot());
       			break;         
       		default:               
       			break;           
       		}        
       	}      
        sc.close(); 
	}
}