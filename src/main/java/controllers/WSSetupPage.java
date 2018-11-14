package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import services.PageService;

public class WSSetupPage extends PageService{
	

	@FXML
	private TextField serverName;
	@FXML
	private TextField appName;
	@FXML
	private TextField contextRoot;
	@FXML
	private TextField virtualHostName;
	@FXML
	private TextField initialHeapSize;
	@FXML
	private TextField maximumHeapSize;
	@FXML
	private CheckBox startServer;
	

    private void setCurrentPageInfo() {
    	//these are the names of interactive TextFields on the page and what their fx:id is
    	inputFields.put("serverName",serverName);
    	inputFields.put("appName",appName);
    	inputFields.put("virtualHostName",virtualHostName);
    	inputFields.put("contextRoot",contextRoot);
    	inputFields.put("initialHeapSize",initialHeapSize);
    	inputFields.put("maximumHeapSize",maximumHeapSize);

    	
    	//this is the previous page and the next page in the sequence
    	prevNext.put("prev","License.fxml");
    	prevNext.put("next","WSExistingInformation.fxml");
    	
    	inputCheckBoxes.put("startServer", startServer);
    }
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	setCurrentPageInfo();
    	setCommon();
	}
    
}