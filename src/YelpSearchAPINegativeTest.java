import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class YelpSearchAPINegativeTest {
	private static YelpAPI yelpApi;
	private final String term;
	private final String location;
	private final String zip;
	private final String latitude;
	private final String longitude;

	public YelpSearchAPINegativeTest(String term, String location, String zip, String latitude, String longitude) {
		super();

		this.term = term;
		this.location = location;
		this.zip = zip;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		yelpApi = new YelpAPI();
	}

	@Parameters
	public static List<Object[]> parserVersions() throws Exception {
		return Arrays.asList(
				new Object[][] { { "", "", "", "", "" }, { "", "", "", "7.7867703362929", "-122.399958372115" } });
	}

	@Test
	public void testByLocation() {
		Map<String, String> params = new HashMap<>();
		params.put("term", term);
		params.put("location", location);
		params.put("zip", zip);
		if (null != latitude && !latitude.trim().isEmpty())
			params.put("latitude", latitude);

		if (null != longitude && !longitude.trim().isEmpty())
			params.put("longitude", longitude);
		String searchResponseJSON = yelpApi.searchForBusinessesByLocation(params);
		assertNotNull(searchResponseJSON);
		JSONParser parser = new JSONParser();
		JSONObject response = null;
		try {
			response = (JSONObject) parser.parse(searchResponseJSON);
			assertNotNull(response);

			JSONArray businesses = (JSONArray) response.get("businesses");
			if (null == businesses)
				assertNull(businesses);
			if (null != businesses) {
				assertNotNull(businesses);
				assertTrue(businesses.size() == 0);
			}

		} catch (ParseException pe) {
			System.out.println("Error: could not parse JSON response:");
			System.out.println(searchResponseJSON);
		}
	}
}
