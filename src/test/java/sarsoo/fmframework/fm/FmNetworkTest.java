package sarsoo.fmframework.fm;

import org.junit.Test;
import static org.junit.Assert.*;

import sarsoo.fmframework.music.Album;
import sarsoo.fmframework.music.Artist;
import sarsoo.fmframework.net.Key;

public class FmNetworkTest {

	@Test
	public void testNetworkInstantiate() {
		assertNotNull(new FmNetwork(Key.getKey()));
	}
	
	@Test
	public void testGetNonNullAlbum() {
		assertNotNull(new FmNetwork(Key.getKey()).getAlbum("To Pimp A Butterfly", "Kendrick Lamar"));
	}
	
	@Test
	public void testAlbumDataMatch() {
		Album album = new FmNetwork(Key.getKey()).getAlbum("To Pimp A Butterfly", "Kendrick Lamar");
		assertEquals(album.getName(), "To Pimp a Butterfly");
	}
	
	@Test
	public void testGetNonNullArtist() {
		assertNotNull(new FmNetwork(Key.getKey()).getArtist("Kendrick Lamar"));
	}
	
	@Test
	public void testArtistDataMatch() {
		Artist artist= new FmNetwork(Key.getKey()).getArtist("Kendrick Lamar");
		assertEquals(artist.getName(), "Kendrick Lamar");
	}

}
