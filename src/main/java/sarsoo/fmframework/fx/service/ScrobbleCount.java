package sarsoo.fmframework.fx.service;

public class ScrobbleCount {
	
	private int dailyCount;
	private int totalCount;
	
	public ScrobbleCount(int dailyCount, int totalCount) {
		this.dailyCount = dailyCount;
		this.totalCount = totalCount;
	}
	
	public int getDailyCount() {
		return dailyCount;
	}

	public int getTotalCount() {
		return totalCount;
	}
}
