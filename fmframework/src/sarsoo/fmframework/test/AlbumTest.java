package sarsoo.fmframework.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import sarsoo.fmframework.music.Album;

class AlbumTest {

	@Test
	void test() {
		Album album = Album.getAlbum("Recovery", "Eminem", "sarsoo");
		System.out.println(album.getArtist());
	}

}
