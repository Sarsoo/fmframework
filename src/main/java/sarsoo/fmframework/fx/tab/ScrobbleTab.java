package sarsoo.fmframework.fx.tab;

import java.io.IOException;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import sarsoo.fmframework.fx.controller.ScrobblePaneController;
import javafx.fxml.FXMLLoader;

public class ScrobbleTab extends Tab {

	public ScrobbleTab() {

		setText("scrobble");

		FXMLLoader loader = new FXMLLoader(getClass().getResource("../ui/ScrobblePane.fxml"));

		AnchorPane pane;
		try {
			pane = (AnchorPane) loader.load();
			
			AnchorPane.setTopAnchor(pane, 0.0);
			AnchorPane.setLeftAnchor(pane, 0.0);
			AnchorPane.setRightAnchor(pane, 0.0);
			AnchorPane.setBottomAnchor(pane, 0.0);

			setContent(pane);

			ScrobblePaneController control = (ScrobblePaneController) loader.getController();

			//control.populate();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
