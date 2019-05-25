package sarsoo.fmframework.fx.controller;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.GridPane;
import sarsoo.fmframework.config.Config;
import sarsoo.fmframework.error.ApiCallException;
import sarsoo.fmframework.fm.FmUserNetwork;
import sarsoo.fmframework.fx.FmFramework;
import sarsoo.fmframework.music.FMObj;
import sarsoo.fmframework.music.Scrobble;
import sarsoo.fmframework.util.FMObjCalendarWrapper;
import sarsoo.fmframework.util.MonthScrobbles;
import sarsoo.fmframework.util.ScrobbleCountCalendar;

public class ScrobblesViewPaneController {

	@FXML
	private SplitPane splitPane;

	@FXML
	private GridPane gridPane;

	@FXML
	private AreaChart<String, Integer> areaChart;

	@FXML
	private Button buttonTracks;

	@FXML
	private Button buttonAlbums;

	@FXML
	private Button buttonTracksAlbums;

	@FXML
	private CheckBox checkBoxCumulative;
	
	@FXML
	private CheckBox checkBoxTotal;

	private FMObj obj;
	
	private List<Scrobble> scrobbles;

	private LocalDate firstDate;

	@FXML
	public void initialize() {

		buttonTracks.setDisable(true);
		buttonAlbums.setDisable(true);
		buttonTracksAlbums.setDisable(true);

	}

	public void populate(FMObj obj) {
		this.obj = obj;
		this.scrobbles = obj.getScrobbles();
		populate();
	}
	
	public void populate(ArrayList<Scrobble> scrobbles) {
		this.scrobbles = scrobbles;
		populate();
	}
	
	private void populate() {
		
		scrobbles.sort(Comparator.comparing(Scrobble::getDateTime).reversed());

		if (scrobbles != null) {
			if (scrobbles.size() > 0) {

				NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.UK);

				gridPane.getChildren().clear();

				int counter;
				for (counter = 0; counter < scrobbles.size(); counter++) {

					Scrobble scrobble = scrobbles.get(counter);

					Label trackName = new Label(scrobble.getTrack().getName().toLowerCase());
					Label artistName = new Label(scrobble.getTrack().getArtist().getName().toLowerCase());
					Label date = new Label(scrobble.getDateTime().toString().toLowerCase());

					gridPane.add(trackName, 0, counter);
					gridPane.add(artistName, 2, counter);
					gridPane.add(date, 3, counter);

					if (scrobble.getAlbum() != null) {
						Label albumName = new Label(scrobble.getAlbum().getName().toLowerCase());
						gridPane.add(albumName, 1, counter);
					}

				}

				Config config = FmFramework.getSessionConfig();
				FmUserNetwork net = new FmUserNetwork(config.getValue("api_key"), config.getValue("username"));

				try {
					firstDate = net.getFirstScrobbleDateTime().toLocalDate();
				} catch (ApiCallException e) {
					firstDate = LocalDate.now();
				}

				buttonAlbums.setDisable(false);
				buttonTracksAlbums.setDisable(false);
				graphShowTracks();

			}
		}

	}

	public void graphShowTracks() {

		ArrayList<FMObjCalendarWrapper> list = new ArrayList<>();

		ScrobbleCountCalendar totalCalendar = new ScrobbleCountCalendar(firstDate, "total");

		for (Scrobble scrobble : scrobbles) {

			Boolean needNew = true;

			LocalDateTime scrobbleDate = scrobble.getDateTime();

			for (FMObjCalendarWrapper wrapper : list) {
				if (scrobble.getTrack().equals(wrapper.getScrobble().getTrack())) {

					needNew = false;

					wrapper.getCalendar().addCount(scrobbleDate.getMonth(), scrobbleDate.getYear());
				}
			}

			if (needNew) {

				ScrobbleCountCalendar calendar = new ScrobbleCountCalendar(firstDate, scrobble.getTrack().getName());

				calendar.addCount(scrobbleDate.getMonth(), scrobbleDate.getYear());

				list.add(new FMObjCalendarWrapper(scrobble, calendar));
			}

			totalCalendar.addCount(scrobbleDate.getMonth(), scrobbleDate.getYear());
		}

		List<ScrobbleCountCalendar> toShow = list.stream().map(FMObjCalendarWrapper::getCalendar)
				.collect(Collectors.toList());

		if (list.size() > 1 && checkBoxTotal.isSelected()) {
			toShow.add(totalCalendar);
		}

		refreshGraph(toShow);

	}

	public void graphShowAlbums() {

		ArrayList<FMObjCalendarWrapper> list = new ArrayList<>();

		ScrobbleCountCalendar totalCalendar = new ScrobbleCountCalendar(firstDate, "total");

		for (Scrobble scrobble : scrobbles) {

			Boolean needNew = true;

			LocalDateTime scrobbleDate = scrobble.getDateTime();

			for (FMObjCalendarWrapper wrapper : list) {
				if (scrobble.getAlbum().equals(wrapper.getScrobble().getAlbum())) {

					needNew = false;

					wrapper.getCalendar().addCount(scrobbleDate.getMonth(), scrobbleDate.getYear());
				}
			}

			if (needNew) {

				ScrobbleCountCalendar calendar = new ScrobbleCountCalendar(firstDate, scrobble.getAlbum().getName());

				calendar.addCount(scrobbleDate.getMonth(), scrobbleDate.getYear());

				list.add(new FMObjCalendarWrapper(scrobble, calendar));
			}

			totalCalendar.addCount(scrobbleDate.getMonth(), scrobbleDate.getYear());
		}

		List<ScrobbleCountCalendar> toShow = list.stream().map(FMObjCalendarWrapper::getCalendar)
				.collect(Collectors.toList());

		if (list.size() > 1 && checkBoxTotal.isSelected()) {
			toShow.add(totalCalendar);
		}

		refreshGraph(toShow);

	}

	public void graphShowTracksByAlbum() {

		ArrayList<FMObjCalendarWrapper> list = new ArrayList<>();

		ScrobbleCountCalendar totalCalendar = new ScrobbleCountCalendar(firstDate, "total");

		for (Scrobble scrobble : scrobbles) {

			Boolean needNew = true;

			LocalDateTime scrobbleDate = scrobble.getDateTime();

			for (FMObjCalendarWrapper wrapper : list) {
				if (scrobble.getTrack().equals(wrapper.getScrobble().getTrack())
						&& scrobble.getAlbum().equals(wrapper.getScrobble().getAlbum())) {

					needNew = false;

					wrapper.getCalendar().addCount(scrobbleDate.getMonth(), scrobbleDate.getYear());
				}
			}

			if (needNew) {

				ScrobbleCountCalendar calendar = new ScrobbleCountCalendar(firstDate, scrobble.getTrack().getName() + " / " + scrobble.getAlbum().getName());

				calendar.addCount(scrobbleDate.getMonth(), scrobbleDate.getYear());

				list.add(new FMObjCalendarWrapper(scrobble, calendar));
			}

			totalCalendar.addCount(scrobbleDate.getMonth(), scrobbleDate.getYear());
		}

		List<ScrobbleCountCalendar> toShow = list.stream().map(FMObjCalendarWrapper::getCalendar)
				.collect(Collectors.toList());

		if (list.size() > 1 && checkBoxTotal.isSelected()) {
			toShow.add(totalCalendar);
		}

		refreshGraph(toShow);

	}

	public void refreshGraph(List<ScrobbleCountCalendar> calendars) {

		areaChart.getData().clear();

		for (ScrobbleCountCalendar calendar : calendars) {

			XYChart.Series<String, Integer> series = new XYChart.Series<>();
			series.setName(calendar.getName());

			int cumulative = 0;

			for (MonthScrobbles month : calendar.getMonthScrobbles()) {
				cumulative += month.getCount();

				if (checkBoxCumulative.isSelected()) {
					series.getData().add(new Data<String, Integer>(month.toString(), cumulative));
				}else {
					series.getData().add(new Data<String, Integer>(month.toString(), month.getCount()));
				}
			}

			areaChart.getData().add(series);

		}

	}

	@FXML
	private void handleShowTracks() {

		buttonTracks.setDisable(true);
		buttonAlbums.setDisable(false);
		buttonTracksAlbums.setDisable(false);

		graphShowTracks();

	}

	@FXML
	private void handleShowAlbums() {

		buttonTracks.setDisable(false);
		buttonAlbums.setDisable(true);
		buttonTracksAlbums.setDisable(false);

		graphShowAlbums();

	}

	@FXML
	private void handleShowTracksByAlbums() {

		buttonTracks.setDisable(false);
		buttonAlbums.setDisable(false);
		buttonTracksAlbums.setDisable(true);

		graphShowTracksByAlbum();

	}
}