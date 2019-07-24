//TODO BrowserStack
package com.mng.robotest.test80.arq.xmlprogram;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.testng.TestNG;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;
import org.testng.xml.XmlSuite.ParallelMode;

import com.mng.robotest.test80.arq.utils.filter.FilterTestsSuiteXML;
import com.mng.robotest.test80.arq.utils.filter.TestMethod;
import com.mng.robotest.test80.data.TestMakerContext;

public abstract class SuiteMaker {

	private final TestMakerContext testMakerContext;
    private final FilterTestsSuiteXML filterSuiteXML;

    private Map<String,String> parameters;
    private List<TestRunMaker> listTestRuns = new ArrayList<>();
    private ParallelMode parallelMode = ParallelMode.METHODS;
    private int threadCount = 3;
	private SuiteTestMaker xmlSuite;
	
	protected SuiteMaker(InputDataTestMaker inputData) {
		this.testMakerContext = TestMakerContext.getNew(inputData);
		this.filterSuiteXML = FilterTestsSuiteXML.getNew(inputData.getDataFilter());
	}
	
    public List<TestMethod> getListTests() {
        XmlTest testRun = getTestRun();
        return (
        	filterSuiteXML.getInitialTestCaseCandidatesToExecute(testRun)
        );
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
    
    protected void addTestRun(TestRunMaker testRun) {
    	listTestRuns.add(testRun);
    }
    
    protected void setParallelMode(ParallelMode parallelMode) {
		this.parallelMode = parallelMode;
	}

	protected void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}
    
    private static List<String> createStandardListeners() {
        List<String> listeners = new ArrayList<>();
        listeners.add("com.mng.robotest.test80.arq.listeners.MyTransformer");
        listeners.add("com.mng.robotest.test80.arq.listeners.InvokeListener");
        listeners.add("com.mng.robotest.test80.arq.listeners.Reporter");
        return listeners;
    }

	private void generateXmlSuiteIfNotAvailable() {
    	if (xmlSuite==null) {
    		xmlSuite = createSuite();
    	}
    }
    
    private SuiteTestMaker createSuite() {
    	SuiteTestMaker suite = SuiteTestMaker.getNew(testMakerContext);
    	String suiteName = testMakerContext.getInputData().getNameSuite();
        suite.setFileName(suiteName + ".xml");
        suite.setName(suiteName);
        suite.setListeners(createStandardListeners());
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
