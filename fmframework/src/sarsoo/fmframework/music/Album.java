package sarsoo.fmframework.music;

import java.util.ArrayList;

public class Album extends FMObj{
	protected Artist artist;
	protected int listeners;
	protected int playCount;
	protected int userPlayCount;
	protected ArrayList<Tag> tagList;
	protected ArrayList<Track> trackList;
	
	public Album(String name, String url, String mbid, Artist artist, int listeners, int playCount, int userPlayCount) {
		this.name = name;
		this.url = url;
		this.mbid = mbid;
		this.artist = artist;
		this.listeners = listeners;
		this.playCount = playCount;
		this.userPlayCount = userPlayCount;
	}
	
	public String toString() {
		return name + " - " + artist.getName();
		
	}
	
}
