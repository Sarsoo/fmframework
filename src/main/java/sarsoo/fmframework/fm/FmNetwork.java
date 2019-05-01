package sarsoo.fmframework.fm;

import sarsoo.fmframework.music.Album;
import sarsoo.fmframework.music.Album.AlbumBuilder;
import sarsoo.fmframework.music.Artist;
import sarsoo.fmframework.music.Artist.ArtistBuilder;
import sarsoo.fmframework.music.FMObj;
import sarsoo.fmframework.music.Track;
import sarsoo.fmframework.music.Track.TrackBuilder;
import sarsoo.fmframework.music.Wiki;
import sarsoo.fmframework.util.ConsoleHandler;

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

	@Deprecated
	public FmNetwork(String key, String userName) {
		this.key = key;
		this.userName = userName;
	}

	/**
	 * Get an album from Last.FM
	 * 
	 * @param name   Album Name
	 * @param artist Artist Name
	 * @return Album
	 */
	public Album getAlbum(String name, String artist) {
		if (ConsoleHandler.isVerbose())
			ConsoleHandler.getConsole().write(">>getAlbum: " + name + " " + artist);

		HashMap<String, String> parameters = new HashMap<String, String>();

		parameters.put("album", name);
		parameters.put("artist", artist);

		if (userName != null)
			parameters.put("username", userName);

		JSONObject obj = makeGetRequest("album.getinfo", parameters);

		String nameIn;
		String artistIn;

		try {

			JSONObject albumJson = obj.getJSONObject("album");

			nameIn = albumJson.getString("name");
			artistIn = albumJson.getString("artist");

			AlbumBuilder builder = new AlbumBuilder(nameIn, getArtist(artistIn));

			try {
				builder.setMbid(albumJson.getString("mbid"));
			} catch (JSONException e) {
				if (ConsoleHandler.isVerbose())
					ConsoleHandler.getConsole().write("ERROR: No MBID for " + nameIn + " , " + e.getMessage());
				else
					System.err.println("ERROR: No MBID for " + nameIn + " , " + e.getMessage());
			}

			try {
				builder.setUrl(albumJson.getString("url"));
			} catch (JSONException e) {
				if (ConsoleHandler.isVerbose())
					ConsoleHandler.getConsole().write("ERROR: No Url for " + nameIn + " , " + e.getMessage());
				else
					System.err.println("ERROR: No Url for " + nameIn + " , " + e.getMessage());
			}

			try {
				builder.setListeners(albumJson.getInt("listeners"));
			} catch (JSONException e) {
				if (ConsoleHandler.isVerbose())
					ConsoleHandler.getConsole().write("ERROR: No listeners for " + nameIn + " , " + e.getMessage());
				else
					System.err.println("ERROR: No listeners for " + nameIn + " , " + e.getMessage());
			}

			try {
				builder.setPlayCount(albumJson.getInt("playcount"));
			} catch (JSONException e) {
				if (ConsoleHandler.isVerbose())
					ConsoleHandler.getConsole().write("ERROR: No play count for " + nameIn + " , " + e.getMessage());
				else
					System.err.println("ERROR: No play count for " + nameIn + " , " + e.getMessage());
			}

			try {
				builder.setUserPlayCount(albumJson.getInt("userplaycount"));
			} catch (JSONException e) {

			}

			try {
				JSONObject wikiJson = albumJson.getJSONObject("wiki");

				Wiki wiki = new Wiki(wikiJson.getString("published"), wikiJson.getString("summary"),
						wikiJson.getString("content"));

				builder.setWiki(wiki);

			} catch (JSONException e) {
				if (ConsoleHandler.isVerbose())
					ConsoleHandler.getConsole().write("ERROR: No wiki for " + nameIn + " , " + e.getMessage());
				else
					System.err.println("ERROR: No wiki for " + nameIn + " , " + e.getMessage());
			}

			return builder.build();

		} catch (JSONException e) {
			if (ConsoleHandler.isVerbose())
				ConsoleHandler.getConsole().write("ERROR: Album Name Not Found, " + e.getMessage());
			else
				System.err.println("ERROR: Album Name Not Found, " + e.getMessage());
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
		if (ConsoleHandler.isVerbose())
			ConsoleHandler.getConsole().write(">>getArtist: " + name);

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
				if (ConsoleHandler.isVerbose())
					ConsoleHandler.getConsole().write("ERROR: No MBID for " + artistName + " , " + e.getMessage());
				else
					System.err.println("ERROR: No MBID for " + artistName + " , " + e.getMessage());
			}

			try {
				builder.setUrl(artistJson.getString("url"));
			} catch (JSONException e) {
				if (ConsoleHandler.isVerbose())
					ConsoleHandler.getConsole().write("ERROR: No Url for " + artistName + " , " + e.getMessage());
				else
					System.err.println("ERROR: No Url for " + artistName + " , " + e.getMessage());
			}

			try {
				builder.setListeners(artistJson.getJSONObject("stats").getInt("listeners"));
			} catch (JSONException e) {
				if (ConsoleHandler.isVerbose())
					ConsoleHandler.getConsole().write("ERROR: No listeners for " + artistName + " , " + e.getMessage());
				else
					System.err.println("ERROR: No listeners for " + artistName + " , " + e.getMessage());
			}

			try {
				builder.setPlayCount(artistJson.getJSONObject("stats").getInt("playcount"));
			} catch (JSONException e) {
				if (ConsoleHandler.isVerbose())
					ConsoleHandler.getConsole()
							.write("ERROR: No play count for " + artistName + " , " + e.getMessage());
				else
					System.err.println("ERROR: No play count for " + artistName + " , " + e.getMessage());
			}

			try {
				builder.setUserPlayCount(artistJson.getJSONObject("stats").getInt("userplaycount"));
			} catch (JSONException e) {

			}

			try {
				JSONObject wikiJson = artistJson.getJSONObject("bio");

				Wiki wiki = new Wiki(wikiJson.getString("published"), wikiJson.getString("summary"),
						wikiJson.getString("content"));

				builder.setWiki(wiki);

			} catch (JSONException e) {
				if (ConsoleHandler.isVerbose())
					ConsoleHandler.getConsole().write("ERROR: No wiki for " + artistName + " , " + e.getMessage());
				else
					System.err.println("ERROR: No wiki for " + artistName + " , " + e.getMessage());
			}

			return builder.build();

		} catch (JSONException e) {
			if (ConsoleHandler.isVerbose())
				ConsoleHandler.getConsole().write("ERROR: Arist Name Not Found, " + e.getMessage());
			else
				System.err.println("ERROR: Arist Name Not Found, " + e.getMessage());
		}

		return null;

	}

	/**
	 * Get a track from Last.FM
	 * 
	 * @param name   Track Name
	 * @param artist Artist Name
	 * @return Track
	 */
	public Track getTrack(String name, String artist) {
		if (ConsoleHandler.isVerbose())
			ConsoleHandler.getConsole().write(">>getTrack: " + name + " " + artist);

		HashMap<String, String> parameters = new HashMap<String, String>();

		parameters.put("artist", artist);
		parameters.put("track", name);

		if (userName != null)
			parameters.put("username", userName);

		JSONObject obj = makeGetRequest("track.getinfo", parameters);

		String nameIn;
		String artistIn;

		try {

			JSONObject trackJson = obj.getJSONObject("track");

			nameIn = trackJson.getString("name");
			artistIn = trackJson.getJSONObject("artist").getString("name");

			TrackBuilder builder = new TrackBuilder(nameIn, getArtist(artistIn));

			try {
				builder.setMbid(trackJson.getString("mbid"));
			} catch (JSONException e) {
				if (ConsoleHandler.isVerbose())
					ConsoleHandler.getConsole().write("ERROR: No MBID for " + nameIn + " , " + e.getMessage());
				else
					System.err.println("ERROR: No MBID for " + nameIn + " , " + e.getMessage());
			}

			try {
				builder.setUrl(trackJson.getString("url"));
			} catch (JSONException e) {
				if (ConsoleHandler.isVerbose())
					ConsoleHandler.getConsole().write("ERROR: No Url for " + nameIn + " , " + e.getMessage());
				else
					System.err.println("ERROR: No Url for " + nameIn + " , " + e.getMessage());
			}

			try {
				builder.setListeners(trackJson.getInt("listeners"));
			} catch (JSONException e) {
				if (ConsoleHandler.isVerbose())
					ConsoleHandler.getConsole().write("ERROR: No listeners for " + nameIn + " , " + e.getMessage());
				else
					System.err.println("ERROR: No listeners for " + nameIn + " , " + e.getMessage());
			}

			try {
				builder.setPlayCount(trackJson.getInt("playcount"));
			} catch (JSONException e) {
				if (ConsoleHandler.isVerbose())
					ConsoleHandler.getConsole().write("ERROR: No play count for " + nameIn + " , " + e.getMessage());
				else
					System.err.println("ERROR: No play count for " + nameIn + " , " + e.getMessage());
			}

			try {
				builder.setUserPlayCount(trackJson.getInt("userplaycount"));
			} catch (JSONException e) {

			}

			try {
				JSONObject wikiJson = trackJson.getJSONObject("wiki");

				Wiki wiki = new Wiki(wikiJson.getString("published"), wikiJson.getString("summary"),
						wikiJson.getString("content"));

				builder.setWiki(wiki);

			} catch (JSONException e) {
				if (ConsoleHandler.isVerbose())
					ConsoleHandler.getConsole().write("ERROR: No wiki for " + nameIn + " , " + e.getMessage());
				else
					System.err.println("ERROR: No wiki for " + nameIn + " , " + e.getMessage());
			}

			return builder.build();

		} catch (JSONException e) {
			if (ConsoleHandler.isVerbose())
				ConsoleHandler.getConsole().write("ERROR: Album Name Not Found, " + e.getMessage());
			else
				System.err.println("ERROR: Album Name Not Found, " + e.getMessage());
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
		if (ConsoleHandler.isVerbose())
			ConsoleHandler.getConsole().write(">>refreshAlbum: " + album.getName() + " " + album.getArtist().getName());

		return getAlbum(album.getName(), album.getArtist().getName());
	}

	/**
	 * Exchanges artist object for stat-updated artist from Last.FM
	 * 
	 * @param artist Old Artist Object
	 * @return Refreshed Artist
	 */
	public Artist refresh(Artist artist) {
		if (ConsoleHandler.isVerbose())
			ConsoleHandler.getConsole().write(">>refreshArtist: " + artist.getName());

		return getArtist(artist.getName());
	}

	/**
	 * Exchanges track object for stat-updated track from Last.FM
	 * 
	 * @param track Old Track Object
	 * @return Refreshed Track
	 */
	public Track refresh(Track track) {
		if (ConsoleHandler.isVerbose())
			ConsoleHandler.getConsole().write(">>refreshTrack: " + track.getName() + " " + track.getArtist().getName());

		Track refreshedTrack = getTrack(track.getName(), track.getArtist().getName());

		refreshedTrack.setAlbum(refresh(track.getAlbum()));

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
			request = Unirest.get("http://ws.audioscrobbler.com/2.0/").header("Accept", "application/json")
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
				System.out.println(response.getBody());
				if (ConsoleHandler.isVerbose())
					ConsoleHandler.getConsole().write("ERROR : HTTP Request Error " + response.getStatus());
				else
					System.err.println("ERROR : HTTP Request Error " + response.getStatus());
				return null;
			}
		} catch (UnirestException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	protected class RequestParam {
		
		int intparam;
		String strparam;
		
		public RequestParam(int param) {
			
		}
		
	}
}
