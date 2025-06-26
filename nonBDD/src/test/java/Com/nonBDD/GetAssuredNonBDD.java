package Com.nonBDD;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class GetAssuredNonBDD {
	
	//GET METHOD
	@Test(priority=1)
	public void GetRequest() {
		RestAssured.baseURI="https://jsonplaceholder.typicode.com/";
		RequestSpecification reqspec = RestAssured.given();
		Response response =  reqspec.request(Method.GET , "posts");
		System.out.println(response.asPrettyString());
		System.out.println(response.getStatusLine());
		System.out.println("GET COMPLETED");
	}

	//POST METHOD
	@Test(priority=2)
	public void PostRequest() {
		RestAssured.baseURI="https://jsonplaceholder.typicode.com/";

		String jsonBody = "{\r\n"
				+ "  \"userId\": 1,\r\n"
				+ "  \"title\": \"Complete Automation Testing Project\",\r\n"
				+ "  \"completed\": false\r\n"
				+ "}";

		RequestSpecification Reqspec =  RestAssured.given()
				.header("Content-Type","application/json")
				.body(jsonBody)
				;

		Response res =  Reqspec.request(Method.POST, "posts");
		System.out.println(res.asPrettyString());
		System.out.println(res.getStatusCode());
		System.out.println("POST COMPLETED");

	}

	//PUT METHOD 
	@Test(priority = 3)
	public void PutRequest() {
		RestAssured.baseURI="https://jsonplaceholder.typicode.com/";
		String jbody = " {\r\n"
				+ "    \"userId\": 100,\r\n"
				+ "    \"title\": \"ea molestias quasi exercitationem repellat qui ipsa sit aut\",\r\n"
				+ "    \"body\": \"et iusto sed quo iure\\nvoluptatem occaecati omnis eligendi aut ad\\nvol\"\r\n"
				+ "  }";
		RequestSpecification reqspec =  RestAssured.given()
				.header("Content-Type" , "application/json")
				.body(jbody);
		Response res =	reqspec.request(Method.PUT,"posts/3");
		System.out.println(res.asPrettyString());
		System.out.println(res.getStatusCode());
		System.out.println(res.getStatusLine());
		System.out.println("PUT COMPLETED");

	}
	
	
	//DELETE METHOD
	@Test(priority = 4)
	public void DeleteRequest() {
		RestAssured.baseURI="https://jsonplaceholder.typicode.com/";
		RequestSpecification reqspec = RestAssured.given();
		Response res = reqspec.request(Method.DELETE , "posts/5");
		System.out.println(res.asPrettyString());
		System.out.println(res.getStatusCode()); 
		System.out.println(res.getStatusLine()); 
		System.out.println("DELETE COMPLETED");
		
	}
	@Test(priority = 5)
	public void VerifyTimeForGetRequest() {
		RestAssured.baseURI="https://jsonplaceholder.typicode.com";
		Response response = RestAssured.given().when().get("/posts");
		long ResponseTime = response.getTime();
		System.out.println(ResponseTime + "ms");
		Assert.assertTrue(ResponseTime < 1000, "request time exceeds the limit!!!");
		System.out.println("Response Time Taken is = "+ ResponseTime + "ms, it is sucessfully GET within the limits ");
	}
	@Test(priority=6)
	public void VerifyInvalidStatusCode() {
		RestAssured.baseURI="https://jsonplaceholder.typicode.com";
		RequestSpecification reqspec = RestAssured.given();
		Response response = reqspec.request(Method.GET,"pos");
		int stscode = response.getStatusCode();
		System.out.println("this is status Code:"+stscode);
		Assert.assertTrue(stscode == 404, "the status code is not 404 , it is working properly by invalid url!!");
		System.out.println("the page will sucessfully not working properly");
		
	}
	@Test(priority=7)
	public void Verify_Authentication_Without_Credentials() {
		RestAssured.baseURI="https://reqres.in";
		Response response = RestAssured.given().header("Content-Type", "application/json").when().post("api/login");
		System.out.println(response.asPrettyString());
		System.out.println(response.getStatusCode());
		Assert.assertTrue(response.getStatusCode()==400 || response.getStatusCode()==401 , "Expected 400 or 401 but got "+response.getStatusCode());
	}
	
	@Test(priority=8)
	public void Verify_Query_Parameters() {
		RestAssured.baseURI="https://jsonplaceholder.typicode.com";
		Response response = RestAssured.given()
				.queryParam("userId", 2)
				.queryParam("id",13)
				.when()
				.get("/posts");
		
		response.then().log().body().statusCode(200);
		System.out.println(response.getStatusCode());
		
	}
	@Test(priority=9)
	public void Verify_Response_header() {
		RestAssured.baseURI="https://jsonplaceholder.typicode.com";
		Response response = RestAssured.given().header("Content-Type","application/json").when().get("/posts");
		String reshead =  response.getHeader("Content-Type");
		System.out.println(reshead);		
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(reshead, "application/json; charset=utf-8");
		
	}
	
	
	@Test(priority=10)     
    public void InvalidPostData() {
    	RestAssured.baseURI = "https://reqres.in";
    	Response response = RestAssured
    			.given()
    			.header("Content-Type", "application/json")
    			.body("{ \"email\": \"eve.holt@reqres.in\"}")  
    			.when()
    			.post("/api/login");
    	
    	System.out.println("Response Body:\n" + response.asPrettyString());
    Assert.assertTrue(response.getStatusCode()== 400|| response.getStatusCode()==401);
    String error = response.jsonPath().getString("error");
    Assert.assertEquals(error, "Missing API key.");
	}
	
	@Test(priority=11)
	public void Valid_Login_Post_Data() {
		 RestAssured.baseURI = "https://reqres.in";
	        Response response = RestAssured
	            .given()
	            .header("Content-Type", "application/json")
	            .header("x-api-key" ,"reqres-free-v1")
	            .body("{ \"email\": \"eve.holt@reqres.in\" , \"password\": \"art123\"}")  
	            .when()
	            .post("/api/login");

	        System.out.println("Response Body:\n" + response.asPrettyString());
	        String token = response.jsonPath().getString("token");
	        System.out.println(token);
	        Assert.assertNotNull(token, "token should not be null");
	}
	
}
