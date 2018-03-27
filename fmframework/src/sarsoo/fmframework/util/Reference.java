package sarsoo.fmframework.util;

import sarsoo.fmframework.music.Artist;

public class Reference {
	private static String userName;

	public static String getUserName() {
		return userName;
	}

	public static void setUserName(String userNameIn) {
		userName = userNameIn;
	}
	
	public static ArtistList getTDE() {
		ArtistList tde = new ArtistList("TDE");
		
		tde.add(Artist.getArtist("Kendrick Lamar", Reference.getUserName()));
		tde.add(Artist.getArtist("Jay Rock", Reference.getUserName()));
		tde.add(Artist.getArtist("ScHoolboy Q", Reference.getUserName()));
		tde.add(Artist.getArtist("Ab-Soul", Reference.getUserName()));
		tde.add(Artist.getArtistByMbid("6fc5c0c6-bf05-4b29-bda0-5fa6cc863785", Reference.getUserName())); //Black Hippy
		tde.add(Artist.getArtist("Isaiah Rashad", Reference.getUserName()));
		tde.add(Artist.getArtist("SZA", Reference.getUserName()));
		tde.add(Artist.getArtist("Sir", Reference.getUserName()));
		
		return tde;
	}
	
}
