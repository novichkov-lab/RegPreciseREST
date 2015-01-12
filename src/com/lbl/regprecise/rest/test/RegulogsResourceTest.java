/**
 * 
 */
package com.lbl.regprecise.rest.test;

import static com.lbl.regprecise.rest.test.RegPreciseWebResource.webResource;
import static org.junit.Assert.*;

import javax.ws.rs.core.MediaType;

import org.junit.Test;

import com.lbl.regprecise.xml.Regulog;

/**
 * @author Elena Novichkova
 *
 */
public class RegulogsResourceTest {

	@Test
	public void getRegulogsTestByCollectionType() {
	int collectionId = 1;
	String[] collectionTypes = {"taxGroup","tf","tfFam", "rnaFam", "pathway", "effector"};
	
	for(String collectionType: collectionTypes){
		
		Regulog[] regulogs = webResource()
	    		.path("regulogs")
	    		.queryParam("collectionType", collectionType)
	    		.queryParam("collectionId","" + collectionId)
	    		.accept(MediaType.APPLICATION_XML)
	    		.get(Regulog[].class);
			
		for(Regulog regulog: regulogs){
			
			assertNotNull("Regulog type is null", regulog.getRegulationType());
			assertNotNull("Regulog family is null", regulog.getRegulatorFamily());
		}
		
		}
	
	}

}
