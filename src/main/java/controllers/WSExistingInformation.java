package controllers;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import services.ExternalService;
import services.PageService;

public class WSExistingInformation extends PageService{
	


	@FXML
	private ChoiceBox<String> nodeName;
	@FXML
	private ChoiceBox<String> cellName;
	@FXML
	private ChoiceBox<String> webServerName;
	List<String> servers;
	
 

    private void setCurrentPageInfo() {
  
    	
    	
    	//this is the previous page and the next page in the sequence
    	prevNext.put("prev","WSSetupPage.fxml");
    	prevNext.put("next","Installation.fxml");
    	
    	inputChoiceBoxes.put("nodeName",nodeName);
    	inputChoiceBoxes.put("cellName",cellName);
    	inputChoiceBoxes.put("webServerName",webServerName);
    }
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	
    	Map<String,List<String>> mapOfLists = ExternalService.wsChoiceBoxInput("AllWSInfo.csv");
    	nodeName.getItems().addAll(mapOfLists.get("Node"));
    	cellName.getItems().addAll(mapOfLists.get("Cell"));
    	webServerName.getItems().addAll(mapOfLists.get("Server"));
    	webServerName.getItems().add("none");
//    	for (Map.Entry<String, List<String>> entry : mapOfLists.entrySet())
//		{
//			String key = entry.getKey();
//			List<String> value = entry.getValue();
//			for(String str : value) {
//				System.out.println(key + "  " + str);
//			}
//		    
//		    
//		}
    	setCurrentPageInfo();
    	setCommon();
    	
    	

		
	}
    

}