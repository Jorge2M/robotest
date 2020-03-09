package com.mng.testmaker.domain.suitetree;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Base64;
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

	private final int invocationCount;
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
		this.invocationCount = makeInvocationCount();
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
	
	private int makeInvocationCount() {
		int maxCount = 0;
		List<TestCaseTM> listTestCases = testRunParent.getListTestCases();
		for (TestCaseTM testCaseTM : listTestCases) {
			if (testCaseTM.getNameUnique().compareTo(getNameUnique())==0) {
				if (testCaseTM.getInvocationCount() > invocationCount) {
					maxCount = testCaseTM.getInvocationCount();
				}
			}
		}
		return maxCount + 1;
	}
	
	public int getInvocationCount() {
		return invocationCount;
	}
	
	public String getNameUnique() {
		String nameTest = result.getName() + getRefineDataName();
		if (getInvocationCount()>1) {
			nameTest+="(" + getInvocationCount() + ")";
		}
		return nameTest;
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
	
	public String getTestPathDirectory() {
		SuiteTM suiteParent = getSuiteParent();
		String suitePath = SuiteTM.getPathDirectory(
				suiteParent.getName(), 
				suiteParent.getIdExecution());
		
		TestRunTM testRunParent = getTestRunParent();
		String testPath = 
			suitePath + File.separator + 
			testRunParent.getName() + File.separator +
			getNameUnique();
		
		return testPath;
	}
	
	public static TestCaseTM getTestCase(ITestResult result) {
		for (SuiteTM suite : SuitesExecuted.getSuitesExecuted()) {
			for (TestRunTM testRun : suite.getListTestRuns()) {
				for (TestCaseTM testCase : testRun.getListTestCases()) {
					if (testCase!=null) {
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
	public void setListStep(List<StepTM> listSteps) {
		this.listSteps = listSteps;
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
		testCaseBean.setRefineDataName(getRefineDataName());
		testCaseBean.setNameUnique(getNameUnique());
		testCaseBean.setDescription(getResult().getMethod().getDescription());
		testCaseBean.setIndexInTestRun(getIndexInTestRun());
		testCaseBean.setResult(getStateResult());
		testCaseBean.setStatusTng(getResult().getStatus());
		
		Date inicio = new Date(getResult().getStartMillis());
		Date fin = new Date(getResult().getEndMillis());
		testCaseBean.setInicioDate(inicio);
		testCaseBean.setFinDate(fin); 
		testCaseBean.setDurationMillis(fin.getTime() - inicio.getTime());
		
		testCaseBean.setNumberSteps(getListStep().size());
		testCaseBean.setClassSignature(getResult().getInstanceName());
		testCaseBean.setThrowable(toStringB64(getResult().getThrowable()));
		
		List<StepTM> listStepBean = new ArrayList<>();
		for (StepTM step : getListStep()) {
			listStepBean.add(step);
		}
		testCaseBean.setListStep(listStepBean);
		
		return testCaseBean;
	}

	/** Write the object to a Base64 string. */
	private static String toStringB64(Serializable o) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream( baos );
			oos.writeObject( o );
			oos.close();
			return Base64.getEncoder().encodeToString(baos.toByteArray()); 
		}
		catch (Exception e) {
			return null;
		}
	}
}
