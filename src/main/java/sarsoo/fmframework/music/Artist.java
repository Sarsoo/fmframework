package sarsoo.fmframework.music;

import java.io.Serializable;
import java.util.ArrayList;

import org.w3c.dom.Document;

import sarsoo.fmframework.net.Network;
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

	@Deprecated
	public Artist(String name) {
		super(name, null, null, 0, 0, 0, null);
	}

	@Deprecated
	public Artist(String name, String url, String mbid, int listeners, int playCount, int userPlayCount, Wiki wiki) {
		super(name, url, mbid, listeners, playCount, userPlayCount, wiki);
	}
	
	private Artist(ArtistBuilder builder) {
		this.name = builder.name;
		
		this.url = builder.url;
		
		this.listeners = builder.listeners;
		this.playCount = builder.playCount;
		this.userPlayCount = builder.userPlayCount;
		
		this.wiki = builder.wiki;
		
		this.mbid = builder.mbid;
		
		this.albums = builder.albums;
		this.similarArtists = builder.similarArtists;
		this.tagList = builder.tagList;
		
		
	}

	@Deprecated
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

	@Deprecated
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
	
	@Deprecated
	@Override
	public void refresh() {
		Artist artist = Artist.getArtist(name, Reference.getUserName());
		
		this.listeners = artist.listeners;
		this.userPlayCount = artist.userPlayCount;
		this.playCount = artist.playCount;
		this.wiki = artist.wiki;
		this.mbid = artist.mbid;
		
		
	}
	
public static class ArtistBuilder{
		
		protected String name;
		
		protected String url;
		
		protected int listeners;
		protected int playCount;
		protected int userPlayCount;
		
		protected Wiki wiki;
		
		protected String mbid;
		
		protected ArrayList<Album> albums;
		protected ArrayList<Artist> similarArtists;
		protected ArrayList<Tag> tagList;
		
		
		public ArtistBuilder(String name) {
			
			this.name = name;
			
		}
		
		public ArtistBuilder setUrl(String url) {
			this.url = url;
			return this;
		}
		
		public ArtistBuilder setListeners(int listeners) {
			this.listeners = listeners;
			return this;
		}
		
		public ArtistBuilder setPlayCount(int playCount) {
			this.playCount = playCount;
			return this;
		}
		
		public ArtistBuilder setUserPlayCount(int userPlayCount) {
			this.userPlayCount = userPlayCount;
			return this;
		}
		
		public ArtistBuilder setWiki(Wiki wiki) {
			this.wiki = wiki;
			return this;
		}
		
		public ArtistBuilder setMbid(String Mbid) {
			this.mbid = Mbid;
			return this;
		}
		
		public ArtistBuilder setAlbums(ArrayList<Album> albums) {
			this.albums = albums;
			return this;
		}
		
		public ArtistBuilder setSimilarArtists(ArrayList<Artist> similarArtists) {
			this.similarArtists = similarArtists;
			return this;
		}
		
		public ArtistBuilder setTagList(ArrayList<Tag> tagList) {
			this.tagList = tagList;
			return this;
		}
		
		public Artist build() {
			return new Artist(this);
		}
	}

}
