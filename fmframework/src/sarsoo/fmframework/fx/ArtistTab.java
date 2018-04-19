package sarsoo.fmframework.fx;

import java.io.IOException;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import sarsoo.fmframework.fx.controller.AlbumPaneController;
import sarsoo.fmframework.fx.controller.ArtistPaneController;
import sarsoo.fmframework.music.Album;
import sarsoo.fmframework.music.Artist;
import javafx.fxml.FXMLLoader;

public class ArtistTab extends Tab {

	public ArtistTab(Artist artist) throws IOException {

		setText(artist.getName());

		FXMLLoader loader = new FXMLLoader(getClass().getResource("ui/ArtistPane.fxml"));

		AnchorPane pane = (AnchorPane) loader.load();

		AnchorPane.setTopAnchor(pane, 0.0);
		AnchorPane.setLeftAnchor(pane, 0.0);
		AnchorPane.setRightAnchor(pane, 0.0);
		AnchorPane.setBottomAnchor(pane, 0.0);

		setContent(pane);

		ArtistPaneController control = (ArtistPaneController) loader.getController();

		control.populate(artist);
		
		

	}

}
