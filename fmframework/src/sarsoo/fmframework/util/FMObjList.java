package sarsoo.fmframework.util;

import java.util.ArrayList;

import sarsoo.fmframework.gui.FMObjListView;
import sarsoo.fmframework.music.Artist;
import sarsoo.fmframework.music.FMObj;

public class FMObjList extends ArrayList<FMObj> {

	private String groupName = null;
	
	public FMObjList() {
		super();
	}
	
	public FMObjList(String name) {
		super();
		this.groupName = name;
	}

	public int getTotalUserScrobbles() {
		int counter;
		int totalScrobbles = 0;
		for (counter = 0; counter < size(); counter++) {
			totalScrobbles += get(counter).getUserPlayCount();
		}
		return totalScrobbles;
	}

	public void view(String title) {
		FMObjListView view = new FMObjListView(this, title);
		view.setVisible(true);
	}

	public void view() {
		if(groupName != null) {
			FMObjListView view = new FMObjListView(this, getGroupName());
			view.setVisible(true);
		}else {
			FMObjListView view = new FMObjListView(this, "List View");
			view.setVisible(true);
		}
	}
	
	public String getGroupName() {
		return groupName;
	}
	
	public void setGroupName(String name) {
		this.groupName = name;
	}
}
