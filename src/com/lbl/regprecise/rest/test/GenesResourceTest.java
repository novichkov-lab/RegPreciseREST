/**
 * 
 */
package com.lbl.regprecise.rest.test;

import static com.lbl.regprecise.rest.test.RegPreciseWebResource.webResource;
import static org.junit.Assert.*;

import javax.ws.rs.core.MediaType;

import org.junit.Test;

import com.lbl.regprecise.xml.Gene;

/**
 * @author Elena Novichkova
 *
 */
public class GenesResourceTest {

	@Test
	public void getGenesTestByRegulogId() {
		
		int regulogId = 4755;
		
		Gene[] genes = webResource()
	    		.path("genes")
	    		.queryParam("regulogId", "" + regulogId)//???
	    		.accept(MediaType.APPLICATION_XML)
	    		.get(Gene[].class);
		
		assertNotNull("Can not get genes, got null", genes);
		assertTrue("Can not get genes", genes.length > 1);
		
		for(Gene gene: genes){
			assertNotNull("gene function is null", gene.getFunction());
			assertNotNull("gene locus tag is null", gene.getLocusTag());
			assertNotNull("gene name is null", gene.getName());
			assertNotNull("gene regulonId is null", gene.getRegulonId());
			assertNotNull("gene vimssid is null", gene.getVimssId());
		}		
	}
	
	@Test
	public void getGenesTestByRegulonId() {
		
		int regulonId = 47178;
		
		Gene[] genes = webResource()
	    		.path("genes")
	    		.queryParam("regulonId", "" + regulonId)//??
	    		.accept(MediaType.APPLICATION_XML)
	    		.get(Gene[].class);
			
		assertNotNull("Can not get genes, got null", genes);
		assertTrue("Can not get genes", genes.length > 1);
		
		for(Gene gene: genes){
			assertNotNull("gene function is null", gene.getFunction());
			assertNotNull("gene locus tag is null", gene.getLocusTag());
			assertNotNull("gene name is null", gene.getName());
			assertNotNull("gene regulon id is null", gene.getRegulonId());
			assertNotNull("gene vimssid is null", gene.getVimssId());
		}
		
	}

}
