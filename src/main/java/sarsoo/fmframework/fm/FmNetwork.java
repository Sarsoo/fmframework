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

	public FmNetwork(String key) {
		this.key = key;
	}

	public FmNetwork(String key, String userName) {
		this.key = key;
		this.userName = userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	public void clearUser() {
		this.userName = null;
	}

	public Album getAlbum(String name, String artist) {
		if (ConsoleHandler.isVerbose())
			ConsoleHandler.getConsole().write(">>getAlbum: " + name + " " + artist);

		HttpRequest request;
		try {
			request = Unirest.get("http://ws.audioscrobbler.com/2.0/").header("Accept", "application/json")
					.header("User-Agent", "fmframework").queryString("method", "album.getinfo")
					.queryString("artist", artist).queryString("album", name).queryString("api_key", key)
					.queryString("format", "json");

			if (userName != null)
				request.queryString("username", userName);

			HttpResponse<JsonNode> response = request.asJson();

			JSONObject obj = new JSONObject(response.getBody().toString());

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
						ConsoleHandler.getConsole()
								.write("ERROR: No play count for " + nameIn + " , " + e.getMessage());
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

		} catch (UnirestException e) {
			e.printStackTrace();
		}

		return null;

	}

	public Artist getArtist(String name) {
		if (ConsoleHandler.isVerbose())
			ConsoleHandler.getConsole().write(">>getArtist: " + name);

		HttpRequest request;
		try {
			request = Unirest.get("http://ws.audioscrobbler.com/2.0/").header("Accept", "application/json")
					.header("User-Agent", "fmframework").queryString("method", "artist.getinfo")
					.queryString("artist", name).queryString("api_key", key).queryString("format", "json");

			if (userName != null)
				request.queryString("username", userName);

			HttpResponse<JsonNode> response = request.asJson();

			JSONObject obj = new JSONObject(response.getBody().toString());

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
						ConsoleHandler.getConsole()
								.write("ERROR: No listeners for " + artistName + " , " + e.getMessage());
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

		} catch (UnirestException e) {
			e.printStackTrace();
		}

		return null;

	}

	public Track getTrack(String name, String artist) {
		if (ConsoleHandler.isVerbose())
			ConsoleHandler.getConsole().write(">>getTrack: " + name + " " + artist);

		HttpRequest request;
		try {
			request = Unirest.get("http://ws.audioscrobbler.com/2.0/").header("Accept", "application/json")
					.header("User-Agent", "fmframework").queryString("method", "track.getinfo")
					.queryString("artist", artist).queryString("track", name).queryString("api_key", key)
					.queryString("format", "json");

			if (userName != null)
				request.queryString("username", userName);

			HttpResponse<JsonNode> response = request.asJson();

			JSONObject obj = new JSONObject(response.getBody().toString());

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
						ConsoleHandler.getConsole()
								.write("ERROR: No play count for " + nameIn + " , " + e.getMessage());
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

		} catch (UnirestException e) {
			e.printStackTrace();
		}

		return null;
	}

	public Album refresh(Album album) {
		return getAlbum(album.getName(), album.getArtist().getName());
	}
	
	public Artist refresh(Artist artist) {
		return getArtist(artist.getName());
	}
	
	public Track refresh(Track track) {
		Track refreshedTrack = getTrack(track.getName(), track.getArtist().getName());
		
		refreshedTrack.setAlbum(refresh(track.getAlbum()));
		
		return refreshedTrack;
	}
	
	public FMObj refresh(FMObj obj) {
		if(obj.getClass() == Track.class) return refresh((Track)obj);
		if(obj.getClass() == Album.class) return refresh((Album)obj);
		if(obj.getClass() == Artist.class) return refresh((Artist)obj);
		return null;
	}
}
