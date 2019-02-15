package com.mng.robotest.test80.mango.test.xmlprogram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlDependencies;
import org.testng.xml.XmlGroups;
import org.testng.xml.XmlRun;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;
import org.testng.xml.XmlSuite.ParallelMode;

import com.mng.robotest.test80.ParamsBean;


public class ValesPaisesXML {

    /**
     * Ejecución desde el Online
     * @param params
     *    params.version
     *          V1 - NO pasar, NO pago - Sí filtro calendar
     *          V2 - SÍ pasar, NO pago - Sí filtro calendar 
     *          V3 - SÍ pasar, SÍ pago - Sí filtro calendar
     *          V4 - NO pasar, NO pago - No filtro calendar
     *          V5 - SÍ pasar, NO pago - No filtro calendar
     *          V6 - SÍ pasar, SÍ pago - No filtro calendar
     *    params.browser
     *          chrome, firefox
     *          browserstack
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
        suite.setFileName("tng_ValesPaises.xml");
        suite.setName(params.getSuiteName());
        suite.setListeners(commonsXML.createStandardListeners());
        
        //Componemos la descripción del TestRun
        String testRunDescription = commonsXML.getDescriptionTestRun(params);

        //Creamos los parámetros comunes y los asociamos a la suite
        Map<String, String> parametersSuite = new HashMap<>();
        createCommonParamsSuite(parametersSuite, params);
        suite.setParameters(parametersSuite);
        
        //En caso <> browserstack paralelizaremos a nivel de los métodos (casos de prueba)
        suite.setParallel(ParallelMode.METHODS);
        suite.setThreadCount(4);
            
        //Sólo ejecutamos 1 TestRun
        joinSuiteWithTestRunLocal(suite, testRunDescription);
        
        return suite;
    }
    
    /**
     * Creación de los parámetros comunes a nivel de la Suite
     */
    private void createCommonParamsSuite(Map<String, String> parametersSuite, ParamsBean params) {
        String version = params.getVersion();
        
        //Establecemos los parámetros genéricos (válidos para todos los casos de prueba)
        commonsXML.setCommonsParamsSuite(parametersSuite, params);

        //Indica si hemoos de validar los pagos marcados en el XML para testear (Más adelante esto dependerá del parámetro 'versión')
        switch (version) {
        case "V1":
            parametersSuite.put("validaPasarelas", "false");
            parametersSuite.put("validaPagos", "false");
            parametersSuite.put("validaPedidosEnManto", "false");
            parametersSuite.put("filtroCalendar", "true");
            break;
        case "V2":
            parametersSuite.put("validaPasarelas", "true");
            parametersSuite.put("validaPedidosEnManto", "false");
            parametersSuite.put("validaPagos", "false");
            parametersSuite.put("filtroCalendar", "true");
            break;
        case "V3":
            parametersSuite.put("validaPasarelas", "true");
            parametersSuite.put("validaPagos", "true");
            parametersSuite.put("validaPedidosEnManto", "true");
            parametersSuite.put("filtroCalendar", "true");
            break;
        case "V4":
            parametersSuite.put("validaPasarelas", "false");
            parametersSuite.put("validaPagos", "false");
            parametersSuite.put("validaPedidosEnManto", "false");
            parametersSuite.put("filtroCalendar", "false");
            break;
        case "V5":
            parametersSuite.put("validaPasarelas", "true");
            parametersSuite.put("validaPagos", "false");
            parametersSuite.put("validaPedidosEnManto", "false");
            parametersSuite.put("filtroCalendar", "false");
            break;
        case "V6":
            parametersSuite.put("validaPasarelas", "true");
            parametersSuite.put("validaPagos", "true");
            parametersSuite.put("validaPedidosEnManto", "true");
            parametersSuite.put("filtroCalendar", "false");
            break;            
        default:
            break;
        }
        
        //Indica si hemos de realizar las pruebas introduciendo una tarjeta de empleado como vale descuento (Más adelante esto dependerá del parámetro 'versión')
        parametersSuite.put("isEmpl", "false"); 
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
        return run;
    }
    
    private XmlDependencies createDependencies() {
        XmlDependencies dependencies = new XmlDependencies();
        return dependencies;
    }
    
    private List<XmlClass> createClasses() {
        List<XmlClass> listClasses = new ArrayList<>();
        listClasses.add(new XmlClass("com.mng.robotest.test80.mango.test.factoryes.ValesPaises"));
        return listClasses;
    }
}
