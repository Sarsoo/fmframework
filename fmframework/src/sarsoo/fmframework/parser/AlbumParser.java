package sarsoo.fmframework.parser;

import javax.xml.parsers.*;

import java.io.IOException;
import java.io.InputStream;

import sarsoo.fmframework.music.Album;
import sarsoo.fmframework.music.Artist;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
 

public class AlbumParser {
	
	public static Album parseAlbum(Document doc){
		
		String name = doc.getElementsByTagName("name").item(0).getTextContent();
		String artist = doc.getElementsByTagName("artist").item(0).getTextContent();
		String mbid = doc.getElementsByTagName("mbid").item(0).getTextContent();
		String url = doc.getElementsByTagName("url").item(0).getTextContent();
		int listeners = Integer.parseInt(doc.getElementsByTagName("listeners").item(0).getTextContent());
		int playCount = Integer.parseInt(doc.getElementsByTagName("playcount").item(0).getTextContent());
		int userPlayCount = Integer.parseInt(doc.getElementsByTagName("userplaycount").item(0).getTextContent());
		
		Artist artistObj = new Artist(artist);
		
		Album album = new Album(name, url, mbid, artistObj, listeners, playCount, userPlayCount);
		return album;
		
	}

}
