package sarsoo.fmframework.fx.controller;

import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import sarsoo.fmframework.util.FMObjList;
import sarsoo.fmframework.util.Getter;
import sarsoo.fmframework.util.Reference;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.chart.*;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

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
                new PieChart.Data("rock", Getter.getUserTag(Reference.getUserName(), "rap").getTotalUserScrobbles()));
        pieChartGenres = new PieChart(pieChartData);
        
        
	}
	
}
