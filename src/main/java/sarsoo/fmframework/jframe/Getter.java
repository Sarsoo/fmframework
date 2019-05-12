package sarsoo.fmframework.jframe;

import javax.swing.JOptionPane;

import sarsoo.fmframework.config.Config;
import sarsoo.fmframework.fm.FmUserNetwork;
import sarsoo.fmframework.fx.FmFramework;
import sarsoo.fmframework.music.Album;
import sarsoo.fmframework.music.Artist;
import sarsoo.fmframework.music.Track;

public class Getter {

	public static Album getAlbum() {
		Config config = FmFramework.getSessionConfig();
		
		FmUserNetwork net = new FmUserNetwork(config.getValue("api_key"), config.getValue("username"));
		String albumName = JOptionPane.showInputDialog(null, "Enter Album Name");
		if (albumName != null) {
			String artistName = JOptionPane.showInputDialog(null, "Enter Artist Name");
			if (artistName != null) {
				return net.getAlbum(albumName, artistName);
			}
		}
		return null;
	}

	public static Artist getArtist() {
		Config config = FmFramework.getSessionConfig();
		
		FmUserNetwork net = new FmUserNetwork(config.getValue("api_key"), config.getValue("username"));
		String artistName = JOptionPane.showInputDialog(null, "Enter Artist Name");
		if (artistName != null) {
			return net.getArtist(artistName);
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
				return net.getTrack(trackName, artistName);
			}
		}
		return null;
	}

	public static Track getTrack(Album album) {
		Config config = FmFramework.getSessionConfig();
		
		FmUserNetwork net = new FmUserNetwork(config.getValue("api_key"), config.getValue("username"));
		String trackName = JOptionPane.showInputDialog(null, "Enter Track Name");
		if (trackName != null) {
			Track track = net.getTrack(trackName, album.getArtist().getName());
			track.setAlbum(album);
			return track;
		}
		return null;
	}

}
