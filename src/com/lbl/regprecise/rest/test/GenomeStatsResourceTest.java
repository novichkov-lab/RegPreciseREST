/**
 * 
 */
package com.lbl.regprecise.rest.test;

import static com.lbl.regprecise.rest.test.RegPreciseWebResource.webResource;
import static org.junit.Assert.*;

import javax.ws.rs.core.MediaType;

import org.junit.Test;
import com.lbl.regprecise.xml.GenomeStat;

/**
 * @author Elena Novichkova
 *
 */
public class GenomeStatsResourceTest {

	@Test
	public void test() {

		
		GenomeStat[] genomeStat = webResource()
    		.path("genomeStats")
    		.accept(MediaType.APPLICATION_XML)
    		.get(GenomeStat[].class);
		
		assertNotNull("Genome Statistics is null", genomeStat);
		assertTrue("Genome statistics is not null but equals zero", genomeStat.length > 0);
	}

}
