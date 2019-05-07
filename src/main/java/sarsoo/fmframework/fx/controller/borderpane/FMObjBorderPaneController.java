package sarsoo.fmframework.fx.controller.borderpane;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import sarsoo.fmframework.fx.controller.ScrobblesViewPaneController;
import sarsoo.fmframework.fx.tab.FMObjTab;
import sarsoo.fmframework.net.Network;

public abstract class FMObjBorderPaneController {
	
	@FXML
	protected ToolBar toolBar;
	
	@FXML
	protected ProgressBar progressBar;
	
	@FXML
	protected AnchorPane progressBarAnchorPane;
	
	@FXML
	protected Button buttonViewScrobbles;
	
	@FXML
	protected Button buttonViewInfo;
	
	@FXML
	protected Button buttonViewOnline;
	
	@FXML
	protected BorderPane borderPane;
	
	protected AnchorPane scrobbleAnchorPane;
	protected ScrobblesViewPaneController scrobblePaneController;
	
	protected AnchorPane infoAnchorPane;
	
	@FXML
	protected abstract void handleRefresh(ActionEvent event);
	
	@FXML
	protected void handleViewScrobbles(ActionEvent event) {
		setScrobblesView();
	}
	
	@FXML
	protected void handleViewInfo(ActionEvent event) {
		setInfoView();
	}
	
	@FXML
	protected abstract void handleViewOnline(ActionEvent event);
	
	public void setScrobblesView() {
		buttonViewInfo.setDisable(false);
		buttonViewScrobbles.setDisable(true);
		borderPane.setCenter(scrobbleAnchorPane);
	}
	
	public void setInfoView() {
		buttonViewInfo.setDisable(true);
		buttonViewScrobbles.setDisable(false);
		borderPane.setCenter(infoAnchorPane);
	}
	
	public abstract void loadInfoPane()throws IOException;
	
	public void loadScrobblePane() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../../ui/FMObjScrobblePane.fxml"));

		this.scrobbleAnchorPane = (AnchorPane) loader.load();

		AnchorPane.setTopAnchor(scrobbleAnchorPane, 0.0);
		AnchorPane.setLeftAnchor(scrobbleAnchorPane, 0.0);
		AnchorPane.setRightAnchor(scrobbleAnchorPane, 0.0);
		AnchorPane.setBottomAnchor(scrobbleAnchorPane, 0.0);

		this.scrobblePaneController = (ScrobblesViewPaneController) loader.getController();
	}

}
