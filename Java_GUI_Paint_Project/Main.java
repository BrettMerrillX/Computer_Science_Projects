//Created by Brett Merrill 
//Date:  03/18/2017
//Designed for CIS 212 at the University of Oregon 
/*
This Project gave me exposure to Java Swing. Using this I built
an interactive paint application with a mix of standard and custom
wigits. This invloved opening a UI window to display the various painting 
tools. 

The application stores data in the file PaintFrame.java by capturing
and drawing points when the user clicks or drags their mouse. Each paint 
point is stored in an ArrayList of instances of the PaintPoint class using

//Instantiates the paint frame
import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {
		MainFrame frame = new MainFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

}
