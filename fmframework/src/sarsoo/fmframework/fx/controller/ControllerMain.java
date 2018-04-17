package sarsoo.fmframework.fx.controller;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.scene.control.Button;

public class ControllerMain {

	@FXML
	private Button changeScene;
	
	private int clicked = 0;
	
	@FXML
	protected void handleButtonClick(ActionEvent event) {
		JOptionPane.showMessageDialog(null, "Changed");
	}
}
