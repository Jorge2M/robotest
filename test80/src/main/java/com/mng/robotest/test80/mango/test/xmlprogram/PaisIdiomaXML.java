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
import com.mng.robotest.test80.arq.utils.otras.Constantes;
import com.mng.robotest.test80.arq.utils.webdriver.BStackDataMovil;
import com.mng.robotest.test80.arq.utils.webdriver.maker.FactoryWebdriverMaker.TypeWebDriver;

public class PaisIdiomaXML {

    /**
     * Ejecución desde el Online
     * @param params
     *    params.version
     *          V1 - Países-Idiomas-Líneas
     *          V2 - Países-Idiomas-Líneas-Banners
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
        suite.setFileName("tng_PaisesIdiom_PC_web.xml");
        suite.setName(params.getSuiteName());
        suite.setListeners(commonsXML.createStandardListeners());
        
        //Componemos la descripción del TestRun
        String testRunDescription = commonsXML.getDescriptionTestRun(params);
        
        //Creamos los parámetros comunes y los asociamos a la suite
        Map<String, String> parametersSuite = new HashMap<>();
        createCommonParamsSuite(parametersSuite, params);
        suite.setParameters(parametersSuite);
        
        if (params.getBrowser().compareTo(Constantes.BROWSERSTACK)==0) {
            //En el caso de BrowserStack paralelizaremos a nivel de los TestRuns (1 hilo x cada TestRun asociado a un dispositivo)
            suite.setParallel(ParallelMode.TESTS);
            suite.setThreadCount(Constantes.BSTACK_PARALLEL);
            
            //Asociamos a la suite X testruns (de momento pasamos datos hardcodeados pero deberán llegarnos vía parámetro)
            joinSuiteWithTestRunMobilBStack(TypeWebDriver.browserstack, suite, commonsXML.bsMovilAndroid);
            joinSuiteWithTestRunMobilBStack(TypeWebDriver.browserstack, suite, commonsXML.bsMovilIOS);
        } else {
            //En caso <> browserstack paralelizaremos a nivel de los métodos (casos de prueba)
            suite.setParallel(ParallelMode.METHODS);
            suite.setThreadCount(3);
            
            //Sólo ejecutamos 1 TestRun
            joinSuiteWithTestRunLocal(suite, testRunDescription);
        }
        
        return suite;
    }
    
    /**
     * Creación de los parámetros comunes a nivel de la Suite
     */
    private void createCommonParamsSuite(Map<String, String> parametersSuite, ParamsBean params) {
        String version = params.getVersion();
        
        //Establecemos los parámetros genéricos (válidos para todos los casos de prueba)
        commonsXML.setCommonsParamsSuite(parametersSuite, params);
        
        //Flags en función de la versión a testear
        switch (version) {
        case "V2":
            parametersSuite.put("recorreBanners", "true");
            break;
        case "V1":
        default:            
            parametersSuite.put("recorreBanners", "false");
            break;            
        }

        //Indicamos que no se han de testear todos los menús
        parametersSuite.put("recorreMenus", "false");
        
        //Especifica el tipo de test
        //1: Test países-idiomas-líneas-banners
        //2: Test países-idiomas-registro
        parametersSuite.put("typeTest", "1");
    }
    
    private XmlTest joinSuiteWithTestRunMobilBStack(TypeWebDriver webdriverType, XmlSuite suite, BStackDataMovil bsMovil) {
        XmlTest testRun = commonsXML.joinSuiteWithTestRunMobilBStack(webdriverType, suite, bsMovil);
        testRun.setGroups(createGroups());
        testRun.setXmlClasses(createClasses());     
        return testRun;
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
        listClasses.add(new XmlClass("com.mng.robotest.test80.mango.test.factoryes.ListAllCountrys"));
        return listClasses;
    }
}
