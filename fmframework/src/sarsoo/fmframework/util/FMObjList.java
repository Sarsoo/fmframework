package sarsoo.fmframework.util;

import java.io.Serializable;
import java.util.ArrayList;

import sarsoo.fmframework.jframe.FMObjListView;
import sarsoo.fmframework.music.Album;
import sarsoo.fmframework.music.Artist;
import sarsoo.fmframework.music.FMObj;
import sarsoo.fmframework.music.Track;

public class FMObjList extends ArrayList<FMObj> implements Comparable<FMObjList>, Serializable{

	private static final long serialVersionUID = 1L;

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
			FMObj obj = get(counter);

			if (obj.getClass() == Artist.class)
				totalScrobbles += obj.getUserPlayCount();
			
			else if (obj.getClass() == Track.class) {
				Track track = (Track) obj;
				
				Artist artist = track.getArtist();
				
				boolean found = false;
				int counter2;
				for (counter2 = 0; counter2 < size(); counter2++) {
					if (artist.equals(get(counter2))) {
						found = true;
						break;
					}
				}
				if (!found) {
					totalScrobbles += obj.getUserPlayCount();
				}
				
//				if (!super.contains(track.getArtist())) {
//					Album album = track.getAlbum();
//					if (album != null) {
//						if (!super.contains(album))
//							totalScrobbles += obj.getUserPlayCount();
//					}
//				}
			}
			else if (obj.getClass() == Album.class) {
				Album album = (Album) obj;

				Artist artist = album.getArtist();

				boolean found = false;
				int counter2;
				for (counter2 = 0; counter2 < size(); counter2++) {
					if (artist.equals(get(counter2))) {
						found = true;
						break;
					}
				}
				if (!found) {
					totalScrobbles += obj.getUserPlayCount();
				}
			}
		}
		return totalScrobbles;
	}

	public void view(String title) {
		FMObjListView view = new FMObjListView(this, title);
		view.setVisible(true);
	}

	public void view() {
		if (groupName != null) {
			FMObjListView view = new FMObjListView(this, getGroupName());
			view.setVisible(true);
		} else {
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

	@Override
	public int compareTo(FMObjList list) {
		return getTotalUserScrobbles() - list.getTotalUserScrobbles();
	}
	
	public void refresh() {
		int counter;
		for(counter = 0; counter < size(); counter++) {
			get(counter).refresh();
		}
		
	}
}
