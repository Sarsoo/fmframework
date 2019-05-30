package sarsoo.fmframework.fx.controller;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.Locale;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import sarsoo.fmframework.config.Config;
import sarsoo.fmframework.error.ApiCallException;
import sarsoo.fmframework.file.ListPersister;
import sarsoo.fmframework.fm.FmNetwork;
import sarsoo.fmframework.fm.FmUserNetwork;
import sarsoo.fmframework.fx.FmFramework;
import sarsoo.fmframework.fx.tab.AlbumTab;
import sarsoo.fmframework.fx.tab.ArtistTab;
import sarsoo.fmframework.fx.tab.TrackTab;
import sarsoo.fmframework.music.Album;
import sarsoo.fmframework.music.Artist;
import sarsoo.fmframework.music.FMObj;
import sarsoo.fmframework.music.Track;
import sarsoo.fmframework.util.FMObjList;
import sarsoo.fmframework.util.Maths;
import javafx.scene.layout.*;
import javafx.scene.chart.*;
import javafx.stage.FileChooser;

public class FMObjListPaneEditController {

	@FXML
	private Label labelTotalScrobbles;

	@FXML
	private Label labelPercent;

	@FXML
	private GridPane gridPaneFMObjs;

	@FXML
	private PieChart pieChart;

	@FXML
	private PieChart pieChartArtists;

	private FMObjList list = new FMObjList();

	public void setList(FMObjList list) {
		this.list = list;
	}

	@FXML
	protected void handleRefresh(ActionEvent event) {
		updateList();
		refresh();
	}

	public void updateList() {

		FmNetwork net = new FmUserNetwork(FmFramework.getSessionConfig().getValue("api_key"),
				FmFramework.getSessionConfig().getValue("username"));

		FMObjList newList = new FMObjList();
		int counter;
		for (counter = 0; counter < list.size(); counter++) {

			try {
				newList.add(net.refresh(list.get(counter)));
			} catch (ApiCallException e) {}
		}

		setList(newList);
	}

	public void refresh() {
		double percent = Maths.getPercentListening(list, FmFramework.getSessionConfig().getValue("username"));
		NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.UK);

		labelTotalScrobbles.setText(numberFormat.format(list.getTotalUserScrobbles()));
		labelPercent.setText(String.format("%.2f%%", percent));

		Collections.sort(list);

		Collections.reverse(list);

		gridPaneFMObjs.getChildren().clear();

		int counter;
		for (counter = 0; counter < list.size(); counter++) {

			FMObj obj = list.get(counter);

			Label name = new Label(obj.getName().toLowerCase());

			name.getStyleClass().add("nameLabel");

			name.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<Event>() {

				@Override
				public void handle(Event event) {

					if (obj.getClass() == Artist.class) {
						FmFramework.getController().addTab(new ArtistTab((Artist) obj));
					} else if (obj.getClass() == Album.class) {
						FmFramework.getController().addTab(new AlbumTab((Album) obj));
					} else if (obj.getClass() == Track.class) {
						FmFramework.getController().addTab(new TrackTab((Track) obj));
					}

				}

			});

			Label userScrobbles = new Label(numberFormat.format(obj.getUserPlayCount()));
			Label totalScrobbles = new Label(numberFormat.format(obj.getPlayCount()));

			Button delete = new Button("delete");
			delete.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<Event>() {

				@Override
				public void handle(Event event) {

					list.remove(obj);
					refresh();

				}

			});

			gridPaneFMObjs.add(name, 0, counter);
			gridPaneFMObjs.add(userScrobbles, 1, counter);
			gridPaneFMObjs.add(totalScrobbles, 2, counter);
			gridPaneFMObjs.add(delete, 3, counter);

		}

		Config config = FmFramework.getSessionConfig();
		FmUserNetwork net = new FmUserNetwork(config.getValue("api_key"), config.getValue("username"));

		int other;
		try {
			other = net.getUserScrobbleCount() - list.getTotalUserScrobbles();
		} catch (ApiCallException e) {
			other = 0;
		}
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
				new PieChart.Data(String.format("%d%%", (int) list.getTotalUserScrobbles() * 100 / other),
						list.getTotalUserScrobbles()),
				new PieChart.Data("other", other));
		pieChart.setData(pieChartData);

		ObservableList<PieChart.Data> pieChartArtistsData = FXCollections.observableArrayList();

		for (counter = 0; counter < list.size(); counter++) {

			PieChart.Data data = new PieChart.Data(list.get(counter).getName(), list.get(counter).getUserPlayCount());
			pieChartArtistsData.add(data);
		}

		pieChartArtists.setData(pieChartArtistsData);
	}

	@FXML
	protected TextField textArtist;
	@FXML
	protected TextField textTrack;
	@FXML
	protected TextField textAlbum;

	@FXML
	protected void handleAddTrack(ActionEvent event) {

		Config config = FmFramework.getSessionConfig();
		FmUserNetwork net = new FmUserNetwork(config.getValue("api_key"), config.getValue("username"));

		String name = textTrack.getText();
		String album = textAlbum.getText();
		String artist = textArtist.getText();

		if ((name != null) && (artist != null)) {
			Track track;
			try {
				track = net.getTrack(name, artist);
				
				if (album != null) {
					Album albumObj = net.getAlbum(album, artist);
					track.setAlbum(albumObj);

					textAlbum.setText(null);
				}

				textTrack.setText(null);
				textArtist.setText(null);

				list.add(track);
				
			} catch (ApiCallException e) {}			
		}

		refresh();

	}

	@FXML
	protected void handleAddAlbum(ActionEvent event) {

		Config config = FmFramework.getSessionConfig();
		FmUserNetwork net = new FmUserNetwork(config.getValue("api_key"), config.getValue("username"));

		String album = textAlbum.getText();
		String artist = textArtist.getText();

		if ((album != null) && (artist != null)) {
			Album albumObj;
			try {
				albumObj = net.getAlbum(album, artist);
				
				list.add(albumObj);
				
			} catch (ApiCallException e) {}
			
			textAlbum.setText(null);
			textArtist.setText(null);
		}

		refresh();

	}

	@FXML
	protected void handleAddArtist(ActionEvent event) {
		Config config = FmFramework.getSessionConfig();
		FmUserNetwork net = new FmUserNetwork(config.getValue("api_key"), config.getValue("username"));

		String artist = textArtist.getText();

		if (artist != null) {

			Artist artistObj;
			try {
				artistObj = net.getArtist(artist);
				
				list.add(artistObj);
				
			} catch (ApiCallException e) {}

			textArtist.setText(null);
		}

		refresh();

	}

	@FXML
	protected void handleSave(ActionEvent event) {

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("save fm list");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("FMObjList", "*.fmlist"));
		File file = fileChooser.showSaveDialog(FmFramework.getStage());

		if (file != null) {

			ListPersister persist = new ListPersister();
			persist.saveListToFile(file, list);

		}
	}
}
