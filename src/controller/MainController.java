package controller;

import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import handler.ViewHandler;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import model.UserModel;


public class MainController extends AbstractController {
	
	@FXML
    private Button identifyGamerBtn;

    @FXML
    private Button logoutBtn;

    @FXML
    private Button showHistory;

    @FXML
    private Button showRulesOfGame;

    @FXML
    private Button startGame;

    @FXML
    private Label messageLabel;
    
    @FXML
    private Label gameOverLabel;
    
    
    private static String windowState;
    private static BooleanProperty gameOver;
    private String gamerName; 
    private static int score;

	public MainController(ViewHandler viewHandler, UserModel model) {
		super(viewHandler, model);
		windowState = "noUserLoggedIn";
		gameOver = new SimpleBooleanProperty(false);
		// TODO Auto-generated constructor stub
	}
	

	@Override
	public void initialize(URL location, ResourceBundle bundle) {
		//Set properties of the controls
		setPropertiesOfControls();
		
		//Button-Event-Handling
		identifyGamerBtn.setOnAction(loginAction());	
		logoutBtn.setOnAction(logoutAction());
		showHistory.setOnAction(showHistoryAction());
		//showRulesOfGame.setOnAction(showRulesAction());
		startGame.setOnAction(showGameFieldAction());

	}
	
	private EventHandler<ActionEvent> showGameFieldAction() {
		return e -> {
			try {
				score = 0;
				gameOver.setValue(false);
				viewHandler.launchGameWindow();
			} catch (IOException ex) {
				/* implementation of alert dialog */
				ex.printStackTrace();
			}
		};
	}

	private EventHandler<ActionEvent> showHistoryAction() {
		return e -> {
			try {
				score = 0;
				gameOver.setValue(false);
				viewHandler.launchScoreWindow();
			} catch (IOException ex) {
				/* implementation of alert dialog */
				ex.printStackTrace();
			}
		};
	}


	private EventHandler<ActionEvent> logoutAction() {
		return e -> {
			try {
				score = 0;
				gameOver.setValue(false);
				viewHandler.launchMessageWindow();
			} catch (IOException ex) {
				/* implementation of alert dialog */
				ex.printStackTrace();
			}
		};
	}


	public void setPropertiesOfControls() {
		switch (windowState) {
		case "noUserLoggedIn":
			identifyGamerBtn.setDisable(false);
			logoutBtn.setDisable(true);
			showHistory.setDisable(true);
			showRulesOfGame.setDisable(true);
			startGame.setDisable(true);
			messageLabel.setVisible(false);
			break;
		case "UserLoggedIn": 
			identifyGamerBtn.setDisable(true);
			logoutBtn.setDisable(false);
			showHistory.setDisable(false);
			//showRulesOfGame.setDisable(false);
			startGame.setDisable(false);
			
			if(score == 0) {
				messageLabel.setText("Du bist angemeldet als:  " + gamerName);
				messageLabel.setVisible(true);
			}else {
				messageLabel.setText(gamerName + ", dein aktueller Score von: " + score + " Punkten wurde gespeichert.");
				messageLabel.setVisible(true);
			}
			
			
			//label bind to BooleanProperty
			gameOverLabel.setVisible(gameOver.get());
			gameOverLabel.visibleProperty().bind(gameOver);
			break;
		}
	}
	
	private EventHandler<ActionEvent> loginAction() {
		return e -> {
			try {
				viewHandler.launchLoginWindow();
			} catch (IOException ex) {
				/* implementation of alert dialog */
				ex.printStackTrace();
			}
		};
	}


	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		if(evt.getPropertyName().equals("add")|| evt.getPropertyName().equals("login")){
			windowState = "UserLoggedIn";
			gamerName = evt.getNewValue().toString();
		}else if(evt.getPropertyName().equals("logout")) {
			windowState = "NoUserLoggedIn";
		}
		
	}
	
	public static void setWindowState(String state) {
		windowState = state;
	}
	
	public static void setGameOver(Boolean state) {
		gameOver = new SimpleBooleanProperty(state);
	}
	
	public static void setScore(Integer scoreline) {
		score = scoreline;
	}
	
}
