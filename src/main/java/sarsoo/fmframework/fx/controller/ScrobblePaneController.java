package sarsoo.fmframework.fx.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

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

	}
	
	@FXML
	private void handleScrobble() {
		
	}

}