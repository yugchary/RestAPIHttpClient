package com.qa.tests;

import java.beans.AppletInitializer;
import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.annotation.JsonAppend.Prop;
import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.util.TestUtil;

public class getAPITest extends TestBase{
	
	TestBase testBase;
	String serviceurl;
	String apiurl;
	String url;
	RestClient restClient;
	CloseableHttpResponse closeableHttpResponse;
	
	
	@BeforeMethod
	public void setUP() {
		testBase = new TestBase();
		serviceurl = prop.getProperty("URL");
		apiurl = prop.getProperty("serviceURL");
		url = serviceurl + apiurl;
		
	}
	
	@Test	
	public void getAPI() throws ClientProtocolException, IOException {
		
		restClient = new RestClient();
		closeableHttpResponse= restClient.get(url);
		
		int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status code.....> "+statusCode);		
		Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200);		
		
		
		String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
		
		JSONObject responseJson = new JSONObject(responseString);
		System.out.println("Response JSON from API....>" + responseJson);
		
		
		String per_page = TestUtil.getValueByJPath(responseJson, "/per_page");		
		int per_page_value = Integer.parseInt(per_page);		
		System.out.println("per_page ....>"+ per_page_value);		
		Assert.assertEquals(per_page_value, 3);
		
		
		String id = TestUtil.getValueByJPath(responseJson, "/data[0]/id");		
		int id_value = Integer.parseInt(id);		
		System.out.println("id_value ....>"+ id_value);		
		Assert.assertEquals(id_value, 1);
		
		Header[] headerArray = closeableHttpResponse.getAllHeaders();
		
		HashMap<String, String> allHeaders = new HashMap<String, String>();
		
		for(Header header:headerArray) {
			allHeaders.put(header.getName(), header.getValue());
		}
		
		System.out.println("Hearder Array ...>" + allHeaders);
			
	}
	
	@Test
	public void getAPIwithHeaders() throws ClientProtocolException, IOException {
		restClient = new RestClient();
		HashMap<String, String> headermap = new HashMap<String, String>();
		headermap.put("Conten-Type", "application/json");
		closeableHttpResponse= restClient.get(url, headermap);
		
		int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status code.....> "+statusCode);		
		Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200);		
		
		
		String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
		
		JSONObject responseJson = new JSONObject(responseString);
		System.out.println("Response JSON from API....>" + responseJson);
		
		
		String per_page = TestUtil.getValueByJPath(responseJson, "/per_page");		
		int per_page_value = Integer.parseInt(per_page);		
		System.out.println("per_page ....>"+ per_page_value);		
		Assert.assertEquals(per_page_value, 3);
		
		
		String id = TestUtil.getValueByJPath(responseJson, "/data[0]/id");		
		int id_value = Integer.parseInt(id);		
		System.out.println("id_value ....>"+ id_value);		
		Assert.assertEquals(id_value, 1);
		
		Header[] headerArray = closeableHttpResponse.getAllHeaders();
		
		HashMap<String, String> allHeaders = new HashMap<String, String>();
		
		for(Header header:headerArray) {
			allHeaders.put(header.getName(), header.getValue());
		}
		
		System.out.println("Hearder Array ...>" + allHeaders);
		
	}
	
	
	
	

}
