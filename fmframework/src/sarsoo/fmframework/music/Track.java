package sarsoo.fmframework.music;

import java.util.ArrayList;

import org.w3c.dom.Document;

import sarsoo.fmframework.jframe.TrackView;
import sarsoo.fmframework.net.Network;
import sarsoo.fmframework.net.URLBuilder;
//import sarsoo.fmframework.net.TestCall;
import sarsoo.fmframework.parser.Parser;
import sarsoo.fmframework.util.Reference;

public class Track extends FMObj {
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

	public Track(String name, String url, String mbid, Artist artist, int listeners, int playCount, int userPlayCount,
			Wiki wiki) {
		super(name, url, mbid, listeners, playCount, userPlayCount, wiki);
		this.artist = artist;
	}

	public static Track getTrack(String name, String artist, String username) {
		String url = URLBuilder.getTrackInfoUrl(name, artist, username);
		// TestCall.test(url);
		Document response = Network.getResponse(url);
		if (response != null) {
			Track track = Parser.parseTrack(response);
			return track;
		}
		return null;
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
		return URLBuilder.getLyricsUrl(name, artist.getName());
	}

	public Album getAlbum() {
		return album;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj.getClass() != this.getClass())
			return false;

		Track track = (Track) obj;
		if (getName().equals(track.getName()))
			if (getArtist().equals(track.getArtist()))
				return true;

		return false;
	}

	@Override
	public void view() {
		TrackView view = new TrackView(this);
		view.setVisible(true);
	}

	public String toString() {
		return name + " - " + artist.getName();

	}
	
	@Override
	public void refresh() {
		Track track = Track.getTrack(name, artist.getName(), Reference.getUserName());
		
		this.listeners = track.listeners;
		this.userPlayCount = track.userPlayCount;
		this.playCount = track.playCount;
		this.wiki = track.wiki;
		this.mbid = track.mbid;
		this.isLoved = track.isLoved;
		
	}
}
