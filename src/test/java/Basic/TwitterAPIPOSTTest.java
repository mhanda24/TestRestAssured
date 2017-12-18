package Basic;

import static io.restassured.RestAssured.given;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;


public class TwitterAPIPOSTTest {
	
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

	/*
	 * description="Extract response in 2 ways. 1 with extract.response then use response.path for a particular attribut"
			+ "2 way is to convert the resonse string to jsonPath an duse get method for a particular attribute
	 */
	@Test(enabled=true)
	public void POSTTweetTest() {
		Response response=	
		given()
			.auth()
			.oauth(consumerKey, consumerSecret, accessToken, accessTokenSecret)
			.queryParam("status","My First Tweet 12")
		.when()
			.post("/update.json")
		.then()
			.log()
			.body()
			.statusCode(200)
			.extract().response();
		
		//System.out.println(response.prettyPrint());
		
		tweetId = response.path("id_str");
		System.out.println("id of the tweet is " +tweetId);
		
		String responsePath = response.asString();
		//System.out.println("String format of response " + responsePath);
		
		//for JSON
		JsonPath jpath = new JsonPath(responsePath);
		
		//System.out.println("JSON format of response " + responsePath );
		String name = jpath.get("user.name");
		System.out.println("Name who tweets " + name);
	}

	//@Test(dependsOnMethods={"POSTTweetTest"},enabled=false)
	@Test(enabled=true)
	public void ReadTweetTest() {
		Response response=	
		given()
			.auth()
			.oauth(consumerKey, consumerSecret, accessToken, accessTokenSecret)
			.queryParam("id",tweetId)
		.when()
			.get("/show.json")
		.then()
			.extract().response();
		//System.out.println(response.prettyPrint());
		
		String text = response.path("text");
		System.out.println("The tweet text is " + text);
	}
	
	
	@Test(dependsOnMethods={"ReadTweetTest"},enabled=false)
	public void deleteTweetTest() {
		
		given()
			.auth()
			.oauth(consumerKey, consumerSecret, accessToken, accessTokenSecret)
			//.queryParam("id",tweetId)
			.pathParam("id", tweetId)  // This will put the value in the curly braces in exact resource
		.when()
			.post("/destroy/{id}.json")
		.then()
			.statusCode(200);
		
}
}