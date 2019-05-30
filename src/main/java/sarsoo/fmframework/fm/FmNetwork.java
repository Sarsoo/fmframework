package sarsoo.fmframework.fm;

import sarsoo.fmframework.error.ApiCallException;
import sarsoo.fmframework.log.Log;
import sarsoo.fmframework.log.Logger;
import sarsoo.fmframework.log.entry.ErrorEntry;
import sarsoo.fmframework.log.entry.InfoEntry;
import sarsoo.fmframework.log.entry.LogEntry;
import sarsoo.fmframework.music.Album;
import sarsoo.fmframework.music.Album.AlbumBuilder;
import sarsoo.fmframework.music.Artist;
import sarsoo.fmframework.music.Artist.ArtistBuilder;
import sarsoo.fmframework.music.FMObj;
import sarsoo.fmframework.music.Track;
import sarsoo.fmframework.music.Track.TrackBuilder;
import sarsoo.fmframework.music.Wiki;
import sarsoo.fmframework.util.FMObjList;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;

public class FmNetwork {

	protected String key;
	protected String userName;

	/**
	 * FmNetwork for raw and user independent Last.FM information
	 * 
	 * @param key Last.FM API Key
	 */
	public FmNetwork(String key) {
		this.key = key;
	}

	public Album getAlbum(String name, String artist) throws ApiCallException {
		return getAlbum(name, getArtist(artist));
	}

	/**
	 * Get an album from Last.FM
	 * 
	 * @param name   Album Name
	 * @param artist Artist Name
	 * @return Album
	 * @throws ApiCallException 
	 * @throws JSONException 
	 */
	public Album getAlbum(String name, Artist artist) throws ApiCallException {

		Log log = Logger.getLog();
		log.log(new LogEntry("getAlbum").addArg(name).addArg(artist.getName()));

		HashMap<String, String> parameters = new HashMap<String, String>();

		parameters.put("album", name);
		parameters.put("artist", artist.getName());

		if (userName != null)
			parameters.put("username", userName);

		JSONObject obj = makeGetRequest("album.getinfo", parameters);

		String nameIn;

		try {

			JSONObject albumJson = obj.getJSONObject("album");

			nameIn = albumJson.getString("name");

			AlbumBuilder builder = new AlbumBuilder(nameIn, artist);

			try {
				builder.setMbid(albumJson.getString("mbid"));
			} catch (JSONException e) {
//				log.logInfo(new InfoEntry("getAlbum").addArg("no mbid for").addArg(nameIn).addArg(e.getMessage()));
			}

			try {
				builder.setUrl(albumJson.getString("url"));
			} catch (JSONException e) {
				log.logInfo(new InfoEntry("getAlbum").addArg("no url for").addArg(nameIn).addArg(e.getMessage()));
			}

			try {
				builder.setListeners(albumJson.getInt("listeners"));
			} catch (JSONException e) {
				log.logInfo(new InfoEntry("getAlbum").addArg("no listeners for").addArg(nameIn).addArg(e.getMessage()));
			}

			try {
				builder.setPlayCount(albumJson.getInt("playcount"));
			} catch (JSONException e) {
				log.logInfo(
						new InfoEntry("getAlbum").addArg("no play count for").addArg(nameIn).addArg(e.getMessage()));
			}

			try {
				builder.setUserPlayCount(albumJson.getInt("userplaycount"));
			} catch (JSONException e) {
				log.logInfo(new InfoEntry("getAlbum").addArg("no user play count for").addArg(nameIn)
						.addArg(e.getMessage()));
			}
			
			try {
				JSONArray imageArray = albumJson.getJSONArray("image");
				JSONObject imageObj = (JSONObject) imageArray.get(imageArray.length() - 1);
				
				builder.setImageUrl(imageObj.getString("#text"));
			} catch (JSONException e) {
				log.logInfo(new InfoEntry("getAlbum").addArg("no image for").addArg(nameIn)
						.addArg(e.getMessage()));
			}

			try {
				JSONObject wikiJson = albumJson.getJSONObject("wiki");

				Wiki wiki = new Wiki(wikiJson.getString("published"), wikiJson.getString("summary"),
						wikiJson.getString("content"));

				builder.setWiki(wiki);

			} catch (JSONException e) {
				log.logInfo(new InfoEntry("getAlbum").addArg("no wiki for").addArg(nameIn).addArg(e.getMessage()));
			}

			return builder.build();

		} catch (JSONException e) {
			log.logInfo(new InfoEntry("getAlbum").addArg("album name not found").addArg(e.getMessage()));
		}

		return null;

	}

	/**
	 * Get an artist from Last.FM
	 * 
	 * @param name Artist Name
	 * @return Artist
	 * @throws ApiCallException 
	 * @throws JSONException 
	 */
	public Artist getArtist(String name) throws ApiCallException {

		Log log = Logger.getLog();
		log.log(new LogEntry("getArtist").addArg(name));

		HashMap<String, String> parameters = new HashMap<String, String>();

		parameters.put("artist", name);

		if (userName != null)
			parameters.put("username", userName);

		JSONObject obj = makeGetRequest("artist.getinfo", parameters);

		String artistName;

		try {

			JSONObject artistJson = obj.getJSONObject("artist");

			artistName = artistJson.getString("name");

			ArtistBuilder builder = new ArtistBuilder(artistName);

			try {
				builder.setMbid(artistJson.getString("mbid"));
			} catch (JSONException e) {
				log.logInfo(new InfoEntry("getArtist").addArg("no mbid for").addArg(artistName).addArg(e.getMessage()));
			}

			try {
				builder.setUrl(artistJson.getString("url"));
			} catch (JSONException e) {
				log.logInfo(new InfoEntry("getArtist").addArg("no url for").addArg(artistName).addArg(e.getMessage()));
			}

			try {
				builder.setListeners(artistJson.getJSONObject("stats").getInt("listeners"));
			} catch (JSONException e) {
				log.logInfo(new InfoEntry("getArtist").addArg("no listeners for").addArg(artistName)
						.addArg(e.getMessage()));
			}

			try {
				builder.setPlayCount(artistJson.getJSONObject("stats").getInt("playcount"));
			} catch (JSONException e) {
				log.logInfo(new InfoEntry("getArtist").addArg("no play count for").addArg(artistName)
						.addArg(e.getMessage()));
			}

			try {
				builder.setUserPlayCount(artistJson.getJSONObject("stats").getInt("userplaycount"));
			} catch (JSONException e) {
				log.logInfo(new InfoEntry("getArtist").addArg("no user play count for").addArg(artistName)
						.addArg(e.getMessage()));
			}

			try {
				JSONObject wikiJson = artistJson.getJSONObject("bio");

				Wiki wiki = new Wiki(wikiJson.getString("published"), wikiJson.getString("summary"),
						wikiJson.getString("content"));

				builder.setWiki(wiki);

			} catch (JSONException e) {
				log.logInfo(new InfoEntry("getArtist").addArg("no wiki for").addArg(artistName).addArg(e.getMessage()));
			}

			return builder.build();

		} catch (JSONException e) {
			log.logInfo(new InfoEntry("getArtist").addArg("artist name not found").addArg(e.getMessage()));
		}

		return null;

	}

	public Track getTrack(String name, String artist) throws ApiCallException {
		return getTrack(name, getArtist(artist));
	}

	/**
	 * Get a track from Last.FM
	 * 
	 * @param name   Track Name
	 * @param artist Artist Name
	 * @return Track
	 * @throws ApiCallException 
	 * @throws JSONException 
	 */
	public Track getTrack(String name, Artist artist) throws ApiCallException {

		Log log = Logger.getLog();
		log.log(new LogEntry("getTrack").addArg(name).addArg(artist.getName()));

		HashMap<String, String> parameters = new HashMap<String, String>();

		parameters.put("artist", artist.getName());
		parameters.put("track", name);

		if (userName != null)
			parameters.put("username", userName);

		JSONObject obj = makeGetRequest("track.getinfo", parameters);

		return parseTrack(obj.getJSONObject("track"), artist);

	}

	protected Track parseTrack(JSONObject trackJson, Artist artist) {

		Log log = Logger.getLog();

		String nameIn;

		try {

//			JSONObject trackJson = obj.getJSONObject("track");

			nameIn = trackJson.getString("name");

			TrackBuilder builder = new TrackBuilder(nameIn, artist);

			try {
				builder.setMbid(trackJson.getString("mbid"));
			} catch (JSONException e) {
				log.logInfo(new InfoEntry("parseTrack").addArg("no mbid for").addArg(nameIn).addArg(e.getMessage()));
			}

			try {
				builder.setUrl(trackJson.getString("url"));
			} catch (JSONException e) {
				log.logInfo(new InfoEntry("parseTrack").addArg("no url for").addArg(nameIn).addArg(e.getMessage()));
			}

			try {
				builder.setListeners(trackJson.getInt("listeners"));
			} catch (JSONException e) {
				log.logInfo(
						new InfoEntry("parseTrack").addArg("no listeners for").addArg(nameIn).addArg(e.getMessage()));
			}

			try {
				builder.setPlayCount(trackJson.getInt("playcount"));
			} catch (JSONException e) {
				log.logInfo(
						new InfoEntry("parseTrack").addArg("no play count for").addArg(nameIn).addArg(e.getMessage()));
			}

			try {
				builder.setUserPlayCount(trackJson.getInt("userplaycount"));
			} catch (JSONException e) {
				log.logInfo(new InfoEntry("parseTrack").addArg("no user play count for").addArg(nameIn)
						.addArg(e.getMessage()));
			}

			try {
				JSONObject wikiJson = trackJson.getJSONObject("wiki");

				Wiki wiki = new Wiki(wikiJson.getString("published"), wikiJson.getString("summary"),
						wikiJson.getString("content"));

				builder.setWiki(wiki);

			} catch (JSONException e) {
				log.logInfo(new InfoEntry("parseTrack").addArg("no wiki for").addArg(nameIn).addArg(e.getMessage()));
			}

			return builder.build();

		} catch (JSONException e) {
			log.logInfo(new InfoEntry("parseTrack").addArg("track name not found").addArg(e.getMessage()));
		}

		return null;
	}

	public FMObjList getArtistTopTracks(Artist artist, int number) throws ApiCallException {

		Logger.getLog()
				.log(new LogEntry("getArtistTopTracks").addArg(artist.getName()).addArg(Integer.toString(number)));

		int limit = 50;

		int pages = 0;

		System.out.println(number / limit);
		if ((double) number % (double) limit != 0) {
			pages = (number / limit) + 1;
		} else {
			pages = number / limit;
		}

		FMObjList tracks = new FMObjList();
		int counter;
		for (counter = 0; counter < pages; counter++) {

			HashMap<String, String> parameters = new HashMap<String, String>();

			parameters.put("artist", artist.getName());
			parameters.put("limit", Integer.toString(limit));
			parameters.put("page", Integer.toString(counter + 1));

			JSONObject obj = makeGetRequest("artist.gettoptracks", parameters);

			JSONArray tracksJson = obj.getJSONObject("toptracks").getJSONArray("track");

			for (int i = 0; i < tracksJson.length(); i++) {
				JSONObject json = (JSONObject) tracksJson.get(i);

				if (tracks.size() < number) {
					
					tracks.add(parseTrack(json, artist));

				}

			}
		}
		
		return tracks;
	}

	/**
	 * Exchanges album object for stat-updated album from Last.FM
	 * 
	 * @param album Old Album Object
	 * @return Refreshed Album
	 * @throws ApiCallException 
	 * @throws JSONException 
	 */
	public Album refresh(Album album) throws ApiCallException {

		Logger.getLog().log(new LogEntry("refreshAlbum").addArg(album.getName()).addArg(album.getArtist().getName()));

		return getAlbum(album.getName(), album.getArtist().getName());
	}

	/**
	 * Exchanges artist object for stat-updated artist from Last.FM
	 * 
	 * @param artist Old Artist Object
	 * @return Refreshed Artist
	 * @throws ApiCallException 
	 * @throws JSONException 
	 */
	public Artist refresh(Artist artist) throws ApiCallException {

		Logger.getLog().log(new LogEntry("refreshArtist").addArg(artist.getName()));

		return getArtist(artist.getName());
	}

	/**
	 * Exchanges track object for stat-updated track from Last.FM
	 * 
	 * @param track Old Track Object
	 * @return Refreshed Track
	 * @throws ApiCallException 
	 * @throws JSONException 
	 */
	public Track refresh(Track track) throws ApiCallException {

		Logger.getLog().log(new LogEntry("refreshTrack").addArg(track.getName()).addArg(track.getArtist().getName()));

		Artist refreshedArtist = getArtist(track.getArtist().getName());
		Track refreshedTrack = getTrack(track.getName(), refreshedArtist);

		if (track.getAlbum() != null) {
			refreshedTrack.setAlbum(getAlbum(track.getAlbum().getName(), refreshedArtist));
		}

		return refreshedTrack;
	}

	/**
	 * Catch-all refresh method to update an FMObj by routing based on object class
	 * 
	 * @param obj FMObj for refreshing
	 * @return Updated FMObj
	 * @throws ApiCallException 
	 * @throws JSONException 
	 */
	public FMObj refresh(FMObj obj) throws ApiCallException {
		if (obj.getClass() == Track.class)
			return refresh((Track) obj);
		if (obj.getClass() == Album.class)
			return refresh((Album) obj);
		if (obj.getClass() == Artist.class)
			return refresh((Artist) obj);
		return null;
	}

	protected JSONObject makeGetRequest(String method, HashMap<String, String> parameters) throws ApiCallException {

		return makeGetRequest(method, parameters, null);

	}

	protected JSONObject makeGetRequest(String method, HashMap<String, String> parameters,
			HashMap<String, String> headers) throws ApiCallException {

		HttpRequest request;
		try {
			request = Unirest.get("https://ws.audioscrobbler.com/2.0/").header("Accept", "application/json")
					.header("User-Agent", "fmframework");

			parameters.put("method", method);
			parameters.put("api_key", key);
			parameters.put("format", "json");

			if (headers != null) {
				for (String key : headers.keySet()) {
					request = request.header(key, headers.get(key));
				}
			}

			if (parameters != null) {
				for (String key : parameters.keySet()) {
					request = request.queryString(key, parameters.get(key));
				}
			}

			HttpResponse<JsonNode> response = request.asJson();

			if (response.getStatus() >= 200 && response.getStatus() < 300) {

				return new JSONObject(response.getBody().toString());

			} else {
				JSONObject obj = new JSONObject(response.getBody().toString());
				
				throw new ApiCallException(method, obj.getInt("error"), obj.getString("message"));
			}
		} catch (UnirestException e) {
			e.printStackTrace();
		}

		return null;
	}
}
