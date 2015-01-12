/**
 * 
 */
package com.lbl.regprecise.rest.test;

import static com.lbl.regprecise.rest.test.RegPreciseWebResource.webResource;
import static org.junit.Assert.*;

import javax.ws.rs.core.MediaType;

import org.junit.Test;

import com.lbl.regprecise.xml.RegulogCollection;

/**
 * @author Elena Novichkova
 *
 */
public class RegulogCollectionsResourceTest {

	@Test
	public void getRegulogCollectionsTest() {

	String[] collectionTypes = {"taxGroup","tf","tfFam", "rnaFam", "pathway", "effector"};
	
	for(String collectionType: collectionTypes){
		
	RegulogCollection[] regulogCollections = webResource()
    		.path("regulogCollections")
    		.queryParam("collectionType", collectionType)
    		.accept(MediaType.APPLICATION_XML)
    		.get(RegulogCollection[].class);
		
		
		for(RegulogCollection regulogCollection: regulogCollections){
			assertNotNull("CollectionId is null", regulogCollection.getCollectionId());
			assertNotNull("Collection type is null", regulogCollection.getCollectionType());
			assertNotNull("Collection name is null", regulogCollection.getName());
		}
	}
	}

}
