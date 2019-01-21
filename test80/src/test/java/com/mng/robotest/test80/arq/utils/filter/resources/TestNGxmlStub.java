package com.mng.robotest.test80.arq.utils.filter.resources;

import java.util.ArrayList;
import java.util.List;

import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlDependencies;
import org.testng.xml.XmlGroups;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlRun;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlSuite.ParallelMode;

import com.mng.robotest.test80.ParamsBean;
import com.mng.robotest.test80.mango.test.xmlprogram.commonsXML;

import org.testng.xml.XmlTest;

@SuppressWarnings("javadoc")
public class TestNGxmlStub {

    public final String classWithTestAnnotations = "com.mng.robotest.test80.arq.utils.filter.resources.ClassWithTCasesStub";
    public final String methodGroupGaleriaProductoIncluded = "GPO001_Galeria_Camisas";
    public final String methodGroupMiCuentaNotIncluded = "MIC001_Opciones_Mi_Cuenta";
    public final String methodThatDoesNotExists = "COM001_Compra_TrjSaved_Empl";
    
    ParamsBean params = null;
    
    /**
     * Ejecución desde el Online
     * @param params
     */
    public void testRunner(ParamsBean paramsToStore) {
        List<XmlSuite> suites = new ArrayList<>();
        suites.add(createSuite(paramsToStore));    
        TestNG tng = new TestNG();
        tng.setXmlSuites(suites);
        tng.setUseDefaultListeners(false);
        tng.run();
    }
    
    /**
     * @param paramsToStore.version
     *  V1 - Include all methods
     *  V2 - Include only the method this.methodGroupGaleriaProductoIncluded
     */
    public XmlSuite createSuite(ParamsBean paramsToStore) {
        this.params = paramsToStore;
        XmlSuite suite = new XmlSuite();
        suite.setName("Suite Example");
        suite.setParallel(ParallelMode.METHODS);
        suite.setThreadCount(3);
        joinSuiteWithTestRunLocal(suite, "Description TestRun");
        return suite;
    }
    
    public XmlTest joinSuiteWithTestRunLocal(XmlSuite suite, String testRunName) {
        XmlTest testRun = commonsXML.createTestRun(suite, testRunName);
        testRun.setGroups(createGroups());
        testRun.setXmlClasses(createClasses());     
        return testRun;
    }    
    
    private XmlGroups createGroups() {
        XmlGroups groups = new XmlGroups();
        groups.setRun(createRun());
        groups.setXmlDependencies(createDependencies());
        return groups;
    }    
    
    private XmlRun createRun() {
        XmlRun run = new XmlRun();
        for (String group : commonsXML.getListOfPossibleGroups(this.params.getChannel(), this.params.getAppE()))
            run.onInclude(group);
            
        return run;
    }
    
    private XmlDependencies createDependencies() {
        XmlDependencies dependencies = new XmlDependencies();
        dependencies.onGroup("Buscador", "IniciarSesion");
        dependencies.onGroup("Bolsa", "Buscador");
        dependencies.onGroup("Compra", "Bolsa");
        dependencies.onGroup("FichaProducto", "Compra");
        dependencies.onGroup("GaleriaProducto", "Micuenta");
        dependencies.onGroup("Micuenta" , "FichaProducto");
        dependencies.onGroup("Manto", "Compra");
        return dependencies;
    }
    
    private List<XmlClass> createClasses() {
        List<XmlClass> listClasses = new ArrayList<>();
        XmlClass xmlClass = new XmlClass(this.classWithTestAnnotations);
        List<XmlInclude>includeMethods = new ArrayList<>();
        if ("V2".compareTo(this.params.getVersion())==0)
            includeMethods.add(new XmlInclude(this.methodGroupGaleriaProductoIncluded));
        
        xmlClass.setIncludedMethods(includeMethods);
        listClasses.add(xmlClass);
        
        return listClasses;
    }
    
    public ParamsBean getParams() {
        return this.params;
    }
}
