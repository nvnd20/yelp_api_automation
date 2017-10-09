import static org.junit.Assert.assertNotNull;

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
public class YelpSearchAPIPositiveTest {
	private static YelpAPI yelpApi;
	private final String term;
	private final String location;
	private final String zip;
	

	public YelpSearchAPIPositiveTest(String term, String location, String zip) {
		super();
		this.term = term;
		this.location = location;
		this.zip = zip;
		
	}

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		yelpApi = new YelpAPI();
	}

	@Parameters
	public static List<Object[]> parserVersions() throws Exception {
		return Arrays.asList(new Object[][] { { "", "Irivine,CA", ""}, { "dinner", "Irivine,CA", ""},
				{ "dinner", "San Francisco,CA", "94016"}, });
	}

	@Test
	public void testByLocation() {
		Map<String, String> params = new HashMap<>();

		if (null != term && !term.trim().isEmpty())
			params.put("term", term);

		if (null != location && !location.trim().isEmpty())
			params.put("location", location);

		if (null != zip && !zip.trim().isEmpty())
			params.put("zip", zip);

	

		String searchResponseJSON = yelpApi.searchForBusinessesByLocation(params);
		assertNotNull(searchResponseJSON);
		JSONParser parser = new JSONParser();
		JSONObject response = null;
		try {
			response = (JSONObject) parser.parse(searchResponseJSON);
			assertNotNull(response);
			JSONArray businesses = (JSONArray) response.get("businesses");
			assertNotNull(businesses);
			JSONObject firstBusiness = (JSONObject) businesses.get(0);
			assertNotNull(firstBusiness);
			String firstBusinessID = firstBusiness.get("id").toString();
			assertNotNull(firstBusinessID);
		} catch (ParseException pe) {
			System.out.println("Error: could not parse JSON response:");
			System.out.println(searchResponseJSON);
		}
	}
}