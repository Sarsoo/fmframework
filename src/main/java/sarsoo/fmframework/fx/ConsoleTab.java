package sarsoo.fmframework.fx;

import javafx.scene.control.Tab;

public class ConsoleTab extends Tab{
	
	public ConsoleTab(){

		setText("console");

//		AnchorPane pane = new AnchorPane();

//		AnchorPane.setTopAnchor(pane, 0.0);
//		AnchorPane.setLeftAnchor(pane, 0.0);
//		AnchorPane.setRightAnchor(pane, 0.0);
//		AnchorPane.setBottomAnchor(pane, 0.0);
//
//		setContent(pane);

		

		setContent(TextAreaConsole.getInstance().getTextArea());
		
		

	}

}
