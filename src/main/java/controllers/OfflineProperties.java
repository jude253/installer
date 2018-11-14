package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import services.PageService;
public class OfflineProperties extends PageService {
	
	@FXML
	private ChoiceBox<String> autoSyncEnabled;
	@FXML
	private TextField autoSyncInterval;
	@FXML
	private ChoiceBox<String> offlineStartCenter;
	@FXML
	private Button advOfflineProps;
	
	private void setCurrentPageInfo() {
		//these are the names of interactive TextFields on the page and what their fx:id is
		inputFields.put("ezmaxmobile.offline.autosync.interval",autoSyncInterval);
	    	
	    //this is the previous page and the next page in the sequence
	    prevNext.put("prev","OfflineMode.fxml");
	    prevNext.put("next","PushProperties.fxml");
	    
	    inputChoiceBoxes.put("ezmaxmobile.offline.autosync.enabled",autoSyncEnabled);
	    inputChoiceBoxes.put("ezmaxmobile.offline.startcenter.enabled",offlineStartCenter);
	    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		autoSyncInterval.setPromptText("20");
		setCurrentPageInfo(); 
		setCommon();
		disabledUntilTrue(autoSyncEnabled,autoSyncInterval);
		offlineStartCenter.getItems().addAll("true","false");
		
		advOfflineProps.setOnAction(e -> {
			getUserInput();
			pageChange("AdvancedOfflineProperties.fxml");
		});
	}
}
	
	

