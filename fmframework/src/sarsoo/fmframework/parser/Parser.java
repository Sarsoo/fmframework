package sarsoo.fmframework.parser;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import sarsoo.fmframework.music.Album;
import sarsoo.fmframework.music.Artist;
import sarsoo.fmframework.music.Track;
import sarsoo.fmframework.music.Wiki;
import sarsoo.fmframework.util.Reference;

public class Parser {

	public static Album parseAlbum(Document doc) {
		try {
			String name = doc.getElementsByTagName("name").item(0).getTextContent();
			String artist = doc.getElementsByTagName("artist").item(0).getTextContent();
			String mbid = null;
			try {
				mbid = doc.getElementsByTagName("mbid").item(0).getTextContent();
			} catch (NullPointerException e) {
				System.err.println("Null Mbid for " + name);
			}
			String url = doc.getElementsByTagName("url").item(0).getTextContent();
			int listeners = Integer.parseInt(doc.getElementsByTagName("listeners").item(0).getTextContent());
			int playCount = Integer.parseInt(doc.getElementsByTagName("playcount").item(0).getTextContent());

			int userPlayCount = 0;
			try {
				userPlayCount = Integer.parseInt(doc.getElementsByTagName("userplaycount").item(0).getTextContent());
			} catch (Exception e) {
				System.err.println("Couldn't parse userPlayCount, possibly unscrobbled for " + name);
			}

			// Node trackListNode = doc.getElementsByTagName("tracks").item(0);
			// NodeList trackNodeList = trackListNode.getChildNodes();
			// if (trackListNode != null) {
			// int counter = 0;
			// while (trackNodeList.item(counter) != null) {
			// Node track = trackNodeList.item(counter);
			// //System.out.println(track.getTextContent());
			// String trackName = track.getFirstChild().getTextContent();
			// String trackUrl =
			// track.getFirstChild().getNextSibling().getNextSibling().getTextContent();
			// //String trackDuration =
			// track.getFirstChild().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getTextContent();
			// int trackNumber =
			// Integer.parseInt(track.getAttributes().getNamedItem("rank").getNodeValue());
			// // String trackName =
			// // trackListNode.getFirstChild().getFirstChild().getTextContent();
			// // String trackUrl = trackListNode.getNextSibling().getTextContent();
			// System.out.println(trackUrl);
			// counter++;
			// }
			// }

			// Node wikiNode = doc.getElementsByTagName("wiki").item(0);
			// String published = wikiNode.getFirstChild().getTextContent();
			// String summary = wikiNode.getFirstChild().getNextSibling().getTextContent();
			// String content =
			// wikiNode.getFirstChild().getNextSibling().getNextSibling().getTextContent();

			// System.out.println(published);
			// System.out.println(summary);
			// System.out.println(content);

			Artist artistObj = Artist.getArtist(artist, Reference.getUserName());
			Wiki wiki = new Wiki();

			Album album = new Album(name, url, mbid, artistObj, listeners, playCount, userPlayCount, wiki);
			return album;
		} catch (NullPointerException e) {
			System.err.println("Could Not Parse Album");
			return null;
		}

	}

	public static Artist parseArtist(Document doc) {
		try {
			String name = doc.getElementsByTagName("name").item(0).getTextContent();
			String mbid = null;
			try {
				mbid = doc.getElementsByTagName("mbid").item(0).getTextContent();
			} catch (NullPointerException e) {
				System.err.println("Null Mbid for " + name);
			}
			String url = doc.getElementsByTagName("url").item(0).getTextContent();
			// String streamable =
			// doc.getElementsByTagName("streamable").item(0).getTextContent();
			int listeners = Integer.parseInt(doc.getElementsByTagName("listeners").item(0).getTextContent());
			int playCount = Integer.parseInt(doc.getElementsByTagName("playcount").item(0).getTextContent());

			int userPlayCount = 0;
			try {
				userPlayCount = Integer.parseInt(doc.getElementsByTagName("userplaycount").item(0).getTextContent());
			} catch (Exception e) {
				System.err.println("Couldn't parse userPlayCount, possibly unscrobbled for " + name);
			}
			// System.out.println(listeners);

			Artist artist = new Artist(name, url, mbid, listeners, playCount, userPlayCount, false, false, null);
			return artist;
		} catch (NullPointerException e) {
			System.err.println("Could Not Parse Artist");
			return null;
		}
	}

	public static Track parseTrack(Document doc) {

		try {
			String name = doc.getElementsByTagName("name").item(0).getTextContent();
			String artistName = doc.getElementsByTagName("artist").item(0).getFirstChild().getTextContent();
			String mbid = null;
			try {
				mbid = doc.getElementsByTagName("mbid").item(0).getTextContent();
			} catch (NullPointerException e) {
				System.err.println("Null Mbid for " + name + " - " + artistName);
			}
			String url = doc.getElementsByTagName("url").item(0).getTextContent();
			int listeners = Integer.parseInt(doc.getElementsByTagName("listeners").item(0).getTextContent());
			int playCount = Integer.parseInt(doc.getElementsByTagName("playcount").item(0).getTextContent());

			int userPlayCount = 0;
			try {
				userPlayCount = Integer.parseInt(doc.getElementsByTagName("userplaycount").item(0).getTextContent());
			} catch (Exception e) {
				System.err.println("Couldn't parse userPlayCount, possibly unscrobbled for " + name);
			}

			// String albumName =
			// doc.getElementsByTagName("album").item(0).getTextContent();
			// System.out.println(albumName);
			Artist artistObj = Artist.getArtist(artistName, Reference.getUserName());

			// System.out.println(userPlayCount);

			Track track = new Track(name, url, mbid, artistObj, listeners, playCount, userPlayCount, null);
			return track;
		} catch (NullPointerException e) {
			System.err.println("Could Not Parse Track");
			return null;
		}
	}

	public static Track parseLastTrack(Document doc) {
		try {
			String name = doc.getElementsByTagName("name").item(0).getTextContent();

			String artistName = doc.getElementsByTagName("artist").item(0).getTextContent();

			String albumName = doc.getElementsByTagName("album").item(0).getTextContent();

			Track track = Track.getTrack(name, artistName, Reference.getUserName());

			Album album = Album.getAlbum(albumName, artistName, Reference.getUserName());

			track.setAlbum(album);

			return track;
		} catch (NullPointerException e) {
			System.err.println("Could Not Parse Track");
			return null;
		}

	}

	public static void printDocument(Document doc, OutputStream out) throws IOException, TransformerException {
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

		transformer.transform(new DOMSource(doc), new StreamResult(new OutputStreamWriter(out, "UTF-8")));
	}

	public static void stripSpace(Node node) {
		Node child = node.getFirstChild();
		while (child != null) {
			// save the sibling of the node that will
			// perhaps be removed and set to null
			Node c = child.getNextSibling();
			if ((child.getNodeType() == Node.TEXT_NODE && child.getNodeValue().trim().length() == 0)
					|| ((child.getNodeType() != Node.TEXT_NODE) && (child.getNodeType() != Node.ELEMENT_NODE)))
				node.removeChild(child);
			else // process children recursively
				stripSpace(child);
			child = c;
		}
	}

	public static void compact(Node node, String indent) {
		if (node == null)
			return;
		switch (node.getNodeType()) {
		case Node.ELEMENT_NODE: {
			System.out.print(node.getNodeName() + '[');
			indent += blanks(node.getNodeName().length() + 1);
			NamedNodeMap attrs = node.getAttributes();
			boolean first = true;
			for (int i = 0; i < attrs.getLength(); i++) {
				if (!first)
					System.out.print('\n' + indent);
				System.out.print('@' + attrs.item(i).getNodeName() + '[' + attrs.item(i).getNodeValue() + ']');
				first = false;
			}
			for (Node child = node.getFirstChild(); child != null; child = child.getNextSibling()) {
				if (!first)
					System.out.print('\n' + indent);
				compact(child, indent);
				first = false;
			}
			System.out.print(']');
			break;
		}
		case Node.TEXT_NODE: {
			System.out.print(node.getNodeValue().trim());
			break;
		}
		}
	}

	// production of string of spaces with a lazy StringBuffer
	private static StringBuffer blanks = new StringBuffer();

	private static String blanks(int n) {
		for (int i = blanks.length(); i < n; i++)
			blanks.append(' ');
		return blanks.substring(0, n);
	}
}
