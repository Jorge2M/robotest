//TODO BrowserStack
package com.mng.robotest.test80.arq.xmlprogram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlDependencies;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;
import org.testng.xml.XmlSuite.ParallelMode;

import com.mng.robotest.test80.TestMaker;
import com.mng.robotest.test80.arq.utils.filter.FilterTestsSuiteXML;
import com.mng.robotest.test80.arq.utils.filter.TestMethod;
import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.arq.utils.otras.Constantes;
import com.mng.robotest.test80.arq.utils.webdriver.maker.FactoryWebdriverMaker.TypeWebDriver;

public abstract class TestMakerSuiteXML {

	private final TestMaker testMaker;
    private final FilterTestsSuiteXML filterSuiteXML;
    
    private ParamsBean params;
    private List<XmlClass> listXMLclasses;
    private XmlDependencies depGroupsXML;
	private XmlSuite xmlSuite;
	
	protected TestMakerSuiteXML(DataFilter dataFilter, TestMaker testMaker) {
		this.testMaker = testMaker;
		this.filterSuiteXML = FilterTestsSuiteXML.getNew(dataFilter);
	}
	
	protected TestMaker getTestMaker() {
		return this.testMaker;
	}
	
    protected void setClassesWithTests(List<String> listClases) {
    	listXMLclasses = new ArrayList<>();
    	for (String classe : listClases) {
    		listXMLclasses.add(new XmlClass(classe));
    	}
    }
    
    /**
     * @return Map with pair: group <- group
     */
    protected void setDependencyGroups(Map<String,String> dependencies) {
    	depGroupsXML = new XmlDependencies();
    	for (Map.Entry<String,String> dependency : dependencies.entrySet()) {
    		depGroupsXML.onGroup(dependency.getKey(), dependency.getValue());
    	}
    }
    
    private static List<String> createStandardListeners() {
        List<String> listeners = new ArrayList<>();
        listeners.add("com.mng.robotest.test80.arq.listeners.MyTransformer");
        listeners.add("com.mng.robotest.test80.arq.listeners.InvokeListener");
        listeners.add("com.mng.robotest.test80.arq.listeners.Reporter");
        return listeners;
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
    
    public List<TestMethod> getListTests() {
    	generateXmlSuiteIfNotAvailable();
        XmlTest testRun = xmlSuite.getTests().get(0);
        return (
        	getInitialTestCaseCandidatesToExecute(testRun, dFilter.getChannel(), dFilter.getAppE())
        );
    }
    
    private void generateXmlSuiteIfNotAvailable() {
    	if (xmlSuite==null) {
    		,,,
    	}
    }
    
    private static XmlSuite createSuite(ParamsBean params) {
        XmlSuite suite = new XmlSuite();
        suite.setFileName("");
        suite.setName(params.getSuiteName());
        suite.setListeners(createStandardListeners());

        Map<String, String> parametersSuite = new HashMap<>();
        createCommonParamsSuite(parametersSuite, params);
        suite.setParameters(parametersSuite);
        
        //En caso <> BrowserStack paralelizaremos a nivel de los Métodos (casos de prueba)
        suite.setParallel(ParallelMode.METHODS);
        suite.setThreadCount(3);
        
        //Sólo ejecutamos 1 TestRun
        createTestRunFilteredWithTestCases(params, suite, CommonsXML.getDescriptionTestRun(params), params.getGroups(), params.getTestCases());
        
        return suite;
    }
    
    private static XmlTest createTestRunFilteredWithTestCases(ParamsBean params, XmlSuite suite, String testRunName, String[] groups, String[] testCaseList) {
        XmlTest testRun = CommonsXML.createTestRun(suite, testRunName, testCaseList);
        testRun.setGroups(createGroups(params));
        testRun.setXmlClasses(createClasses(params));
        //filterTestCasesToExec(testRun);
        return testRun;
    }    
}
