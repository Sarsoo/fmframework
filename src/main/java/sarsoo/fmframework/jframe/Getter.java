package sarsoo.fmframework.jframe;

import javax.swing.JOptionPane;

import sarsoo.fmframework.music.Album;
import sarsoo.fmframework.music.Artist;
import sarsoo.fmframework.music.Track;
import sarsoo.fmframework.util.Reference;

public class Getter {

	public static Album getAlbum() {
		String albumName = JOptionPane.showInputDialog(null, "Enter Album Name");
		if (albumName != null) {
			String artistName = JOptionPane.showInputDialog(null, "Enter Artist Name");
			if (artistName != null) {
				return Album.getAlbum(albumName, artistName, Reference.getUserName());
			}
		}
		return null;
	}

	public static Artist getArtist() {
		String artistName = JOptionPane.showInputDialog(null, "Enter Artist Name");
		if (artistName != null) {
			return Artist.getArtist(artistName, Reference.getUserName());
		}
		return null;
	}

	public static Track getTrack() {
		String trackName = JOptionPane.showInputDialog(null, "Enter Track Name");
		if (trackName != null) {
			String artistName = JOptionPane.showInputDialog(null, "Enter Artist Name");
			if (artistName != null) {
				return Track.getTrack(artistName, trackName, Reference.getUserName());
			}
		}
		return null;
	}

	public static Track getTrack(Album album) {
		String trackName = JOptionPane.showInputDialog(null, "Enter Track Name");
		if (trackName != null) {
			Track track = Track.getTrack(album.getArtist().getName(), trackName, Reference.getUserName());
			track.setAlbum(album);
			return track;
		}
		return null;
	}

}
