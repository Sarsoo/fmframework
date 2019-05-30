package sarsoo.fmframework.fx.tab;

import java.io.IOException;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import sarsoo.fmframework.fx.controller.borderpane.AlbumBorderPaneController;
import sarsoo.fmframework.fx.controller.borderpane.ArtistBorderPaneController;
import sarsoo.fmframework.fx.controller.info.AlbumPaneController;
import sarsoo.fmframework.music.Album;


import javafx.fxml.FXMLLoader;

public class AlbumTab extends Tab {

	public AlbumTab(Album album) {

		setText(album.getName().toLowerCase());

		FXMLLoader loader = new FXMLLoader(getClass().getResource("../ui/FMObjBorderPane.fxml"));

		AlbumBorderPaneController controller = new AlbumBorderPaneController();

		loader.setController(controller);
		
		AnchorPane pane;
		
		try {
			pane = (AnchorPane) loader.load();
			
			AnchorPane.setTopAnchor(pane, 0.0);
			AnchorPane.setLeftAnchor(pane, 0.0);
			AnchorPane.setRightAnchor(pane, 0.0);
			AnchorPane.setBottomAnchor(pane, 0.0);

			setContent(pane);

			controller.populate(album);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
