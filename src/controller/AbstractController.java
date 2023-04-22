package controller;

import handler.ViewHandler;
import javafx.fxml.Initializable;
import model.UserModel;

import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.ResourceBundle;

public abstract class AbstractController implements Initializable, PropertyChangeListener{

    protected final ViewHandler viewHandler;
    protected final UserModel model;

    public AbstractController(ViewHandler viewHandler, UserModel model) {
        this.viewHandler = viewHandler;
        this.model = model;
        model.addPropertyChangeListener(this);
    }

    @Override
    public abstract void initialize(URL location, ResourceBundle bundle);
}
