package sarsoo.fmframework.fx;

import java.text.NumberFormat;
import java.util.Collections;
import java.util.Locale;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import sarsoo.fmframework.music.FMObj;
import sarsoo.fmframework.util.FMObjList;
import sarsoo.fmframework.util.Maths;
import sarsoo.fmframework.util.Reference;

public class FMObjListTab extends BorderPane{
	
	public FMObjListTab(FMObjList list) {
		getStylesheets().add("sarsoo/fmframework/fx/styles/FMObjListTab.css");
		
		Collections.sort(list);
		Collections.reverse(list);
		
		VBox stats = new VBox();
		GridPane statsPane = new GridPane();
		Label total = new Label(Integer.toString(list.getTotalUserScrobbles()));
		total.getStyleClass().add("totalScrobbles");
		statsPane.add(total, 0, 0);
		
		double percent = Maths.getPercentListening(list, Reference.getUserName());
		Label percentLabel = new Label(String.format("%.2f%%", percent));
		percentLabel.getStyleClass().add("percent");
		statsPane.add(percentLabel, 1, 0);
		
		stats.getChildren().add(statsPane);
		setBottom(stats);
		
		ScrollPane scroll = new ScrollPane();
		scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

		GridPane pane = new GridPane();
		scroll.setContent(pane);
		
		NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
		FMObj FMObj;
		int counter;
		for(counter = 0; counter < list.size(); counter++) {
			FMObj = list.get(counter);
			
			Label name = new Label(FMObj.getName());
			name.getStyleClass().add("nameLabel");
			
			Label scrobbles = new Label(numberFormat.format(FMObj.getUserPlayCount()));
			Label totalScrobbles = new Label(numberFormat.format(FMObj.getPlayCount()));
			totalScrobbles.getStyleClass().add("number");
			
			pane.add(name, 0, counter);
			pane.add(scrobbles, 1, counter);
			pane.add(totalScrobbles, 2, counter);
		}
		
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setPercentWidth(50);
		column1.setHgrow(Priority.ALWAYS);
		ColumnConstraints column2 = new ColumnConstraints();
		column2.setPercentWidth(25);
		column2.setHgrow(Priority.ALWAYS);
		pane.getColumnConstraints().add(column1);
		pane.getColumnConstraints().add(column2);
		pane.getColumnConstraints().add(column2);
		
		setCenter(scroll);
		
	}
	
}
