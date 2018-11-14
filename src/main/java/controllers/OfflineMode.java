package controllers;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import services.ExternalService;
import services.PageService;
public class OfflineMode extends PageService {
	
	@FXML
	private ChoiceBox<String> offlineMode;
	@FXML
	private Button nextOffline;
	@FXML
	private Button prevLDAP;
	
	private void setCurrentPageInfo() {
		//these are the names of interactive TextFields on the page and what their fx:id is
	    	
	    //this is the previous page and the next page in the sequence
	    prevNext.put("prev","MaximoInformationCont.fxml");
	    prevNext.put("next","PushProperties.fxml");
	    
	    
	    inputChoiceBoxes.put("ezmaxmobile.offline.enabled", offlineMode);
	    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Map<String,String> userInput = ExternalService.readFileToMap("user_input.csv");	
		setCurrentPageInfo(); 
		setCommon();
		offlineMode.getItems().addAll("true","false");
		nextOffline.setOnAction(e -> {
			getUserInput();
			if(offlineMode.getValue().equals("true")) {
				pageChange("OfflineProperties.fxml");
			} else {
				pageChange(prevNext.get("next"));
			}
		});
		prevLDAP.setOnAction(e -> {
			getUserInput();
			if(userInput.get("LDAPConnection").equals("true")) {
				pageChange("MaximoInformationContLDAP.fxml");
			} else {
				pageChange(prevNext.get("prev"));
			}
		});
	}
}
	
	

