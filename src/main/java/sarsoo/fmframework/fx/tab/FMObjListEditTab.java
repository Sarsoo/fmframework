package sarsoo.fmframework.fx.tab;

import java.io.IOException;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import sarsoo.fmframework.fx.controller.FMObjListPaneEditController;
import sarsoo.fmframework.util.FMObjList;
import javafx.fxml.FXMLLoader;

public class FMObjListEditTab extends Tab {

	public FMObjListEditTab() {

		setText("List");

		FXMLLoader loader = new FXMLLoader(getClass().getResource("../ui/FMObjListEditPane.fxml"));

		AnchorPane pane;
		
		try {
			pane = (AnchorPane) loader.load();
			
			AnchorPane.setTopAnchor(pane, 0.0);
			AnchorPane.setLeftAnchor(pane, 0.0);
			AnchorPane.setRightAnchor(pane, 0.0);
			AnchorPane.setBottomAnchor(pane, 0.0);

//			BorderPane

			setText("list");

			setContent(pane);

			FMObjListPaneEditController control = (FMObjListPaneEditController) loader.getController();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public FMObjListEditTab(FMObjList list) {

		if (list.getGroupName() != null) {
			setText(list.getGroupName());
		}else {
			setText("list");
		}

		FXMLLoader loader = new FXMLLoader(getClass().getResource("../ui/FMObjListEditPane.fxml"));

		AnchorPane pane;

		try {
			pane = (AnchorPane) loader.load();
			
			AnchorPane.setTopAnchor(pane, 0.0);
			AnchorPane.setLeftAnchor(pane, 0.0);
			AnchorPane.setRightAnchor(pane, 0.0);
			AnchorPane.setBottomAnchor(pane, 0.0);

//			BorderPane

			setContent(pane);

			FMObjListPaneEditController control = (FMObjListPaneEditController) loader.getController();
			control.setList(list);
			control.refresh();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
