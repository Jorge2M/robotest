package com.mng.robotest.test80.arq.utils.filter;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlTest;

import com.mng.robotest.test80.arq.utils.conf.AppTest;
import com.mng.robotest.test80.arq.utils.filter.FilterTNGxmlTRun;
import com.mng.robotest.test80.arq.utils.filter.resources.TestNGxmlStub;
import com.mng.robotest.test80.arq.utils.filter.resources.TestNGxmlStub.TypeStubTest;
import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.arq.xmlprogram.ParamsBean;

public class TestFilterTNGxmlTRun {

	List<String> groupsVoid = null;
    public enum GroupDep {from, to}
    
    public enum AppEcom implements AppTest {
    	shop,
        outlet,
        votf;

    	@Override
    	public AppTest getValueOf(String application) {
    	    return (AppEcom.valueOf(application));
    	}
    }
    
    @Test
    public void getListOfTestMethodsFilteredByIncludesAndDesktopShop() {
        Channel channel = Channel.desktop;
        AppTest appE = AppEcom.shop;
        TestNGxmlStub testStub = makeTestStub(channel, appE, TypeStubTest.WithoutMethodsIncludedInClass);
        XmlTest testRun = getTestRun(testStub, channel, appE);
        
        //Code to test
        DataFilterTCases dFilter = new DataFilterTCases();
        dFilter.setAppE(appE);
        dFilter.setChannel(channel);
        List<TestMethod> testMethods = FilterTNGxmlTRun.getTestCasesToExecute(testRun, dFilter);
        
        assertEquals("The number of tests is 3", 3, testMethods.size());
        ArrayList<String> testMethodsNames = new ArrayList<>();
        testMethodsNames.add(testMethods.get(0).getMethod().getName());
        testMethodsNames.add(testMethods.get(1).getMethod().getName());
        testMethodsNames.add(testMethods.get(2).getMethod().getName());
        String method1 = "MIC001_Opciones_Mi_Cuenta";
        String method2 = "GPO001_Galeria_Camisas";
        String method3 = "BOR001_AddBolsaFromGaleria_NoReg";
        assertTrue("The method " + method1 + " is present", testMethodsNames.contains(method1));
        assertTrue("The method " + method2 + " is present", testMethodsNames.contains(method2));
        assertTrue("The method " + method3 + " is present", testMethodsNames.contains(method3));
    }

    
    @Test
    public void getListOfTestMethodsFilteredByIncludes() {
        Channel channel = Channel.desktop;
        AppEcom appE = AppEcom.shop;
        TestNGxmlStub testStub = makeTestStub(channel, appE, TypeStubTest.OnlyMethodGpo001includedInClass);
        XmlTest xmlTest = getTestRun(testStub, channel, appE);
        
        //Code to test
        List<TestMethod> testMethods = FilterTNGxmlTRun.getTestsCasesInXMLClasses(xmlTest);
        
        String descriptionExpected = "[Usuario registrado] Acceder a galería camisas. Filtros y ordenación. Seleccionar producto y color";
        assertEquals("The number of tests is 1", 1, testMethods.size());
        
        TestMethod methodIncluded = testMethods.get(0);
        String methodNameExpected = testStub.getMethodsIncludedInClass().get(0);
        assertEquals("The description is " + descriptionExpected, descriptionExpected, methodIncluded.getAnnotationTest().description());
        assertEquals("The method is " + methodNameExpected, methodNameExpected, methodIncluded.getMethod().getName());
    }
    
    @Test
    public void getListOfTestMethodsNotFilteredByIncludes() {
        Channel channel = Channel.desktop;
        AppEcom appE = AppEcom.shop;
        TestNGxmlStub testStub = makeTestStub(channel, appE, TypeStubTest.WithoutMethodsIncludedInClass);
        XmlTest testRun = getTestRun(testStub, channel, appE);
        
        //Code to test
        List<TestMethod> testMethods = FilterTNGxmlTRun.getTestsCasesInXMLClasses(testRun);
        
        assertEquals("The number of tests is 4", 4, testMethods.size());
    }
    
    @Test
    public void filterListOfTestCasesVoidWithInclude() {
        Channel channel = Channel.desktop;
        AppEcom appE = AppEcom.shop;
        TestNGxmlStub testStub = makeTestStub(channel, appE, TypeStubTest.OnlyMethodMic001includedInClass);
        XmlTest xmlTest = getTestRun(testStub, channel, appE);
        
        int initNumberXmlClasses = xmlTest.getXmlClasses().size();
        //int initNumberXmlDependencyGroups = xmlTest.getXmlDependencyGroups().size();
        int initNumberIncludeMethods = getIncludedMethodsCount(xmlTest.getXmlClasses());

        //Code to test
        DataFilterTCases dFilter = new DataFilterTCases();
        dFilter.setAppE(appE);
        dFilter.setChannel(channel);
        FilterTNGxmlTRun.filterTestCasesToExec(xmlTest, dFilter);
        
        //The xmlTest is not changed 
        assertEquals("The number of classes remais equal", initNumberXmlClasses, xmlTest.getXmlClasses().size());
        assertEquals("The number of dependencies-group is 0", 0, xmlTest.getXmlDependencyGroups().size());
        assertEquals("The number of methods included remais equal", initNumberIncludeMethods, getIncludedMethodsCount(xmlTest.getXmlClasses()));
    }
    
    @Test
    public void filterListOfTestCasesVoidWithoutInclude() {
        Channel channel = Channel.desktop;
        AppEcom appE = AppEcom.shop;
        TestNGxmlStub testStub = makeTestStub(channel, appE, TypeStubTest.WithoutMethodsIncludedInClass);
        XmlTest testRun = getTestRun(testStub, channel, appE);
        int initNumberXmlClasses = testRun.getXmlClasses().size();

        //Code to test
        DataFilterTCases dFilter = new DataFilterTCases();
        dFilter.setAppE(appE);
        dFilter.setChannel(channel);
        FilterTNGxmlTRun.filterTestCasesToExec(testRun, dFilter);
        
        assertEquals("The number of classes remais equal", initNumberXmlClasses, testRun.getXmlClasses().size());
        assertEquals("The number of dependencies-group is " + 1, 1, testRun.getXmlDependencyGroups().size());
        assertEquals("The number of methods is " + testStub.numberTestsCasesDesktopShop, testStub.numberTestsCasesDesktopShop, getIncludedMethodsCount(testRun.getXmlClasses()));
    }
    
    @Test
    public void filterIncludeNewTestCase() {
        Channel channel = Channel.desktop;
        AppEcom appE = AppEcom.shop;
        TestNGxmlStub testStub = makeTestStub(channel, appE, TypeStubTest.WithoutMethodsIncludedInClass);
        XmlTest testRun = getTestRun(testStub, channel, appE);
        String testExpectedToBeIncluded = testStub.methodGroupGaleriaProductoToInclude;
        List<String> testCaseList = Arrays.asList(testExpectedToBeIncluded);
        
        //Code to test
        DataFilterTCases dFilter = new DataFilterTCases();
        dFilter.setAppE(appE);
        dFilter.setChannel(channel);
        dFilter.setTestCasesFilter(testCaseList);
        FilterTNGxmlTRun.filterTestCasesToExec(testRun, dFilter);
        
        String textExpectedToNotBeIncluded = testStub.methodGroupMiCuentaToInclude;
        assertTrue("The new method " + testExpectedToBeIncluded + " is included", classIncludesMethod(testRun.getXmlClasses().get(0), testExpectedToBeIncluded));
        assertTrue("The old method " + textExpectedToNotBeIncluded + " disappear", !classIncludesMethod(testRun.getXmlClasses().get(0), textExpectedToNotBeIncluded));
        assertEquals("No remains dependencies-group", 0, testRun.getXmlDependencyGroups().size());
    }
    
    @Test
    public void filterIncludeTwoTestCaseByName_1groupRemains() {
    	Channel channel = Channel.desktop;
    	AppEcom app = AppEcom.shop;
        TestNGxmlStub testStub = makeTestStub(channel, app, TypeStubTest.WithTwoMethodsIncludedInClass);
        includeTwoTestsMethodsAndCheck(testStub, channel, app, true);
    }
    
    @Test
    public void filterIncludeTwoTestCasesByCode_1groupRemains() {
    	Channel channel = Channel.desktop;
    	AppEcom app = AppEcom.shop;
        TestNGxmlStub testStub = makeTestStub(channel, app, TypeStubTest.WithTwoMethodsIncludedInClass);
        includeTwoTestsMethodsAndCheck(testStub, channel, app, false);
    }
    
    private void includeTwoTestsMethodsAndCheck(TestNGxmlStub testStub, Channel channel, AppEcom app, boolean byName) {
    	List<String> testCaseList;
    	if (byName) {
            testCaseList = Arrays.asList(
            	testStub.methodGroupGaleriaProductoToInclude, 
            	testStub.methodGroupMiCuentaToInclude);
    	} else {
            testCaseList = Arrays.asList(
            	testStub.methodGroupGaleriaProductoToInclude.substring(0,6), 
            	testStub.methodGroupMiCuentaToInclude.substring(0,6));
    	}
    	
        XmlTest testRun = getTestRun(testStub, channel, app);
        
        //Code to test
        DataFilterTCases dFilter = new DataFilterTCases();
        dFilter.setAppE(app);
        dFilter.setChannel(channel);
        dFilter.setTestCasesFilter(testCaseList);
        FilterTNGxmlTRun.filterTestCasesToExec(testRun, dFilter);
        
        assertEquals("Remains only 1 dependency-group ", 1, testRun.getXmlDependencyGroups().size());
        assertTrue("Is present the group \"GaleriaProducto\"", isGroupInDependencies(testRun, "GaleriaProducto", GroupDep.to));
        assertTrue("Is present the dependency \"Micuenta\"", isGroupInDependencies(testRun, "Micuenta", GroupDep.from));
        assertTrue("The new method " + testStub.methodGroupGaleriaProductoToInclude + " is included", classIncludesMethod(testRun.getXmlClasses().get(0), testStub.methodGroupGaleriaProductoToInclude));
        assertTrue("The old method " + testStub.methodGroupMiCuentaToInclude + " remains included", classIncludesMethod(testRun.getXmlClasses().get(0), testStub.methodGroupMiCuentaToInclude));        
    }
    
    @Test
    public void includeTestCaseThatDoesnotExists() {
        Channel channel = Channel.desktop;
        AppEcom appE = AppEcom.shop;
        TestNGxmlStub testStub = makeTestStub(channel, appE, TypeStubTest.OnlyMethodThatDoesntExistInClass);
        XmlTest testRun = getTestRun(testStub, channel, appE);        
        List<String> testCaseList = Arrays.asList(testStub.methodThatDoesNotExistsInClass);
        
        //Code to test
        DataFilterTCases dFilter = new DataFilterTCases();
        dFilter.setAppE(appE);
        dFilter.setChannel(channel);
        dFilter.setTestCasesFilter(testCaseList);
        FilterTNGxmlTRun.filterTestCasesToExec(testRun, dFilter);
        
        assertEquals("No classes remains ", 0, testRun.getClasses().size());
    }
    
    private TestNGxmlStub makeTestStub(Channel channel, AppTest appE, TypeStubTest typeTest) {
        ParamsBean params = new ParamsBean(appE, null);
        params.setChannel(channel);
        return (TestNGxmlStub.newTest(typeTest));
    }
    
    private XmlTest getTestRun(TestNGxmlStub testStub, Channel channel, AppTest appE) {
        ParamsBean params = new ParamsBean(appE, null);
        params.setChannel(channel);
        return (testStub.createSuite(params).getTests().get(0));
    }
    
    private int getIncludedMethodsCount(List<XmlClass> listXmlClasses) {
        int count = 0;
        for (XmlClass xmlClass : listXmlClasses)
            count+=xmlClass.getIncludedMethods().size();
        
        return count;
    }
    
    private boolean classIncludesMethod(XmlClass xmlClass, String methodName) {
        for (XmlInclude methodIncluded : xmlClass.getIncludedMethods()) {
            if (methodIncluded.getName().compareTo(methodName)==0)
                return true;
        }
        
        return false;
    }
    
    private boolean isGroupInDependencies(XmlTest xmlTest, String group, GroupDep typeGroupDep) {
        Set<Entry<String, String>> xmlDependencyGroups = xmlTest.getXmlDependencyGroups().entrySet();
        for (Entry<String, String> dependency : xmlDependencyGroups) {
            switch (typeGroupDep) {
            case from:
                if (dependency.getValue().compareTo(group)==0)
                    return true;
                break;
            case to:
                if (dependency.getKey().compareTo(group)==0)
                    return true;
                break;
            default:
                break;
            }
        }
        
        return false;
    }
}
