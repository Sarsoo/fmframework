package sarsoo.fmframework.fm;

import org.junit.Test;

import sarsoo.fmframework.music.Scrobble;
import sarsoo.fmframework.net.Key;

import static org.junit.Assert.*;

import java.util.ArrayList;

public class FmUserNetworkTest {

	@Test
	public void testNetworkInstantiate() {
		assertNotNull(new FmUserNetwork(Key.getKey(), "sarsoo"));
	}
	
	@Test
	public void testGetLastTrack() {
		assertNotNull(new FmUserNetwork(Key.getKey(), "sarsoo").getLastTrack());
	}
	
	@Test
	public void testGetRecentTracks() {
		FmUserNetwork net = new FmUserNetwork(Key.getKey(), "sarsoo");
		ArrayList<Scrobble> scrobbles = net.getRecentScrobbles(50);
		scrobbles.stream().forEach(System.out::println);
		System.out.println(scrobbles.size());
		assertNotNull(1);
	}
}
