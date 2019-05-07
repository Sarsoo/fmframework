package sarsoo.fmframework.fm;

import org.junit.Test;

import sarsoo.fmframework.music.Scrobble;
import sarsoo.fmframework.net.Key;
import sarsoo.fmframework.util.FMObjList;

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
	
	@Test
	public void testGetTopAlbums() {
		FmUserNetwork net = new FmUserNetwork(Key.getKey(), "sarsoo");
		FMObjList list = net.getTopAlbums("7day", 15);
		list.stream().forEach(System.out::println);
		assertEquals(15, list.size());
	}
	
	@Test
	public void testGetTopArtists() {
		FmUserNetwork net = new FmUserNetwork(Key.getKey(), "sarsoo");
		FMObjList list = net.getTopArtists("7day", 15);
		list.stream().forEach(System.out::println);
		assertEquals(15, list.size());
	}
	
	@Test
	public void testGetTopTracks() {
		FmUserNetwork net = new FmUserNetwork(Key.getKey(), "sarsoo");
		FMObjList list = net.getTopTracks("7day", 15);
		list.stream().forEach(System.out::println);
		assertEquals(15, list.size());
	}
}
