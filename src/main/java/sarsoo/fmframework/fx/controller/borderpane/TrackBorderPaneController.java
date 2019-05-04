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
import sarsoo.fmframework.fx.controller.info.TrackPaneController;
import sarsoo.fmframework.music.Artist;
import sarsoo.fmframework.music.Track;
import sarsoo.fmframework.net.Key;
import sarsoo.fmframework.net.Network;
import sarsoo.fmframework.util.Reference;

public class TrackBorderPaneController extends FMObjBorderPaneController{
	
	TrackPaneController infoPaneController;
	Track track;
	
	@FXML
	public void initialize() {
		
		borderPane.setTop(null);

	}
	
	public void populate(Track track) {
		
		this.track = track;
		
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
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../../ui/TrackPane.fxml"));

		this.infoAnchorPane = (AnchorPane) loader.load();

		AnchorPane.setTopAnchor(infoAnchorPane, 0.0);
		AnchorPane.setLeftAnchor(infoAnchorPane, 0.0);
		AnchorPane.setRightAnchor(infoAnchorPane, 0.0);
		AnchorPane.setBottomAnchor(infoAnchorPane, 0.0);

		infoPaneController = (TrackPaneController) loader.getController();
		
		infoPaneController.refresh(track);
		
	}

	@Override
	protected void handleViewOnline(ActionEvent event) {
		Network.openURL(track.getUrl());
	}

	@Override
	protected void handleRefresh(ActionEvent event) {

		Service<Void> service = new Service<Void>() {
			@Override
			protected Task<Void> createTask() {
				return new Task<Void>() {
					@Override
					protected Void call() throws Exception {
						
						track = new FmUserNetwork(Key.getKey(), Reference.getUserName()).refresh(track);

						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								
								infoPaneController.refresh(track);	
							
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
