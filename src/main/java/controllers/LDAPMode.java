package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import services.PageService;
public class LDAPMode extends PageService {
	

	@FXML
	private ChoiceBox<String> LDAPMode;
	@FXML
	private Button nextLDAP;
	
	private void setCurrentPageInfo() {
		//these are the names of interactive TextFields on the page and what their fx:id is
	
	    	
	    //this is the previous page and the next page in the sequence
	    prevNext.put("prev","MaximoInformation.fxml");
	    prevNext.put("next","MaximoInformationCont.fxml");
	    
	    
	    inputChoiceBoxes.put("LDAPConnection", LDAPMode);
	    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		LDAPMode.getItems().addAll("true","false");
		setCurrentPageInfo(); 
		setCommon();
		nextLDAP.setOnAction(e -> {
			getUserInput();
			if(LDAPMode.getValue().equals("true")) {
				pageChange("MaximoInformationContLDAP.fxml");
			} else {
				pageChange(prevNext.get("next"));
			}
		});
	}
}
	
	

