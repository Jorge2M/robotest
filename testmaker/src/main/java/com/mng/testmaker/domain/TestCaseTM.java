package com.mng.testmaker.domain;


import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;

import com.mng.testmaker.conf.State;

public class TestCaseTM  {

	private final List<StepTM> listSteps = new ArrayList<>();
	
	private StateExecution stateRun = StateExecution.Started;
	private State state = State.Ok;
	private final SuiteTM suiteParent;
	private final TestRunTM testRunParent;
	private final WebDriver driver;
	private final ITestResult result;
	private final String threadName;
	private String refineDataName = "";

	public TestCaseTM(ITestResult result) {
		this.testRunParent = (TestRunTM)result.getTestContext().getCurrentXmlTest();
		this.suiteParent = (SuiteTM)testRunParent.getSuite();
		this.result = result;
		this.threadName = Thread.currentThread().getName();
        if (suiteParent.getStateExecution()!=StateExecution.Stopping) {
        	this.driver = getWebDriverForTestCase();
        } else {
        	this.driver = null;
        }
	}
	
	public String getNameUnique() {
		return result.getName() + getRefineDataName();
	}
	
	public void end(State state) {
    	stopTest();
    	this.state = state;
	}
	
	public void end() {
		stopTest();
    	this.state = getStateFromSteps();
	}
	
	private void stopTest() {
    	setStateRun(StateExecution.Finished_Normally);
    	suiteParent.getPoolWebDrivers().quitWebDriver(driver, testRunParent);
	}
	
	public int getIndexInTestRun() {
		List<TestCaseTM> listTestCases = testRunParent.getListTestCases();
		for (int i=0; i<listTestCases.size(); i++) {
			if (this==listTestCases.get(i)) {
				return i;
			}
		}
		return -1;
	}
	
	public WebDriver getWebDriver() {
		return this.driver;
	}

	public State getStateResult() {
		return this.state;
	}
	
	public static TestCaseTM getTestCase(ITestResult result) {
		for (SuiteTM suite : SuitesExecuted.getSuitesExecuted()) {
			for (TestRunTM testRun : suite.getListTestRuns()) {
				for (TestCaseTM testCase : testRun.getListTestCases()) {
					if (testCase!=null) {
						if (testCase.getResult()==result) {
							return testCase;
						}
					}
				}
			}
		}
		return null;
	}
	
	public static TestCaseTM getTestCaseInExecution() {
		String threadName = Thread.currentThread().getName();
		for (SuiteTM suite : SuitesExecuted.getSuitesExecuted()) {
			for (TestRunTM testRun : suite.getListTestRuns()) {
				for (TestCaseTM testCase : testRun.getListTestCases()) {
					if (testCase.getThreadName().compareTo(threadName)==0 &&
						testCase.getStateRun()==StateExecution.Started) {
						return testCase;
					}
				}
			}
		}
		return null;
	}
	
	public ITestResult getResult() {
		return this.result;
	}
	
	public void addStep(StepTM step) {
		listSteps.add(step);
	}
	
	public List<StepTM> getStepsList() {
		return this.listSteps;
	}

	private State getStateFromSteps() {
		State stateReturn = State.Ok;
		for (StepTM step : getStepsList()) {
			if (step.getResultSteps().isMoreCriticThan(stateReturn)) {
				stateReturn = step.getResultSteps();
			}
		}
		return stateReturn;
	}
	
	public StepTM getCurrentStepInExecution() {
		StepTM stepReturn = null;
		for (StepTM step : listSteps) {
			if (step.getState()==StateExecution.Started) {
				stepReturn = step;
			}
		}
		return stepReturn;
	}
	
	public StepTM getLastStep() {
		if (listSteps.size() > 0) {
			return (listSteps.get(listSteps.size() - 1));
		}
		return null;
	}
	
	public boolean isLastStep(StepTM step) {
		return (step==getLastStep());
	}
	
	public StateExecution getStateRun() {
		return this.stateRun;
	}
	
	public void setStateRun(StateExecution stateRun) {
		this.stateRun = stateRun;
	}
	
	private WebDriver getWebDriverForTestCase() {
		InputParamsTM inputData = suiteParent.getInputParams();
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
	
	public SuiteTM getSuiteParent() {
		return suiteParent;
	}
	
	public InputParamsTM getInputParamsSuite() {
		return getSuiteParent().getInputParams();
	}
	
	public TestRunTM getTestRunParent() {
		return testRunParent;
	}
	
	public ITestContext getTestRunContext() {
		return testRunParent.getTestNgContext();
	}
	
	public String getRefineDataName() {
		return this.refineDataName;
	}
	
	public void setRefineDataName(String refineDataName) {
		this.refineDataName = refineDataName;
	}
}
