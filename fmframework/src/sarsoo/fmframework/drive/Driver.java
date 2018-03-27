package sarsoo.fmframework.drive;

import sarsoo.fmframework.music.Artist;
import sarsoo.fmframework.util.ArtistList;
import sarsoo.fmframework.util.GetObject;
import sarsoo.fmframework.util.Reference;

public class Driver {
	
	public static void main(String[] args) {
		Reference.setUserName("sarsoo");
		
		//Network.getRecentTracaksUrl("sarsoo");
		Reference.getTDE().view();
	}

}
