package handler;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.UserModel;
import view.WindowFactory;
import view.window.AbstractWindow;

import java.io.IOException;

import java.util.ResourceBundle;

public class AppViewHandler implements ViewHandler{

	private final Stage primaryStage;
	private Stage infoStage;
	private final ResourceBundle bundle;
	private static final String TITLE = "P&F WiSe 2022";
	private final UserModel model;
	
	private static AbstractWindow main;
	private static AbstractWindow login;
	private static AbstractWindow createAccount;
	private static AbstractWindow game;
	private static AbstractWindow score;
	private static AbstractWindow message;
	
	public AppViewHandler(Stage primaryStage, ResourceBundle bundle) {
		this.primaryStage = primaryStage;
		primaryStage.getIcons().add(new Image("./resources/z.png"));
		this.bundle = bundle;
		this.model = new UserModel();
	}

	@Override
	public void startWindowFactory() throws IOException {
		//Create windows so that they can register directly as listeners model
		main = WindowFactory.MAIN.createWindow(this, bundle, model);
		message = WindowFactory.MESSAGE.createWindow(this, bundle, model);
		score = WindowFactory.SCORE.createWindow(this, bundle, model);	
		
		//start first Window
		launchMainWindow();
	}
	
	@Override
	public void launchMainWindow() throws IOException {
		if (main == null) {
			main = WindowFactory.MAIN.createWindow(this, bundle, model);
		}
		System.out.println();
		buildAndShowScene(primaryStage, main);
	}
	
	@Override
	public void launchLoginWindow() throws IOException {
		if (login == null) {
			login = WindowFactory.LOGIN.createWindow(this, bundle, model);	
		}
		buildAndShowScene(primaryStage, login);
	}
	
	@Override
	public void launchCreateAccountWindow() throws IOException {
		if (createAccount == null) {
			createAccount = WindowFactory.CREATEACCOUNT.createWindow(this, bundle, model);	
		}
		buildAndShowScene(primaryStage, createAccount);
	}
	
	@Override
	public void launchMessageWindow() throws IOException {
		infoStage = new Stage();
		infoStage.setTitle("Info");
		infoStage.initOwner(primaryStage);
		infoStage.initModality(Modality.WINDOW_MODAL);
		
		if (message == null) {
			message = WindowFactory.MESSAGE.createWindow(this, bundle, model);	
		}
		buildAndShowScene(infoStage, message);
	}
	
	@Override
	public void launchGameWindow() throws IOException {
		if (game == null) {
			game = WindowFactory.GAME.createWindow(this, bundle, model);	
		}
		buildAndShowScene(primaryStage, game);
	}
	
	@Override
	public void launchScoreWindow() throws IOException {
		if (score == null) {
			score = WindowFactory.SCORE.createWindow(this, bundle, model);	
		}
		buildAndShowScene(primaryStage, score);
	}
	
	private void buildAndShowScene(Stage stage, AbstractWindow window) throws IOException {
		stage.setTitle(TITLE);
		stage.setResizable(window.resizable());
		stage.setScene(new Scene(window.root()));
		stage.show();	
	}


	@Override
	public void closeMessageWindow() {
		infoStage.close();
	}
	
}
