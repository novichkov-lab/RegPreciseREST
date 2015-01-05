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

/**
 * @author Elena Novichkova
 *
 */
@Path("/regulogCollections")
public class RegulogCollectionsResource extends Resource{

	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public List<RegulogCollection> getRegulogCollections( @QueryParam("collectionType") String collectionType)
	{
		
		assertParameter(collectionType);
		assertParameter( RegulogCollection.isValidCollectionType(collectionType) );
		
		
		List<RegulogCollection> regulogCollections = null;
		ConstrainedDataProvider dataProvider = new StatusConstrainedDataProvider(Term.TERM_REGULOG_STATE_PUBLIC);
		try{
			if(collectionType.equals(RegulogCollection.COLLECTION_TYPE_TAXGROUP))
			{
				regulogCollections = toRegulogCollections(collectionType, 
						dataProvider.getCollectionsStatDTO(Term.COLLECTION_TYPE_BY_TAXONOMY));
			}
			else if(collectionType.equals(RegulogCollection.COLLECTION_TYPE_TF)){
				regulogCollections = toRegulogCollections(collectionType,
						dataProvider.getCollectionsStatDTO(Term.COLLECTION_TYPE_BY_REGULATOR));
			}
			else if(collectionType.equals(RegulogCollection.COLLECTION_TYPE_TFFAM)){
				regulogCollections = toTFFamRegulogCollections(dataProvider.getTFFamiliyStatDTO());
			}
			else if(collectionType.equals(RegulogCollection.COLLECTION_TYPE_RNAFAM)){
				regulogCollections = toRiboswitchRegulogCollections(dataProvider.getRiboswitchStatDTO());
			}			
			else if(collectionType.equals(RegulogCollection.COLLECTION_TYPE_EFFECTOR)){
				regulogCollections = toEffectorRegulogCollections(dataProvider.getEffectorsStatDTO());
			}			
			else if(collectionType.equals(RegulogCollection.COLLECTION_TYPE_PATHWAY)){
				regulogCollections = toPathwayRegulogCollections(dataProvider.getPathwaysStatDTO());
			}	
			else{
				regulogCollections = new Vector<RegulogCollection>();
			}
		}
		catch(Exception e)
		{
			serverError();
		}
		finally{
			dataProvider.close();
		}		
		return regulogCollections;
	}

	private List<RegulogCollection> toPathwayRegulogCollections(
			List<PathwayStatDTO> pathwaysStatDTO) {
		
		List<RegulogCollection> regulogCollections = new Vector<RegulogCollection>();
		for(PathwayStatDTO stat: pathwaysStatDTO)
		{
			RegulogCollection regCollection = new RegulogCollection();
			regCollection.setCollectionType(RegulogCollection.COLLECTION_TYPE_PATHWAY);
			regCollection.setCollectionId(stat.getPathwayId());
			regCollection.setName(stat.getPathwayName());
			regCollection.setClassName(stat.getPathwayClassName());
			regulogCollections.add(regCollection);
		}
		return regulogCollections;
	}



	private List<RegulogCollection> toEffectorRegulogCollections(
			List<EffectorStatDTO> effectorsStatDTO) {
		
		List<RegulogCollection> regulogCollections = new Vector<RegulogCollection>();
		for(EffectorStatDTO stat: effectorsStatDTO)
		{
			RegulogCollection regCollection = new RegulogCollection();
			regCollection.setCollectionType(RegulogCollection.COLLECTION_TYPE_EFFECTOR);
			regCollection.setCollectionId(stat.getEffectorId());
			regCollection.setName(stat.getEffectorName());
			regCollection.setClassName(stat.getEffectorClassName());
			regulogCollections.add(regCollection);
		}
		return regulogCollections;
	}



	private List<RegulogCollection> toRiboswitchRegulogCollections(
			List<RiboswitchStatDTO> riboswitchStatDTO) {
		
		List<RegulogCollection> regulogCollections = new Vector<RegulogCollection>();
		for(RiboswitchStatDTO stat: riboswitchStatDTO)
		{
			RegulogCollection regCollection = new RegulogCollection();
			regCollection.setCollectionType(RegulogCollection.COLLECTION_TYPE_RNAFAM);
			regCollection.setCollectionId(stat.getRiboswitchId());
			regCollection.setName(stat.getRiboswitchName());
			regCollection.setClassName("");
			regulogCollections.add(regCollection);
		}
		return regulogCollections;	
	}



	private List<RegulogCollection> toRegulogCollections(
			String collectionType, List<CollectionStatDTO> collectionsStatDTO) {
		
		List<RegulogCollection> regulogCollections = new Vector<RegulogCollection>();
		for(CollectionStatDTO stat: collectionsStatDTO)
		{
			RegulogCollection regCollection = new RegulogCollection();
			regCollection.setCollectionType(collectionType);
			regCollection.setCollectionId(stat.getCollectionId());
			regCollection.setName(stat.getCollectionName());
			regCollection.setClassName("");
			regulogCollections.add(regCollection);
		}
		return regulogCollections;	
	}



	private List<RegulogCollection> toTFFamRegulogCollections(
			List<TFFamiliyStatDTO> tfFamiliyStatDTO) {
		
		List<RegulogCollection> regulogCollections = new Vector<RegulogCollection>();
		for(TFFamiliyStatDTO stat: tfFamiliyStatDTO)
		{
			RegulogCollection regCollection = new RegulogCollection();
			regCollection.setCollectionType(RegulogCollection.COLLECTION_TYPE_TFFAM);
			regCollection.setCollectionId(stat.getTfFamilyId());
			regCollection.setName(stat.getTfFamilyName());
			regCollection.setClassName("");
			regulogCollections.add(regCollection);
		}
		return regulogCollections;	
	}

	
}
