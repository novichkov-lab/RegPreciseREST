package com.lbl.regprecise.rest;

import java.util.List;
import java.util.Vector;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.lbl.regprecise.dao.hibernate.ConstrainedDataProvider;
import com.lbl.regprecise.dao.hibernate.StatusConstrainedDataProvider;
import com.lbl.regprecise.ent.Genome;
import com.lbl.regprecise.ent.Regulog;
import com.lbl.regprecise.ent.Term;
import com.lbl.regprecise.xml.Regulon;

/**
 * @author Elena Novichkova
 *
 */
@Path("/regulons")
public class RegulonsResource extends Resource {
	
	
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public List<Regulon> getRegulons( @QueryParam("regulogId") Integer regulogId,
				@QueryParam("genomeId") Integer genomeId) 
	{	
		assertParameters(regulogId, genomeId);
		
		List<Regulon> regulons = null;
		ConstrainedDataProvider dataProvider = new StatusConstrainedDataProvider(Term.TERM_REGULOG_STATE_PUBLIC);
		try{
			if(regulogId != null)
			{
				Regulog regulog = dataProvider.getRegulog(regulogId);
				regulons = toRegulons(regulog.getRegulons());				
			}
			else if(genomeId != null){
				Genome genome = dataProvider.getGenome(genomeId);
				regulons = toRegulons(dataProvider.getRegulons(genome));
			}
		}
		catch(Exception e)
		{
			serverError();
		}
		finally{
			dataProvider.close();
		}		
		return regulons;
	}

	private List<Regulon> toRegulons(
			List<com.lbl.regprecise.ent.Regulon> dbRegulons) {
		
		List<Regulon> regulons = new Vector<Regulon>();
		for(com.lbl.regprecise.ent.Regulon dbRegulon: dbRegulons)
		{
			if(!dbRegulon.hasSites()) continue;
			Regulon regulon = RegulonResource.toRegulon(dbRegulon);
			regulons.add(regulon);
		}
		
		return regulons;
	}
	
}
