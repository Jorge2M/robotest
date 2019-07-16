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

import com.mng.robotest.test80.TestMaker;
import com.mng.robotest.test80.arq.utils.filter.DataFilterTCases;
import com.mng.robotest.test80.arq.utils.filter.FilterTestsSuiteXML;
import com.mng.robotest.test80.arq.utils.filter.TestMethod;
import com.mng.robotest.test80.arq.utils.otras.Constantes;
import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.arq.utils.webdriver.BStackDataDesktop;
import com.mng.robotest.test80.arq.utils.webdriver.BStackDataMovil;
import com.mng.robotest.test80.arq.utils.webdriver.maker.FactoryWebdriverMaker.TypeWebDriver;
import com.mng.robotest.test80.arq.xmlprogram.ParamsBean;
import com.mng.robotest.test80.arq.xmlprogram.TestMakerSuiteXML;
import com.mng.robotest.test80.arq.xmlprogram.CommonsXML;

public class SmokeTestXML extends TestMakerSuiteXML {

    private SmokeTestXML(ParamsBean params, TestMaker testMaker) {
    	super(params, testMaker);
    }
    
    /**
     * Ejecución desde el Online
     *
     */
    public void testRunner() { 
        List<XmlSuite> suites = new ArrayList<>();
        suites.add(xmlSuite);    
        TestNG tng = new TestNG();
        tng.setXmlSuites(suites);
        tng.setUseDefaultListeners(false);
        tng.run();
    }
    
    public List<TestMethod> getDataTestAnnotationsToExec() {
        XmlTest testRun = xmlSuite.getTests().get(0);
        return (
        	getInitialTestCaseCandidatesToExecute(testRun, dFilter.getChannel(), dFilter.getAppE())
        );
    }
    
    //Esto ha de estar en una clase padre

    private static XmlSuite createSuite(ParamsBean params) {
        XmlSuite suite = new XmlSuite();
        
        //Asignamos un nombre a la suite, definimos sus atributos y los listeners
        //suite.setName("TestMovilWeb");
        suite.setFileName("tng_Funcionales_Movil.xml");
        suite.setName(params.getSuiteName());
        suite.setListeners(CommonsXML.createStandardListeners());

        //Creamos los parámetros comunes y los asociamos a la suite
        Map<String, String> parametersSuite = new HashMap<>();
        createCommonParamsSuite(parametersSuite, params);
        suite.setParameters(parametersSuite);
        
        if (params.getBrowser().compareTo(Constantes.BROWSERSTACK)==0) {
            //En el caso de BrowserStack paralelizaremos a nivel de los TestRuns (1 hilo x cada TestRun asociado a un dispositivo/browser)
            suite.setParallel(ParallelMode.TESTS);
            suite.setThreadCount(Constantes.BSTACK_PARALLEL);
            if (params.getChannel()==Channel.movil_web) {
                joinSuiteWithTestRunMobilBStack(TypeWebDriver.browserstack, params, suite, CommonMangoDataForXML.bsMovilAndroid);
                joinSuiteWithTestRunMobilBStack(TypeWebDriver.browserstack, params, suite, CommonMangoDataForXML.bsMovilIOS);            
            } else {
                //joinSuiteWithTestRunDesktopBStack(TypeDriver.browserstack, suite, commonsXML.bsDktopWin10Explorer);
                joinSuiteWithTestRunDesktopBStack(TypeWebDriver.browserstack, params, suite, CommonMangoDataForXML.bsDktopOSXSafari);
                joinSuiteWithTestRunDesktopBStack(TypeWebDriver.browserstack, params, suite, CommonMangoDataForXML.bsDktopWin8Firefox);
            }
        } else {
            //En caso <> BrowserStack paralelizaremos a nivel de los Métodos (casos de prueba)
            suite.setParallel(ParallelMode.METHODS);
            suite.setThreadCount(3);
            
            //Sólo ejecutamos 1 TestRun
            createTestRunFilteredWithTestCases(params, suite, CommonsXML.getDescriptionTestRun(params), params.getGroups(), params.getTestCases());
        }
        
        return suite;
    }
    
    private static void createCommonParamsSuite(Map<String, String> parametersSuite, ParamsBean paramsI) {
    	CommonMangoDataForXML.setCommonsParamsSuite(parametersSuite, paramsI);
    }
    
    private static XmlTest joinSuiteWithTestRunMobilBStack(TypeWebDriver webdriverType, ParamsBean params, XmlSuite suite, BStackDataMovil bsMovil) {
        XmlTest testRun = CommonsXML.joinSuiteWithTestRunMobilBStack(webdriverType, suite, bsMovil);
        testRun.setGroups(createGroups(params));
        testRun.setXmlClasses(createClasses(params));
        filterTestCasesToExec(testRun);
        return testRun;
    }
    
    private static XmlTest joinSuiteWithTestRunDesktopBStack(TypeWebDriver webdriverType, ParamsBean params, XmlSuite suite, BStackDataDesktop bsDesktop) {
        XmlTest testRun = CommonsXML.joinSuiteWithTestRunDesktopBStack(webdriverType, suite, bsDesktop);
        testRun.setGroups(createGroups(params));
        testRun.setXmlClasses(createClasses(params));
        filterTestCasesToExec(testRun);
        return testRun;
    }    
    
    private static XmlTest createTestRunFilteredWithTestCases(ParamsBean params, XmlSuite suite, String testRunName, String[] groups, String[] testCaseList) {
        XmlTest testRun = CommonsXML.createTestRun(suite, testRunName, testCaseList);
        testRun.setGroups(createGroups(params));
        testRun.setXmlClasses(createClasses(params));
        filterTestCasesToExec(testRun);
        return testRun;
    }    
    
    private static XmlGroups createGroups(ParamsBean params) {
        XmlGroups groups = new XmlGroups();
        groups.setRun(createRun(params));
        groups.setXmlDependencies(createDependencies(params));
        return groups;
    }    
    
    private static XmlRun createRun(ParamsBean params) {
        XmlRun run = new XmlRun();
        for (String group : CommonsXML.getListOfPossibleGroups(params.getChannel(), params.getAppE()))
            run.onInclude(group);
        
        return run;
    }
    
    private static XmlDependencies createDependencies(ParamsBean params) {
        XmlDependencies dependencies = new XmlDependencies();
        switch (params.getChannel()) {
        case desktop:
//            //dependencies.onGroup("Buscador", "Registro");
//            dependencies.onGroup("Buscador", "IniciarSesion");
//            dependencies.onGroup("Bolsa", "Buscador");
//            dependencies.onGroup("Compra", "Bolsa");
//            dependencies.onGroup("FichaProducto", "Compra");
//            dependencies.onGroup("GaleriaProducto", "FichaProducto");
//            dependencies.onGroup("Favoritos" , "FichaProducto");
            break;
        case movil_web:            
//            dependencies.onGroup("Favoritos", "Compra");
            break;
        default:
            break;
        }
        
        return dependencies;
    }
    
    private static List<XmlClass> createClasses(ParamsBean params) {
        List<XmlClass> listClasses = new ArrayList<>();
        
        //Existen un conjunto de tests que todavía no están implementados en el canal movil_web
        if (params.getChannel()==Channel.desktop) {
            listClasses.add(new XmlClass("com.mng.robotest.test80.mango.test.appshop.Otras"));
            listClasses.add(new XmlClass("com.mng.robotest.test80.mango.test.appshop.SEO"));
            listClasses.add(new XmlClass("com.mng.robotest.test80.mango.test.appshop.IniciarSesion"));
            listClasses.add(new XmlClass("com.mng.robotest.test80.mango.test.appshop.Bolsa"));
        }

        listClasses.add(new XmlClass("com.mng.robotest.test80.mango.test.appshop.FichaProducto"));
        listClasses.add(new XmlClass("com.mng.robotest.test80.mango.test.appshop.Ayuda"));
        listClasses.add(new XmlClass("com.mng.robotest.test80.mango.test.appshop.Buscador"));
        listClasses.add(new XmlClass("com.mng.robotest.test80.mango.test.appshop.Footer"));
        listClasses.add(new XmlClass("com.mng.robotest.test80.mango.test.appshop.Registro"));
        listClasses.add(new XmlClass("com.mng.robotest.test80.mango.test.appshop.PaisIdioma"));
        listClasses.add(new XmlClass("com.mng.robotest.test80.mango.test.appshop.GaleriaProducto"));
        listClasses.add(new XmlClass("com.mng.robotest.test80.mango.test.appshop.Compra"));
        listClasses.add(new XmlClass("com.mng.robotest.test80.mango.test.factoryes.ListPagosEspana"));
        listClasses.add(new XmlClass("com.mng.robotest.test80.mango.test.appshop.MiCuenta"));
        listClasses.add(new XmlClass("com.mng.robotest.test80.mango.test.appshop.Favoritos"));
        listClasses.add(new XmlClass("com.mng.robotest.test80.mango.test.appshop.Reembolsos"));
        listClasses.add(new XmlClass("com.mng.robotest.test80.mango.test.appshop.Loyalty"));
        
        return listClasses;
    }
    
    public ParamsBean getParams() {
        return this.params;
    }
}
