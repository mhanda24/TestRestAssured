package com.twitter.commons;

import static org.hamcrest.Matchers.lessThan;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import com.twitter.Constants.Auth;
import com.twitter.Constants.Base;
import io.restassured.RestAssured;
import io.restassured.authentication.AuthenticationScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import static io.restassured.RestAssured.given;

public class RestUtilities {
	
	public static String END_POINT;
	public static RequestSpecBuilder REQUEST_BUILDER;
	public static RequestSpecification REQUEST_SPECS;
	public static ResponseSpecBuilder RESPONSE_BUILDER;
	public static ResponseSpecification RESPONSE_SPECS;
	
	
	public static void setEndPoint(String ePoint){
		END_POINT = ePoint;
		
	}
	
	public static RequestSpecification getRequestSpecification(){
		
		REQUEST_BUILDER = new RequestSpecBuilder();
		AuthenticationScheme oAuth=RestAssured.oauth(Auth.CONSUMER_KEY,Auth.CONSUMER_SECRET,Auth.ACCESS_TOKEN, Auth.ACCESS_TOKEN_SECRET);
		REQUEST_BUILDER.setAuth(oAuth);
		REQUEST_BUILDER.setBaseUri(Base.BASE_URI);
		REQUEST_SPECS = REQUEST_BUILDER.build();
		return REQUEST_SPECS;
	}
	
	public static ResponseSpecification getResponseSpecification(){
		RESPONSE_BUILDER = new ResponseSpecBuilder();
		RESPONSE_BUILDER.expectStatusCode(200);
		RESPONSE_BUILDER.expectResponseTime(lessThan(1500L), TimeUnit.MILLISECONDS);
		RESPONSE_SPECS = RESPONSE_BUILDER.build();
		return RESPONSE_SPECS;
	}

	
	public static RequestSpecification createQueryParam(RequestSpecification rSpec, String key, String value){
		return rSpec.queryParam(key, value);
	}

	public static RequestSpecification createQueryParam(RequestSpecification rSpec, Map<String,String> queryMap){
		return rSpec.queryParams(queryMap);
	}

	public static RequestSpecification createPathParam(RequestSpecification rSpec, String key, String value){
		return rSpec.pathParam(key, value);
	}

	public static Response getResponse(){
		return given().get(END_POINT);
	}
	
	public static Response getResponse(String type){
		
		Response response = null;
		if(type.equalsIgnoreCase("get")){
			response=given().spec(REQUEST_SPECS).when().get(END_POINT);
		}else if(type.equalsIgnoreCase("post")){
			response=given().spec(REQUEST_SPECS).when().post(END_POINT);
		}else if(type.equalsIgnoreCase("put")){
			response=given().spec(REQUEST_SPECS).when().put(END_POINT);
		}else if(type.equalsIgnoreCase("delete")){
			response=given().spec(REQUEST_SPECS).when().delete(END_POINT);
		}else{
			System.out.println("Type is not correct");
		}
		return response;
	}

	public static JsonPath jsonPath(Response res){
		String path = res.asString();
		return new JsonPath(path);
	}
	
	public static XmlPath xmlPath(Response res){
		String path = res.asString();
		return new XmlPath(path);
	}

	public static void setContentType(ContentType type){
		given().contentType(type);
	}
	
	public static void resetBasePath(){
		RestAssured.basePath = null;
	}
}
