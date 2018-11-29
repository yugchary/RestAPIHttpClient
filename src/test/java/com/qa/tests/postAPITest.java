package com.qa.tests;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.data.Users;
import com.qa.util.TestUtil;

public class postAPITest extends TestBase {
	
	TestBase testBase;
	String serviceurl;
	String apiurl;
	String url;
	RestClient restClient;
	CloseableHttpResponse closeableHttpResponse, closebaleHttpResponse;
	
	
	@BeforeMethod
	public void setUP() {
		testBase = new TestBase();
		serviceurl = prop.getProperty("URL");
		apiurl = prop.getProperty("serviceURL");
		url = serviceurl + apiurl;
		
	}
	
	
	@Test()
	public void postAPI() throws JsonGenerationException, JsonMappingException, IOException {
		restClient = new RestClient();
		HashMap<String, String> headermap = new HashMap<String, String>();
		headermap.put("Conten-Typeww", "application/json");
		
		
		//jackson API
		ObjectMapper mapper = new ObjectMapper();
		Users users = new Users("morpheus", "leader");
		
		//object to json 
		mapper.writeValue(new File("C:\\Users\\akkyu01\\eclipse-workspace\\JavaTraining\\RestAPITest\\src\\main\\java\\com\\qa\\data\\users.json"), users);
		
		
		//json object to json string (marshelling)
		String usersJsonString = mapper.writeValueAsString(users);
		System.out.println(usersJsonString);
		
		closeableHttpResponse = restClient.post(url, usersJsonString, headermap);
		
		int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status code.....> "+statusCode);		
		Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_201);		
		
		
		String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
		
		JSONObject responseJson = new JSONObject(responseString);
		System.out.println("Response JSON from API....>" + responseJson);
		
		
		
		
		Header[] headerArray = closeableHttpResponse.getAllHeaders();
		
		HashMap<String, String> allHeaders = new HashMap<String, String>();
		
		for(Header header:headerArray) {
			allHeaders.put(header.getName(), header.getValue());
		}
		
		System.out.println("Hearder Array ...>" + allHeaders);
		
		//json string to json object (unmarshalling)
		Users usersResObj= mapper.readValue(responseString, Users.class);
		System.out.println(usersResObj);
		Assert.assertTrue(users.getName().equals(usersResObj.getName()));
		//Assert.assertTrue(users.getName().equals(usrResObj.getName()));
		System.out.println(users.getJob().equals(usersResObj.getJob()));
	}
	
	@Test
	public void postAPI2() throws JsonGenerationException, JsonMappingException, IOException {
		restClient = new RestClient();
		HashMap<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("Content-Type", "application/json");
		
		//jackson API:
		ObjectMapper mapper = new ObjectMapper();
		Users users = new Users("morpheus", "leader"); //expected users obejct
		
		//object to json file:
		mapper.writeValue(new File("C:\\Users\\akkyu01\\eclipse-workspace\\JavaTraining\\RestAPITest\\src\\main\\java\\com\\qa\\data\\users.json"), users);
		
		//java object to json in String: 
		String usersJsonString = mapper.writeValueAsString(users);
		System.out.println(usersJsonString);
		
		closebaleHttpResponse = restClient.post(url, usersJsonString, headerMap); //call the API
		
		//validate response from API:
		//1. status code:
		int statusCode = closebaleHttpResponse.getStatusLine().getStatusCode();
		Assert.assertEquals(statusCode, testBase.RESPONSE_STATUS_CODE_201);
		
		//2. JsonString:
		String responseString = EntityUtils.toString(closebaleHttpResponse.getEntity(), "UTF-8");
		
		JSONObject responseJson = new JSONObject(responseString);
		System.out.println("The response from API is:"+ responseJson);
		
		//json to java object:
		Users usersResObj = mapper.readValue(responseString, Users.class); //actual users object
		System.out.println(usersResObj);
		
		Assert.assertTrue(users.getName().equals(usersResObj.getName()));
		
		Assert.assertTrue(users.getJob().equals(usersResObj.getJob()));
		
		System.out.println(usersResObj.getId());
		System.out.println(usersResObj.getCreatedAt());
	}


}
