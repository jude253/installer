package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import services.PageService;
public class MaximoInformation extends PageService {
	
	@FXML
	private TextField driver;
	@FXML
	private TextField url;
	@FXML
	private TextField schema;
	@FXML
	private ChoiceBox<String> type;
	@FXML
	private CheckBox LDAP;
	@FXML
	private Button nextLDAP;
	
	private void setCurrentPageInfo() {
		//these are the names of interactive TextFields on the page and what their fx:id is
		inputFields.put("maximo.db.driver",driver);
	    inputFields.put("maximo.db.url",url);
	    inputFields.put("maximo.db.schema",schema);
	    	
	    //this is the previous page and the next page in the sequence
	    prevNext.put("prev","WSAdminLogin.fxml");
	    prevNext.put("next","MaximoInformationCont.fxml");
	    
	    inputChoiceBoxes.put("maximo.db.type",type);
	    
	    inputCheckBoxes.put("LDAPConnection", LDAP);
	    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		type.getItems().addAll("SQLSERVER","ORACLE","DB2");
		setCurrentPageInfo(); 
		setCommon();
		nextLDAP.setOnAction(e -> {
			getUserInput();
			if(LDAP.isSelected()) {
				pageChange("MaximoInformationContLDAP.fxml");
			} else {
				pageChange(prevNext.get("next"));
			}
		});
	}
}
	
	

