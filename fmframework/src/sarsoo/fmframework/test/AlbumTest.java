package sarsoo.fmframework.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import sarsoo.fmframework.music.Album;

class AlbumTest {

	@Test
	void testGetName() {
		Album album = Album.getAlbum("Recovery", "Eminem", "sarsoo");
		assertEquals(album.getName(), "Recovery");
	}
	
	@Test
	void testGetArtist() {
		Album album = Album.getAlbum("Recovery", "Eminem", "sarsoo");
		assertEquals(album.getArtist().getName(), "Eminem");
	}

}
