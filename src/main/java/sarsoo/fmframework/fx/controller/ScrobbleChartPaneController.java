package sarsoo.fmframework.fx.controller;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;

import javax.swing.JOptionPane;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import sarsoo.fmframework.fm.FmNetwork;
import sarsoo.fmframework.fm.FmUserNetwork;
import sarsoo.fmframework.fx.ArtistTab;
import sarsoo.fmframework.fx.FmFramework;
import sarsoo.fmframework.fx.TrackTab;
import sarsoo.fmframework.music.Album;
import sarsoo.fmframework.music.Wiki;
import sarsoo.fmframework.net.Key;
import sarsoo.fmframework.net.Network;
import sarsoo.fmframework.util.Maths;
import sarsoo.fmframework.util.Reference;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

public class ScrobbleChartPaneController {

	@FXML
	private ToolBar toolBar;

	@FXML
	private Button buttonRefresh;

	@FXML
	private ChoiceBox dropDownTimeRange;

	@FXML
	private LineChart lineChartScrobbles;

	@FXML
	private BorderPane chartBorderPane;

	@FXML
	public void initialize() {
		// dropDownTimeRange.setItems(FXCollections.observableArrayList("week", "month",
		// "3 month", "6 month", "year"));
		dropDownTimeRange.getItems().addAll("week", "30 day", "90 day", "180 day", "year");
	}

	@FXML
	protected void handleRefresh(ActionEvent event) {
		refresh();
	}

	public void populate(Album album) {

	}

	public void refresh() {
		Service<Void> service = new Service<Void>() {
			@Override
			protected Task<Void> createTask() {
				return new Task<Void>() {
					@Override
					protected Void call() throws Exception {

						FmUserNetwork net = new FmUserNetwork(Key.getKey(), "sarsoo");
						String value = (String) dropDownTimeRange.getValue();

						int dayLength = 0;

						switch (value) {
						case "week":
							dayLength = 7;
							break;
						case "30 day":
							dayLength = 30;
							break;
						case "90 day":
							dayLength = 90;
							break;
						case "180 day":
							dayLength = 180;
							break;
						case "year":
							dayLength = 365;
							break;
						}

						XYChart.Series series = new XYChart.Series();

						int counter;
						for (counter = 0; counter < dayLength; counter++) {
							int scrobble = net.getScrobbleCountByDeltaDay(dayLength - counter - 1);
//							System.out.println(scrobble);
							series.getData().add(new XYChart.Data(String.valueOf(dayLength - counter), scrobble));
						}

						final CountDownLatch latch = new CountDownLatch(1);
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								try {
									lineChartScrobbles.getData().clear();
									lineChartScrobbles.getData().add(series);

								} finally {
									latch.countDown();
								}
							}
						});
						latch.await();
						// Keep with the background work
						return null;
					}
				};
			}
		};
		service.start();

	}

}
