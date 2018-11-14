package controllers;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.PageService;

public class FolderLocations extends PageService{

	@FXML
	private TextField MaximoEarFileLocation;
	@FXML
	private TextField EMMPathAndFile;
	@FXML
	private TextField WebSphereBinPathAndFile;
	@FXML
	private Button maximoBrowse;
	@FXML
	private Button emmBrowse;
	@FXML
	private Button wsBrowse;
	private DirectoryChooser directoryChooser;
	private FileChooser fileChooser;
	private File currentInputFile;
	private File selectedDirectory;
    private void setCurrentPageInfo() {
    	//these are the names of interactive TextFields on the page and what their fx:id is
    	inputFields.put("MaximoEarFileLocation",MaximoEarFileLocation);
    	inputFields.put("EMMPathAndFile",EMMPathAndFile);
    	inputFields.put("WebSphereBinPathAndFile",WebSphereBinPathAndFile);

    	//this is the previous page and the next page in the sequence
    	prevNext.put("prev","SplashPage.fxml");
    	prevNext.put("next","WSAdminLogin.fxml");
    	
    }
//    C:\Program Files (x86)\IBM\WebSphere\AppServer\profiles\ctgAppSrv01\installedApps\ctgCell01
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	setCurrentPageInfo();
    	setCommon();
    	maximoBrowse.setOnAction(e -> {
    		currentInputFile = new File(MaximoEarFileLocation.getText());
    		Stage stage = (Stage) maximoBrowse.getScene().getWindow();
    		
    		directoryChooser = new DirectoryChooser();
    		directoryChooser.setTitle("Select Maximo .ear Folder");
    		if(currentInputFile.exists()) {
    			directoryChooser.setInitialDirectory(currentInputFile);
    		}
    		
    		selectedDirectory = directoryChooser.showDialog(stage);
            if (selectedDirectory != null) {
                MaximoEarFileLocation.setText(selectedDirectory.getAbsolutePath());
            }
    	});
    	emmBrowse.setOnAction(e -> {
    		currentInputFile = new File(EMMPathAndFile.getText());
    		Stage stage = (Stage) emmBrowse.getScene().getWindow();
    		
    		fileChooser = new FileChooser();
    		fileChooser.setTitle("Select EZMaxMobile .war File");
    		if(currentInputFile.exists()) {
    			fileChooser.setInitialDirectory(currentInputFile);
    		}
    		
    		selectedDirectory = fileChooser.showOpenDialog(stage);
            if (selectedDirectory != null) {
            	EMMPathAndFile.setText(selectedDirectory.getAbsolutePath());
            }
    	});
    	wsBrowse.setOnAction(e -> {
    		currentInputFile = new File(WebSphereBinPathAndFile.getText());
    		Stage stage = (Stage) wsBrowse.getScene().getWindow();
    		
    		directoryChooser = new DirectoryChooser();
    		directoryChooser.setTitle("Select Websphere Bin Folder");
    		if(currentInputFile.exists()) {
    			directoryChooser.setInitialDirectory(currentInputFile);
    		}
    		
    		selectedDirectory = directoryChooser.showDialog(stage);
            if (selectedDirectory != null) {
            	WebSphereBinPathAndFile.setText(selectedDirectory.getAbsolutePath());
            }
    	});
    	
	}
    
}