package game;

import java.util.List;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

//Nimmt eine Liste an StoneShapes entgegen und speicher sie in einer Liste. es wird ducrch die Liste
//ittereiert um auf die einzelnen Steine zuzugreifen. 

public class CompositeStone extends StoneShape {
	double x, y;
	 List<StoneShape> components;
	 boolean rotable = true;
	public CompositeStone(Pane root, List<StoneShape> components) {
		super(root);
		this.components = components;
	}

	
	@Override
	public void draw() {
		 components.forEach(c -> c.draw());		
	}

	@Override
	public void move(String direction) {
	components.forEach(c -> c.move(direction));		
	}

	@Override
	public double getY() {
		y=0;
		components.forEach(c -> {			
			c.getY();
			if(c.getY()>y) {y=c.getY();}
			});
		return y;
	
	}
	//Gibt das StoneShape Object zurück
	@Override
	public  StoneShape getStoneShape(int i) {
	    	StoneShape s = components.get(i);
			return s;	    	
	    }

	@Override
	public void setY(double d) {
		components.forEach(c -> c.setY(d));		
	}

	@Override
	public void rotate(StoneShape pivotStone) {		
		components.forEach(c -> c.rotate(pivotStone));
	}
	@Override
	public void clear(int i) {
		components.get(i).clear(i);
	}
	
	//gibt die linke Position des Rechtecks. Initialwert mit 9999
	public double getXLeft() {
		x=9999;
		components.forEach(c -> {			
			c.getX();
			if(c.getX()<x) {x=c.getX();}
			});
		return x;
	}
	
	public double getXRight() {
		x=0;
		components.forEach(c -> {			
			c.getX();
			if(c.getX()>x) {x=c.getX();}
			});
		return x;
	}

	@Override
	public Shape getRect(int g) {
		return components.get(g).getRect(g);
	}

	@Override
	public Shape collisionBottom(int g) {
		return components.get(g).collisionBottom(g);
	}

	@Override
	public boolean checkCollision(List<StoneShape> shapeList) {		
		components.forEach(c -> c.checkCollision(shapeList));
		return false;
	}

	@Override
	public Shape collisionLeft(int g) {
		return components.get(g).collisionLeft(g);
	}
	
	@Override
	public Shape collisionRight(int g) {
		return components.get(g).collisionRight(g);
	}

	@Override
	public double getX() {
		return 0;
	}

	@Override
	public void add() {
		
	}

	@Override
	public Color getColor() {
		return null;
	}

	public void drawPreview(Pane prewiev) {
		components.forEach(c -> c.drawPreview(prewiev));
		
	}

}
