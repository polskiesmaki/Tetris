package game;

import java.util.List;

import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.transform.NonInvertibleTransformException;
import javafx.scene.transform.Rotate;

public class StonePart extends StoneShape {
    double x, y, width, height;
    Color paint;
    Rectangle r = new Rectangle();
    Line l;
    
    public StonePart(Pane root, double x, double y, double width, double height, Color paint) {
        super(root);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.paint = paint;
        
    }

    public Color getColor() {
    	return this.paint;
    }
    @Override
    public void draw() {
    	
    	r.setX(x);
    	r.setY(y);
    	r.setWidth(width);
    	r.setHeight(height);
    	r.setArcWidth(5);
    	r.setArcHeight(5);
    	r.setFill(paint);
    	r.setStroke(Color.BLACK);
    	this.root.getChildren().add(r);
    	
       
        }
    //Zeichnet die Preview Pane oben rechts mit dem nächsten Spielstein
    public void drawPreview(Pane preview) {
    	r.setX(x);
    	r.setY(y);
    	r.setWidth(width);
    	r.setHeight(height);
    	r.setArcWidth(5);
    	r.setArcHeight(5);
    	r.setFill(paint);
    	r.setStroke(Color.BLACK);
    	preview.getChildren().add(r);
    }
 
    
    @Override
    public void move(String direction) {
    	
    		if(direction.equals("left")) {
    			r.setX(r.getX()-width);
    		}
    		if(direction.equals("right")) {
    			r.setX(r.getX()+width);
    		}
    		if(direction.equals("down")) {
    			r.setY(r.getY()+height);
    		}
          
    }
    
    public double getY() {
    	return r.getY();
    }
    public double getX() {
    	return r.getX();
    }
    public void setY(double y) {
    	r.setY(y);
    }
    public void setX(double x) {
    	r.setX(x);
    }
    @Override
    public Shape getRect(int g) {
		return r;
    	
    }
    
    boolean temp = false;
	int count = 0;
    
	@Override
	public boolean checkCollision(List<StoneShape> shapeList) {
    	temp = false;
    	count = 0;
    	shapeList.forEach(c ->{
    			if(r.getBoundsInLocal().intersects(c.getRect(count).getBoundsInLocal())) {
    				temp=true;
    			}});
		return temp;
    }

	@Override
	public void rotate(StoneShape pivotStone) {
		Point2D point = new Point2D(r.getX(), r.getY());
		
		Rotate rotate = new Rotate(90, pivotStone.getX(), pivotStone.getY());
		try {
			Point2D q1 = rotate.inverseTransform(point);
			 r.setX(Math.round(q1.getX()));
		     r.setY(Math.round(q1.getY()));
		} catch (NonInvertibleTransformException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	public void clear(int i) {
	root.getChildren().remove(r);
	}

	@Override
	public StoneShape getStoneShape(int i) {
		return this;
	}
    
	public Shape collisionBottom(int g) {
		
		Line line = new Line();
		line.setStartX(r.getX()+3);
		line.setStartY(r.getY()+r.getHeight());
		line.setEndX(r.getX()+r.getWidth()-3);
		line.setEndY(r.getY()+r.getHeight());
		return line;
		
	}
	
	public Shape collisionLeft(int g) {
		Line line = new Line();
		line.setStartX(r.getX());
		line.setStartY(r.getY()+3);
		line.setEndX(r.getX());
		line.setEndY(r.getY()+r.getHeight()-3);
		return line;
	}
	
	public Shape collisionRight(int g) {
		Line line = new Line();
		line.setStartX(r.getX()+r.getWidth());
		line.setStartY(r.getY()+3);
		line.setEndX(r.getX()+r.getWidth());
		line.setEndY(r.getY()+r.getHeight()-3);
		return line;
	}

	@Override
	public double getXLeft() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getXRight() {
		return 0;
	}

	@Override
	public void add() {
		this.root.getChildren().add(r);
		
	}

	
		
}

