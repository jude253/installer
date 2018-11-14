package centralInformation;
	
import javafx.application.Application;
import javafx.stage.Stage;
import services.ExternalService;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			//set up appropriate files in EMMInstaller folder for installation process:
			ExternalService.setUpEMMinstallationFolder();
			
			
			//set up the Installer window
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/SplashPage.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());
			primaryStage.getIcons().add(ExternalService.getImage("EZMax_logo_small.png"));
			primaryStage.setTitle("EZMaxMobile Installer");
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
