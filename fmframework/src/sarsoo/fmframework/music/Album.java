package sarsoo.fmframework.music;

import java.util.ArrayList;

public class Album extends FMObj{
	protected int id;
	protected String date;
	protected Artist artist;
	protected int listeners;
	protected int playCount;
	protected ArrayList<Tag> tagList;
	protected ArrayList<Track> trackList;
	
	public Album(String name, String url, String mbid, int id, String artist) {
		this.name = name;
		this.url = url;
		this.mbid = mbid;
		this.id = id;
		this.artist = new Artist(artist);
	}
	
}
