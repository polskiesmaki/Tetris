import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

import handler.AppViewHandler;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
    	new AppViewHandler(primaryStage, ResourceBundle.getBundle("fxml.resources", Locale.getDefault()))
        //.launchGameWindow();
    	.startWindowFactory();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
