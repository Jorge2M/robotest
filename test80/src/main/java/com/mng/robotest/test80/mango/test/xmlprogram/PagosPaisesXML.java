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

import com.mng.robotest.test80.arq.utils.otras.Constantes;
import com.mng.robotest.test80.arq.utils.webdriver.BStackDataMovil;
import com.mng.robotest.test80.arq.utils.webdriver.maker.FactoryWebdriverMaker.TypeWebDriver;
import com.mng.robotest.test80.arq.xmlprogram.ParamsBean;
import com.mng.robotest.test80.arq.xmlprogram.CommonsXML;

public class PagosPaisesXML {

    ParamsBean params = null;
    
    /**
     * Ejecución desde el Online
     * @param params
     *    params.version
     *          V1 - NO pasarelas-NO pago
     *          V2 - SÍ pasarelas-NO pago
     *          V3 - SÍ pasarelas-SÍ pago-NO manto
     *          V4 - SÍ pasarelas-SÍ pago-SÍ manto
     *          V5 - NO pasar-NO pag-Empl
     *          V6 - SÍ pasar-NO pag-Empl
     *          V7 - SÍ pasar-SÍ pag-Empl-NO manto          
     *          V8 - SÍ pasar-SÍ pag-Empl-SÍ manto
     *    params.browser
     *          chrome, firefox
     *          browserstack
     */
    public void testRunner(ParamsBean paramsToStore) {
        this.params = paramsToStore;
        
        // Lista de suites (sólo creamos una)
        List<XmlSuite> suites = new ArrayList<>();
        suites.add(createSuite());    
          
        //Creamos el XML de TestNG asignándole la suite
        TestNG tng = new TestNG();
        tng.setXmlSuites(suites);
        tng.setUseDefaultListeners(false);
        tng.run();
    }
    
    private XmlSuite createSuite() {
        XmlSuite suite = new XmlSuite();
        
        //Asignamos un nombre a la suite, definimos sus atributos y los listeners
        //suite.setName("TestMovilWeb");
        suite.setFileName("tng_PagosPaises.xml");
        suite.setName(this.params.getSuiteName());
        suite.setListeners(CommonsXML.createStandardListeners());
        
        //Componemos la descripción del TestRun
        String testRunDescription = CommonsXML.getDescriptionTestRun(this.params);

        //Creamos los parámetros comunes y los asociamos a la suite
        Map<String, String> parametersSuite = new HashMap<>();
        createCommonParamsSuite(parametersSuite);
        suite.setParameters(parametersSuite);
        
        if (this.params.getBrowser().compareTo(Constantes.BROWSERSTACK)==0) {
            //En el caso de BrowserStack paralelizaremos a nivel de los TestRuns (1 hilo x cada TestRun asociado a un dispositivo)
            suite.setParallel(ParallelMode.TESTS);
            suite.setThreadCount(Constantes.BSTACK_PARALLEL);
            
            //Asociamos a la suite X testruns (de momento pasamos datos hardcodeados pero deberán llegarnos vía parámetro)
            joinSuiteWithTestRunBStack(TypeWebDriver.browserstack, suite, CommonMangoDataForXML.bsMovilAndroid);
            joinSuiteWithTestRunBStack(TypeWebDriver.browserstack, suite, CommonMangoDataForXML.bsMovilIOS);
        } else {
            //En caso <> browserstack paralelizaremos a nivel de los métodos (casos de prueba)
            suite.setParallel(ParallelMode.METHODS);
            suite.setThreadCount(4);
            
            //Sólo ejecutamos 1 TestRun
            joinSuiteWithTestRunLocal(suite, testRunDescription);
        }
        
        return suite;
    }
    
    /**
     * Creación de los parámetros comunes a nivel de la Suite
     */
    private void createCommonParamsSuite(Map<String, String> parametersSuite) {
        String version = this.params.getVersion();
        
        //Establecemos los parámetros genéricos (válidos para todos los casos de prueba)
        CommonMangoDataForXML.setCommonsParamsSuite(parametersSuite, this.params);

        //Indica si hemoos de validar los pagos marcados en el XML para testear (Más adelante esto dependerá del parámetro 'versión')
        switch (version) {
        case "V1":
            parametersSuite.put("validaPasarelas", "false");
            parametersSuite.put("validaPagos", "false");
            parametersSuite.put("validaPedidosEnManto", "false");
            parametersSuite.put("isEmpl", "false");
            parametersSuite.put("forceTestMisCompras", "false");
            break;
        case "V2":
            parametersSuite.put("validaPasarelas", "true");
            parametersSuite.put("validaPagos", "false");
            parametersSuite.put("validaPedidosEnManto", "false");
            parametersSuite.put("isEmpl", "false");
            parametersSuite.put("forceTestMisCompras", "false");
            break;
        case "V3":
            parametersSuite.put("validaPasarelas", "true");
            parametersSuite.put("validaPagos", "true");
            parametersSuite.put("validaPedidosEnManto", "false");
            parametersSuite.put("isEmpl", "false");
            parametersSuite.put("forceTestMisCompras", "false");
            break;
        case "V4":
            parametersSuite.put("validaPasarelas", "true");
            parametersSuite.put("validaPagos", "true");
            parametersSuite.put("validaPedidosEnManto", "true");
            parametersSuite.put("isEmpl", "false");
            parametersSuite.put("forceTestMisCompras", "false");
            break;
        case "V5":
            parametersSuite.put("validaPasarelas", "false");
            parametersSuite.put("validaPagos", "false");
            parametersSuite.put("validaPedidosEnManto", "false");
            parametersSuite.put("isEmpl", "true");
            parametersSuite.put("forceTestMisCompras", "false");
            break;
        case "V6":
            parametersSuite.put("validaPasarelas", "true");
            parametersSuite.put("validaPagos", "false");
            parametersSuite.put("validaPedidosEnManto", "false");
            parametersSuite.put("isEmpl", "true");
            parametersSuite.put("forceTestMisCompras", "false");
            break;
        case "V7":
            parametersSuite.put("validaPasarelas", "true");
            parametersSuite.put("validaPagos", "true");
            parametersSuite.put("validaPedidosEnManto", "false");
            parametersSuite.put("isEmpl", "true");
            parametersSuite.put("validaMisCompras", "false");
            break;
        case "V8":
            parametersSuite.put("validaPasarelas", "true");
            parametersSuite.put("validaPagos", "true");
            parametersSuite.put("validaPedidosEnManto", "true");
            parametersSuite.put("isEmpl", "true");
            parametersSuite.put("forceTestMisCompras", "false");
            break;            
        case "V9":
            parametersSuite.put("validaPasarelas", "true");
            parametersSuite.put("validaPagos", "true");
            parametersSuite.put("validaPedidosEnManto", "false");
            parametersSuite.put("isEmpl", "false");
            parametersSuite.put("forceTestMisCompras", "true");
            break;              
        default:
            break;
        }
    }
    
    private XmlTest joinSuiteWithTestRunBStack(TypeWebDriver webdriverType, XmlSuite suite, BStackDataMovil bsMovil) {
        XmlTest testRun = CommonsXML.joinSuiteWithTestRunMobilBStack(webdriverType, suite, bsMovil);
        testRun.setGroups(createGroups());
        testRun.setXmlClasses(createClasses());     
        return testRun;
    }
    
    public XmlTest joinSuiteWithTestRunLocal(XmlSuite suite, String testRunName) {
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
        return run;
    }
    
    private XmlDependencies createDependencies() {
        XmlDependencies dependencies = new XmlDependencies();
        return dependencies;
    }
    
    private List<XmlClass> createClasses() {
        List<XmlClass> listClasses = new ArrayList<>();
        listClasses.add(new XmlClass("com.mng.robotest.test80.mango.test.factoryes.ListPrecompraPaises"));
        return listClasses;
    }
}