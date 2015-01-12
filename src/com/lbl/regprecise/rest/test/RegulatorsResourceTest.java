/**
 * 
 */
package com.lbl.regprecise.rest.test;

import static com.lbl.regprecise.rest.test.RegPreciseWebResource.webResource;
import static org.junit.Assert.*;

import javax.ws.rs.core.MediaType;

import org.junit.Test;

import com.lbl.regprecise.xml.Regulator;

/**
 * @author Elena Novichkova
 *
 */

public class RegulatorsResourceTest {

	@Test
	public void getRegulatorsTestByRegulogId() {
		
	int regulogId = 4755;
		
		Regulator[] regulators = webResource()
    		.path("regulators")
    		.queryParam("regulogId","" + regulogId)
    		.accept(MediaType.APPLICATION_XML)
    		.get(Regulator[].class);
		
		assertNotNull("Can not get genes, got null", regulators);
		assertTrue("Can not get genes", regulators.length > 1);
		
		for(Regulator regulator: regulators){
			assertNotNull("regulator locus tag is null", regulator.getLocusTag());
			assertNotNull("regulator name is null", regulator.getName());
			assertNotNull("regulator family is null", regulator.getRegulatorFamily());
			assertNotNull("regulator regulonId is null", regulator.getRegulonId());
			assertNotNull("regulator vimssid is null", regulator.getVimssId());
		}
	}
	
	@Test
	public void getRegulatorsTestByRegulonId() {
		

	int regulonId = 47178;
		
		Regulator[] regulators = webResource()
    		.path("regulators")
    		.queryParam("regulonId","" + regulonId)
    		.accept(MediaType.APPLICATION_XML)
    		.get(Regulator[].class);
		
		assertNotNull("Can not get genes, got null", regulators);
		assertTrue("Can not get genes", regulators.length > 1);
		
		for(Regulator regulator: regulators){
			assertNotNull("regulator locus tag is null", regulator.getLocusTag());
			assertNotNull("regulator name is null", regulator.getName());
			assertNotNull("regulator family is null", regulator.getRegulatorFamily());
			assertNotNull("regulator regulonId is null", regulator.getRegulonId());
			assertNotNull("regulator vimssid is null", regulator.getVimssId());
		}
	}

}
