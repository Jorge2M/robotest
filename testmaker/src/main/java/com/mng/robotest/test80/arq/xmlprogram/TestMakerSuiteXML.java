//TODO BrowserStack
package com.mng.robotest.test80.arq.xmlprogram;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlDependencies;
import org.testng.xml.XmlGroups;
import org.testng.xml.XmlRun;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;
import org.testng.xml.XmlSuite.ParallelMode;

import com.mng.robotest.test80.arq.utils.XmlTestP80;
import com.mng.robotest.test80.arq.utils.conf.AppTest;
import com.mng.robotest.test80.arq.utils.filter.FilterTestsSuiteXML;
import com.mng.robotest.test80.arq.utils.filter.TestMethod;
import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.data.TestMakerContext;

public abstract class TestMakerSuiteXML {

	private final TestMakerContext testMakerContext;
    private final FilterTestsSuiteXML filterSuiteXML;
    
    private List<XmlClass> listXMLclasses;
    private XmlDependencies depGroupsXML;
    private Map<String,String> parameters;
	private XmlSuite xmlSuite;
	
	protected TestMakerSuiteXML(InputDataTestMaker inputData) {
		this.testMakerContext = TestMakerContext.getNew(inputData);
		this.filterSuiteXML = FilterTestsSuiteXML.getNew(inputData.getDataFilter());
	}
	
    protected List<TestMethod> getListTests() {
    	generateXmlSuiteIfNotAvailable();
        XmlTest testRun = xmlSuite.getTests().get(0);
        return (
        	filterSuiteXML.getInitialTestCaseCandidatesToExecute(testRun)
        );
    }
    
    protected void run() { 
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
    
    private ArrayList<String> getListOfPossibleGroups() {
        ArrayList<String> listOfGroups = new ArrayList<>();
        Channel channel = testMakerContext.getInputData().getChannel();
        AppTest app = testMakerContext.getInputData().getApp();
        listOfGroups.add("Canal:all_App:all");
        listOfGroups.add("Canal:all_App:" + app);
        listOfGroups.add("Canal:" + channel + "_App:all");
        listOfGroups.add("Canal:" + channel + "_App:" + app);
        return listOfGroups;
    }
    
    private void generateXmlSuiteIfNotAvailable() {
    	if (xmlSuite==null) {
    		xmlSuite = createSuite();
    	}
    }
    
    private XmlSuite createSuite() {
        XmlSuite suite = new XmlSuite();
        suite.setFileName("");
        suite.setName(testMakerContext.getInputData().getNameSuite());
        suite.setListeners(createStandardListeners());
        suite.setParameters(parameters);
        suite.setParallel(ParallelMode.METHODS);
        suite.setThreadCount(3);
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
        for (String group : getListOfPossibleGroups()) {
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
