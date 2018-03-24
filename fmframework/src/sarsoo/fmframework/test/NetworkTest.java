package sarsoo.fmframework.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.InputStream;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import sarsoo.fmframework.net.Network;
import sarsoo.fmframework.net.TestCall;
import sarsoo.fmframework.parser.AlbumParser;

class NetworkTest {

	@Test
	void testCall() {
		TestCall.test("Pink Floyd", "The Wall", "sarsoo");
	}
	
	@Test
	void test() {
		//Document doc = Network.apiAlbumInfoCall("Mastodon", "Leviathan", "sarsoo");
		//AlbumParser.parseAlbum(doc);
	}

}
