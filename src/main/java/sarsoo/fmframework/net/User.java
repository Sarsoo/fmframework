package sarsoo.fmframework.net;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import sarsoo.fmframework.music.Tag;
import sarsoo.fmframework.music.Track;
import sarsoo.fmframework.parser.Parser;
import sarsoo.fmframework.util.FMObjList;
import sarsoo.fmframework.util.Reference;

public class User {
	
	protected String userName;
	
	public User(String userName) {
		this.userName = userName;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public Track getLastTrack() {
		if(Reference.isVerbose())
			Reference.getConsole().write(">>getLastTrack");
		
		String url = URLBuilder.getLastTrackUrl(userName);
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

	public int getScrobbles() {
		if(Reference.isVerbose())
			Reference.getConsole().write(">>getScrobbles");
		
		String url = URLBuilder.getUserInfoUrl(userName);
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

	public int getScrobblesToday() {
		if(Reference.isVerbose())
			Reference.getConsole().write(">>getScrobblesToday");
		
		String url = URLBuilder.getTodayScrobbles(userName);
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

	public FMObjList getUserTag(String tag) {
		if(Reference.isVerbose())
			Reference.getConsole().write(">>getUserTag");
		
		String url = URLBuilder.getUserPersonalTags(userName, tag);
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
					String urlNew = URLBuilder.getUserPersonalTags(userName, tag, counter);
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
	
	public FMObjList getArtistTracks(String artist) {
		
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
			String url = URLBuilder.getArtistTracks(artist, counter, userName);
			Document doc = Network.getResponse(url);
			
			list = Parser.parseArtistTracks(doc);
			
			totalList.addAll(list);
			counter++;
			
		}while(list.size() > 0);
		
		return totalList;
		
		
	}

	public ArrayList<Tag> getUserTags() {
		if(Reference.isVerbose())
			Reference.getConsole().write(">>getUserTags");
		
		String url = URLBuilder.getUserTopTags(userName);
		Document doc = Network.getResponse(url);
		if (doc != null) {
			return Parser.parseUserTags(doc);
		}
		return null;
	}


	

}
