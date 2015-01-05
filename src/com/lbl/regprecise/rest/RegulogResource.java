package com.lbl.regprecise.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.lbl.regprecise.dao.hibernate.ConstrainedDataProvider;
import com.lbl.regprecise.dao.hibernate.StatusConstrainedDataProvider;
import com.lbl.regprecise.ent.Term;
import com.lbl.regprecise.xml.Regulog;

/**
 * @author Elena Novichkova
 *
 */
@Path("/regulog")
public class RegulogResource extends Resource {
	
	
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Regulog getRegulog( @QueryParam("regulogId") Integer regulogId) 
	{
		
		assertParameter(regulogId);
		
		Regulog regulog = null;
		ConstrainedDataProvider dataProvider = new StatusConstrainedDataProvider(Term.TERM_REGULOG_STATE_PUBLIC);
		try{
			
			regulog =  toRegulog( dataProvider.getRegulog(regulogId) );
		}
		catch(Exception e)
		{
			serverError();
		}
		finally{
			dataProvider.close();
		}		
		return regulog;
	}

	public static Regulog toRegulog(com.lbl.regprecise.ent.Regulog dbRegulog) {
		Regulog regulog = new Regulog();
		
		regulog.setRegulogId(dbRegulog.getId());
		regulog.setRegulatorName(dbRegulog.getRegulatorName());
		regulog.setTaxonName(dbRegulog.getTaxonName());
		regulog.setEffector(dbRegulog.getEffectorNames());
		regulog.setPathway(dbRegulog.getPathwayNames());

		regulog.setRegulatorFamily(dbRegulog.getRegulatorFamily());
		regulog.setRegulationType(dbRegulog.getRegulationTypeTermId() == Term.TERM_REGULATION_TYPE_TF ? "TF" : "RNA");
		
		return regulog;
	}
}
