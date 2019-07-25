package com.mng.robotest.test80.arq.xmlprogram;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.testng.xml.XmlClass;
import org.testng.xml.XmlDependencies;
import org.testng.xml.XmlGroups;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlRun;
import org.testng.xml.XmlSuite;

import com.mng.robotest.test80.arq.utils.filter.FilterTestsSuiteXML;
import com.mng.robotest.test80.arq.utils.webdriver.BrowserStackDesktop;
import com.mng.robotest.test80.arq.utils.webdriver.BrowserStackMobil;

public class TestRunMaker {

	private final String id;
    private final List<XmlClass> listXMLclasses;
    private final XmlDependencies depGroupsXML;
	private BrowserStackDesktop browserStackDesktop = null;
	private BrowserStackMobil browserStackMobil = null;
    
    private TestRunMaker(String id, List<String> listClases, Map<String,String> dependencies) {
    	this.id = id;
    	this.listXMLclasses = getClassesWithTests(listClases);
    	this.depGroupsXML = getDependencyGroups(dependencies);
    }
    
    public static TestRunMaker getNew(String id, List<String> listClases, Map<String,String> dependencies) {
    	return (new TestRunMaker(id, listClases, dependencies));
    }
    
    public static TestRunMaker getNew(String id, List<String> listClases) {
    	return (new TestRunMaker(id, listClases, null));
    }

    public void includeMethodsInClass(String pathClass, List<String> methodsToInclude) {
    	XmlClass xmlClass = getXmlClass(pathClass);
    	includeMethodsInClass(xmlClass, methodsToInclude);
    }
    
	public void setBrowserStackDesktop(BrowserStackDesktop browserStackDesktop) {
		this.browserStackDesktop = browserStackDesktop;
	}
	
	public void setBrowserStackMobil(BrowserStackMobil browserStackMobil) {
		this.browserStackMobil = browserStackMobil;
	}
    
    public TestRunTestMaker createTestRun(XmlSuite suite, FilterTestsSuiteXML filterSuiteXML, InputDataTestMaker inputData) {
    	TestRunTestMaker testRun = new TestRunTestMaker(suite);
        testRun.setName(getTestRunName(inputData));
        testRun.setPreserveOrder(Boolean.valueOf(true));
        testRun.setGroups(createGroups(filterSuiteXML));
        testRun.setXmlClasses(listXMLclasses);
        testRun.setBrowserStackDesktop(browserStackDesktop);
        testRun.setBrowserStackMobil(browserStackMobil);
        filterSuiteXML.filterTestCasesToExec(testRun); 
        return testRun;
    }
    
    private XmlGroups createGroups(FilterTestsSuiteXML filterSuiteXML) {
        XmlGroups groups = new XmlGroups();
        groups.setRun(createRun(filterSuiteXML));
        groups.setXmlDependencies(depGroupsXML);
        return groups;
    }   
    
    private XmlRun createRun(FilterTestsSuiteXML filterSuiteXML) {
        XmlRun run = new XmlRun();
        for (String group : filterSuiteXML.getListChanelAndAppGroups()) {
            run.onInclude(group);
        }
        return run;
    }
    
    private List<XmlClass> getClassesWithTests(List<String> listClases) {
    	List<XmlClass> listXMLclasses = new ArrayList<>();
    	for (String classe : listClases) {
    		listXMLclasses.add(new XmlClass(classe));
    	}
    	return listXMLclasses;
    }
    
    private XmlDependencies getDependencyGroups(Map<String,String> dependencies) {
    	XmlDependencies depGroupsXML = new XmlDependencies();
    	if (dependencies!=null) {
	    	for (Map.Entry<String,String> dependency : dependencies.entrySet()) {
	    		depGroupsXML.onGroup(dependency.getKey(), dependency.getValue());
	    	}
    	}
    	return depGroupsXML;
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
    
    private String getTestRunName(InputDataTestMaker inputData) {
        return (
        	id + "-" + 
        	inputData.getVersionSuite() + "-" + 
        	inputData.getApp() + "-" + 
        	inputData.getChannel() + "-" + 
        	inputData.getTypeWebDriver());
    } 
}
