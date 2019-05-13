package sarsoo.fmframework.fx;

import java.io.File;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sarsoo.fmframework.config.Config;
import sarsoo.fmframework.config.ConfigPersister;
import sarsoo.fmframework.fx.controller.RootController;
import sarsoo.fmframework.fx.service.SaveConfigService;
import sarsoo.fmframework.log.Logger;
import sarsoo.fmframework.log.entry.LogEntry;

public class FmFramework extends Application {

	private static Stage stage;
	private Scene rootScene;

	private static Config config;

	private static RootController control;

	@Override
	public void start(Stage stage) throws Exception {
		FmFramework.stage = stage;

		initConfig();
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ui/RootPane.fxml"));

//		Parent root = FXMLLoader.load(getClass().getResource("ui/main.fxml"));
		Parent root = (Parent) loader.load();

		Scene scene = new Scene(root, 1000, 800);

		rootScene = scene;
//		scene.getStylesheets().add("styles/style.css");

		control = (RootController) loader.getController();
//		(new Thread(new TagCaller())).start();
		stage.setMinHeight(800);
		stage.setMinWidth(960);
		stage.setTitle("fmframework");
		stage.setScene(scene);
		stage.show();

	}
	
	private void initConfig() {
		ConfigPersister persist = new ConfigPersister();

		config = persist.readConfig(".fm/");

		if (config != null) {

			if (!(new File(".fm/").isFile())) {
				new File(".fm/").mkdir();
			}

			SaveConfigService saveConfig = new SaveConfigService(".fm/", config);
			saveConfig.start();
		}else {
			Logger.getLog().log(new LogEntry("load config").addArg("null config returned"));
		}
	}

	public static Config getSessionConfig() {
		return config;
	}

	public static void main(String[] args) {
		Runtime.getRuntime().addShutdownHook(
				new Thread(() -> (new ConfigPersister()).saveConfig(".fm/", config)));
		launch(args);
	}

	public static RootController getController() {
		return control;
	}

	public static Stage getStage() {
		return stage;
	}
}
