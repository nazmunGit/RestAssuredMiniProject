package testCases;

import org.testng.annotations.Test;

import io.restassured.response.Response;
import junit.framework.Assert;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.HashMap;

public class TC_VideoGameAPI {
	// **************GET - List of all the Video Games********
	@Test(priority =1)
	public void test_getAllVideoGames() 
	{
		given()
			.when()
				.get("http://localhost:8082/app/videogames")
			.then()
				.statusCode(200)
				.log().body();
		
	}
	//************POST- Add a new video game ****************
	@Test(priority =2)
	public void test_addNewVideoGame()
	{
		HashMap data = new HashMap();
		data.put("id", "100");
		data.put("name", "Spider-Man");
		data.put("releaseDate", "2024-08-06T17:15:44.669Z");
		data.put("reviewScore", "5");
		data.put("category", "Adventure");
		data.put("rating", "Universal");
		
		Response res=
		given()
			.contentType("application/json")
			.body(data)
		.when()
			.post("http://localhost:8082/app/videogames")
		.then()
			.statusCode(200)
			.log().body()
			.extract().response();
		
		String jsonString = res.asString();
		Assert.assertEquals(jsonString.contains("Record Added Successfully"), true);
		
	}
	//***** GET a single video game by ID *******
	@Test(priority = 3)
	public void test_getVideoGame()
	{
		given()
			.when()
				.get("http://localhost:8082/app/videogames/100")
			.then()
				.statusCode(200)
				.log().body()
				.body("videoGame.id" , equalTo("100"))
				.body("videoGame.name", equalTo("Spider-Man"));
						
	}
	//*******PUT --- update a video game*******
	@Test(priority = 4)
	public void test_UpdateVideoGame()
	{
		HashMap data = new HashMap();
		data.put("id", "100");
		data.put("name", "Bat-Man");
		data.put("releaseDate", "2024-08-06T17:35:44.669Z");
		data.put("reviewScore", "4");
		data.put("category", "Adventure");
		data.put("rating", "Universal");
		
		given()
			.contentType("application/json")
			.body(data)
		 .when()
		 	.put("http://localhost:8082/app/videogames/100")
		 .then()
		 	.statusCode(200)
		 	.log().body()
		 	.body("videoGame.id" , equalTo("100"))
			.body("videoGame.name", equalTo("Bat-Man"));
		 			
	}
	//*******DELETE ---delete a video game in DB*******
	@Test(priority = 5)
	public void test_DeleteVideoGame() throws InterruptedException
	{
		Response res=
		given()
			.delete("http://localhost:8082/app/videogames/100")
		.then()
			.statusCode(200)
			.log().body()
			.extract().response();
		Thread.sleep(3000);
		String jsonString=res.asString();
		Assert.assertEquals(jsonString.contains("Record Deleted Successfully"), true);
		
	}
	
}
