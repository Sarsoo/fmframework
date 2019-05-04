package sarsoo.fmframework.music;

import java.io.Serializable;
import java.util.ArrayList;

import sarsoo.fmframework.fm.FmUserNetwork;
import sarsoo.fmframework.net.Key;
import sarsoo.fmframework.util.Maths;
import sarsoo.fmframework.util.Reference;

public abstract class FMObj implements Comparable<FMObj>, Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String name;
	protected String url;
	protected String mbid;
	
	protected int listeners;
	protected int playCount;
	protected int userPlayCount = 0;
	
	protected Wiki wiki;
	
	public FMObj() {
		
	}
	
	
	@Override
	public int compareTo(FMObj obj) {
		
		return userPlayCount - obj.getUserPlayCount();
	}
	
	public double getTimeListenRatio() {
		return Maths.getDaysScrobbling() / (double) userPlayCount;
	}
	
	public String getName() {
		return name;
	}
	
	public String getUrl() {
		return url;
	}
	
	public String getMbid() {
		return mbid;
	}
	
	public int getListeners() {
		return listeners;
	}
	
	public int getPlayCount() {
		return playCount;
	}
	
	public int getUserPlayCount() {
		return userPlayCount;
	}
	
	public double getPercent() {
		FmUserNetwork net = new FmUserNetwork(Key.getKey(), Reference.getUserName());
		return ((double)userPlayCount*100)/(double) net.getUser().getScrobbleCount();
	}
	
	public Wiki getWiki() {
		return wiki;
	}
	
	abstract public String getMusicBrainzURL();
	
	public abstract ArrayList<Scrobble> getScrobbles();

}
