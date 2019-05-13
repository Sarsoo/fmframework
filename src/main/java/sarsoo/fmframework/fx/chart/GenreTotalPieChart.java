package sarsoo.fmframework.fx.chart;

import java.util.ArrayList;

import javafx.scene.chart.PieChart;
import sarsoo.fmframework.config.Config;
import sarsoo.fmframework.fm.FmUserNetwork;
import sarsoo.fmframework.fx.FmFramework;

public class GenreTotalPieChart extends GenrePieChart{
	
	public GenreTotalPieChart(String name, ArrayList<String> tagNames) {
		super(name, tagNames);
		
		getStylesheets().add("../styles/mainPane.css");
		getStyleClass().add("backGround");
		
		setTitle(name + " total");
		
		Config config = FmFramework.getSessionConfig();
		
		FmUserNetwork net = new FmUserNetwork(config.getValue("api_key"), config.getValue("username"));

		int totalScrobbles = net.getUserScrobbleCount();
		
		int other = totalScrobbles - genreTotal;
		pieChartData.add(new PieChart.Data(String.format("other %d%%", (int) other * 100 / totalScrobbles), other));
		
	}
	
}
