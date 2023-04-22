package view.window;

import java.util.ResourceBundle;

import controller.AbstractController;
import model.UserModel;

public class MessageWindow extends AbstractWindow {

	public MessageWindow(AbstractController controller, ResourceBundle bundle, UserModel model) {
		super(controller, bundle, model);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String fxmlFileName() {
		// TODO Auto-generated method stub
		return "message.fxml";
	}

}
