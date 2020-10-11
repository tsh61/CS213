package app;

/** 
 * @author Travis Harrell (tsh61)
 * @author Elizaveta Belaya (edb81)
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SongLib extends Application{
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();   
		loader.setLocation(getClass().getResource("/app/songL.fxml"));
		AnchorPane root = (AnchorPane)loader.load();

		SListController listController = loader.getController();
		listController.start(primaryStage);

		Scene scene = new Scene(root, 268, 460);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show(); 
	}
	public static void main(String[] args) {
		launch(args);
	}

}
