//Created by Brett Merrill 
//Date:  03/18/2017
//Designed for CIS 212 at the University of Oregon 

//***************************************************************
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JPanel;

public class PaintPanel extends JPanel {
	private ArrayList<PaintPoint> _points;
	
	private Color _globalColor;
	private int _currentShape;
	private int _currentSize;
	
	//***********************************************************
	// PaintPanel() constructor creates the colored points that
	// 		are shown on the MainFrame.
	//***********************************************************
	public PaintPanel(){
		_points = new ArrayList<>();
		_globalColor = Color.BLACK;
		_currentShape = PaintPoint.CIRCLE_SHAPE;
		_currentSize = PaintPoint.SIZE_SMALL;
		
		MouseAdapter adapter = new MouseAdapter(){
			@Override
			public void mouseDragged(MouseEvent e){
				PaintPoint point = new PaintPoint(e.getX(), e.getY(),
						_currentShape,_currentSize, _globalColor);
				_points.add(point);
				repaint();
			}
			public void mouseClicked(MouseEvent e){
				PaintPoint point = new PaintPoint(e.getX(), e.getY(),
						_currentShape,_currentSize, _globalColor);
				_points.add(point);
				repaint();
			}
		};
		
		addMouseListener(adapter);
		addMouseMotionListener(adapter);
	}
	//***********************************************************
	// paintComponent gets the current version of PaintPoint
	//***********************************************************
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		for (PaintPoint point : _points){
			g.setColor(point.getColor());
			point.draw(g);
		}
	}
	//***********************************************************
	// Sets the current color of the PaintPoint
	//***********************************************************
	public void setCurrentColor(Color color){
		_globalColor = color;
		repaint();
		
	}
	//***********************************************************
	// Sets the shape of the PaintPoint
	//***********************************************************
	public void setShape(int shape){
		_currentShape = shape;
	}
	//***********************************************************
	// Sets the size of the PaintPoint
	//***********************************************************
	public void setSize(int size){
		_currentSize = size;
	}
	//***********************************************************
	// Clears the saved points on the MainFrame
	//***********************************************************
	public void clearPoints(){
		_points.clear();
		repaint();
	}
}
