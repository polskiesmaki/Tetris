package view.window;

import controller.AbstractController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import model.UserModel;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public abstract class AbstractWindow {

	private final AbstractController controller;
	private final ResourceBundle bundle;
	private final UserModel model;
	

	public AbstractWindow(AbstractController controller, ResourceBundle bundle, UserModel model) {
		this.controller = controller;
		this.bundle = bundle;
		this.model = model;
	}

	public Parent root() throws IOException {
		FXMLLoader loader = new FXMLLoader(url(), bundle);
		loader.setController(controller);
		return loader.load();
	}

	private URL url() {
		return getClass().getClassLoader().getResource("fxml/" + fxmlFileName());
	}


	public boolean resizable() {
		return false;
	}

	protected abstract String fxmlFileName();
	

}
