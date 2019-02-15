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
import com.mng.robotest.test80.arq.utils.filter.FilterTNGxmlTRun;
import com.mng.robotest.test80.arq.utils.filter.TestMethod;


public class SmokeMantoXML {

    ParamsBean params = null;
    
    /**
     * Ejecución desde el Online
     * @param params
     */
    public void testRunner(ParamsBean paramsToStore) {
        this.params = paramsToStore;
        
        // Lista de suites (sólo creamos una)
        List<XmlSuite> suites = new ArrayList<>();
        suites.add(createSuite(this.params));    
          
        TestNG tng = new TestNG();
        tng.setXmlSuites(suites);
        tng.setUseDefaultListeners(false);
        tng.run();
    }
    
    public ArrayList<TestMethod> getDataTestAnnotationsToExec(ParamsBean paramsToStore) {
        this.params = paramsToStore;
        XmlSuite xmlSuite = createSuite(this.params);
        return FilterTNGxmlTRun.getListOfTestAnnotationsOfTCasesToExecute(xmlSuite.getTests().get(0), this.params.getChannel(), this.params.getAppE());
    }
    
    public XmlSuite createSuite(ParamsBean paramsI) {
        
        XmlSuite suite = new XmlSuite();
        
        //Asignamos un nombre a la suite, definimos sus atributos y los listeners
        //suite.setName("TestMovilWeb");
        suite.setFileName("tng_Funcionales_Movil.xml");
        suite.setName(paramsI.getSuiteName());
        suite.setListeners(commonsXML.createStandardListeners());

        //Creamos los parámetros comunes y los asociamos a la suite
        Map<String, String> parametersSuite = new HashMap<>();
        createCommonParamsSuite(parametersSuite, paramsI);
        suite.setParameters(parametersSuite);
        
        //En caso <> BrowserStack paralelizaremos a nivel de los Métodos (casos de prueba)
        suite.setParallel(ParallelMode.METHODS);
        suite.setThreadCount(3);
        
        //Sólo ejecutamos 1 TestRun
        createTestRunFilteredWithTestCases(suite, commonsXML.getDescriptionTestRun(this.params), this.params.getListaTestCases());
        
        return suite;
    }
    
    //Creación de los parámetros comunes a nivel de la Suite
    private void createCommonParamsSuite(Map<String, String> parametersSuite, ParamsBean paramsI) {
        commonsXML.setCommonsParamsSuite(parametersSuite, paramsI);
    }
    
    public XmlTest createTestRunFilteredWithTestCases(XmlSuite suite, String testRunName, String[] testCaseList) {
        XmlTest testRun = commonsXML.createTestRun(suite, testRunName, testCaseList);
        testRun.setGroups(createGroups());
        testRun.setXmlClasses(createClasses());
        FilterTNGxmlTRun.filterWithTCasesToExec(testRun, testCaseList, this.params.getChannel(), this.params.getAppE());
        return testRun;
    }    
    
    private XmlGroups createGroups() {
        XmlGroups groups = new XmlGroups();
        groups.setRun(createRun());
        return groups;
    }    
    
    private XmlRun createRun() {
        XmlRun run = new XmlRun();
        for (String group : commonsXML.getListOfPossibleGroups(this.params.getChannel(), this.params.getAppE()))
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
