package sarsoo.fmframework.music;

import java.util.ArrayList;

import org.w3c.dom.Document;

import sarsoo.fmframework.net.Network;
import sarsoo.fmframework.parser.Parser;

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
	
	public Artist(String name, String url, String mbid, int listeners, int playCount, int userPlayCount, boolean streamable, boolean onTour, Wiki wiki) {
		super(name, url, mbid, listeners, playCount, userPlayCount, wiki);
	}
	
	public String toString() {
		return name;
	}
	
	public static Artist getArtist(String name, String username) {
		String url = Network.getArtistInfoUrl(name, username);
		Document response = Network.getResponse(url);
		Artist artist = Parser.parseArtist(response);
		return artist;
	}
}
