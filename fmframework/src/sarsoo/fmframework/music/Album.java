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
	
}
