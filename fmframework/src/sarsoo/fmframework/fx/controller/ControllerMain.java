package sarsoo.fmframework.fx.controller;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.text.Text;
import sarsoo.fmframework.fx.AlbumTab;
import sarsoo.fmframework.fx.ArtistTab;
import sarsoo.fmframework.fx.FMObjListTab;
import sarsoo.fmframework.fx.TrackTab;
import sarsoo.fmframework.music.Album;
import sarsoo.fmframework.music.Tag;
import sarsoo.fmframework.util.FMObjList;
import sarsoo.fmframework.util.Getter;
import sarsoo.fmframework.util.Reference;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.chart.*;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.Parent;

import javafx.scene.layout.*;

public class ControllerMain {

	@FXML
	private Label labelStatsScrobblesToday;

	@FXML
	private Label labelStatsUsername;

	@FXML
	private Label labelStatsScrobblesTotal;

	@FXML
	private PieChart pieChartGenres;

	@FXML
	private TabPane tabPane;

	@FXML
	public void initialize() {
		Reference.setUserName("sarsoo");

		labelStatsScrobblesToday.setText(Integer.toString(Getter.getScrobblesToday(Reference.getUserName())));

		labelStatsUsername.setText(Reference.getUserName());

		// updateGenrePieChart();

		labelStatsScrobblesTotal
				.setText(NumberFormat.getInstance().format(Getter.getScrobbles(Reference.getUserName())));

		tags = Getter.getUserTags(Reference.getUserName());

		int counter;
		for (counter = 0; counter < tags.size(); counter++) {

			String name = tags.get(counter).getName().toLowerCase();

			// System.out.println(name);

			MenuItem item = new MenuItem(name);

			item.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					try {
						FMObjListTab tab = new FMObjListTab(Getter.getUserTag(Reference.getUserName(), name));

						tabPane.getTabs().add(tab);
//						System.out.println("tab added");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			});

			menuTag.getItems().add(item);
		}

	}

	public void updateGenrePieChart() {

		String[] names = { "rock", "rap", "classic rock", "pop punk", "electronic", "metal", "grime", "classic rap",
				"indie", "jazz", "blues", "core" };

		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
				// new PieChart.Data("rap", Getter.getUserTag(Reference.getUserName(),
				// "rap").getTotalUserScrobbles()),
				// new PieChart.Data("rock", Getter.getUserTag(Reference.getUserName(),
				// "rap").getTotalUserScrobbles()),
				new PieChart.Data("rock", 5), new PieChart.Data("rock", 5), new PieChart.Data("rock", 5));
		pieChartGenres = new PieChart(pieChartData);

	}

	@FXML
	protected void handleLookupAlbum(ActionEvent event) throws IOException {

		// FXMLLoader loader = new
		// FXMLLoader(getClass().getResource("../ui/albumview.fxml"));
		//
		// Pane pane = (Pane)loader.load();
		//
		//// AlbumTabController controller = new
		// AlbumTabController(Album.getAlbum("recovery", "eminem",
		// Reference.getUserName()));
		//// loader.setController(controller);
		//// pane.setController(controller);
		//
		//// Parent pane = loader.load();
		//
		// Tab tab = new Tab("Album View");
		//
		// tab.setContent(pane);
		//
		// AlbumTabController albumControl = (AlbumTabController)loader.getController();
		//
		//
		// albumControl.populateTab(Getter.getAlbum());
		//
		//
		// tabPane.getTabs().add(tab);

		// Tab tab = new Tab("rock");
		//
		// tab.setContent(new FMObjListTab(Getter.getUserTag(Reference.getUserName(),
		// "rock")));
		// tabPane.getTabs().add(tab);
		//
		
		tabPane.getTabs().add(new AlbumTab(Getter.getAlbum()));
	}
	
	@FXML
	protected void handleLookupArtist(ActionEvent event) throws IOException {

		tabPane.getTabs().add(new ArtistTab(Getter.getArtist()));
	}
	
	@FXML
	protected void handleLookupTrack(ActionEvent event) throws IOException {

		tabPane.getTabs().add(new TrackTab(Getter.getTrack()));
	}

	@FXML
	private Menu menuTag;

	private ArrayList<Tag> tags;

	@FXML
	protected void handleTagClick(ActionEvent event) throws IOException {
		// System.out.println("clicked");

		if (tags == null) {
			// System.out.println("list null");

			tags = Getter.getUserTags(Reference.getUserName());

			int counter;
			for (counter = 0; counter < tags.size(); counter++) {

				String name = tags.get(counter).getName().toLowerCase();

				// System.out.println(name);

				MenuItem item = new MenuItem(name);

				item.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent e) {
						try {
							FMObjListTab tab = new FMObjListTab(Getter.getUserTag(Reference.getUserName(), name));

							tabPane.getTabs().add(tab);
							System.out.println("tab added");
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				});

				menuTag.getItems().add(item);
			}
		}
	}

	@FXML
	protected void handleKeyShortcut(KeyEvent event) throws IOException {

		if (event.getCode() == KeyCode.F5) {
			refresh();
//			System.out.println("refreshed");
		}
	}

	public void refresh() {
		labelStatsScrobblesToday.setText(Integer.toString(Getter.getScrobblesToday(Reference.getUserName())));

		labelStatsUsername.setText(Reference.getUserName());

		labelStatsScrobblesTotal
				.setText(NumberFormat.getInstance().format(Getter.getScrobbles(Reference.getUserName())));

		tags = Getter.getUserTags(Reference.getUserName());

		int counter;
		for (counter = 0; counter < tags.size(); counter++) {

			String name = tags.get(counter).getName();

			// System.out.println(name);

			MenuItem item = new MenuItem(name);

			item.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					try {
						FMObjListTab tab = new FMObjListTab(Getter.getUserTag(Reference.getUserName(), name));

						tabPane.getTabs().add(tab);
						System.out.println("tab added");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			});
			
			menuTag.getItems().clear();
			menuTag.getItems().add(item);
		}
	}

	public void addTab(Tab tab) {
		tabPane.getTabs().add(tab);
		SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
		
		selectionModel.select(tab);
	}
}
