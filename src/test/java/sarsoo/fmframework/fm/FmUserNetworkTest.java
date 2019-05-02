package sarsoo.fmframework.fm;

import org.junit.Test;

import sarsoo.fmframework.net.Key;

import static org.junit.Assert.*;

public class FmUserNetworkTest {

	@Test
	public void testNetworkInstantiate() {
		assertNotNull(new FmUserNetwork(Key.getKey(), "sarsoo"));
	}
	
	@Test
	public void testGetLastTrack() {
		assertNotNull(new FmUserNetwork(Key.getKey(), "sarsoo").getLastTrack());
	}
}
