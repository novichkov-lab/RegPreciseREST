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
import com.lbl.regprecise.dto.RegulogStatDTO;
import com.lbl.regprecise.ent.Effector;
import com.lbl.regprecise.ent.Pathway;
import com.lbl.regprecise.ent.Riboswitch;
import com.lbl.regprecise.ent.TFFamily;
import com.lbl.regprecise.ent.Term;
import com.lbl.regprecise.xml.Regulog;
import com.lbl.regprecise.xml.RegulogCollection;

/**
 * @author Elena Novichkova
 *
 */
@Path("/regulogs")
public class RegulogsResource extends Resource {
	
	
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public List<Regulog> getRegulogs( @QueryParam("collectionType") String collectionType,
				@QueryParam("collectionId") Integer collectionId) 
	{
		
		assertParameter(collectionType);
		assertParameter(collectionId);
		assertParameter( RegulogCollection.isValidCollectionType(collectionType) );
		
		
		List<Regulog> regulogs = null;
		ConstrainedDataProvider dataProvider = new StatusConstrainedDataProvider(Term.TERM_REGULOG_STATE_PUBLIC);
		try{
			if(collectionType.equals(RegulogCollection.COLLECTION_TYPE_TAXGROUP))
			{
				List<RegulogStatDTO> regulogStats = dataProvider.getRegulogStatsDTO(collectionId);
				regulogs = toRegulogs(regulogStats);
			}
			else if(collectionType.equals(RegulogCollection.COLLECTION_TYPE_TF)){
				List<RegulogStatDTO> regulogStats = dataProvider.getRegulogStatsDTO(collectionId);
				regulogs = toRegulogs(regulogStats);
			}
			else if(collectionType.equals(RegulogCollection.COLLECTION_TYPE_TFFAM)){
				TFFamily tfFamily = dataProvider.getTFFamily(collectionId);
				List<RegulogStatDTO> regulogStats = dataProvider.getRegulogStatsDTO(tfFamily);
				regulogs = toRegulogs(regulogStats);
			}
			else if(collectionType.equals(RegulogCollection.COLLECTION_TYPE_RNAFAM)){
				Riboswitch riboswitch = dataProvider.getRiboswitch(collectionId);
				List<RegulogStatDTO> regulogStats = dataProvider.getRegulogStatsDTO(riboswitch);
				regulogs = toRegulogs(regulogStats);
			}			
			else if(collectionType.equals(RegulogCollection.COLLECTION_TYPE_EFFECTOR)){
				Effector effector = dataProvider.getEffector(collectionId);
				List<RegulogStatDTO> regulogStats = dataProvider.getRegulogStatsDTO(effector);
				regulogs = toRegulogs(regulogStats);
			}			
			else if(collectionType.equals(RegulogCollection.COLLECTION_TYPE_PATHWAY)){
				Pathway pathway = dataProvider.getPathway(collectionId);
				List<RegulogStatDTO> regulogStats = dataProvider.getRegulogStatsDTO(pathway);
				regulogs = toRegulogs(regulogStats);
			}	
			else{
				regulogs = new Vector<Regulog>();
			}
		}
		catch(Exception e)
		{
			serverError();
		}
		finally{
			dataProvider.close();
		}		
		return regulogs;
	}
	
	
	protected List<Regulog> toRegulogs(List<RegulogStatDTO> regulogStats)
	{
		List<Regulog> regulogs = new Vector<Regulog>();
		
		for(RegulogStatDTO regulogStat: regulogStats)
		{
			Regulog regulog = new Regulog();
		
			regulog.setRegulogId(regulogStat.getRegulogId());
			regulog.setRegulatorName(regulogStat.getRegulatorName());
			regulog.setTaxonName(regulogStat.getTaxonName());
			regulog.setEffector(regulogStat.getEffector());
			regulog.setPathway(regulogStat.getPathway());

			regulog.setRegulatorFamily(regulogStat.getRegulatorFamily());
			regulog.setRegulationType(regulogStat.getRegulationTypeTermId() == Term.TERM_REGULATION_TYPE_TF ? "TF" : "RNA");
			
			regulogs.add(regulog);
		}
		
		return regulogs;
	}
	
}
