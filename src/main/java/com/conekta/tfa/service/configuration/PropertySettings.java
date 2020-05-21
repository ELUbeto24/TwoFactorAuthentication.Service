package com.conekta.tfa.service.configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.springframework.context.annotation.Configuration;

import com.newrelic.api.agent.NewRelic;

/**
* <h1>Property Settings Class</h1>
* This class is in charge of extracting 
* the file tfa.properties that will 
* be used in the Cybersource class.
*
* @author  Edilberto López Ubaldo
* @version 1.0
* @since   2020-04-02
*/

@Configuration
public class PropertySettings {
	// Properties type variable is initialized for internal use of the methods of this class.
	private static Properties properties = new Properties();
	
	/** Read the properties found in the tfa.properties file.
	 * @return return Properties Response.
	 */
	public Properties readProperties() {
		
		try {
			
			FileInputStream fis = new FileInputStream("tfa.properties");
			properties.load(fis);
		    fis.close();
		        
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			NewRelic.noticeError("FileNotFoundException in readProperties: " + e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			NewRelic.noticeError("IOException in readProperties: " + e.getMessage());
		}
       
		return properties;
	}
	
	/** Read specific properties found in tfa.properties file.
	 * @param String propertyKey.
	 * @return return Properties Response.
	 */
	public String getProperty(String propertyKey) {
		String responseKey = null;
		
		try {
			FileInputStream fis = new FileInputStream("tfa.properties");
			properties.load(fis);
			
			responseKey = properties.getProperty(propertyKey);
			
		    fis.close();
		        
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			NewRelic.noticeError("FileNotFoundException in getProperty: " + e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			NewRelic.noticeError("IOException in getProperty: " + e.getMessage());
		}
		
		return responseKey;
	}
}
