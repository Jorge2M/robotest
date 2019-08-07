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
import com.mng.robotest.test80.arq.utils.filter.resources.TestNGxmlStub;
import com.mng.robotest.test80.arq.utils.filter.resources.TestNGxmlStub.TypeStubTest;
import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.arq.utils.webdriver.maker.FactoryWebdriverMaker.TypeWebDriver;
import com.mng.robotest.test80.arq.xmlprogram.InputDataTestMaker;

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
    	
    	@Override 
    	public List<AppTest> getValues() {
    		return Arrays.asList(values());
    	}
    }
    
    @Test
    public void getListOfTestMethodsFilteredByIncludesAndDesktopShop() {
        InputDataTestMaker inputData = getInputDataBasic();
        TestNGxmlStub testStub = TestNGxmlStub.getNew(TypeStubTest.WithoutMethodsIncludedInClass, inputData);
        
        //Code to test
        testStub.getTestRun();
        
        assertEquals("The number of tests is 3", 3, testStub.getListTests().size());
        ArrayList<String> testMethodsNames = new ArrayList<>();
        testMethodsNames.add(testStub.getListTests().get(0).getMethod().getName());
        testMethodsNames.add(testStub.getListTests().get(1).getMethod().getName());
        testMethodsNames.add(testStub.getListTests().get(2).getMethod().getName());
        String method1 = "MIC001_Opciones_Mi_Cuenta";
        String method2 = "GPO001_Galeria_Camisas";
        String method3 = "BOR001_AddBolsaFromGaleria_NoReg";
        assertTrue("The method " + method1 + " is present", testMethodsNames.contains(method1));
        assertTrue("The method " + method2 + " is present", testMethodsNames.contains(method2));
        assertTrue("The method " + method3 + " is present", testMethodsNames.contains(method3));
    }

    @Test
    public void getListOfTestMethodsFilteredByIncludes() {
        InputDataTestMaker inputData = getInputDataBasic();
        TestNGxmlStub testStub = TestNGxmlStub.getNew(TypeStubTest.OnlyMethodGpo001includedInClass, inputData);
        
        //Code to test
        testStub.getTestRun();
        
        String descriptionExpected = "[Usuario registrado] Acceder a galería camisas. Filtros y ordenación. Seleccionar producto y color";
        assertEquals("The number of tests is 1", 1, testStub.getListTests().size());
        
        TestMethod methodIncluded = testStub.getListTests().get(0);
        String methodNameExpected = testStub.getMethodsIncludedInClass().get(0);
        assertEquals("The description is " + descriptionExpected, descriptionExpected, methodIncluded.getAnnotationTest().description());
        assertEquals("The method is " + methodNameExpected, methodNameExpected, methodIncluded.getMethod().getName());
    }
    
    @Test
    public void getListOfTestMethodsNotFilteredByIncludes() {
        InputDataTestMaker inputData = getInputDataBasic();
        TestNGxmlStub testStub = TestNGxmlStub.getNew(TypeStubTest.WithoutMethodsIncludedInClass, inputData);
        
        //Code to test
        testStub.getTestRun();
        
        assertEquals("The number of tests is 3", 3, testStub.getListTests().size());
    }
    
    @Test
    public void filterListOfTestCasesVoidWithInclude() {
        InputDataTestMaker inputData = getInputDataBasic();
        TestNGxmlStub testStub = TestNGxmlStub.getNew(TypeStubTest.OnlyMethodMic001includedInClass, inputData);
        
        //Code to test
        XmlTest testRun = testStub.getTestRun();
        
        //The xmlTest is not changed 
        assertEquals("The number of classes is 1", 1, testRun.getXmlClasses().size());
        assertEquals("The number of dependencies-group is 0", 0, testRun.getXmlDependencyGroups().size());
        assertEquals("The number of methods included is 1", 1, getIncludedMethodsCount(testRun.getXmlClasses()));
    }
    
    @Test
    public void filterListOfTestCasesVoidWithoutInclude() {
        InputDataTestMaker inputData = getInputDataBasic();
        TestNGxmlStub testStub = TestNGxmlStub.getNew(TypeStubTest.WithoutMethodsIncludedInClass, inputData);
        
        //Code to test
        XmlTest testRun = testStub.getTestRun();
        
        assertEquals("The number of dependencies-group is " + 1, 1, testRun.getXmlDependencyGroups().size());
        assertEquals("The number of methods is " + TestNGxmlStub.numberTestsCasesDesktopShop, TestNGxmlStub.numberTestsCasesDesktopShop, getIncludedMethodsCount(testRun.getXmlClasses()));
    }
    
    @Test
    public void filterIncludeNewTestCase() {
        InputDataTestMaker inputData = getInputDataBasic();
        String testExpectedToBeIncluded = TestNGxmlStub.methodGroupGaleriaProductoToInclude;
        inputData.setTestCasesFilter(Arrays.asList(testExpectedToBeIncluded));
        TestNGxmlStub testStub = TestNGxmlStub.getNew(TypeStubTest.WithoutMethodsIncludedInClass, inputData);
        
        //Code to test
        XmlTest testRun = testStub.getTestRun();
        
        String textExpectedToNotBeIncluded = TestNGxmlStub.methodGroupMiCuentaToInclude;
        assertTrue("The new method " + testExpectedToBeIncluded + " is included", classIncludesMethod(testRun.getXmlClasses().get(0), testExpectedToBeIncluded));
        assertTrue("The old method " + textExpectedToNotBeIncluded + " disappear", !classIncludesMethod(testRun.getXmlClasses().get(0), textExpectedToNotBeIncluded));
        assertEquals("No remains dependencies-group", 0, testRun.getXmlDependencyGroups().size());
    }
    
    @Test
    public void filterIncludeTwoTestCaseByName_1groupRemains() {
        InputDataTestMaker inputData = getInputDataBasic();
        inputData.setTestCasesFilter(
        	Arrays.asList(
        		TestNGxmlStub.methodGroupGaleriaProductoToInclude, 
        		TestNGxmlStub.methodGroupMiCuentaToInclude));
        
        TestNGxmlStub testStub = TestNGxmlStub.getNew(TypeStubTest.WithTwoMethodsIncludedInClass, inputData);
        
        checkAfterIncludeTwoMethods(testStub);
    }
    
    @Test
    public void filterIncludeTwoTestCasesByCode_1groupRemains() {
        InputDataTestMaker inputData = getInputDataBasic();
        inputData.setTestCasesFilter(
        	Arrays.asList(
        		TestNGxmlStub.methodGroupGaleriaProductoToInclude.substring(0,6), 
        		TestNGxmlStub.methodGroupMiCuentaToInclude.substring(0,6)));
        
        TestNGxmlStub testStub = TestNGxmlStub.getNew(TypeStubTest.WithTwoMethodsIncludedInClass, inputData);
        
        checkAfterIncludeTwoMethods(testStub);
    }
    
    private void checkAfterIncludeTwoMethods(TestNGxmlStub testStub) {
        //Code to test
        XmlTest testRun = testStub.getTestRun();
        
        assertEquals("Remains only 1 dependency-group ", 1, testRun.getXmlDependencyGroups().size());
        assertTrue("Is present the group \"GaleriaProducto\"", isGroupInDependencies(testRun, "GaleriaProducto", GroupDep.to));
        assertTrue("Is present the dependency \"Micuenta\"", isGroupInDependencies(testRun, "Micuenta", GroupDep.from));
        assertTrue("The new method " + TestNGxmlStub.methodGroupGaleriaProductoToInclude + " is included", classIncludesMethod(testRun.getXmlClasses().get(0), TestNGxmlStub.methodGroupGaleriaProductoToInclude));
        assertTrue("The old method " + TestNGxmlStub.methodGroupMiCuentaToInclude + " remains included", classIncludesMethod(testRun.getXmlClasses().get(0), TestNGxmlStub.methodGroupMiCuentaToInclude));        
    }
    
    @Test
    public void includeTestCaseThatDoesnotExists() {
        InputDataTestMaker inputData = getInputDataBasic();
        inputData.setTestCasesFilter(Arrays.asList(TestNGxmlStub.methodThatDoesNotExistsInClass));
        
        TestNGxmlStub testStub = TestNGxmlStub.getNew(TypeStubTest.OnlyMethodThatDoesntExistInClass, inputData);
        
        //Code to test
        XmlTest testRun = testStub.getTestRun();
        
        assertEquals("No classes remains ", 0, testRun.getClasses().size());
    }
    
    private InputDataTestMaker getInputDataBasic() {
	    return InputDataTestMaker.getNew(
			"Suite for Unit Tests", 
			Channel.desktop, 
			AppEcom.shop, 
			"https://shop.mango.com/preHome.faces",
			TypeWebDriver.chrome);
    }
    
    private int getIncludedMethodsCount(List<XmlClass> listXmlClasses) {
        int count = 0;
        for (XmlClass xmlClass : listXmlClasses) {
            count+=xmlClass.getIncludedMethods().size();
        }
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
