package sarsoo.fmframework.fx.tab;

import java.io.IOException;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import sarsoo.fmframework.fx.controller.AlbumPaneController;
import sarsoo.fmframework.music.Album;


import javafx.fxml.FXMLLoader;

public class AlbumTab extends Tab {

	public AlbumTab(Album album) throws IOException {

		setText(album.getName());

		FXMLLoader loader = new FXMLLoader(getClass().getResource("../ui/AlbumPane.fxml"));

		AnchorPane pane = (AnchorPane) loader.load();

		AnchorPane.setTopAnchor(pane, 0.0);
		AnchorPane.setLeftAnchor(pane, 0.0);
		AnchorPane.setRightAnchor(pane, 0.0);
		AnchorPane.setBottomAnchor(pane, 0.0);

		setContent(pane);

		AlbumPaneController control = (AlbumPaneController) loader.getController();

		control.populate(album);
		
		

	}

}
