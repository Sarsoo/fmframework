package sarsoo.fmframework.fx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sarsoo.fmframework.config.Config;
import sarsoo.fmframework.config.ConfigVariable;
import sarsoo.fmframework.fx.controller.RootController;
import sarsoo.fmframework.net.Key;

public class FmFramework extends Application {
	
	private static Stage stage;
	private Scene rootScene;
	
	private static Config config;
	
	private static RootController control;

	@Override
	public void start(Stage stage) throws Exception {
		FmFramework.stage = stage;
		
		config = new Config();
		
		config.addVariable(new ConfigVariable("username", "sarsoo"));
		config.addVariable(new ConfigVariable("api_key", Key.getKey()));
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ui/RootPane.fxml"));
		
//		Parent root = FXMLLoader.load(getClass().getResource("ui/main.fxml"));
		Parent root = (Parent)loader.load();
		
		Scene scene = new Scene(root, 1000, 800);
		
		rootScene = scene;
//		scene.getStylesheets().add("styles/style.css");
		
		
		control = (RootController)loader.getController();
//		(new Thread(new TagCaller())).start();
		stage.setMinHeight(800);
		stage.setMinWidth(960);
		stage.setTitle("fmframework");
		stage.setScene(scene);
		stage.show();

	}
	
	public static Config getSessionConfig() {
		return config;
	}

	public static void main(String[] args) {
		launch(args);
	}
	
//	public void changeScene() throws IOException {
//		Parent root = FXMLLoader.load(getClass().getResource("ui/changed.fxml"));
//		
//	}
	
	public static RootController getController() {
		return control;
	}

	public static Stage getStage() {
		return stage;
	}
}
