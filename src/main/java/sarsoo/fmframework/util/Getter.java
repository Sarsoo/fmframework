package sarsoo.fmframework.util;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import sarsoo.fmframework.music.Tag;
import sarsoo.fmframework.music.Track;
import sarsoo.fmframework.net.Network;
import sarsoo.fmframework.net.URLBuilder;
import sarsoo.fmframework.parser.Parser;

@Deprecated
public class Getter {
	public static Track getLastTrack() {
		if(Reference.isVerbose())
			Reference.getConsole().write(">>getLastTrack");
		
		String url = URLBuilder.getLastTrackUrl(Reference.getUserName());
		// TestCall.test(url);
		Document doc = Network.getResponse(url);

		// System.out.println(doc.getDocumentElement().getAttribute("status"));
		if (doc != null) {
			// System.out.println(doc.getDocumentElement().getAttribute("status"));
			Parser.stripSpace(doc.getDocumentElement());
			Track track = Parser.parseLastTrack(doc);
//			if(Reference.isVerbose())
//				System.out.println(track);
			// return null;
			return track;
		}
		return null;

	}

	public static int getScrobbles(String username) {
		if(Reference.isVerbose())
			Reference.getConsole().write(">>getScrobbles");
		
		String url = URLBuilder.getUserInfoUrl(username);
		Document doc = Network.getResponse(url);
		if (doc.getDocumentElement().getAttribute("status").equals("ok")) {
			String scrobbles = doc.getElementsByTagName("playcount").item(0).getTextContent();
			if (scrobbles != null)
				if(Reference.isVerbose())
					Reference.getConsole().write("total scrobbles: " + scrobbles);
				return Integer.parseInt(scrobbles);
		}
		return 0;
	}

	public static int getScrobblesToday(String username) {
		if(Reference.isVerbose())
			Reference.getConsole().write(">>getScrobblesToday");
		
		String url = URLBuilder.getTodayScrobbles(username);
		Document doc = Network.getResponse(url);
		if (doc.getDocumentElement().getAttribute("status").equals("ok")) {
			Node node = doc.getChildNodes().item(0).getFirstChild();
			NamedNodeMap var = node.getAttributes();
			// System.out.println(var.getNamedItem("total").getNodeValue());
			// if (scrobbles != null)
			if(Reference.isVerbose())
				Reference.getConsole().write("scrobbles today: " + var.getNamedItem("total").getNodeValue());
			return Integer.parseInt(var.getNamedItem("total").getNodeValue());
		}
		return 0;
	}

	public static FMObjList getUserTag(String username, String tag) {
		if(Reference.isVerbose())
			Reference.getConsole().write(">>getUserTag");
		
		String url = URLBuilder.getUserPersonalTags(username, tag);
		Document doc = Network.getResponse(url);

		if (doc != null) {
			int pages = Integer.valueOf(
					doc.getFirstChild().getFirstChild().getAttributes().getNamedItem("totalPages").getNodeValue());
			if(Reference.isVerbose())
				Reference.getConsole().write("tag pages: " + pages);

			FMObjList list = Parser.parseUserTagList(doc);
			list.setGroupName(tag);
			
			if(Reference.isVerbose())
				Reference.getConsole().write("tag: " + tag);

			if (pages > 1) {
				int counter;
				for (counter = 2; counter <= pages; counter++) {
					String urlNew = URLBuilder.getUserPersonalTags(username, tag, counter);
					Document docNew = Network.getResponse(urlNew);

					if (docNew != null) {
						list.addAll(Parser.parseUserTagList(docNew));
//						return Parser.parseUserTagList(docNew);
					}
				}
			}
			
			return list;
		}
		
		return null;
	}
	
	public static FMObjList getArtistTracks(String artist, String username) {
		
//		String url = URLBuilder.getArtistTracks(artist, 1, username);
//		Document doc = Network.getResponse(url);
		
//		if (doc != null) {
//			int pages = Integer.valueOf(
//					doc.getFirstChild().getFirstChild().getAttributes().getNamedItem("totalPages").getNodeValue());
//
//			FMObjList list = Parser.parseArtistTracks(doc);
////			list.setGroupName(artist + " tracks");
//			
//			System.out.println(pages);
//			
//			
//			return list;
//		}
		
		if(Reference.isVerbose())
			Reference.getConsole().write(">>getArtistTracks");
		
		FMObjList totalList = new FMObjList();
		FMObjList list = new FMObjList();
		
		int counter = 1;
		do {
			String url = URLBuilder.getArtistTracks(artist, counter, username);
			Document doc = Network.getResponse(url);
			
			list = Parser.parseArtistTracks(doc);
			
			totalList.addAll(list);
			counter++;
			
		}while(list.size() > 0);
		
		return totalList;
		
		
	}

	public static ArrayList<Tag> getUserTags(String username) {
		if(Reference.isVerbose())
			Reference.getConsole().write(">>getUserTags");
		
		String url = URLBuilder.getUserTopTags(username);
		Document doc = Network.getResponse(url);
		if (doc != null) {
			return Parser.parseUserTags(doc);
		}
		return null;
	}

	

}
