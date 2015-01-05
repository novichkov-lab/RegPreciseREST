package com.lbl.regprecise.rest;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * @author Elena Novichkova
 *
 */
public class Resource {
	
	public void assertParameter(Integer param)
	{
		if(param == null) badParameter();
	}
	
	public void assertParameter(String param)
	{
		if(param == null) badParameter();
	}
	
	public void assertParameter(boolean condition)
	{
		if(!condition) badParameter();
	}


	public void assertParameters(Object...params )
	{
		boolean hasNonNull = false;
		for(Object param: params)
		{
			if(param != null) {
				hasNonNull = true;
				break;
			}
		}
		
		if(!hasNonNull)
		{
			badParameter();
		}
	}

	
	protected void badParameter()
	{
		System.err.println("Going to throw BAD_REQUEST exception");
		throw new WebApplicationException(Response.Status.BAD_REQUEST); 
	}
	
	protected void serverError()
	{
		System.err.println("Going to throw INTERNAL_SERVER_ERROR exception");
		throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR); 
	}
	
}
