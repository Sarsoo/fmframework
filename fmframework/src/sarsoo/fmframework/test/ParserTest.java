package sarsoo.fmframework.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import sarsoo.fmframework.music.Album;
import sarsoo.fmframework.net.Network;
import sarsoo.fmframework.parser.AlbumParser;

class ParserTest {

	@Test
	void testParseAlbum() {
		String url = Network.getAlbumInfoUrl("Pink Floyd", "The Wall", "sarsoo");
		Document doc = Network.getResponse(url);
		Album album = AlbumParser.parseAlbum(doc);
		assertNotNull(album);
	}
	
	@Test
	void testParseArtist() {
		String url = Network.getArtistInfoUrl("Pink Floyd", "sarsoo");
		Document doc = Network.getResponse(url);
		System.out.println();
	}

}
