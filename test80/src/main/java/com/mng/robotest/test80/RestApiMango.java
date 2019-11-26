package com.mng.robotest.test80;

import javax.ws.rs.BeanParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.mng.testmaker.restcontroller.RestApiTM;

@Path("/")
public class RestApiMango extends RestApiTM {

	@POST
	@Path("/suiterunmango")
	@Produces("application/json")
	public Response newSuiteRunMango(@BeanParam InputParamsMango inputParams) {
		return newSuiteRun(inputParams);
	}
	
}
