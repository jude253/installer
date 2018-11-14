package controllers;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;

import centralInformation.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import services.ExternalService;
import services.PageService;

public class WSAdminLogin extends PageService{
	


	@FXML
	private TextField webSphereUser;
	@FXML
	private TextField webSpherePassword;
	@FXML
	private Button testLogin;
	@FXML
	private Label loginStatus;
	@FXML
	private ProgressIndicator pind;
	
	private boolean statusFromFile = false;
	
	
    private void setCurrentPageInfo() {
    	//these are the names of interactive TextFields on the page and what their fx:id is
    	inputFields.put("websphere.username",webSphereUser);
    	inputFields.put("webshpere.password",webSpherePassword);
    	
    	//this is the previous page and the next page in the sequence
    	prevNext.put("prev","FolderLocations.fxml");
    	prevNext.put("next","MaximoInformation.fxml");
    	
    }
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	next.setDisable(true);
    	setUpProgress();
    	statusFromFile = ExternalService.setwsLoginTest();
    	if(statusFromFile == true) {
    		next.setDisable(false);
    		loginStatus.setText("Successful");
    		loginStatus.setTextFill(Color.GREEN);
    	}
    	setCurrentPageInfo();
    	setCommon();
    	testLogin.setOnAction(e -> {
    		Variables.startTime = new Date();
    		getUserInput();
    		next.setDisable(true);
    		prev.setDisable(true);
    		testLogin.setDisable(true);
    		Task<?> task = ExternalService.doLoginTest();
            pind.progressProperty().unbind();
            pind.progressProperty().bind(task.progressProperty());
            new Thread(task).start();
    	});	
	}
    
    private void setUpProgress() {
    	pind.indeterminateProperty().addListener(new ChangeListener<Boolean>() {

            @Override
             public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                     if(t1){
                     loginStatus.setText("Calculating Time");
                     loginStatus.setTextFill(Color.BLUE);
                 }
                 else{
                 	loginStatus.setText("In Progress ~30 seconds");
                             
                 }
             }
         });
         pind.progressProperty().addListener(new ChangeListener<Number>() {

             
             @Override
             public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                 
                 if(t1.doubleValue()==1) {
                	 statusFromFile = ExternalService.setwsLoginTest();
                	 if(statusFromFile == true  && FileUtils.isFileNewer(Variables.wsLoginTest, Variables.startTime)){
                 		next.setDisable(false);
                 		loginStatus.setText("Successful");
                 		loginStatus.setTextFill(Color.GREEN);
                 	} else {
                 		loginStatus.setText("Login Failed. Check user, password, and bin location");
                 		loginStatus.setTextFill(Color.RED);
                 	}
                	 prev.setDisable(false);
                	 testLogin.setDisable(false);
                	 
                }
            }

         });
    }
    
    //Create a New Task
   
  
    
    
}