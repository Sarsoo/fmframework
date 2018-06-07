package sarsoo.fmframework.fx;

import java.io.IOException;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import sarsoo.fmframework.fx.controller.TrackPaneController;
import sarsoo.fmframework.music.Track;
import javafx.fxml.FXMLLoader;

public class TrackTab extends Tab {

	public TrackTab(Track track) throws IOException {

		setText(track.getName());

		FXMLLoader loader = new FXMLLoader(getClass().getResource("ui/TrackPane.fxml"));

		AnchorPane pane = (AnchorPane) loader.load();

		AnchorPane.setTopAnchor(pane, 0.0);
		AnchorPane.setLeftAnchor(pane, 0.0);
		AnchorPane.setRightAnchor(pane, 0.0);
		AnchorPane.setBottomAnchor(pane, 0.0);

		setContent(pane);

		TrackPaneController control = (TrackPaneController) loader.getController();

		control.populate(track);
		
		

	}

}
