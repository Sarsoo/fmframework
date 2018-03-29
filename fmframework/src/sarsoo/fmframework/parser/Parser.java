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
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import sarsoo.fmframework.music.Album;
import sarsoo.fmframework.music.Artist;
import sarsoo.fmframework.music.Track;
import sarsoo.fmframework.music.Wiki;
import sarsoo.fmframework.util.Reference;

public class Parser {

	public static Album parseAlbum(Document doc) {
		if (doc.getDocumentElement().getAttribute("status").equals("ok")) {
			String name = doc.getElementsByTagName("name").item(0).getTextContent();
			String artist = doc.getElementsByTagName("artist").item(0).getTextContent();

			String mbid = null;
			NodeList mbidNodeList = doc.getElementsByTagName("mbid");
			if (mbidNodeList.item(0) != null) {
				mbid = doc.getElementsByTagName("mbid").item(0).getTextContent();
			}

			String url = doc.getElementsByTagName("url").item(0).getTextContent();
			int listeners = Integer.parseInt(doc.getElementsByTagName("listeners").item(0).getTextContent());
			int playCount = Integer.parseInt(doc.getElementsByTagName("playcount").item(0).getTextContent());

			int userPlayCount = 0;
			NodeList userPlayCountNodeList = doc.getElementsByTagName("userplaycount");
			if (userPlayCountNodeList.item(0) != null) {
				userPlayCount = Integer.parseInt(userPlayCountNodeList.item(0).getTextContent());
			}

			Artist artistObj = Artist.getArtist(artist, Reference.getUserName());

			Wiki wiki = null;
			NodeList wikiNodeList = doc.getElementsByTagName("wiki");
			if (wikiNodeList.item(0) != null) {
				String date = wikiNodeList.item(0).getFirstChild().getTextContent();

				Node summaryNode = wikiNodeList.item(0).getFirstChild().getNextSibling();
				while (!(summaryNode instanceof Element) && summaryNode != null) {
					summaryNode = summaryNode.getNextSibling();
				}
				String summary = summaryNode.getTextContent();
				// System.out.println(summary);

				Node contentNode = summaryNode.getNextSibling();
				while (!(contentNode instanceof Element) && contentNode != null) {
					contentNode = contentNode.getNextSibling();
				}
				String content = contentNode.getTextContent();
				// System.out.println(content);

				wiki = new Wiki(date, summary, content);
			}

			Album album = new Album(name, url, mbid, artistObj, listeners, playCount, userPlayCount, wiki);

			return album;
		}
		return null;
	}

	public static Artist parseArtist(Document doc) {
		if (doc.getDocumentElement().getAttribute("status").equals("ok")) {
			String name = doc.getElementsByTagName("name").item(0).getTextContent();
			String mbid = null;

			NodeList mbidNodeList = doc.getElementsByTagName("mbid");
			if (mbidNodeList.item(0) != null) {
				mbid = doc.getElementsByTagName("mbid").item(0).getTextContent();
			}

			String url = doc.getElementsByTagName("url").item(0).getTextContent();

			int listeners = Integer.parseInt(doc.getElementsByTagName("listeners").item(0).getTextContent());
			int playCount = Integer.parseInt(doc.getElementsByTagName("playcount").item(0).getTextContent());

			int userPlayCount = 0;
			NodeList userPlayCountNodeList = doc.getElementsByTagName("userplaycount");
			if (userPlayCountNodeList.item(0) != null) {
				userPlayCount = Integer.parseInt(userPlayCountNodeList.item(0).getTextContent());
			}

			Wiki wiki = null;
			NodeList wikiNodeList = doc.getElementsByTagName("bio");
			if (wikiNodeList.getLength() != 0) {
				Node wikiNode = wikiNodeList.item(0);
				NodeList wikiContents = wikiNode.getChildNodes();

				String date = null;
				String summary = null;
				String content = null;

				int counter;
				Node node;
				for (counter = 0; counter < wikiContents.getLength(); counter++) {
					node = wikiContents.item(counter);
					System.out.println(node.getNodeName() + node.getTextContent());
					if (node.getNodeName().equals("published")) {
						date = node.getTextContent();
					}
					if (node.getNodeName().equals("summary")) {
						summary = node.getTextContent();
					}
					if (node.getNodeName().equals("content")) {
						content = node.getTextContent();
					}
				}

				if (date != null && content != null)
					wiki = new Wiki(date, summary, content);
				// if (wikiNodeList.item(0) != null) {
				// String date = wikiNodeList.item(0).getFirstChild().getTextContent();
				//
				// Node summaryNode = wikiNodeList.item(0).getFirstChild().getNextSibling();
				// while (!(summaryNode instanceof Element) && summaryNode != null) {
				// summaryNode = summaryNode.getNextSibling();
				// }
				// String summary = summaryNode.getTextContent();
				// // System.out.println(summary);
				//
				// Node contentNode = summaryNode.getNextSibling();
				// while (!(contentNode instanceof Element) && contentNode != null) {
				// contentNode = contentNode.getNextSibling();
				// }
				// String content = contentNode.getTextContent();
				// // System.out.println(content);
				//
				// wiki = new Wiki(date, summary, content);
				// }
			}

			Artist artist = new Artist(name, url, mbid, listeners, playCount, userPlayCount, false, false, null);
			return artist;
		}
		return null;
	}

	public static Track parseTrack(Document doc) {
		if (doc.getDocumentElement().getAttribute("status").equals("ok")) {
			String name = " ";
			try {
				name = doc.getElementsByTagName("name").item(0).getTextContent();
			} catch (NullPointerException e) {
				System.err.println("Could Not Parse Track Name");
				return null;
			}
			String artistName = " ";
			try {
				artistName = doc.getElementsByTagName("artist").item(0).getFirstChild().getTextContent();
			} catch (NullPointerException e) {
				System.err.println("Could Not Parse Artist Name for " + name);
				return null;
			}
			String mbid = null;

			NodeList mbidNodeList = doc.getElementsByTagName("mbid");
			if (mbidNodeList.item(0) != null) {
				mbid = doc.getElementsByTagName("mbid").item(0).getTextContent();
			}

			String url = null;

			url = doc.getElementsByTagName("url").item(0).getTextContent();

			int listeners = Integer.parseInt(doc.getElementsByTagName("listeners").item(0).getTextContent());
			int playCount = Integer.parseInt(doc.getElementsByTagName("playcount").item(0).getTextContent());

			int userPlayCount = 0;

			NodeList userPlayCountNodeList = doc.getElementsByTagName("userplaycount");
			if (userPlayCountNodeList.item(0) != null) {
				userPlayCount = Integer.parseInt(userPlayCountNodeList.item(0).getTextContent());
			}

			Artist artistObj = Artist.getArtist(artistName, Reference.getUserName());

			Wiki wiki = null;
			NodeList wikiNodeList = doc.getElementsByTagName("wiki");
			if (wikiNodeList.item(0) != null) {
				String date = wikiNodeList.item(0).getFirstChild().getTextContent();

				Node summaryNode = wikiNodeList.item(0).getFirstChild().getNextSibling();
				while (!(summaryNode instanceof Element) && summaryNode != null) {
					summaryNode = summaryNode.getNextSibling();
				}
				String summary = summaryNode.getTextContent();
				// System.out.println(summary);

				Node contentNode = summaryNode.getNextSibling();
				while (!(contentNode instanceof Element) && contentNode != null) {
					contentNode = contentNode.getNextSibling();
				}
				String content = contentNode.getTextContent();
				// System.out.println(content);

				wiki = new Wiki(date, summary, content);
			}

			Track track = new Track(name, url, mbid, artistObj, listeners, playCount, userPlayCount, null);
			return track;
		} else {
			return null;
		}

	}

	public static Track parseLastTrack(Document doc) {

		if (doc.getDocumentElement().getAttribute("status").equals("ok")) {
			String name = doc.getElementsByTagName("name").item(0).getTextContent();
			// System.out.println(name);
			String artistName = doc.getElementsByTagName("artist").item(0).getTextContent();

			String albumName = doc.getElementsByTagName("album").item(0).getTextContent();

			Track track = Track.getTrack(name, artistName, Reference.getUserName());

			Album album = Album.getAlbum(albumName, artistName, Reference.getUserName());

			track.setAlbum(album);

			return track;
		}

		return null;

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
