package sarsoo.fmframework.fx.tab;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import sarsoo.fmframework.fx.controller.ScrobblesViewPaneController;
import sarsoo.fmframework.fx.controller.borderpane.FMObjBorderPaneController;

public abstract class FMObjTab extends Tab {

	protected AnchorPane infoAnchorPane;
	protected FMObjBorderPaneController controller;

	protected FMObjTab() throws IOException {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../ui/FMObjBorderPane.fxml"));

		AnchorPane anchor = (AnchorPane) loader.load();

		AnchorPane.setTopAnchor(anchor, 0.0);
		AnchorPane.setLeftAnchor(anchor, 0.0);
		AnchorPane.setRightAnchor(anchor, 0.0);
		AnchorPane.setBottomAnchor(anchor, 0.0);

		this.controller = (FMObjBorderPaneController) loader.getController();

		setContent(anchor);

	}

	public AnchorPane getInfoAnchorPane() {
		return infoAnchorPane;
	}

	public abstract AnchorPane loadInfoAnchorPane() throws IOException;

}
