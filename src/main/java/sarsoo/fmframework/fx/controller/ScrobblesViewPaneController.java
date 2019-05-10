package sarsoo.fmframework.fx.controller;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
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
					
					if(scrobble.getAlbum() != null) {
						Label albumName = new Label(scrobble.getAlbum().getName().toLowerCase());
						gridPane.add(albumName, 1, counter);
					}

				}
			}
		}

	}

}
