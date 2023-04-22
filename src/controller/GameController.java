package controller;

import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.ResourceBundle;


import model.service.RestServiceException;
import model.HistoryEntry;
import game.Map;
import game.Randomizer;
import game.StoneShape;

import game.Music;
import handler.ViewHandler;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.NonInvertibleTransformException;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import model.UserModel;

public class GameController extends AbstractController {
	
	@FXML 
	private Button playGameBtn;
	@FXML 
	private Button exitBtn;
	@FXML
    private Pane gamepanel;
	@FXML
    private ComboBox<String> levelSelection;
	@FXML
    private Pane showNextStone;
	@FXML
    private Label scoreLabel;
	@FXML
    private Label timerLabel;
	
	private static final double WIDTH = 300;
	private static final double HEIGTH = 540;
	private static final double STONE_SIZE = 30;
	
	StoneShape s, futureStone;
    HashMap<String, StoneShape> rectMap = new HashMap<String, StoneShape>();
    boolean bottomCol = false;
    boolean keyPressed1 = false;
    Map map = new Map(WIDTH, HEIGTH, STONE_SIZE);
    Timeline timeline;
    Music music = new Music();
    public SimpleIntegerProperty score;
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    private StringProperty timer;
    private int currentLevel;
 

	public GameController(ViewHandler viewHandler, UserModel model) {
		super(viewHandler, model);
	}

	//Initialisiert das Spiel
	@Override
	public void initialize(URL location, ResourceBundle bundle) {
		playGameBtn.setOnAction(startGame());
		exitBtn.setOnMouseClicked(backToMain());	
		
		gamepanel.setMaxSize(WIDTH, HEIGTH);
		gamepanel.setMinSize(WIDTH, HEIGTH); 
		
		if(model.getOnline()) {
			levelSelection.getItems().add("Einfach");
			
			try {
				switch(model.getMaxLevel()) {
				 case 0: break;
				 case 1: break;
				 case 2: levelSelection.getItems().addAll("Mittel"); 
				 		 break;
				 case 3: levelSelection.getItems().addAll("Mittel", "Schwer"); 
				 		 break;
				}
			} catch (RestServiceException e) {
				e.printStackTrace();
			}
		}
		
		playGameBtn.setDisable(true);
		
		//Binding between Level-Selection and Display of PlayGameButton
		playGameBtn.disableProperty().bind(levelSelection.valueProperty().isNull());
		
		//Binding between Value of score and text of label
		score = new SimpleIntegerProperty(0);
		scoreLabel.textProperty().bind(score.asString());
		
		//Binding between Value of timer and text of label
		timer = new SimpleStringProperty("00:00:00");
		timerLabel.textProperty().bind(timer);
	}

	private EventHandler<ActionEvent> startGame(){
		return e -> {
			map.setScore();
			Randomizer randomStone = new Randomizer(gamepanel, s, STONE_SIZE, WIDTH);
			s = randomStone.spawnStone();
			s.draw();
			futureStone = randomStone.spawnStone();
		    futureStone.drawPreview(showNextStone);
			music.playMusic("624883__sonically-sound__old-video-game-6.wav");
			gamepanel.setFocusTraversable(true);
			gamepanel.addEventFilter(KeyEvent.KEY_PRESSED, evt -> {
				handleKeyInput(randomStone, evt);
			});
			
			//timer and rate/duration für das Fallen der Steine
			double frameDuration = 0;
			switch(levelSelection.getValue()) {
				case "Einfach": currentLevel = 1;
								frameDuration = 1; 
								break;
				case "Mittel": currentLevel = 2;
							  frameDuration = 2; 
								break;
				case "Schwer": currentLevel = 3;
								frameDuration = 4; 
								break;
			}
			fallingTimer(gamepanel, randomStone, frameDuration);
		};
	}
	
	private void handleKeyInput(Randomizer randomStone, KeyEvent evt) {
		boolean leftCol  = collisionLeft(gamepanel, randomStone);
    	boolean rightCol = collisionRight(gamepanel, randomStone);
    		//Bewegung rechts
            if (evt.getCode().toString().equals("RIGHT") && !rightCol && s.getXRight()+STONE_SIZE < WIDTH) {
            		s.move("right");
            		evt.consume();
            		gamepanel.requestFocus();
            }
            //Bewegung links
            if (evt.getCode().toString().equals("LEFT") && !leftCol && s.getXLeft()>0) {
            		s.move("left");
            		evt.consume();
            		gamepanel.requestFocus();
            }
            //Bewegung nach unten
            if (evt.getCode().toString().equals("DOWN")) {
            	
            	if(!bottomCol) {
            		if(!(s.getY()>=HEIGTH-STONE_SIZE)) {
            			collisionBottom(gamepanel, randomStone);
            			if(!bottomCol) {
            				s.move("down");
            				evt.consume();
                    		gamepanel.requestFocus();
            			}
                	}
            	}
            }
            //Rotation, drücke UP
            if (evt.getCode().toString().equals("UP") && s.rotable && rotatePossible(gamepanel, s.getStoneShape(1))) {
            	s.rotate(s.getStoneShape(1));
            	evt.consume();
        		gamepanel.requestFocus();
            }
            if(s.getY()>=HEIGTH-STONE_SIZE) {
            	stonesOnMapCalc(gamepanel, randomStone);
            }
            collisionBottom(gamepanel, randomStone);
		
	}

	private EventHandler<? super MouseEvent> backToMain() {
		controller.MainController.setWindowState("UserLoggedIn");
		return e -> {
			try {
				if(timeline != null) {
					if(score.get()>0) {
						saveScore(currentLevel, score.get(), timer.get());
						controller.MainController.setScore(score.get()); 
					}				
					timeline.stop();
					rectMap.clear();
				}
				viewHandler.launchMainWindow();
			} catch (IOException ex) {
				/* implementation of alert dialog */
			}
		};
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		
	}
	
	//Diese Methode ist Zuständig für das Fallen der Spielstene
	//Dies ist durch die Timeline implementiert
	private void fallingTimer(Pane gamepanel, Randomizer randomStone, double frameDuration) {
		long startTime = System.currentTimeMillis();
		timeline = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {
			gamepanel.requestFocus();
			
    		collisionBottom(gamepanel, randomStone);
    		if(s.getY()>=HEIGTH-STONE_SIZE) {
    			stonesOnMapCalc(gamepanel, randomStone);
    		}
    		if(s.getY()<HEIGTH-STONE_SIZE) {
    			  s.move("down");
    		}
    		
    		formateTimestamp(System.currentTimeMillis()-startTime);
    		
    		if(currentLevel == 1) {
    			if(System.currentTimeMillis()-startTime >= (0.5*60*1000)) {
    				currentLevel = 2;
    				timeline.setRate(2.0);
    				System.out.println("newLevel");
    			}
    		} else if (currentLevel == 2) {
    			if(System.currentTimeMillis()-startTime >= (1*60*1000)) {
    				currentLevel = 3;
    				timeline.setRate(4.0);
    				System.out.println("newLevel");
    			}
    		}
    	}));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.setRate(frameDuration);
	 	timeline.play();
	 	
	 	playGameBtn.disableProperty().bind(Bindings.createBooleanBinding(
	 		    () -> timeline.getStatus() == Animation.Status.RUNNING,
	 		    timeline.statusProperty()));
	 	
	 	levelSelection.disableProperty().bind(Bindings.createBooleanBinding(
	 		    () -> timeline.getStatus() == Animation.Status.RUNNING,
	 		    timeline.statusProperty()));
	}

	@SuppressWarnings("deprecation")
	public void formateTimestamp (long milliseconds) {
		Date result = new Date(milliseconds);
		//Stunden korrigiert,da 'Date' automatisch immer 1:00:00 setzt als Beginn
		result.setHours((int)(milliseconds/(1000*60*60)));
		timer.set(sdf.format(result));
	}

	private void collisionBottom(Pane gamepanel, Randomizer randomStone) {
		
    		for(int i=0; i<4; i++) {
    			
    			StoneShape stonePart = s.getStoneShape(i);
    			int keyXint =  (int) ((int) stonePart.getX()/STONE_SIZE);
    			int keyYint =  (int) ((int) stonePart.getY()/STONE_SIZE)+1;
    			String key = "" + keyXint + "," + keyYint;
    			StoneShape partOfMap = rectMap.get(key);
    			
    				if(partOfMap != null && s.collisionBottom(i).intersects(partOfMap.getRect(1).getLayoutBounds())){
    	    			System.out.println("Kollision!");
    	    			bottomCol = true;
    	    			stonesOnMapCalc(gamepanel, randomStone);
	    				
    	    			keyPressed1 = false;
    	    			bottomCol = false;
    	    		}
    		}
	}
	
	private void gameOver(){
		controller.MainController.setWindowState("UserLoggedIn");
		controller.MainController.setGameOver(true);
			try {
				if(timeline != null) {
					if(score.get()>0) {
						saveScore(currentLevel, score.get(), timer.get());
						controller.MainController.setScore(score.get());     
					}				
					timeline.stop();
					rectMap.clear();
				}
				music.playMusic("lose2.wav");
				viewHandler.launchMainWindow();
			} catch (IOException ex) {
				/* implementation of alert dialog */
			}				
	}
	
	//Kalkuliert Kollissionen auf der Map und ruft Methoden zum erzeugen neuer Spielsteien auf.
	private void stonesOnMapCalc(Pane gamepanel, Randomizer randomStone) {
		String [] keys = new String [4];
		music.playMusic("block_rpg.wav");
		for (int i = 0; i<4; i++) {
			StoneShape stonePart = s.getStoneShape(i);
			int keyXint =  (int) ((int) stonePart.getX()/STONE_SIZE);
			int keyYint =  (int) ((int) stonePart.getY()/STONE_SIZE);
			String key = "" + keyXint + "," + keyYint;
			keys[i]= key;
			rectMap.put(key, stonePart);
		}
	
		map.fullLineChecker(rectMap);
		score.set(map.getScore());
    	s =  futureStone;
    	futureStone = randomStone.spawnStone();
    	s.draw();
    	futureStone.drawPreview(showNextStone);
    	
    	gameover:
    	for (int i = 0; i<4; i++) {
    		for(int j = 0; j < 4; j++) {
    			if(rectMap.get(keys[j])!=null) {
    				if(s.collisionBottom(i).intersects(rectMap.get(keys[j]).getRect(j).getLayoutBounds())) {
    					System.out.println("GAME OVER");
    					gameOver();
    				break gameover;}}}
    	}
	}
	
	private boolean collisionLeft(Pane gamepanel, Randomizer randomStone) {
		boolean b = false;
	
		for(int i=0; i<4; i++) {
			StoneShape stonePart = s.getStoneShape(i);
			int keyXint =  (int) ((int) stonePart.getX()/STONE_SIZE)-1;
			int keyYint =  (int) ((int) stonePart.getY()/STONE_SIZE);
			String key = "" + keyXint + "," + keyYint;
			StoneShape partOfMap = rectMap.get(key);
				if(partOfMap != null && s.collisionLeft(i).intersects(partOfMap.getRect(1).getLayoutBounds())) {
	    			b = true;
	    			return b;
	    		}
    		}
    	return b;
	}
	
	private boolean collisionRight(Pane gamepanel, Randomizer randomStone) {
		boolean b = false;
		  
		for(int i=0; i<4; i++) {
			StoneShape stonePart = s.getStoneShape(i);
			int keyXint =  (int) ((int) stonePart.getX()/STONE_SIZE)+1;
			int keyYint =  (int) ((int) stonePart.getY()/STONE_SIZE);
			String key = "" + keyXint + "," + keyYint;
			StoneShape partOfMap = rectMap.get(key);
				if(partOfMap != null && s.collisionRight(i).intersects(partOfMap.getRect(1).getLayoutBounds())) {
	    			b = true;
	    			return b;
	    		}
		}
		
    	return b;
	}
    // Prüfen ob Rotation möglich, es wird eine Kopie angelegt
    private boolean rotatePossible(Pane gamepanel, StoneShape pivotStone) {
    	boolean b  = true;
    	for(int i = 0; i < 4; i++) {
    		Rectangle rectangleCopy = new Rectangle(s.getStoneShape(i).getX(), s.getStoneShape(i).getY(), STONE_SIZE, STONE_SIZE);
	    	
    		Point2D point = new Point2D(rectangleCopy.getX(), rectangleCopy.getY());
    		
    		Rotate rotate = new Rotate(90, pivotStone.getX(), pivotStone.getY());
    		try {
    			Point2D q1 = rotate.inverseTransform(point);
    			rectangleCopy.setX(Math.round(q1.getX()));
    			rectangleCopy.setY(Math.round(q1.getY()));
    		} catch (NonInvertibleTransformException e) {
    			e.printStackTrace();
    		}
    		if(rectangleCopy.getX()<0 || rectangleCopy.getX()>(WIDTH-STONE_SIZE) 
    	    		|| rectangleCopy.getY()<0 || rectangleCopy.getY()>(HEIGTH-STONE_SIZE)) {
    	    	b = false;
    	        return b ;
    	    }
    		
    		for (Entry<String, StoneShape> entry : rectMap.entrySet()) {
	    	    StoneShape shape = entry.getValue();
	    	    if (rectangleCopy.intersects(shape.getStoneShape(i).getRect(i).getBoundsInLocal())) {
	    	        // Intersection detected, cancel rotation
	    	    	b = false;
	    	        return b ;
	    	    }
	    	    
	    	}
    	}
		return b;
    }
    
    public void saveScore(int level, int score, String playtime) {    	
    	HistoryEntry historyEntry = new HistoryEntry(level, score, currentDate() , playtime);
        System.out.print("Eine neuer History-Eintrag wurde erzeugt: ");
        try{
            System.out.println(model.addHistory(historyEntry));
        }catch(RestServiceException exp){
            System.out.println(exp.getMessage());
            if (exp.getCause() != null) {
                System.out.println("Caused by: " + exp.getCause());
            }
        }
    }
    
    public String currentDate() {
     	LocalDate today = LocalDate.now();
    	DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd.MM.yyyy");
       	return today.format(myFormatObj);
    }
}
