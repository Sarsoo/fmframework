package sarsoo.fmframework.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.InputStream;
import java.time.Instant;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import sarsoo.fmframework.music.Album;
import sarsoo.fmframework.music.Artist;
import sarsoo.fmframework.music.Tag;
import sarsoo.fmframework.music.Track;
import sarsoo.fmframework.net.Network;
import sarsoo.fmframework.net.TestCall;
import sarsoo.fmframework.net.URLBuilder;
import sarsoo.fmframework.parser.AlbumParser;
import sarsoo.fmframework.parser.Parser;
import sarsoo.fmframework.util.FMObjList;
import sarsoo.fmframework.util.Getter;
import sarsoo.fmframework.util.Maths;
import sarsoo.fmframework.util.Reference;

class NetworkTest {
//
//	@Test
//	void testCall() {
//		//TestCall.test("Pink Floyd", "The Wall", "sarsoo");
//	}
//	
//	@Test
//	void test() {
//		String url = URLBuilder.getAlbumInfoUrl("The Wall", "Pink Floyd", "Sarsoo");
//		Document response = Network.getResponse(url);
//		Album album = Parser.parseAlbum(response);
//		//System.out.println(album);
//	}
//	
//	@Test
//	void testArtist() {
//		String url = URLBuilder.getArtistInfoUrl("Pink Floyd", "sarsoo");
//		Document response = Network.getResponse(url);
//		Artist artist = Parser.parseArtist(response);
//		//System.out.println(album);
//	}
//	
//	@Test
//	void testTrack() {
//		String url = URLBuilder.getTrackInfoUrl("Business", "Eminem", "sarsoo");
//		Document response = Network.getResponse(url);
//		Track track = Parser.parseTrack(response);
//		//System.out.println(album);
//	}
	
	@Test
	void testTag() {

//		System.out.println(Instant.parse("2018-04-05T07:00:00.00Z").getEpochSecond());
		Reference.setUserName("sarsoo");
		Getter.getArtistTracks("eminem", "sarsoo");
		
//		System.out.println(url);
	}
	
//	@Test
//	void getArtistXml() {
//		//String url = Network.getAlbumInfoUrl("The Wall", "Pink Floyd", "Sarsoo");
//		//TestCall.test(url);
//	}
}
