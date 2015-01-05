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
import com.lbl.regprecise.ent.Regulog;
import com.lbl.regprecise.ent.Regulon;
import com.lbl.regprecise.ent.Term;
import com.lbl.regprecise.xml.Regulator;

/**
 * @author Elena Novichkova
 *
 */
@Path("/regulators")
public class RegulatorsResource extends Resource {

	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public List<Regulator> getRegulators( @QueryParam("regulonId") Integer regulonId,
				@QueryParam("regulogId") Integer regulogId) 
	{	
		assertParameters(regulonId, regulogId);
		
		List<Regulator> regulators = null;
		ConstrainedDataProvider dataProvider = new StatusConstrainedDataProvider(Term.TERM_REGULOG_STATE_PUBLIC);
		try{
			if(regulonId != null)
			{
				Regulon regulon = dataProvider.getRegulon(regulonId);
				regulators = toRegulators(regulon.getRegulators());				
			}
			else if(regulogId != null)
			{
				Regulog regulog = dataProvider.getRegulog(regulogId);
				regulators = toRegulators(regulog);				
			}			
		}
		catch(Exception e)
		{
			serverError();
		}
		finally{
			dataProvider.close();
		}		
		return regulators;
	}

	private List<Regulator> toRegulators(Regulog regulog) {
		List<Regulator> regulators = new Vector<Regulator>();
		for(Regulon regulon: regulog.getRegulons())
		{
			regulators.addAll(toRegulators(regulon.getRegulators()));
		}
		
		return regulators;
	}

	private List<Regulator> toRegulators(
			List<com.lbl.regprecise.ent.Regulator> dbRegulators) {
		
		List<Regulator> regulators = new Vector<Regulator>();
		for(com.lbl.regprecise.ent.Regulator dbRegulator: dbRegulators)
		{
			Regulator regulator = toRegulator(dbRegulator);
			regulators.add(regulator);
		}
		
		return regulators;
	}

	private Regulator toRegulator(com.lbl.regprecise.ent.Regulator dbRegulator) {
		com.lbl.regprecise.ent.Regulon dbRegulon = dbRegulator.getRegulon();
		com.lbl.regprecise.ent.Regulog dbRegulog = dbRegulon.getRegulog();
		
		Regulator regulator = new Regulator();
		
		regulator.setName(dbRegulog.getRegulatorName());
		regulator.setRegulonId(dbRegulon.getId());
		
		regulator.setLocusTag(dbRegulator.getLocusTag());
		regulator.setVimssId(dbRegulator.getMoId());

		regulator.setRegulatorFamily(dbRegulog.getRegulatorFamily());
		
		return regulator;
	}
	
}
