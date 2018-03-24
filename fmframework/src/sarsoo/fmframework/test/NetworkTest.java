package sarsoo.fmframework.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.InputStream;

import org.junit.jupiter.api.Test;

import sarsoo.fmframework.net.Network;
import sarsoo.fmframework.parser.AlbumParser;

class NetworkTest {

	@Test
	void test() {
		Network.apiAlbumInfoCall("Mastodon", "Leviathan", "sarsoo");
		//System.out.println(xml);
		//AlbumParser.parseAlbum(xml);
	}

}
