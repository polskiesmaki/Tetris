package controller;

import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


import handler.ViewHandler;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.UserModel;
import model.service.RestServiceException;
import model.HistoryEntry;

public class ScoreController extends AbstractController {
	
	@FXML 
	private Button exitBtn;
	
	@FXML
	private Label userName;
	
	@FXML 
	private TableView<HistoryEntry> tbData;
	
	@FXML 
	private TableColumn<HistoryEntry, Integer> level;
	@FXML 
	private TableColumn<HistoryEntry, Integer> score;
	@FXML 
	private TableColumn<HistoryEntry, String> date;
	@FXML 
	private TableColumn<HistoryEntry, String> playtime;
	
	private ObservableList<HistoryEntry> historyEntry;
	
	private StringProperty gamerName;
	

	public ScoreController(ViewHandler viewHandler, UserModel model) {
		super(viewHandler, model);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialize(URL location, ResourceBundle bundle) {
		
		userName.textProperty().bind(gamerName);
		exitBtn.setOnAction(backToMain());
		
		
		/* make sure the property value factory should be exactly same as 
		 * the e.g getLevel from historyEntry class
		 */
		level.setCellValueFactory(new PropertyValueFactory<>("Level"));
        score.setCellValueFactory(new PropertyValueFactory<>("Score"));
        date.setCellValueFactory(new PropertyValueFactory<>("Date"));
        playtime.setCellValueFactory(new PropertyValueFactory<>("Playtime"));
        
        //add your data to the table here.
        setObservableList();
        tbData.setItems(historyEntry);				
	}
	
	// add your data here from any source 
	public void setObservableList(){
		try {
			historyEntry = FXCollections.observableArrayList(
					model.getHistory()		
					);
		} catch (RestServiceException e) {
					e.printStackTrace();
		}	
			/*zum Debuggen: Gibt alle HistoryEntry´s auf der Konsole aus
			for (HistoryEntry entry : historyEntry) {
				  System.out.println(entry.toString());
			}*/
    }

	private EventHandler<ActionEvent> backToMain() {
		controller.MainController.setWindowState("UserLoggedIn");
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
		if(evt.getPropertyName().equals("add")|| evt.getPropertyName().equals("login")){
			gamerName = new SimpleStringProperty(evt.getNewValue().toString());
		}
	}

}
