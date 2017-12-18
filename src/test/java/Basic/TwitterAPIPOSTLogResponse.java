package Basic;

import static io.restassured.RestAssured.given;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.RestAssured;


public class TwitterAPIPOSTLogResponse {
	
	String consumerKey="9rhxwRnI7WlOYtcLFKeHRB4bO";
	String consumerSecret="Ut9OxqNra0nbzlYVQjdYsiVSdKJX6p53DZiNGera13XqfQfl5z";
	String accessToken="936261998362447873-TENFdoiIpSjtlsxDrQk5hFWCoCbpdbH";
	String accessTokenSecret="5N4cHckP81NxY21k2aW5vijrUMbYUpRk9CSn4CE4eLcX8";
	String tweetId="";
	
	@BeforeClass
	public void setup(){
		RestAssured.baseURI="https://api.twitter.com";
		RestAssured.basePath="/1.1/statuses";

	}


	@Test(enabled=true)
	public void POSTTweetTest() {
		given()
			.log()
			//.all()
			.ifValidationFails()
			.auth()
			.oauth(consumerKey, consumerSecret, accessToken, accessTokenSecret)
			.queryParam("status","My First Tweet 1")
		.when()
			.post("/update.json")
		.then()
			.statusCode(200);
		
	}	
}