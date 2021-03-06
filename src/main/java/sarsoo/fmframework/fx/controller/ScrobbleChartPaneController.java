package sarsoo.fmframework.fx.controller;

import java.time.LocalDate;
import java.util.concurrent.CountDownLatch;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import sarsoo.fmframework.config.Config;
import sarsoo.fmframework.fm.FmUserNetwork;
import sarsoo.fmframework.fx.FmFramework;
import sarsoo.fmframework.music.Album;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

public class ScrobbleChartPaneController {

	@FXML
	private ToolBar toolBar;

	@FXML
	private Button buttonRefresh;

	@FXML
	private ChoiceBox<String> dropDownTimeRange;

	@FXML
	private BarChart barChartScrobbles;

	@FXML
	private BorderPane chartBorderPane;

	@FXML
	public void initialize() {
		// dropDownTimeRange.setItems(FXCollections.observableArrayList("week", "month",
		// "3 month", "6 month", "year"));
		dropDownTimeRange.getItems().addAll("week", "30 day", "90 day", "180 day", "year");
		
		dropDownTimeRange.getSelectionModel().select(0);
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

						Config config = FmFramework.getSessionConfig();
						FmUserNetwork net = new FmUserNetwork(config.getValue("api_key"), config.getValue("username"));
						
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
//							series.getData().add(new XYChart.Data(String.valueOf(dayLength - counter), scrobble));
							series.getData().add(new XYChart.Data(LocalDate.now().minusDays(dayLength - counter - 1).toString(), scrobble));
						}

						final CountDownLatch latch = new CountDownLatch(1);
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								try {
									barChartScrobbles.getData().clear();
									barChartScrobbles.getData().add(series);

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
