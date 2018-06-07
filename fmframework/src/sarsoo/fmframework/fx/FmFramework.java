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
	
	private static Stage stage;
	private Scene rootScene;
	
	private static ControllerMain control;

	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		Reference.setUserName("sarsoo");
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ui/main.fxml"));
		
//		Parent root = FXMLLoader.load(getClass().getResource("ui/main.fxml"));
		Parent root = (Parent)loader.load();
		
		Scene scene = new Scene(root, 1000, 800);
		
		rootScene = scene;
//		scene.getStylesheets().add("styles/style.css");
		
		
		control = (ControllerMain)loader.getController();
//		(new Thread(new TagCaller())).start();
		stage.setMinHeight(800);
		stage.setMinWidth(960);
		stage.setTitle("fmframework");
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

	public static Stage getStage() {
		return stage;
	}
}
