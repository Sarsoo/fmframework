package sarsoo.fmframework.fx.tab;

import java.io.IOException;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import sarsoo.fmframework.fx.controller.FMObjListPaneController;
import sarsoo.fmframework.util.FMObjList;
import javafx.fxml.FXMLLoader;

public class FMObjListTab extends Tab {

	public FMObjListTab(FMObjList list) {

		setText(list.getGroupName());

		FXMLLoader loader = new FXMLLoader(getClass().getResource("../ui/FMObjListPane.fxml"));

		AnchorPane pane;
		try {
			pane = (AnchorPane) loader.load();
			
			AnchorPane.setTopAnchor(pane, 0.0);
			AnchorPane.setLeftAnchor(pane, 0.0);
			AnchorPane.setRightAnchor(pane, 0.0);
			AnchorPane.setBottomAnchor(pane, 0.0);

			setContent(pane);
			
			setText(list.getGroupName());

			FMObjListPaneController control = (FMObjListPaneController) loader.getController();

			control.populate(list);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
