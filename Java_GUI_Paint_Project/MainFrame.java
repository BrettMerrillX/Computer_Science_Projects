//Created by Brett Merrill 
//Date:  03/18/2017
//Designed for CIS 212 at the University of Oregon 


//********************************************************************
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JColorChooser;

//********************************************************************
// MainFrame - The JFrame class. Specifies what will show in the frame 
//********************************************************************
public class MainFrame extends JFrame{
	private final PaintPanel _paintPanel;
	protected static final Color PaintColor = null;
	
	
	
	public MainFrame(){
		super("Java Paint");
		setLayout(new BorderLayout());
		
		_paintPanel = new PaintPanel();
		_paintPanel.setPreferredSize(new Dimension(600,600));
		add(_paintPanel, BorderLayout.CENTER);
		
		
		//***********************************************************
		// Buttons for the frame.
		// includes: ClearButton, CircleButton, SquareButton,
		//		SmallButton, MedButton, LargeButton, 
		//	    ChooseColorButton,_Red,_Blue,_Green,_Yellow
		//***********************************************************
		
		JButton ClearButton = new JButton("Clear");
		ClearButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				_paintPanel.clearPoints();
			}
		});
		JButton CircleButton = new JButton("Circle");
		CircleButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				_paintPanel.setShape(PaintPoint.CIRCLE_SHAPE);
			}
		});
		JButton SquareButton = new JButton("Square");
		SquareButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				_paintPanel.setShape(PaintPoint.SQUARE_SHAPE);
			}
		});
		
		JButton SmallButton = new JButton("Small");
		SmallButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				_paintPanel.setSize(PaintPoint.SIZE_SMALL);
			}
		});
		
		JButton MedButton = new JButton("Medium");
		MedButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				_paintPanel.setSize(PaintPoint.SIZE_MED);
			}
		});
		JButton LargeButton = new JButton("Large");
		LargeButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				_paintPanel.setSize(PaintPoint.SIZE_LARGE);
			}
		});
		
		
		
		
		JButton chooseColorButton = new JButton("Choose Color");
		chooseColorButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Color color = JColorChooser.showDialog(MainFrame.this, 
						"Choose a color!",Color.GREEN);
				if(color != null){
					_paintPanel.setCurrentColor(color);
				}
			}
		});
					
		JButton _Blue = new JButton("Blue");
		_Blue.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				_paintPanel.setCurrentColor(Color.BLUE);
			}
		});
		
		JButton _Red = new JButton("Red");
		_Red.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				_paintPanel.setCurrentColor(Color.RED);
			}
		});
		
		JButton _Green = new JButton("Green");
		_Green.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				_paintPanel.setCurrentColor(Color.GREEN);
			}
		});
		
		JButton _Yellow = new JButton("Yellow");
		_Yellow.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				_paintPanel.setCurrentColor(Color.YELLOW);
			}
		});
		
		
		//***********************************************************
		// Creates the Panels of buttons for the MainFrame
		// Includes: shapeButtonPanel, sizeButtonPanel,
		// 		ChooseColorsPanel
		//***********************************************************
		JPanel shapeButtonPanel = new JPanel(new GridLayout(3,1));
		shapeButtonPanel.add(CircleButton);
		shapeButtonPanel.add(SquareButton);
		shapeButtonPanel.add(ClearButton);
		
		JPanel sizeButtonPanel = new JPanel(new GridLayout(3,1));
		sizeButtonPanel.add(SmallButton);
		sizeButtonPanel.add(MedButton);
		sizeButtonPanel.add(LargeButton);
		
		JPanel ChooseColorsPanel = new JPanel(new GridLayout(5,1));
		ChooseColorsPanel.add(_Blue);
		ChooseColorsPanel.add(_Red);
		ChooseColorsPanel.add(_Green);
		ChooseColorsPanel.add(_Yellow);
		ChooseColorsPanel.add(chooseColorButton);
		
		
		JPanel buttonPanel = new JPanel(new GridLayout(3,1));
		buttonPanel.add(ChooseColorsPanel);
		buttonPanel.add(sizeButtonPanel);
		buttonPanel.add(shapeButtonPanel);
		add(buttonPanel, BorderLayout.WEST);
		
	}
	
}
