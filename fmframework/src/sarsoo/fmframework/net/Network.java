package sarsoo.fmframework.net;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class Network {

	public static Document getResponse(String urlString) {
		try {
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			InputStream input = conn.getInputStream();

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder;
			try {
				dBuilder = dbFactory.newDocumentBuilder();
				try {
					Document doc = dBuilder.parse(input);
					conn.disconnect();
					doc.getDocumentElement().normalize();

					return doc;

				} catch (SAXException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			}

		} catch (

		MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
		return null;

	}

	public static String getArtistInfoUrl(String artist, String username) {
			String urlString = String.format(
					"http://ws.audioscrobbler.com/2.0/?method=artist.getInfo&artist=%s&autocorrect=1&username=%s&api_key=%s",
					artist, username, Key.getKey());
			return urlString;
			
			
	}

	public static String getAlbumInfoUrl(String album, String artist,  String username) {
			String urlString = String.format(
					"http://ws.audioscrobbler.com/2.0/?method=album.getInfo&album=%s&artist=%s&autocorrect=1&username=%s&api_key=%s",
						album, artist, username, Key.getKey());
			return urlString;
	}
	
	public static String getTrackInfoUrl(String name, String artist,  String username) {
		String urlString = String.format(
				"http://ws.audioscrobbler.com/2.0/?method=track.getInfo&track=%s&artist=%s&autocorrect=1&username=%s&api_key=%s",
					name, artist, username, Key.getKey());
		return urlString;
}
}
