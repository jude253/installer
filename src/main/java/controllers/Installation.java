package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import services.ExternalService;
import services.PageService;
public class Installation extends PageService {
	
	
	@FXML
	private Button finish;
	@FXML
	private Button exit;
	@FXML
	private ProgressBar pb;
	@FXML
	private Label txtState;
	
	
	private void setCurrentPageInfo() {
		
	    	
	    //this is the previous page and the next page in the sequence
	    prevNext.put("prev","WSExistingInformation.fxml");
	    prevNext.put("next","Installation.fxml");
	    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		txtState.setText("Click Finish to Start Installation");
		setUpProgressBar();
		setCurrentPageInfo(); 
		setCommon();
		exit.setVisible(false);
		finish.setOnAction(e -> {
			finish.setDisable(true);
			prev.setDisable(true);
			Task<?> task = ExternalService.installEMM();
			pb.progressProperty().unbind();
	        pb.progressProperty().bind(task.progressProperty());
	        new Thread(task).start();
		});
		exit.setOnAction(e -> {
			Stage stage = (Stage) exit.getScene().getWindow(); 
		    stage.close();
		});
	}
	

	
	public void setUpProgressBar() {
		pb.indeterminateProperty().addListener(new ChangeListener<Boolean>() {
	           @Override
	            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
	                if(t1){
	                    txtState.setText("Calculating Time");
	                    txtState.setTextFill(Color.BLUE);
	                }
	                else{
	                    txtState.setText("In Progress ~ 5 minutes");  
	                    txtState.setTextFill(Color.BLUE);
	                }
	            }
	        });
		
		pb.progressProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                if(t1.doubleValue()==1){
                    txtState.setText("Installation Complete");
                    txtState.setTextFill(Color.GREEN);
                    exit.setVisible(true);
                }
                if(t1.doubleValue()==.1){
                    txtState.setText("Installation Failed. Check Log.");
                    txtState.setTextFill(Color.RED);
                    exit.setVisible(true);
                }
                if(t1.doubleValue()==.2){
                    txtState.setText("Installation Failed. Check Log For Last Step. Websphere Issue.");
                    txtState.setTextFill(Color.RED);
                    exit.setVisible(true);
                }
            }
        });

	}
	
    }
