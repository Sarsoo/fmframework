package sarsoo.fmframework.fx.controller;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import sarsoo.fmframework.fm.FmNetwork;
import sarsoo.fmframework.fx.ArtistTab;
import sarsoo.fmframework.fx.FmFramework;
import sarsoo.fmframework.music.Album;
import sarsoo.fmframework.music.Wiki;
import sarsoo.fmframework.net.Key;
import sarsoo.fmframework.net.Network;
import sarsoo.fmframework.util.Maths;
import sarsoo.fmframework.util.Reference;
import javafx.scene.chart.LineChart;
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
		

	}
	
	@FXML
	protected void handleRefresh(ActionEvent event) {
		refresh();
	}	


	public void populate(Album album) {
		
		
	}
	
	
	public void refresh() {
		
	}

}
