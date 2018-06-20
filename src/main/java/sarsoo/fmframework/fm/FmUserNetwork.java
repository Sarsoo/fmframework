package sarsoo.fmframework.fm;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import sarsoo.fmframework.music.Track;

public class FmUserNetwork extends FmNetwork{
	
	public FmUserNetwork(String key, String userName) {
		super(key, userName);
	}
	
	public User getUser() {
		try {
			HttpResponse<JsonNode> response = Unirest.get("http://ws.audioscrobbler.com/2.0/").
					header("Accept",  "application/json").
			        header("User-Agent",  "fmframework").
			        queryString("method","user.getinfo").
			        queryString("user", userName).
			        queryString("api_key", key).
			        queryString("format", "json").
			        asJson();
			
			JSONObject obj = new JSONObject(response.getBody().toString()).getJSONObject("user");
			
			return new User(obj.getString("name"),
					obj.getString("realname"),
					obj.getString("url"),
					obj.getString("country"),
					obj.getInt("age"),
					obj.getString("gender").charAt(0),
					obj.getInt("playcount"));
			
		} catch (UnirestException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public String getUserRealName() {
		
		return getUser().getRealName();
	}
	
	public int getUserScrobbleCount() {
		
		return getUser().getScrobbleCount();
	}
	
	public Track getLastTrack() {
		try {
			HttpResponse<JsonNode> response = Unirest.get("http://ws.audioscrobbler.com/2.0/").
					header("Accept",  "application/json").
			        header("User-Agent",  "fmframework").
			        queryString("method","user.getrecenttracks").
			        queryString("user", userName).
			        queryString("api_key", key).
			        queryString("format", "json").
			        queryString("limit", "1").
			        asJson();
			
			JSONArray obj = new JSONObject(response.getBody().toString()).getJSONObject("recenttracks").getJSONArray("track");
			
			JSONObject track = (JSONObject) obj.get(0);
			
			Track trackObj = getTrack(track.getString("name"), track.getJSONObject("artist").getString("#text"));
			trackObj.setAlbum(getAlbum(track.getJSONObject("album").getString("#text"), track.getJSONObject("artist").getString("#text")));
			
			return trackObj;
			
		} catch (UnirestException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
