package sarsoo.fmframework.fm;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sarsoo.fmframework.log.Logger;
import sarsoo.fmframework.log.entry.InfoEntry;
import sarsoo.fmframework.log.entry.LogEntry;
import sarsoo.fmframework.music.Album;
import sarsoo.fmframework.music.Album.AlbumBuilder;
import sarsoo.fmframework.music.Artist;
import sarsoo.fmframework.music.Artist.ArtistBuilder;
import sarsoo.fmframework.music.Scrobble;
import sarsoo.fmframework.music.Tag;
import sarsoo.fmframework.music.Track;
import sarsoo.fmframework.music.Track.TrackBuilder;
import sarsoo.fmframework.util.FMObjList;

public class FmUserNetwork extends FmNetwork {

	public enum TaggingType {
		ARTIST, ALBUM, TRACK
	}

	/**
	 * FmNetwork for user specific Last.FM information
	 * 
	 * @param key      Last.FM API Key
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

		Logger.getLog().log(new LogEntry("getUser"));

		HashMap<String, String> parameters = new HashMap<String, String>();

		parameters.put("user", userName);

		JSONObject obj = makeGetRequest("user.getinfo", parameters).getJSONObject("user");

		return new User(obj.getString("name"), obj.getString("realname"), obj.getString("url"),
				obj.getString("country"), obj.getInt("age"), obj.getString("gender").charAt(0),
				obj.getInt("playcount"));

	}

	/**
	 * Return user real name
	 * 
	 * @return User real name
	 */
	public String getUserRealName() {

		Logger.getLog().log(new LogEntry("getUserRealName"));

		return getUser().getRealName();
	}

	/**
	 * Return user's total scrobble count
	 * 
	 * @return Total scrobble count
	 */
	public int getUserScrobbleCount() {

		Logger.getLog().log(new LogEntry("getUserScrobbleCount"));

		return getUser().getScrobbleCount();
	}

	/**
	 * Returns last or currently listening track
	 * 
	 * @return Last track
	 */
	public Track getLastTrack() {

		Logger.getLog().log(new LogEntry("getLastTrack"));

		HashMap<String, String> parameters = new HashMap<String, String>();

		parameters.put("user", userName);
		parameters.put("limit", "1");

		JSONArray obj = makeGetRequest("user.getrecenttracks", parameters).getJSONObject("recenttracks")
				.getJSONArray("track");

		JSONObject track = (JSONObject) obj.get(0);

		Artist artistObj = getArtist(track.getJSONObject("artist").getString("#text"));

		Track trackObj = getTrack(track.getString("name"), artistObj);
		trackObj.setAlbum(getAlbum(track.getJSONObject("album").getString("#text"), artistObj));

		return trackObj;

	}

	/**
	 * Return scrobble count from today
	 * 
	 * @return Scrobble count today
	 */
	public int getScrobblesToday() {

		Logger.getLog().log(new LogEntry("getScrobblesToday"));

		LocalDate local = LocalDate.now();

		ZoneId zoneId = ZoneId.systemDefault();
		long epoch = local.atStartOfDay(zoneId).toEpochSecond();

		HashMap<String, String> parameters = new HashMap<String, String>();

		parameters.put("user", userName);
		parameters.put("from", Long.toString(epoch));
		parameters.put("limit", "1");

		JSONObject obj = makeGetRequest("user.getrecenttracks", parameters);

		int total = obj.getJSONObject("recenttracks").getJSONObject("@attr").getInt("total");

		return total;

	}

	/**
	 * Return scrobble count by date
	 * 
	 * @param day   Day int
	 * @param month Month int
	 * @param year  Year int
	 * @return Scrobble count
	 */
	public int getScrobbleCountByDate(int day, int month, int year) {

		Logger.getLog().log(new LogEntry("getScrobblesByDate").addArg(Integer.toString(day))
				.addArg(Integer.toString(month)).addArg(Integer.toString(year)));

		LocalDate startDate = LocalDate.of(year, month, day);

		ZoneId zoneId = ZoneId.systemDefault();
		long epoch = startDate.atStartOfDay(zoneId).toEpochSecond();
		long endEpoch = epoch + (24 * 60 * 60);

		HashMap<String, String> parameters = new HashMap<String, String>();

		parameters.put("user", userName);
		parameters.put("from", Long.toString(epoch));
		parameters.put("to", Long.toString(endEpoch));
		parameters.put("limit", "1");

		JSONObject obj = makeGetRequest("user.getrecenttracks", parameters);

		int total = obj.getJSONObject("recenttracks").getJSONObject("@attr").getInt("total");

		return total;

	}

	/**
	 * Returns scrobble count of day by today - {int day}
	 * 
	 * @param day Negative day offset
	 * @return Scrobble count
	 */
	public int getScrobbleCountByDeltaDay(int day) {

		Logger.getLog().log(new LogEntry("getScrobblesByDeltaDay").addArg(Integer.toString(day)));

		LocalDate local = LocalDate.now();

		ZoneId zoneId = ZoneId.systemDefault();
		long epoch = local.atStartOfDay(zoneId).toEpochSecond();
		epoch -= (day * (24 * 60 * 60));
		long endEpoch = epoch + (24 * 60 * 60);

		HashMap<String, String> parameters = new HashMap<String, String>();

		parameters.put("user", userName);
		parameters.put("from", Long.toString(epoch));
		parameters.put("to", Long.toString(endEpoch));
		parameters.put("limit", "1");

		JSONObject obj = makeGetRequest("user.getrecenttracks", parameters);

		int total = obj.getJSONObject("recenttracks").getJSONObject("@attr").getInt("total");

		return total;

	}

	public Artist getArtistTracks(String artistName) {
		return getArtistTracks(getArtist(artistName));
	}

	public Artist getArtistTracks(Artist artist) {

		Logger.getLog().log(new LogEntry("getArtistTracks").addArg(artist.getName()));

		int limit = 50;

		ArrayList<Scrobble> scrobbles = new ArrayList<Scrobble>();

		Boolean done = false;
		int counter = 1;
		while (!done) {

			HashMap<String, String> parameters = new HashMap<String, String>();

			parameters.put("user", userName);
			parameters.put("artist", artist.getName());
			parameters.put("limit", Integer.toString(limit));
			parameters.put("page", Integer.toString(counter + 1));

			JSONObject obj = makeGetRequest("user.getartisttracks", parameters);

			JSONArray returnedScrobbles = obj.getJSONObject("artisttracks").getJSONArray("track");

			if (returnedScrobbles.length() > 0) {

				for (int i = 0; i < returnedScrobbles.length(); i++) {

					JSONObject scrob = returnedScrobbles.getJSONObject(i);

				}
			}else {
				done = true;
			}

		}

		return null;
	}

	public ArrayList<Scrobble> getRecentScrobbles(int number) {

		Logger.getLog().log(new LogEntry("getRecentTracks").addArg(Integer.toString(number)));

		int limit = 50;

		int pages = 0;

		System.out.println(number / limit);
		if ((double) number % (double) limit != 0) {
			pages = (number / limit) + 1;
		} else {
			pages = number / limit;
		}

		ArrayList<Scrobble> scrobbles = new ArrayList<Scrobble>();
		int counter;
		for (counter = 0; counter < pages; counter++) {

			HashMap<String, String> parameters = new HashMap<String, String>();

			parameters.put("user", userName);
			parameters.put("limit", Integer.toString(limit));
			parameters.put("page", Integer.toString(counter + 1));

			JSONObject obj = makeGetRequest("user.getrecenttracks", parameters);

			JSONArray tracks = obj.getJSONObject("recenttracks").getJSONArray("track");

			for (int i = 0; i < tracks.length(); i++) {
				JSONObject json = (JSONObject) tracks.get(i);

				if (scrobbles.size() < number) {
					Artist artist = new ArtistBuilder(json.getJSONObject("artist").getString("#text")).build();
					Album album = new AlbumBuilder(json.getJSONObject("album").getString("#text"), artist).build();

					Track track = new TrackBuilder(json.getString("name"), artist).build();
					track.setAlbum(album);

					try {
						Scrobble scrobble = new Scrobble(json.getJSONObject("date").getLong("uts"), track);
						scrobbles.add(scrobble);
					} catch (JSONException e) {
						Logger.getLog().logInfo(new InfoEntry("getRecentTracks").addArg("first track"));

					}

				}
			}
		}

		return scrobbles;
	}

	/**
	 * Returns list of user tags
	 * 
	 * @return List of tags
	 */
	public ArrayList<Tag> getTags() {

		Logger.getLog().log(new LogEntry("getTags"));

		HashMap<String, String> parameters = new HashMap<String, String>();

		parameters.put("user", userName);

		JSONObject obj = makeGetRequest("user.gettoptags", parameters);

		JSONArray tagJsonArray = obj.getJSONObject("toptags").getJSONArray("tag");

		JSONObject tagJson;

		ArrayList<Tag> tags = new ArrayList<Tag>();

		int counter;
		for (counter = 0; counter < tagJsonArray.length(); counter++) {

			tagJson = (JSONObject) tagJsonArray.get(counter);

			Tag tag = new Tag(tagJson.getString("name"), tagJson.getString("url"), tagJson.getInt("count"));

			tags.add(tag);

		}

		return tags;

	}

	/**
	 * Returns FMObjList of tagged artists
	 * 
	 * @param tagName Tag to explore
	 * @return FMObjList of artists
	 */
	public FMObjList getTag(String tagName) {

		Logger.getLog().log(new LogEntry("getTag").addArg(tagName));

		HashMap<String, String> parameters = new HashMap<String, String>();

		parameters.put("user", userName);
		parameters.put("tag", tagName);
		parameters.put("taggingtype", "artist");
		parameters.put("limit", "70");

		JSONObject obj = makeGetRequest("user.getpersonaltags", parameters);

		JSONArray tagJsonArray = obj.getJSONObject("taggings").getJSONObject("artists").getJSONArray("artist");

		JSONObject artistJson;

		FMObjList list = new FMObjList();

		list.setGroupName(tagName);

		int counter;
		for (counter = 0; counter < tagJsonArray.length(); counter++) {

			artistJson = (JSONObject) tagJsonArray.get(counter);

			Artist artist = getArtist(artistJson.getString("name"));

			Logger.getLog().logInfo(new InfoEntry("Tag").addArg(tagName).addArg(artist.getName()));

			list.add(artist);

		}

		return list;

	}

}
