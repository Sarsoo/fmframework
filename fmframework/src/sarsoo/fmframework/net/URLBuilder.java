package sarsoo.fmframework.net;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.Instant;
import java.time.LocalDateTime;

public class URLBuilder {
	
	public enum FMObjType{
		ARTIST,
		ALBUM,
		TRACK
	}

	public static String getArtistInfoUrl(String artist, String username) {
		String urlString;
		try {
			urlString = String.format(
					"http://ws.audioscrobbler.com/2.0/?method=artist.getInfo&artist=%s&autocorrect=0&username=%s&api_key=%s",
					URLEncoder.encode(artist, "UTF-8"), URLEncoder.encode(username, "UTF-8"),
					URLEncoder.encode(Key.getKey(), "UTF-8"));
			return urlString;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public static String getArtistInfoMbidUrl(String mbid, String username) {
		String urlString;
		try {
			urlString = String.format(
					"http://ws.audioscrobbler.com/2.0/?method=artist.getInfo&mbid=%s&autocorrect=0&username=%s&api_key=%s",
					URLEncoder.encode(mbid, "UTF-8"), URLEncoder.encode(username, "UTF-8"),
					URLEncoder.encode(Key.getKey(), "UTF-8"));
			return urlString;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public static String getAlbumInfoUrl(String album, String artist, String username) {
		String urlString;
		try {
			urlString = String.format(
					"http://ws.audioscrobbler.com/2.0/?method=album.getInfo&album=%s&artist=%s&autocorrect=0&username=%s&api_key=%s",
					URLEncoder.encode(album, "UTF-8"), URLEncoder.encode(artist, "UTF-8"),
					URLEncoder.encode(username, "UTF-8"), URLEncoder.encode(Key.getKey(), "UTF-8"));
			return urlString;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String getTrackInfoUrl(String name, String artist, String username) {
		String urlString;
		try {
			urlString = String.format(
					"http://ws.audioscrobbler.com/2.0/?method=track.getInfo&track=%s&artist=%s&autocorrect=0&username=%s&api_key=%s",
					URLEncoder.encode(name, "UTF-8"), URLEncoder.encode(artist, "UTF-8"),
					URLEncoder.encode(username, "UTF-8"), URLEncoder.encode(Key.getKey(), "UTF-8"));
			return urlString;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String getLastTrackUrl(String username) {
		String urlString;
		try {
			urlString = String.format(
					"http://ws.audioscrobbler.com/2.0/?method=user.getrecenttracks&user=%s&limit=1&api_key=%s",
					URLEncoder.encode(username, "UTF-8"), URLEncoder.encode(Key.getKey(), "UTF-8"));
			return urlString;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println(urlString);
		return null;
	}

	public static String getUserInfoUrl(String username) {
		String urlString;
		try {
			urlString = String.format("http://ws.audioscrobbler.com/2.0/?method=user.getinfo&user=%s&api_key=%s",
					URLEncoder.encode(username, "UTF-8"), URLEncoder.encode(Key.getKey(), "UTF-8"));
			return urlString;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println(urlString);
		return null;
	}

	public static String getLyricsUrl(String trackName, String artistName) {
		String track = trackName.replaceAll(" ", "-");
		// track = track.replaceAll("[^a-zA-Z ]", "").toLowerCase();
		String artist = artistName.replaceAll(" ", "-");
		// artist = artist.replaceAll("[^a-zA-Z ]", "").toLowerCase();
		String urlString = String.format("http://genius.com/%s-%s-lyrics", artist, track);
		return urlString;
	}

	public static String getUserPersonalTags(String username, String tag) {
		String urlString;
		try {
			urlString = String.format("http://ws.audioscrobbler.com/2.0/?method=user.getpersonaltags&user=%s&tag=%s&taggingtype=artist&api_key=%s",
					URLEncoder.encode(username, "UTF-8"), 
					URLEncoder.encode(tag, "UTF-8"), 
//					URLEncoder.encode(taggingType.toString().toLowerCase(), "UTF-8"), 
					URLEncoder.encode(Key.getKey(), "UTF-8"));
			return urlString;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println(urlString);
		return null;
	}
	
	public static String getUserTopTags(String username) {
		String urlString;
		try {
			urlString = String.format("http://ws.audioscrobbler.com/2.0/?method=user.gettoptags&user=%s&api_key=%s",
					URLEncoder.encode(username, "UTF-8"),  
					URLEncoder.encode(Key.getKey(), "UTF-8"));
			return urlString;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println(urlString);
		return null;
	}
	
	public static String getTodayScrobbles(String username) {
		LocalDateTime now = LocalDateTime.now();
		
		String month;
		if(now.getMonthValue() < 10) {
			month = "0" + now.getMonthValue();
		}else {
			month = Integer.toString(now.getMonthValue());
		}
		
		String day;
		if(now.getMonthValue() < 10) {
			day = "0" + now.getDayOfMonth();
		}else {
			day = Integer.toString(now.getDayOfMonth());
		}
		
		String date = String.format("%d-%s-%sT07:00:00.00Z", now.getYear(), month, day);
//		System.out.println(date);
//		long midnight = Instant.parse("2018-04-05T07:00:00.00Z").getEpochSecond();
		long midnight = Instant.parse(date).getEpochSecond();
		
		String urlString;
		try {
			urlString = String.format("http://ws.audioscrobbler.com/2.0/?method=user.getrecenttracks&user=%s&limit=200&from=%s&api_key=%s",
					URLEncoder.encode(username, "UTF-8"),
					URLEncoder.encode(Long.toString(midnight), "UTF-8"),
					URLEncoder.encode(Key.getKey(), "UTF-8"));
			return urlString;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println(urlString);
		return null;
	}

//	public static String getUserTopTags() {
//
//	}

	// public static String getRecentTracaksUrl(String username) {
	//// Date date = new Date();
	//// Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
	//// Calendar cal2 = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
	////
	//// cal.set(cal.YEAR, cal.MONTH, cal.DATE);
	//// System.out.println(cal.getTime());
	// //System.out.println(cal2.getTime());
	//
	// String urlString = String.format(
	// "http://ws.audioscrobbler.com/2.0/"
	// + "?method=user.getRecentTracks&"
	// + "user=%s&"
	// + "limit = 200&"
	// + "from=%d&"
	// + "api_key=%s",
	// username, Key.getKey());
	// return urlString;
	// }

}
