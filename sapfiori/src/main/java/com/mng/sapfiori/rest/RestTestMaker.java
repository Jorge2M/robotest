package com.mng.sapfiori.rest;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import com.mng.sapfiori.ExecutorSuite;
import com.mng.testmaker.domain.InputParamsTM;

@Path("/")
public class RestTestMaker {

	@POST
	@Path("suite")
	//@Consumes("application/json")
	@Produces("application/json")
	public void executeNewSuite(@BeanParam InputParamsTM inputParams) {
		ExecutorSuite executor = ExecutorSuite.getNew();
		try {
			executor.execTestSuite(inputParams);
		}
		catch (Exception e) {
			throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GET
	@Path("/testserver")
	public String test() {
		return "Jetty Server Started Ok";
	}
	
//	@GET
//	@Path("suite/{id}")
//	@Produces("application/json")
//	public StreamingOutput getExecutedSuite(@PathParam("id") String id) {
//		
//	}
}
