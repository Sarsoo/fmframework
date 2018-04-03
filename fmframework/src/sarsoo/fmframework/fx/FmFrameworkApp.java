package sarsoo.fmframework.fx;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

public class FmFrameworkApp extends Application{

	@Override
	public void start(Stage stage) {
		stage.setTitle("Hello World");
		Text text = new Text("Hello World");
		
		StackPane root = new StackPane();
		root.getChildren().add(text);
		
		Scene sc = new Scene(root, 300, 350);
		
		stage.setScene(sc);
		stage.show();
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}

}
