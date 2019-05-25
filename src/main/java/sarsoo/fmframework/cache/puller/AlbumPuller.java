package sarsoo.fmframework.cache.puller;

import sarsoo.fmframework.error.ApiCallException;
import sarsoo.fmframework.fm.FmNetwork;
import sarsoo.fmframework.music.Album;

public class AlbumPuller implements Puller<Album, Album> {
	
	private FmNetwork net;
	
	public AlbumPuller(FmNetwork net) {
		this.net = net;
	}
	
	public Album pull(Album album) {
		try {
			return net.refresh(album);
		} catch (ApiCallException e) {}
		
		return null;
	}

}
