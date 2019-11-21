package com.mng.sapfiori.rest;

import java.net.URI;
import java.util.List;

import javax.ws.rs.BeanParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import com.mng.sapfiori.ExecutorSuiteSapFiori;
import com.mng.sapfiori.datatmaker.Apps;
import com.mng.sapfiori.datatmaker.Suites;
import com.mng.testmaker.boundary.access.CmdLineMaker;
import com.mng.testmaker.boundary.access.MessageError;
import com.mng.testmaker.boundary.access.ResultCheckOptions;
import com.mng.testmaker.domain.ExecutorSuite;
import com.mng.testmaker.domain.InputParamsTM;
import com.mng.testmaker.domain.InputParamsTM.TypeAccess;
import com.mng.testmaker.domain.SuiteTM;
import com.mng.testmaker.domain.data.SuiteData;
import com.mng.testmaker.service.TestMaker;

@Path("/")
public class RestTestMaker {

	@POST
	@Path("/suiterun")
	@Produces("application/json")
	public Response newSuiteRun(@BeanParam InputParamsTM inputParams) {
		inputParams.setSuiteEnum(Suites.class);
		inputParams.setAppEnum(Apps.class);
		inputParams.setTypeAccess(TypeAccess.Rest);
		try {
			CmdLineMaker cmdLineAccess = CmdLineMaker.from(inputParams);
			ResultCheckOptions resultCheck = cmdLineAccess.checkOptionsValue();
			if (resultCheck.isOk()) {
				ExecutorSuite executor = ExecutorSuiteSapFiori.getNew(inputParams);
				SuiteTM suite = TestMaker.execSuiteAsync(executor);
				return Response
						.status(Response.Status.OK) 
						.entity(SuiteData.from(suite))
						.build();
			} else {
				List<MessageError> listErrors = resultCheck.getListMessagesError();
				GenericEntity<List<MessageError>> entity = new GenericEntity<List<MessageError>>(listErrors){};
				return Response
						.status(Response.Status.BAD_REQUEST)
						.entity(entity)
						.build();
				}
		}
		catch (Exception e) {
			return Response
					.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(e.getCause())
					.build();
		}
	}
	
	@GET
	@Path("/suiterun/{idexecution}")
	@Produces("application/json")
	public SuiteData getSuiteRunData(@PathParam("idexecution") String idSuiteExec) {
		SuiteTM suite = TestMaker.getSuite(idSuiteExec);
		if (suite!=null) {
			return SuiteData.from(suite);
		} else {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
	}
	
	@DELETE
	@Path("/suiterun/{idexecution}")
	public void stopSuiteRun(@PathParam("idexecution") String idExecSuite) {
		SuiteTM suite = TestMaker.getSuite(idExecSuite);
		if (suite!=null) {
			TestMaker.stopSuite(suite);
		}
	}
	
	@GET
	@Path("/suiterun/{idexecution}/report")
	public Response getSuiteReportHtml(@PathParam("idexecution") String idExecSuite) {
		SuiteTM suite = TestMaker.getSuite(idExecSuite);
		if (suite!=null) {
			URI uriReport = UriBuilder.fromUri(suite.getDnsReportHtml()).build();
			return Response.temporaryRedirect(uriReport).build();
		} else {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
	}
	
	@GET
	@Path("/testserver")
	public String test() {
		return "Jetty Server Started Ok";
	}
}
