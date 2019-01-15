package sarsoo.fmframework.fx.tab;

import java.io.IOException;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import sarsoo.fmframework.fx.controller.FMObjListPaneController;
import sarsoo.fmframework.util.FMObjList;
import javafx.fxml.FXMLLoader;

public class FMObjListTab extends Tab {

	public FMObjListTab(FMObjList list) throws IOException {

		setText(list.getGroupName());

		FXMLLoader loader = new FXMLLoader(getClass().getResource("ui/FMObjListPane.fxml"));

		AnchorPane pane = (AnchorPane) loader.load();

		AnchorPane.setTopAnchor(pane, 0.0);
		AnchorPane.setLeftAnchor(pane, 0.0);
		AnchorPane.setRightAnchor(pane, 0.0);
		AnchorPane.setBottomAnchor(pane, 0.0);

		setContent(pane);
		
		setText(list.getGroupName());

		FMObjListPaneController control = (FMObjListPaneController) loader.getController();

		control.populate(list);
		
		

	}
	
//	public FMObjListTab() throws IOException {
//
//		setText("List");
//
//		FXMLLoader loader = new FXMLLoader(getClass().getResource("ui/FMObjListPaneEdit.fxml"));
//
//		AnchorPane pane = (AnchorPane) loader.load();
//
//		AnchorPane.setTopAnchor(pane, 0.0);
//		AnchorPane.setLeftAnchor(pane, 0.0);
//		AnchorPane.setRightAnchor(pane, 0.0);
//		AnchorPane.setBottomAnchor(pane, 0.0);
//		
////		BorderPane
//
//		setContent(pane);
//
//		FMObjListPaneEditController control = (FMObjListPaneEditController) loader.getController();		
//		
//
//	}

}
