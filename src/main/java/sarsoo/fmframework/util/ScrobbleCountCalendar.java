package sarsoo.fmframework.util;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import sarsoo.fmframework.log.Logger;
import sarsoo.fmframework.log.entry.ErrorEntry;

public class ScrobbleCountCalendar {
	
	public ArrayList<MonthScrobbles> months;
	
	public ScrobbleCountCalendar(LocalDate date) {
		
		months = new ArrayList<>();
		
		LocalDate now = LocalDate.now();

		long monthsDiff = ChronoUnit.MONTHS.between(date, now) + 1;
		
		for(int i = 0; i < monthsDiff; i++) {
			LocalDate counter = date.plusMonths(i);
			
			months.add(new MonthScrobbles(counter.getMonth(), counter.getYear()));
		}
		
	}
	
	public void addCount(Month monthIn, int year) {
		
		for(MonthScrobbles month: months) {
			if(month.getMonth().getValue() == monthIn.getValue() && year == month.getYear()) {
				month.addCount();
				break;
			}
		}
		
		Logger.getLog().logError(new ErrorEntry("ScrobbleCountCalendar").addArg("add count").addArg("month not found"));
		
	}
	
	public ArrayList<MonthScrobbles> getMonthScrobbles(){
		return months;
	}

}
