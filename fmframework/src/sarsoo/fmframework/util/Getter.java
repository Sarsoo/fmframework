package sarsoo.fmframework.util;

import javax.swing.JOptionPane;

import org.w3c.dom.Document;

import sarsoo.fmframework.music.Album;
import sarsoo.fmframework.music.Artist;
import sarsoo.fmframework.music.Track;
import sarsoo.fmframework.net.Network;
import sarsoo.fmframework.net.TestCall;
import sarsoo.fmframework.net.URLBuilder;
import sarsoo.fmframework.parser.Parser;

public class Getter {
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

		String url = URLBuilder.getLastTrackUrl(Reference.getUserName());
//		TestCall.test(url);
		Document doc = Network.getResponse(url);
		
//		System.out.println(doc.getDocumentElement().getAttribute("status"));
		if (doc != null) {
			// System.out.println(doc.getDocumentElement().getAttribute("status"));
			Parser.stripSpace(doc.getDocumentElement());
			Track track = Parser.parseLastTrack(doc);
			// return null;
			return track;
		}
		return null;

	}
	
	public static int getScrobbles(String username) {
		String url = URLBuilder.getUserInfoUrl(username);
		Document doc = Network.getResponse(url);
		if (doc.getDocumentElement().getAttribute("status").equals("ok")) {
			String scrobbles = doc.getElementsByTagName("playcount").item(0).getTextContent();
			if(scrobbles != null)
				return Integer.parseInt(scrobbles);
		}
		return 0;
	}

}
