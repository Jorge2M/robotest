package com.mng.robotest.test80.arq.utils.filter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlDependencies;
import org.testng.xml.XmlGroups;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlTest;

import com.mng.robotest.test80.arq.utils.conf.AppTest;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.arq.xmlprogram.TestRunTestMaker;

public class FilterTestsSuiteXML {
	
    static Logger pLogger = LogManager.getLogger(fmwkTest.log4jLogger);
    
    private final DataFilterTCases dFilter;

    private FilterTestsSuiteXML(DataFilterTCases dFilter) {
    	this.dFilter = dFilter;
    }
    
    public static FilterTestsSuiteXML getNew(DataFilterTCases dFilter) {
    	return (new FilterTestsSuiteXML(dFilter));
    }
    
    public DataFilterTCases dFilter() {
    	return this.dFilter;
    }
    
    /**
     * Filter of a XmlTest of TestNG with the list of testCases. 
     * @param testCaseList contains the names of the methods to exec
     * 
     */
    public void filterTestCasesToExec(TestRunTestMaker testRun) {
        try {
        	List<TestMethod> testCaseListToExec = getTestCasesToExecute(testRun);
    		includeTestCasesInTestRun(testCaseListToExec, testRun);
    		removeDependenciesWithGroupsNotExecuted(testRun);
        }
        catch (ClassNotFoundException e) {
            pLogger.fatal("Problem filtering TestCases", e);
        }
    }
    
    public List<TestMethod> getInitialTestCaseCandidatesToExecute(XmlTest testRun) {
        List<TestMethod> listTestToReturn = new ArrayList<>();
        List<TestMethod> listTestsInXMLClasses = getTestsCasesInXMLClasses(testRun);
        List<String> groupsFromTestRun = testRun.getIncludedGroups();
        List<String> groupsAccordingChannelAndApp = getListChanelAndAppGroups();
        for (TestMethod tmethod : listTestsInXMLClasses) {
        	if (groupsContainsAnyGroup(tmethod.getAnnotationTest().groups(), groupsAccordingChannelAndApp)) {
        		if (groupsContainsAnyGroup(tmethod.getAnnotationTest().groups(), groupsFromTestRun)) {
        			listTestToReturn.add(tmethod);
        		}
        	}
        }
        
        return listTestToReturn;
    }
    
    public List<String> getListChanelAndAppGroups() {
        ArrayList<String> listOfGroups = new ArrayList<>();
        Channel channel = dFilter.getChannel();
        AppTest app = dFilter.getAppE();
        listOfGroups.add("Canal:all_App:all");
        listOfGroups.add("Canal:all_App:" + app);
        listOfGroups.add("Canal:" + channel + "_App:all");
        listOfGroups.add("Canal:" + channel + "_App:" + app);
        return listOfGroups;
    }
    
    public boolean methodInTestCaseList(String methodName, List<String> listTestCases) {
        for (String testCase : listTestCases) {
            if (testCase.compareTo(methodName)==0 ||  
                methodName.indexOf(getCodeFromTestCase(testCase))==0) {
                return true;
            }
        }
        
        return false;
    }
        
    public List<TestMethod> getTestCasesToExecute(XmlTest testRun) {
    	List<TestMethod> testsCaseCandidates = getInitialTestCaseCandidatesToExecute(testRun);
    	return getTestCasesFilteredWithParams(testsCaseCandidates);
    }
    
    private List<TestMethod> getTestCasesFilteredWithParams(List<TestMethod> listToFilter) {
    	if (!dFilter.isSomeFilterActive()) {
    		return listToFilter;
    	}
    	List<TestMethod> listTestsFilreredByGroups = getListTestsFilteredByGroups(listToFilter);
    	return (getListTestsFilteredByTestCases(listTestsFilreredByGroups));
    }
    
    private List<TestMethod> getListTestsFilteredByGroups(List<TestMethod> listToFilter) {
    	if (!dFilter.isActiveFilterByGroups()) {
    		return listToFilter;
    	}
    	
    	List<TestMethod> listTestsFiltered = new ArrayList<>();
    	for (TestMethod testMethod : listToFilter) {
    		List<String> groupsMethod = Arrays.asList(testMethod.getAnnotationTest().groups());
    		if (someGroupInList(groupsMethod, dFilter.getGroupsFilter())) {
    			listTestsFiltered.add(testMethod);
    		}
    	}
    	
    	return listTestsFiltered;
    }
    
    private List<TestMethod> getListTestsFilteredByTestCases(List<TestMethod> listToFilter) {
    	if (!dFilter.isActiveFilterByTestCases()) {
    		return listToFilter;
    	}
    	
    	List<TestMethod> listTestsFiltered = new ArrayList<>();
    	for (TestMethod testMethod : listToFilter) {
    		String methodName = testMethod.getMethod().getName();
    		if (methodInTestCaseList(methodName, dFilter.getTestCasesFilter())) {
    			listTestsFiltered.add(testMethod);
    		}
    	}
    	
    	return listTestsFiltered;
    }
     
    private boolean groupsContainsAnyGroup(String[] groupsTest, List<String> possibleGroups) {
    	if (possibleGroups==null) {
    		return true;
    	}
        for (int i=0; i<groupsTest.length; i++) {
            if (possibleGroups.contains(groupsTest[i])) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * @return obtendremos una lista de TestCases teniendo en cuenta el filtro que suponen: 
     *   - Los includes de métodos específicos existentes en la XML programática asociados a clases
     */
    protected List<TestMethod> getTestsCasesInXMLClasses(XmlTest testRun) {
        ArrayList<TestMethod> listOfAnnotationsOfTestCases = new ArrayList<>();
        try {
            for (XmlClass xmlClass : testRun.getClasses()) {
                ArrayList<Method> methodListToRun = getAllMethodsFilteredByInclude(xmlClass);
                for (Method method : methodListToRun) {
                    ArrayList<Annotation> annotationsList = new ArrayList<>(Arrays.asList(method.getDeclaredAnnotations()));
                    for (Annotation annotation : annotationsList) {
                        if (annotation.annotationType()==org.testng.annotations.Test.class &&
                        	((Test)annotation).enabled()) {
                            listOfAnnotationsOfTestCases.add(new TestMethod((Test)annotation, method));
                        }
                    }
                }
            }
        }
        catch (ClassNotFoundException e) {
            pLogger.fatal("Problem getting list of annotations of TestCases methods",  e);
        }
        
        return listOfAnnotationsOfTestCases;
    }    
    
    private void includeTestCasesInTestRun(List<TestMethod> testCasesToInclude, XmlTest testRun) 
    throws ClassNotFoundException {
        for (Iterator<XmlClass> iterator = testRun.getClasses().iterator(); iterator.hasNext(); ) {
            XmlClass xmlClass = iterator.next();
            if (!isFactoryWithoutTestsClass(xmlClass)) {
	            includeOnlyTestCasesInXmlClass(xmlClass, testCasesToInclude);
	            if (xmlClass.getIncludedMethods().size()==0) {
	                iterator.remove();
	            }
            }
        }
    }
    
    private boolean isFactoryWithoutTestsClass(XmlClass xmlClass) throws ClassNotFoundException {
    	if (isAnnotationInClass(org.testng.annotations.Test.class, xmlClass)) {
    		return false;
    	}
    	if (isAnnotationInClass(org.testng.annotations.Factory.class, xmlClass)) {
    		return true;
    	}
    	return false;
    }
    
    private boolean isAnnotationInClass(Class<? extends Annotation> annotationExpected, XmlClass xmlClass) 
    throws ClassNotFoundException {
        List<Method> listMethods = Arrays.asList(Class.forName(xmlClass.getName()).getMethods());
        for (Method method : listMethods) {
        	List<Annotation> listAnnotations = Arrays.asList(method.getAnnotations());
	        for (Annotation annotation : listAnnotations) {
	        	if (annotation.annotationType()==annotationExpected) {
	        		return true;
	        	}
	        }
        }
        return false;
    }
    
    /**
     * Remove of the dependencies with source or destination group without any test for execution
     */
    private void removeDependenciesWithGroupsNotExecuted(TestRunTestMaker testRun) {
        HashSet<String> groupsWithTestsToExec = getListOfGroupsWithTestCasesToExecute(testRun);
        XmlGroups xmlGroups = testRun.getGroups();
        if (xmlGroups!=null) {
            List<XmlDependencies> listXmlDep  = xmlGroups.getDependencies();
            if (listXmlDep.size() > 0) {
            	Iterator<Entry<String,String>> itDependencyGroups = listXmlDep.get(0).getDependencies().entrySet().iterator();
                while (itDependencyGroups.hasNext()) {
                    HashMap.Entry<String, String> entryDependency = itDependencyGroups.next();
                    if (!groupsWithTestsToExec.contains(entryDependency.getKey()) ||
                        !groupsWithTestsToExec.contains(entryDependency.getValue()))
                        itDependencyGroups.remove();
                }
            }
        }
        
        testRun.setGroups(xmlGroups);
    }
    
    private void includeOnlyTestCasesInXmlClass(XmlClass xmlClass, List<TestMethod> testCasesToInclude) 
    throws ClassNotFoundException {
        xmlClass.getIncludedMethods().clear();
        List<XmlInclude> includeMethods = new ArrayList<>();
        ArrayList<Method> methodList = new ArrayList<>(Arrays.asList(Class.forName(xmlClass.getName()).getMethods()));
        for (Method method : methodList) {
            ArrayList<Annotation> annotationsList = new ArrayList<>(Arrays.asList(method.getDeclaredAnnotations()));
            for (Annotation annotation : annotationsList) {
                if (annotation.annotationType()==org.testng.annotations.Test.class &&
                	((Test)annotation).enabled() &&
                	testMethodsContainsMethod(testCasesToInclude, method.getName()))
                    includeMethods.add(new XmlInclude(method.getName()));
            }
        }              
        
        xmlClass.setIncludedMethods(includeMethods);
    }
    
    private HashSet<String> getListOfGroupsWithTestCasesToExecute(XmlTest testRun) {
        HashSet<String> listOfGropusWithTestCases = new HashSet<>();
        List<TestMethod> listOfTestAnnotations = getTestCasesToExecute(testRun);
        for (TestMethod testMethod : listOfTestAnnotations) {
            listOfGropusWithTestCases.addAll(Arrays.asList(testMethod.getAnnotationTest().groups()));                        
        }
        return listOfGropusWithTestCases;
    }
    
    private ArrayList<Method> getAllMethodsFilteredByInclude(XmlClass xmlClass) throws ClassNotFoundException {
        ArrayList<Method> methodListOfClass = new ArrayList<>(Arrays.asList(Class.forName(xmlClass.getName()).getMethods()));
        List<XmlInclude> includedMethods = xmlClass.getIncludedMethods();
        if (xmlClass.getIncludedMethods().isEmpty()) {
            return methodListOfClass;
        }

        //Filter by Include-XML
        ArrayList<Method> methodListFiltered = new ArrayList<>();
        for (Method methodOfClass : methodListOfClass) {
            if (listOfIncludesContains(includedMethods, methodOfClass)) {
                methodListFiltered.add(methodOfClass);
            }
        }
        
        return methodListFiltered;
    }
    
    private boolean listOfIncludesContains(List<XmlInclude> listOfIncludes, Method method) {
        for (XmlInclude include : listOfIncludes) {
            if (include.getName().compareTo(method.getName())==0) {
                return true;
            }
        }
        
        return false;
    }
    
    private boolean someGroupInList(List<String> groupsMethod, List<String> listGroups) {
    	for (String groupMethod : groupsMethod) {
    		if (listGroups.contains(groupMethod)) {
    			return true;
    		}
    	}
    	
    	return false;
    }

    
    private boolean testMethodsContainsMethod(List<TestMethod> testCasesToInclude, String methodName) {
        for (TestMethod testMethod : testCasesToInclude) {
            if (testMethod.getMethod().getName().compareTo(methodName)==0 ||  
                methodName.indexOf(getCodeFromTestCase(testMethod.getMethod().getName()))==0) {
                return true;
            }
        }
        
        return false;
    }
    
    private String getCodeFromTestCase(String testCase) {
        int posUnderscore = testCase.indexOf("_");
        if (posUnderscore<0) {
            return testCase;
        }
        return testCase.substring(0, posUnderscore);
    }
}
