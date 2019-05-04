package sarsoo.fmframework.music;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class Scrobble {
	
	private LocalDateTime dateTime;
	private Track track;
	
	public Scrobble(long uts, Track track) {
		this.track = track;		
		this.dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(uts), ZoneId.systemDefault());
	}
	
	public LocalDateTime getDateTime() {
		return dateTime;
	}
	
	public Track getTrack() {
		return track;
	}
	
	public String toString() {
		return dateTime + " " + track.toString();
	}

}
