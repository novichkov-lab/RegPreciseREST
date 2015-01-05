package com.lbl.regprecise.rest;

import java.util.List;
import java.util.Vector;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.lbl.regprecise.dao.hibernate.ConstrainedDataProvider;
import com.lbl.regprecise.dao.hibernate.StatusConstrainedDataProvider;
import com.lbl.regprecise.dto.GenomeStatDTO;
import com.lbl.regprecise.ent.Term;
import com.lbl.regprecise.xml.GenomeStat;

/**
 * @author Elena Novichkova
 *
 */
@Path("/genomeStats")
public class GenomeStatsResource extends Resource{

	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public List<GenomeStat> getGenomeStats() 
	{	
		
		List<GenomeStat> genomeStats = null;
		ConstrainedDataProvider dataProvider = new StatusConstrainedDataProvider(Term.TERM_REGULOG_STATE_PUBLIC);
		try{
			genomeStats = toGenomeStats( dataProvider.getGenomeStatsDTO(false) );
		}
		catch(Exception e)
		{
			e.printStackTrace();
			serverError();
		}
		finally{
			dataProvider.close();
		}		
		return genomeStats;
	}

	private List<GenomeStat> toGenomeStats(List<GenomeStatDTO> dbGenomeStats) {
		List<GenomeStat> genomeStats = new Vector<GenomeStat>();
		
		for(GenomeStatDTO dbGenomeStat: dbGenomeStats)
		{			
			GenomeStat genomeStat = new GenomeStat();
			genomeStat.setGenomeId(dbGenomeStat.getGenomeId());
			genomeStat.setName(dbGenomeStat.getGenomeName());
			genomeStat.setTaxonomyId(dbGenomeStat.getTaxonomyId());
			
			genomeStat.setTfRegulonCount(dbGenomeStat.getTfRegulonCount());
			genomeStat.setRnaRegulonCount(dbGenomeStat.getRnaRegulonCount());
			
			genomeStat.setTfSiteCount(dbGenomeStat.getTfSiteCount());
			genomeStat.setRnaSiteCount(dbGenomeStat.getRnaSiteCount());
			
			genomeStats.add(genomeStat);
		}
		
		return genomeStats;
	}
	
}
