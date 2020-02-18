package com.mng.testmaker.boundary.aspects.test;

import java.util.Arrays;

import org.junit.Ignore;
import org.junit.Test;

import com.mng.testmaker.conf.Channel;
import com.mng.testmaker.domain.InputParamsBasic;

public class ClientRestApiTMTest {

	private enum Suites {SmokeTest}
	private enum AppEcom {shop}
	
	@Ignore
	@Test
	public void test() throws Exception {
		InputParamsBasic inputParams = new InputParamsBasic(Suites.class, AppEcom.class);
		inputParams.setSuite(Suites.SmokeTest);
		inputParams.setBrowser("chrome");
		inputParams.setChannel(Channel.desktop);
		inputParams.setApp(AppEcom.shop);
		inputParams.setVersion("V1");
		inputParams.setUrlBase("https://shop.mango.com/preHome.faces");
		inputParams.setListTestCaseItems(Arrays.asList("BOR001"));
		inputParams.setAsyncExec("false");
		
		ClientRestApiTM client = new ClientRestApiTM();
		client.suiteRun(inputParams);
	}

}
