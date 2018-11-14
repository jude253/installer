package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import services.ExternalService;
import services.PageService;

public class SpashPageController extends PageService {
	
	
	@FXML
	private ImageView logo;
	@FXML
	private Label installer;
	
	private void setCurrentPageInfo() {
	    //these are the names of interactive TextFields on the page and what their fx:id is

	    	
	    //this is the previous page and the next page in the sequence
		prevNext.put("prev","SplashPage.fxml");
	    prevNext.put("next","FolderLocations.fxml");
	    	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		setCurrentPageInfo(); 
		setCommon();
	
		logo.setImage(ExternalService.getImage("EZMax_logo.png"));

	}

}
