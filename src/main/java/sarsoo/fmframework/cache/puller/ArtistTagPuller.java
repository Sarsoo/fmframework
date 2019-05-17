package sarsoo.fmframework.cache.puller;

import sarsoo.fmframework.fm.FmUserNetwork;
import sarsoo.fmframework.util.FMObjList;

public class ArtistTagPuller implements Puller<FMObjList, String> {
	
	private FmUserNetwork net;
	
	public ArtistTagPuller(FmUserNetwork net) {
		this.net = net;
	}
	
	public FMObjList pull(String name) {
		return net.getPopulatedArtistTag(name);
	}

}
