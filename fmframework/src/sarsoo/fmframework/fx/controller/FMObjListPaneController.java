package sarsoo.fmframework.fx.controller;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.Locale;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import sarsoo.fmframework.fx.AlbumTab;
import sarsoo.fmframework.fx.ArtistTab;
import sarsoo.fmframework.fx.FmFramework;
import sarsoo.fmframework.music.Artist;
import sarsoo.fmframework.music.FMObj;
import sarsoo.fmframework.util.FMObjList;
import sarsoo.fmframework.util.Getter;
import sarsoo.fmframework.util.Maths;
import sarsoo.fmframework.util.Reference;
import javafx.scene.layout.*;
import javafx.scene.chart.*;

public class FMObjListPaneController {

	@FXML
	private Label labelTotalScrobbles;

	@FXML
	private Label labelPercent;

	@FXML
	private GridPane gridPaneFMObjs;
	
	@FXML
	private PieChart pieChart;
	
	private FMObjList list;

	public void populate(FMObjList list) {
		this.list = list;
		
		double percent = Maths.getPercentListening(list, Reference.getUserName());
		NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);

		labelTotalScrobbles.setText("Σ " + list.getTotalUserScrobbles());
		labelPercent.setText(String.format("%.2f%%", percent));

		Collections.sort(list);
		Collections.reverse(list);

		int counter;
		for (counter = 0; counter < list.size(); counter++) {

			FMObj obj = list.get(counter);

			Label name = new Label(obj.getName().toLowerCase());
			
			name.getStyleClass().add("nameLabel");
			
			name.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<Event>() {

				@Override
				public void handle(Event event) {

					try {
						FmFramework.getController().addTab(new ArtistTab((Artist) obj));
					} catch (IOException e) {

						e.printStackTrace();
					}

				}

			});

			Label userScrobbles = new Label(numberFormat.format(obj.getUserPlayCount()));
			Label totalScrobbles = new Label(numberFormat.format(obj.getPlayCount()));

			gridPaneFMObjs.add(name, 0, counter);
			gridPaneFMObjs.add(userScrobbles, 1, counter);
			gridPaneFMObjs.add(totalScrobbles, 2, counter);

		}
		
		
		
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
				new PieChart.Data(list.getGroupName(), list.getTotalUserScrobbles()),
				new PieChart.Data("other", Getter.getScrobbles(Reference.getUserName()) - list.getTotalUserScrobbles()));
		pieChart.setData(pieChartData);

	}
	
	@FXML
	protected void handleRefresh(ActionEvent event) {
		
		list = Getter.getUserTag(Reference.getUserName(), list.getGroupName());		
		
		
		double percent = Maths.getPercentListening(list, Reference.getUserName());
		NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);

		labelTotalScrobbles.setText("Σ " + list.getTotalUserScrobbles());
		labelPercent.setText(String.format("%.2f%%", percent));

		Collections.sort(list);
		Collections.reverse(list);
		
		gridPaneFMObjs.getChildren().clear();

		int counter;
		for (counter = 0; counter < list.size(); counter++) {

			FMObj obj = list.get(counter);

			Label name = new Label(obj.getName().toLowerCase());
			
			name.getStyleClass().add("nameLabel");
			
			name.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<Event>() {

				@Override
				public void handle(Event event) {

					try {
						FmFramework.getController().addTab(new ArtistTab((Artist) obj));
					} catch (IOException e) {

						e.printStackTrace();
					}

				}

			});

			Label userScrobbles = new Label(numberFormat.format(obj.getUserPlayCount()));
			Label totalScrobbles = new Label(numberFormat.format(obj.getPlayCount()));

			gridPaneFMObjs.add(name, 0, counter);
			gridPaneFMObjs.add(userScrobbles, 1, counter);
			gridPaneFMObjs.add(totalScrobbles, 2, counter);

		}
		
		
		
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
				new PieChart.Data(list.getGroupName(), list.getTotalUserScrobbles()),
				new PieChart.Data("other", Getter.getScrobbles(Reference.getUserName()) - list.getTotalUserScrobbles()));
		pieChart.setData(pieChartData);
	}

}
