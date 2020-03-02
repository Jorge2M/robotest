package com.mng.testmaker.boundary.aspects.test.remote;

import java.util.Arrays;

import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

import com.mng.testmaker.conf.Channel;
import com.mng.testmaker.domain.InputParamsBasic;
import com.mng.testmaker.domain.suitetree.SuiteBean;
import com.mng.testmaker.domain.suitetree.TestCaseBean;

public class RemoteTestTest {

	private enum Suites {SmokeTest}
	private enum AppEcom {shop}
	
	//@Ignore
	@Test
	public void testSuiteRun() throws Exception {
		//Given
		InputParamsBasic inputParams = new InputParamsBasic(Suites.class, AppEcom.class);
		inputParams.setSuite(Suites.SmokeTest);
		inputParams.setBrowser("chrome");
		inputParams.setChannel(Channel.desktop);
		inputParams.setApp(AppEcom.shop);
		inputParams.setVersion("V1");
		inputParams.setUrlBase("https://shop.mango.com/preHome.faces");
		
		//When
		RemoteTest remoteTest = new RemoteTest();
		SuiteBean suiteRemote = remoteTest.suiteRun(inputParams, Arrays.asList("SES002"));
		
		//Then
		TestCaseBean testCaseRemote = suiteRemote.
				getListTestRun().get(0).
				getListTestCase().get(0);
		assertTrue("SES002_IniciarSesion_OK".compareTo(testCaseRemote.getName())==0);
	}

}
