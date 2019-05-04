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
import sarsoo.fmframework.fx.controller.info.AlbumPaneController;
import sarsoo.fmframework.fx.controller.info.ArtistPaneController;
import sarsoo.fmframework.music.Album;
import sarsoo.fmframework.music.Artist;
import sarsoo.fmframework.net.Key;
import sarsoo.fmframework.net.Network;
import sarsoo.fmframework.util.Reference;

public class AlbumBorderPaneController extends FMObjBorderPaneController{
	
	AlbumPaneController infoPaneController;
	Album album;
	
	@FXML
	public void initialize() {
		
		borderPane.setTop(null);

	}
	
	public void populate(Album album) {
		
		this.album = album;
		
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
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../../ui/AlbumPane.fxml"));

		this.infoAnchorPane = (AnchorPane) loader.load();

		AnchorPane.setTopAnchor(infoAnchorPane, 0.0);
		AnchorPane.setLeftAnchor(infoAnchorPane, 0.0);
		AnchorPane.setRightAnchor(infoAnchorPane, 0.0);
		AnchorPane.setBottomAnchor(infoAnchorPane, 0.0);

		infoPaneController = (AlbumPaneController) loader.getController();
		
		infoPaneController.refresh(album);
		
	}

	@Override
	protected void handleViewOnline(ActionEvent event) {
		Network.openURL(album.getUrl());
	}

	@Override
	protected void handleRefresh(ActionEvent event) {

		Service<Void> service = new Service<Void>() {
			@Override
			protected Task<Void> createTask() {
				return new Task<Void>() {
					@Override
					protected Void call() throws Exception {
						
						album = new FmUserNetwork(Key.getKey(), Reference.getUserName()).refresh(album);

						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								
								infoPaneController.refresh(album);	
							
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
