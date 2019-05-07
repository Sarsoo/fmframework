package sarsoo.fmframework.fx.tab;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import sarsoo.fmframework.fx.controller.GenrePieChartPaneController;

public class GenrePieChartTab extends Tab{
	
	public GenrePieChartTab() {

		setText("genre pie");

		FXMLLoader loader = new FXMLLoader(getClass().getResource("../ui/GenrePieChartPane.fxml"));

		AnchorPane pane;
		try {
			pane = (AnchorPane) loader.load();
			
			AnchorPane.setTopAnchor(pane, 0.0);
			AnchorPane.setLeftAnchor(pane, 0.0);
			AnchorPane.setRightAnchor(pane, 0.0);
			AnchorPane.setBottomAnchor(pane, 0.0);

			setContent(pane);

			GenrePieChartPaneController control = (GenrePieChartPaneController) loader.getController();

			//control.populate();
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		
	}
}
