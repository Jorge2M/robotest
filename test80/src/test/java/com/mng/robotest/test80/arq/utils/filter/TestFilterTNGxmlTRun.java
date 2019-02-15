package com.mng.robotest.test80.arq.utils.filter;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import com.mng.robotest.test80.ParamsBean;
import com.mng.robotest.test80.arq.utils.filter.FilterTNGxmlTRun;
import com.mng.robotest.test80.arq.utils.filter.resources.TestNGxmlStub;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;

public class TestFilterTNGxmlTRun {

    TestNGxmlStub testNGxml = new TestNGxmlStub();
    public enum GroupDep {from, to}
    
    @Test
    public void getListOfTestMethodsFilteredByIncludesAndDesktopShop() {
        Channel channel = Channel.desktop;
        AppEcom appE = AppEcom.shop;
        XmlTest xmlTest = makeXmlTest(channel, appE, "V1"/*not filter methods with includes associated to class*/);
        
        //Code to test
        ArrayList<TestMethod> testMethods = FilterTNGxmlTRun.getListOfTestAnnotationsOfTCasesToExecute(xmlTest, channel, appE);
        
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
        XmlTest xmlTest = makeXmlTest(channel, appE, "V2"/*add include*/);
        
        //Code to test
        ArrayList<TestMethod> testMethods = FilterTNGxmlTRun.getListOfTestAnnotationsOfTCases(xmlTest);
        
        String descriptionExpected = "[Usuario registrado] Acceder a galería camisas. Filtros y ordenación. Seleccionar producto y color";
        assertEquals("The number of tests is 1", 1, testMethods.size());
        assertEquals("The description is " + this.testNGxml.methodGroupGaleriaProductoIncluded, descriptionExpected, testMethods.get(0).getAnnotationTest().description());
        assertEquals("The method is " + this.testNGxml.methodGroupGaleriaProductoIncluded, this.testNGxml.methodGroupGaleriaProductoIncluded, testMethods.get(0).getMethod().getName());
    }
    
    @Test
    public void getListOfTestMethodsNotFilteredByIncludes() {
        Channel channel = Channel.desktop;
        AppEcom appE = AppEcom.shop;
        XmlTest xmlTest = makeXmlTest(channel, appE, "V1"/*not filter methods with includes associated to class*/);
        
        //Code to test
        ArrayList<TestMethod> testMethods = FilterTNGxmlTRun.getListOfTestAnnotationsOfTCases(xmlTest);
        
        assertEquals("The number of tests is 4", 4, testMethods.size());
    }
    
    @Test
    public void filterListOfTestCasesVoidWithInclude() {
        Channel channel = Channel.desktop;
        AppEcom appE = AppEcom.shop;
        XmlTest xmlTest = makeXmlTest(channel, appE, "V2"/*add include*/);
        String[] voidTestCaseList = {};
        int initNumberXmlClasses = xmlTest.getXmlClasses().size();
        //int initNumberXmlDependencyGroups = xmlTest.getXmlDependencyGroups().size();
        int initNumberIncludeMethods = getIncludedMethodsCount(xmlTest.getXmlClasses());

        //Code to test
        FilterTNGxmlTRun.filterWithTCasesToExec(xmlTest, voidTestCaseList, channel, appE);
        
        //The xmlTest is not changed 
        assertEquals("The number of classes remais equal", initNumberXmlClasses, xmlTest.getXmlClasses().size());
        assertEquals("The number of dependencies-group is 0", 0, xmlTest.getXmlDependencyGroups().size());
        assertEquals("The number of methods included remais equal", initNumberIncludeMethods, getIncludedMethodsCount(xmlTest.getXmlClasses()));
    }
    
    @Test
    public void filterListOfTestCasesVoidWithoutInclude() {
        Channel channel = Channel.desktop;
        AppEcom appE = AppEcom.shop;
        XmlTest xmlTest = makeXmlTest(channel, appE, "V1"/*without includes*/);
        String[] voidTestCaseList = {};
        int initNumberXmlClasses = xmlTest.getXmlClasses().size();
        int initNumberIncludeMethods = getIncludedMethodsCount(xmlTest.getXmlClasses());

        //Code to test
        FilterTNGxmlTRun.filterWithTCasesToExec(xmlTest, voidTestCaseList, channel, appE);
        
        //The xmlTest is not changed 
        assertEquals("The number of classes remais equal", initNumberXmlClasses, xmlTest.getXmlClasses().size());
        
        //The list of groups of @Tests in ClassWithTCasesStub is "MiCuenta", "GaleriaProducto" and "Bolsa" -> Only survives the dependency "GaleriaProducto <- Micuenta"
        assertEquals("The number of dependencies-group is " + 1, 1, xmlTest.getXmlDependencyGroups().size());
        
        assertEquals("The number of methods included remais equal", initNumberIncludeMethods, getIncludedMethodsCount(xmlTest.getXmlClasses()));
    }
    
    @Test
    public void filterIncludeNewTestCase() {
        Channel channel = Channel.desktop;
        AppEcom appE = AppEcom.shop;
        XmlTest xmlTest = makeXmlTest(channel, appE, "V2"/*add include*/);
        String[] testCaseList = {this.testNGxml.methodGroupMiCuentaNotIncluded};
        
        //Code to test
        FilterTNGxmlTRun.filterWithTCasesToExec(xmlTest, testCaseList, channel, appE);
        
        assertTrue("The new method " + this.testNGxml.methodGroupMiCuentaNotIncluded + " is included", classIncludesMethod(xmlTest.getXmlClasses().get(0), this.testNGxml.methodGroupMiCuentaNotIncluded));
        assertTrue("The old method " + this.testNGxml.methodGroupGaleriaProductoIncluded + " disappear", !classIncludesMethod(xmlTest.getXmlClasses().get(0), this.testNGxml.methodGroupGaleriaProductoIncluded));
        assertEquals("No remains dependencies-group", 0, xmlTest.getXmlDependencyGroups().size());
    }
    
    @Test
    public void filterIncludeTwoTestCaseAnd1groupRemains() {
        Channel channel = Channel.desktop;
        AppEcom appE = AppEcom.shop;
        XmlTest xmlTest = makeXmlTest(channel, appE, "V2"/*add include*/);
        String[] testCaseList = {this.testNGxml.methodGroupMiCuentaNotIncluded, this.testNGxml.methodGroupGaleriaProductoIncluded};
        execAndvalidateTestsTwoMethods(xmlTest, testCaseList);
    }
    
    @Test
    public void filterIncludeTwoTestCaseWithCode() {
        Channel channel = Channel.desktop;
        AppEcom appE = AppEcom.shop;
        XmlTest xmlTest = makeXmlTest(channel, appE, "V2"/*add include*/);
        String codeMethod1 = this.testNGxml.methodGroupMiCuentaNotIncluded.substring(0,5);
        String codeMethod2 = this.testNGxml.methodGroupGaleriaProductoIncluded.substring(0,5);
        String[] testCaseList = {codeMethod1, codeMethod2};
        execAndvalidateTestsTwoMethods(xmlTest, testCaseList);
    }
    
    private void execAndvalidateTestsTwoMethods(XmlTest xmlTest, String[] testCaseList) {
        Channel channel = Channel.desktop;
        AppEcom appE = AppEcom.shop;
        
        //Code to test
        FilterTNGxmlTRun.filterWithTCasesToExec(xmlTest, testCaseList, channel, appE);

        assertEquals("Remains only 1 dependency-group ", 1, xmlTest.getXmlDependencyGroups().size());
        assertTrue("Is present the group \"GaleriaProducto\"", isGroupInDependencies(xmlTest, "GaleriaProducto", GroupDep.to));
        assertTrue("Is present the dependency \"Micuenta\"", isGroupInDependencies(xmlTest, "Micuenta", GroupDep.from));
        assertTrue("The new method " + this.testNGxml.methodGroupMiCuentaNotIncluded + " is included", classIncludesMethod(xmlTest.getXmlClasses().get(0), this.testNGxml.methodGroupMiCuentaNotIncluded));
        assertTrue("The old method " + this.testNGxml.methodGroupGaleriaProductoIncluded + " remains included", classIncludesMethod(xmlTest.getXmlClasses().get(0), this.testNGxml.methodGroupGaleriaProductoIncluded));        
    }
    
    @Test
    public void includeTestCaseThatDoesnotExists() {
        Channel channel = Channel.desktop;
        AppEcom appE = AppEcom.shop;
        XmlTest xmlTest = makeXmlTest(channel, appE, "V2"/*add include*/);
        String[] testCaseList = {this.testNGxml.methodThatDoesNotExists};
        
        //Code to test
        FilterTNGxmlTRun.filterWithTCasesToExec(xmlTest, testCaseList, channel, appE);
        
        assertEquals("No classes remains ", 0, xmlTest.getClasses().size());
    }
    
    /**
     * @param version
     *  V1 - Include all methods
     *  V2 - Include only the method this.methodGroupGaleriaProductoIncluded
     */
    private XmlTest makeXmlTest(Channel channel, AppEcom appE, String version) {
        ParamsBean params = new ParamsBean();
        params.setChannel(channel);
        params.setAppE(appE);
        params.setVersion(version);
        XmlSuite xmlSuite = this.testNGxml.createSuite(params);
        return xmlSuite.getTests().get(0);        
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
