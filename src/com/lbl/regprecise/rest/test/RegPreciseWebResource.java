/**
 * 
 */
package com.lbl.regprecise.rest.test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

/**
 * @author Pavel Novichkov
 *
 */
public class RegPreciseWebResource {
    private static WebResource webResource = null;	
	
	
	public static WebResource webResource() {
		
		if(webResource == null){
	        ClientConfig clientConfig = new DefaultClientConfig();
	        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
	        Client client = Client.create(clientConfig);
	        webResource = client.resource("http://regprecise.lbl.gov/Services/rest/");
		}
		return webResource;
	}
}
