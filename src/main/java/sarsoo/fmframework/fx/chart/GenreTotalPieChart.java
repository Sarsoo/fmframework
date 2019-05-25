package sarsoo.fmframework.fx.chart;

import java.util.ArrayList;

import javafx.scene.chart.PieChart;
import sarsoo.fmframework.config.Config;
import sarsoo.fmframework.error.ApiCallException;
import sarsoo.fmframework.fm.FmUserNetwork;
import sarsoo.fmframework.fx.FmFramework;

public class GenreTotalPieChart extends GenrePieChart{
	
	public GenreTotalPieChart(String name, ArrayList<String> tagNames) {
		super(name, tagNames);
		
		getStyleClass().add("backGround");
		
		setTitle(name + " total");
		
		Config config = FmFramework.getSessionConfig();
		
		FmUserNetwork net = new FmUserNetwork(config.getValue("api_key"), config.getValue("username"));

		int totalScrobbles;
		try {
			totalScrobbles = net.getUserScrobbleCount();
		} catch (ApiCallException e) {
			totalScrobbles = 0;
		}
		
		int other = totalScrobbles - genreTotal;
		pieChartData.add(new PieChart.Data(String.format("other %d%%", (int) other * 100 / totalScrobbles), other));
		
	}
	
}
