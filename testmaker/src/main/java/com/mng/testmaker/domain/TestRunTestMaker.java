package com.mng.testmaker.domain;

import java.util.ArrayList;
import java.util.List;

import org.testng.ITestContext;
import org.testng.TestRunner;
import org.testng.xml.XmlGroups;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import com.mng.testmaker.utils.State;
import com.mng.testmaker.utils.conf.StorerErrorStep;
import com.mng.testmaker.utils.webdriver.BrowserStackDesktop;
import com.mng.testmaker.utils.webdriver.BrowserStackMobil;

public class TestRunTestMaker extends XmlTest {

	private static final long serialVersionUID = -4002416107477209626L;
	private StateRun stateRun = StateRun.Started;
	private State state = State.Ok;
    public XmlGroups x_xmlGroupsVisible;
    private ITestContext testNgContext;
    private StorerErrorStep storerErrorStep = null;
    private List<TestCaseTestMaker> listTestCases = new ArrayList<>();
	private BrowserStackDesktop browserStackDesktop = null;
	private BrowserStackMobil browserStackMobil = null;
	
    public TestRunTestMaker(XmlSuite suite, int index) {
        super(suite, index);
    }

    public TestRunTestMaker(XmlSuite suite) {
        super(suite);
    }
    
	public void end() {
		state = getStateFromTestCases();
		stateRun = StateRun.Stopped;
	}
	
	private State getStateFromTestCases() {
		State stateReturn = State.Ok;
		for (TestCaseTestMaker testCase : getListTestCases()) {
			State stateTestCase = testCase.getStateResult();
			if (stateTestCase.isMoreCriticThan(stateReturn)) {
				stateReturn = stateTestCase;
			}
		}
		return stateReturn;
	}
    
	public StateRun getStateRun() {
		return stateRun;
	}
	
	public void setState(StateRun stateRun) {
		this.stateRun = stateRun;
	}
    
    public SuiteTestMaker getSuiteParent() {
    	return (SuiteTestMaker)getSuite();
    }
    
    public ITestContext getTestNgContext() {
    	return testNgContext;
    }
    
    public void setTestNgContext(ITestContext testNgContext) {
        this.testNgContext = testNgContext;
        String suiteDirectory = ((SuiteTestMaker)getSuite()).getDirectory();
        setTestRunOutputDirectory(suiteDirectory);
  	
    }
    
    public StorerErrorStep getStorerErrorStep() {
    	return storerErrorStep;
    }
    
    private void setTestRunOutputDirectory(String outputDirectory) {
        TestRunner runner = (TestRunner)testNgContext;
        runner.setOutputDirectory(outputDirectory);  
    }
    
    public XmlGroups getGroups() {
        return this.x_xmlGroupsVisible;
    }
    
    @Override
    public void setGroups(XmlGroups xmlGroups) {
        super.setGroups(xmlGroups);
        this.x_xmlGroupsVisible = xmlGroups;
    }
	
	public List<TestCaseTestMaker> getListTestCases() {
		return listTestCases;
	}
	
	public int getNumTestCases() {
		return getListTestCases().size();
	}
	
	public State getState() {
		return state;
	}
	
	public void addTestCase(TestCaseTestMaker testCase) {
		listTestCases.add(testCase);
	}
    
	public void setBrowserStackDesktop(BrowserStackDesktop browserStackDesktop) {
		this.browserStackDesktop = browserStackDesktop;
	}
	
	public void setBrowserStackMobil(BrowserStackMobil browserStackMobil) {
		this.browserStackMobil = browserStackMobil;
	}
	
	public BrowserStackDesktop getBrowserStackDesktop() {
		return this.browserStackDesktop;
	}
	
	public BrowserStackMobil getBrowserStackMobil() {
		return this.browserStackMobil;
	}
}
