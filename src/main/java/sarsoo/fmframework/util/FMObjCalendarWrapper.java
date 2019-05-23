package sarsoo.fmframework.util;

import sarsoo.fmframework.music.Scrobble;

public class FMObjCalendarWrapper {
	
	private Scrobble obj;
	private ScrobbleCountCalendar calendar;
	
	public FMObjCalendarWrapper(Scrobble scrobble, ScrobbleCountCalendar calendar) {
		this.obj = scrobble;
		this.calendar = calendar;
	}
	
	public ScrobbleCountCalendar getCalendar() {
		return calendar;
	}
	
	public Scrobble getScrobble() {
		return obj;
	}

}
