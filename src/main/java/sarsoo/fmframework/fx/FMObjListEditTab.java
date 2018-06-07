package sarsoo.fmframework.fx;

import java.io.IOException;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import sarsoo.fmframework.fx.controller.FMObjListPaneEditController;
import sarsoo.fmframework.util.FMObjList;
import javafx.fxml.FXMLLoader;

public class FMObjListEditTab extends Tab {
	
	public FMObjListEditTab() throws IOException {

		setText("List");

		FXMLLoader loader = new FXMLLoader(getClass().getResource("ui/FMObjListPaneEdit.fxml"));

		AnchorPane pane = (AnchorPane) loader.load();

		AnchorPane.setTopAnchor(pane, 0.0);
		AnchorPane.setLeftAnchor(pane, 0.0);
		AnchorPane.setRightAnchor(pane, 0.0);
		AnchorPane.setBottomAnchor(pane, 0.0);
		
//		BorderPane

		setText("list");
		
		setContent(pane);

		FMObjListPaneEditController control = (FMObjListPaneEditController) loader.getController();		
		

	}
	
	public FMObjListEditTab(FMObjList list) throws IOException {

		setText("List");

		FXMLLoader loader = new FXMLLoader(getClass().getResource("ui/FMObjListPaneEdit.fxml"));

		AnchorPane pane = (AnchorPane) loader.load();

		AnchorPane.setTopAnchor(pane, 0.0);
		AnchorPane.setLeftAnchor(pane, 0.0);
		AnchorPane.setRightAnchor(pane, 0.0);
		AnchorPane.setBottomAnchor(pane, 0.0);
		
//		BorderPane

		setContent(pane);

		FMObjListPaneEditController control = (FMObjListPaneEditController) loader.getController();		
		control.setList(list);
		control.refresh();
	}

}
