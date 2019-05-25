package sarsoo.fmframework.cache.puller;

import sarsoo.fmframework.error.ApiCallException;
import sarsoo.fmframework.fm.FmNetwork;
import sarsoo.fmframework.music.Track;

public class TrackPuller implements Puller<Track, Track> {
	
	private FmNetwork net;
	
	public TrackPuller(FmNetwork net) {
		this.net = net;
	}
	
	public Track pull(Track track) {
		try {
			return net.refresh(track);
		} catch (ApiCallException e) {}
		
		return null;
	}

}
