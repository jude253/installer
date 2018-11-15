package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import services.PageService;

public class MaximoInformationCont extends PageService{
	

	@FXML
	private TextField server;
	@FXML
	private TextField user;
	@FXML
	private TextField password;
    

    private void setCurrentPageInfo() {
    	//these are the names of interactive TextFields on the page and what their fx:id is
    	inputFields.put("maximo.server", server);
    	inputFields.put("maximo.db.username", user);
    	inputFields.put("maximo.db.password", password);
    	
    	//this is the previous page and the next page in the sequence
    	prevNext.put("prev","LDAPMode.fxml");
    	prevNext.put("next","OfflineMode.fxml");
    	
    }
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	setCurrentPageInfo();
    	setCommon();
	}
    
}