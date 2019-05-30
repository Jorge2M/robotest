package com.mng.robotest.test80.arq.xmlprogram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import com.mng.robotest.test80.arq.jdbc.dao.CorreosGroupDAO;
import com.mng.robotest.test80.arq.listeners.CallBack;
import com.mng.robotest.test80.arq.utils.XmlTestP80;
import com.mng.robotest.test80.arq.utils.conf.AppTest;
import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.arq.utils.otras.Constantes;
import com.mng.robotest.test80.arq.utils.webdriver.BStackDataDesktop;
import com.mng.robotest.test80.arq.utils.webdriver.BStackDataMovil;
import com.mng.robotest.test80.arq.utils.webdriver.maker.FactoryWebdriverMaker.TypeWebDriver;

public class CommonsXML {

    public static String getDescriptionTestRun(ParamsBean params) {
        if (params.getVersion()==null) {
            return params.getAppE() + "-" + params.getChannel() + "-" + params.getBrowser();
        }
        return params.getVersion() + "-" + params.getAppE() + "-" + params.getChannel() + "-" + params.getBrowser();
    }
    
    public static ArrayList<String> getListOfPossibleGroups(Channel channel, AppTest appE) {
        ArrayList<String> listOfGroups = new ArrayList<>();
        listOfGroups.add("Canal:all_App:all");
        listOfGroups.add("Canal:all_App:" + appE);
        listOfGroups.add("Canal:" + channel + "_App:all");
        listOfGroups.add("Canal:" + channel + "_App:" + appE);
        return listOfGroups;
    }
    
    public static List<String> createStandardListeners() {
        List<String> listeners = new ArrayList<>();
        listeners.add("com.mng.robotest.test80.arq.listeners.MyTransformer");
        listeners.add("com.mng.robotest.test80.arq.listeners.InvokeListener");
        listeners.add("com.mng.robotest.test80.arq.listeners.Reporter");
        return listeners;
    }
    
    /**
     * Establecemos los parámetros genéricos a nivel de la Suite (válidos para todos los casos de prueba)
     * @param parametersSuite
     * @param params
     */
    public static void setCommonsParamsSuite(Map<String, String> parametersSuite, ParamsBean params) {
        parametersSuite.put(Constantes.paramUrlBase, params.getURLBase());
        parametersSuite.put(Constantes.paramSuiteExecInCtx, params.getIdSuiteExecution());
        parametersSuite.put(Constantes.paramVersionSuite, params.getVersion());
        parametersSuite.put(Constantes.paramApplicationDNS, params.getApplicationDNS());
        parametersSuite.put(Constantes.paramTypeAccessFmwk, params.getTypeAccess().toString());
        parametersSuite.put(Constantes.paramChannelSuite, params.getChannel().toString());
        parametersSuite.put(Constantes.paramAppEcomSuite, params.getAppE().toString());
        parametersSuite.put(Constantes.paramBrowser, params.getBrowser());
        parametersSuite.put(Constantes.paramBrwsPath, params.getBrowser());
        parametersSuite.put(Constantes.paramNetAnalysis, params.getNetAnalysis());
        if (params.getEnvioCorreo()!=null && "".compareTo(params.getEnvioCorreo())!=0) {
            parametersSuite.put(Constantes.paramEnvioCorreo, params.getEnvioCorreo());
            parametersSuite.put(Constantes.paramSiempreMail, "true");
            ArrayList<String> correosGroup = CorreosGroupDAO.getCorreosGroup(params.getEnvioCorreo());
            if (correosGroup!=null) {
                parametersSuite.put(Constantes.paramToListMail, toCommaSeparated(correosGroup));
            } else {
                parametersSuite.put(Constantes.paramToListMail, "eqp.ecommerce.qamango@mango.com");
            }
            
            parametersSuite.put(Constantes.paramCcListMail, "jorge.munoz.sge@mango.com");
            parametersSuite.put(Constantes.paramAsuntoMail,  "Result TestSuite " + params.getSuiteName() + " (" + params.getAppE() + " / " + params.getURLBase() + ")");
        }
        
        CallBack callBack = params.getCallBack();
        if (params.getCallBack()!=null) {
            parametersSuite.put(Constantes.paramCallBackMethod, callBack.getCallBackMethod());
            parametersSuite.put(Constantes.paramCallBackResource, callBack.getCallBackResource());
            parametersSuite.put(Constantes.paramCallBackSchema, callBack.getCallBackSchema());  
            parametersSuite.put(Constantes.paramCallBackParams, callBack.getCallBackParams());
            parametersSuite.put(Constantes.paramCallBackUser, callBack.getCallBackUser());
            parametersSuite.put(Constantes.paramCallBackPassword, callBack.getCallBackPassword());
        }

        //RecicleWD: Indica el modo de gestión de los webdriver/browsers
        //    true:  reutiliza el webdriver para futuros casos de prueba
        //    false: cuando acaba un caso de prueba cierra el webdriver
        if (params.getRecicleWD()!=null && params.getRecicleWD().compareTo("true")==0) {
            parametersSuite.put(Constantes.paramRecycleWD, "true");
        } else {
            parametersSuite.put(Constantes.paramRecycleWD, "false");
        }
    }
    
    public static XmlTest joinSuiteWithTestRunMobilBStack(TypeWebDriver webdriverType, XmlSuite suite, BStackDataMovil bsMovil) {
        XmlTest testRun = new XmlTestP80(suite);
        testRun.setName(suite.getName() + "_" + bsMovil.device);
        testRun.setPreserveOrder(Boolean.valueOf(true));
        
        //Creación de parámetros del test
        Map<String, String> parametersTest = new HashMap<>();
        parametersTest.put("brwsr-path", webdriverType.toString());
        bsMovil.setParameters(parametersTest);
        testRun.setParameters(parametersTest);
        
        return testRun;
    }
    
    public static XmlTest joinSuiteWithTestRunDesktopBStack(TypeWebDriver webdriverType, XmlSuite suite, BStackDataDesktop bsDesktop) {
        XmlTest testRun = new XmlTestP80(suite);
        testRun.setName(suite.getName() + "_" + bsDesktop.os + "_" + bsDesktop.browser + bsDesktop.browser_version);
        testRun.setPreserveOrder(Boolean.valueOf(true));
        
        //Creación de parámetros del test
        Map<String, String> parametersTest = new HashMap<>();
        parametersTest.put("brwsr-path", webdriverType.toString());
        bsDesktop.setParameters(parametersTest);
        testRun.setParameters(parametersTest);
        
        return testRun;
    }    

    public static XmlTest createTestRun(XmlSuite suite, String testRunName) {
        XmlTest testRun = new XmlTestP80(suite);
        testRun.setName(testRunName);
        testRun.setPreserveOrder(Boolean.valueOf(true));
        return testRun;
    }
    
    public static XmlTest createTestRun(XmlSuite suite, String testRunName, String[] testCaseList) {
        XmlTest testRun = createTestRun(suite, testRunName);
        return testRun;
    }
    
    protected static String toCommaSeparated(ArrayList<String> textToSplit) {
        return StringUtils.join(textToSplit, ',');
    }
}
