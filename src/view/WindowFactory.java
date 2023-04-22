package view;

import controller.*;
import handler.ViewHandler;
import model.UserModel;
import view.window.*;




import java.util.ResourceBundle;

public enum WindowFactory {

	MAIN {
		@Override
		public AbstractWindow createWindow(ViewHandler viewHandler, ResourceBundle bundle, UserModel model) {
			final AbstractController controller = new MainController(viewHandler, model);
			return new MainWindow(controller, bundle, model);
		}
	},
		
	LOGIN {
		@Override
		public AbstractWindow createWindow(ViewHandler viewHandler, ResourceBundle bundle, UserModel model) {
			final AbstractController controller = new LoginController(viewHandler, model);
			return new LoginWindow(controller, bundle, model);
		}
	},
	
	CREATEACCOUNT {
		@Override
		public AbstractWindow createWindow(ViewHandler viewHandler, ResourceBundle bundle, UserModel model) {
			final AbstractController controller = new CreateAccountController(viewHandler, model);
			return new CreateAccountWindow(controller, bundle, model);
		}
	},
	
	GAME {
		@Override
		public AbstractWindow createWindow(ViewHandler viewHandler, ResourceBundle bundle, UserModel model) {
			final AbstractController controller = new GameController(viewHandler, model);
			return new GameWindow(controller, bundle, model);
		}
	},
	
	MESSAGE {
		@Override
		public AbstractWindow createWindow(ViewHandler viewHandler, ResourceBundle bundle, UserModel model) {
			final AbstractController controller = new MessageController(viewHandler, model);
			return new MessageWindow(controller, bundle, model);
		}
	},
	
	SCORE {
		@Override
		public AbstractWindow createWindow(ViewHandler viewHandler, ResourceBundle bundle, UserModel model) {
			final AbstractController controller = new ScoreController(viewHandler, model);
			return new ScoreWindow(controller, bundle, model);
		}
	};
	
	public abstract AbstractWindow createWindow(ViewHandler viewHandler, ResourceBundle bundle, UserModel model);

}
