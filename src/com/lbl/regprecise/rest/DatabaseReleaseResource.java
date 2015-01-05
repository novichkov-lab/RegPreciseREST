package com.lbl.regprecise.rest;

import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.lbl.regprecise.dao.hibernate.ConstrainedDataProvider;
import com.lbl.regprecise.dao.hibernate.StatusConstrainedDataProvider;
import com.lbl.regprecise.ent.Term;
import com.lbl.regprecise.xml.DatabaseRelease;

/**
 * @author Elena Novichkova
 *
 */
@Path("/release")
public class DatabaseReleaseResource extends Resource{
	
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public DatabaseRelease getDatabaseRelease() 
	{	
		
		DatabaseRelease databaseRelease = null;
		ConstrainedDataProvider dataProvider = new StatusConstrainedDataProvider(Term.TERM_REGULOG_STATE_PUBLIC);
		try{
			
			databaseRelease = toCurrentRelease( dataProvider.getDatabaseReleases() );
		}
		catch(Exception e)
		{
			e.printStackTrace();
			serverError();
		}
		finally{
			dataProvider.close();
		}		
		return databaseRelease;
	}

	private DatabaseRelease toCurrentRelease(
			List<com.lbl.regprecise.ent.DatabaseRelease> databaseReleases) {
		
		
		for(com.lbl.regprecise.ent.DatabaseRelease dbRelease: databaseReleases)
		{
			if(dbRelease.getIsCurrent() == true) 
			{
				DatabaseRelease release = new DatabaseRelease();
				release.setMajorVersion(dbRelease.getMajorVersion());
				release.setMionrVersion(dbRelease.getMionrVersion());
				
				Date date = dbRelease.getReleaseDate();
				release.setReleaseDate( date.toString() );
				
				return release;
			}
		}
		
		return null;
	}
	
	
	
}
