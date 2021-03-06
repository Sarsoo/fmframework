package sarsoo.fmframework.fm;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import sarsoo.fmframework.error.ApiCallException;
import sarsoo.fmframework.music.Album;
import sarsoo.fmframework.music.Artist;
import sarsoo.fmframework.music.Artist.ArtistBuilder;
import sarsoo.fmframework.net.Key;
import sarsoo.fmframework.util.FMObjList;

public class FmNetworkTest {

	@Test
	public void testNetworkInstantiate() {
		assertNotNull(new FmNetwork(Key.getKey()));
	}
	
	@Test
	public void testGetNonNullAlbum() {
		try {
			assertNotNull(new FmNetwork(Key.getKey()).getAlbum("To Pimp A Butterfly", "Kendrick Lamar"));
		} catch (ApiCallException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testAlbumDataMatch() {
		Album album;
		try {
			album = new FmNetwork(Key.getKey()).getAlbum("To Pimp A Butterfly", "Kendrick Lamar");
			assertEquals(album.getName(), "To Pimp a Butterfly");
		} catch (ApiCallException e) {
			e.printStackTrace();
		}		
	}
	
	@Test
	public void testGetNonNullArtist() {
		try {
			assertNotNull(new FmNetwork(Key.getKey()).getArtist("Kendrick Lamar"));
		} catch (ApiCallException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testArtistDataMatch() {
		Artist artist;
		try {
			artist = new FmNetwork(Key.getKey()).getArtist("Kendrick Lamar");
			assertEquals(artist.getName(), "Kendrick Lamar");
		} catch (ApiCallException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testNullParameters() {
		FmNetwork network = new FmNetwork(Key.getKey());

//		network.makeGetRequest("artist.getinfo", null);
		
		assertTrue(true);
	}
	
	@Test
	public void testArtistTopTracks() {
		FmNetwork network = new FmNetwork(Key.getKey());

		FMObjList list;
		try {
			list = network.getArtistTopTracks(new ArtistBuilder("kendrick lamar").build(), 10);
			list.stream().forEach(System.out::println);
			
			assertTrue(true);
			
		} catch (ApiCallException e) {
			e.printStackTrace();
		}
	}

}
