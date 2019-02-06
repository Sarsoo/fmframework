package sarsoo.fmframework.fm;

import org.junit.Test;

public class FmUserNetworkTest {

	@Test
	public void test() {
		FmUserNetwork network = new FmUserNetwork("54a9f5c4c36f5d2cba0d4ffe3846e8b4", "sarsoo");
		
		network.getScrobblesToday();
	}
}
