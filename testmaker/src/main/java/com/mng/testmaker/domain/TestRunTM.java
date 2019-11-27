package com.mng.testmaker.domain;

import java.util.ArrayList;
import java.util.List;

import org.testng.ITestContext;
import org.testng.TestRunner;
import org.testng.xml.XmlGroups;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import com.mng.testmaker.conf.State;
import com.mng.testmaker.service.webdriver.maker.brwstack.BrowserStackDesktop;
import com.mng.testmaker.service.webdriver.maker.brwstack.BrowserStackMobil;
import com.mng.testmaker.testreports.html.StorerErrorStep;

public class TestRunTM extends XmlTest {

	private static final long serialVersionUID = -4002416107477209626L;
	private StateExecution stateExecution = StateExecution.Started;
	private State state = State.Ok;
    public XmlGroups x_xmlGroupsVisible;
    private ITestContext testNgContext;
    private StorerErrorStep storerErrorStep = null;
    private List<TestCaseTM> listTestCases = new ArrayList<>();
	private BrowserStackDesktop browserStackDesktop = null;
	private BrowserStackMobil browserStackMobil = null;
	
    public TestRunTM(XmlSuite suite, int index) {
        super(suite, index);
    }

    public TestRunTM(XmlSuite suite) {
        super(suite);
    }
    
	public void end() {
		state = getStateFromTestCases();
		stateExecution = StateExecution.Stopped;
	}
	
	private State getStateFromTestCases() {
		State stateReturn = State.Ok;
		for (TestCaseTM testCase : getListTestCases()) {
			State stateTestCase = testCase.getStateResult();
			if (stateTestCase.isMoreCriticThan(stateReturn)) {
				stateReturn = stateTestCase;
			}
		}
		return stateReturn;
	}
	
	public StateExecution getStateExecution() {
		return stateExecution;
	}
	
	public void setStateExecution(StateExecution stateExecution) {
		this.stateExecution = stateExecution;
	}
	
	public State getResult() {
		return state;
	}
	
    public SuiteTM getSuiteParent() {
    	return (SuiteTM)getSuite();
    }
    
    public ITestContext getTestNgContext() {
    	return testNgContext;
    }
    
    public void setTestNgContext(ITestContext testNgContext) {
        this.testNgContext = testNgContext;
        String suiteDirectory = ((SuiteTM)getSuite()).getPathDirectory();
        setTestRunOutputDirectory(suiteDirectory);
  	
    }
    
    public StorerErrorStep getStorerErrorStep() {
    	return storerErrorStep;
    }
    
    public void setStorerErrorStep(StorerErrorStep storerErrorStep) {
    	this.storerErrorStep = storerErrorStep;
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
	
	public List<TestCaseTM> getListTestCases() {
		return listTestCases;
	}
	
	public int getNumTestCases() {
		return getListTestCases().size();
	}

	
	public void addTestCase(TestCaseTM testCase) {
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