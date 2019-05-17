package sarsoo.fmframework.fx.controller.info;

import java.text.NumberFormat;
import java.util.Locale;

import javafx.fxml.FXML;
import sarsoo.fmframework.fx.FmFramework;
import sarsoo.fmframework.music.Artist;
import sarsoo.fmframework.music.Wiki;
import sarsoo.fmframework.util.Maths;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class ArtistPaneController {

	@FXML
	private Label labelArtistName;

	@FXML
	private Label labelUserScrobbles;

	@FXML
	private Label labelRatio;

	@FXML
	private Label labelTotalListeners;

	@FXML
	private Label labelTotalScrobbles;

	@FXML
	private TextArea textAreaWiki;

	@FXML
	private AnchorPane infoAnchorPane;
	
	@FXML
	private AnchorPane anchorPane;
	
	@FXML
	private GridPane gridPane;

	@FXML
	public void initialize() {

	}

	public void refresh(Artist artist) {

		NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);

		labelArtistName.setText(artist.getName());
		labelUserScrobbles.setText(numberFormat.format(artist.getUserPlayCount()) + String.format(" Scrobbles (%.2f%%)",
				Maths.getPercentListening(artist, FmFramework.getSessionConfig().getValue("username"))));

		labelTotalListeners.setText(numberFormat.format(artist.getListeners()) + " Listeners");
		labelTotalScrobbles.setText(numberFormat.format(artist.getPlayCount()) + " Total Scrobbles");

		double ratio = artist.getTimeListenRatio();

		if (ratio > 1) {
			labelRatio.setText(String.format("listen every %.2f days", ratio));
		} else if (ratio == 1) {
			labelRatio.setText("listen every day");
		} else {
			labelRatio.setText(String.format("%.2f times a day", 1 / ratio));
		}

		Wiki wiki = artist.getWiki();

		if (wiki != null) {

			textAreaWiki.setText(wiki.getContent() + "\n\n" + wiki.getDate());
		} else {
			anchorPane.getChildren().set(0, gridPane);
		}
	}

}
