package sarsoo.fmframework.fm;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;
import com.mashape.unirest.request.HttpRequestWithBody;

import sarsoo.fmframework.error.ApiCallException;
import sarsoo.fmframework.log.Logger;
import sarsoo.fmframework.log.entry.ErrorEntry;
import sarsoo.fmframework.log.entry.LogEntry;
import sarsoo.fmframework.music.Scrobble;

public class FmAuthNetwork extends FmUserNetwork {

	protected String secretKey;

	public FmAuthNetwork(String key, String secretKey, String userName) {
		super(key, userName);
		this.secretKey = secretKey;
	}
	
	public JSONObject scrobble(Scrobble scrobble, String sk) throws ApiCallException {
		
		Logger.getLog().log(new LogEntry("scrobble").addArg(scrobble.toString()));
		
		HashMap<String, String> params = new HashMap<String, String>();
		
		params.put("artist", scrobble.getTrack().getArtist().getName());
		params.put("track", scrobble.getTrack().getName());
		params.put("timestamp", Long.toString(scrobble.getUTS()));
		params.put("sk", sk);
	
		if(scrobble.getAlbum() != null) {
			params.put("album", scrobble.getAlbum().getName());
			params.put("albumArtist", scrobble.getAlbum().getArtist().getName());
		}
		
		JSONObject obj = makeAuthPostRequest("track.scrobble", params);
		
		return obj;
		
	}

	public String getToken() throws ApiCallException {
		
		Logger.getLog().log(new LogEntry("getToken"));
		
		JSONObject obj = makeAuthGetRequest("auth.gettoken");

		return obj.getString("token");
	}

	public String getSession(String token) throws ApiCallException {
		
		Logger.getLog().log(new LogEntry("getSession"));

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("token", token);

		JSONObject obj = makeAuthPostRequest("auth.getSession", params);
		
		return obj.getJSONObject("session").getString("key");
	}

	public String getApiSignature(HashMap<String, String> parameters) {

		TreeMap<String, String> sorted = new TreeMap<>(parameters);

		String apiSig = new String();

		for (Entry<String, String> i : sorted.entrySet()) {
			if (!i.getKey().equals("format") && !i.getKey().equals("callback")) {
				apiSig += i.getKey() + i.getValue();
			}
		}

		apiSig += secretKey;

		try {
			byte[] sigBytes = apiSig.getBytes("UTF-8");
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] digest = md.digest(sigBytes);

			StringBuilder b = new StringBuilder(32);
			
			for(byte aByte : digest) {
				String hex = Integer.toHexString((int) aByte & 0xFF);
				if(hex.length() == 1) {
					b.append('0');
				}
				b.append(hex);
				
			}
			
			return b.toString();

		} catch (UnsupportedEncodingException e) {
			Logger.getLog().logError(new ErrorEntry("make API signature").addArg("unsupported encoding"));
		} 
		catch (NoSuchAlgorithmException e) {
			Logger.getLog().logError(new ErrorEntry("make API signature").addArg("can't get md5 instance"));
		}

		return null;
	}

	protected JSONObject makeAuthGetRequest(String method) throws ApiCallException {

		return makeAuthGetRequest(method, new HashMap<String, String>(), null);

	}

	protected JSONObject makeAuthGetRequest(String method, HashMap<String, String> parameters) throws ApiCallException {

		return makeAuthGetRequest(method, parameters, null);

	}

	protected JSONObject makeAuthGetRequest(String method, HashMap<String, String> parameters,
			HashMap<String, String> headers) throws ApiCallException {

		HttpRequest request;
		try {
			request = Unirest.get("https://ws.audioscrobbler.com/2.0/").header("Accept", "application/json")
					.header("User-Agent", "fmframework");

			parameters.put("method", method);
			parameters.put("api_key", key);
			parameters.put("format", "json");

			parameters.put("api_sig", getApiSignature(parameters).toString());

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

			if (response.getStatus() == 200) {

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

	protected JSONObject makeAuthPostRequest(String method) throws ApiCallException {

		return makeAuthPostRequest(method, new HashMap<String, String>(), null);

	}

	protected JSONObject makeAuthPostRequest(String method, HashMap<String, String> parameters) throws ApiCallException {

		return makeAuthPostRequest(method, parameters, null);

	}

	protected JSONObject makeAuthPostRequest(String method, HashMap<String, String> parameters,
			HashMap<String, String> headers) throws ApiCallException {

		HttpRequestWithBody request;
		try {
			request = Unirest.post("https://ws.audioscrobbler.com/2.0/").header("Accept", "application/json")
					.header("User-Agent", "fmframework");

			parameters.put("method", method);
			parameters.put("api_key", key);
			parameters.put("format", "json");

			String apiSig = getApiSignature(parameters).toString();
			
			parameters.put("api_sig", apiSig);

			if (headers != null) {
				for (String key : headers.keySet()) {
					request = request.header(key, headers.get(key));
				}
			}

			String body = new String();
			if (parameters != null) {

				body = getBodyString(parameters);

				request.body(body);
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

	public String getBodyString(Map<String, String> params) {

		String body = new String();
		
		TreeMap<String, String> sorted = new TreeMap<>(params);

		for (Iterator<Entry<String, String>> it = sorted.entrySet().iterator(); it.hasNext();) {
			Entry<String, String> entry = it.next();
			
			body += entry.getKey();
			body += '=';
			body += entry.getValue();
			
			if(it.hasNext()) {
				body += '&';
			}
		}
		return body;
	}

}
