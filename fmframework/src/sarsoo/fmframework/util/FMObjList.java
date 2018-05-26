package sarsoo.fmframework.util;

import java.io.Serializable;
import java.util.ArrayList;

import sarsoo.fmframework.jframe.FMObjListView;
import sarsoo.fmframework.music.Album;
import sarsoo.fmframework.music.Artist;
import sarsoo.fmframework.music.FMObj;
import sarsoo.fmframework.music.Track;

public class FMObjList extends ArrayList<FMObj> implements Comparable<FMObjList>, Serializable {

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

		int totalScrobbles = stream().filter(t -> t.getClass() == Artist.class).mapToInt(FMObj::getUserPlayCount).sum();

		totalScrobbles += stream().filter(t -> {
			if (t.getClass() == Album.class) {

				Album album = (Album) t;

				if (contains(album.getArtist())) {
					return false;
				} else {
					return true;
				}
			} else {
				return false;
			}
		}).mapToInt(FMObj::getUserPlayCount).sum();

		totalScrobbles += stream().filter(t -> {
			if (t.getClass() == Track.class) {

				Track track = (Track) t;

				if (contains(track.getArtist())) {
					return false;
				} else {

					if (track.getAlbum() != null) {
						if (contains(track.getAlbum())) {
							return false;
						} else {
							return true;
						}
					} else {
						return true;
					}
				}

			} else {
				return false;
			}
		}).mapToInt(FMObj::getUserPlayCount).sum();

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
		stream().forEach(FMObj::refresh);
	}
}
