package sarsoo.fmframework.fx.chart;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import sarsoo.fmframework.util.FMObjList;
import sarsoo.fmframework.util.tagpool.TagPool;

public class GenrePieChart extends PieChart{
	
	protected int genreTotal;
	protected ObservableList<PieChart.Data> pieChartData;
	
	public GenrePieChart(String name, ArrayList<String> tagNames) {
		
		setStartAngle(90);
		setTitle(name);
		
		pieChartData = FXCollections.observableArrayList();
		
		TagPool tagPool = TagPool.getPool();

		ArrayList<FMObjList> tagObjs = new ArrayList<FMObjList>();
		
		int i;
		for(i = 0; i < tagNames.size(); i++){
			tagObjs.add(tagPool.getTag(tagNames.get(i)));
		}
		
		for(i = 0; i < tagObjs.size(); i++) {
			genreTotal += tagObjs.get(i).getTotalUserScrobbles();
		}
		
		for(i = 0; i < tagNames.size(); i++) {
			FMObjList list = tagObjs.get(i);
			System.out.println(list.getGroupName());
			pieChartData.add(new PieChart.Data(
					String.format("%s %d%%", list.getGroupName(),(int) list.getTotalUserScrobbles() * 100 / genreTotal), list.getTotalUserScrobbles()));
		}
		Collections.sort(pieChartData, new Comparator<PieChart.Data>() {

			@Override
			public int compare(Data arg0, Data arg1) {
				return (int) (arg1.getPieValue() - arg0.getPieValue());
			}
		});
		
		setData(pieChartData);
		
	}
	

}
