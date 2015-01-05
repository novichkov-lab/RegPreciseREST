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
import com.lbl.regprecise.dto.GenomeStatDTO;
import com.lbl.regprecise.ent.Regulog;
import com.lbl.regprecise.ent.Term;
import com.lbl.regprecise.xml.Genome;

/**
 * @author Elena Novichkova
 *
 */
@Path("/genomes")
public class GenomesResource extends Resource{

	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public List<Genome> getGenomes( @QueryParam("regulogId") Integer regulogId) 
	{			
		List<Genome> genomes = null;
		ConstrainedDataProvider dataProvider = new StatusConstrainedDataProvider(Term.TERM_REGULOG_STATE_PUBLIC);
		try{
			if(regulogId != null)
			{
				Regulog regulog = dataProvider.getRegulog(regulogId);
				genomes = toGenomes(regulog);				
			}	
			else {
				List<GenomeStatDTO> genomeStats = dataProvider.getGenomeStatsDTO(false);
				genomes = toGenomes(genomeStats);
			}
		}
		catch(Exception e)
		{
			serverError();
		}
		finally{
			dataProvider.close();
		}		
		return genomes;
	}

	private List<Genome> toGenomes(List<GenomeStatDTO> genomeStats) {
		List<Genome> genomes = new Vector<Genome>();
		
		for(GenomeStatDTO genomeStat: genomeStats)
		{			
			Genome genome = new Genome();
			genome.setGenomeId(genomeStat.getGenomeId());
			genome.setName(genomeStat.getGenomeName());
			genome.setTaxonomyId(genomeStat.getTaxonomyId());
			genomes.add(genome);
		}
		
		return genomes;
	}

	private List<Genome> toGenomes(Regulog regulog) {
		List<Genome> genomes = new Vector<Genome>();
		
		for(com.lbl.regprecise.ent.Regulon regulon: regulog.getRegulons())
		{
			com.lbl.regprecise.ent.Genome dbGenome = regulon.getGenome();
			
			Genome genome = new Genome();
			genome.setGenomeId(dbGenome.getId());
			genome.setName(dbGenome.getName());
			genome.setTaxonomyId(dbGenome.getMoId());
			genomes.add(genome);
		}
		
		return genomes;
	}	
	
}
