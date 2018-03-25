package sarsoo.fmframework.music;

import java.util.ArrayList;

public class Artist extends FMObj{
	protected boolean streamable;
	protected boolean onTour;
	protected ArrayList<Album> albums;
	protected ArrayList<Track> tracks;
	protected ArrayList<Artist> similarArtists;
	protected ArrayList<Tag> tagList;
	
	public Artist(String name) {
		super(name, null, null, 0, 0, 0, null);
	}
	
	public String toString() {
		return name;
	}
}
