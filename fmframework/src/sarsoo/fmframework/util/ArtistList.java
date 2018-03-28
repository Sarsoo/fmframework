package sarsoo.fmframework.util;

import java.util.ArrayList;

import sarsoo.fmframework.gui.ArtistListView;
import sarsoo.fmframework.music.Artist;

public class ArtistList extends ArrayList<Artist> {

	private String groupName = null;
	
	public ArtistList() {
		super();
	}
	
	public ArtistList(String name) {
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
		ArtistListView view = new ArtistListView(this, title);
		view.setVisible(true);
	}

	public void view() {
		if(groupName != null) {
			ArtistListView view = new ArtistListView(this, getGroupName());
			view.setVisible(true);
		}else {
			ArtistListView view = new ArtistListView(this, "Artist List View");
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
