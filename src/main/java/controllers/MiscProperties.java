package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import services.PageService;
public class MiscProperties extends PageService {
	@FXML
	private TextField maximoDoclinksFolder;
	@FXML
	private TextField ezmaxmobileMaxsessionTimeout;
	@FXML
	private TextField ezmaxmobileDrilldownLimit;
	@FXML
	private ChoiceBox<String> ezmaxmobileSSLEnabled;
	@FXML
	private ChoiceBox<String> ezmaxmobileProxyEnabled;
	@FXML
	private TextField ezmaxmobileProxyServer;
	@FXML
	private ChoiceBox<String> ezmaxmobileESRIEnabled;
	@FXML
	private TextField ezmaxmobileESRIMapServer;
	
	
	private void setCurrentPageInfo() {
		//these are the names of interactive TextFields on the page and what their fx:id is
		inputFields.put("maximo.doclinks.folder",maximoDoclinksFolder);
		inputFields.put("ezmaxmobile.maxsession.timeout",ezmaxmobileMaxsessionTimeout);
		inputFields.put("ezmaxmobile.drilldownlimit",ezmaxmobileDrilldownLimit);
		inputFields.put("ezmaxmobile.proxy.server",ezmaxmobileProxyServer);
		inputFields.put("ezmaxmobile.esri.mapserver",ezmaxmobileESRIMapServer);
		
		
	    //this is the previous page and the next page in the sequence
	    prevNext.put("prev","PushProperties.fxml");
	    prevNext.put("next","License.fxml");
	    
	    inputChoiceBoxes.put("ezmaxmobile.ssl.enabled",ezmaxmobileSSLEnabled);
	    inputChoiceBoxes.put("ezmaxmobile.proxy.enabled",ezmaxmobileProxyEnabled);
	    inputChoiceBoxes.put("ezmaxmobile.esri.enabled",ezmaxmobileESRIEnabled);
	    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setCurrentPageInfo(); 
		setCommon();
		
		ezmaxmobileSSLEnabled.getItems().addAll("true","false");
		disabledUntilTrue(ezmaxmobileProxyEnabled,ezmaxmobileProxyServer);
		disabledUntilTrue(ezmaxmobileESRIEnabled,ezmaxmobileESRIMapServer);
		
	}
	
	
	
}
	
	

