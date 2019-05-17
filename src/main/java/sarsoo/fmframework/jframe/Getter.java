package sarsoo.fmframework.jframe;

import javax.swing.JOptionPane;

import sarsoo.fmframework.cache.StaticCache;
import sarsoo.fmframework.config.Config;
import sarsoo.fmframework.fm.FmUserNetwork;
import sarsoo.fmframework.fx.FmFramework;
import sarsoo.fmframework.music.Album;
import sarsoo.fmframework.music.Artist;
import sarsoo.fmframework.music.Track;
import sarsoo.fmframework.music.Album.AlbumBuilder;
import sarsoo.fmframework.music.Artist.ArtistBuilder;
import sarsoo.fmframework.music.Track.TrackBuilder;

public class Getter {

	public static Album getAlbum() {
		Config config = FmFramework.getSessionConfig();
		
//		FmUserNetwork net = new FmUserNetwork(config.getValue("api_key"), config.getValue("username"));
		
		String albumName = JOptionPane.showInputDialog(null, "Enter Album Name");
		if (albumName != null) {
			String artistName = JOptionPane.showInputDialog(null, "Enter Artist Name");
			if (artistName != null) {
				StaticCache<Album, Album> albumCache = FmFramework.getAlbumPool();
				StaticCache<Artist, Artist> artistCache = FmFramework.getArtistPool();
				
				Artist artist = artistCache.get(new ArtistBuilder(artistName).build());
				Album album = albumCache.get(new AlbumBuilder(albumName, artist).build());
				return album;
			}
		}
		return null;
	}

	public static Artist getArtist() {
		Config config = FmFramework.getSessionConfig();
		
		FmUserNetwork net = new FmUserNetwork(config.getValue("api_key"), config.getValue("username"));
		String artistName = JOptionPane.showInputDialog(null, "Enter Artist Name");
		if (artistName != null) {
			StaticCache<Artist, Artist> artistCache = FmFramework.getArtistPool();
			
			return artistCache.get(new ArtistBuilder(artistName).build());
		}
		return null;
	}

	public static Track getTrack() {
		Config config = FmFramework.getSessionConfig();
		
		FmUserNetwork net = new FmUserNetwork(config.getValue("api_key"), config.getValue("username"));
		String trackName = JOptionPane.showInputDialog(null, "Enter Track Name");
		if (trackName != null) {
			String artistName = JOptionPane.showInputDialog(null, "Enter Artist Name");
			if (artistName != null) {
				
				StaticCache<Artist, Artist> artistCache = FmFramework.getArtistPool();
				StaticCache<Track, Track> trackCache = FmFramework.getTrackPool();
				
				Artist artist = artistCache.get(new ArtistBuilder(artistName).build());
				
				return trackCache.get(new TrackBuilder(trackName, artist).build());
			}
		}
		return null;
	}

}
