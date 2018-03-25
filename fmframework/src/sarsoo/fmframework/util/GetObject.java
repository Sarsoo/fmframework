package sarsoo.fmframework.util;

import javax.swing.JOptionPane;

import sarsoo.fmframework.music.Album;

public class GetObject {
	public static Album getAlbum() {
		String artistName = JOptionPane.showInputDialog(null, "Enter Artist Name");
		String albumName = JOptionPane.showInputDialog(null, "Enter Album Name");
		return Album.getAlbum(albumName, artistName, "sarsoo");
	}

}
