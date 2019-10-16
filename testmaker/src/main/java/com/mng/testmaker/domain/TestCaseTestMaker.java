package com.mng.testmaker.domain;


import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.SkipException;

import com.mng.testmaker.utils.State;

public class TestCaseTestMaker  {

	private final List<StepTestMaker> listSteps = new ArrayList<>();
	
	private StateRun stateRun = StateRun.Started;
	private State state = State.Ok;
	private final SuiteTestMaker suiteParent;
	private final TestRunTestMaker testRunParent;
	private final WebDriver driver;
	private final ITestResult result;
	private final String threadName;
	private String refineDataName;

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
	
	public String getNameUnique() {
		return result.getName() + getRefineDataName();
	}
	
	public void end(State state) {
    	setStateRun(StateRun.Finished);
    	this.state = state;
	}
	
	public WebDriver getWebDriver() {
		return this.driver;
	}
	
	public void end() {
    	setStateRun(StateRun.Finished);
    	this.state = getStateFromSteps();
	}

	public State getStateResult() {
		return this.state;
	}
	
	public static TestCaseTestMaker getTestCase(ITestResult result) {
		for (SuiteTestMaker suite : SuitesExecuted.getSuitesExecuted()) {
			for (TestRunTestMaker testRun : suite.getListTestRuns()) {
				for (TestCaseTestMaker testCase : testRun.getListTestCases()) {
					if (testCase.getResult()==result) {
						return testCase;
					}
				}
			}
		}
		return null;
	}
	
	public static TestCaseTestMaker getTestCaseInExecution() {
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
	
	public ITestResult getResult() {
		return this.result;
	}
	
	public void addStep(StepTestMaker step) {
		listSteps.add(step);
	}
	
	public List<StepTestMaker> getStepsList() {
		return this.listSteps;
	}

	private State getStateFromSteps() {
		State stateReturn = State.Ok;
		for (StepTestMaker step : getStepsList()) {
			if (step.getResultSteps().isMoreCriticThan(stateReturn)) {
				stateReturn = step.getResultSteps();
			}
		}
		return stateReturn;
	}
	
	public StepTestMaker getCurrentStep() {
		if (listSteps.size() > 0) {
			return (listSteps.get(listSteps.size() - 1));
		}
		return null;
	}
	
//	public StepTestMaker getLastStepFinished() {
//		StepTestMaker stepReturn = null;
//		for (StepTestMaker step : listSteps) {
//			if (step.getState()==StateRun.Finished) {
//				stepReturn = step;
//			}
//		}
//		return stepReturn;
//	}
	
	public StateRun getStateRun() {
		return this.stateRun;
	}
	
	public void setStateRun(StateRun stateRun) {
		this.stateRun = stateRun;
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
	
	public String getRefineDataName() {
		return this.refineDataName;
	}
	
	public void setRefineDataName(String refineDataName) {
		this.refineDataName = refineDataName;
	}
}
