package TestScript;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.io.File;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import Utils.RestUtils1;

import static io.restassured.RestAssured.given;

public class CreateUser {
	
	public static String baseUrl = "https://petstore.swagger.io/v2";
	 String dataJsonpath = System.getProperty("user.dir") + "//src/test/java/Payloads/Jsondata.json";
	    String templateJsonpath = System.getProperty("user.dir") +"//src/test/java/Payloads/RequestTemplate.json";
	    String expectedJsontemplate =System.getProperty("user.dir") + "//src/test/java/Payloads/ExpectedResponseData.json";
	    public Response response;
	    
	    @DataProvider(name = "userDataProvider")
	    public Object[][] readJsonData() throws IOException {
	        ObjectMapper objectMapper = new ObjectMapper();
	        List<Map<String, Object>> users = objectMapper.readValue(new File(dataJsonpath), new TypeReference<List<Map<String, Object>>>() {
	        });
	        Object[][] testData = new Object[users.size()][1];
	        for (int i = 0; i < users.size(); i++) {
	            testData[i][0] = users.get(i);
	        }
	        System.out.println("testData len--->" + testData.length);
	        return testData;
	    }

	    @Test(priority = 1, dataProvider = "userDataProvider")
	    public void getCreateAirline(Map<String, Object> data) throws IOException, JSONException {
	        System.out.println("iteration---->" + data);
	        // Read the JSON file content as a string
	        // Parse the JSON and replace placeholders in the template
	        String requestBody = RestUtils1.readAndParseFileWithJson(templateJsonpath, data);
	        String expectedResponse = RestUtils1.readAndParseFileWithJson(expectedJsontemplate, data);
	        // Send the request and return the response
	        response = given()
	                .baseUri(baseUrl)
					.basePath("/user")
	                .contentType(ContentType.JSON)
	                .body(requestBody)
	                .when()
	                .post();
//	        response.then().log().all();
	        readResponse(expectedResponse);
	    }

	    @Test(priority = 2)
	    public void readResponse(String expectedResponse) throws IOException, JSONException {
	        Response res = response;
	        res.then().log().all();
	        String jsonResponse = res.asString();
	        RestUtils1.Compare(expectedResponse,jsonResponse);
	    }
	  

}
