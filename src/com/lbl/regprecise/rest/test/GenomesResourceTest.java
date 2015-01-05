package com.lbl.regprecise.rest.test;

import static com.lbl.regprecise.rest.test.RegPreciseWebResource.webResource;
import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.MediaType;

import org.junit.Test;

import com.lbl.regprecise.xml.Genome;

/**
 * @author Elena Novichkova
 *
 */
public class GenomesResourceTest {

	@Test
	public void testGetGenomes() {
		
		int expectedGenomesCount = 390;
		
		Genome[] genomes = webResource()
    		.path("genomes")
    		.accept(MediaType.APPLICATION_XML)
    		.get(Genome[].class);
		
		assertEquals("Number of genomes", expectedGenomesCount, genomes.length );
	}
	
}
