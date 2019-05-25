package sarsoo.fmframework.util;

import java.io.Serializable;
import java.util.ArrayList;

import sarsoo.fmframework.cache.Cacheable;
import sarsoo.fmframework.config.Config;
import sarsoo.fmframework.error.ApiCallException;
import sarsoo.fmframework.fm.FmUserNetwork;
import sarsoo.fmframework.fx.FmFramework;
import sarsoo.fmframework.music.Album;
import sarsoo.fmframework.music.Artist;
import sarsoo.fmframework.music.FMObj;
import sarsoo.fmframework.music.Track;

public class FMObjList extends ArrayList<FMObj> implements Comparable<FMObjList>, Serializable, Cacheable {

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

	@Override
	public boolean matches(Object obj) {

		if (obj instanceof String) {
			String stringed = (String) obj;
			if (getGroupName().equalsIgnoreCase(stringed))
				return true;
		}

		if (obj instanceof FMObjList) {
			FMObjList list = (FMObjList) obj;
			if (getGroupName().equalsIgnoreCase(list.getGroupName()))
				return true;
		}
		
		return false;
	}

	public void refresh() {
		Config config = FmFramework.getSessionConfig();

		FmUserNetwork net = new FmUserNetwork(config.getValue("api_key"), config.getValue("username"));
		stream().forEach(item -> {
			try {
				net.refresh(item);
			} catch (ApiCallException e) {}
		});
	}
}
