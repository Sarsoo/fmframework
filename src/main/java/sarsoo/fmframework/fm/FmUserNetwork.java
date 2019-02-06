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

	/**
	 * FmNetwork for user specific Last.FM information
	 * 
	 * @param key Last.FM API Key
	 * @param userName Last.FM username
	 */
	public FmUserNetwork(String key, String userName) {
		// super(key, userName);
		super(key);
		this.userName = userName;
	}
	
	/**
	 * Set network's Last.FM username
	 * 
	 * @param userName Last.FM username
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * Return network's Last.FM username
	 * 
	 * @return Last.FM username
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Clears username from network
	 */
	public void clearUser() {
		this.userName = null;
	}

	/**
	 * Return user object from Last.FM
	 * 
	 * @return User
	 */
	public User getUser() {
		if (ConsoleHandler.isVerbose())
			ConsoleHandler.getConsole().write(">>getUser");

		try {
			HttpResponse<JsonNode> response = Unirest.get("http://ws.audioscrobbler.com/2.0/")
					.header("Accept", "application/json").header("User-Agent", "fmframework")
					.queryString("method", "user.getinfo").queryString("user", userName).queryString("api_key", key)
					.queryString("format", "json").asJson();

			if (response.getStatus() == 200) {

				JSONObject obj = new JSONObject(response.getBody().toString()).getJSONObject("user");

				return new User(obj.getString("name"), obj.getString("realname"), obj.getString("url"),
						obj.getString("country"), obj.getInt("age"), obj.getString("gender").charAt(0),
						obj.getInt("playcount"));

			} else {
				if (ConsoleHandler.isVerbose())
					ConsoleHandler.getConsole().write("ERROR (getUser): HTTP REQUEST ERROR");
				else
					System.err.println("ERROR (getUser): HTTP REQUEST ERROR");
				return null;
			}

		} catch (UnirestException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Return user real name
	 * 
	 * @return User real name
	 */
	public String getUserRealName() {
		if (ConsoleHandler.isVerbose())
			ConsoleHandler.getConsole().write(">>getUserRealname");

		return getUser().getRealName();
	}

	/**
	 * Return user's total scrobble count
	 * 
	 * @return Total scrobble count
	 */
	public int getUserScrobbleCount() {
		if (ConsoleHandler.isVerbose())
			ConsoleHandler.getConsole().write(">>getUserScrobbleCount");

		return getUser().getScrobbleCount();
	}

	/**
	 * Returns last or currently listening track
	 * 
	 * @return Last track
	 */
	public Track getLastTrack() {
		if (ConsoleHandler.isVerbose())
			ConsoleHandler.getConsole().write(">>getLastTrack");

		try {
			HttpResponse<JsonNode> response = Unirest.get("http://ws.audioscrobbler.com/2.0/")
					.header("Accept", "application/json").header("User-Agent", "fmframework")
					.queryString("method", "user.getrecenttracks").queryString("user", userName)
					.queryString("api_key", key).queryString("format", "json").queryString("limit", "1").asJson();

			if (response.getStatus() == 200) {

				JSONArray obj = new JSONObject(response.getBody().toString()).getJSONObject("recenttracks")
						.getJSONArray("track");

				JSONObject track = (JSONObject) obj.get(0);

				Track trackObj = getTrack(track.getString("name"), track.getJSONObject("artist").getString("#text"));
				trackObj.setAlbum(getAlbum(track.getJSONObject("album").getString("#text"),
						track.getJSONObject("artist").getString("#text")));

				return trackObj;

			} else {
				if (ConsoleHandler.isVerbose())
					ConsoleHandler.getConsole().write("ERROR (getLastTrack): HTTP REQUEST ERROR");
				else
					System.err.println("ERROR (getLastTrack): HTTP REQUEST ERROR");
				return null;
			}

		} catch (UnirestException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Return scrobble count from today
	 * 
	 * @return Scrobble count today
	 */
	public int getScrobblesToday() {
		if (ConsoleHandler.isVerbose())
			ConsoleHandler.getConsole().write(">>getScrobblesToday");

		LocalDate local = LocalDate.now();

		ZoneId zoneId = ZoneId.systemDefault();
		long epoch = local.atStartOfDay(zoneId).toEpochSecond();

		try {
			HttpResponse<JsonNode> response = Unirest.get("http://ws.audioscrobbler.com/2.0/")
					.header("Accept", "application/json").header("User-Agent", "fmframework")
					.queryString("method", "user.getrecenttracks").queryString("user", userName)
					.queryString("from", epoch).queryString("limit", 1).queryString("api_key", key)
					.queryString("format", "json").asJson();

			if (response.getStatus() == 200) {

				int total = new JSONObject(response.getBody().toString()).getJSONObject("recenttracks")
						.getJSONObject("@attr").getInt("total");

				return total;

			} else {
				if (ConsoleHandler.isVerbose())
					ConsoleHandler.getConsole().write("ERROR (getScrobblesToday): HTTP REQUEST ERROR");
				else
					System.err.println("ERROR (getScrobblesToday): HTTP REQUEST ERROR");
				return 0;
			}

		} catch (UnirestException e) {
			e.printStackTrace();
		}

		return 0;
	}

	/**
	 * Return scrobble count by date
	 * 
	 * @param day Day int
	 * @param month Month int
	 * @param year Year int
	 * @return Scrobble count
	 */
	public int getScrobbleCountByDate(int day, int month, int year) {
		if (ConsoleHandler.isVerbose())
			ConsoleHandler.getConsole().write(">>getScrobblesByDate " + day + "." + month + "." + year);

		LocalDate startDate = LocalDate.of(year, month, day);

		ZoneId zoneId = ZoneId.systemDefault();
		long epoch = startDate.atStartOfDay(zoneId).toEpochSecond();
		long endEpoch = epoch + (24 * 60 * 60);

		try {
			HttpResponse<JsonNode> response = Unirest.get("http://ws.audioscrobbler.com/2.0/")
					.header("Accept", "application/json").header("User-Agent", "fmframework")
					.queryString("method", "user.getrecenttracks").queryString("user", userName)
					.queryString("from", epoch).queryString("to", endEpoch).queryString("limit", 1)
					.queryString("api_key", key).queryString("format", "json").asJson();

			if (response.getStatus() == 200) {

				int total = new JSONObject(response.getBody().toString()).getJSONObject("recenttracks")
						.getJSONObject("@attr").getInt("total");

				return total;

			} else {
				if (ConsoleHandler.isVerbose())
					ConsoleHandler.getConsole().write("ERROR (getScrobbleCountByDate): " + day + " " + month + " "
							+ year + " HTTP REQUEST ERROR");
				else
					System.err.println("ERROR (getScrobbleCountByDate): " + day + " " + month + " " + year
							+ " HTTP REQUEST ERROR");
				return 0;
			}

		} catch (UnirestException e) {
			e.printStackTrace();
		}

		return 0;
	}

	/**
	 * Returns scrobble count of day 
	 * by today - {int day}
	 * 
	 * @param day Negative day offset
	 * @return Scrobble count
	 */
	public int getScrobbleCountByDeltaDay(int day) {
		if (ConsoleHandler.isVerbose())
			ConsoleHandler.getConsole().write(">>getScrobblesByDeltaDay " + day);

		LocalDate local = LocalDate.now();

		ZoneId zoneId = ZoneId.systemDefault();
		long epoch = local.atStartOfDay(zoneId).toEpochSecond();
		epoch -= (day * (24 * 60 * 60));
		long endEpoch = epoch + (24 * 60 * 60);

		try {
			HttpResponse<JsonNode> response = Unirest.get("http://ws.audioscrobbler.com/2.0/")
					.header("Accept", "application/json").header("User-Agent", "fmframework")
					.queryString("method", "user.getrecenttracks").queryString("user", userName)
					.queryString("from", epoch).queryString("to", endEpoch).queryString("limit", 1)
					.queryString("api_key", key).queryString("format", "json").asJson();

			if (response.getStatus() == 200) {

				int total = new JSONObject(response.getBody().toString()).getJSONObject("recenttracks")
						.getJSONObject("@attr").getInt("total");

				return total;

			} else {
				if (ConsoleHandler.isVerbose())
					ConsoleHandler.getConsole()
							.write("ERROR (getScrobbleCountByDeltaDay): " + day + " HTTP REQUEST ERROR");
				else
					System.err.println("ERROR (getScrobbleCountByDeltaDay): " + day + " HTTP REQUEST ERROR");
				return 0;
			}

		} catch (UnirestException e) {
			e.printStackTrace();
		}

		return 0;
	}

	/**
	 * Returns list of user tags
	 * 
	 * @return List of tags
	 */
	public ArrayList<Tag> getTags() {
		if (ConsoleHandler.isVerbose())
			ConsoleHandler.getConsole().write(">>getTags");

		try {
			HttpResponse<JsonNode> response = Unirest.get("http://ws.audioscrobbler.com/2.0/")
					.header("Accept", "application/json").header("User-Agent", "fmframework")
					.queryString("method", "user.gettoptags").queryString("user", userName).queryString("api_key", key)
					.queryString("format", "json").asJson();

			if (response.getStatus() == 200) {

				JSONArray tagJsonArray = new JSONObject(response.getBody().toString()).getJSONObject("toptags")
						.getJSONArray("tag");

				JSONObject tagJson;

				ArrayList<Tag> tags = new ArrayList<Tag>();

				int counter;
				for (counter = 0; counter < tagJsonArray.length(); counter++) {

					tagJson = (JSONObject) tagJsonArray.get(counter);

					Tag tag = new Tag(tagJson.getString("name"), tagJson.getString("url"), tagJson.getInt("count"));

					tags.add(tag);

				}

				return tags;

			} else {
				if (ConsoleHandler.isVerbose())
					ConsoleHandler.getConsole().write("ERROR (getTags): HTTP REQUEST ERROR");
				else
					System.err.println("ERROR (getTags): HTTP REQUEST ERROR");
				return null;
			}

		} catch (UnirestException e) {
			e.printStackTrace();
		}

		return null;

	}

	/**
	 * Returns FMObjList of tagged artists 
	 * 
	 * @param tagName Tag to explore
	 * @return FMObjList of artists 
	 */
	public FMObjList getTag(String tagName) {
		if (ConsoleHandler.isVerbose())
			ConsoleHandler.getConsole().write(">>getTag: " + tagName);

		try {
			HttpResponse<JsonNode> response = Unirest.get("http://ws.audioscrobbler.com/2.0/")
					.header("Accept", "application/json").header("User-Agent", "fmframework")
					.queryString("method", "user.getpersonaltags").queryString("user", userName)
					.queryString("tag", tagName).queryString("taggingtype", "artist").queryString("limit", 70)
					.queryString("api_key", key).queryString("format", "json").asJson();

			if (response.getStatus() == 200) {

				JSONArray tagJsonArray = new JSONObject(response.getBody().toString()).getJSONObject("taggings")
						.getJSONObject("artists").getJSONArray("artist");

				JSONObject artistJson;

				FMObjList list = new FMObjList();

				list.setGroupName(tagName);

				int counter;
				for (counter = 0; counter < tagJsonArray.length(); counter++) {

					artistJson = (JSONObject) tagJsonArray.get(counter);

					Artist artist = getArtist(artistJson.getString("name"));

					if (ConsoleHandler.isVerbose())
						ConsoleHandler.getConsole().write(">Tag: " + tagName + ", " + artist.getName());

					list.add(artist);

				}

				return list;

			} else {
				if (ConsoleHandler.isVerbose())
					ConsoleHandler.getConsole().write("ERROR (getTag): " + tagName + " HTTP REQUEST ERROR");
				else
					System.err.println("ERROR (getTag): " + tagName + " HTTP REQUEST ERROR");
				return null;
			}

		} catch (UnirestException e) {
			e.printStackTrace();
		}

		return null;

	}

}
