package model;

import model.service.RestServiceException;
import model.HistoryEntry;
import model.service.RestService;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;


/**
 * Rest client controller
 * handles REST service
 */
public class UserModel {

    private RestService restService;
    private Boolean isOnline;
    PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public UserModel() {
        isOnline = false;
        try {
            isOnline = this.checkConnection();
        } catch (RestServiceException exp) {
            System.out.println(exp.getMessage());
            if (exp.getCause() != null) {
                System.out.println("Caused by: " + exp.getCause());
            }
        }
    }


    public Boolean getOnline() {
        return isOnline;
    }

    public void setOnline(Boolean online) {
        isOnline = online;
    }

    //Add or delete observer from the list
    public void addPropertyChangeListener(PropertyChangeListener l) {
        pcs.addPropertyChangeListener(l);
        //showListeners();
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        pcs.removePropertyChangeListener(l);
    }

    //Output all observers to the console for testing
    public void showListeners() {
        PropertyChangeListener[] liste = pcs.getPropertyChangeListeners();
        for (int i = 0; i < liste.length; i++) {
            System.out.println(liste[i]);
        }
    }

    //handle PropertyChangeListenerEvents  
    public Boolean signIn(String username, String password) throws RestServiceException {
        this.restService = new RestService();
        try {
            if (this.restService.signIn(username, password)) {
                pcs.firePropertyChange("login", null, username);
                return true;
            }
        } catch (RestServiceException exp) {
            this.restService = null;
            pcs.firePropertyChange("loginFailured", null, username);
            throw exp;
        }
        return false;
    }

    public List<HistoryEntry> getHistory() throws RestServiceException {
        if (this.restService == null) {
            throw new RestServiceException("Service not initialized. Please login first", 800);
        }
        return this.restService.getHistory();
    }

    public Boolean signUp(String username, String password) throws RestServiceException {
        if (RestService.signUp(username, password)) {
            pcs.firePropertyChange("add", null, username);
            return true;
        }
        return false;
    }

    public Boolean signOut() {
        restService = null;
        pcs.firePropertyChange("logout", null, null);
        return restService == null;
    }

    public Boolean addHistory(HistoryEntry historyEntry) throws RestServiceException {
        if (this.restService == null) {
            throw new RestServiceException("Service not initialized. Please login first", 800);
        } else if (this.restService.addHistory(historyEntry)) {
            pcs.firePropertyChange("newScore", null, null);
            return true;
        } else
            return false;
    }

    public Integer getMaxLevel() throws RestServiceException {
        if (this.restService == null) {
            throw new RestServiceException("Service not initialized. Please login first", 800);

        } else {
            return this.restService.getMaxLevel();
        }
    }

    public Boolean checkConnection() throws RestServiceException {
        return RestService.checkConnection();
    }


}