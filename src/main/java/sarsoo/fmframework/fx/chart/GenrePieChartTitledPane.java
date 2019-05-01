package sarsoo.fmframework.fx.chart;

import java.io.IOException;
import java.util.ArrayList;

import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class GenrePieChartTitledPane extends TitledPane {

	public GenrePieChartTitledPane(String genreName, ArrayList<String> tagNames) throws IOException {

		setText(genreName);

		AnchorPane rootAnchor = new AnchorPane();
//		AnchorPane.setTopAnchor(rootAnchor, 0.0);
//		AnchorPane.setLeftAnchor(rootAnchor, 0.0);
//		AnchorPane.setRightAnchor(rootAnchor, 0.0);
//		AnchorPane.setBottomAnchor(rootAnchor, 0.0);
		
		GridPane gridPane = new GridPane();
		ColumnConstraints constraint = new ColumnConstraints();
		constraint.setHgrow(Priority.ALWAYS);
		
		RowConstraints rowConstraint = new RowConstraints();
		rowConstraint.setPercentHeight(50);
		
		RowConstraints rowConstraint2 = new RowConstraints();
		rowConstraint2.setPercentHeight(50);
		
		gridPane.getColumnConstraints().add(constraint);
		gridPane.getRowConstraints().addAll(rowConstraint, rowConstraint2);;
		setContent(rootAnchor);
		
		AnchorPane.setTopAnchor(gridPane, 0.0);
		AnchorPane.setLeftAnchor(gridPane, 0.0);
		AnchorPane.setRightAnchor(gridPane, 0.0);
		AnchorPane.setBottomAnchor(gridPane, 0.0);

		rootAnchor.getChildren().add(gridPane);

		PieChart pieChartTotal = new GenreTotalPieChart(genreName, tagNames);
		PieChart pieChartGenre = new GenrePieChart(genreName, tagNames);

		gridPane.add(pieChartGenre, 0, 0);
		gridPane.add(pieChartTotal, 0, 1);
		

	}

}
