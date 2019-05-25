package sarsoo.fmframework.cache;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import sarsoo.fmframework.cache.puller.AlbumPuller;
import sarsoo.fmframework.fm.FmUserNetwork;
import sarsoo.fmframework.music.Album;
import sarsoo.fmframework.net.Key;
import sarsoo.fmframework.music.Album.AlbumBuilder;
import sarsoo.fmframework.music.Artist.ArtistBuilder;

public class StaticCacheTest {

	@Test
	public void test() {
		StaticCache<Album, Album> cache = new StaticCache<Album, Album>(new AlbumPuller(new FmUserNetwork(Key.getKey(), "sarsoo")));
		
		Album pimp = cache.get(new AlbumBuilder("to pimp a butterfly", new ArtistBuilder("kendrick lamar").build()).build());
		System.out.println(pimp.getUserPlayCount());
		Album pimp2 = cache.get(new AlbumBuilder("to pimp a butterfly", new ArtistBuilder("kendrick lamar").build()).build());
		System.out.println(pimp2.getUserPlayCount());
		Album pimp3 = cache.getNew(new AlbumBuilder("to pimp a butterfly", new ArtistBuilder("kendrick lamar").build()).build());
		System.out.println(pimp3.getUserPlayCount());
		
		assertTrue(true);
	}

}
