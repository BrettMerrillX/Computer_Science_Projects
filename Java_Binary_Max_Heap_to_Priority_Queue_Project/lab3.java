import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class lab3 {   
	public static void main(String[] args) throws FileNotFoundException {    
		// Loop over the Scanner     
		// Split each line into the task and the corresponding number (if one exists)    
		// Depending on what the input task was, preform the corresponding function     
		//      (ie) insert, maximum, extractMax, isEmpty, or print    
		// Hint: Use a switch-case statement
		
		//File file = new File("inSample.txt");
		//Scanner scan = new Scanner(file);
		Scanner scan = new Scanner(System.in);
		String[] scanInput1 = scan.nextLine().split(" ");
		pQueue<Integer> priorityQ = new pQueue<Integer>(Integer.parseInt(scanInput1[0])); // first line is the length of 
		
		while(scan.hasNext()){
			String[] scanInput2 = scan.nextLine().split(" ");
			String command = scanInput2[0];
			switch (command){
				case "insert":
					priorityQ.insert(Integer.parseInt(scanInput2[1]));
					break;
				case "extractMax":
					Comparable<Integer> temp1 = priorityQ.extractMax();
					System.out.println(temp1);
					break;
				case "maximum":
					Comparable<Integer> temp2 = priorityQ.maximum();
					System.out.println(temp2);
					break;
				case "isEmpty":
					if(priorityQ.isEmpty()){
						System.out.println("Empty");
					}else{
						System.out.println("Not Empty");
					}
					break;
				case "print":
					System.out.print("Current Queue: ");
					priorityQ.print();
					break;
				case "build":
					//Splits string array into an integer array
					String buildInfoString = scanInput2[1].replaceAll("[\\[\\]]",""); // removes brakets
					String[] buildInfo = buildInfoString.split(",");
					Integer[] intBuildInfo = new Integer[buildInfo.length+1];
					for(int i = 1; i < intBuildInfo.length; i++){
						intBuildInfo[i] = Integer.parseInt(buildInfo[i-1]);
					}
					priorityQ.build(intBuildInfo);
					break;
			}
		} 
		scan.close();
	}
}
