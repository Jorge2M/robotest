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

import com.mng.robotest.test80.arq.utils.filter.DataFilterTCases;
import com.mng.robotest.test80.arq.utils.filter.FilterTestsSuiteXML;
import com.mng.robotest.test80.arq.utils.filter.TestMethod;
import com.mng.robotest.test80.arq.xmlprogram.ParamsBean;
import com.mng.robotest.test80.arq.xmlprogram.CommonsXML;

public class SmokeMantoXML {

    ParamsBean params = null;
    final DataFilterTCases dFilter = new DataFilterTCases();
    
    /**
     * Ejecución desde el Online
     * @param params
     */
    public void testRunner(ParamsBean paramsToStore) {
        this.params = paramsToStore;
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
        
        XmlSuite xmlSuite = createSuite(this.params);
        XmlTest testRun = xmlSuite.getTests().get(0);
        return (
        	FilterTestsSuiteXML.getInitialTestCaseCandidatesToExecute(testRun, dFilter.getChannel(), dFilter.getAppE())
        );
    }
    
    //Esto ha de estar en una clase padre
    private void setDataFilterFromParams() {
        dFilter.setAppE(params.getAppE());
        dFilter.setChannel(params.getChannel());
        dFilter.setGroupsFilter(params.getGroupsList());
        dFilter.setTestCasesFilter(params.getTestCasesList());
    }
    
    public XmlSuite createSuite(ParamsBean paramsI) {
        
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
        
        //En caso <> BrowserStack paralelizaremos a nivel de los Métodos (casos de prueba)
        suite.setParallel(ParallelMode.METHODS);
        suite.setThreadCount(3);
        
        //Sólo ejecutamos 1 TestRun
        createTestRunFilteredWithTestCases(suite, CommonsXML.getDescriptionTestRun(this.params), this.params.getGroups(), this.params.getTestCases());
        
        return suite;
    }
    
    //Creación de los parámetros comunes a nivel de la Suite
    private void createCommonParamsSuite(Map<String, String> parametersSuite, ParamsBean paramsI) {
    	CommonMangoDataForXML.setCommonsParamsSuite(parametersSuite, paramsI);
    }
    
    public XmlTest createTestRunFilteredWithTestCases(XmlSuite suite, String testRunName, String groups[], String[] testCaseList) {
        XmlTest testRun = CommonsXML.createTestRun(suite, testRunName, testCaseList);
        testRun.setGroups(createGroups());
        testRun.setXmlClasses(createClasses());
        FilterTestsSuiteXML.filterTestCasesToExec(testRun, dFilter);
        return testRun;
    }    
    
    private XmlGroups createGroups() {
        XmlGroups groups = new XmlGroups();
        groups.setRun(createRun());
        return groups;
    }    
    
    private XmlRun createRun() {
        XmlRun run = new XmlRun();
        for (String group : CommonsXML.getListOfPossibleGroups(this.params.getChannel(), this.params.getAppE()))
            run.onInclude(group);
        
        return run;
    }
    
    private List<XmlClass> createClasses() {
        List<XmlClass> listClasses = new ArrayList<>();
        listClasses.add(new XmlClass("com.mng.robotest.test80.mango.test.appmanto.Manto"));
        return listClasses;
    }
    
    public ParamsBean getParams() {
        return this.params;
    }
}
