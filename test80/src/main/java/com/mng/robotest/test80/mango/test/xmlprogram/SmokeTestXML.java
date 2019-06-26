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

import com.mng.robotest.test80.arq.utils.filter.DataFilterTCases;
import com.mng.robotest.test80.arq.utils.filter.FilterTNGxmlTRun;
import com.mng.robotest.test80.arq.utils.filter.TestMethod;
import com.mng.robotest.test80.arq.utils.otras.Constantes;
import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.arq.utils.webdriver.BStackDataDesktop;
import com.mng.robotest.test80.arq.utils.webdriver.BStackDataMovil;
import com.mng.robotest.test80.arq.utils.webdriver.maker.FactoryWebdriverMaker.TypeWebDriver;
import com.mng.robotest.test80.arq.xmlprogram.ParamsBean;
import com.mng.robotest.test80.arq.xmlprogram.CommonsXML;

public class SmokeTestXML {

    ParamsBean params = null;
    final DataFilterTCases dFilter = new DataFilterTCases();
    
    /**
     * Ejecución desde el Online
     *
     */
    public void testRunner(ParamsBean paramsToStore) {
        params = paramsToStore;
        setDataFilterFromParams();
        
        // Lista de suites (sólo creamos una)
        List<XmlSuite> suites = new ArrayList<>();
        suites.add(createSuite(this.params));    
          
        TestNG tng = new TestNG();
        tng.setXmlSuites(suites);
        tng.setUseDefaultListeners(false);
        tng.run();
    }
    
    public List<TestMethod> getDataTestAnnotationsToExec(ParamsBean paramsToStore) {
        params = paramsToStore;
        setDataFilterFromParams();
        
        XmlSuite xmlSuite = createSuite(params);
        XmlTest testRun = xmlSuite.getTests().get(0);
        return (
        	FilterTNGxmlTRun.getInitialTestCaseCandidatesToExecute(testRun, dFilter.getChannel(), dFilter.getAppE())
        );
    }
    
    //Esto ha de estar en una clase padre
    private void setDataFilterFromParams() {
        dFilter.setAppE(params.getAppE());
        dFilter.setChannel(params.getChannel());
        dFilter.setGroupsFilter(params.getGroupsList());
        dFilter.setTestCasesFilter(params.getTestCasesList());
    }
    
    private XmlSuite createSuite(ParamsBean paramsI) {
        XmlSuite suite = new XmlSuite();
        
        //Asignamos un nombre a la suite, definimos sus atributos y los listeners
        //suite.setName("TestMovilWeb");
        suite.setFileName("tng_Funcionales_Movil.xml");
        suite.setName(paramsI.getSuiteName());
        suite.setListeners(CommonsXML.createStandardListeners());

        //Creamos los parámetros comunes y los asociamos a la suite
        Map<String, String> parametersSuite = new HashMap<>();
        createCommonParamsSuite(parametersSuite, paramsI);
        suite.setParameters(parametersSuite);
        
        if (paramsI.getBrowser().compareTo(Constantes.BROWSERSTACK)==0) {
            //En el caso de BrowserStack paralelizaremos a nivel de los TestRuns (1 hilo x cada TestRun asociado a un dispositivo/browser)
            suite.setParallel(ParallelMode.TESTS);
            suite.setThreadCount(Constantes.BSTACK_PARALLEL);
            if (paramsI.getChannel()==Channel.movil_web) {
                joinSuiteWithTestRunMobilBStack(TypeWebDriver.browserstack, suite, CommonMangoDataForXML.bsMovilAndroid);
                joinSuiteWithTestRunMobilBStack(TypeWebDriver.browserstack, suite, CommonMangoDataForXML.bsMovilIOS);            
            } else {
                //joinSuiteWithTestRunDesktopBStack(TypeDriver.browserstack, suite, commonsXML.bsDktopWin10Explorer);
                joinSuiteWithTestRunDesktopBStack(TypeWebDriver.browserstack, suite, CommonMangoDataForXML.bsDktopOSXSafari);
                joinSuiteWithTestRunDesktopBStack(TypeWebDriver.browserstack, suite, CommonMangoDataForXML.bsDktopWin8Firefox);
            }
        } else {
            //En caso <> BrowserStack paralelizaremos a nivel de los Métodos (casos de prueba)
            suite.setParallel(ParallelMode.METHODS);
            suite.setThreadCount(3);
            
            //Sólo ejecutamos 1 TestRun
            createTestRunFilteredWithTestCases(suite, CommonsXML.getDescriptionTestRun(this.params), this.params.getGroups(), this.params.getTestCases());
        }
        
        return suite;
    }
    
    private void createCommonParamsSuite(Map<String, String> parametersSuite, ParamsBean paramsI) {
    	CommonMangoDataForXML.setCommonsParamsSuite(parametersSuite, paramsI);
    }
    
    private XmlTest joinSuiteWithTestRunMobilBStack(TypeWebDriver webdriverType, XmlSuite suite, BStackDataMovil bsMovil) {
        XmlTest testRun = CommonsXML.joinSuiteWithTestRunMobilBStack(webdriverType, suite, bsMovil);
        testRun.setGroups(createGroups());
        testRun.setXmlClasses(createClasses());
        FilterTNGxmlTRun.filterTestCasesToExec(testRun, dFilter);
        return testRun;
    }
    
    private XmlTest joinSuiteWithTestRunDesktopBStack(TypeWebDriver webdriverType, XmlSuite suite, BStackDataDesktop bsDesktop) {
        XmlTest testRun = CommonsXML.joinSuiteWithTestRunDesktopBStack(webdriverType, suite, bsDesktop);
        testRun.setGroups(createGroups());
        testRun.setXmlClasses(createClasses());
        FilterTNGxmlTRun.filterTestCasesToExec(testRun, dFilter);
        return testRun;
    }    
    
    private XmlTest createTestRunFilteredWithTestCases(XmlSuite suite, String testRunName, String[] groups, String[] testCaseList) {
        XmlTest testRun = CommonsXML.createTestRun(suite, testRunName, testCaseList);
        testRun.setGroups(createGroups());
        testRun.setXmlClasses(createClasses());
        FilterTNGxmlTRun.filterTestCasesToExec(testRun, dFilter);
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
        switch (this.params.getChannel()) {
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
    
    private List<XmlClass> createClasses() {
        List<XmlClass> listClasses = new ArrayList<>();
        
        //Existen un conjunto de tests que todavía no están implementados en el canal movil_web
        if (this.params.getChannel()==Channel.desktop) {
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
