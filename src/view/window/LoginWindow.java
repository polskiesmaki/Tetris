package view.window;

import java.util.ResourceBundle;

import controller.AbstractController;
import model.UserModel;

public class LoginWindow extends AbstractWindow {

	public LoginWindow(AbstractController controller, ResourceBundle bundle, UserModel model) {
		super(controller, bundle, model);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String fxmlFileName() {
		// TODO Auto-generated method stub
		return "login.fxml";
	}

}
