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
			conn.setRequestProperty("User-Agent", "fmframework/1.0");

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

	public static void openURL(String url) {
		try {
			Desktop desktop = java.awt.Desktop.getDesktop();
			URI oURL = new URI(url);
			desktop.browse(oURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
