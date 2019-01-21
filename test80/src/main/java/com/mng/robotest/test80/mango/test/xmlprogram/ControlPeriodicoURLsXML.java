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
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;

/**
 * @author jorge.munoz
 *
 */
public class ControlPeriodicoURLsXML {

    /**
     * Ejecución desde el Online
     * @param params
     *          version
     *                  V1: Funcionalidad estándard
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
        suite.setFileName("tng_ControlPeriodicoURLs.xml");
        suite.setName(params.getSuiteName());
        suite.setParallel(ParallelMode.METHODS);
        suite.setThreadCount(3);
        suite.setListeners(createListeners());

        //Creamos los parámetros comunes y los asociamos a la suite
        Map<String, String> parametersSuite = new HashMap<>();
        createCommonParamsSuite(parametersSuite, params);
        suite.setParameters(parametersSuite);
        
        //Sólo ejecutamos 1 TestRun
        joinSuiteWithTestRunLocal(suite, "ControlPeriodicoURLs");
        
        return suite;
    }
    
    private List<String> createListeners() {
        List<String> listeners = new ArrayList<>();
        listeners.add("com.mng.robotest.test80.arq.listeners.PriorityInterceptor");
        listeners.add("com.mng.robotest.test80.arq.listeners.MyTransformer");
        listeners.add("com.mng.robotest.test80.arq.listeners.InvokeListener");
        listeners.add("com.mng.robotest.test80.arq.listeners.Reporter");
        return listeners;
    }
    
    //Creación de los parámetros comunes a nivel de la Suite
    private void createCommonParamsSuite(Map<String, String> parametersSuite, ParamsBean params) {
        
        //Establecemos los parámetros genéricos (válidos para todos los casos de prueba)
        commonsXML.setCommonsParamsSuite(parametersSuite, params);
        
        //Especifica el tipo de test
        //1: Test países-idiomas-líneas-banners
        //2: Test países-idiomas-registro
        //3: Test acceso país + cambio país
        //4: Test control periódico URLs
        parametersSuite.put("typeTest", "4");
        
        //Indica si hemos de recorrer todos los menús de cada una de las páginas
        parametersSuite.put("recorreMenus", "false");
        
        //Indica si hemos de recorrer todos los banners de cada una de las páginas
        parametersSuite.put("recorreBanners", "false");
        
        //Parámetros que especifican el tipo de canal
        if (params.getChannel()==Channel.movil_web)
            parametersSuite.put("isMobile", "true");
        else
            parametersSuite.put("isMobile", "false");
        
        parametersSuite.put("isOutlet", "false");
        parametersSuite.put("isVOTF", "false");
    }
    
    @SuppressWarnings("javadoc")
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
        //run.onInclude("ControlPeriodicoURLs");
        return run;
    }
    
    private XmlDependencies createDependencies() {
        XmlDependencies dependencies = new XmlDependencies();
        return dependencies;
    }
    
    private List<XmlClass> createClasses() {
        List<XmlClass> listClasses = new ArrayList<>();
        listClasses.add(new XmlClass("com.mng.robotest.test80.mango.test.factoryes.ListAllCountrys"));
        return listClasses;
    }
}
