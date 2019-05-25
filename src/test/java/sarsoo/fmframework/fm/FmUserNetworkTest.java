package sarsoo.fmframework.fm;

import org.junit.Test;

import sarsoo.fmframework.music.Scrobble;
import sarsoo.fmframework.music.Track;
import sarsoo.fmframework.music.Track.TrackBuilder;
import sarsoo.fmframework.error.ApiCallException;
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
		try {
			assertNotNull(new FmUserNetwork(Key.getKey(), "sarsoo").getLastTrack());
		} catch (ApiCallException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetRecentTracks() {
		FmUserNetwork net = new FmUserNetwork(Key.getKey(), "sarsoo");
		
		int limit = 50;
		
		ArrayList<Scrobble> scrobbles;
		try {
			scrobbles = net.getRecentScrobbles(limit);
			
//			scrobbles.stream().forEach(System.out::println);
			System.out.println(scrobbles.size());
			assertEquals(limit, scrobbles.size());
			
		} catch (ApiCallException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetTopAlbums() {
		FmUserNetwork net = new FmUserNetwork(Key.getKey(), "sarsoo");
		
		int limit = 50;
		
		FMObjList list;
		try {
			list = net.getTopAlbums("7day", limit);
			
//			list.stream().forEach(System.out::println);
			assertEquals(limit, list.size());
			
		} catch (ApiCallException e) {
			e.printStackTrace();
		}

	}
	
	@Test
	public void testGetTopArtists() {
		FmUserNetwork net = new FmUserNetwork(Key.getKey(), "sarsoo");
		
		int limit = 50;
		
		FMObjList list;
		try {
			list = net.getTopArtists("7day", limit);
			
//			list.stream().forEach(System.out::println);
			assertEquals(limit, list.size());

		} catch (ApiCallException e) {
			e.printStackTrace();
		}

	}
	
	@Test
	public void testGetTopTracks() {
		FmUserNetwork net = new FmUserNetwork(Key.getKey(), "sarsoo");
		
		int limit = 50;
		
		FMObjList list;
		try {
			list = net.getTopTracks("7day", limit);
			
//			list.stream().forEach(System.out::println);
			assertEquals(limit, list.size());
			
		} catch (ApiCallException e) {
			e.printStackTrace();
		}

	}
	
	@Test
	public void testTrackScrobbles() {
		FmUserNetwork net = new FmUserNetwork(Key.getKey(), "sarsoo");
		
		Track track = new TrackBuilder("shitsville", new ArtistBuilder("freddie gibbs").build()).build();
		
		ArrayList<Scrobble> scrobbles;
		try {
			scrobbles = net.getTrackScrobbles(track);
			
			scrobbles.stream().forEach(System.out::println);
			System.out.println(scrobbles.size());
			assertTrue(true);
			
		} catch (ApiCallException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testFirstScrobbleDateTime() {
		FmUserNetwork net = new FmUserNetwork(Key.getKey(), "sarsoo");
		
		try {
			System.out.println(net.getFirstScrobbleDateTime());
			
			assertTrue(true);

		} catch (ApiCallException e) {
			e.printStackTrace();
		}
	}
}
