package sarsoo.fmframework.fx;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sarsoo.fmframework.fx.controller.ControllerMain;
import sarsoo.fmframework.util.Reference;

public class FmFramework extends Application {
	
	private Stage stage;
	private Scene rootScene;
	
	private static ControllerMain control;

	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ui/main.fxml"));
		
//		Parent root = FXMLLoader.load(getClass().getResource("ui/main.fxml"));
		Parent root = (Parent)loader.load();
		
		Scene scene = new Scene(root, 800, 400);
		
		rootScene = scene;
//		scene.getStylesheets().add("styles/style.css");

		control = (ControllerMain)loader.getController();
				
		stage.setTitle("fm framework");
		stage.setScene(scene);
		stage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public void changeScene() throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("ui/changed.fxml"));
		
	}
	
	public static ControllerMain getController() {
		return control;
	}

}
