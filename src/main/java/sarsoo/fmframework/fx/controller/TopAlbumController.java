package sarsoo.fmframework.fx.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import sarsoo.fmframework.config.Config;
import sarsoo.fmframework.fm.FmUserNetwork;
import sarsoo.fmframework.fm.TimePeriod;
import sarsoo.fmframework.fx.FmFramework;
import sarsoo.fmframework.fx.TiledImage;
import sarsoo.fmframework.fx.service.GetFXImageService;
import sarsoo.fmframework.fx.service.GetTopAlbumsService;
import sarsoo.fmframework.music.Album;
import sarsoo.fmframework.music.FMObj;
import sarsoo.fmframework.util.FMObjList;

public class TopAlbumController {

	@FXML
	protected BorderPane borderPane;

	@FXML
	protected ToolBar toolBar;

	@FXML
	protected ChoiceBox<String> dropDownTimeRange;

	@FXML
	protected ChoiceBox<Integer> dropDownLimit;

	@FXML
	protected Button buttonLoad;

	@FXML
	protected Button buttonGenerate;

	@FXML
	protected GridPane gridPane;

	@FXML
	protected TilePane tilePane;

	FMObjList albums;
	ArrayList<TiledImage> images = new ArrayList<>();

	@FXML
	public void initialize() {
		dropDownTimeRange.getItems().addAll("week", "30 day", "90 day", "180 day", "year", "overall");

		for (int i = 1; i < 16; i++) {
			dropDownLimit.getItems().add(i);
		}

		for (int i = 20; i < 110; i += 10) {
			dropDownLimit.getItems().add(i);
		}

		dropDownTimeRange.getSelectionModel().select(0);
		dropDownLimit.getSelectionModel().select(0);
	}

	@FXML
	private void handleLoad(ActionEvent event) {
		Config config = FmFramework.getSessionConfig();
		FmUserNetwork net = new FmUserNetwork(config.getValue("api_key"), config.getValue("username"));

		String value = (String) dropDownTimeRange.getValue();

		TimePeriod timePeriod = null;

		switch (value) {
		case "week":
			timePeriod = TimePeriod.SEVENDAY;
			break;
		case "30 day":
			timePeriod = TimePeriod.ONEMONTH;
			break;
		case "90 day":
			timePeriod = TimePeriod.THREEMONTH;
			break;
		case "180 day":
			timePeriod = TimePeriod.SIXMONTH;
			break;
		case "year":
			timePeriod = TimePeriod.TWELVEMONTH;
			break;
		case "overall":
			timePeriod = TimePeriod.OVERALL;
			break;
		}

		GetTopAlbumsService service = new GetTopAlbumsService(timePeriod, dropDownLimit.getValue(), net);

		service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent t) {
				Platform.runLater(new Runnable() {

					@Override
					public void run() {

						gridPane.getChildren().clear();

						if (albums != null)
							albums.clear();

						albums = (FMObjList) t.getSource().getValue();

						if (albums.size() > 0) {
							buttonGenerate.setDisable(false);
						} else {
							buttonGenerate.setDisable(true);
						}

						int counter = 0;
						for (FMObj fmObj : albums) {
							Album album = (Album) fmObj;

							Label albumName = new Label(album.getName().toLowerCase());
							Label artistName = new Label(album.getArtist().getName().toLowerCase());
							Label userPlays = new Label(Integer.toString(album.getUserPlayCount()));

							gridPane.add(new Label(Integer.toString(counter + 1)), 0, counter);
							gridPane.add(albumName, 1, counter);
							gridPane.add(artistName, 2, counter);
							gridPane.add(userPlays, 3, counter);

							counter++;
						}

					}

				});
			}
		});

		service.start();

	}

	@FXML
	private void handleGenerate(ActionEvent event) {

		if (albums != null) {
			if (albums.size() > 0) {
				tilePane.getChildren().clear();
				images.clear();

				int counter = 0;
				for (FMObj obj : albums) {

					Album album = (Album) obj;

					if (album.getImageURL() != null) {

						GetFXImageService service = new GetFXImageService(album.getImageURL(), counter);

						service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

							@Override
							public void handle(WorkerStateEvent t) {

								TiledImage image = (TiledImage) t.getSource().getValue();

								images.add(image);

								refreshImages();
							}
						});

						service.start();
						counter++;
					}
				}
			}
		}

	}

	private void refreshImages() {
		ObservableList<Node> list = tilePane.getChildren();

		list.clear();

		Collections.sort(images, new Comparator<TiledImage>() {

			@Override
			public int compare(TiledImage arg0, TiledImage arg1) {
				return arg0.getIndex() - arg1.getIndex();
			}

		});

		for (TiledImage image : images) {
			list.add(new ImageView(image));
		}
	}
}