package sarsoo.fmframework.fx.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

import org.json.JSONObject;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import sarsoo.fmframework.config.Config;
import sarsoo.fmframework.error.ApiCallException;
import sarsoo.fmframework.fm.FmAuthNetwork;
import sarsoo.fmframework.fx.FmFramework;
import sarsoo.fmframework.music.Artist;
import sarsoo.fmframework.music.Artist.ArtistBuilder;
import sarsoo.fmframework.music.Scrobble;
import sarsoo.fmframework.music.Track;
import sarsoo.fmframework.music.Track.TrackBuilder;
import sarsoo.fmframework.music.Album.AlbumBuilder;

public class ScrobblePaneController {

	@FXML
	private TextField textTrack;

	@FXML
	private TextField textAlbum;

	@FXML
	private TextField textArtist;

	@FXML
	private TextField textAlbumArtist;

	@FXML
	private Slider sliderHour;

	@FXML
	private Slider sliderMinute;

	@FXML
	private Slider sliderSecond;

	@FXML
	private Label labelStatus;

	@FXML
	private DatePicker datePicker;

	@FXML
	private Button buttonScrobble;

	@FXML
	public void initialize() {

		Config config = FmFramework.getSessionConfig();

		if (config.getValue("session_key") == null) {
			FmFramework.getController().authenticate();
		}

	}
	
	@FXML
	private void handleClear() {
		textTrack.setText("");
		textAlbum.setText("");
		textArtist.setText("");
		textAlbumArtist.setText("");
		
		sliderHour.setValue(0);
		sliderMinute.setValue(0);
		sliderSecond.setValue(0);
		
		datePicker.setValue(null);
	}

	@FXML
	private void handleScrobble() {
		if (validateText()) {
			if (validateDate()) {
				Config config = FmFramework.getSessionConfig();

				if (config.getValue("session_key") != null) {
					FmAuthNetwork net = new FmAuthNetwork(config.getValue("api_key"), 
							config.getValue("api_secret"),
							config.getValue("username"));
					
					Artist artist = new ArtistBuilder(textArtist.getText()).build();
					Track track = new TrackBuilder(textTrack.getText(), artist).build();
					
					Scrobble scrobble = new Scrobble(getEpochDate(), track);
					
					if(textAlbum.getText().length() > 0) {
						Artist albumArtist = new ArtistBuilder(textAlbumArtist.getText()).build();
						scrobble.setAlbum(new AlbumBuilder(textAlbum.getText(), albumArtist).build());
					}
					
					JSONObject obj;
					
					try {
						obj = net.scrobble(scrobble, config.getValue("session_key"));
						
						if(obj.getJSONObject("scrobbles").getJSONObject("@attr").getInt("accepted") == 1) {
							labelStatus.setText("sent!");
						}else {
							labelStatus.setText("failed");
						}
						
					} catch (ApiCallException e) {}
					
				}else {
					labelStatus.setText("unauthorized");
				}
			} else {
				labelStatus.setText("wrong date");
			}
		} else {
			labelStatus.setText("wrong text");
		}
	}
	
	@FXML
	public void handleNow() {
		LocalDateTime date = LocalDateTime.now();

		datePicker.setValue(date.toLocalDate());
		sliderHour.setValue(date.getHour());
		sliderMinute.setValue(date.getMinute());
		sliderSecond.setValue(date.getSecond());
	}

	private boolean validateText() {
		String track = textTrack.getText();
		String album = textAlbum.getText();
		String artist = textArtist.getText();
		String albumArtist = textAlbumArtist.getText();

		if (track.length() > 0 && artist.length() > 0) {
			if (album.length() > 0 ^ albumArtist.length() > 0) {
				return false;
			} else {
				return true;
			}
		}

		return false;
	}

	private boolean validateDate() {
		if (datePicker.getValue() != null) {

			LocalDateTime now = LocalDateTime.now();
			LocalDateTime selected = getLocalDateTime();

			if (selected.isAfter(now.minusWeeks(2))) {
				if (selected.isBefore(now)) {
					return true;
				}
				return false;
			}
			return false;
		}
		return false;
	}

	public LocalDateTime getLocalDateTime() {

		LocalDate date = datePicker.getValue();
		LocalTime time = LocalTime.of((int) sliderHour.getValue(), (int) sliderMinute.getValue(),
				(int) sliderSecond.getValue());

		return LocalDateTime.of(date, time);
	}

	public long getEpochDate() {
		ZoneId zone = ZoneId.systemDefault();
		return getLocalDateTime().atZone(zone).toEpochSecond();
	}

}