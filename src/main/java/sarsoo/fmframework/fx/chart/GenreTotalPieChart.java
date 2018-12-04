package sarsoo.fmframework.fx.chart;

import java.util.ArrayList;

import javafx.scene.chart.PieChart;
import sarsoo.fmframework.fm.FmUserNetwork;
import sarsoo.fmframework.net.Key;
import sarsoo.fmframework.util.Reference;

public class GenreTotalPieChart extends GenrePieChart{
	
	public GenreTotalPieChart(String name, ArrayList<String> tagNames) {
		super(name, tagNames);
		
		setTitle(name + " total");
		
		FmUserNetwork net = new FmUserNetwork(Key.getKey(), Reference.getUserName());

		int totalScrobbles = net.getUserScrobbleCount();
		
		int other = totalScrobbles - genreTotal;
		pieChartData.add(new PieChart.Data(String.format("other %d%%", (int) other * 100 / totalScrobbles), other));
		
	}
	
}
