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
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.xmlprogram.commonsXML;


public class FilterTNGxmlTRun {
    static Logger pLogger = LogManager.getLogger(fmwkTest.log4jLogger);

    /**
     * Filter of a XmlTest of TestNG with the list of testCases. 
     * @param testCaseList contains the names of the methods to exec
     * 
     */
    public static void filterWithTCasesToExec(XmlTest testRun, String[] testCaseList, Channel channel, AppEcom appE) {
        try {
            if (testCaseList!=null && testCaseList.length>0 && "*".compareTo(testCaseList[0])!=0)
                includeOnlyTestCasesInXmlTest(testRun, testCaseList);
                
            removeDependenciesWithGroupsNotExecuted((XmlTestP80)testRun, channel, appE);
        }
        catch (ClassNotFoundException e) {
            pLogger.fatal("Problem filtering TestCases", e);
        }
    }
    
    /**
     * @return obtenemos la lista de TestCases a ejecutar. Se tendrán en cuenta los filtros que suponen:
     *  - Los includes de métodos específicos existentes en la XML programática asociados a clases
     *  - Los grupos a ejecutar definidos en la XML programática
     */
    public static ArrayList<TestMethod> getListOfTestAnnotationsOfTCasesToExecute(XmlTest testRun, Channel channel, AppEcom appE) {
        ArrayList<TestMethod> listTestToReturn = new ArrayList<>();
        ArrayList<TestMethod> listTests = getListOfTestAnnotationsOfTCases(testRun);
        
        //Filter by possible groups based in channel and app
        ArrayList<String> listOfPossibleGroups = commonsXML.getListOfPossibleGroups(channel, appE);
        for (TestMethod tmethod : listTests) {
            if (groupsContainsAnyGroup(tmethod.getAnnotationTest().groups(), listOfPossibleGroups))
                listTestToReturn.add(tmethod);
        }
        
        return listTestToReturn;
    }
    
    private static boolean groupsContainsAnyGroup(String[] groupsTest, ArrayList<String> possibleGroups) {
        for (int i=0; i<groupsTest.length; i++) {
            if (possibleGroups.contains(groupsTest[i]))
                return true;
        }
        
        return false;
    }
    
    /**
     * @return obtendremos una lista de TestCases teniendo en cuenta el filtro que suponen: 
     *   - Los includes de métodos específicos existentes en la XML programática asociados a clases
     */
    public static ArrayList<TestMethod> getListOfTestAnnotationsOfTCases(XmlTest testRun) {
        ArrayList<TestMethod> listOfAnnotationsOfTestCases = new ArrayList<>();
        try {
            for (XmlClass xmlClass : testRun.getClasses()) {
                ArrayList<Method> methodListToRun = getMethodsFromClassIncludedInTestRun(xmlClass);
                for (Method method : methodListToRun) {
                    ArrayList<Annotation> annotationsList = new ArrayList<>(Arrays.asList(method.getDeclaredAnnotations()));
                    for (Annotation annotation : annotationsList) {
                        if (annotation.annotationType()==org.testng.annotations.Test.class)
                            listOfAnnotationsOfTestCases.add(new TestMethod((Test)annotation, method));
                    }
                }
            }
        }
        catch (ClassNotFoundException e) {
            pLogger.fatal("Problem getting list of annotations of TestCases methods",  e);
        }
        
        return listOfAnnotationsOfTestCases;
    }    
    
    /**
     * @param testCaseList list of the only TestCases to include in TestRun for execution
     */
    private static void includeOnlyTestCasesInXmlTest(XmlTest testRun, String[] testCaseList) throws ClassNotFoundException {
        ArrayList<String> testCasesForInclude = new ArrayList<>(Arrays.asList(testCaseList));
        for (Iterator<XmlClass> iterator = testRun.getClasses().iterator(); iterator.hasNext(); ) {
            XmlClass xmlClass = iterator.next();
            includeOnlyTestCasesInXmlClass(xmlClass, testCasesForInclude);
            if (xmlClass.getIncludedMethods().size()==0)
                iterator.remove();
        }
    }
    
    /**
     * Remove of the dependencies with source or destination group without any test for execution
     */
    public static void removeDependenciesWithGroupsNotExecuted(XmlTestP80 testRun, Channel channel, AppEcom appE) {
        HashSet<String> groupsWithTestsToExec = getListOfGroupsWithTestCasesToExecute(testRun, channel, appE);
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
    
    private static void includeOnlyTestCasesInXmlClass(XmlClass xmlClass, ArrayList<String> testCasesForInclude) throws ClassNotFoundException {
        xmlClass.getIncludedMethods().clear();
        List<XmlInclude> includeMethods = new ArrayList<>();
        ArrayList<Method> methodList = new ArrayList<>(Arrays.asList(Class.forName(xmlClass.getName()).getMethods()));
        for (Method method : methodList) {
            ArrayList<Annotation> annotationsList = new ArrayList<>(Arrays.asList(method.getDeclaredAnnotations()));
            for (Annotation annotation : annotationsList) {
                if (annotation.annotationType()==org.testng.annotations.Test.class &&
                    testCasesContainsMethod(testCasesForInclude, method.getName()))
                    includeMethods.add(new XmlInclude(method.getName()));
            }
        }              
        
        xmlClass.setIncludedMethods(includeMethods);
    }
    
    private static HashSet<String> getListOfGroupsWithTestCasesToExecute(XmlTest testRun, Channel channel, AppEcom appE) {
        HashSet<String> listOfGropusWithTestCases = new HashSet<>();
        ArrayList<TestMethod> listOfTestAnnotations = getListOfTestAnnotationsOfTCasesToExecute(testRun, channel, appE);
        for (TestMethod testMethod : listOfTestAnnotations)
            listOfGropusWithTestCases.addAll(Arrays.asList(testMethod.getAnnotationTest().groups()));                        
        
        return listOfGropusWithTestCases;
    }
    
    private static ArrayList<Method> getMethodsFromClassIncludedInTestRun(XmlClass xmlClass) throws ClassNotFoundException {
        ArrayList<Method> methodListOfClass = new ArrayList<>(Arrays.asList(Class.forName(xmlClass.getName()).getMethods()));
        List<XmlInclude> includedMethods = xmlClass.getIncludedMethods();
        
        //Si no existe un include de método a nivel de Clase -> incluiremos todos los métodos de dicha clase 
        if (xmlClass.getIncludedMethods().isEmpty())
            return methodListOfClass;

        //Si existe un include -> incluiremos sólo los métodos asociados a includes
        ArrayList<Method> methodListFiltered = new ArrayList<>();
        for (Method methodOfClass : methodListOfClass) {
            if (listOfIncludesContains(includedMethods, methodOfClass))
                methodListFiltered.add(methodOfClass);
        }
        
        return methodListFiltered;
    }
    
    private static boolean listOfIncludesContains(List<XmlInclude> listOfIncludes, Method method) {
        for (XmlInclude include : listOfIncludes) {
            if (include.getName().compareTo(method.getName())==0)
                return true;
        }
        
        return false;
    }
    
    public static boolean testCasesContainsMethod(ArrayList<String> testCases, String methodName) {
        for (String testCase : testCases) {
            if (testCase.contains(methodName) ||  
                methodName.indexOf(getCodeFromTestCase(testCase))==0)
                return true;
        }
        
        return false;
    }
    
    private static String getCodeFromTestCase(String testCase) {
        int posUnderscore = testCase.indexOf("_");
        if (posUnderscore<0)
            return testCase;
        
        return testCase.substring(0, posUnderscore);
    }
}
