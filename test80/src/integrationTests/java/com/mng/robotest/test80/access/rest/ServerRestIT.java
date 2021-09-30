package com.mng.robotest.test80.access.rest;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import org.junit.BeforeClass;

import com.github.jorge2m.testmaker.boundary.remotetest.JaxRsClient;
import com.github.jorge2m.testmaker.domain.suitetree.SuiteBean;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseBean;

public class ServerRestIT {
	
	static String ipServer;
	static String portServer;
	static Client client; 
	
	@BeforeClass
	public static void setupLocalServerRobotest() throws Exception {
		ipServer = "localhost";
		portServer = "8085";
		JaxRsClient jaxClient = new JaxRsClient();
		client = jaxClient.getClientIgnoreCertificates();
		startLocalServer(portServer);
		checkServerAvailability(5);
	}
	
	protected static void startLocalServer(String serverPort) {
		String[] args = {"-port", serverPort};
		CompletableFuture.runAsync(() -> {
			try {
				ServerRest.main(args);
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
		});
	}
	
	protected static boolean checkServerAvailability(int maxTimes) 
	throws Exception {
		for (int i=0; i<maxTimes; i++) {
			if (checkServerAvailability()) {
				return true;
			}
			Thread.sleep(1000);
		}
		return false;
	}
	
	protected static boolean checkServerAvailability() {
		try {
			client
				.target("http://" + ipServer + ":" + portServer + "/testserver")
				.request(MediaType.APPLICATION_JSON)
				.get();
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}
	
	protected SuiteBean executeTestsAgainstServer(InputParamsRobotest inputRobotest) throws Exception {
		
		SuiteBean suiteData = 
			client
				.target("http://" + ipServer + ":" + portServer + "/suiterun")
				.request(MediaType.APPLICATION_JSON)
				.post(Entity.form(inputRobotest.getParamsForm()), SuiteBean.class);
		
		return suiteData;
	}
	
	protected boolean checkExistsTestCase(String valueSearched, List<TestCaseBean> listTestCases) {
		for (TestCaseBean testCase : listTestCases) {
			if (testCase.getName().contains(valueSearched)) {
				return true;
			}
		}
		return false;
	}
	
	protected void checkReporsSuiteExists(SuiteBean suite) {
		String pathReports = getPathReports(suite);
		File emailable = new File(pathReports + "/emailable-report.html");
		File logReport = new File(pathReports + "/emailable-report.html");
		File reportHtml = new File(pathReports + "/ReportTSuite.html");
		assertTrue(emailable.exists());
		assertTrue(logReport.exists());
		assertTrue(reportHtml.exists());
	}
	
	protected String getPathReports(SuiteBean suite) {
		return 
			"./output-library/" +
			suite.getName() + "/" +
			suite.getIdExecSuite();
	}

	protected String getPathEvidences(SuiteBean suite, TestCaseBean testCase) {
		return (
			getPathReports(suite) + "/" +
			testCase.getTestRunName() + "/" + 
			testCase.getNameUnique());
	}
}
