package handler;

import java.io.IOException;


public interface ViewHandler{
	
	void startWindowFactory() throws IOException;
	void launchMainWindow() throws IOException;
	void launchLoginWindow() throws IOException;
	void launchCreateAccountWindow() throws IOException;
	void launchMessageWindow() throws IOException;
	void launchGameWindow() throws IOException;
	void launchScoreWindow() throws IOException;
	void closeMessageWindow();

}
