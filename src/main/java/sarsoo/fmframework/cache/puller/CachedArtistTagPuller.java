package sarsoo.fmframework.cache.puller;

import sarsoo.fmframework.cache.StaticCache;
import sarsoo.fmframework.error.ApiCallException;
import sarsoo.fmframework.fm.FmUserNetwork;
import sarsoo.fmframework.music.Artist;
import sarsoo.fmframework.util.FMObjList;

public class CachedArtistTagPuller implements Puller<FMObjList, String> {
	
	private FmUserNetwork net;
	private StaticCache<Artist, Artist> artistPool;
	
	public CachedArtistTagPuller(FmUserNetwork net, StaticCache<Artist, Artist> artistPool) {
		this.net = net;
		this.artistPool = artistPool;
	}
	
	public FMObjList pull(String name) {
		FMObjList list;
		try {
			list = net.getArtistTag(name);
			
			FMObjList returned = new FMObjList();
			returned.setGroupName(list.getGroupName());
			
			for(int i = 0; i < list.size(); i++) {
				returned.add(artistPool.get((Artist) list.get(i)));
			}
			
			return returned;
			
		} catch (ApiCallException e) {}
		
		return null;
	}

}
