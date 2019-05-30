package sarsoo.fmframework.fx.tab;

import java.io.IOException;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import sarsoo.fmframework.fx.controller.borderpane.ArtistBorderPaneController;
import sarsoo.fmframework.fx.controller.borderpane.TrackBorderPaneController;
import sarsoo.fmframework.fx.controller.info.TrackPaneController;
import sarsoo.fmframework.music.Track;
import javafx.fxml.FXMLLoader;

public class TrackTab extends Tab {

	public TrackTab(Track track) {

		setText(track.getName().toLowerCase());

		FXMLLoader loader = new FXMLLoader(getClass().getResource("../ui/FMObjBorderPane.fxml"));

		TrackBorderPaneController controller = new TrackBorderPaneController();
		
		loader.setController(controller);
		
		AnchorPane pane;
		try {
			pane = (AnchorPane) loader.load();
			
			AnchorPane.setTopAnchor(pane, 0.0);
			AnchorPane.setLeftAnchor(pane, 0.0);
			AnchorPane.setRightAnchor(pane, 0.0);
			AnchorPane.setBottomAnchor(pane, 0.0);

			setContent(pane);

			controller.populate(track);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
