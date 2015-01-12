/**
 * 
 */
package com.lbl.regprecise.rest.test;

import static com.lbl.regprecise.rest.test.RegPreciseWebResource.webResource;
import static org.junit.Assert.*;

import javax.ws.rs.core.MediaType;

import org.junit.Test;

import com.lbl.regprecise.xml.Site;

/**
 * @author Elena Novichkova
 *
 */

public class SitesResourceTest {

	@Test
	public void getSitesTestByRegulonId() {

	int regulonId = 6423;
		
		Site[] sites = webResource()
    		.path("sites")
    		.queryParam("regulonId", "" + regulonId)
    		.accept(MediaType.APPLICATION_XML)
    		.get(Site[].class);
		
		for(Site site: sites){
			assertNotNull("Site gene locus tag is null", site.getGeneLocusTag());
			assertNotNull("Site gene position is null", site.getPosition());
			assertNotNull("Site regulon id is null", site.getRegulonId());
		}
		

	}
	
	@Test
	public void getSitesTestByRegulogId() {

	int regulogId = 647;
		
		Site[] sites = webResource()
    		.path("sites")
    		.queryParam("regulogId", "" + regulogId)
    		.accept(MediaType.APPLICATION_XML)
    		.get(Site[].class);
		
		for(Site site: sites){
			assertNotNull("Site gene locus tag is null", site.getGeneLocusTag());
			assertNotNull("Site gene position is null", site.getPosition());
			assertNotNull("Site regulon id is null", site.getRegulonId());
		}

	}
	
	

}
