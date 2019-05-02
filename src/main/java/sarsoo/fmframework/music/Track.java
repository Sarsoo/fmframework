package sarsoo.fmframework.music;

import java.io.Serializable;
import java.util.ArrayList;

import org.w3c.dom.Document;

import sarsoo.fmframework.net.Network;
import sarsoo.fmframework.util.Reference;

public class Track extends FMObj implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Album album;
	protected Artist artist;
	protected int trackNumber;
	protected int duration;
	protected boolean isLoved;
	protected ArrayList<Tag> tagList;

	private int utsFirstListen;
	
	private Track(TrackBuilder builder) {
		
		this.name = builder.name;

		this.url = builder.url;

		this.listeners = builder.listeners;
		this.playCount = builder.playCount;
		this.userPlayCount = builder.userPlayCount;

		this.wiki = builder.wiki;

		this.mbid = builder.mbid;

		this.album = builder.album;
		this.artist = builder.artist;
		this.trackNumber = builder.trackNumber;
		this.duration = builder.duration;
		this.isLoved = builder.isLoved;
		this.tagList = builder.tagList;
		
	}

	public Artist getArtist() {
		return artist;
	}

	public ArrayList<Tag> getTags() {
		return tagList;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}

	@Override
	public String getMusicBrainzURL() {
		return "https://musicbrainz.org/artist/" + mbid;
	}

	public String getLyricsURL() {
		String trackName = name.replaceAll(" ", "-");
		String artistName = artist.getName().replaceAll(" ", "-");
		String urlString = String.format("http://genius.com/%s-%s-lyrics", artistName, trackName);
		return urlString;
	}

	public Album getAlbum() {
		return album;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj.getClass() != this.getClass())
			return false;

		Track track = (Track) obj;
		if (getName().equalsIgnoreCase(track.getName()))
			if (getArtist().equals(track.getArtist()))
				return true;

		return false;
	}


	public String toString() {
		if (album != null)
			return "Track: " + name + " - " + album.getName() + " - " + artist.getName();
		return "Track: " + name + " - " + artist.getName();

	}

	public int getUtsFirstListen() {
		return utsFirstListen;
	}

	public void setUtsFirstListen(int utsFirstListen) {
		this.utsFirstListen = utsFirstListen;
	}

	public static class TrackBuilder {

		protected String name;

		protected String url;

		protected int listeners;
		protected int playCount;
		protected int userPlayCount;

		protected Wiki wiki;

		protected String mbid;

		protected Album album;
		protected Artist artist;
		protected int trackNumber;
		protected int duration;
		protected boolean isLoved;
		protected ArrayList<Tag> tagList;

		private int utsFirstListen;

		public TrackBuilder(String name, Artist artist) {

			this.name = name;
			this.artist = artist;

		}

		public TrackBuilder setUrl(String url) {
			this.url = url;
			return this;
		}

		public TrackBuilder setListeners(int listeners) {
			this.listeners = listeners;
			return this;
		}

		public TrackBuilder setPlayCount(int playCount) {
			this.playCount = playCount;
			return this;
		}

		public TrackBuilder setUserPlayCount(int userPlayCount) {
			this.userPlayCount = userPlayCount;
			return this;
		}

		public TrackBuilder setWiki(Wiki wiki) {
			this.wiki = wiki;
			return this;
		}

		public TrackBuilder setMbid(String Mbid) {
			this.mbid = Mbid;
			return this;
		}

		public TrackBuilder setAlbum(Album album) {
			this.album = album;
			return this;
		}

		public TrackBuilder setArtist(Artist artist) {
			this.artist = artist;
			return this;
		}

		public TrackBuilder setTrackNumber(int trackNumber) {
			this.trackNumber = trackNumber;
			return this;
		}
		
		public TrackBuilder setDuration(int duration) {
			this.duration = duration;
			return this;
		}
		
		public TrackBuilder set(int userPlayCount) {
			this.userPlayCount = userPlayCount;
			return this;
		}

		public TrackBuilder setIsLoved(boolean isLoved) {
			this.isLoved = isLoved;
			return this;
		}
		
		public TrackBuilder setTagList(ArrayList<Tag> tagList) {
			this.tagList = tagList;
			return this;
		}

		public Track build() {
			return new Track(this);
		}
	}
}
