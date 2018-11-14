package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import services.PageService;

public class License extends PageService{


	@FXML
	private TextField license;
	@FXML
    protected Button prev;
	

    private void setCurrentPageInfo() {
    	//these are the names of interactive TextFields on the page and what their fx:id is
    	inputFields.put("ezmaxmobile.license",license);

    	//this is the previous page and the next page in the sequence
    	prevNext.put("prev","MiscProperties.fxml");
    	prevNext.put("next","WSSetupPage.fxml");
    	
    }
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	setCurrentPageInfo();
    	setCommon();
    	
	}
    
}