package sarsoo.fmframework.fx.pane;

import java.io.IOException;
import java.util.ArrayList;

import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import sarsoo.fmframework.fx.chart.GenreTotalPieChart;

public class PieChartTitledPane extends TitledPane {

	public PieChartTitledPane(String genreName, ArrayList<String> tagNames) throws IOException {

		setText(genreName);

		AnchorPane rootAnchor = new AnchorPane();
//		AnchorPane.setTopAnchor(rootAnchor, 0.0);
//		AnchorPane.setLeftAnchor(rootAnchor, 0.0);
//		AnchorPane.setRightAnchor(rootAnchor, 0.0);
//		AnchorPane.setBottomAnchor(rootAnchor, 0.0);
		
//		GridPane gridPane = new GridPane();
//		ColumnConstraints columnConstraint = new ColumnConstraints();
//		columnConstraint.setHgrow(Priority.ALWAYS);

//		RowConstraints rowConstraint = new RowConstraints();
//		rowConstraint.setPercentHeight(100);
		
//		gridPane.getColumnConstraints().add(columnConstraint);
//		gridPane.getRowConstraints().add(rowConstraint);
		setContent(rootAnchor);
		
		PieChart pieChartTotal = new GenreTotalPieChart(genreName, tagNames);
		
		AnchorPane.setTopAnchor(pieChartTotal, 0.0);
		AnchorPane.setLeftAnchor(pieChartTotal, 0.0);
		AnchorPane.setRightAnchor(pieChartTotal, 0.0);
		AnchorPane.setBottomAnchor(pieChartTotal, 0.0);

//		PieChart pieChartTotal = new GenreTotalPieChart(genreName, tagNames);
		rootAnchor.getChildren().add(pieChartTotal);
//		gridPane.add(pieChartTotal, 0, 1);
		

	}

}
