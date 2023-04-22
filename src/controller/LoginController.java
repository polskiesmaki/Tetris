package controller;

import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import handler.ViewHandler;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.UserModel;
import model.service.RestServiceException;

public class LoginController extends AbstractController {
	
	@FXML 
	private TextField userField;
	@FXML 
	private PasswordField passwordField;
	@FXML 
	private Button loginBtn;
	@FXML 
	private Button exitBtn;
	@FXML
	private Hyperlink registerBtn;
	@FXML
	private Label alert;

	public LoginController(ViewHandler viewHandler, UserModel model) {
		super(viewHandler, model);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialize(URL location, ResourceBundle bundle) {
		alert.setVisible(false);
		loginBtn.setDisable(true);
		
		//Binding between TextFields and Display of LoginButton	
		BooleanBinding loginInfoEntered = userField.textProperty()
				.isNotEmpty()
				.and(userField.textProperty().length().greaterThan(3))
				.and(passwordField.textProperty().isNotEmpty())
				.and(passwordField.textProperty().length().greaterThan(5));
		
		loginBtn.disableProperty().bind(loginInfoEntered.not());
		
		//Listen when userField and/or passwordField changes
		ChangeListener<String> changeLoginInfo = new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				alert.setVisible(false);
			}
		};
		
		userField.textProperty().addListener(changeLoginInfo);
		passwordField.textProperty().addListener(changeLoginInfo);
		
		//Button-Event-Handling
		loginBtn.setOnAction(loginUser());
		exitBtn.setOnAction(backToMainWindow());
		registerBtn.setOnAction(registerUser());
	}

	private EventHandler<ActionEvent> loginUser() {
		return e -> {
			String username = userField.getText();
			String password = passwordField.getText();
			
			
			try {
				if (model.signIn(username, password)) {
						viewHandler.launchMessageWindow();
				}
			} catch (RestServiceException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		};
	}
	
	private EventHandler<ActionEvent> backToMainWindow() {
		return e -> {
			try {
				viewHandler.launchMainWindow();
			} catch (IOException ex) {
				/* implementation of alert dialog */
			}
		};
	}
	
	private EventHandler<ActionEvent> registerUser() {
		return e -> {
			try {
				viewHandler.launchCreateAccountWindow();
			} catch (IOException ex) {
				/* implementation of alert dialog */
			}
		};
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName().equals("loginFailured")){
			alert.setVisible(true);
		}
		
	}
}
