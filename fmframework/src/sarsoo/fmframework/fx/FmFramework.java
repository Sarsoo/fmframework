package sarsoo.fmframework.fx;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sarsoo.fmframework.util.Reference;

public class FmFramework extends Application {
	
	private Stage stage;
	private Scene rootScene;

	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		
		Parent root = FXMLLoader.load(getClass().getResource("ui/main.fxml"));
		
		Scene scene = new Scene(root, 800, 400);
		
		rootScene = scene;
//		scene.getStylesheets().add("styles/style.css");

		stage.setTitle("FM Framework");
		stage.setScene(scene);
		stage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public void changeScene() throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("ui/changed.fxml"));
		
		
		
	}

}
