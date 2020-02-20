package com.mng.testmaker.boundary.aspects.test;

import java.util.List;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import com.mng.testmaker.domain.InputParamsTM;
import com.mng.testmaker.domain.suitetree.SuiteBean;

public class ClientRestApiTM extends JaxRsClient {
	
	public SuiteBean suiteRun(InputParamsTM inputParams, List<String> testCases) throws Exception {
		Form formParams = getFormParams(inputParams.getAllParamsValues());
		MultivaluedMap<String, String> mapParams = formParams.asMap();
		mapParams.putSingle(InputParamsTM.TCaseNameParam, String.join(",", testCases));
		mapParams.putSingle(InputParamsTM.AsyncExecParam, "false");
		mapParams.putSingle(InputParamsTM.RemoteParam, "true");
		Client client = getClientIgnoreCertificates();
		SuiteBean suiteData = 
			client
				.target("http://localhost:80/suiterun")
				.request(MediaType.APPLICATION_JSON)
				.post(Entity.form(formParams), SuiteBean.class);
		//TODO ha de retornar la Suite + TestRuns + TestCases ... + Steps + Validations
		return suiteData;
	}
	
	private Form getFormParams(Map<String,String> params) {
		Form formParams = new Form();
		for (Map.Entry<String, String> entry : params.entrySet()) {
			formParams.param(entry.getKey(), entry.getValue());
		}
		return formParams;
	}
}
