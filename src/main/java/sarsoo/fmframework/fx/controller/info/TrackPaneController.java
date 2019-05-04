package sarsoo.fmframework.fx.controller.info;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import sarsoo.fmframework.fm.FmUserNetwork;
import sarsoo.fmframework.fx.FmFramework;
import sarsoo.fmframework.fx.tab.AlbumTab;
import sarsoo.fmframework.fx.tab.ArtistTab;
import sarsoo.fmframework.music.Track;
import sarsoo.fmframework.music.Wiki;
import sarsoo.fmframework.net.Key;
import sarsoo.fmframework.net.Network;
import sarsoo.fmframework.util.Maths;
import sarsoo.fmframework.util.Reference;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class TrackPaneController {

	@FXML
	private Label labelTrackName;

	@FXML
	private Label labelAlbumName;

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
	private Button buttonViewAlbum;
	
	@FXML
	private BorderPane trackBorderPane;
	
	@FXML
	private AnchorPane infoAnchorPane;

	@FXML
	public void initialize() {

	}

	Track track;

	public void populate(Track track) {

		this.track = track;

		NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);

		labelTrackName.setText(track.getName());
		
		if (track.getAlbum() == null)
			labelAlbumName.setVisible(false);
		else
			labelAlbumName.setText(track.getAlbum().getName());
		
		labelArtistName.setText(track.getArtist().getName());
		labelUserScrobbles.setText(numberFormat.format(track.getUserPlayCount())
				+ String.format(" Scrobbles (%.2f%%)", Maths.getPercentListening(track, Reference.getUserName())));

		labelTotalListeners.setText(numberFormat.format(track.getListeners()) + " Listeners");
		labelTotalScrobbles.setText(numberFormat.format(track.getPlayCount()) + " Total Scrobbles");

		double ratio = track.getTimeListenRatio();

		if (ratio > 1) {
			labelRatio.setText(String.format("listen every %.2f days", ratio));
		} else if (ratio == 1) {
			labelRatio.setText("listen every day");
		} else {
			labelRatio.setText(String.format("%.2f times a day", 1 / ratio));
		}

		Wiki wiki = track.getWiki();

		if (wiki != null) {

			textAreaWiki.setText(wiki.getContent() + "\n\n" + wiki.getDate());
		}else {
			trackBorderPane.setCenter(infoAnchorPane);
		}

		if (track.getAlbum() == null) {
			buttonViewAlbum.setVisible(false);
		}
	}

	@FXML
	protected void handleRefresh(ActionEvent event) {
		refresh();
	}

	@FXML
	protected void viewOnline(ActionEvent event) {
		Network.openURL(track.getUrl());
	}

	@FXML
	protected void viewArtist(ActionEvent event) throws IOException {
		FmFramework.getController().addTab(new ArtistTab(track.getArtist()));
	}

	@FXML
	protected void viewAlbum(ActionEvent event) throws IOException {
		FmFramework.getController().addTab(new AlbumTab(track.getAlbum()));
	}

	public void refresh() {
		track = new FmUserNetwork(Key.getKey(), Reference.getUserName()).refresh(track);

		NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);

		labelUserScrobbles.setText(numberFormat.format(track.getUserPlayCount())
				+ String.format(" Scrobbles (%.2f%%)", Maths.getPercentListening(track, Reference.getUserName())));

		labelTotalListeners.setText(numberFormat.format(track.getListeners()) + " Listeners");
		labelTotalScrobbles.setText(numberFormat.format(track.getPlayCount()) + " Total Scrobbles");

		double ratio = track.getTimeListenRatio();

		if (ratio > 1) {
			labelRatio.setText(String.format("listen every %.2f days", ratio));
		} else if (ratio == 1) {
			labelRatio.setText("listen every day");
		} else {
			labelRatio.setText(String.format("%.2f times a day", 1 / ratio));
		}

		Wiki wiki = track.getWiki();

		if (wiki != null) {

			textAreaWiki.setText(wiki.getContent() + "\n\n" + wiki.getDate());
		}
	}

}
