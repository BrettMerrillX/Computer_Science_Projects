//Created by Brett Merrill 
//Date:  03/18/2017
//Designed for CIS 212 at the University of Oregon 

//***********************************************************
import java.awt.Color;
import java.awt.Graphics;
//***********************************************************
public class PaintPoint {
	
	
	//***********************************************************
	public static final int CIRCLE_SHAPE = 0;
	public static final int SQUARE_SHAPE = 1;
	public static final int SMALL_SHAPE = 0;
	public static final int MED_SHAPE = 1;
	public static final int LARGE_SHAPE = 2;
	public static final int SIZE_SMALL = 5;
	public static final int SIZE_MED = 10;
	public static final int SIZE_LARGE = 20;
	
	private final int _x;
	private final int _y;
	private final int _shape;
	private final Color _Color;
	private final int _size;
	//***********************************************************
	public PaintPoint(int x, int y, int shape,int size, Color color){
		_x = x;
		_y = y;
		_shape = shape;
		_Color = color;
		_size = size;
	}
	//***********************************************************
	public int getX(){
		return _x;
	}
	//***********************************************************
	public int getY(){
		return _y;
	}
	//***********************************************************
	public int getShape(){
		return _shape;
	}
	//***********************************************************
	public Color getColor(){
		return _Color;
	}
	//***********************************************************
	public void draw(Graphics g) {
		switch (_shape){
				case CIRCLE_SHAPE:
					g.fillOval(_x, _y, _size, _size);
					break;
				case SQUARE_SHAPE:
					g.fillRect(_x, _y, _size, _size);
					break;
			}
	//}
	}
}

