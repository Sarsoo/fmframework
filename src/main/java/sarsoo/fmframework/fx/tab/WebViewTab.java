package sarsoo.fmframework.fx.tab;

import java.io.IOException;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class WebViewTab extends Tab {

	public WebViewTab(String path) throws IOException {

		setText("web view");

		StackPane pane = new StackPane();
		
		WebView web = new WebView();
		
		pane.getChildren().add(web);
		
		WebEngine webEngine = web.getEngine();
		webEngine.load(path);

		setContent(pane);

	}
}
