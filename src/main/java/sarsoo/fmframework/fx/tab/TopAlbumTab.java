package sarsoo.fmframework.fx.tab;

import java.io.IOException;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import sarsoo.fmframework.fx.controller.TopAlbumController;
import javafx.fxml.FXMLLoader;

public class TopAlbumTab extends Tab {

	public TopAlbumTab() {

		setText("top albums");

		FXMLLoader loader = new FXMLLoader(getClass().getResource("../ui/TopAlbumPane.fxml"));

		AnchorPane pane;
		try {
			pane = (AnchorPane) loader.load();
			AnchorPane.setTopAnchor(pane, 0.0);
			AnchorPane.setLeftAnchor(pane, 0.0);
			AnchorPane.setRightAnchor(pane, 0.0);
			AnchorPane.setBottomAnchor(pane, 0.0);

			setContent(pane);

			TopAlbumController control = (TopAlbumController) loader.getController();

			//control.populate();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
