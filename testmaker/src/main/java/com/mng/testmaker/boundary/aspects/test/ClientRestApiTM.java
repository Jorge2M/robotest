package com.mng.testmaker.boundary.aspects.test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import com.google.gson.Gson;
import com.mng.testmaker.domain.InputParamsBasic;
import com.mng.testmaker.domain.data.SuiteData;

public class ClientRestApiTM extends JaxRsClient {
	
	public SuiteData suiteRun(InputParamsBasic inputParams) throws Exception {
		Gson gson = new Gson();
		String json = gson.toJson(inputParams.getAllParamsValues());

		Client client = getClientIgnoreCertificates();
		SuiteData suiteData = 
			client
				.target("http://localhost:80/suiterun")
				.request(MediaType.APPLICATION_FORM_URLENCODED)
				.post(Entity.json(json), SuiteData.class);

		return suiteData;
	}
	
}
