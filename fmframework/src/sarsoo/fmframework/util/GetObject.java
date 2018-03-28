package sarsoo.fmframework.util;

import javax.swing.JOptionPane;

import org.w3c.dom.Document;

import sarsoo.fmframework.music.Album;
import sarsoo.fmframework.music.Track;
import sarsoo.fmframework.net.Network;
import sarsoo.fmframework.parser.Parser;

public class GetObject {
	public static Album getAlbum() {
		String artistName = JOptionPane.showInputDialog(null, "Enter Artist Name");
		String albumName = JOptionPane.showInputDialog(null, "Enter Album Name");
		return Album.getAlbum(albumName, artistName, "sarsoo");
	}
	
	public static Track getLastTrack() {
		
		String url = Network.getLastTrackUrl(Reference.getUserName());
		Document doc = Network.getResponse(url);
		Parser.stripSpace(doc.getDocumentElement());
		Track track = Parser.parseLastTrack(doc);
		
		return track;
		
	}

}
