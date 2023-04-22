package game;


import java.util.List;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
//Abstracte Klasse für das Composite Pattern
public abstract class StoneShape {
	 Pane root;
	 List<StoneShape> components;
	public boolean rotable;
	    public StoneShape(Pane root2) {
	        this.root = root2;
	    }
	
	public abstract void draw();
	
	public abstract void add();
	
	public abstract void move(String direction);
	
	public abstract double getY();
	
	public abstract void setY(double d);
	
	public abstract void rotate(StoneShape pivotStone);
	
	public abstract double getX();
	
	public abstract void clear(int i);
	
	public abstract Shape getRect(int g);
	
	public abstract boolean checkCollision(List<StoneShape> shapeList);
	
	public abstract Shape collisionBottom(int g);
	
	public abstract Shape collisionLeft(int i);
	 
	public abstract Shape collisionRight(int i);
	
	public abstract double getXLeft();
	 
	public abstract double getXRight();
	
	public abstract StoneShape getStoneShape(int i);
	
	public abstract Color getColor() ;

	public abstract  void drawPreview(Pane preview);
	

		
}
