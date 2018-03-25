package sarsoo.fmframework.music;

import java.util.ArrayList;

import org.w3c.dom.Document;

import sarsoo.fmframework.net.Network;
import sarsoo.fmframework.parser.Parser;

public class Album extends FMObj{
	protected Artist artist;
	protected ArrayList<Tag> tagList;
	protected ArrayList<Track> trackList;
	
	public Album(String name) {
		super(name, null, null, 0, 0, 0, null);
		this.name = name;
	}
	
	public Album(String name, String url, String mbid, Artist artist, int listeners, int playCount, int userPlayCount, Wiki wiki) {
		super(name, url, mbid, listeners, playCount, userPlayCount, wiki);
		this.artist = artist;
	}
	
	public String toString() {
		return name + " - " + artist.getName();
		
	}
	
	public static Album getAlbum(String name, String artist, String username) {
		String url = Network.getAlbumInfoUrl(name, artist, username);
		Document response = Network.getResponse(url);
		Album album = Parser.parseAlbum(response);
		return album;
	}
	
}
