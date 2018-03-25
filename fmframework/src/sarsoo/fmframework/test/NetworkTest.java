package sarsoo.fmframework.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.InputStream;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import sarsoo.fmframework.music.Album;
import sarsoo.fmframework.net.Network;
import sarsoo.fmframework.net.TestCall;
import sarsoo.fmframework.parser.AlbumParser;
import sarsoo.fmframework.parser.Parser;

class NetworkTest {

	@Test
	void testCall() {
		//TestCall.test("Pink Floyd", "The Wall", "sarsoo");
	}
	
	@Test
	void test() {
		String url = Network.getAlbumInfoUrl("The Wall", "Pink Floyd", "Sarsoo");
		Document response = Network.getResponse(url);
		Album album = Parser.parseAlbum(response);
		System.out.println(album);
	}
	
	@Test
	void getArtistXml() {
		//String url = Network.getAlbumInfoUrl("The Wall", "Pink Floyd", "Sarsoo");
		//TestCall.test(url);
	}
}
