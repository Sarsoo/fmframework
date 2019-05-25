package sarsoo.fmframework.cache;

import org.junit.jupiter.api.Test;

import sarsoo.fmframework.cache.puller.AlbumPuller;
import sarsoo.fmframework.cache.puller.ArtistPuller;
import sarsoo.fmframework.fm.FmUserNetwork;
import sarsoo.fmframework.log.Log;
import sarsoo.fmframework.music.Album;
import sarsoo.fmframework.net.Key;
import sarsoo.fmframework.music.Album.AlbumBuilder;
import sarsoo.fmframework.music.Artist;
import sarsoo.fmframework.music.Artist.ArtistBuilder;

public class AlbumCacheTest {

	@Test
	public void test() {
		
		FmUserNetwork net = new FmUserNetwork(Key.getKey(), "sarsoo");
		
		StaticCache<Artist, Artist> artistCache = new StaticCache<Artist, Artist>(new ArtistPuller(net));
		
		StaticCache<Album, Album> cache = new AlbumCache<>(new AlbumPuller(net), artistCache);
		
		Album pimp = cache.get(new AlbumBuilder("to pimp a butterfly", new ArtistBuilder("kendrick lamar").build()).build());
		System.out.println(pimp.getUserPlayCount());
		Album pimp2 = cache.get(new AlbumBuilder("to pimp a butterfly", new ArtistBuilder("kendrick lamar").build()).build());
		System.out.println(pimp2.getUserPlayCount());
		Album pimp3 = cache.getNew(new AlbumBuilder("to pimp a butterfly", new ArtistBuilder("kendrick lamar").build()).build());
		System.out.println(pimp3.getUserPlayCount());
		
		artistCache.dumpToLog(new Log());
		cache.dumpToLog(new Log());
		
//		assertThat();
	}

}
