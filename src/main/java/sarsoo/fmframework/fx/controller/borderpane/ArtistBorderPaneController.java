package sarsoo.fmframework.fx.controller.borderpane;

import java.io.IOException;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import sarsoo.fmframework.fm.FmUserNetwork;
import sarsoo.fmframework.fx.chart.GenrePieChartTitledPane;
import sarsoo.fmframework.fx.controller.info.ArtistPaneController;
import sarsoo.fmframework.music.Artist;
import sarsoo.fmframework.net.Key;
import sarsoo.fmframework.net.Network;
import sarsoo.fmframework.util.Reference;

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
	protected void handleViewOnline(ActionEvent event) {
		Network.openURL(artist.getUrl());
	}

	@Override
	protected void handleRefresh(ActionEvent event) {

		Service<Void> service = new Service<Void>() {
			@Override
			protected Task<Void> createTask() {
				return new Task<Void>() {
					@Override
					protected Void call() throws Exception {
						
						artist = new FmUserNetwork(Key.getKey(), Reference.getUserName()).refresh(artist);

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
