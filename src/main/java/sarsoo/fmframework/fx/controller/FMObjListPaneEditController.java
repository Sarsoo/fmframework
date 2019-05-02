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
import sarsoo.fmframework.net.Key;
import sarsoo.fmframework.util.FMObjList;
import sarsoo.fmframework.util.Maths;
import sarsoo.fmframework.util.Reference;
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

	// public void populate(FMObjList list) {
	// this.list = list;
	//
	// double percent = Maths.getPercentListening(list, Reference.getUserName());
	// NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
	//
	// labelTotalScrobbles.setText("Î£ " + list.getTotalUserScrobbles());
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
		updateList();
		refresh();
	}

	public void updateList() {
		
		FmNetwork net = new FmUserNetwork(Key.getKey(), Reference.getUserName());
		
		FMObjList newList = new FMObjList();
		int counter;
		for (counter = 0; counter < list.size(); counter++) {
			
			newList.add(net.refresh(list.get(counter)));
		}
		
		setList(newList);
	}

	public void refresh() {
		double percent = Maths.getPercentListening(list, Reference.getUserName());
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

					try {

						if (obj.getClass() == Artist.class) {
							FmFramework.getController().addTab(new ArtistTab((Artist) obj));
						} else if (obj.getClass() == Album.class) {
							FmFramework.getController().addTab(new AlbumTab((Album) obj));
						} else if (obj.getClass() == Track.class) {
							FmFramework.getController().addTab(new TrackTab((Track) obj));
						}
					} catch (IOException e) {

						e.printStackTrace();
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

		int other = new FmUserNetwork(Key.getKey(), Reference.getUserName()).getUserScrobbleCount() - list.getTotalUserScrobbles();
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
		
		FmNetwork net = new FmUserNetwork(Key.getKey(), Reference.getUserName());

		String name = textTrack.getText();
		String album = textAlbum.getText();
		String artist = textArtist.getText();

		if ((name != null) && (artist != null)) {
			Track track = net.getTrack(name, artist);
			if (album != null) {
				Album albumObj = net.getAlbum(album, artist);
				track.setAlbum(albumObj);
				
				textAlbum.setText(null);
			}
			
			textTrack.setText(null);
			textArtist.setText(null);

			list.add(track);
		}

		refresh();

	}

	@FXML
	protected void handleAddAlbum(ActionEvent event) {
		FmNetwork net = new FmUserNetwork(Key.getKey(), Reference.getUserName());
		
		String album = textAlbum.getText();
		String artist = textArtist.getText();

		if ((album != null) && (artist != null)) {
			Album albumObj = net.getAlbum(album, artist);

			list.add(albumObj);
			textAlbum.setText(null);
			textArtist.setText(null);
		}

		refresh();

	}

	@FXML
	protected void handleAddArtist(ActionEvent event) {
		FmNetwork net = new FmUserNetwork(Key.getKey(), Reference.getUserName());
		
		String artist = textArtist.getText();

		if (artist != null) {

			Artist artistObj = net.getArtist(artist);

			list.add(artistObj);
			
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

        if(file != null) {
        	
        	ListPersister persist = new ListPersister();
        	persist.saveListToFile(file, list);
        	
        }
	}
}
