package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import services.PageService;
public class AdvancedOfflineProperties extends PageService {

	@FXML
	private Button back;
	@FXML
	private Button applyBack;
	
	@FXML
	private TextField ezmaxmobileOfflineSqliteDirectory;
	@FXML
	private TextField ezmaxmobileOfflinePagesize;
	@FXML
	private TextField ezmaxmobileDBPath;
	@FXML
	private TextField ezmaxmobileDBCleanupdays;
	@FXML
	private TextField ezmaxmobileDBName;
	@FXML
	private TextField ezmaxmobileDBUsername;
	@FXML
	private TextField ezmaxmobileDBPassword;

	
	
	private void setCurrentPageInfo() {
		//these are the names of interactive TextFields on the page and what their fx:id is
		inputFields.put("ezmaxmobile.offline.sqlite.directory",ezmaxmobileOfflineSqliteDirectory);
		inputFields.put("ezmaxmobile.offline.pagesize",ezmaxmobileOfflinePagesize);
		inputFields.put("ezmaxmobile.db.path",ezmaxmobileDBPath);
		inputFields.put("ezmaxmobile.db.cleanupdays",ezmaxmobileDBCleanupdays);
		inputFields.put("ezmaxmobile.db.name",ezmaxmobileDBName);
		inputFields.put("ezmaxmobile.db.username",ezmaxmobileDBUsername);
		inputFields.put("ezmaxmobile.db.password",ezmaxmobileDBPassword);
		
		
	    //this is the previous page and the next page in the sequence
	    prevNext.put("prev","PushProperties.fxml");
	    prevNext.put("next","License.fxml");
	    
	    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setCurrentPageInfo(); 
		setCommon();
		
		applyBack.setOnAction(e -> {
			getUserInput();
			pageChange("OfflineProperties.fxml");
		});
		back.setOnAction(e -> {
			pageChange("OfflineProperties.fxml");
		});
	}
	
	
	
}
	
	

