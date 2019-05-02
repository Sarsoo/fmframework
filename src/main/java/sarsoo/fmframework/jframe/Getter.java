package sarsoo.fmframework.jframe;

import javax.swing.JOptionPane;

import sarsoo.fmframework.fm.FmUserNetwork;
import sarsoo.fmframework.music.Album;
import sarsoo.fmframework.music.Artist;
import sarsoo.fmframework.music.Track;
import sarsoo.fmframework.net.Key;
import sarsoo.fmframework.util.Reference;

public class Getter {

	public static Album getAlbum() {
		FmUserNetwork net = new FmUserNetwork(Key.getKey(), Reference.getUserName());
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
		FmUserNetwork net = new FmUserNetwork(Key.getKey(), Reference.getUserName());
		String artistName = JOptionPane.showInputDialog(null, "Enter Artist Name");
		if (artistName != null) {
			return net.getArtist(artistName);
		}
		return null;
	}

	public static Track getTrack() {
		FmUserNetwork net = new FmUserNetwork(Key.getKey(), Reference.getUserName());
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
		FmUserNetwork net = new FmUserNetwork(Key.getKey(), Reference.getUserName());
		String trackName = JOptionPane.showInputDialog(null, "Enter Track Name");
		if (trackName != null) {
			Track track = net.getTrack(trackName, album.getArtist().getName());
			track.setAlbum(album);
			return track;
		}
		return null;
	}

}
