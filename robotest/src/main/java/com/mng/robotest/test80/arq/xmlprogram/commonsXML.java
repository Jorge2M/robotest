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
import com.mng.robotest.test80.arq.utils.webdriver.EnumsBrowserStack.PlatformDesktopBS;
import com.mng.robotest.test80.arq.utils.webdriver.EnumsBrowserStack.PlatformMobilBS;
import com.mng.robotest.test80.arq.utils.webdriver.maker.FactoryWebdriverMaker.TypeWebDriver;

public class commonsXML {
    
    public static BStackDataMovil bsMovilAndroid = new BStackDataMovil(PlatformMobilBS.Android.getValueaAPI(), "7.0", "Samsung Galaxy S8", "true", "chrome");
    public static BStackDataMovil bsMovilIOS = new BStackDataMovil(PlatformMobilBS.iOS.getValueaAPI(), "11.0", "iPhone 8", "true", "safari"); 
    
    public static BStackDataDesktop bsDktopWin10Explorer = new BStackDataDesktop(PlatformDesktopBS.Windows.getValueaAPI(), "10", "Edge", "16.0", "1920x1080");
    public static BStackDataDesktop bsDktopWin8Firefox = new BStackDataDesktop(PlatformDesktopBS.Windows.getValueaAPI(), "10", "Firefox", "62.0", "1920x1080");
    public static BStackDataDesktop bsDktopOSXSafari = new BStackDataDesktop(PlatformDesktopBS.OSX.getValueaAPI(), "High Sierra", "Safari", "11.0", "1920x1080");
    
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

        //Lista de países (de tipo "001,030,...")
        parametersSuite.put(Constantes.paramCountrys, params.getListaPaisesStr());
        
        //Filtro de líneas (de tipo "she,he,ninos,...")
        parametersSuite.put(Constantes.paramLineas, params.getListaLineasStr());
        
        //Filtro de pagos (de tipo el valor del nombre del pago en el XML de países, p.e. "VISA, TARJETA MANGO, AMERICAN EXPRESS...")
        parametersSuite.put(Constantes.paramPayments, params.getListaPaymentsStr());        
        
        //RecicleWD: Indica el modo de gestión de los webdriver/browsers
        //    true:  reutiliza el webdriver para futuros casos de prueba
        //    false: cuando acaba un caso de prueba cierra el webdriver
        if (params.getRecicleWD()!=null && params.getRecicleWD().compareTo("true")==0) {
            parametersSuite.put(Constantes.paramRecycleWD, "true");
        } else {
            parametersSuite.put(Constantes.paramRecycleWD, "false");
        }
        
        //Credenciales acceso a Manto
        parametersSuite.put(Constantes.paramUsrmanto, Constantes.userManto);
        parametersSuite.put(Constantes.paramPasmanto, Constantes.passwordManto);
        if (params.getUrlManto()!=null) {
            parametersSuite.put(Constantes.paramUrlmanto, params.getUrlManto());
        } else {
            parametersSuite.put(Constantes.paramUrlmanto, "http://manto.pre.mango.com");
        }
        
        //Parámetros de acceso a Proxy
        parametersSuite.put("proxyHost", "");
        parametersSuite.put("proxyPort", "");
        parametersSuite.put("proxyUser", "");
        parametersSuite.put("proxyPassword", "");
        
        //Indica si accedemos al XML de Countrys mediante URL o fichero
        parametersSuite.put("accesoURLCountrys", "false");
        
        //Especificación path del "output-library" respecto al directorio de ejecución (donde está la BD SQLite y se grabarán el resultado HTML)
        parametersSuite.put("outputReports", "../../" + Constantes.directoryOutputTests + "/"); 
        
        //Producto inexistente
        parametersSuite.put("prodInexistente", "91439153");
        
        //Lista separada por comas de referencias de productos a incluir en las pruebas de compra
        parametersSuite.put("masProductos", ""); 
        
        //Importe a partir del cual el precio del transporte es 0
        parametersSuite.put("transporteExento", "30");
        
        //Importe del transporte en España (Península y Baleares)
        parametersSuite.put("importeTransporte", "2,95€");
        
        parametersSuite.put("categoriaProdExistente", "BOLSOS");
        parametersSuite.put("catProdInexistente", "Anchoas del Cantábrico");
        
        //Credenciales para el acceso a la shop de un usuario
        //parametersSuite.put("usuarioRegistrado", "jorge.munoz@mango.com"); 
        //parametersSuite.put("passwordUsuario", "sirjorge74");
        
        //Usuario con compras de ambos tipos (tienda, online) en PRE pero sólo de tipo online en PRO
        parametersSuite.put("userConComprasPeroSoloOnlineEnPRO" ,"espana.test@mango.com");
        parametersSuite.put("passwordUserConCompras" ,"mango123");
        
        parametersSuite.put("userConDevolucionPeroSoloEnPRO" ,"robot.test@mango.com");
        parametersSuite.put("passwordUserConDevolucion" ,"sirjorge74");
        
        //Parámetro para el caso de prueba REG002 que indica si posteriormente al registro es preciso loginarse + validar datos + logoff
        parametersSuite.put("loginAfterRegister" ,"true");
    	parametersSuite.put("register", "true");
        
        //Credenciales para la identificación en el servicio de BrowserStack
        parametersSuite.put(Constantes.UserBStack, "equipoqa1");
        parametersSuite.put(Constantes.PassBStack, "qp3dr5VJbFMAxPsT4k1b");
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
