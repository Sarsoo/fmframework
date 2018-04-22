package sarsoo.fmframework.fx;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.Locale;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import sarsoo.fmframework.fx.controller.FMObjListPaneController;
import sarsoo.fmframework.fx.controller.FMObjListPaneEditController;
import sarsoo.fmframework.music.FMObj;
import sarsoo.fmframework.util.FMObjList;
import sarsoo.fmframework.util.Maths;
import sarsoo.fmframework.util.Reference;

import javafx.fxml.FXML;
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

		FMObjListPaneController control = (FMObjListPaneController) loader.getController();

		control.populate(list);
		
		

	}
	
	public FMObjListTab() throws IOException {

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
		

	}

}
