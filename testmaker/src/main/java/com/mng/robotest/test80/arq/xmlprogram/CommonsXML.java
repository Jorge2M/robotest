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
