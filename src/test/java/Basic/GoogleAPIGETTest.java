package Basic;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.equalTo;
import static io.restassured.RestAssured.given;

public class GoogleAPIGETTest {

	/*
	 *
	 */

	@BeforeClass
	public void setup(){
		RestAssured.baseURI="https://maps.googleapis.com";
		RestAssured.basePath="/maps/api";

	}


	@Test(enabled=true)
	public void getResponseTest() {
		Response response=
		given()
			.param("origins", "Washington,DC")
			.param("destinations", "New+York+City,NY")
			.param("key", "AIzaSyCpLrcBnkRXsQhZE2E-IiSvJEelBJjwtgA")
		.when()
			.get("/distancematrix/xml")
		.then()
			.extract().response();
		
			
		String responsePath = response.asString();
		System.out.println(responsePath);
		
		
		String text = response.path("DistanceMatrixResponse.row.element.distance.text");
		System.out.println("Text is " +text);
		
		XmlPath xmlPath = new XmlPath(responsePath);
		
		String value = xmlPath.get("DistanceMatrixResponse.row.element.distance.value");
		Assert.assertEquals(value, "361972");
	}
	
	@Test(enabled=false)
	public void responseVerificationTest() {
	
				given()
				.param("origins", "Washington,DC")
				.param("destinations", "New+York+City,NY")
				.param("key", "AIzaSyCpLrcBnkRXsQhZE2E-IiSvJEelBJjwtgA")
			.when()
				.get("/distancematrix/json")
			.then()
				.statusCode(200)
				.and()
				.contentType(ContentType.JSON)
				.body("rows[0].elements[0].distance.text", equalTo("362 km") );
	
		
	}
	
	
}
