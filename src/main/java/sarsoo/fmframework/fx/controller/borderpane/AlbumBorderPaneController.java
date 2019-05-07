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
import sarsoo.fmframework.fm.FmUserNetwork;
import sarsoo.fmframework.fx.FmFramework;
import sarsoo.fmframework.fx.controller.info.AlbumPaneController;
import sarsoo.fmframework.fx.tab.ArtistTab;
import sarsoo.fmframework.log.Logger;
import sarsoo.fmframework.log.entry.ErrorEntry;
import sarsoo.fmframework.music.Album;
import sarsoo.fmframework.net.Key;
import sarsoo.fmframework.net.Network;
import sarsoo.fmframework.util.Reference;

public class AlbumBorderPaneController extends FMObjBorderPaneController {

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
		
		Button openRym = new Button("view rym");
		Button viewArtist = new Button("view artist");
		
		openRym.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Network.openURL(album.getRymURL());
			}
		});

		if (album.getArtist() != null) {

			viewArtist.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					try {
						FmFramework.getController().addTab(new ArtistTab(album.getArtist()));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			});
		} else {
			viewArtist.setDisable(true);
		}

		toolBar.getItems().add(openRym);
		toolBar.getItems().add(viewArtist);
		
//		if(album.getUrl() == null) {
			buttonViewOnline.setDisable(true);
//		}
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
	@FXML
	protected void handleViewOnline(ActionEvent event) {
		System.out.println(album.getUrl());
		if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
		    try {
				Desktop.getDesktop().browse(new URI(album.getUrl()));
			} catch (Exception e) {
				Logger.getLog().logError(new ErrorEntry("Can't Open"));
			}
		}
//		Network.openURL(album.getUrl());
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
