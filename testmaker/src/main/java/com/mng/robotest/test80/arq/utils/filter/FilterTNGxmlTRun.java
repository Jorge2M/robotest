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

import com.mng.robotest.test80.arq.utils.XmlTestP80;
import com.mng.robotest.test80.arq.utils.conf.AppTest;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.arq.xmlprogram.CommonsXML;

public class FilterTNGxmlTRun {
    static Logger pLogger = LogManager.getLogger(fmwkTest.log4jLogger);

    /**
     * Filter of a XmlTest of TestNG with the list of testCases. 
     * @param testCaseList contains the names of the methods to exec
     * 
     */
    public static void filterTestCasesToExec(XmlTest testRun, DataFilterTCases dFilter) {
        try {
        	List<TestMethod> testCaseListToExec = getTestCasesToExecute(testRun, dFilter);
    		includeTestCasesInTestRun(testCaseListToExec, testRun);
    		removeDependenciesWithGroupsNotExecuted((XmlTestP80)testRun, dFilter);
        }
        catch (ClassNotFoundException e) {
            pLogger.fatal("Problem filtering TestCases", e);
        }
    }
    
    public static List<TestMethod> getInitialTestCaseCandidatesToExecute(XmlTest testRun, Channel channel, AppTest app) {
        List<TestMethod> listTestToReturn = new ArrayList<>();
        List<TestMethod> listTestsInXMLClasses = getTestsCasesInXMLClasses(testRun);
        List<String> groupsFromTestRun = testRun.getIncludedGroups();
        List<String> groupsAccordingChannelAndApp = CommonsXML.getListOfPossibleGroups(channel, app);
        for (TestMethod tmethod : listTestsInXMLClasses) {
        	if (groupsContainsAnyGroup(tmethod.getAnnotationTest().groups(), groupsAccordingChannelAndApp)) {
        		if (groupsContainsAnyGroup(tmethod.getAnnotationTest().groups(), groupsFromTestRun)) {
        			listTestToReturn.add(tmethod);
        		}
        	}
        }
        
        return listTestToReturn;
    }
    
    public static boolean methodInTestCaseList(String methodName, List<String> listTestCases) {
        for (String testCase : listTestCases) {
            if (testCase.compareTo(methodName)==0 ||  
                methodName.indexOf(getCodeFromTestCase(testCase))==0) {
                return true;
            }
        }
        
        return false;
    }
        
    static List<TestMethod> getTestCasesToExecute(XmlTest testRun, DataFilterTCases dFilter) {
    	List<TestMethod> testsCaseCandidates = getInitialTestCaseCandidatesToExecute(testRun, dFilter.getChannel(), dFilter.getAppE());
    	return getTestCasesFilteredWithParams(testsCaseCandidates, dFilter);
    }
    
    private static List<TestMethod> getTestCasesFilteredWithParams(List<TestMethod> listToFilter, DataFilterTCases dFilter) {
    	if (!dFilter.isSomeFilterActive()) {
    		return listToFilter;
    	}
    	List<TestMethod> listTestsFilreredByGroups = getListTestsFilteredByGroups(listToFilter, dFilter);
    	return (getListTestsFilteredByTestCases(listTestsFilreredByGroups, dFilter));
    }
    
    private static List<TestMethod> getListTestsFilteredByGroups(List<TestMethod> listToFilter, DataFilterTCases dFilter) {
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
    
    private static List<TestMethod> getListTestsFilteredByTestCases(List<TestMethod> listToFilter, DataFilterTCases dFilter) {
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
     
    private static boolean groupsContainsAnyGroup(String[] groupsTest, List<String> possibleGroups) {
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
    static List<TestMethod> getTestsCasesInXMLClasses(XmlTest testRun) {
        ArrayList<TestMethod> listOfAnnotationsOfTestCases = new ArrayList<>();
        try {
            for (XmlClass xmlClass : testRun.getClasses()) {
                ArrayList<Method> methodListToRun = getAllMethodsFilteredByInclude(xmlClass);
                for (Method method : methodListToRun) {
                    ArrayList<Annotation> annotationsList = new ArrayList<>(Arrays.asList(method.getDeclaredAnnotations()));
                    for (Annotation annotation : annotationsList) {
                        if (annotation.annotationType()==org.testng.annotations.Test.class) {
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
    
    private static void includeTestCasesInTestRun(List<TestMethod> testCasesToInclude, XmlTest testRun) 
    throws ClassNotFoundException {
        for (Iterator<XmlClass> iterator = testRun.getClasses().iterator(); iterator.hasNext(); ) {
            XmlClass xmlClass = iterator.next();
            includeOnlyTestCasesInXmlClass(xmlClass, testCasesToInclude);
            if (xmlClass.getIncludedMethods().size()==0) {
                iterator.remove();
            }
        }
    }
    
    /**
     * Remove of the dependencies with source or destination group without any test for execution
     */
    public static void removeDependenciesWithGroupsNotExecuted(XmlTestP80 testRun, DataFilterTCases dFilter) {
        HashSet<String> groupsWithTestsToExec = getListOfGroupsWithTestCasesToExecute(testRun, dFilter);
        XmlGroups xmlGroups = testRun.getGroups();
        if (xmlGroups!=null) {
            List<XmlDependencies> listXmlDep  = xmlGroups.getDependencies();
            if (listXmlDep.size() > 0) {
                for (Iterator<Entry<String,String>> itDependencyGroups = listXmlDep.get(0).getDependencies().entrySet().iterator(); itDependencyGroups.hasNext(); ) {
                    HashMap.Entry<String, String> entryDependency = itDependencyGroups.next();
                    if (!groupsWithTestsToExec.contains(entryDependency.getKey()) ||
                        !groupsWithTestsToExec.contains(entryDependency.getValue()))
                        itDependencyGroups.remove();
                }
            }
        }
        
        testRun.setGroups(xmlGroups);
    }
    
    private static void includeOnlyTestCasesInXmlClass(XmlClass xmlClass, List<TestMethod> testCasesToInclude) 
    throws ClassNotFoundException {
        xmlClass.getIncludedMethods().clear();
        List<XmlInclude> includeMethods = new ArrayList<>();
        ArrayList<Method> methodList = new ArrayList<>(Arrays.asList(Class.forName(xmlClass.getName()).getMethods()));
        for (Method method : methodList) {
            ArrayList<Annotation> annotationsList = new ArrayList<>(Arrays.asList(method.getDeclaredAnnotations()));
            for (Annotation annotation : annotationsList) {
                if (annotation.annotationType()==org.testng.annotations.Test.class &&
                	testMethodsContainsMethod(testCasesToInclude, method.getName()))
                    includeMethods.add(new XmlInclude(method.getName()));
            }
        }              
        
        xmlClass.setIncludedMethods(includeMethods);
    }
    
    private static HashSet<String> getListOfGroupsWithTestCasesToExecute(XmlTest testRun, DataFilterTCases dFilter) {
        HashSet<String> listOfGropusWithTestCases = new HashSet<>();
        List<TestMethod> listOfTestAnnotations = getTestCasesToExecute(testRun, dFilter);
        for (TestMethod testMethod : listOfTestAnnotations) {
            listOfGropusWithTestCases.addAll(Arrays.asList(testMethod.getAnnotationTest().groups()));                        
        }
        return listOfGropusWithTestCases;
    }
    
    static ArrayList<Method> getAllMethodsFilteredByInclude(XmlClass xmlClass) throws ClassNotFoundException {
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
    
    private static boolean listOfIncludesContains(List<XmlInclude> listOfIncludes, Method method) {
        for (XmlInclude include : listOfIncludes) {
            if (include.getName().compareTo(method.getName())==0) {
                return true;
            }
        }
        
        return false;
    }
    
    private static boolean someGroupInList(List<String> groupsMethod, List<String> listGroups) {
    	for (String groupMethod : groupsMethod) {
    		if (listGroups.contains(groupMethod)) {
    			return true;
    		}
    	}
    	
    	return false;
    }

    
    public static boolean testMethodsContainsMethod(List<TestMethod> testCasesToInclude, String methodName) {
        for (TestMethod testMethod : testCasesToInclude) {
            if (testMethod.getMethod().getName().compareTo(methodName)==0 ||  
                methodName.indexOf(getCodeFromTestCase(testMethod.getMethod().getName()))==0) {
                return true;
            }
        }
        
        return false;
    }
    
    private static String getCodeFromTestCase(String testCase) {
        int posUnderscore = testCase.indexOf("_");
        if (posUnderscore<0) {
            return testCase;
        }
        return testCase.substring(0, posUnderscore);
    }
}
