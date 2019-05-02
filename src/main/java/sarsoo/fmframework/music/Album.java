package sarsoo.fmframework.music;

import java.io.Serializable;
import java.util.ArrayList;

public class Album extends FMObj implements Serializable{

	private static final long serialVersionUID = 1L;
	protected Artist artist;
	protected ArrayList<Tag> tagList;
	protected ArrayList<Track> trackList;

	private Album(AlbumBuilder builder) {
		this.name = builder.name;
		this.artist = builder.artist;
		
		this.url = builder.url;
		
		this.listeners = builder.listeners;
		this.playCount = builder.playCount;
		this.userPlayCount = builder.userPlayCount;
		
		this.wiki = builder.wiki;
		
		this.mbid = builder.mbid;
		
		this.tagList = builder.tagList;
		this.trackList = builder.trackList;
		
		
	}
	
	public Artist getArtist() {
		return artist;
	}

	public ArrayList<Tag> getTags() {
		return tagList;
	}

	public String getRymURL() {
		return "https://rateyourmusic.com/release/album/" + getArtist().getName().replaceAll(" ", "_").toLowerCase()
				+ "/" + getName().replaceAll(" ", "_").toLowerCase();
	}

	@Override
	public String getMusicBrainzURL() {
		return "https://musicbrainz.org/release/" + mbid;

	}

	@Override
	public boolean equals(Object obj) {
		if (obj.getClass() != this.getClass())
			return false;

		Album album = (Album) obj;
		if (getName().equalsIgnoreCase(album.getName()))
			if (getArtist().equals(album.getArtist()))
				return true;

		return false;
	}

	public String toString() {
		return "Album: " + name + " - " + artist.getName();

	}
	
	public static class AlbumBuilder{
		
		protected String name;
		protected Artist artist;
		
		protected String url;
		
		protected int listeners;
		protected int playCount;
		protected int userPlayCount;
		
		protected Wiki wiki;
		
		protected String mbid;
		
		protected ArrayList<Tag> tagList;
		protected ArrayList<Track> trackList;
		
		
		public AlbumBuilder(String name, Artist artist) {
			
			this.name = name;
			this.artist = artist;
		}
		
		public AlbumBuilder setUrl(String url) {
			this.url = url;
			return this;
		}
		
		public AlbumBuilder setListeners(int listeners) {
			this.listeners = listeners;
			return this;
		}
		
		public AlbumBuilder setPlayCount(int playCount) {
			this.playCount = playCount;
			return this;
		}
		
		public AlbumBuilder setUserPlayCount(int userPlayCount) {
			this.userPlayCount = userPlayCount;
			return this;
		}
		
		public AlbumBuilder setWiki(Wiki wiki) {
			this.wiki = wiki;
			return this;
		}
		
		public AlbumBuilder setMbid(String Mbid) {
			this.mbid = Mbid;
			return this;
		}
		
		public AlbumBuilder setTagList(ArrayList<Tag> tagList) {
			this.tagList = tagList;
			return this;
		}
		
		public AlbumBuilder setTrackList(ArrayList<Track> trackList) {
			this.trackList = trackList;
			return this;
		}
		
		public Album build() {
			return new Album(this);
		}
	}
}
