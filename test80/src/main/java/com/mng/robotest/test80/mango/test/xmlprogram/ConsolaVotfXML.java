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

import com.mng.robotest.test80.arq.xmlprogram.ParamsBean;
import com.mng.robotest.test80.arq.xmlprogram.CommonsXML;

public class ConsolaVotfXML {

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
          
        //Creamos el XML de TestNG asignándole la suite
        TestNG tng = new TestNG();
        tng.setXmlSuites(suites);
        tng.setUseDefaultListeners(false);
        tng.run();
    }
    
    private XmlSuite createSuite(ParamsBean paramsI) {
        
        XmlSuite suite = new XmlSuite();
        
        //Asignamos un nombre a la suite, definimos sus atributos y los listeners
        //suite.setName("TestMovilWeb");
        suite.setFileName("tng_ConsolaVotf.xml");
        suite.setName(paramsI.getSuiteName());
        suite.setListeners(CommonsXML.createStandardListeners());

        //Creamos los parámetros comunes y los asociamos a la suite
        Map<String, String> parametersSuite = new HashMap<>();
        createCommonParamsSuite(parametersSuite, paramsI);
        suite.setParameters(parametersSuite);
        
        suite.setParallel(ParallelMode.METHODS);
        suite.setThreadCount(1);
            
        //Sólo ejecutamos 1 TestRun
        joinSuiteWithTestRunLocal(suite, "ConsolaVotfTestRun");
        
        return suite;
    }
    
    //Creación de los parámetros comunes a nivel de la Suite
    private void createCommonParamsSuite(Map<String, String> parametersSuite, ParamsBean paramsI) {
        
        //Establecemos los parámetros genéricos (válidos para todos los casos de prueba)
    	CommonMangoData.setCommonsParamsSuite(parametersSuite, paramsI);
        
        //Código artículo (referencia + código talla + código color) en stock y que no esté en rebajas
        parametersSuite.put("prodDisponible1", "3300049936TN");
        
        //Indicamos que el mail sólo se envíe cuando se produce un NOK en el test
        parametersSuite.put("siempreMail", "false"); 
        
        //Lista de destinatarios correo separados por coma
        parametersSuite.put("ToList", "laura.rios.sge@mango.com"); 
        parametersSuite.put("CcList", "jorge.munoz.sge@mango.com");
        parametersSuite.put("Asunto", "Resultado Test VOTF");
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
        listClasses.add(new XmlClass("com.mng.robotest.test80.mango.test.appshop.ConsolaVotf"));
        return listClasses;
    }
}
