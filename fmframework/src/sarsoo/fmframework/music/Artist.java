package sarsoo.fmframework.music;

import java.io.Serializable;
import java.util.ArrayList;

import org.w3c.dom.Document;

import sarsoo.fmframework.net.Network;
import sarsoo.fmframework.net.TestCall;
import sarsoo.fmframework.net.URLBuilder;
//import sarsoo.fmframework.net.TestCall;
import sarsoo.fmframework.parser.Parser;
import sarsoo.fmframework.util.Reference;

public class Artist extends FMObj implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// protected boolean streamable;
	// protected boolean onTour;
	protected ArrayList<Album> albums;
	protected ArrayList<Artist> similarArtists;
	protected ArrayList<Tag> tagList;

	public Artist(String name) {
		super(name, null, null, 0, 0, 0, null);
	}

	public Artist(String name, String url, String mbid, int listeners, int playCount, int userPlayCount, Wiki wiki) {
		super(name, url, mbid, listeners, playCount, userPlayCount, wiki);
	}

	public static Artist getArtist(String name, String username) {
		String url = URLBuilder.getArtistInfoUrl(name, username);
//		TestCall.test(url);
		Document response = Network.getResponse(url);
		if (response != null) {
			Artist artist = Parser.parseArtist(response);
			return artist;
		}
		return null;
	}

	public static Artist getArtistByMbid(String mbid, String username) {
		String url = URLBuilder.getArtistInfoMbidUrl(mbid, username);
		Document response = Network.getResponse(url);
		Artist artist = Parser.parseArtist(response);
		return artist;
	}

	public ArrayList<Album> getAlbum() {
		return albums;
	}

	public ArrayList<Artist> getSimilarArtists() {
		return similarArtists;
	}

	public ArrayList<Tag> getTags() {
		return tagList;
	}

	@Override
	public String getMusicBrainzURL() {
		return "https://musicbrainz.org/artist/" + mbid;
	}

	public String getRymURL() {
		return "https://rateyourmusic.com/artist/" + getName().replaceAll(" ", "_").toLowerCase();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj.getClass() != this.getClass())
			return false;

		Artist artist = (Artist) obj;
		if (getName().equalsIgnoreCase(artist.getName()))
			return true;

		return false;
	}

	public String toString() {
		return "Artist: " + name;
	}
	
	@Override
	public void refresh() {
		Artist artist = Artist.getArtist(name, Reference.getUserName());
		
		this.listeners = artist.listeners;
		this.userPlayCount = artist.userPlayCount;
		this.playCount = artist.playCount;
		this.wiki = artist.wiki;
		this.mbid = artist.mbid;
		
		
	}

}
