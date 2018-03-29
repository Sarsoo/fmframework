package sarsoo.fmframework.util;

import javax.swing.JOptionPane;

import org.w3c.dom.Document;

import sarsoo.fmframework.music.Album;
import sarsoo.fmframework.music.Artist;
import sarsoo.fmframework.music.Track;
import sarsoo.fmframework.net.Network;
import sarsoo.fmframework.parser.Parser;

public class GetObject {
	public static Album getAlbum() {
		String artistName = JOptionPane.showInputDialog(null, "Enter Artist Name");
		if (artistName != null) {
			String albumName = JOptionPane.showInputDialog(null, "Enter Album Name");
			if (albumName != null) {
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

	public static Track getLastTrack() {

		String url = Network.getLastTrackUrl(Reference.getUserName());
		Document doc = Network.getResponse(url);
//		System.out.println(doc.getDocumentElement().getAttribute("status"));
		Parser.stripSpace(doc.getDocumentElement());
		Track track = Parser.parseLastTrack(doc);
//		return null;
		return track;

	}

}
