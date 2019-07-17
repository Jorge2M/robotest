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

    public SmokeTestXML(ParamsBean params, TestMaker testMaker) {
    	super(testMaker);
    	Channel channel = params.getChannel();
    	setClassesWithTests(getClasses(channel));
    	setDependencyGroups(getDependencyGroups());
    }
    
    private static void createCommonParamsSuite(Map<String, String> parametersSuite, ParamsBean paramsI) {
    	CommonMangoDataForXML.setCommonsParamsSuite(parametersSuite, paramsI);
    }
    
    private static XmlTest joinSuiteWithTestRunMobilBStack(TypeWebDriver webdriverType, ParamsBean params, XmlSuite suite, BStackDataMovil bsMovil) {
        XmlTest testRun = CommonsXML.joinSuiteWithTestRunMobilBStack(webdriverType, suite, bsMovil);
        testRun.setGroups(createGroups(params));
        testRun.setXmlClasses(createClasses(params));
        //filterTestCasesToExec(testRun);
        return testRun;
    }
    
    private static XmlTest joinSuiteWithTestRunDesktopBStack(TypeWebDriver webdriverType, ParamsBean params, XmlSuite suite, BStackDataDesktop bsDesktop) {
        XmlTest testRun = CommonsXML.joinSuiteWithTestRunDesktopBStack(webdriverType, suite, bsDesktop);
        testRun.setGroups(createGroups(params));
        testRun.setXmlClasses(createClasses(params));
        //filterTestCasesToExec(testRun);
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
    
    /**
     * @return Map with pair: group <- group
     */
    //ok
    private Map<String,String> getDependencyGroups() {
    	Map<String,String> dependencyGroups = new HashMap<>();
    	return dependencyGroups;
    }
    
    //ok
    private static List<String> getClasses(Channel channel) {
        List<String> listClasses = new ArrayList<>();
        if (channel==Channel.desktop) {
            listClasses.add("com.mng.robotest.test80.mango.test.appshop.Otras");
            listClasses.add("com.mng.robotest.test80.mango.test.appshop.SEO");
            listClasses.add("com.mng.robotest.test80.mango.test.appshop.IniciarSesion");
            listClasses.add("com.mng.robotest.test80.mango.test.appshop.Bolsa");
        }
        listClasses.add("com.mng.robotest.test80.mango.test.appshop.FichaProducto");
        listClasses.add("com.mng.robotest.test80.mango.test.appshop.Ayuda");
        listClasses.add("com.mng.robotest.test80.mango.test.appshop.Buscador");
        listClasses.add("com.mng.robotest.test80.mango.test.appshop.Footer");
        listClasses.add("com.mng.robotest.test80.mango.test.appshop.Registro");
        listClasses.add("com.mng.robotest.test80.mango.test.appshop.PaisIdioma");
        listClasses.add("com.mng.robotest.test80.mango.test.appshop.GaleriaProducto");
        listClasses.add("com.mng.robotest.test80.mango.test.appshop.Compra");
        listClasses.add("com.mng.robotest.test80.mango.test.factoryes.ListPagosEspana");
        listClasses.add("com.mng.robotest.test80.mango.test.appshop.MiCuenta");
        listClasses.add("com.mng.robotest.test80.mango.test.appshop.Favoritos");
        listClasses.add("com.mng.robotest.test80.mango.test.appshop.Reembolsos");
        listClasses.add("com.mng.robotest.test80.mango.test.appshop.Loyalty");
        return listClasses;
    }
    
    public ParamsBean getParams() {
        return this.params;
    }
}
