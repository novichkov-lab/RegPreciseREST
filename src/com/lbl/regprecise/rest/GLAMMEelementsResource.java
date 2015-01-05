package com.lbl.regprecise.rest;

import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.lbl.regprecise.dao.hibernate.ConstrainedDataProvider;
import com.lbl.regprecise.dao.hibernate.StatusConstrainedDataProvider;
import com.lbl.regprecise.dto.GLAMMElementDTO;
import com.lbl.regprecise.ent.Term;
import com.lbl.regprecise.xml.GLAMMElement;

/**
 * @author Pavel Novichkov
 *
 */
@Path("/glammElements")
public class GLAMMEelementsResource extends Resource {

	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public List<GLAMMElement> getGLAMMElements(
			//Old version
			@QueryParam("taxonomyId") Integer taxonomyId, 
			@QueryParam("regulonId") Integer regulonId,
			
			// New version
			@QueryParam("id") Integer resourceId, @QueryParam("type") String resourceType
			) 
	{
		assertParameters(taxonomyId, regulonId, resourceId);
		
		List<GLAMMElement> elements = null;
		ConstrainedDataProvider dataProvider = new StatusConstrainedDataProvider(Term.TERM_REGULOG_STATE_PUBLIC);
		try{
			
			if(regulonId != null)
			{
				elements = buildGLAMMElements(dataProvider, regulonId, GLAMMElementDTO.TYPE_REGULON);
			}
			else if(taxonomyId != null)
			{
				elements = buildGLAMMElements(dataProvider, taxonomyId, GLAMMElementDTO.TYPE_TAXONOMY);
			}
			else if(resourceId != null)
			{
				assertParameter(resourceType);
				elements = buildGLAMMElements(dataProvider, resourceId, resourceType);
			}
		}
		catch(Exception e)
		{
			serverError();
		}
		finally{
			dataProvider.close();
		}		
		return elements;
	}

	private static class Counter{
		int value = 0;
		void increment()
		{
			value++;
		}		
	}
	
	private List<GLAMMElement> buildGLAMMElements(ConstrainedDataProvider dataProvider, Integer resourceId,
			String resourceType) {
		List<GLAMMElementDTO> genes = dataProvider.getGLAMMElementsDTO(resourceId, resourceType);

		List<GLAMMElement> elements;
		
		if(resourceType.equals(GLAMMElementDTO.TYPE_REGULOG))
		{
			int genomeCount = dataProvider.getRegulog(resourceId).getRegulons().size();
			elements = buildGLAMMElements_OrthologStrength(genes, genomeCount);
		}
		else{
			elements = buildGLAMMElements_SingleGeneStrength(genes);
		}
		
		return elements;
	}

	private List<GLAMMElement> buildGLAMMElements_SingleGeneStrength(List<GLAMMElementDTO> genes) {
		
		Hashtable<Integer,Counter> geneMoId2Count = new Hashtable<Integer,Counter>();
		List<GLAMMElement> elements = new Vector<GLAMMElement>();
		
		// update counters
		for(GLAMMElementDTO gene: genes)
		{
			Counter counter = geneMoId2Count.get(gene.getGeneMoId());
			if(counter == null){
				counter = new Counter();
				geneMoId2Count.put(gene.getGeneMoId(), counter);
			}
			counter.increment();		
		}
		
		// Populate GLAMM Elements
		for(GLAMMElementDTO gene: genes)
		{
			GLAMMElement element = buildGLAMMElement(gene);
			Counter counter = geneMoId2Count.get(gene.getGeneMoId());
			
			if(counter.value == 1)
			{
				element.setStrength(0f);
			}
			else if(counter.value == 2)
			{
				element.setStrength(0.5f);
			}
			else{
				element.setStrength(1f);
			}
			elements.add(element);
		}
				
		return elements;
	}

	private List<GLAMMElement> buildGLAMMElements_OrthologStrength(List<GLAMMElementDTO> genes, int genomeCount) {
		
		Hashtable<Integer,Counter> orthologId2Count = new Hashtable<Integer,Counter>();
		List<GLAMMElement> elements = new Vector<GLAMMElement>();
		
		// update counters
		for(GLAMMElementDTO gene: genes)
		{
			Counter counter = orthologId2Count.get(gene.getOrthologId());
			if(counter == null){
				counter = new Counter();
				orthologId2Count.put(gene.getOrthologId(), counter);
			}
			counter.increment();			
		}
		
		// Populate GLAMM Elements
		for(GLAMMElementDTO gene: genes)
		{
			GLAMMElement element = buildGLAMMElement(gene);
			Counter counter = orthologId2Count.get(gene.getOrthologId());
			element.setStrength((float) (counter.value*1.0/genomeCount));
			elements.add(element);
		}
				
		return elements;
	}

	private GLAMMElement buildGLAMMElement(GLAMMElementDTO gene) {
		GLAMMElement element = new GLAMMElement();
		element.setCallbackURL(getCallbackURL(gene.getRegulonId()));
		element.setEcNumber(null);
		element.setGenomeName(gene.getGenomeName());
		element.setTaxonomyId(gene.getGenomeMoId());
		element.setGroupId("" + gene.getRegulonId());
		element.setGroupName(gene.getRegulatorName());
		element.setStrength(0f);
		element.setVimssId(gene.getGeneMoId());

		return element;
	}

	private String getCallbackURL(Integer regulonId) {
		return "http://regprecise.lbl.gov/RegPrecise/regulon.jsp?regulon_id=" + regulonId;
	}
}
