package com.mng.testmaker.domain.data;

import java.util.Date;

import com.mng.testmaker.conf.State;
import com.mng.testmaker.domain.SuiteTM;
import com.mng.testmaker.domain.TestRunTM;
import com.mng.testmaker.service.webdriver.maker.FactoryWebdriverMaker.WebDriverType;

public class TestRunData {

	private String idExecSuite;
	private String suiteName;
	private String name;
	private State result;
	private String device;
	private Date inicioDate;
	private Date finDate;
	private float durationMillis;
	private int numberTestCases;
	private WebDriverType webDriverType;
	
	public String getIdExecSuite() {
		return idExecSuite;
	}
	public void setIdExecSuite(String idExecSuite) {
		this.idExecSuite = idExecSuite;
	}
	public String getSuiteName() {
		return suiteName;
	}
	public void setSuiteName(String suiteName) {
		this.suiteName = suiteName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public State getResult() {
		return result;
	}
	public void setResult(State result) {
		this.result = result;
	}
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}
	public Date getInicioDate() {
		return inicioDate;
	}
	public void setInicioDate(Date inicioDate) {
		this.inicioDate = inicioDate;
	}
	public Date getFinDate() {
		return finDate;
	}
	public void setFinDate(Date finDate) {
		this.finDate = finDate;
	}
	public float getDurationMillis() {
		return durationMillis;
	}
	public void setDurationMillis(float durationMillis) {
		this.durationMillis = durationMillis;
	}
	public int getNumberTestCases() {
		return numberTestCases;
	}
	public void setNumberTestCases(int numberTestCases) {
		this.numberTestCases = numberTestCases;
	}
	public WebDriverType getWebDriverType() {
		return webDriverType;
	}
	public void setWebDriverType(WebDriverType webDriverType) {
		this.webDriverType = webDriverType;
	}
	
	public static TestRunData from(TestRunTM testRun) {
		TestRunData testRunData = new TestRunData();
		SuiteTM suite = testRun.getSuiteParent();
		
		testRunData.setSuiteName(suite.getName());
		testRunData.setName(testRun.getName());
		testRunData.setResult(testRun.getResult());
		if (testRun.getBrowserStackMobil()!=null) {
			testRunData.setDevice(testRun.getBrowserStackMobil().getDevice());
		} else {
			testRunData.setDevice("");
		}
		
		Date inicio = testRun.getTestNgContext().getStartDate();
		Date fin = testRun.getTestNgContext().getEndDate();
		testRunData.setInicioDate(inicio);
		testRunData.setFinDate(fin);
		testRunData.setDurationMillis(fin.getTime() - inicio.getTime());
		testRunData.setNumberTestCases(testRun.getNumTestCases());
		testRunData.setWebDriverType(suite.getInputParams().getWebDriverType());
		
		return testRunData;
	}
}
