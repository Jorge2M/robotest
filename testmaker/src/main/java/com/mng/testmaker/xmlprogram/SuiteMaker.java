package com.mng.testmaker.xmlprogram;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.testng.TestNG;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;
import org.testng.xml.XmlSuite.ParallelMode;

import com.mng.testmaker.access.InputParamsTestMaker;
import com.mng.testmaker.data.TestMakerContext;
import com.mng.testmaker.listeners.InvokeListener;
import com.mng.testmaker.listeners.MyTransformer;
import com.mng.testmaker.listeners.Reporter;
import com.mng.testmaker.utils.filter.FilterTestsSuiteXML;
import com.mng.testmaker.utils.filter.TestMethod;

public abstract class SuiteMaker {

	private final TestMakerContext testMakerContext;
    private final FilterTestsSuiteXML filterSuiteXML;

    private Map<String,String> parameters;
    private List<TestRunMaker> listTestRuns = new ArrayList<>();
    private ParallelMode parallelMode = ParallelMode.METHODS;
    private int threadCount = 3;
	private SuiteTestMaker xmlSuite;
	
	protected SuiteMaker(InputParamsTestMaker inputData) {
		this.testMakerContext = new TestMakerContext(inputData);
		this.filterSuiteXML = FilterTestsSuiteXML.getNew(inputData.getDataFilter());
	}
	
	public String getIdSuiteExecution() {
		return testMakerContext.getIdSuiteExecution();
	}
	
    public List<TestMethod> getListTests() {
        XmlTest testRun = getTestRun();
        return (
        	filterSuiteXML.getInitialTestCaseCandidatesToExecute(testRun)
        );
    }
    
    public SuiteTestMaker getSuiteTestMaker() {
    	return this.xmlSuite;
    }
    
    public XmlTest getTestRun() {
    	generateXmlSuiteIfNotAvailable();
        return (xmlSuite.getTests().get(0));
    }

    public void run() { 
    	generateXmlSuiteIfNotAvailable();
        List<XmlSuite> suites = new ArrayList<>();
        suites.add(xmlSuite);    
        TestNG tng = new TestNG();
        tng.setXmlSuites(suites);
        tng.setUseDefaultListeners(false);
        tng.run();
    }

    protected void setParameters(Map<String,String> parameters) {
    	this.parameters = parameters;
    }
    
    protected void addParameters(Map<String,String> parameters) {
    	if (this.parameters==null) {
    		setParameters(parameters);
    	} else {
    		this.parameters.putAll(parameters);
    	}
    }
    
    protected void addTestRun(TestRunMaker testRun) {
    	listTestRuns.add(testRun);
    }
    
    protected void addTestRuns(List<TestRunMaker> testRuns) {
    	listTestRuns.addAll(testRuns);
    }
    
    protected void setParallelMode(ParallelMode parallelMode) {
		this.parallelMode = parallelMode;
	}

	protected void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}
    
    private static List<Class<?>> createStandardListeners() {
        List<Class<?>> listeners = new ArrayList<>();
        listeners.add(MyTransformer.class);
        listeners.add(InvokeListener.class);
        listeners.add(Reporter.class);
        return listeners;
    }

	private void generateXmlSuiteIfNotAvailable() {
    	if (xmlSuite==null) {
    		xmlSuite = createSuite();
    	}
    }
    
    private SuiteTestMaker createSuite() {
    	SuiteTestMaker suite = new SuiteTestMaker(testMakerContext);
    	String suiteName = testMakerContext.getInputData().getSuiteName();
        suite.setFileName(suiteName + ".xml");
        suite.setName(suiteName);
        suite.setListenersClass(createStandardListeners());
        suite.setParameters(parameters);
        suite.setParallel(parallelMode);
        suite.setThreadCount(threadCount);
        createTestRuns(suite);
        return suite;
    }
    
    private void createTestRuns(SuiteTestMaker suite) {
    	for (TestRunMaker testRun : listTestRuns) {
    		testRun.createTestRun(suite, filterSuiteXML, testMakerContext.getInputData());
    	}
    }
}
