package com.lbl.regprecise.rest.test;

import static com.lbl.regprecise.rest.test.RegPreciseWebResource.webResource;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
		
		
		Genome[] genomes = webResource()
    		.path("genomes")
    		.accept(MediaType.APPLICATION_XML)
    		.get(Genome[].class);
		
		assertNotNull("There is null in place of genomes", genomes);
		assertTrue("There is no any genome found", genomes.length > 0);
		
		for(Genome genome: genomes){
			assertNotNull(genome.getTaxonomyId());
		}
		
	}
	
}
