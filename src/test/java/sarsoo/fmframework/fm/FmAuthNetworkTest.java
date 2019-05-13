package sarsoo.fmframework.fm;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Scanner;

import org.junit.Test;

import sarsoo.fmframework.music.Scrobble;
import sarsoo.fmframework.music.Track.TrackBuilder;
import sarsoo.fmframework.music.Artist.ArtistBuilder;
import sarsoo.fmframework.net.Key;

public class FmAuthNetworkTest {

	@Test
	public void test() {
		
//		FmAuthNetwork net = new FmAuthNetwork(Key.getKey(), Key.getSecret(), "sarsoo");
//		
////		HashMap<String, String> hash = new HashMap<>();
////		
////		hash.put("zoo", "ash");
////		hash.put("boo", "ash");
////		hash.put("ash", "boo");
////		
////		net.getApiSignature(hash);
//		
//		String token = net.getToken();
//		
//		System.out.println(token);
//		
////		new Scanner(System.in).nextLine();
//		
//		String key = net.getSession(token);
//		
//		System.out.println(key);
//		
//		Scrobble scrobble = new Scrobble(1557750712, new TrackBuilder("i", new ArtistBuilder("kendrick lamar").build()).build());
//		
//		net.scrobble(scrobble, key);
		
		assertTrue(true);
	}

}
