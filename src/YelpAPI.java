import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Code sample for accessing the Yelp API V2.
 *
 * This program demonstrates the capability of the Yelp API version 2.0 by using
 * the Search API to query for businesses by a search term and location, and the
 * Business API to query additional information about the top result from the
 * search query.
 *
 * <p>
 * See <a href="http://www.yelp.com/developers/documentation">Yelp
 * Documentation</a> for more info.
 */
public class YelpAPI {

	private OkHttpClient client = new OkHttpClient();
	/*
	 * Update OAuth credentials below from the Yelp Developers API site:
	 * http://www.yelp.com/developers/getting_started/api_access
	 */
	private final String CONSUMER_KEY = "4KOfTP7HzlZrHdnHL8lfVg";
	private final String CONSUMER_SECRET = "HEy81sFXCPH5AMeauu9we5lpxqeLBmaQtg7eFWQACM4GKtvGGUWwpgbXr5OtfxGj";
	private String accessToken;

	/**
	 * Setup the Yelp API OAuth credentials.
	 *
	 * @param consumerKey
	 *            Consumer key
	 * @param consumerSecret
	 *            Consumer secret
	 * @param token
	 *            Token
	 * @param tokenSecret
	 *            Token secret
	 */
	public YelpAPI() {
		getAccessToken();
	}

	/**
	 * Creates and sends a request to the Search API by term and location.
	 * <p>
	 * See
	 * <a href="http://www.yelp.com/developers/documentation/v2/search_api">Yelp
	 * Search API V2</a> for more info.
	 *
	 * @param term
	 *            <tt>String</tt> of the search term to be queried
	 * @param location
	 *            <tt>String</tt> of the location
	 * @return <tt>String</tt> JSON Response
	 */
	public String searchForBusinessesByLocation(Map<String, String> params) {
		StringBuilder strBuider = new StringBuilder();
		strBuider.append("https://api.yelp.com/v3/businesses/search?");
		int i = 0;
		for (Entry<String, String> entry : params.entrySet()) {
			strBuider.append(entry.getKey());
			strBuider.append("=");
			strBuider.append(entry.getValue());
			if (i < params.size() - 1)
				strBuider.append("&");
			i++;
		}
		Request request = new Builder().url(strBuider.toString()).get().addHeader("cache-control", "no-cache")
				.addHeader("authorization", "Bearer" + " " + accessToken).build();

		try {
			Response response = client.newCall(request).execute();
			return response.body().string().trim();

		} catch (IOException e) {
		}
		return null;
	}

	/**
	 * Get the access token
	 */
	private void getAccessToken() {

		MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
		RequestBody body = RequestBody.create(mediaType,
				"client_id=" + CONSUMER_KEY + "&client_secret=" + CONSUMER_SECRET + "&grant_type=client_credentials");
		Request request = new Request.Builder().url("https://api.yelp.com/oauth2/token").post(body)
				.addHeader("cache-control", "no-cache").build();

		try {
			okhttp3.Response response = client.newCall(request).execute();
			String respbody = response.body().string().trim();
			JSONParser parser = new JSONParser();
			JSONObject json = (JSONObject) parser.parse(respbody);
			accessToken = (String) json.get("access_token");
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	}
}
