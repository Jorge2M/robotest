//TODO BrowserStack
package com.mng.robotest.test80.arq.xmlprogram;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlDependencies;
import org.testng.xml.XmlGroups;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlRun;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;
import org.testng.xml.XmlSuite.ParallelMode;

import com.mng.robotest.test80.arq.utils.XmlTestP80;
import com.mng.robotest.test80.arq.utils.filter.FilterTestsSuiteXML;
import com.mng.robotest.test80.arq.utils.filter.TestMethod;
import com.mng.robotest.test80.data.TestMakerContext;

public abstract class TestMakerSuite {

	private final TestMakerContext testMakerContext;
    private final FilterTestsSuiteXML filterSuiteXML;

    private List<XmlClass> listXMLclasses;
    private XmlDependencies depGroupsXML;
    private Map<String,String> parameters;
    private ParallelMode parallelMode = ParallelMode.METHODS;
    private int threadCount = 3;
	private SuiteTestMaker xmlSuite;
	
	protected TestMakerSuite(InputDataTestMaker inputData) {
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
	
    protected void setClassesWithTests(List<String> listClases) {
    	listXMLclasses = new ArrayList<>();
    	for (String classe : listClases) {
    		listXMLclasses.add(new XmlClass(classe));
    	}
    }
    
    protected void includeMethodsInClass(String pathClass, List<String> methodsToInclude) {
    	XmlClass xmlClass = getXmlClass(pathClass);
    	includeMethodsInClass(xmlClass, methodsToInclude);
    }
    
    private XmlClass getXmlClass(String pathClass) {
    	for (XmlClass xmlClass : listXMLclasses) {
    		if (xmlClass.getName().compareTo(pathClass)==0) {
    			return xmlClass;
    		}
    	}
    	return null;
    }
    
    private void includeMethodsInClass(XmlClass xmlClass, List<String> methodsToInclude) {
        List<XmlInclude> includeMethods = new ArrayList<>();
        if (methodsToInclude!=null) {
        	for (String method : methodsToInclude) {
        		includeMethods.add(new XmlInclude(method));
        	}
        }
        xmlClass.setIncludedMethods(includeMethods);
    }
    
    protected void setDependencyGroups(Map<String,String> dependencies) {
    	depGroupsXML = new XmlDependencies();
    	for (Map.Entry<String,String> dependency : dependencies.entrySet()) {
    		depGroupsXML.onGroup(dependency.getKey(), dependency.getValue());
    	}
    }
    
    protected void setParameters(Map<String,String> parameters) {
    	this.parameters = parameters;
    }
    
    private static List<String> createStandardListeners() {
        List<String> listeners = new ArrayList<>();
        listeners.add("com.mng.robotest.test80.arq.listeners.MyTransformer");
        listeners.add("com.mng.robotest.test80.arq.listeners.InvokeListener");
        listeners.add("com.mng.robotest.test80.arq.listeners.Reporter");
        return listeners;
    }

    
    public void setParallelMode(ParallelMode parallelMode) {
		this.parallelMode = parallelMode;
	}

	public void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
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
        createTestRun(suite);
        return suite;
    }
    
    private XmlTest createTestRun(XmlSuite suite) {
        XmlTest testRun = new XmlTestP80(suite);
        testRun.setName(getTestRunName());
        testRun.setPreserveOrder(Boolean.valueOf(true));
        testRun.setGroups(createGroups());
        testRun.setXmlClasses(listXMLclasses);
        filterSuiteXML.filterTestCasesToExec(testRun);
        return testRun;
    }
    
    private XmlGroups createGroups() {
        XmlGroups groups = new XmlGroups();
        groups.setRun(createRun());
        groups.setXmlDependencies(depGroupsXML);
        return groups;
    }   
    
    private XmlRun createRun() {
        XmlRun run = new XmlRun();
        for (String group : filterSuiteXML.getListChanelAndAppGroups()) {
            run.onInclude(group);
        }
        return run;
    }
    
    private String getTestRunName() {
    	InputDataTestMaker inputData = testMakerContext.getInputData();
        return (
        	inputData.getVersionSuite() + "-" + 
        	inputData.getApp() + "-" + 
        	inputData.getChannel() + "-" + 
        	inputData.getTypeWebDriver());
    } 
}
