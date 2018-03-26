package sarsoo.fmframework.music;

import java.util.ArrayList;

import org.w3c.dom.Document;

import sarsoo.fmframework.gui.AlbumView;
import sarsoo.fmframework.net.Network;
import sarsoo.fmframework.parser.Parser;

public class Album extends FMObj{
	protected Artist artist;
	protected ArrayList<Tag> tagList;
	protected ArrayList<Track> trackList;
	
	public Album(String name, String artist) {
		super(name, null, null, 0, 0, 0, null);
		this.artist = Artist.getArtist(artist, "sarsoo");
	}
	
	public Album(String name, String url, String mbid, Artist artist, int listeners, int playCount, int userPlayCount, Wiki wiki) {
		super(name, url, mbid, listeners, playCount, userPlayCount, wiki);
		this.artist = artist;
	}
	
	public String toString() {
		return name + " - " + artist.getName();
		
	}
	
	public Artist getArtist() {
		return artist;
	}
	
	public static Album getAlbum(String name, String artist, String username) {
		String url = Network.getAlbumInfoUrl(name, artist, username);
		Document response = Network.getResponse(url);
		Album album = Parser.parseAlbum(response);
		return album;
	}
	
	public ArrayList<Track> getTrackList(){
		return trackList;
	}
	
	public Track getTrack(int track) {
		return trackList.get(track);
	}
	
	public ArrayList<Tag> getTags(){
		return tagList;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj.getClass() != this.getClass()) return false;
		
		Album album = (Album)obj;
		if(getName() == album.getName())
			if(getArtist().equals(album.getArtist()))
				return true;
		
		return false;
	}
	
	public String getRymURL() {
		return "https://rateyourmusic.com/release/album/" + getArtist().getName().replaceAll(" ", "_").toLowerCase() + "/" + getName().replaceAll(" ", "_").toLowerCase();
	}
	
	@Override
	public void view() {
		AlbumView view = new AlbumView(this);
		view.setVisible(true);
	}

	@Override
	public String getMusicBeanzURL() {
		return "https://musicbrainz.org/release/"  + mbid;
		
	}
}
