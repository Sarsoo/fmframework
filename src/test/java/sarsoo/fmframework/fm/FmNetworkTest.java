package sarsoo.fmframework.fm;

import org.junit.Test;

import sarsoo.fmframework.music.Track;

public class FmNetworkTest {

	@Test
	public void test() {
		FmUserNetwork network = new FmUserNetwork("54a9f5c4c36f5d2cba0d4ffe3846e8b4", "sarsoo");
		
		Track track = network.getLastTrack();
		
		System.out.println(track);
		System.out.println(track.getUserPlayCount());
	}

}
