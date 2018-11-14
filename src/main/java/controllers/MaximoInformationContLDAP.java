package controllers;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import services.ExternalService;
import services.PageService;

public class MaximoInformationContLDAP extends PageService{
	


	@FXML
	private TextField user;
	@FXML
	private TextField password;
	@FXML
	private ChoiceBox<String> maximoServerName;

    private void setCurrentPageInfo() {
    	//these are the names of interactive TextFields on the page and what their fx:id is
    	inputFields.put("maximo.db.username", user);
    	inputFields.put("maximo.db.password", password);
    	
    	//this is the previous page and the next page in the sequence
    	prevNext.put("prev","MaximoInformation.fxml");
    	prevNext.put("next","OfflineMode.fxml");
    	
    	inputChoiceBoxes.put("maximoServerName", maximoServerName);
    }
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	setCurrentPageInfo();
    	setCommon();
    	Map<String,List<String>> mapOfLists = ExternalService.wsChoiceBoxInput("AllWSInfo.csv");
    	maximoServerName.getItems().addAll(mapOfLists.get("Server"));
    	maximoServerName.getUserData();
	}
    
}