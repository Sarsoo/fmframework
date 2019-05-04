package sarsoo.fmframework.fx.controller;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.GridPane;
import sarsoo.fmframework.music.FMObj;
import sarsoo.fmframework.music.Scrobble;

public class ScrobblesViewPaneController {
	
	@FXML
	private SplitPane splitPane;
	
	@FXML
	private GridPane gridPane;
	
	@FXML
	private AreaChart areaChart;
	
	@FXML
	public void initialize() {

		

	}
	
	public void populate(FMObj obj) {
		
		ArrayList<Scrobble> scrobbles = obj.getScrobbles();
		
		
	}

}
