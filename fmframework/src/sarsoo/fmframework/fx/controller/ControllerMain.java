package sarsoo.fmframework.fx.controller;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.text.Text;
import sarsoo.fmframework.music.Album;
import sarsoo.fmframework.util.FMObjList;
import sarsoo.fmframework.util.Getter;
import sarsoo.fmframework.util.Reference;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.chart.*;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

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
		
		
//		updateGenrePieChart();
		
		labelStatsScrobblesTotal.setText(NumberFormat.getInstance().format(Getter.getScrobbles(Reference.getUserName())));
		
	}
	
	public void updateGenrePieChart() {

		String[] names = {
			"rock",
			"rap",
			"classic rock",
			"pop punk",
			"electronic",
			"metal",
			"grime",
			"classic rap",
			"indie",
			"jazz",
			"blues",
			"core"
		};	
		
		ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
//                new PieChart.Data("rap", Getter.getUserTag(Reference.getUserName(), "rap").getTotalUserScrobbles()),
//                new PieChart.Data("rock", Getter.getUserTag(Reference.getUserName(), "rap").getTotalUserScrobbles()),
                new PieChart.Data("rock", 5),
                new PieChart.Data("rock", 5),
                new PieChart.Data("rock", 5));
        pieChartGenres = new PieChart(pieChartData);
        
        
	}
	
	@FXML
	protected void handleLookupAlbum(ActionEvent event) throws IOException {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../ui/albumview.fxml"));
		
		Pane pane = (Pane)loader.load();
		
//		AlbumTabController controller = new AlbumTabController(Album.getAlbum("recovery", "eminem", Reference.getUserName()));
//		loader.setController(controller);
//		pane.setController(controller);
		
//		Parent pane = loader.load();
		
		Tab tab = new Tab("Recovery");
		
		tab.setContent(pane);
		
		AlbumTabController albumControl = (AlbumTabController)loader.getController();
		
		
		albumControl.populateTab(Getter.getAlbum());
		
		
		tabPane.getTabs().add(tab);
	}
	
	
}
