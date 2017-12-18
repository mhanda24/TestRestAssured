package Basic;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.equalTo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class GoogleAPIPOSTTest {

	@BeforeClass
	public void setup(){
		RestAssured.baseURI="https://maps.googleapis.com";
		RestAssured.basePath="/maps/api";

	}

	@Test
	public void POSTTest() {
		Map<String, Double> location = new HashMap<String, Double>();
		location.put("lat", -33.8669710);
		location.put("lng", 151.1958750);
		
		List<String> types = new ArrayList<String>();
		types.add("shoe_store");
		
		AddPlaces place = new AddPlaces();
		place.setLocation(location);
		place.setTypes(types);
		place.setAccuracy("50");
		place.setAddress("48 Pirrama Road, Pyrmont, NSW 2009, Australia");
		place.setLanguage("en-AU");
		place.setName("Google Shoes!");
		place.setPhone_number("(02) 9374 4000");
		place.setWebsite("http://www.google.com.au");
		
		Response response = 
		given()
			.queryParam("key", "AIzaSyAFNxOzcDNEZ9coJzPc_9N-CA8Euun2fDA")
			.body(place)
		.when()
			.post("/place/add/json")
		.then()
			.statusCode(200).and()
			.contentType(ContentType.JSON).and()
			.body("status", equalTo("OK"))
			.extract().response();
		System.out.println(response.prettyPrint());
		

	}

}
