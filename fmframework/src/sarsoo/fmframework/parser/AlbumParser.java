package sarsoo.fmframework.parser;

import javax.xml.parsers.*;

import java.io.IOException;
import java.io.InputStream;

import sarsoo.fmframework.music.Album;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
 

public class AlbumParser {
	
	public static Album parseAlbum(InputStream input){
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			try {
				Document doc = dBuilder.parse(input);
				doc.getDocumentElement().normalize();
				
				System.out.println("Root Element: " + doc.getDocumentElement().getNodeName());
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}

}
