package sarsoo.fmframework.parser;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import sarsoo.fmframework.music.Album;
import sarsoo.fmframework.music.Artist;
import sarsoo.fmframework.music.Track;
import sarsoo.fmframework.music.Wiki;

public class Parser {

	public static Album parseAlbum(Document doc) {

		String name = doc.getElementsByTagName("name").item(0).getTextContent();
		String artist = doc.getElementsByTagName("artist").item(0).getTextContent();
		String mbid = doc.getElementsByTagName("mbid").item(0).getTextContent();
		String url = doc.getElementsByTagName("url").item(0).getTextContent();
		int listeners = Integer.parseInt(doc.getElementsByTagName("listeners").item(0).getTextContent());
		int playCount = Integer.parseInt(doc.getElementsByTagName("playcount").item(0).getTextContent());
		int userPlayCount = Integer.parseInt(doc.getElementsByTagName("userplaycount").item(0).getTextContent());

//		Node trackListNode = doc.getElementsByTagName("tracks").item(0);
//		NodeList trackNodeList = trackListNode.getChildNodes();
//		if (trackListNode != null) {
//			int counter = 0;
//			while (trackNodeList.item(counter) != null) {
//				Node track = trackNodeList.item(counter);
//				//System.out.println(track.getTextContent());
//				String trackName = track.getFirstChild().getTextContent();
//				String trackUrl = track.getFirstChild().getNextSibling().getNextSibling().getTextContent();
//				//String trackDuration = track.getFirstChild().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getTextContent();
//				int trackNumber = Integer.parseInt(track.getAttributes().getNamedItem("rank").getNodeValue());
//				// String trackName =
//				// trackListNode.getFirstChild().getFirstChild().getTextContent();
//				// String trackUrl = trackListNode.getNextSibling().getTextContent();
//				System.out.println(trackUrl);
//				counter++;
//			}
//		}

		Node wikiNode = doc.getElementsByTagName("wiki").item(0);
		String published = wikiNode.getFirstChild().getTextContent();
		String summary = wikiNode.getFirstChild().getNextSibling().getTextContent();
		String content = wikiNode.getFirstChild().getNextSibling().getNextSibling().getTextContent();

		// System.out.println(published);
		// System.out.println(summary);
		// System.out.println(content);

		Artist artistObj = new Artist(artist);
		Wiki wiki = new Wiki();

		Album album = new Album(name, url, mbid, artistObj, listeners, playCount, userPlayCount, wiki);
		return album;

	}

	public static Artist parseArtist(Document doc) {

		String name = doc.getElementsByTagName("name").item(0).getTextContent();
		String mbid = doc.getElementsByTagName("mbid").item(0).getTextContent();
		String url = doc.getElementsByTagName("url").item(0).getTextContent();
		String streamable = doc.getElementsByTagName("streamable").item(0).getTextContent();
		int listeners = Integer.parseInt(doc.getElementsByTagName("listeners").item(0).getTextContent());
		int playCount = Integer.parseInt(doc.getElementsByTagName("playcount").item(0).getTextContent());
		int userPlayCount = Integer.parseInt(doc.getElementsByTagName("userplaycount").item(0).getTextContent());
		
		//System.out.println(listeners);

		Artist artist = new Artist(name, url, mbid, listeners, playCount, userPlayCount, false, false, null);
		return artist;
	}

	public static Track parseTrack(Document doc) {

		String name = doc.getElementsByTagName("name").item(0).getTextContent();
		String artistName = doc.getElementsByTagName("artist").item(0).getFirstChild().getTextContent();
		String mbid = doc.getElementsByTagName("mbid").item(0).getTextContent();
		String url = doc.getElementsByTagName("url").item(0).getTextContent();
		int listeners = Integer.parseInt(doc.getElementsByTagName("listeners").item(0).getTextContent());
		int playCount = Integer.parseInt(doc.getElementsByTagName("playcount").item(0).getTextContent());
		int userPlayCount = Integer.parseInt(doc.getElementsByTagName("userplaycount").item(0).getTextContent());

		Artist artistObj = new Artist(artistName);
		
//		System.out.println(userPlayCount);

		Track track = new Track(name, url, mbid, listeners, playCount, userPlayCount, null);
		return track;

	}
}
