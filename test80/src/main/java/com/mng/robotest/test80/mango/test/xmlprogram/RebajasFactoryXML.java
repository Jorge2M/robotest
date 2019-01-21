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

@SuppressWarnings("javadoc")
public class RebajasFactoryXML {

    /**
     * Ejecución desde el Online
     * @param params
     *    params.version
     *          V1 - Sólo Registro
     *          V2 - Registro + posterior login + validación datos + logoff
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
        suite.setFileName("tng_PagosPaises.xml");
        suite.setName(params.getSuiteName());
        suite.setListeners(commonsXML.createStandardListeners());
        
        //suite.setObjectFactory(SingletonObjectFactory.instance());

        //Creamos los parámetros comunes y los asociamos a la suite
        Map<String, String> parametersSuite = new HashMap<>();
        createCommonParamsSuite(parametersSuite, params);
        suite.setParameters(parametersSuite);
        
        //En caso <> browserstack paralelizaremos a nivel de los métodos (casos de prueba)
        suite.setParallel(ParallelMode.METHODS);
        suite.setThreadCount(4);
        
        //Sólo ejecutamos 1 TestRun
        joinSuiteWithTestRunLocal(suite, "RegistrosTRun");
        
        return suite;
    }
    
    //Creación de los parámetros comunes a nivel de la Suite
    private void createCommonParamsSuite(Map<String, String> parametersSuite, ParamsBean params) {
        String version = params.getVersion();
        
        //Establecemos los parámetros genéricos (válidos para todos los casos de prueba)
        commonsXML.setCommonsParamsSuite(parametersSuite, params);

        //Control versiones
        switch (version) {
        case "V1":
            parametersSuite.put("loginAfterRegister", "false");
            break;
        default:
        case "V2":
            parametersSuite.put("loginAfterRegister", "true");
            break;
        }
        
        //Parámetros que especifican el tipo de canal
        if (params.getChannel()==Channel.movil_web)
            parametersSuite.put("isMobil", "true");
        else
            parametersSuite.put("isMobil", "false");

        //Indica si hemos de realizar las pruebas introduciendo una tarjeta de empleado como vale descuento (Más adelante esto dependerá del parámetro 'versión')
        parametersSuite.put("isEmpl", "false"); 
        
        //Parámetros que especifican el tipo de canal
        parametersSuite.put("isOutlet", "false");
        parametersSuite.put("isVOTF", "false");
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
        run.onInclude("SupportsFactoryCountrys");
        return run;
    }
    
    private XmlDependencies createDependencies() {
        XmlDependencies dependencies = new XmlDependencies();
        return dependencies;
    }
    
    private List<XmlClass> createClasses() {
        List<XmlClass> listClasses = new ArrayList<>();
        listClasses.add(new XmlClass("com.mng.robotest.test80.mango.test.factoryes.ListRebajasXPais"));
        
        return listClasses;
    }
}
