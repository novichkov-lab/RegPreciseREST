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
import com.lbl.regprecise.xml.Site;

/**
 * @author Elena Novichkova
 *
 */
@Path("/sites")
public class SitesResource extends Resource{
	
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public List<Site> getSites( @QueryParam("regulonId") Integer regulonId,
				@QueryParam("regulogId") Integer regulogId) 
	{	
		assertParameters(regulonId, regulogId);
		
		List<Site> sites = null;
		ConstrainedDataProvider dataProvider = new StatusConstrainedDataProvider(Term.TERM_REGULOG_STATE_PUBLIC);
		try{
			if(regulonId != null)
			{
				Regulon regulon = dataProvider.getRegulon(regulonId);
				sites = toSites(regulon);				
			}
			else if(regulogId != null)
			{
				Regulog regulog = dataProvider.getRegulog(regulogId);
				sites = toSites(regulog);				
			}			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			serverError();
		}
		finally{
			dataProvider.close();
		}		
		return sites;
	}

	private List<Site> toSites(Regulog regulog) {
		List<Site> sites = new Vector<Site>();
		for(Regulon regulon: regulog.getRegulons())
		{
			sites.addAll(toSites(regulon));
		}
		
		return sites;
	}

	private List<Site> toSites(Regulon dbRegulon) {
		
		List<Site> sites = new Vector<Site>();
		for(com.lbl.regprecise.ent.Operon dbOperon: dbRegulon.getOperons())
		{
			for(com.lbl.regprecise.ent.Site dbSite: dbOperon.getSites())
			{
				Site site = toSite(dbSite);
				sites.add(site);
			}
		}
		
		return sites;
	}

	private Site toSite(com.lbl.regprecise.ent.Site dbSite) {
		com.lbl.regprecise.ent.Regulon dbRegulon = dbSite.getRegulon();
		
		Site site = new Site();
		
		site.setRegulonId(dbRegulon.getId());
		
		site.setGeneLocusTag(dbSite.getRelativeGene().getLocusTag());
		site.setGeneVIMSSId(dbSite.getRelativeGene().getMoId());

		site.setSequence(dbSite.getSequence());
		site.setPosition(dbSite.getRelativePosition());
		site.setScore(dbSite.getScore());
						
		return site;
	}	
}
