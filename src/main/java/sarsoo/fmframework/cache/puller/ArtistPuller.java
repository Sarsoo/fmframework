package sarsoo.fmframework.cache.puller;

import sarsoo.fmframework.error.ApiCallException;
import sarsoo.fmframework.fm.FmNetwork;
import sarsoo.fmframework.music.Album;
import sarsoo.fmframework.music.Artist;

public class ArtistPuller implements Puller<Artist, Artist> {
	
	private FmNetwork net;
	
	public ArtistPuller(FmNetwork net) {
		this.net = net;
	}
	
	public ArtistPuller(FmNetwork net, Artist artist) {
		this.net = net;
	}
	
	public Artist pull(Artist artist) {
		try {
			return net.refresh(artist);
		} catch (ApiCallException e) {}
		
		return null;
	}

}
