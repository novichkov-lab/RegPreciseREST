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
import com.lbl.regprecise.ent.SearchIndex;
import com.lbl.regprecise.ent.Term;
import com.lbl.regprecise.xml.RegulonRef;

/**
 * @author Elena Novichkova
 *
 */
@Path("/searchRegulons")
public class SearchRegulonResource extends Resource {
	
	private static String TERM_GENE = "gene";
	private static String TERM_REGULATOR = "regulator";
	
	
	
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public List<RegulonRef> getRegulonRefs( @QueryParam("objType") String objType,
				@QueryParam("text") String text, @QueryParam("vimssId") Integer vimssId) 
	{	
		assertParameters(objType);
		assertParameters(text, vimssId);
		assertParameter(objType.equals(TERM_GENE) || objType.equals(TERM_REGULATOR));
		
		List<RegulonRef> regulonRefs = null;
		ConstrainedDataProvider dataProvider = new StatusConstrainedDataProvider(Term.TERM_REGULOG_STATE_PUBLIC);
		try{
			List<SearchIndex> searchIndeces = null;
			
			if(text != null)
			{
				searchIndeces = dataProvider.getSearchIndeces(text);
			}
			else if(vimssId != null){
		
				if(objType.equals(TERM_GENE))
				{
					searchIndeces = dataProvider.getSearchIndecesByGeneVimssId(vimssId);
				}				
				else if(objType.equals(TERM_REGULATOR))
				{
					searchIndeces = dataProvider.getSearchIndecesByRegulatorVimssId(vimssId);
				}
			}
				
			regulonRefs = toRegulonRefs(searchIndeces, objType);
		}
		catch(Exception e)
		{
			serverError();
		}
		finally{
			dataProvider.close();
		}		
		return regulonRefs;
	}

	private List<RegulonRef> toRegulonRefs(List<SearchIndex> searchIndeces,
			String objType) {
		List<RegulonRef> regulonRefs = new Vector<RegulonRef>();
		
		for(SearchIndex searchIndex: searchIndeces)
		{
			RegulonRef regulonRef = new RegulonRef();
			
			String objTypeName = "";
			if(searchIndex.getObjType().intValue() == Term.TERM_GENE && objType.equals(TERM_GENE))
			{
				objTypeName = TERM_GENE;
			}
			else if( (searchIndex.getObjType().intValue() == Term.TERM_REGULATOR || searchIndex.getObjType().intValue() == Term.TERM_REGULON)
					&& objType.equals(TERM_REGULATOR))
			{
				objTypeName = TERM_REGULATOR;
			}
			else{
				continue;
			}
			
			regulonRef.setRegulonId(searchIndex.getRegulonId());
			regulonRef.setGenomeName(searchIndex.getGenomeName());
			regulonRef.setRegulatorName(searchIndex.getRegulatorName());
			
			regulonRef.setFoundObjType(objTypeName);
			regulonRef.setFoundObjName(searchIndex.getObjName());			
			
			regulonRefs.add(regulonRef);
		}
		
		return regulonRefs;
	}

	
}
