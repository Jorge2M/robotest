package com.mng.testmaker.boundary.aspects.test.remote;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import com.mng.testmaker.domain.InputParamsTM;
import com.mng.testmaker.domain.suitetree.ChecksTM;
import com.mng.testmaker.domain.suitetree.StepTM;
import com.mng.testmaker.domain.suitetree.SuiteBean;
import com.mng.testmaker.domain.suitetree.TestCaseBean;
import com.mng.testmaker.domain.suitetree.TestCaseTM;

public class RemoteTest extends JaxRsClient {
	
	public SuiteBean execute(TestCaseTM testCase, InputParamsTM inputParams) throws Exception {
		//Exec TestCase
		SuiteBean suiteRemote = suiteRun(inputParams, Arrays.asList(testCase.getNameUnique()));
		TestCaseBean testCaseRemote = suiteRemote.
				getListTestRun().get(0).
				getListTestCase().get(0);
		
		//Coser
		List<StepTM> listStepsRemote = testCaseRemote.getListStep();
		for (StepTM stepRemote : listStepsRemote) {
			stepRemote.setParents(testCase);
			for (ChecksTM checks : stepRemote.getListChecksTM()) {
				checks.setParents(stepRemote);
			}
		}
		testCase.setListStep(listStepsRemote);
		return suiteRemote;
	}
	
	SuiteBean suiteRun(InputParamsTM inputParams, List<String> testCases) throws Exception {
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
