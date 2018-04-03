package sarsoo.fmframework.util;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

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
}
