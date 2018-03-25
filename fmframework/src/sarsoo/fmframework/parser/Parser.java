package sarsoo.fmframework.parser;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import sarsoo.fmframework.music.Album;
import sarsoo.fmframework.music.Artist;
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
		
		
		Node wikiNode = doc.getElementsByTagName("wiki").item(0);
		String published = wikiNode.getFirstChild().getTextContent();
		String summary = wikiNode.getFirstChild().getNextSibling().getTextContent();
		String content = wikiNode.getFirstChild().getNextSibling().getNextSibling().getTextContent();
		
		//System.out.println(published);
		//System.out.println(summary);
		//System.out.println(content);
		
		Artist artistObj = new Artist(artist);
		Wiki wiki = new Wiki();

		Album album = new Album(name, url, mbid, artistObj, listeners, playCount, userPlayCount, wiki);
		return album;

	}

	public static Album parseArtist(Document doc) {

		String name = doc.getElementsByTagName("name").item(0).getTextContent();
		String artist = doc.getElementsByTagName("artist").item(0).getTextContent();
		String mbid = doc.getElementsByTagName("mbid").item(0).getTextContent();
		String url = doc.getElementsByTagName("url").item(0).getTextContent();
		int listeners = Integer.parseInt(doc.getElementsByTagName("listeners").item(0).getTextContent());
		int playCount = Integer.parseInt(doc.getElementsByTagName("playcount").item(0).getTextContent());
		int userPlayCount = Integer.parseInt(doc.getElementsByTagName("userplaycount").item(0).getTextContent());

		Artist artistObj = new Artist(artist);

		Album album = new Album(name, url, mbid, artistObj, listeners, playCount, userPlayCount, null);
		return album;

	}

	public static Album parseTrack(Document doc) {

		String name = doc.getElementsByTagName("name").item(0).getTextContent();
		String artist = doc.getElementsByTagName("artist").item(0).getTextContent();
		String mbid = doc.getElementsByTagName("mbid").item(0).getTextContent();
		String url = doc.getElementsByTagName("url").item(0).getTextContent();
		int listeners = Integer.parseInt(doc.getElementsByTagName("listeners").item(0).getTextContent());
		int playCount = Integer.parseInt(doc.getElementsByTagName("playcount").item(0).getTextContent());
		int userPlayCount = Integer.parseInt(doc.getElementsByTagName("userplaycount").item(0).getTextContent());

		Artist artistObj = new Artist(artist);

		Album album = new Album(name, url, mbid, artistObj, listeners, playCount, userPlayCount, null);
		return album;

	}
}
