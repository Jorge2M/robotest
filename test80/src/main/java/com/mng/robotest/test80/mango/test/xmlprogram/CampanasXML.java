package com.mng.robotest.test80.mango.test.xmlprogram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlGroups;
import org.testng.xml.XmlRun;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;
import org.testng.xml.XmlSuite.ParallelMode;

import com.mng.robotest.test80.ParamsBean;


public class CampanasXML {

    /**
     * Ejecución desde el Online
     * @param params
     *    params.version
     *          V1 - Versión por defecto
     *    params.browser
     *          chrome, firefox
     */
    public void testRunner(ParamsBean params) {
        // Lista de suites (sólo creamos una)
        List<XmlSuite> suites = new ArrayList<>();
        suites.add(createSuite(params));    
          
        //Creamos el XML de TestNG asignándole la suite
        TestNG tng = new TestNG();
        tng.setXmlSuites(suites);
        tng.setUseDefaultListeners(false);  
        tng.run();
    }
    
    private XmlSuite createSuite(ParamsBean params) {
        XmlSuite suite = new XmlSuite();
        
        //Asignamos un nombre a la suite, definimos sus atributos y los listeners
        //suite.setName("TestMovilWeb");
        suite.setFileName("tng_PaisesIdiom_PC_web.xml");
        suite.setName(params.getSuiteName());
        suite.setListeners(commonsXML.createStandardListeners());
        
        //Componemos la descripción del TestRun
        String testRunDescription = commonsXML.getDescriptionTestRun(params);
        
        //Creamos los parámetros comunes y los asociamos a la suite
        Map<String, String> parametersSuite = new HashMap<>();
        createCommonParamsSuite(parametersSuite, params);
        suite.setParameters(parametersSuite);
        
        //Paralelizaremos a nivel de los métodos (casos de prueba)
        suite.setParallel(ParallelMode.METHODS);
        suite.setThreadCount(3);
        
        //Sólo ejecutamos 1 TestRun
        joinSuiteWithTestRunLocal(suite, testRunDescription);
        
        return suite;
    }
    
    /**
     * Creación de los parámetros comunes a nivel de la Suite
     */
    private void createCommonParamsSuite(Map<String, String> parametersSuite, ParamsBean params) {
        //Establecemos los parámetros genéricos (válidos para todos los casos de prueba)
        commonsXML.setCommonsParamsSuite(parametersSuite, params);
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
        return groups;
    }    
    
    private XmlRun createRun() {
        XmlRun run = new XmlRun();
        return run;
    }
    
    private List<XmlClass> createClasses() {
        List<XmlClass> listClasses = new ArrayList<>();
        listClasses.add(new XmlClass("com.mng.robotest.test80.mango.test.factoryes.ListCampanas"));
        return listClasses;
    }
}
