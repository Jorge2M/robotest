package com.mng.testmaker.domain.suitetree;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;

import com.mng.testmaker.conf.State;
import com.mng.testmaker.domain.InputParamsTM;
import com.mng.testmaker.domain.StateExecution;
import com.mng.testmaker.domain.SuitesExecuted;

public class TestCaseTM  {

	private List<StepTM> listSteps = new ArrayList<>();
	private StateExecution stateRun = StateExecution.Started;
	private State state = State.Ok;
	private final SuiteTM suiteParent;
	private final TestRunTM testRunParent;
	private final ITestResult result;
	private final long threadId;
	private String refineDataName = "";
	private WebDriver driver;

	public TestCaseTM(ITestResult result) {
		this.testRunParent = (TestRunTM)result.getTestContext().getCurrentXmlTest();
		this.suiteParent = (SuiteTM)testRunParent.getSuite();
		this.result = result;
		this.threadId = Thread.currentThread().getId();
//		if (suiteParent.getStateExecution()!=StateExecution.Stopping) {
//			this.driver = getWebDriverForTestCase();
//		} else {
//			this.driver = null;
//		}
	}
	
	public void makeWebDriver() {
		this.driver = getWebDriverForTestCase();
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
    	setStateRun(StateExecution.Finished);
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
						System.out.println("Compare: " + testCase.getResult().hashCode() + " / " + result.hashCode());
						if (testCase.getResult().equals(result)) {
							return testCase;
						}
					}
				}
			}
		}
		return null;
	}
	
	public static TestCaseTM getTestCaseInExecution() {
		Long threadId = Thread.currentThread().getId();
		for (SuiteTM suite : SuitesExecuted.getSuitesExecuted()) {
			for (TestRunTM testRun : suite.getListTestRuns()) {
				for (TestCaseTM testCase : testRun.getListTestCases()) {
					if (testCase.getThreadId().compareTo(threadId)==0 &&
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
	
	public List<StepTM> getListStep() {
		return this.listSteps;
	}

	private State getStateFromSteps() {
		State stateReturn = State.Ok;
		for (StepTM step : getListStep()) {
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
	
	public Long getThreadId() {
		return this.threadId;
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
	
	public TestCaseBean getTestCaseBean() {
		TestCaseBean testCaseBean = new TestCaseBean();
		SuiteTM suite = getSuiteParent();
		
		testCaseBean.setIdExecSuite(suite.getIdExecution());
		testCaseBean.setSuiteName(suite.getName());
		testCaseBean.setTestRunName(getTestRunParent().getName());
		testCaseBean.setName(getNameUnique());
		testCaseBean.setNameUnique(getNameUnique());
		testCaseBean.setDescription(getResult().getMethod().getDescription());
		testCaseBean.setIndexInTestRun(getIndexInTestRun());
		testCaseBean.setResult(getStateResult());
		
		Date inicio = new Date(getResult().getStartMillis());
		Date fin = new Date(getResult().getEndMillis());
		testCaseBean.setInicioDate(inicio);
		testCaseBean.setFinDate(fin); 
		testCaseBean.setDurationMillis(fin.getTime() - inicio.getTime());
		
		testCaseBean.setNumberSteps(getListStep().size());
		testCaseBean.setClassSignature(getResult().getInstanceName());
		
		List<StepTM> listStepBean = new ArrayList<>();
		for (StepTM step : getListStep()) {
			listStepBean.add(step);
		}
		testCaseBean.setListStep(listStepBean);
		
		return testCaseBean;
	}
}
