package com.lbl.regprecise.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.lbl.regprecise.dao.hibernate.ConstrainedDataProvider;
import com.lbl.regprecise.dao.hibernate.StatusConstrainedDataProvider;
import com.lbl.regprecise.ent.Term;
import com.lbl.regprecise.xml.Regulon;

/**
 * @author Elena Novichkova
 *
 */
@Path("/regulon")
public class RegulonResource extends Resource{

	
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Regulon getRegulon( @QueryParam("regulonId") Integer regulonId) 
	{	
		assertParameter(regulonId);
		
		Regulon regulon = null;
		ConstrainedDataProvider dataProvider = new StatusConstrainedDataProvider(Term.TERM_REGULOG_STATE_PUBLIC);
		try{
			regulon = toRegulon(dataProvider.getRegulon(regulonId) );
		}
		catch(Exception e)
		{
			serverError();
		}
		finally{
			dataProvider.close();
		}		
		return regulon;
	}
	
	
	public static Regulon toRegulon(com.lbl.regprecise.ent.Regulon dbRegulon)
	{
		com.lbl.regprecise.ent.Regulog dbRegulog = dbRegulon.getRegulog();
		com.lbl.regprecise.ent.Genome dbGenome = dbRegulon.getGenome();
		
		Regulon regulon = new Regulon();
		
		regulon.setRegulonId(dbRegulon.getId());
		regulon.setRegulogId(dbRegulog.getId());
		regulon.setGenomeId(dbGenome.getId());
		regulon.setGenomeName(dbGenome.getName());
		
		regulon.setRegulatorName(dbRegulog.getRegulatorName());
		regulon.setEffector(dbRegulog.getEffectorNames());
		regulon.setPathway(dbRegulog.getPathwayNames());

		regulon.setRegulatorFamily(dbRegulog.getRegulatorFamily());
		regulon.setRegulationType(dbRegulog.getRegulationTypeTermId() == Term.TERM_REGULATION_TYPE_TF ? "TF" : "RNA");
		
		return regulon;
	}
}
