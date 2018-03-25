package sarsoo.fmframework.music;

import java.util.ArrayList;

public class Track extends FMObj{
	protected Album album;
	protected Artist artist;
	protected int trackNumber;
	protected int duration;
	protected boolean streamable;
	protected boolean isLoved;
	protected ArrayList<Tag> tagList;
	
	public Track(String name, String artist) {
		super(name, null, null, 0, 0, 0, null);
		this.artist = new Artist(artist);
	}
}
