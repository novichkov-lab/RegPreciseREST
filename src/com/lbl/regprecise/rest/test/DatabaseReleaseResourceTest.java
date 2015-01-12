/**
 * 
 */
package com.lbl.regprecise.rest.test;

import static com.lbl.regprecise.rest.test.RegPreciseWebResource.webResource;

import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.ws.rs.core.MediaType;

import org.junit.Test;

import com.lbl.regprecise.xml.DatabaseRelease;


/**
 * @author Elena Novichkova
 *
 */
public class DatabaseReleaseResourceTest {

	@Test
	public void getDatabaseReleaseTest() {
		
		
		
		DatabaseRelease databaseRelease = webResource()
	    		.path("release")
	    		.accept(MediaType.APPLICATION_XML)
	    		.get(DatabaseRelease.class);
			
		assertTrue("Database Release data is not valid",isValidDate(databaseRelease.getReleaseDate()));
	}
	
	  public boolean isValidDate(String inDate) {
		    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		    dateFormat.setLenient(false);
		    try {
		      dateFormat.parse(inDate.trim());
		    } catch (ParseException pe) {
		      return false;
		    }
		    return true;
		  }

}
