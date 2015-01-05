package com.lbl.regprecise.rest;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.lbl.regprecise.dao.hibernate.ConstrainedDataProvider;
import com.lbl.regprecise.dao.hibernate.StatusConstrainedDataProvider;
import com.lbl.regprecise.dto.GeneRegulonDTO;
import com.lbl.regprecise.ent.Term;
import com.lbl.regprecise.xml.Gene2Regulon;

/**
 * @author Elena Novichkova
 *
 */
@Path("/searchExtRegulons")
public class SearchExtRegulonResource extends Resource {

	private static final String LOCUS_TAGS_SEPARATOR = ",";

	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public List<Gene2Regulon> getGene2Regulons( @QueryParam("taxonomyId") Integer taxonomyId,
				@QueryParam("locusTags") String locusTags) 
	{	
		assertParameters(taxonomyId);
		assertParameters(locusTags);
		
		List<Gene2Regulon> gene2Regulons = null;
		ConstrainedDataProvider dataProvider = new StatusConstrainedDataProvider(Term.TERM_REGULOG_STATE_PUBLIC);
		try{
			
			List<GeneRegulonDTO> rows = dataProvider.getGeneRegulonDTO(taxonomyId, toList(locusTags, LOCUS_TAGS_SEPARATOR));
			gene2Regulons = toGene2Regulons(rows);
		}
		catch(Exception e)
		{
			serverError();
		}
		finally{
			dataProvider.close();
		}		
		return gene2Regulons;
	}

	private List<String> toList(String locusTags, String separator) {
		
		String[] vals = locusTags.split(separator);
		return Arrays.asList(vals);
	}
	
	private List<Gene2Regulon> toGene2Regulons(List<GeneRegulonDTO> rows) {
		List<Gene2Regulon> gene2Regulons = new Vector<Gene2Regulon>();
		for(GeneRegulonDTO row: rows)
		{
			Gene2Regulon gr = new Gene2Regulon();
			gr.setGeneId(row.getGeneId());
			gr.setLocusTag(row.getLocusTag());
			gr.setRegulonId(row.getRegulonId());
			gr.setRegulatorName(row.getRegulatorName());
			gr.setTaxonName(row.getTaxonName());
			gr.setPhylum(row.getPhylum());
			gr.setFunction(row.getFunction());
			gr.setEffector(row.getEffector());
			gr.setPathway(row.getPathway());
/*			gr.setRegulatorFamily(row.getRegulatorFamily());*/
			gene2Regulons.add(gr);			
		}
		return gene2Regulons;
	}			
}
