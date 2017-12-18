package Basic;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class GoogleAPIDELETE {

	@BeforeClass
	public void setup(){
		RestAssured.baseURI="https://maps.googleapis.com";
		RestAssured.basePath="/maps/api";

	}

	@Test
	public void POSTTest() {
		Map<String, String> place_id = new HashMap<String, String>();
		place_id.put("place_id", "qgYvCi0wMDAwMDAwNjdmMDljN2Y5OjZiMTJhZTM3YjQ3OjcxN2MyNjgwY2U2Y2MxYzQ");
	
		AddPlaces place = new AddPlaces();
		place.setPlace_id(place_id);
	
		
		given()
			.queryParam("key", "AIzaSyAFNxOzcDNEZ9coJzPc_9N-CA8Euun2fDA")
			.body(place)
			.when()
				.post("/place/delete/json")
			.then()
				.statusCode(200);
				

	}

}
