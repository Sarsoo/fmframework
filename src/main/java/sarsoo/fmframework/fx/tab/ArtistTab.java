package sarsoo.fmframework.fx.tab;

import java.io.IOException;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import sarsoo.fmframework.fx.controller.borderpane.ArtistBorderPaneController;
import sarsoo.fmframework.music.Artist;
import javafx.fxml.FXMLLoader;

public class ArtistTab extends Tab{
	
	public ArtistTab(Artist artist) {
		
		setText(artist.getName().toLowerCase());
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../ui/FMObjBorderPane.fxml"));
		
		ArtistBorderPaneController controller = new ArtistBorderPaneController();
		
		loader.setController(controller);
		
		AnchorPane pane;
		
		try {
			pane = (AnchorPane) loader.load();
			
			AnchorPane.setTopAnchor(pane, 0.0);
			AnchorPane.setLeftAnchor(pane, 0.0);
			AnchorPane.setRightAnchor(pane, 0.0);
			AnchorPane.setBottomAnchor(pane, 0.0);
			
			setContent(pane);
			
			controller.populate(artist);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
