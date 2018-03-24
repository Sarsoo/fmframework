package sarsoo.fmframework.music;

import java.util.ArrayList;

public class Artist extends FMObj{
	protected int listeners;
	protected int plays;
	protected boolean streamable;
	protected ArrayList<Album> albums;
	protected ArrayList<Track> tracks;
	protected ArrayList<Artist> similarArtists;
	protected ArrayList<Tag> tagList;
	protected Wiki wiki;
	
	public Artist(String name) {
		this.name = name;
	}
}
