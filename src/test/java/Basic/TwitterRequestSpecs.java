package Basic;

import static io.restassured.RestAssured.given;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.authentication.AuthenticationScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.lessThan;

public class TwitterRequestSpecs {
	String consumerKey="9rhxwRnI7WlOYtcLFKeHRB4bO";
	String consumerSecret="Ut9OxqNra0nbzlYVQjdYsiVSdKJX6p53DZiNGera13XqfQfl5z";
	String accessToken="936261998362447873-TENFdoiIpSjtlsxDrQk5hFWCoCbpdbH";
	String accessTokenSecret="5N4cHckP81NxY21k2aW5vijrUMbYUpRk9CSn4CE4eLcX8";
	
	RequestSpecBuilder RequestBuilder;
	static RequestSpecification RequestSpecs;
	
	@BeforeClass
	public void setup(){
		RequestBuilder = new RequestSpecBuilder();
		AuthenticationScheme oAuth=RestAssured.oauth(consumerKey, consumerSecret, accessToken, accessTokenSecret);
		RequestBuilder.setAuth(oAuth);
		RequestBuilder.setBaseUri("https://api.twitter.com");
		RequestBuilder.setBasePath("/1.1/statuses");
		RequestBuilder.addQueryParam("screen_name","testRestAssured");
		RequestSpecs = RequestBuilder.build();
		

	}


	@Test(enabled=true)
	public void AssertTweetTest() {
		
		given()
			.spec(RequestSpecs)
		.when()
			.get("/user_timeline.json")
		.then()
			.body("entities.hashtags[0].text", hasItem("testing"),
					"entities.hashtags[0].size()", lessThan(2));
		

}
}
