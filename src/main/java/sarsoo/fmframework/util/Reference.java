package sarsoo.fmframework.util;

import java.util.ArrayList;

import sarsoo.fmframework.log.console.Console;
import sarsoo.fmframework.music.Album;
import sarsoo.fmframework.music.Artist;

public class Reference {
	private static String userName;
	private static boolean isVerbose = false;
	private static boolean isHeadless = false;
	
	private static Console console;

	public static String getUserName() {
		return userName;
	}

	public static void setUserName(String userNameIn) {
		userName = userNameIn;
	}

	public static boolean isHeadless() {
		return isHeadless;
	}

	public static void setIsHeadless(boolean headlessIn) {
		isHeadless = headlessIn;
	}
	
	public static Console getConsole() {
		return console;
	}
	
	public static boolean isVerbose() {
		return isVerbose;
	}

	public static void setVerbose(Console consoleIn) {
		if(consoleIn == null)
			isVerbose = false;
		else 
			isVerbose = true;
		console = consoleIn;
	}

	private static ArrayList<FMObjList> groups = new ArrayList<FMObjList>();

//	public static void initGroupsList() {
//		groups.add(getTDE());
//		groups.add(getBPHQ());
//		groups.add(getDre());
//		groups.add(getWu());
//		groups.add(getHopeless());
//		groups.add(getSaturation());
//	}

	public static FMObjList getSaturation() {
		FMObjList saturation = new FMObjList("Saturation");

		saturation.add(Album.getAlbum("Saturation", "Brockhampton", Reference.getUserName()));
		saturation.add(Album.getAlbum("Saturation II", "Brockhampton", Reference.getUserName()));
		saturation.add(Album.getAlbum("Saturation III", "Brockhampton", Reference.getUserName()));

		return saturation;
	}

//	public static ArrayList<FMObjList> getGroups() {
//		return groups;
//	}
	
	public static FMObjList getHopeless() {
		FMObjList hopeless = new FMObjList("Hopless");

		hopeless.add(Artist.getArtist("Circa Survive", Reference.getUserName()));
		hopeless.add(Artist.getArtist("Enter Shikari", Reference.getUserName()));
		hopeless.add(Artist.getArtist("Have Mercy", Reference.getUserName()));
		hopeless.add(Artist.getArtist("Moose Blood", Reference.getUserName()));
//		hopeless.add(Artist.getArtist("New Found Glory", Reference.getUserName()));
		hopeless.add(Artist.getArtist("Neck Deep", Reference.getUserName()));
		hopeless.add(Artist.getArtist("Sum 41", Reference.getUserName()));
//		hopeless.add(Artist.getArtist("Taking Back Saturday", Reference.getUserName()));
		hopeless.add(Artist.getArtist("Tonight Alive", Reference.getUserName()));
		hopeless.add(Artist.getArtist("Trash Boat", Reference.getUserName()));
		hopeless.add(Artist.getArtist("The Wonder Years", Reference.getUserName()));
		hopeless.add(Artist.getArtist("With Confidence", Reference.getUserName()));
		hopeless.add(Artist.getArtist("Trophy Eyes", Reference.getUserName()));
		hopeless.add(Artist.getArtist("Dryjacket", Reference.getUserName()));
		hopeless.add(Artist.getArtist("Yellowcard", Reference.getUserName()));

		return hopeless;
	}

	public static FMObjList getTDE() {
		FMObjList tde = new FMObjList("TDE");

		tde.add(Artist.getArtist("Kendrick Lamar", Reference.getUserName()));
		tde.add(Artist.getArtist("Jay Rock", Reference.getUserName()));
		tde.add(Artist.getArtist("ScHoolboy Q", Reference.getUserName()));
		tde.add(Artist.getArtist("Ab-Soul", Reference.getUserName()));
		tde.add(Artist.getArtist("Black Hippy", Reference.getUserName())); 
		tde.add(Artist.getArtist("Isaiah Rashad", Reference.getUserName()));
		tde.add(Artist.getArtist("SZA", Reference.getUserName()));
		tde.add(Artist.getArtist("Sir", Reference.getUserName()));

		return tde;
	}

	public static FMObjList getBB() {
		FMObjList bphq = new FMObjList("British Rock");

		bphq.add(Artist.getArtist("Deaf Havana", Reference.getUserName()));
		bphq.add(Artist.getArtist("Lower Than Atlantis", Reference.getUserName()));
		bphq.add(Artist.getArtist("Mallory Knox", Reference.getUserName()));
		bphq.add(Artist.getArtist("Don Broco", Reference.getUserName()));
		bphq.add(Artist.getArtist("Moose Blood", Reference.getUserName()));
		bphq.add(Artist.getArtist("Young Guns", Reference.getUserName()));

		return bphq;
	}

	public static FMObjList getDre() {
		FMObjList dre = new FMObjList("Dre");

		dre.add(Artist.getArtist("N.W.A", Reference.getUserName()));
//		dre.add(Album.getAlbum("Straight Outta Compton", "N.W.A", Reference.getUserName()));
//		dre.add(Album.getAlbum("Doggystyle", "Snoop Dogg", Reference.getUserName()));
		dre.add(Artist.getArtist("Dr. Dre", Reference.getUserName()));
		dre.add(Artist.getArtist("Snoop Dogg", Reference.getUserName()));
		dre.add(Artist.getArtist("Eminem", Reference.getUserName()));

		return dre;
	}
	
	public static FMObjList getWu() {
		FMObjList wu = new FMObjList("Wu-Tang Clan");

		wu.add(Artist.getArtist("Wu-Tang Clan", Reference.getUserName()));
		wu.add(Artist.getArtist("GZA/Genius", Reference.getUserName()));
		wu.add(Artist.getArtist("Ol' Dirty Bastard", Reference.getUserName()));
		wu.add(Artist.getArtist("Ghostface Killah", Reference.getUserName()));
		wu.add(Artist.getArtist("Method Man", Reference.getUserName()));
		wu.add(Artist.getArtist("Raekwon", Reference.getUserName()));

		return wu;
	}

	public static FMObjList getEmoTrio() {
		FMObjList emoTrio = new FMObjList("Classic Emo Trio");

		emoTrio.add(Artist.getArtist("My Chemical Romance", Reference.getUserName()));
		emoTrio.add(Artist.getArtist("Fall Out Boy", Reference.getUserName()));
		emoTrio.add(Artist.getArtist("Panic! at the Disco", Reference.getUserName()));

		return emoTrio;
	}
}
