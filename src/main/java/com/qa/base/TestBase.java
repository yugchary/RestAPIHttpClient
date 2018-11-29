package com.qa.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class TestBase {
	
	public int RESPONSE_STATUS_CODE_200 = 200;
	public int RESPONSE_STATUS_CODE_500 = 500;
	public int RESPONSE_STATUS_CODE_400 = 400;
	public int RESPONSE_STATUS_CODE_401 = 401;
	public int RESPONSE_STATUS_CODE_201 = 201;
	public Properties prop = new Properties();
	
	public TestBase() {
		
		

		FileInputStream fileip;
		try {
			fileip = new FileInputStream(
					"C:\\Users\\akkyu01\\eclipse-workspace\\JavaTraining\\RestAPITest\\src\\main\\java\\com\\qa\\config\\config.properties");
			prop.load(fileip);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
