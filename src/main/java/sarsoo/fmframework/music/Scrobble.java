package sarsoo.fmframework.music;

public class Scrobble {
	
	private long utc;
	private Track track;
	
	public Scrobble(long utc, Track track) {
		this.utc = utc;
		this.track = track;
	}
	
	public long getUTC() {
		return utc;
	}
	
	public Track getTrack() {
		return track;
	}

}
