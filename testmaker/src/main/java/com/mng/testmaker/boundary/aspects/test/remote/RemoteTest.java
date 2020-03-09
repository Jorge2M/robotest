package com.mng.testmaker.boundary.aspects.test.remote;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.lang3.SerializationUtils;

import com.mng.testmaker.domain.InputParamsTM;
import com.mng.testmaker.domain.suitetree.ChecksTM;
import com.mng.testmaker.domain.suitetree.StepTM;
import com.mng.testmaker.domain.suitetree.SuiteBean;
import com.mng.testmaker.domain.suitetree.TestCaseBean;
import com.mng.testmaker.domain.suitetree.TestCaseTM;

public class RemoteTest extends JaxRsClient {
	
	public SuiteBean execute(TestCaseTM testCase, InputParamsTM inputParams, Serializable testObject) 
	throws Exception {
		byte[] testSerialized = SerializationUtils.serialize(testObject);
		String testSerializedStrB64 = Base64.getEncoder().encodeToString(testSerialized);
		SuiteBean suiteRemote = suiteRun(inputParams, null, testSerializedStrB64);
		return processTestCaseRemote(testCase, suiteRemote);
	}
	
	public SuiteBean execute(TestCaseTM testCase, InputParamsTM inputParams) throws Exception {
		SuiteBean suiteRemote = suiteRun(inputParams, Arrays.asList(testCase.getNameUnique()), null);
		return processTestCaseRemote(testCase, suiteRemote);
	}
	
	private SuiteBean processTestCaseRemote(TestCaseTM testCase, SuiteBean suiteRemoteExecuted) {
		//Get TestCase Remote
		TestCaseBean testCaseRemote = suiteRemoteExecuted.
				getListTestRun().get(0).
				getListTestCase().get(0);
		
		//Coser TestCase
		List<StepTM> listStepsRemote = testCaseRemote.getListStep();
		for (StepTM stepRemote : listStepsRemote) {
			testCase.addStep(stepRemote);
			stepRemote.setParents(testCase);
			stepRemote.getEvidencesWarehouse().setStep(stepRemote);
			stepRemote.moveContentEvidencesToFile();
			for (ChecksTM checks : stepRemote.getListChecksTM()) {
				checks.setParents(stepRemote);
			}
		}
		String throwableStrB64 = testCaseRemote.getThrowable();
		Throwable throwable = (Throwable)fromStringB64(throwableStrB64);
		testCase.getResult().setThrowable(throwable);
		testCase.getResult().setStatus(testCaseRemote.getStatusTng());
		testCase.setRefineDataName(testCaseRemote.getRefineDataName());
		return suiteRemoteExecuted;
	}
	
	SuiteBean suiteRun(InputParamsTM inputParams, List<String> testCases, String testObjectSerialized) 
	throws Exception {
		Form formParams = getFormParams(inputParams.getAllParamsValues());
		MultivaluedMap<String, String> mapParams = formParams.asMap();
		if (testCases!=null) {
			mapParams.putSingle(InputParamsTM.TCaseNameParam, String.join(",", testCases));
		}
		if (testObjectSerialized!=null) {
			mapParams.putSingle(InputParamsTM.TestObjectParam, testObjectSerialized);
		}
		mapParams.putSingle(InputParamsTM.AsyncExecParam, "false");
		mapParams.putSingle(InputParamsTM.RemoteParam, "true");

		Client client = getClientIgnoreCertificates();
		SuiteBean suiteData = 
			client
				.target("http://localhost:80/suiterun")
				.request(MediaType.APPLICATION_JSON)
				.post(Entity.form(formParams), SuiteBean.class);

		return suiteData;
	}
	
	private Form getFormParams(Map<String,String> params) {
		Form formParams = new Form();
		for (Map.Entry<String, String> entry : params.entrySet()) {
			formParams.param(entry.getKey(), entry.getValue());
		}
		return formParams;
	}
	
	/** Read the object from Base64 string. */
	private static Object fromStringB64(String s) {
		try {
			byte [] data = Base64.getDecoder().decode( s );
			ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
			Object o  = ois.readObject();
			ois.close();
			return o;
		}
		catch (Exception e) {
			return null;
		}
	}
}
