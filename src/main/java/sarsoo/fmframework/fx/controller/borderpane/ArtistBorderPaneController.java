package sarsoo.fmframework.fx.controller.borderpane;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import sarsoo.fmframework.config.Config;
import sarsoo.fmframework.fm.FmUserNetwork;
import sarsoo.fmframework.fx.FmFramework;
import sarsoo.fmframework.fx.controller.info.ArtistPaneController;
import sarsoo.fmframework.music.Artist;

public class ArtistBorderPaneController extends FMObjBorderPaneController{
	
	ArtistPaneController infoPaneController;
	Artist artist;
	
	@FXML
	public void initialize() {
		
		borderPane.setTop(null);

	}
	
	public void populate(Artist artist) {
		
		this.artist = artist;
		
		try {
			loadInfoPane();
			loadScrobblePane();
		} catch (IOException e) {
			e.printStackTrace();
		}
		setInfoView();
		
//		if(artist.getUrl() == null) {
			buttonViewOnline.setDisable(true);
//		}
	}

	@Override
	public void loadInfoPane() throws IOException {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../../ui/ArtistPane.fxml"));

		this.infoAnchorPane = (AnchorPane) loader.load();

		AnchorPane.setTopAnchor(infoAnchorPane, 0.0);
		AnchorPane.setLeftAnchor(infoAnchorPane, 0.0);
		AnchorPane.setRightAnchor(infoAnchorPane, 0.0);
		AnchorPane.setBottomAnchor(infoAnchorPane, 0.0);

		infoPaneController = (ArtistPaneController) loader.getController();
		
		infoPaneController.refresh(artist);
		
	}

	@Override
	@FXML
	protected void handleViewOnline(ActionEvent event) {
		System.out.println(artist.getUrl());
		if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
		    try {
				Desktop.getDesktop().browse(new URI(artist.getUrl()));
			} catch (IOException | URISyntaxException e) {
				e.printStackTrace();
			}
		}
//		Network.openURL(artist.getUrl());
	}

	@Override
	@FXML
	protected void handleRefresh(ActionEvent event) {

		Service<Void> service = new Service<Void>() {
			@Override
			protected Task<Void> createTask() {
				return new Task<Void>() {
					@Override
					protected Void call() throws Exception {
						
						artist = FmFramework.getArtistPool().getNew(artist);

						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								
								infoPaneController.refresh(artist);	
							
							}
						});
						return null;
					}
				};
			}
		};
		service.start();
	}
	
}
