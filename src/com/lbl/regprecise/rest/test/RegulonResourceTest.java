package com.lbl.regprecise.rest.test;

import static com.lbl.regprecise.rest.test.RegPreciseWebResource.webResource;
import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.MediaType;

import org.junit.Test;

import com.lbl.regprecise.xml.Regulon;

/**
 * @author Elena Novichkova
 *
 */
public class RegulonResourceTest {

	@Test
	public void testGetRegulon() {
		
		int regulonId = 28046;
		String expectedRegulatorName = "T-box(Tyr)"; 
		int expectedRegulogId = 2952;
		
        Regulon regulon = webResource()
        	.path("regulon")
        	.queryParam("regulonId", "" + regulonId)
        	.accept(MediaType.APPLICATION_XML)
        	.get(Regulon.class);		
		
		assertEquals("Check the name of regulon " + regulonId, expectedRegulatorName , regulon.getRegulatorName());		
		assertEquals("Check the regulogId of regulon " + regulonId, expectedRegulogId , regulon.getRegulogId().intValue());		
	}	
}
