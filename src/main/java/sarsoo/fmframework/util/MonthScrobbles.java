package sarsoo.fmframework.util;

import java.time.Month;

public class MonthScrobbles {

	private final Month month;
	private final int year;
	
	private int count = 0;

	public MonthScrobbles(Month left, int right) {
		this.month = left;
		this.year = right;
	}

	public Month getMonth() {
		return month;
	}

	public int getYear() {
		return year;
	}
	
	public void addCount() {
		count++;
	}
	
	public int getCount() {
		return count;
	}
	
	@Override
	public String toString() {
		return month.toString() + " " + Integer.toString(year);
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof MonthScrobbles))
			return false;
		MonthScrobbles pairo = (MonthScrobbles) o;
		return this.month.toString().equals(pairo.getMonth().toString()) && (this.year == pairo.getYear());
	}

}