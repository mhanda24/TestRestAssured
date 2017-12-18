package Basic;

import static io.restassured.RestAssured.given;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.lessThan;
import java.util.concurrent.TimeUnit;

public class TwitterResponseTime {
	String consumerKey="9rhxwRnI7WlOYtcLFKeHRB4bO";
	String consumerSecret="Ut9OxqNra0nbzlYVQjdYsiVSdKJX6p53DZiNGera13XqfQfl5z";
	String accessToken="936261998362447873-TENFdoiIpSjtlsxDrQk5hFWCoCbpdbH";
	String accessTokenSecret="5N4cHckP81NxY21k2aW5vijrUMbYUpRk9CSn4CE4eLcX8";
	
	
	@BeforeClass
	public void setup(){
		RestAssured.baseURI="https://api.twitter.com";
		RestAssured.basePath="/1.1/statuses";

	}

	@Test(enabled=false)
	public void ResponseTest1() {
	long responsetime=	
		given()
			.auth()
			.oauth(consumerKey, consumerSecret, accessToken, accessTokenSecret)
			.queryParam("screen_name","testRestAssured")
		.when()
			.get("/user_timeline.json")
			.time();
			//.timeIn(TimeUnit.MILLISECONDS);
	System.out.println("Response captured in " + responsetime + " " + TimeUnit.MILLISECONDS);
	}

	@Test(enabled=true)
	public void ResponseTest2() {
		
		given()
			.auth()
			.oauth(consumerKey, consumerSecret, accessToken, accessTokenSecret)
			.queryParam("screen_name","testRestAssured")
		.when()
			.get("/user_timeline.json")
		.then()
			.time(lessThan(1L), TimeUnit.SECONDS)
			//.log().body()
			.rootPath("entities.hashtags[0]")
			.body("text", hasItem("testing"),
					"size()", lessThan(2))
			.rootPath("user")
			.body("name", hasItem("TestRestAssured"));
		

}
}
