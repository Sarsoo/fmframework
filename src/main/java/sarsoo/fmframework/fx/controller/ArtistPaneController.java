package sarsoo.fmframework.fx.controller;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import sarsoo.fmframework.fx.FMObjListTab;
import sarsoo.fmframework.fx.FmFramework;
import sarsoo.fmframework.music.Artist;
import sarsoo.fmframework.music.Wiki;
import sarsoo.fmframework.net.Network;
import sarsoo.fmframework.util.Getter;
import sarsoo.fmframework.util.Maths;
import sarsoo.fmframework.util.Reference;
import javafx.scene.control.*;

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
	public void initialize() {

		

	}
	
	Artist artist;

	public void populate(Artist artist) {
		
		this.artist = artist;
		
		NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);

		labelArtistName.setText(artist.getName());
		labelUserScrobbles.setText(numberFormat.format(artist.getUserPlayCount())
				+ String.format(" Scrobbles (%.2f%%)", Maths.getPercentListening(artist, Reference.getUserName())));

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
		
		if(wiki != null) {
			
			textAreaWiki.setText(wiki.getContent()+ "\n\n" + wiki.getDate());
		}
	}
	
	@FXML
	protected void handleRefresh(ActionEvent event) {
		refresh();
	}	
	
	@FXML
	protected void viewOnline(ActionEvent event) {
		Network.openURL(artist.getUrl());
	}
	
	
	@FXML
	protected void viewRYM(ActionEvent event) {
		Network.openURL(artist.getRymURL());
	}
	
	@FXML
	protected void handleViewTracks(ActionEvent event) {

		try {
			FmFramework.getController().addTab(new FMObjListTab(Getter.getArtistTracks(artist.getName(), Reference.getUserName())));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void refresh() {
		artist.refresh();
		
		NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);

		labelUserScrobbles.setText(numberFormat.format(artist.getUserPlayCount())
				+ String.format(" Scrobbles (%.2f%%)", Maths.getPercentListening(artist, Reference.getUserName())));

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
		
		if(wiki != null) {
			
			textAreaWiki.setText(wiki.getContent()+ "\n\n" + wiki.getDate());
		}
	}

}
