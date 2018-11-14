package services;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Map.Entry;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class PageService implements Initializable {
	
	protected Map<String,String> prevNext = new HashMap<String,String>();
	protected Map<String,String> input = new HashMap<String,String>();
	protected Map<String,TextField> inputFields = new HashMap<String,TextField>();
	protected Map<String,CheckBox> inputCheckBoxes = new HashMap<String,CheckBox>();
	protected HashMap<String,ChoiceBox<String>> inputChoiceBoxes = new HashMap<String,ChoiceBox<String>>();
	
	@FXML
 	protected AnchorPane rootPane;
	@FXML
	protected ButtonBar buttonBar;
	@FXML
	protected Button apply;
	@FXML
	protected Button next;
    @FXML
    protected Button prev;
    @FXML
	protected CheckBox checkBox;
    @FXML
   	protected ChoiceBox<String> choiceBox;
    @FXML
   	protected ImageView logoSmall;
    @FXML
   	protected Label title;
    @FXML
   	protected AnchorPane banner;
    @Override
	public void initialize(URL location, ResourceBundle resources) {
	}
    
	
	
	

	
	protected void setCommon() {
//		this.prevNext = prevNext;
//		this.inputFields = inputFields;
//		this.inputCheckBoxes = inputCheckBoxes;
//		this.inputChoiceBoxes = inputChoiceBoxes;
		try {next.setOnAction(e -> {
			getUserInput();
			pageChange(prevNext.get("next"));
		});
		} catch(Exception e) {}
		try {prev.setOnAction(e -> {
			getUserInput();
			pageChange(prevNext.get("prev"));
		});
		} catch(Exception e) {}
    	previouslyFilledFields();
	}
	
//	public void getUserInput() {
//    	try {
//    		for(Entry<String, TextField> entry : inputFields.entrySet()) {
//    			input.put(entry.getKey(), entry.getValue().getText());
//    	    }
//    		ExternalService.writeFile(input, "user_input.csv");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//    }
    
	
 
    
  
    
    public void getUserInput() {
    	try {
    		for(Entry<String, ChoiceBox<String>> entry : inputChoiceBoxes.entrySet()) {
        			input.put(entry.getKey(), entry.getValue().getValue());
    		}
    		for(Entry<String, CheckBox> entry : inputCheckBoxes.entrySet()) {
    			if(entry.getValue().isSelected()) {
        			input.put(entry.getKey(), "true");
        		} else {
        			input.put(entry.getKey(), "false");
        		}
    		}
    		for(Entry<String, TextField> entry : inputFields.entrySet()) {
    			input.put(entry.getKey(), entry.getValue().getText());
    	    }
    		ExternalService.writeFile(input, "user_input.csv");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
   
    

    
    public void previouslyFilledFields() {
    	input = ExternalService.readFileToMap("user_input.csv");
    	for(Map.Entry<String, String> entry : input.entrySet()) {
    		if(inputFields.containsKey(entry.getKey())) {
    			inputFields.get(entry.getKey()).setText(entry.getValue());
    		}
    		if(inputChoiceBoxes.containsKey(entry.getKey())) {
    			inputChoiceBoxes.get(entry.getKey()).setValue(entry.getValue());
    		}
    		if(inputCheckBoxes.containsKey(entry.getKey())) {
    			if(entry.getValue().equals("true")) {
    				inputCheckBoxes.get(entry.getKey()).setSelected(true);
        		} else {
        			inputCheckBoxes.get(entry.getKey()).setSelected(false);
        		}
    		}
    		
	    }
    	try {
    		if(input.get("ezmaxmobile.offline.enabled").equals("true")) {
    			checkBox.setSelected(true);
    		} else {
    			checkBox.setSelected(false);
    		}
			
		} catch (Exception e) {
			
		}
    	try {
    		logoSmall.setImage(ExternalService.getImage("EZMax_logo_small.png"));
    	} catch (Exception e) {
			
		}
    	
    }
    
 
    
    protected void pageChange(String fxmlFileName){
		AnchorPane pane;
		try {
			pane = (AnchorPane)FXMLLoader.load(getClass().getResource("/" + fxmlFileName));
			rootPane.getChildren().setAll(pane);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public static void disabledUntilTrue(ChoiceBox<String> TrueFalse,TextField textField) {
    	
    	if(TrueFalse.getItems().isEmpty()) {
        	TrueFalse.getItems().addAll("true","false");
    	}

		if(TrueFalse.getValue() != null) {
			if(!TrueFalse.getValue().equals("true")) {
				textField.setDisable(true);
			} else {
				textField.setDisable(false);
			}
		} else {
			textField.setDisable(true);
			}
		
		TrueFalse.setOnAction(e -> {
			if(TrueFalse.getValue().equals("true")) {
				textField.setDisable(false);
			} else {
				textField.setDisable(true);
			}
		});
	}
    
public static void disabledUntilTrue(ChoiceBox<String> TrueFalse,List<TextField> textFields) {
    	
    	if(TrueFalse.getItems().isEmpty()) {
        	TrueFalse.getItems().addAll("true","false");
    	}

		if(TrueFalse.getValue() != null) {
			if(!TrueFalse.getValue().equals("true")) {
				for(TextField textField : textFields)
					textField.setDisable(true);
			} else {
				for(TextField textField : textFields)
					textField.setDisable(false);
			}
		} else {
			for(TextField textField : textFields)
				textField.setDisable(true);
			}
		
		TrueFalse.setOnAction(e -> {
			if(TrueFalse.getValue().equals("true")) {
				for(TextField textField : textFields)
					textField.setDisable(false);
			} else {
				for(TextField textField : textFields)
					textField.setDisable(true);
			}
		});
	}

}