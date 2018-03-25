package sarsoo.fmframework.music;

import java.util.ArrayList;

import org.w3c.dom.Document;

import sarsoo.fmframework.net.Network;
import sarsoo.fmframework.parser.Parser;

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
	
	public Track(String name, String url, String mbid, int listeners, int playCount, int userPlayCount, Wiki wiki) {
		super(name, url, mbid, listeners, playCount, userPlayCount, wiki);
	}
	
	public static Track getTrack(String name, String artist, String username) {
		String url = Network.getTrackInfoUrl(name, artist, username);
		Document response = Network.getResponse(url);
		Track track = Parser.parseTrack(response);
		return track;
	}
	
	public Artist getArtist() {
		return artist;
	}
	
	public ArrayList<Tag> getTags(){
		return tagList;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj.getClass() != this.getClass()) return false;
		
		Track track = (Track)obj;
		if(getName() == track.getName())
			if(getArtist().equals(track.getArtist()))
				return true;
		
		return false;
	}		
}
