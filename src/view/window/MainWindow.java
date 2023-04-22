package view.window;

import java.util.ResourceBundle;

import controller.AbstractController;
import model.UserModel;

public class MainWindow extends AbstractWindow{

	public MainWindow(AbstractController controller, ResourceBundle bundle, UserModel model) {
		super(controller, bundle, model);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String fxmlFileName() {
		// TODO Auto-generated method stub
		return "main.fxml";
	}

}
