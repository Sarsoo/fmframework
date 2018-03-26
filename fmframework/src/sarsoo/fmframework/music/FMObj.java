package sarsoo.fmframework.music;

import sarsoo.fmframework.gui.FMObjView;

public abstract class FMObj {
	
	protected String name;
	protected String url;
	protected String mbid;
	
	protected int listeners;
	protected int playCount;
	protected int userPlayCount;
	
	protected Wiki wiki;
	
	public FMObj(String name, String url, String mbid, int listeners, int playCount, int userPlayCount, Wiki wiki) {
		this.name = name;
		this.url = url;
		this.mbid = mbid;
		this.listeners = listeners;
		this.playCount = playCount;
		this.userPlayCount = userPlayCount;
		this.wiki = wiki;
	}
	
	public void view() {
		FMObjView view = new FMObjView(this);
		view.setVisible(true);
	}
	
	public String toString() {
		return name;
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
	
	public Wiki getWiki() {
		return wiki;
	}
	
	abstract public String getMusicBeanzURL();

}
