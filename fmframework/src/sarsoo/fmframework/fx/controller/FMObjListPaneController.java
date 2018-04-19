package sarsoo.fmframework.fx.controller;

import java.text.NumberFormat;
import java.util.Collections;
import java.util.Locale;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import sarsoo.fmframework.music.FMObj;
import sarsoo.fmframework.util.FMObjList;
import sarsoo.fmframework.util.Maths;
import sarsoo.fmframework.util.Reference;
import javafx.scene.layout.*;

public class FMObjListPaneController {
	
	@FXML
	private Label labelTotalScrobbles;

	@FXML
	private Label labelPercent;

	@FXML
	private GridPane gridPaneFMObjs;
	
	public void populate(FMObjList list) {
		double percent = Maths.getPercentListening(list, Reference.getUserName());
		NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
		
		labelTotalScrobbles.setText("Σ" + list.getTotalUserScrobbles());
		labelPercent.setText(String.format("%.2f%%", percent));
		
		Collections.sort(list);
		Collections.reverse(list);
		
		int counter;
		for(counter = 0; counter < list.size(); counter++) {
			
			FMObj obj = list.get(counter);
			
			Label name = new Label(obj.getName().toLowerCase());
			Label userScrobbles = new Label(numberFormat.format(obj.getUserPlayCount()));
			Label totalScrobbles = new Label(numberFormat.format(obj.getPlayCount()));
			
			gridPaneFMObjs.add(name, 0, counter);
			gridPaneFMObjs.add(userScrobbles, 1, counter);
			gridPaneFMObjs.add(totalScrobbles, 2, counter);
			
			
		}
		
	}

}
