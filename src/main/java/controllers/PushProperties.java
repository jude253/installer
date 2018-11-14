package controllers;

import java.net.URL;
import java.util.Arrays;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import services.ExternalService;
import services.PageService;
public class PushProperties extends PageService {
	@FXML
	private ChoiceBox<String> ezmaxmobilePushEnabled;
	@FXML
	private ChoiceBox<String> ezmaxmobilePushHttpsenabled;
	@FXML
	private TextField ezmaxmobilePushClient;
	@FXML
	private TextField ezmaxmobilePushToken;
	@FXML
	private ChoiceBox<String> ezmaxmobilePushProxyserverEnabled;
	@FXML
	private TextField ezmaxmobilePushProxyserver;
	@FXML
	private TextField ezmaxmobilePushProxyserverPort;
	@FXML
	private Button prevOffline;
	
	private void setCurrentPageInfo() {
		//these are the names of interactive TextFields on the page and what their fx:id is
		inputFields.put("ezmaxmobile.push.client",ezmaxmobilePushClient);
		inputFields.put("ezmaxmobile.push.token",ezmaxmobilePushToken);
		inputFields.put("ezmaxmobile.push.proxyserver",ezmaxmobilePushProxyserver);
		inputFields.put("ezmaxmobile.push.proxyserver.port",ezmaxmobilePushProxyserverPort);

	    	
	    //this is the previous page and the next page in the sequence
	    prevNext.put("prev","OfflineMode.fxml");
	    prevNext.put("next","MiscProperties.fxml");
	    
	    inputChoiceBoxes.put("ezmaxmobile.push.enabled",ezmaxmobilePushEnabled);
	    inputChoiceBoxes.put("ezmaxmobile.push.httpsenabled",ezmaxmobilePushHttpsenabled);
	    inputChoiceBoxes.put("ezmaxmobile.push.proxyserver.enabled",ezmaxmobilePushProxyserverEnabled);
	    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Map<String,String> userInput = ExternalService.readFileToMap("user_input.csv");	
		
		setCurrentPageInfo(); 
		setCommon();
		
		disabledUntilTrue(ezmaxmobilePushEnabled,Arrays.asList(ezmaxmobilePushClient,ezmaxmobilePushToken));
		ezmaxmobilePushHttpsenabled.getItems().addAll("true","false");
		disabledUntilTrue(ezmaxmobilePushProxyserverEnabled,Arrays.asList(ezmaxmobilePushProxyserverPort,ezmaxmobilePushProxyserver));
		
		
		prevOffline.setOnAction(e -> {
			getUserInput();
			if(userInput.get("ezmaxmobile.offline.enabled").equals("true")) {
				pageChange("OfflineProperties.fxml");
			} else {
				pageChange(prevNext.get("prev"));
			}
		});
	}
}
	
	

