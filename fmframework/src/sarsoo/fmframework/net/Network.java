package sarsoo.fmframework.net;

import java.awt.Desktop;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import sarsoo.fmframework.error.ApiCallException;

public class Network {

	public static Document getResponse(String urlString) {
		try {
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/xml");

			if (conn.getResponseCode() != 200) {
				throw new ApiCallException(conn.getResponseCode());
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
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ApiCallException e) {
			System.err.println(e.getFailureCode() + " " + e.getError());
		}
		return null;

	}

	public static String getArtistInfoUrl(String artist, String username) {
		String urlString = String.format(
				"http://ws.audioscrobbler.com/2.0/?method=artist.getInfo&artist=%s&autocorrect=0&username=%s&api_key=%s",
				artist, username, Key.getKey());
		return urlString;

	}

	public static String getArtistInfoMbidUrl(String mbid, String username) {
		String urlString = String.format(
				"http://ws.audioscrobbler.com/2.0/?method=artist.getInfo&mbid=%s&autocorrect=0&username=%s&api_key=%s",
				mbid, username, Key.getKey());
		return urlString;

	}

	public static String getAlbumInfoUrl(String album, String artist, String username) {
		String urlString = String.format(
				"http://ws.audioscrobbler.com/2.0/?method=album.getInfo&album=%s&artist=%s&autocorrect=0&username=%s&api_key=%s",
				album, artist, username, Key.getKey());
		return urlString;
	}

	public static String getTrackInfoUrl(String name, String artist, String username) {
		String urlString = String.format(
				"http://ws.audioscrobbler.com/2.0/?method=track.getInfo&track=%s&artist=%s&autocorrect=0&username=%s&api_key=%s",
				name, artist, username, Key.getKey());
		return urlString;
	}

	public static String getLastTrackUrl(String username) {
		String urlString = String.format(
				"http://ws.audioscrobbler.com/2.0/?method=user.getRecentTracks&limit=1&user=%s&api_key=%s", username,
				Key.getKey());
		return urlString;
	}

	public static String getLyricsUrl(String trackName, String artistName) {
		String track = trackName.replaceAll(" ", "-");
		// track = track.replaceAll("[^a-zA-Z ]", "").toLowerCase();
		String artist = artistName.replaceAll(" ", "-");
		// artist = artist.replaceAll("[^a-zA-Z ]", "").toLowerCase();
		String urlString = String.format("http://genius.com/%s-%s-lyrics", artist, track);
		return urlString;
	}

	public static void openURL(String url) {
		try {
			Desktop desktop = java.awt.Desktop.getDesktop();
			URI oURL = new URI(url);
			desktop.browse(oURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// public static String getRecentTracaksUrl(String username) {
	//// Date date = new Date();
	//// Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
	//// Calendar cal2 = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
	////
	//// cal.set(cal.YEAR, cal.MONTH, cal.DATE);
	//// System.out.println(cal.getTime());
	// //System.out.println(cal2.getTime());
	//
	// String urlString = String.format(
	// "http://ws.audioscrobbler.com/2.0/"
	// + "?method=user.getRecentTracks&"
	// + "user=%s&"
	// + "limit = 200&"
	// + "from=%d&"
	// + "api_key=%s",
	// username, Key.getKey());
	// return urlString;
	// }
}
