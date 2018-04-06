package sarsoo.fmframework.util;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import sarsoo.fmframework.music.Album;
import sarsoo.fmframework.music.Artist;
import sarsoo.fmframework.music.Tag;
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
		// TestCall.test(url);
		Document doc = Network.getResponse(url);

		// System.out.println(doc.getDocumentElement().getAttribute("status"));
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
			if (scrobbles != null)
				return Integer.parseInt(scrobbles);
		}
		return 0;
	}

	public static int getScrobblesToday(String username) {
		String url = URLBuilder.getTodayScrobbles(username);
		Document doc = Network.getResponse(url);
		if (doc.getDocumentElement().getAttribute("status").equals("ok")) {
			Node node = doc.getChildNodes().item(0).getFirstChild();
			NamedNodeMap var = node.getAttributes();
//			System.out.println(var.getNamedItem("total").getNodeValue());
			// if (scrobbles != null)
			return Integer.parseInt(var.getNamedItem("total").getNodeValue());
		}
		return 0;
	}

	public static FMObjList getUserTag(String username, String tag) {
		String url = URLBuilder.getUserPersonalTags(username, tag);
		Document doc = Network.getResponse(url);
		if (doc != null) {
			return Parser.parseUserTagList(doc);
		}
		return null;
	}

	public static ArrayList<Tag> getUserTags(String username) {
		String url = URLBuilder.getUserTopTags(username);
		Document doc = Network.getResponse(url);
		if (doc != null) {
			return Parser.parseUserTags(doc);
		}
		return null;
	}

}
