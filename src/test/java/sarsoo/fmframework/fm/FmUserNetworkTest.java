package sarsoo.fmframework.fm;

import org.junit.Test;

import sarsoo.fmframework.music.Scrobble;
import sarsoo.fmframework.music.Track;
import sarsoo.fmframework.music.Track.TrackBuilder;
import sarsoo.fmframework.music.Artist.ArtistBuilder;
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
		
		int limit = 50;
		
		ArrayList<Scrobble> scrobbles = net.getRecentScrobbles(limit);
//		scrobbles.stream().forEach(System.out::println);
		System.out.println(scrobbles.size());
		assertEquals(limit, scrobbles.size());
	}
	
	@Test
	public void testGetTopAlbums() {
		FmUserNetwork net = new FmUserNetwork(Key.getKey(), "sarsoo");
		
		int limit = 50;
		
		FMObjList list = net.getTopAlbums("7day", limit);
//		list.stream().forEach(System.out::println);
		assertEquals(limit, list.size());
	}
	
	@Test
	public void testGetTopArtists() {
		FmUserNetwork net = new FmUserNetwork(Key.getKey(), "sarsoo");
		
		int limit = 50;
		
		FMObjList list = net.getTopArtists("7day", limit);
//		list.stream().forEach(System.out::println);
		assertEquals(limit, list.size());
	}
	
	@Test
	public void testGetTopTracks() {
		FmUserNetwork net = new FmUserNetwork(Key.getKey(), "sarsoo");
		
		int limit = 50;
		
		FMObjList list = net.getTopTracks("7day", limit);
//		list.stream().forEach(System.out::println);
		assertEquals(limit, list.size());
	}
	
	@Test
	public void testTrackScrobbles() {
		FmUserNetwork net = new FmUserNetwork(Key.getKey(), "sarsoo");
		
		Track track = new TrackBuilder("shitsville", new ArtistBuilder("freddie gibbs").build()).build();
		
		ArrayList<Scrobble> scrobbles = net.getTrackScrobbles(track);
		
		scrobbles.stream().forEach(System.out::println);
		System.out.println(scrobbles.size());
		assertEquals(53, scrobbles.size());
	}
}
