package sarsoo.fmframework.fx.controller;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.text.Text;
import sarsoo.fmframework.fx.FmFramework;
import sarsoo.fmframework.music.Album;
import sarsoo.fmframework.music.Artist;
import sarsoo.fmframework.music.Wiki;
import sarsoo.fmframework.net.Network;
import sarsoo.fmframework.util.FMObjList;
import sarsoo.fmframework.util.Getter;
import sarsoo.fmframework.util.Maths;
import sarsoo.fmframework.util.Reference;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.chart.*;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

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
