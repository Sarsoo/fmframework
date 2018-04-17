package sarsoo.fmframework.fx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FmFramework extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("ui/main.fxml"));

		Scene scene = new Scene(root, 800, 400);
//		scene.getStylesheets().add("styles/style.css");

		stage.setTitle("FM Framework");
		stage.setScene(scene);
		stage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

}
