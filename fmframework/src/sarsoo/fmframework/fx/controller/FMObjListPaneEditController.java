package sarsoo.fmframework.fx.controller;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import sarsoo.fmframework.fx.AlbumTab;
import sarsoo.fmframework.fx.ArtistTab;
import sarsoo.fmframework.fx.FmFramework;
import sarsoo.fmframework.music.Album;
import sarsoo.fmframework.music.Artist;
import sarsoo.fmframework.music.FMObj;
import sarsoo.fmframework.music.Track;
import sarsoo.fmframework.util.FMObjList;
import sarsoo.fmframework.util.Getter;
import sarsoo.fmframework.util.Maths;
import sarsoo.fmframework.util.Reference;
import javafx.scene.layout.*;
import javafx.scene.chart.*;
import javafx.scene.chart.PieChart.Data;

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

	// public void populate(FMObjList list) {
	// this.list = list;
	//
	// double percent = Maths.getPercentListening(list, Reference.getUserName());
	// NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
	//
	// labelTotalScrobbles.setText("Σ " + list.getTotalUserScrobbles());
	// labelPercent.setText(String.format("%.2f%%", percent));
	//
	// Collections.sort(list);
	// Collections.reverse(list);
	//
	// int counter;
	// for (counter = 0; counter < list.size(); counter++) {
	//
	// FMObj obj = list.get(counter);
	//
	// Label name = new Label(obj.getName().toLowerCase());
	//
	// name.getStyleClass().add("nameLabel");
	//
	// name.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<Event>() {
	//
	// @Override
	// public void handle(Event event) {
	//
	// try {
	// FmFramework.getController().addTab(new ArtistTab((Artist) obj));
	// } catch (IOException e) {
	//
	// e.printStackTrace();
	// }
	//
	// }
	//
	// });
	//
	// Label userScrobbles = new Label(numberFormat.format(obj.getUserPlayCount()));
	// Label totalScrobbles = new Label(numberFormat.format(obj.getPlayCount()));
	//
	// gridPaneFMObjs.add(name, 0, counter);
	// gridPaneFMObjs.add(userScrobbles, 1, counter);
	// gridPaneFMObjs.add(totalScrobbles, 2, counter);
	//
	// }
	//
	//
	// ObservableList<PieChart.Data> pieChartData =
	// FXCollections.observableArrayList(
	// new PieChart.Data(list.getGroupName(), list.getTotalUserScrobbles()),
	// new PieChart.Data("other", Getter.getScrobbles(Reference.getUserName()) -
	// list.getTotalUserScrobbles()));
	//
	// ObservableList<PieChart.Data> pieChartArtistsData =
	// FXCollections.observableArrayList();
	// int counter2;
	// for(counter2 = 0; counter2 < list.size(); counter2++) {
	//
	// PieChart.Data data = new PieChart.Data(list.get(counter2).getName(),
	// list.get(counter2).getUserPlayCount());
	//
	// pieChartArtistsData.add(data);
	//
	// }
	//
	// Collections.sort(pieChartArtistsData, new Comparator<PieChart.Data>() {
	//
	// @Override
	// public int compare(Data arg0, Data arg1) {
	// return (int) (arg1.getPieValue() - arg0.getPieValue());
	// }
	// });
	//
	// pieChart.setData(pieChartData);
	// pieChartArtists.setData(pieChartArtistsData);
	//
	// }

	@FXML
	protected void handleRefresh(ActionEvent event) {

		refresh();
	}

	public void refresh() {
		double percent = Maths.getPercentListening(list, Reference.getUserName());
		NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);

		labelTotalScrobbles.setText("Σ " + list.getTotalUserScrobbles());
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

					try {
						FmFramework.getController().addTab(new ArtistTab((Artist) obj));
					} catch (IOException e) {

						e.printStackTrace();
					}

				}

			});

			Label userScrobbles = new Label(numberFormat.format(obj.getUserPlayCount()));
			Label totalScrobbles = new Label(numberFormat.format(obj.getPlayCount()));

			gridPaneFMObjs.add(name, 0, counter);
			gridPaneFMObjs.add(userScrobbles, 1, counter);
			gridPaneFMObjs.add(totalScrobbles, 2, counter);

		}

		ObservableList<PieChart.Data> pieChartData = FXCollections
				.observableArrayList(new PieChart.Data("list", list.getTotalUserScrobbles()), new PieChart.Data("other",
						Getter.getScrobbles(Reference.getUserName()) - list.getTotalUserScrobbles()));
		pieChart.setData(pieChartData);

	}

	@FXML
	protected TextField textArtist;
	@FXML
	protected TextField textTrack;
	@FXML
	protected TextField textAlbum;

	@FXML
	protected void handleAddTrack(ActionEvent event) {

		String name = textTrack.getText();
		System.out.println(name + ".");
		String album = textAlbum.getText();
		System.out.println(album + ".");
		String artist = textArtist.getText();
		System.out.println(artist + ".");
		System.out.println("Click");
		
		if ((name != null) && (artist != null)) {
			System.out.println("not null");
			Track track = Track.getTrack(name, artist, Reference.getUserName());
			System.out.println("track created");
			if (album != null) {
				Album albumObj = Album.getAlbum(album, artist, Reference.getUserName());
				System.out.println("album created");
				track.setAlbum(albumObj);
				System.out.println("no album");
			}
			
			list.add(track);
		}

		refresh();

	}

}