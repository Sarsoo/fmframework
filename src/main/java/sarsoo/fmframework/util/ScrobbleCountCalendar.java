package sarsoo.fmframework.util;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import sarsoo.fmframework.log.Logger;
import sarsoo.fmframework.log.entry.ErrorEntry;

public class ScrobbleCountCalendar {

	private ArrayList<MonthScrobbles> months;
	private String name;

	public ScrobbleCountCalendar(LocalDate date, String name) {

		months = new ArrayList<>();
		this.name = name;

		LocalDate now = LocalDate.now();

		long monthsDiff = ChronoUnit.MONTHS.between(date, now) + 1;

		for (int i = 0; i < monthsDiff; i++) {
			LocalDate counter = date.plusMonths(i);

			months.add(new MonthScrobbles(counter.getMonth(), counter.getYear()));
		}

	}

	public void addCount(Month monthIn, int year) {

		Boolean found = false;

		for (MonthScrobbles month : months) {
			if (month.getMonth().getValue() == monthIn.getValue() && year == month.getYear()) {
				month.addCount();
				found = true;
				break;
			}
		}

		if (found == false)
			Logger.getLog().logError(new ErrorEntry("ScrobbleCountCalendar").addArg("add count")
					.addArg("month not found").addArg(monthIn.toString()).addArg(Integer.toString(year)));

	}

	public ArrayList<MonthScrobbles> getMonthScrobbles() {
		return months;
	}

	public String getName() {
		return name;
	}

}
