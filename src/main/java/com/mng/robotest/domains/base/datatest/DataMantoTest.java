package com.mng.robotest.domains.base.datatest;

import java.util.Optional;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.domain.InputParamsTM;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.service.exceptions.NotFoundException;
import com.mng.robotest.conf.AppEcom;
import com.mng.robotest.test.data.Constantes;

public class DataMantoTest {
	private String urlManto;
	private String userManto;
	private String passManto; 
	private Channel channel;
	private AppEcom appE;
	
	private DataMantoTest() {}
	
	public static DataMantoTest make() {
		var dMantoAcc = new DataMantoTest();
		var testCase = getTestCase();
		var testRun = testCase.getTestRunParent();
		InputParamsTM inputParams = getTestCase().getInputParamsSuite();
		dMantoAcc.setUrlManto(inputParams.getUrlBase());
		dMantoAcc.setUserManto(testRun.getParameter(Constantes.PARAM_USR_MANTO));
		dMantoAcc.setPassManto(testRun.getParameter(Constantes.PARAM_PAS_MANTO));
		dMantoAcc.setAppE(AppEcom.shop);
		return dMantoAcc;
	}		
	
	private static TestCaseTM getTestCase() throws NotFoundException {
		Optional<TestCaseTM> testCaseOpt = TestMaker.getTestCase();
		if (!testCaseOpt.isPresent()) {
		  throw new NotFoundException("Not found TestCase");
		}
		return testCaseOpt.get();
	}	
	
	public String getUrlManto() {
		return urlManto;
	}
	public void setUrlManto(String urlManto) {
		this.urlManto = urlManto;
	}
	public String getUserManto() {
		return userManto;
	}
	public void setUserManto(String userManto) {
		this.userManto = userManto;
	}
	public String getPassManto() {
		return passManto;
	}
	public void setPassManto(String passManto) {
		this.passManto = passManto;
	}
	public Channel getChannel() {
		return channel;
	}
	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	public AppEcom getAppE() {
		return appE;
	}
	public void setAppE(AppEcom appE) {
		this.appE = appE;
	}
	
		
}
