package sarsoo.fmframework.music;

import java.util.ArrayList;

import org.w3c.dom.Document;

import sarsoo.fmframework.gui.TrackView;
import sarsoo.fmframework.net.Network;
//import sarsoo.fmframework.net.TestCall;
import sarsoo.fmframework.parser.Parser;

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
		String url = Network.getTrackInfoUrl(name, artist, username);
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
		return Network.getLyricsUrl(name, artist.getName());
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
}
