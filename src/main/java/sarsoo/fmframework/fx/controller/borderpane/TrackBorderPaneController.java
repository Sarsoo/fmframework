package sarsoo.fmframework.fx.controller.borderpane;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import sarsoo.fmframework.config.Config;
import sarsoo.fmframework.fm.FmUserNetwork;
import sarsoo.fmframework.fx.FmFramework;
import sarsoo.fmframework.fx.controller.info.TrackPaneController;
import sarsoo.fmframework.fx.tab.AlbumTab;
import sarsoo.fmframework.fx.tab.ArtistTab;
import sarsoo.fmframework.music.Track;

public class TrackBorderPaneController extends FMObjBorderPaneController {

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
		
		Button viewAlbum = new Button("view album");
		Button viewArtist = new Button("view artist");

		if (track.getAlbum() != null) {
			viewAlbum.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					try {
						FmFramework.getController().addTab(new AlbumTab(track.getAlbum()));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			});
		}else {
			viewAlbum.setDisable(true);
		}
		
		if (track.getArtist() != null) {
			viewArtist.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					try {
						FmFramework.getController().addTab(new ArtistTab(track.getArtist()));
					} catch (IOException e1) {
						e1.printStackTrace();
					}//
				}
			});
		}else {
			viewArtist.setDisable(true);
		}

		toolBar.getItems().add(viewAlbum);
		toolBar.getItems().add(viewArtist);
		
//		if(track.getUrl() == null) {
			buttonViewOnline.setDisable(true);
//		}
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
	@FXML
	protected void handleViewOnline(ActionEvent event) {
		System.out.println(track.getUrl());
		if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
		    try {
				Desktop.getDesktop().browse(new URI(track.getUrl()));
			} catch (IOException | URISyntaxException e) {
				e.printStackTrace();
			}
		}
//		Network.openURL(track.getUrl());
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
						
						Config config = FmFramework.getSessionConfig();

						FmUserNetwork net = new FmUserNetwork(config.getValue("api_key"), config.getValue("username"));
						
						track = net.refresh(track);
						track.setScrobbles(net.getTrackScrobbles(track));

						Platform.runLater(new Runnable() {
							@Override
							public void run() {

								infoPaneController.refresh(track);
								scrobblePaneController.populate(track);

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
