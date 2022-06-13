package com.mng.robotest.access.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import javax.validation.constraints.NotNull;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import com.mng.robotest.access.InputParamsMango;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.utils.PagoGetter;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.domain.InputParamsBasic;
import com.github.jorge2m.testmaker.domain.testfilter.TestMethodData;
import com.github.jorge2m.testmaker.restcontroller.RestApiTM;


@Path("/")
public class RestApiMango extends RestApiTM {

	@Override
	@POST
	@Path("/suiterun_disabled")
	@Produces("application/json")
	public Response newSuiteRun(@BeanParam InputParamsBasic inputParams) {
		return null;
	}
	
	@Override
	@GET
	@Path("/suitetestcases_disabled")
	@Produces("application/json")
	public List<TestMethodData> getTestCasesFromSuite(@BeanParam InputParamsBasic inputParams) {
		return null;
	}
	
	@POST
	@Path("/suiterun")
	@Produces("application/json")
	public Response newSuiteRunMango(@BeanParam InputParamsMango inputParams) {
		return super.newSuiteRun(inputParams);
	}
	
	@GET
	@Path("/suite/testcases")
	@Produces("application/json")
	public List<TestMethodData> getTestCasesFromSuiteMango(
			@QueryParam("suite") String suite,
			@QueryParam("app") String app,
			@QueryParam("channel") String channel,
			@QueryParam("driver") String driver) throws Exception {
		InputParamsMango inputParams = new InputParamsMango();
		inputParams.setSuite(suite);
		inputParams.setApp(app);
		inputParams.setChannel(channel);
		inputParams.setDriver(driver);
		return super.getTestCasesFromSuite(inputParams);
	}
	
	@GET
	@Path("/payments")
	@Produces("application/json")
	public List<PagoLabelJson> getPayments(
					@QueryParam("countries") String countrysCommaSeparated,
					@NotNull
					@QueryParam("channel") String channelInput,
					@NotNull
					@QueryParam("app") String appInput) throws Exception {
		if (countrysCommaSeparated!=null) {
			if (!Pattern.matches("(\\d{3},)*\\d{3}", countrysCommaSeparated)) {
				throw new WebApplicationException("Parameter 'countrys' incorrect", Response.Status.BAD_REQUEST);
			}
		} 
		if (!enumContainsValue(Channel.class, channelInput)) {
			throw new WebApplicationException("Parameter 'channel' incorrect", Response.Status.BAD_REQUEST);
		}
		if (!enumContainsValue(AppEcom.class, appInput)) {
			throw new WebApplicationException("Parameter 'app' incorrect", Response.Status.BAD_REQUEST);
		}
		
		Channel channel = Channel.valueOf(channelInput);
		AppEcom app = AppEcom.valueOf(appInput);
		List<String> listLabelsPagos = null;
		if (countrysCommaSeparated!=null) {
			List<String> countrysList = Arrays.asList(countrysCommaSeparated.split(","));
			listLabelsPagos = PagoGetter.getLabelsPaymentsAlphabetically(countrysList, channel, app, false);
		} else {
			listLabelsPagos = PagoGetter.getLabelsPaymentsAlphabetically(channel, app, false);
		}
		
		return getPagoLabelsJson(listLabelsPagos);
	}
	
	private List<PagoLabelJson> getPagoLabelsJson(List<String> listLabelsPagos) {
		List<PagoLabelJson> listLabelsJson = new ArrayList<>();
		for (String pagoLabel : listLabelsPagos) {
			listLabelsJson.add(new PagoLabelJson(pagoLabel));
		}
		return listLabelsJson;
	}
	
	private boolean enumContainsValue(Class<? extends Enum<?>> enumClass, String value) {
		for (Enum<?> enumItem : enumClass.getEnumConstants()) {
			if (enumItem.name().compareTo(value)==0) {
				return true;
			}
		}
		return false;
	}
}
