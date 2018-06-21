package sarsoo.fmframework.fm;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import sarsoo.fmframework.music.Artist;
import sarsoo.fmframework.music.Tag;
import sarsoo.fmframework.music.Track;
import sarsoo.fmframework.util.ConsoleHandler;
import sarsoo.fmframework.util.FMObjList;

public class FmUserNetwork extends FmNetwork {
	
	public enum TaggingType {
	    ARTIST, ALBUM, TRACK
	}

	public FmUserNetwork(String key, String userName) {
		super(key, userName);
	}

	public User getUser() {
		if (ConsoleHandler.isVerbose())
			ConsoleHandler.getConsole().write(">>getUser");
		
		try {
			HttpResponse<JsonNode> response = Unirest.get("http://ws.audioscrobbler.com/2.0/")
					.header("Accept", "application/json").header("User-Agent", "fmframework")
					.queryString("method", "user.getinfo").queryString("user", userName).queryString("api_key", key)
					.queryString("format", "json").asJson();

			JSONObject obj = new JSONObject(response.getBody().toString()).getJSONObject("user");

			return new User(obj.getString("name"), obj.getString("realname"), obj.getString("url"),
					obj.getString("country"), obj.getInt("age"), obj.getString("gender").charAt(0),
					obj.getInt("playcount"));

		} catch (UnirestException e) {
			e.printStackTrace();
		}

		return null;
	}

	public String getUserRealName() {
		if (ConsoleHandler.isVerbose())
			ConsoleHandler.getConsole().write(">>getUserRealname");
		
		return getUser().getRealName();
	}

	public int getUserScrobbleCount() {
		if (ConsoleHandler.isVerbose())
			ConsoleHandler.getConsole().write(">>getUserScrobbleCount");

		return getUser().getScrobbleCount();
	}

	public Track getLastTrack() {
		if (ConsoleHandler.isVerbose())
			ConsoleHandler.getConsole().write(">>getLastTrack");
		
		try {
			HttpResponse<JsonNode> response = Unirest.get("http://ws.audioscrobbler.com/2.0/")
					.header("Accept", "application/json").header("User-Agent", "fmframework")
					.queryString("method", "user.getrecenttracks").queryString("user", userName)
					.queryString("api_key", key).queryString("format", "json").queryString("limit", "1").asJson();

			JSONArray obj = new JSONObject(response.getBody().toString()).getJSONObject("recenttracks")
					.getJSONArray("track");

			JSONObject track = (JSONObject) obj.get(0);

			Track trackObj = getTrack(track.getString("name"), track.getJSONObject("artist").getString("#text"));
			trackObj.setAlbum(getAlbum(track.getJSONObject("album").getString("#text"),
					track.getJSONObject("artist").getString("#text")));

			return trackObj;

		} catch (UnirestException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public int getScrobblesToday() {
		if (ConsoleHandler.isVerbose())
			ConsoleHandler.getConsole().write(">>getScrobblesToday");
		
		LocalDate local = LocalDate.now();
		
		ZoneId zoneId = ZoneId.systemDefault(); 
		long epoch = local.atStartOfDay(zoneId).toEpochSecond();
		
		try {
			HttpResponse<JsonNode> response = Unirest.get("http://ws.audioscrobbler.com/2.0/").
					header("Accept",  "application/json").
			        header("User-Agent",  "fmframework").
			        queryString("method","user.getrecenttracks").
			        queryString("user", userName).
			        queryString("from", epoch).
			        queryString("limit", 1).
			        queryString("api_key", key).
			        queryString("format", "json").
			        asJson();
			
			int total = new JSONObject(response.getBody().toString()).getJSONObject("recenttracks").getJSONObject("@attr").getInt("total");
			
			return total;
			
		} catch (UnirestException e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	public ArrayList<Tag> getTags(){
		if (ConsoleHandler.isVerbose())
			ConsoleHandler.getConsole().write(">>getTags");
		
		try {
			HttpResponse<JsonNode> response = Unirest.get("http://ws.audioscrobbler.com/2.0/").
					header("Accept",  "application/json").
			        header("User-Agent",  "fmframework").
			        queryString("method","user.gettoptags").
			        queryString("user", userName).
			        queryString("api_key", key).
			        queryString("format", "json").
			        asJson();
			
			JSONArray tagJsonArray = new JSONObject(response.getBody().toString()).getJSONObject("toptags").getJSONArray("tag");
			
			JSONObject tagJson;
			
			ArrayList<Tag> tags = new ArrayList<Tag>();
			
			int counter;
			for(counter = 0; counter < tagJsonArray.length(); counter++) {
				
				tagJson = (JSONObject) tagJsonArray.get(counter);
			
				Tag tag = new Tag(tagJson.getString("name"), tagJson.getString("url"), tagJson.getInt("count"));
				
				tags.add(tag);
				
			}
			
			return tags;
			
		} catch (UnirestException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	public FMObjList getTag(String tagName) {
		if (ConsoleHandler.isVerbose())
			ConsoleHandler.getConsole().write(">>getTag: " + tagName);
		
		try {
			HttpResponse<JsonNode> response = Unirest.get("http://ws.audioscrobbler.com/2.0/").
					header("Accept",  "application/json").
			        header("User-Agent",  "fmframework").
			        queryString("method","user.getpersonaltags").
			        queryString("user", userName).
			        queryString("tag", tagName).
			        queryString("taggingtype", "artist").
			        queryString("limit", 70).
			        queryString("api_key", key).
			        queryString("format", "json").
			        asJson();
			
			JSONArray tagJsonArray = new JSONObject(response.getBody().toString()).getJSONObject("taggings").getJSONObject("artists").getJSONArray("artist");
			
			JSONObject artistJson;
			
			FMObjList list = new FMObjList();
			
			list.setGroupName(tagName);
			
			int counter;
			for(counter = 0; counter < tagJsonArray.length(); counter++) {
				
				artistJson = (JSONObject) tagJsonArray.get(counter);
			
				Artist artist = getArtist(artistJson.getString("name"));
				
				if (ConsoleHandler.isVerbose())
					ConsoleHandler.getConsole().write(">Tag: " + tagName + ", " + artist.getName());
				
				list.add(artist);
				
			}
			
			return list;
			
		} catch (UnirestException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}

}
