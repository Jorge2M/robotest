package com.mng.testmaker.domain;


import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.SkipException;

public class TestCaseTestMaker  {

	private final List<StepTestMaker> listSteps = new ArrayList<>();
	
	private StateRun state = StateRun.Started;
	private final SuiteTestMaker suiteParent;
	private final TestRunTestMaker testRunParent;
	private final WebDriver driver;
	private final ITestResult result;
	private final String threadName;
	private String specificDataFactory;

	public TestCaseTestMaker(ITestResult result) {
		this.testRunParent = (TestRunTestMaker)result.getTestContext().getCurrentXmlTest();
		this.suiteParent = (SuiteTestMaker)testRunParent.getSuite();
		//TODO no tengo claro si esta excepción tiene efecto
        if (suiteParent.getState()==StateRun.Stopping) {
            throw new SkipException("Received Signal for stop TestSuite");
        }
		this.result = result;
		this.driver = getWebDriverForTestCase();
		this.threadName = Thread.currentThread().getName();
	}
	
	public static TestCaseTestMaker getTestCaseInThread() {
		String threadName = Thread.currentThread().getName();
		for (SuiteTestMaker suite : SuitesExecuted.getSuitesExecuted()) {
			for (TestRunTestMaker testRun : suite.getListTestRuns()) {
				for (TestCaseTestMaker testCase : testRun.getListTestCases()) {
					if (testCase.getThreadName().compareTo(threadName)==0) {
						return testCase;
					}
				}
			}
		}
		return null;
	}
	
	public void addStep(StepTestMaker step) {
		listSteps.add(step);
	}
	
	public List<StepTestMaker> getListaSteps() {
		return this.listSteps;
	}
	
	public StepTestMaker getCurrentStep() {
		if (listSteps.size() > 0) {
			listSteps.get(listSteps.size() - 1);
		}
		return null;
	}
	
	public StepTestMaker getLastStepFinished() {
		StepTestMaker stepReturn = null;
		for (StepTestMaker step : listSteps) {
			if (step.getState()==StateRun.Finished) {
				stepReturn = step;
			}
		}
		return stepReturn;
	}
	
	public StateRun getState() {
		return this.state;
	}
	
	public void setState(StateRun state) {
		this.state = state;
	}
	
	private WebDriver getWebDriverForTestCase() {
		InputParamsTestMaker inputData = suiteParent.getInputData();
		return (
			suiteParent.getPoolWebDrivers().getWebDriver(
				inputData.getWebDriverType(), 
				inputData.getChannel(), 
				testRunParent));
	}
	
	public WebDriver getDriver() {
		return driver;
	}
	
	public String getThreadName() {
		return this.threadName;
	}
	
	public SuiteTestMaker getSuiteParent() {
		return suiteParent;
	}
	
	public TestRunTestMaker getTestRunParent() {
		return testRunParent;
	}
	
	public String getSpecificDataFactory() {
		return this.specificDataFactory;
	}
	
	public void setSpecificDataFactory(String specificDataFactory) {
		this.specificDataFactory = specificDataFactory;
	}
}
