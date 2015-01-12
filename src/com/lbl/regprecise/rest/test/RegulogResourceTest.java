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
public class RegulogResourceTest {

	@Test
	public void test() {
	int expectedRegulogId = 34;
	int regulogId = 34;
		
	Regulog regulog = webResource()
    		.path("regulog")
    		.queryParam("regulogId", "" + regulogId)
    		.accept(MediaType.APPLICATION_XML)
    		.get(Regulog.class);
		
		assertEquals("Regulog ID", expectedRegulogId, "" + regulog.getRegulogId() );
	}

}
