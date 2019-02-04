package sarsoo.fmframework.fx;

import java.io.IOException;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import sarsoo.fmframework.fx.controller.AlbumPaneController;
import sarsoo.fmframework.fx.controller.ScrobbleChartPaneController;
import sarsoo.fmframework.music.Album;


import javafx.fxml.FXMLLoader;

public class ScrobbleChartTab extends Tab {

	public ScrobbleChartTab() throws IOException {

		setText("scrobbles");

		FXMLLoader loader = new FXMLLoader(getClass().getResource("ui/ScrobbleChartPane.fxml"));

		AnchorPane pane = (AnchorPane) loader.load();

		AnchorPane.setTopAnchor(pane, 0.0);
		AnchorPane.setLeftAnchor(pane, 0.0);
		AnchorPane.setRightAnchor(pane, 0.0);
		AnchorPane.setBottomAnchor(pane, 0.0);

		setContent(pane);

		ScrobbleChartPaneController control = (ScrobbleChartPaneController) loader.getController();

		//control.populate();
		
		

	}

}
