package sarsoo.fmframework.fm;

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

import java.util.HashMap;

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

	public Album getAlbum(String name, String artist) {
		return getAlbum(name, getArtist(artist));
	}

	/**
	 * Get an album from Last.FM
	 * 
	 * @param name   Album Name
	 * @param artist Artist Name
	 * @return Album
	 */
	public Album getAlbum(String name, Artist artist) {

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
				log.logInfo(new InfoEntry("getAlbum").addArg("no mbid for").addArg(nameIn).addArg(e.getMessage()));
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
	 */
	public Artist getArtist(String name) {

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

	public Track getTrack(String name, String artist) {
		return getTrack(name, getArtist(artist));
	}

	/**
	 * Get a track from Last.FM
	 * 
	 * @param name   Track Name
	 * @param artist Artist Name
	 * @return Track
	 */
	public Track getTrack(String name, Artist artist) {

		Log log = Logger.getLog();
		log.log(new LogEntry("getTrack").addArg(name).addArg(artist.getName()));

		HashMap<String, String> parameters = new HashMap<String, String>();

		parameters.put("artist", artist.getName());
		parameters.put("track", name);

		if (userName != null)
			parameters.put("username", userName);

		JSONObject obj = makeGetRequest("track.getinfo", parameters);

		String nameIn;

		try {

			JSONObject trackJson = obj.getJSONObject("track");

			nameIn = trackJson.getString("name");

			TrackBuilder builder = new TrackBuilder(nameIn, artist);

			try {
				builder.setMbid(trackJson.getString("mbid"));
			} catch (JSONException e) {
				log.logInfo(new InfoEntry("getTrack").addArg("no mbid for").addArg(nameIn).addArg(e.getMessage()));
			}

			try {
				builder.setUrl(trackJson.getString("url"));
			} catch (JSONException e) {
				log.logInfo(new InfoEntry("getTrack").addArg("no url for").addArg(nameIn).addArg(e.getMessage()));
			}

			try {
				builder.setListeners(trackJson.getInt("listeners"));
			} catch (JSONException e) {
				log.logInfo(new InfoEntry("getTrack").addArg("no listeners for").addArg(nameIn).addArg(e.getMessage()));
			}

			try {
				builder.setPlayCount(trackJson.getInt("playcount"));
			} catch (JSONException e) {
				log.logInfo(
						new InfoEntry("getTrack").addArg("no play count for").addArg(nameIn).addArg(e.getMessage()));
			}

			try {
				builder.setUserPlayCount(trackJson.getInt("userplaycount"));
			} catch (JSONException e) {
				log.logInfo(new InfoEntry("getTrack").addArg("no user play count for").addArg(nameIn)
						.addArg(e.getMessage()));
			}

			try {
				JSONObject wikiJson = trackJson.getJSONObject("wiki");

				Wiki wiki = new Wiki(wikiJson.getString("published"), wikiJson.getString("summary"),
						wikiJson.getString("content"));

				builder.setWiki(wiki);

			} catch (JSONException e) {
				log.logInfo(new InfoEntry("getTrack").addArg("no wiki for").addArg(nameIn).addArg(e.getMessage()));
			}

			return builder.build();

		} catch (JSONException e) {
			log.logInfo(new InfoEntry("getTrack").addArg("track name not found").addArg(e.getMessage()));
		}

		return null;
	}

	/**
	 * Exchanges album object for stat-updated album from Last.FM
	 * 
	 * @param album Old Album Object
	 * @return Refreshed Album
	 */
	public Album refresh(Album album) {

		Logger.getLog().log(new LogEntry("refreshAlbum").addArg(album.getName()).addArg(album.getArtist().getName()));

		return getAlbum(album.getName(), album.getArtist().getName());
	}

	/**
	 * Exchanges artist object for stat-updated artist from Last.FM
	 * 
	 * @param artist Old Artist Object
	 * @return Refreshed Artist
	 */
	public Artist refresh(Artist artist) {

		Logger.getLog().log(new LogEntry("refreshArtist").addArg(artist.getName()));

		return getArtist(artist.getName());
	}

	/**
	 * Exchanges track object for stat-updated track from Last.FM
	 * 
	 * @param track Old Track Object
	 * @return Refreshed Track
	 */
	public Track refresh(Track track) {

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
	 */
	public FMObj refresh(FMObj obj) {
		if (obj.getClass() == Track.class)
			return refresh((Track) obj);
		if (obj.getClass() == Album.class)
			return refresh((Album) obj);
		if (obj.getClass() == Artist.class)
			return refresh((Artist) obj);
		return null;
	}

	protected JSONObject makeGetRequest(String method, HashMap<String, String> parameters) {

		return makeGetRequest(method, parameters, null);

	}

	protected JSONObject makeGetRequest(String method, HashMap<String, String> parameters,
			HashMap<String, String> headers) {

		HttpRequest request;
		try {
			request = Unirest.get("https://ws.audioscrobbler.com/2.0/").header("Accept", "application/json")
					.header("User-Agent", "fmframework").queryString("method", method);

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

			request = request.queryString("api_key", key).queryString("format", "json");

			HttpResponse<JsonNode> response = request.asJson();

			if (response.getStatus() == 200) {

				return new JSONObject(response.getBody().toString());

			} else {
				JSONObject obj = new JSONObject(response.getBody().toString());
				Logger.getLog().logError(new ErrorEntry("HTTP Get").setErrorCode(response.getStatus())
						.addArg(Integer.toString(obj.getInt("error"))).addArg(obj.getString("message")));
				return null;
			}
		} catch (UnirestException e) {
			e.printStackTrace();
		}

		return null;
	}
}
