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
import com.lbl.regprecise.dto.CollectionStatDTO;
import com.lbl.regprecise.dto.EffectorStatDTO;
import com.lbl.regprecise.dto.PathwayStatDTO;
import com.lbl.regprecise.dto.RiboswitchStatDTO;
import com.lbl.regprecise.dto.TFFamiliyStatDTO;
import com.lbl.regprecise.ent.Term;
import com.lbl.regprecise.xml.RegulogCollection;
import com.lbl.regprecise.xml.RegulogCollectionStat;

/**
 * @author Elena Novichkova
 *
 */
@Path("/regulogCollectionStats")
public class RegulogCollectionStatsResource extends Resource{

	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public List<RegulogCollectionStat> getRegulogCollectionStats( @QueryParam("collectionType") String collectionType)
	{
		
		assertParameter(collectionType);
		assertParameter( RegulogCollection.isValidCollectionType(collectionType) );
		
		
		List<RegulogCollectionStat> regulogCollectionStats = null;
		ConstrainedDataProvider dataProvider = new StatusConstrainedDataProvider(Term.TERM_REGULOG_STATE_PUBLIC);
		try{
			if(collectionType.equals(RegulogCollection.COLLECTION_TYPE_TAXGROUP))
			{
				regulogCollectionStats = toRegulogCollectionStats(collectionType, 
						dataProvider.getCollectionsStatDTO(Term.COLLECTION_TYPE_BY_TAXONOMY));
			}
			else if(collectionType.equals(RegulogCollection.COLLECTION_TYPE_TF)){
				regulogCollectionStats = toRegulogCollectionStats(collectionType,
						dataProvider.getCollectionsStatDTO(Term.COLLECTION_TYPE_BY_REGULATOR));
			}
			else if(collectionType.equals(RegulogCollection.COLLECTION_TYPE_TFFAM)){
				regulogCollectionStats = toTFFamRegulogCollectionStats(dataProvider.getTFFamiliyStatDTO());
			}
			else if(collectionType.equals(RegulogCollection.COLLECTION_TYPE_RNAFAM)){
				regulogCollectionStats = toRiboswitchRegulogCollectionStats(dataProvider.getRiboswitchStatDTO());
			}			
			else if(collectionType.equals(RegulogCollection.COLLECTION_TYPE_EFFECTOR)){
				regulogCollectionStats = toEffectorRegulogCollectionStats(dataProvider.getEffectorsStatDTO());
			}			
			else if(collectionType.equals(RegulogCollection.COLLECTION_TYPE_PATHWAY)){
				regulogCollectionStats = toPathwayRegulogCollectionStats(dataProvider.getPathwaysStatDTO());
			}	
			else{
				regulogCollectionStats = new Vector<RegulogCollectionStat>();
			}
		}
		catch(Exception e)
		{
			serverError();
		}
		finally{
			dataProvider.close();
		}		
		return regulogCollectionStats;
	}

	
	private List<RegulogCollectionStat> toRegulogCollectionStats(
			String collectionType, List<CollectionStatDTO> collectionsStatDTO) {
		
		List<RegulogCollectionStat> regulogCollections = new Vector<RegulogCollectionStat>();
		for(CollectionStatDTO stat: collectionsStatDTO)
		{
			RegulogCollectionStat regCollection = new RegulogCollectionStat();
			regCollection.setCollectionType(collectionType);
			regCollection.setCollectionId(stat.getCollectionId());
			regCollection.setName(stat.getCollectionName());
			regCollection.setClassName("");
			
			regCollection.setTotalGenomeCount(stat.getTotlaGenomeCount().intValue());
			regCollection.setTotalRegulogCount(stat.getTotalRegulogCount().intValue());
			
			regCollection.setTfCount(stat.getTfCount().intValue());
			regCollection.setTfRegulogCount(stat.getTfRegulogCount().intValue());	
			regCollection.setTfSiteCount(stat.getTfSiteCount().intValue());
			
			regCollection.setRnaCount(stat.getRnaCount().intValue());
			regCollection.setRnaRegulogCount(stat.getRnaRegulogCount().intValue());
			regCollection.setRnaSiteCount(stat.getRnaSiteCount().intValue());
			
			regulogCollections.add(regCollection);
		}
		return regulogCollections;	
	}	
	
	
	
	private List<RegulogCollectionStat> toPathwayRegulogCollectionStats(
			List<PathwayStatDTO> pathwaysStatDTO) {
		
		List<RegulogCollectionStat> regulogCollections = new Vector<RegulogCollectionStat>();
		for(PathwayStatDTO stat: pathwaysStatDTO)
		{
			RegulogCollectionStat regCollection = new RegulogCollectionStat();
			regCollection.setCollectionType(RegulogCollection.COLLECTION_TYPE_PATHWAY);
			regCollection.setCollectionId(stat.getPathwayId());
			regCollection.setName(stat.getPathwayName());
			regCollection.setClassName(stat.getPathwayClassName());
			 
			regCollection.setTotalGenomeCount(stat.getTotalGenomeCount().intValue());
			regCollection.setTotalRegulogCount(stat.getTotalRegulogCount().intValue());
			
			regCollection.setTfCount(stat.getTfCount().intValue());
			regCollection.setTfRegulogCount(stat.getTfRegulogCount().intValue());	
			regCollection.setTfSiteCount(stat.getTfSiteCount().intValue());
			
			regCollection.setRnaCount(stat.getRnaCount().intValue());
			regCollection.setRnaRegulogCount(stat.getRnaRegulogCount().intValue());
			regCollection.setRnaSiteCount(stat.getRnaSiteCount().intValue());					
			regulogCollections.add(regCollection);
		}
		return regulogCollections;
	}



	private List<RegulogCollectionStat> toEffectorRegulogCollectionStats(
			List<EffectorStatDTO> effectorsStatDTO) {
		
		List<RegulogCollectionStat> regulogCollections = new Vector<RegulogCollectionStat>();
		for(EffectorStatDTO stat: effectorsStatDTO)
		{
			RegulogCollectionStat regCollection = new RegulogCollectionStat();
			regCollection.setCollectionType(RegulogCollection.COLLECTION_TYPE_EFFECTOR);
			regCollection.setCollectionId(stat.getEffectorId());
			regCollection.setName(stat.getEffectorName());
			regCollection.setClassName(stat.getEffectorClassName());
			
			regCollection.setTotalGenomeCount(stat.getTotalGenomeCount().intValue());
			regCollection.setTotalRegulogCount(stat.getTotalRegulogCount().intValue());
			
			regCollection.setTfCount(stat.getTfCount().intValue());
			regCollection.setTfRegulogCount(stat.getTfRegulogCount().intValue());	
			regCollection.setTfSiteCount(stat.getTfSiteCount().intValue());
			
			regCollection.setRnaCount(stat.getRnaCount().intValue());
			regCollection.setRnaRegulogCount(stat.getRnaRegulogCount().intValue());
			regCollection.setRnaSiteCount(stat.getRnaSiteCount().intValue());					
			regulogCollections.add(regCollection);			
			
			regulogCollections.add(regCollection);
		}
		return regulogCollections;
	}



	private List<RegulogCollectionStat> toRiboswitchRegulogCollectionStats(
			List<RiboswitchStatDTO> riboswitchStatDTO) {
		
		List<RegulogCollectionStat> regulogCollections = new Vector<RegulogCollectionStat>();
		for(RiboswitchStatDTO stat: riboswitchStatDTO)
		{
			RegulogCollectionStat regCollection = new RegulogCollectionStat();
			regCollection.setCollectionType(RegulogCollection.COLLECTION_TYPE_RNAFAM);
			regCollection.setCollectionId(stat.getRiboswitchId());
			regCollection.setName(stat.getRiboswitchName());
			regCollection.setClassName("");
			
			regCollection.setTotalGenomeCount(stat.getGenomeCount().intValue());
			regCollection.setTotalRegulogCount(stat.getRegulogCount().intValue());
			
			regCollection.setTfCount(0);
			regCollection.setTfRegulogCount(0);	
			regCollection.setTfSiteCount(0);
			
			regCollection.setRnaCount(stat.getRegulogCount().intValue());
			regCollection.setRnaRegulogCount(stat.getRegulogCount().intValue());
			regCollection.setRnaSiteCount(stat.getSiteCount().intValue());					
			regulogCollections.add(regCollection);
			
			regulogCollections.add(regCollection);
		}
		return regulogCollections;	
	}

	private List<RegulogCollectionStat> toTFFamRegulogCollectionStats(
			List<TFFamiliyStatDTO> tfFamiliyStatDTO) {
		
		List<RegulogCollectionStat> regulogCollections = new Vector<RegulogCollectionStat>();
		for(TFFamiliyStatDTO stat: tfFamiliyStatDTO)
		{
			RegulogCollectionStat regCollection = new RegulogCollectionStat();
			regCollection.setCollectionType(RegulogCollection.COLLECTION_TYPE_TFFAM);
			regCollection.setCollectionId(stat.getTfFamilyId());
			regCollection.setName(stat.getTfFamilyName());
			regCollection.setClassName("");
			
			regCollection.setTotalGenomeCount(stat.getGenomeCount().intValue());
			regCollection.setTotalRegulogCount(stat.getRegulogCount().intValue());
			
			regCollection.setTfCount(stat.getTfCount().intValue());
			regCollection.setTfRegulogCount(stat.getRegulogCount().intValue());	
			regCollection.setTfSiteCount(stat.getSiteCount().intValue());
			
			regCollection.setRnaCount(0);
			regCollection.setRnaRegulogCount(0);
			regCollection.setRnaSiteCount(0);					
			regulogCollections.add(regCollection);
			
			regulogCollections.add(regCollection);
		}
		return regulogCollections;	
	}

}
