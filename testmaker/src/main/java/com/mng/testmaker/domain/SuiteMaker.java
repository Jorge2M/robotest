package com.mng.testmaker.domain;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.testng.xml.XmlTest;
import org.testng.xml.XmlSuite.ParallelMode;

import com.mng.testmaker.boundary.listeners.InvokeListener;
import com.mng.testmaker.boundary.listeners.MyTransformer;
import com.mng.testmaker.service.testreports.Reporter;
import com.mng.testmaker.utils.filter.FilterTestsSuiteXML;
import com.mng.testmaker.utils.filter.TestMethod;

public abstract class SuiteMaker {

	private final String idSuiteExecution;
	private final InputParamsTestMaker inputData;
    private final FilterTestsSuiteXML filterSuiteXML;

    private Map<String,String> parameters;
    private List<TestRunMaker> listTestRuns = new ArrayList<>();
    private ParallelMode parallelMode = ParallelMode.METHODS;
    private int threadCount = 3;
	private SuiteTestMaker suite;
	
	protected SuiteMaker(InputParamsTestMaker inputData) {
		this.idSuiteExecution = makeIdSuiteExecution();
		this.inputData = inputData;
		this.filterSuiteXML = FilterTestsSuiteXML.getNew(inputData.getDataFilter());
	}
	
    private static String makeIdSuiteExecution() {
        Calendar c1 = Calendar.getInstance();
        String timestamp = new SimpleDateFormat("yyMMdd_HHmmssSS").format(c1.getTime());
        return (timestamp);
    }
	
    public List<TestMethod> getListTests() {
        XmlTest testRun = getTestRun();
        return (
        	filterSuiteXML.getInitialTestCaseCandidatesToExecute(testRun)
        );
    }
    
    public SuiteTestMaker getSuite() {
    	generateXmlSuiteIfNotAvailable();
    	return this.suite;
    }
    
    public XmlTest getTestRun() {
    	generateXmlSuiteIfNotAvailable();
        return (suite.getTests().get(0));
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
    	if (suite==null) {
    		suite = createSuite();
    	}
    }
    
    private SuiteTestMaker createSuite() {
    	SuiteTestMaker suite = new SuiteTestMaker(idSuiteExecution, inputData);
    	String suiteName = inputData.getSuiteName();
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
    		testRun.createTestRun(suite, filterSuiteXML, inputData);
    	}
    }
}