/**
 * 
 */
package com.lbl.regprecise.rest.test;

import static com.lbl.regprecise.rest.test.RegPreciseWebResource.webResource;
import static org.junit.Assert.*;

import javax.ws.rs.core.MediaType;

import org.junit.Test;

import com.lbl.regprecise.xml.RegulonRef;

/**
 * @author Elena Novichkova
 *
 */
public class SearchRegulonResourceTest {

	@Test
	public void getRegulonRefsTest() {

	String[] objTypes = {"regulator", "gene"};
	String text = "SO_2706";
	
	for(String objType: objTypes){
		
		RegulonRef[] regulonRefs = webResource()
	    		.path("searchRegulons")
	    		.queryParam("objType", objType)
	    		.queryParam("text", text)
	    		.accept(MediaType.APPLICATION_XML)
	    		.get(RegulonRef[].class);
			
		for(RegulonRef regulonRef: regulonRefs){
			assertNotNull("RegulonRef found object name is null", regulonRef.getFoundObjName());
			assertNotNull("RegulonRef found object type is null", regulonRef.getFoundObjType());
			assertNotNull("RegulonRef genome name is null", regulonRef.getGenomeName());
			assertNotNull("RegulonRef regulator name is null", regulonRef.getRegulatorName());
		}
		}
	}
	

	
	

}
