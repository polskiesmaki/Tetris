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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.UserModel;
import model.service.RestServiceException;

public class CreateAccountController extends AbstractController {
	
	@FXML 
	private TextField userField;
	@FXML 
	private PasswordField passwordField;
	@FXML 
	private PasswordField secondPasswordField;
	@FXML
	private Button registerBtn;
	@FXML 
	private Button exitBtn;
	@FXML
	private Label alert;


	public CreateAccountController(ViewHandler viewHandler, UserModel model) {
		super(viewHandler, model);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialize(URL location, ResourceBundle bundle) {
		alert.setVisible(false);
		registerBtn.setDisable(true);
		
		//Binding between TextFields and Display of RegisterButton
		BooleanBinding userInfoEntered = userField.textProperty()
				.isNotEmpty()
				.and(userField.textProperty().length().greaterThan(3));
		
		BooleanBinding passwordInfoEntered = passwordField.textProperty()
				.isNotEmpty()
				.and(passwordField.textProperty().length().greaterThan(5));
		
		BooleanBinding secondPasswordInfoEntered = secondPasswordField.textProperty()
				.isNotEmpty()
				.and(secondPasswordField.textProperty().isEqualTo(passwordField.textProperty()));
		
		registerBtn.disableProperty().bind(userInfoEntered.and(passwordInfoEntered).and(secondPasswordInfoEntered).not());
				
		//Listen when userField and/or passwordField changes
		ChangeListener<String> changeRegisterInfo = new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				alert.setVisible(false);
			}
		};
				
		userField.textProperty().addListener(changeRegisterInfo);
		passwordField.textProperty().addListener(changeRegisterInfo);
		secondPasswordField.textProperty().addListener(changeRegisterInfo);
		
		
		// TODO Auto-generated method stub
		registerBtn.setOnAction(registerNewUser());
		exitBtn.setOnAction(backToMainWindow());	
	}
	
	private EventHandler<ActionEvent> registerNewUser() {
		return e -> {
			String username = userField.getText();
			String password = passwordField.getText();
			
				try {
					if(model.signUp(username, password)) {
							viewHandler.launchMessageWindow();
					} else {
						alert.setText("Registrieren nicht möglich. Bitte wählen Sie einen anderen Benutzernamen");
						alert.setVisible(true);
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

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		
	}

}
