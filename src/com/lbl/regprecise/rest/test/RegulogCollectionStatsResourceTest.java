/**
 * 
 */
package com.lbl.regprecise.rest.test;

import static com.lbl.regprecise.rest.test.RegPreciseWebResource.webResource;
import static org.junit.Assert.*;

import javax.ws.rs.core.MediaType;

import org.junit.Test;

import com.lbl.regprecise.xml.RegulogCollectionStat;

/**
 * @author Elena Novichkova
 *
 */
public class RegulogCollectionStatsResourceTest {

	@Test
	public void test() {

	String[] collectionTypes = {"taxGroup","tf","tfFam", "rnaFam", "pathway", "effector"};
	
	for(String collectionType: collectionTypes){
			
		RegulogCollectionStat[] regulogCollectionStats = webResource()
	    		.path("regulogCollectionStats")
	    		.queryParam("collectionType", collectionType)
	    		.accept(MediaType.APPLICATION_XML)
	    		.get(RegulogCollectionStat[].class);
			
			for(RegulogCollectionStat regulogCollectionStat: regulogCollectionStats){
				assertNotNull("CollectionId is null", regulogCollectionStat.getCollectionId());
				assertNotNull("Collection type is null", regulogCollectionStat.getCollectionType());
				assertNotNull("Collection name is null", regulogCollectionStat.getName());
			}
		}
	}

}
