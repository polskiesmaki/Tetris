package game;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.scene.shape.Circle;

public class Map {
	int ROWS, COLUMNS, STONE_SIZE;
	int WIDTH, HEIGTH;
	int x=STONE_SIZE/2;
	int y=STONE_SIZE/2;
	int score = 0, bonus = 1; 
	List<List<Circle>> mapPoints = new ArrayList<List<Circle>>();
	Music music = new Music ();
	
	public Map(double WIDTH, double HEIGTH, double STONE_SIZE) {
		this.STONE_SIZE = (int) STONE_SIZE;
		this.HEIGTH = (int) HEIGTH;
		this.WIDTH = (int) WIDTH;
		this.ROWS = (int) (HEIGTH/STONE_SIZE); 
		this.COLUMNS = (int) (WIDTH/STONE_SIZE);
	
		//Füllt die Map mit Kreisen für die Kollisionsprüfung 
		y=(int) (STONE_SIZE/2);
		for(int i = 0; i< ROWS; i++ ) {
			x = this.STONE_SIZE/2;
			List<Circle> mapRow = new ArrayList<Circle>();
        	for (int j = 0; j<COLUMNS; j++) { 
        		mapRow.add(new Circle(x, y, 2));
        		x+=this.STONE_SIZE;
        	}
        	mapPoints.add(mapRow);
        	y+=this.STONE_SIZE;
        }
        
	}
	 
	public List<List<Circle>> getMap() {
		return mapPoints;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore() {
		score = 0;
	}
	//Prüft ob die Zeilen voll sind
	public void fullLineChecker(HashMap<String, StoneShape> rectMap) {
		int lineCount = 0;
		int linesDeleted = 0;
		bonus = 0;
		System.out.println();
    			for(int j = (ROWS-1); j >= 0; j-- ) {
    				lineCount=0;
    	        	for (int n = 0; n<COLUMNS; n++) {
    	        		String coordinates = "" + n +","+ j; 
    	        		StoneShape stone = rectMap.get(coordinates);
	    	        	if(stone != null && mapPoints.get(j).get(n).intersects(stone.getRect(1).getBoundsInLocal())) {
	    	        		lineCount++;
    	        		}
	    	        }
    				if(lineCount == COLUMNS) {
        				//Wenn die Zeile voll ist wird die Funktion line Update aufgerufen 
        				lineUpdate(j, rectMap);
        				j++;
        				linesDeleted++;
        				if(linesDeleted > 1) {
        					bonus++;
        				}
        				score += 50;
        			}
    			}
    			score += bonus*50;
    			System.out.println("Score: " + score);	
    			if(linesDeleted==1) {
    				music.playMusic("346116__lulyc__retro-game-heal-sound.wav");
    			}else if (linesDeleted == 2) {
    				music.playMusic("lines2.wav");
    			}else if (linesDeleted > 2) {
    				music.playMusic("lines3.wav");
    			}
	}
	private void lineUpdate(int line, HashMap<String, StoneShape> rectMap) {
		
		//Löscht die Rectangles in der Vollenzeile sowohl auf dem Plane, als auch in der HashMap
		for (int i = 0; i < COLUMNS; i++) {
			String coordinates = "" + i + "," + line;
			if(rectMap.get(coordinates) != null) {
				rectMap.get(coordinates).clear(i);
			}
			rectMap.remove(coordinates);
			
		}
		
		//Schiebt die oberen Zeilen nach unten
		for (int y = (line-1); y >= 0; y--) {
			for (int x = 0; x<COLUMNS; x++) {
				String activeCoordinates = x + "," +y;
				if(rectMap.get(activeCoordinates) != null) {
					
					String newCoordinates =""+ x + "," +(y+1);
					StoneShape temp = rectMap.get(activeCoordinates);
					rectMap.put(newCoordinates, temp);
					rectMap.get(activeCoordinates).clear(x);
					rectMap.remove(activeCoordinates);
					int newDrawYPosition = (int) (rectMap.get(newCoordinates).getY() + STONE_SIZE);
					rectMap.get(newCoordinates).setY(newDrawYPosition);
					rectMap.get(newCoordinates).add();
				}
			}
		}
	}
}
