package com.mng.robotest.test80.arq.utils.filter.resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlDependencies;
import org.testng.xml.XmlGroups;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlRun;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlSuite.ParallelMode;

import com.mng.robotest.test80.arq.xmlprogram.ParamsBean;
import com.mng.robotest.test80.arq.xmlprogram.TestMakerSuiteXML;
import com.mng.robotest.test80.TestMaker;
import com.mng.robotest.test80.arq.xmlprogram.CommonsXML;

import org.testng.xml.XmlTest;

public class TestNGxmlStub extends TestMakerSuiteXML {

    public enum TypeStubTest {
    	WithoutMethodsIncludedInClass,
    	OnlyMethodGpo001includedInClass,
    	OnlyMethodMic001includedInClass,
    	WithTwoMethodsIncludedInClass,
    	OnlyMethodThatDoesntExistInClass;
    }
	
    public final String classWithTestAnnotations = "com.mng.robotest.test80.arq.utils.filter.resources.ClassWithTCasesStub";
    public final String methodGroupGaleriaProductoToInclude = "GPO001_Galeria_Camisas";
    public final String methodGroupMiCuentaToInclude = "MIC001_Opciones_Mi_Cuenta";
    public final String methodThatDoesNotExistsInClass = "COM001_Compra_TrjSaved_Empl";
    public final int numberTestsCasesDesktopShop = 3;
    
    TypeStubTest typeTest;

    private TestNGxmlStub(TypeStubTest typeTest, ParamsBean params, TestMaker testMaker) {
    	super(params, testMaker);
    	this.typeTest = typeTest;
    }
    
    public static TestNGxmlStub getNew(TypeStubTest typeTest, ParamsBean params, TestMaker testMaker) {
    	TestNGxmlStub test = new TestNGxmlStub(typeTest, params, testMaker); 
    	return test;
    }
    
    /**
     * Ejecución desde el Online
     * @param params
     */
    public void testRunner(ParamsBean paramsToStore) {
        List<XmlSuite> suites = new ArrayList<>();
        suites.add(createSuite());    
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
    @Override
    public XmlSuite createSuite() {
        XmlSuite suite = new XmlSuite();
        suite.setName("Suite Example");
        suite.setParallel(ParallelMode.METHODS);
        suite.setThreadCount(3);
        joinSuiteWithTestRunLocal(suite, "Description TestRun");
        return suite;
    }
    
    private XmlTest joinSuiteWithTestRunLocal(XmlSuite suite, String testRunName) {
        XmlTest testRun = CommonsXML.createTestRun(suite, testRunName);
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
        for (String group : CommonsXML.getListOfPossibleGroups(this.params.getChannel(), this.params.getAppE()))
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
        List<String> listMethodsToInclude = getMethodsIncludedInClass();
        if (listMethodsToInclude!=null) {
        	for (String method : listMethodsToInclude) {
        		includeMethods.add(new XmlInclude(method));
        	}
        }
        xmlClass.setIncludedMethods(includeMethods);
        listClasses.add(xmlClass);
        
        return listClasses;
    }
    
    public List<String> getMethodsIncludedInClass() {
    	switch (typeTest) {
    	case OnlyMethodGpo001includedInClass:
    		return (
    			Arrays.asList(methodGroupGaleriaProductoToInclude)
    		);
    	case OnlyMethodMic001includedInClass:
    		return (
    			Arrays.asList(methodGroupMiCuentaToInclude)
    		);
    	case WithTwoMethodsIncludedInClass:
    		return (
    			Arrays.asList(
    				methodGroupGaleriaProductoToInclude,
    				methodGroupMiCuentaToInclude)
    		);
    	case OnlyMethodThatDoesntExistInClass:
    		return (
    			Arrays.asList(methodThatDoesNotExistsInClass)
    	    );
    	default:
    	case WithoutMethodsIncludedInClass:
    		return (new ArrayList<String>());
    	}
    }
}
