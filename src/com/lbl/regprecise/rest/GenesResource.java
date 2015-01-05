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
import com.lbl.regprecise.xml.Gene;

/**
 * @author Elena Novichkova
 *
 */
@Path("/genes")
public class GenesResource extends Resource{

	
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public List<Gene> getGenes( @QueryParam("regulonId") Integer regulonId,
				@QueryParam("regulogId") Integer regulogId) 
	{	
		assertParameters(regulonId, regulogId);
		
		List<Gene> genes = null;
		ConstrainedDataProvider dataProvider = new StatusConstrainedDataProvider(Term.TERM_REGULOG_STATE_PUBLIC);
		try{
			if(regulonId != null)
			{
				Regulon regulon = dataProvider.getRegulon(regulonId);
				genes = toGenes(regulon);				
			}
			else if(regulogId != null)
			{
				Regulog regulog = dataProvider.getRegulog(regulogId);
				genes = toGenes(regulog);				
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
		return genes;
	}

	private List<Gene> toGenes(Regulog regulog) {
		List<Gene> genes = new Vector<Gene>();
		for(Regulon regulon: regulog.getRegulons())
		{
			genes.addAll(toGenes(regulon));
		}
		
		return genes;
	}

	private List<Gene> toGenes(Regulon dbRegulon) {
		
		List<Gene> genes = new Vector<Gene>();
		for(com.lbl.regprecise.ent.Operon dbOperon: dbRegulon.getOperons())
		{
			if(!dbOperon.hasSites()) continue;
			for(com.lbl.regprecise.ent.Gene dbGene: dbOperon.getGenes())
			{
				Gene gene = toGene(dbGene);
				genes.add(gene);
			}
		}
		
		return genes;
	}

	private Gene toGene(com.lbl.regprecise.ent.Gene dbGene) {
		com.lbl.regprecise.ent.Regulon dbRegulon = dbGene.getRegulon();
		
		Gene gene = new Gene();
		
		gene.setRegulonId(dbRegulon.getId());
		
		gene.setLocusTag(dbGene.getLocusTag());
		gene.setVimssId(dbGene.getMoId());

		gene.setName(dbGene.getName());
		gene.setFunction(dbGene.getFunction());
						
		return gene;
	}
	
}
