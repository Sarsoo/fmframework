package sarsoo.fmframework.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import sarsoo.fmframework.music.FMObj;

public class Maths {
	public static double getPercentListening(FMObj obj, String username) {

		int userScrobbles = Getter.getScrobbles(Reference.getUserName());
		double plays = (double) obj.getUserPlayCount();
		if (userScrobbles > 0 && plays > 0) {

			double userScrobblesDouble = (double) userScrobbles;

			double percentage = (plays / userScrobblesDouble) * 100;

			return percentage;
		}

		return 0;
	}

	public static double getPercentListening(FMObjList objList, String username) {

		int userScrobbles = Getter.getScrobbles(Reference.getUserName());
		double plays = (double) objList.getTotalUserScrobbles();
		if (userScrobbles > 0 && plays > 0) {

			double userScrobblesDouble = (double) userScrobbles;

			double percentage = (plays / userScrobblesDouble) * 100;

			return percentage;
		}

		return 0;
	}

	public static int getDaysScrobbling() {

		Calendar calendar = new GregorianCalendar(2017, 10, 2);

//		System.out.println(calendar.getTime().getTime());

		Date date = new Date();
//		System.out.println(date.getTime());
		
		long diff = date.getTime() - calendar.getTime().getTime();
		
//		System.out.println(diff/(1000*60*60*24));

		return (int) (diff/(1000*60*60*24));
	}
}
