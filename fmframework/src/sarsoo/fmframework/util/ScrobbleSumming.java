package sarsoo.fmframework.util;
import sarsoo.fmframework.music.Artist;

public class ScrobbleSumming {
	
	public static int getTopDawgScrobbles() {
		ArtistList tde = new ArtistList();
		
//		Artist kendrick = Artist.getArtist("Kendrick Lamar", "sarsoo");
//		Artist jay = Artist.getArtist("Jay Rock", "sarsoo");
//		Artist school = Artist.getArtist("ScHoolboy Q", "sarsoo");
//		Artist ab = Artist.getArtist("Ab-Soul", "sarsoo");
//		Artist blackHippy = Artist.getArtist("Black Hippy", "sarsoo");
//		Artist isaiah = Artist.getArtist("Isaiah Rashad", "sarsoo");
//		Artist sza = Artist.getArtist("SZA", "sarsoo");
//		Artist lance = Artist.getArtist("Lance Skiiiwalker", "sarsoo");
//		Artist sir = Artist.getArtist("SiR", "sarsoo");
		
		tde.add(Artist.getArtist("Kendrick Lamar", "sarsoo"));
		tde.add(Artist.getArtist("Jay Rock", "sarsoo"));
		tde.add(Artist.getArtist("ScHoolboy Q", "sarsoo"));
		tde.add(Artist.getArtist("Ab-Soul", "sarsoo"));
		tde.add(Artist.getArtist("Black Hippy", "sarsoo"));
		tde.add(Artist.getArtist("Isaiah Rashad", "sarsoo"));
		tde.add(Artist.getArtist("SZA", "sarsoo"));
		tde.add(Artist.getArtist("Lance Skiiiwalker", "sarsoo"));
		tde.add(Artist.getArtist("SiR", "sarsoo"));
		
		return tde.getTotalUserScrobbles();
	}
	
}
