package controller;

import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import handler.ViewHandler;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import model.UserModel;


public class MessageController extends AbstractController {

	    @FXML
	    private Label messageText;

	    @FXML
	    private Button backBtn;
	    
	    @FXML
	    private Button logoutBtn;
	
	public MessageController(ViewHandler viewHandler, UserModel model) {
		super(viewHandler, model);
		// TODO Auto-generated constructor stub
	}
	

	@Override
	public void initialize(URL location, ResourceBundle bundle) {
		// TODO Auto-generated method stub
		messageText.setText("Möchten Sie sich wirklich abmelden?");
		backBtn.setText("Abbrechen");
		backBtn.setOnAction(goBackAction());
		logoutBtn.setOnAction(logoutAction());
		
	}
	
	private EventHandler<ActionEvent> logoutAction() {
		// TODO Auto-generated method stub
		return e->{
			model.signOut();
			viewHandler.closeMessageWindow();
			try {
				viewHandler.launchMessageWindow();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		};
	}


	private EventHandler<ActionEvent> goBackAction() {
		return e -> {
			try {
				viewHandler.closeMessageWindow();
				viewHandler.launchMainWindow();
				
			} catch (IOException ex) {
				// TODO Auto-generated catch block
			}
		};
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		Platform.runLater(new Runnable(){

			@Override
			public void run() {				
				// TODO Auto-generated method stub
				backBtn.setText("OK");
				logoutBtn.setVisible(false);
				
				switch (evt.getPropertyName()) {
					case "add":
						messageText.setText("Sie haben sich erfolgreich registriert.");
						break;
					case "login":
						messageText.setText("Sie haben sich erfolgreich angemeldet.");
						break;
					case "logout":
						messageText.setText("Sie wurden abgemeldet.");
						break;
				}
			}
		});
	}

}
