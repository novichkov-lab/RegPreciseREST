/**
 * 
 */
package com.lbl.regprecise.rest.test;

import static com.lbl.regprecise.rest.test.RegPreciseWebResource.webResource;
import static org.junit.Assert.*;

import javax.ws.rs.core.MediaType;

import org.junit.Test;

import com.lbl.regprecise.xml.Gene2Regulon;


/**
 * @author Elena Novichkova
 *
 */
public class SearchExtRegulonResourceTest {

	@Test
	public void getGene2RegulonsTestByTaxonomyId() {

	int taxonomyId = 882;
	String locusTags = "DVU0043,DVU0694,DVU3234,DVU3233,DVU0910,DVU3384,DVU0863";
		
	Gene2Regulon[] gene2regulons = webResource()
    		.path("searchExtRegulons")
    		.queryParam("taxonomyId", "" + taxonomyId)
    		.queryParam("locusTags", "" + locusTags)
    		.accept(MediaType.APPLICATION_XML)
    		.get(Gene2Regulon[].class);
	
	
	for(Gene2Regulon gene2Regulon: gene2regulons){
		assertNotNull("gene2Regulon function is null",gene2Regulon.getFunction());
		assertNotNull("gene2Regulon Locus tag is null",gene2Regulon.getLocusTag());
		assertNotNull("gene2Regulon gene id is null",gene2Regulon.getGeneId());
	}
		

	}
	

}
