package sarsoo.fmframework.cache;

import sarsoo.fmframework.cache.puller.Puller;
import sarsoo.fmframework.music.Album;
import sarsoo.fmframework.music.Artist;

public class AlbumCache<S> extends StaticCache<Album, S> {
	
	private StaticCache<Artist, Artist> artistPool;
	
	public AlbumCache(Puller<Album, S> puller, StaticCache<Artist, Artist> artistCache) {
		super(puller);
		this.artistPool = artistCache;
	}
	
	@Override
	protected void propagateCache(Album in) {
		artistPool.add(in.getArtist());
	}
	
}
