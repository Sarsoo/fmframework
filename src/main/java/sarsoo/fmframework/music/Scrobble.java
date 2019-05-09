package sarsoo.fmframework.music;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class Scrobble {
	
	private LocalDateTime dateTime;
	private Track track;
	private Album album;
	
	public Scrobble(long uts, Track track) {
		this.track = track;		
		this.dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(uts), ZoneId.systemDefault());
	}
	
	public Scrobble(long uts, Track track, Album album) {
		this.track = track;	
		this.album = album;
		this.dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(uts), ZoneId.systemDefault());
	}
	
	public LocalDateTime getDateTime() {
		return dateTime;
	}
	
	public Track getTrack() {
		return track;
	}
	
	public Album getAlbum() {
		return album;
	}
	
	public Artist getArtist() {
		return track.getArtist();
	}
	
	public String toString() {
		
		String string =  "Scrobble: " + dateTime + " " + track.getName();
		
		if(album != null) {
			string += " " + album.getName();
		}
		
		string += " " + track.getArtist().getName();
		
		return string;
	}

}
