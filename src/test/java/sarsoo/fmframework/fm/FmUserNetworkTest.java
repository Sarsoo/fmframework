package sarsoo.fmframework.fm;

import static org.junit.Assert.*;

import org.junit.Test;

public class FmUserNetworkTest {

	@Test
	public void test() {
		FmUserNetwork network = new FmUserNetwork("54a9f5c4c36f5d2cba0d4ffe3846e8b4", "sarsoo");
		
		System.out.println(network.getLastTrack());
	}
}
