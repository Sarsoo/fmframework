package sarsoo.fmframework.cache;

import sarsoo.fmframework.cache.puller.Puller;
import sarsoo.fmframework.music.Album;
import sarsoo.fmframework.music.Artist;
import sarsoo.fmframework.music.Track;

public class TrackCache<S> extends StaticCache<Track, S> {

	private StaticCache<Album, Album> albumPool;
	private StaticCache<Artist, Artist> artistPool;

	public TrackCache(Puller<Track, S> puller, StaticCache<Album, Album> albumCache,
			StaticCache<Artist, Artist> artistCache) {
		super(puller);
		this.albumPool = albumCache;
		this.artistPool = artistCache;
	}

	@Override
	protected void propagateCache(Track in) {
		if (in.getAlbum() != null) {
			albumPool.add(in.getAlbum());
		}
		artistPool.add(in.getArtist());
	}

}
