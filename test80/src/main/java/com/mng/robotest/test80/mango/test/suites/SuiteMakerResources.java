package com.mng.robotest.test80.mango.test.suites;

import static com.mng.robotest.test80.mango.test.data.BrowserStackCtxDesktop.macOSMojave_Safari;
import static com.mng.robotest.test80.mango.test.data.BrowserStackCtxDesktop.Win8_Firefox68;
import static com.mng.robotest.test80.mango.test.data.BrowserStackCtxMobil.IPhoneXS_iOS12;
import static com.mng.robotest.test80.mango.test.data.BrowserStackCtxMobil.SamsungGalaxyS9plus_Android9;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mng.robotest.test80.InputParams;
//import com.mng.testmaker.listeners.CallBack;
import com.mng.testmaker.utils.otras.Channel;
import com.mng.testmaker.service.webdriver.maker.FactoryWebdriverMaker.WebDriverType;
import com.mng.robotest.test80.mango.test.data.Constantes;
import com.mng.testmaker.domain.TestRunMaker;

public class SuiteMakerResources {
    
    public static Map<String,String> getParametersSuiteShop(InputParams params) {
    	Map<String,String> parametersReturn = new HashMap<>();
    	
    	parametersReturn.put(Constantes.paramCountrys, params.getListaPaisesStr());
    	parametersReturn.put(Constantes.paramLineas, params.getListaLineasStr());
    	parametersReturn.put(Constantes.paramPayments, params.getListaPaymentsStr());        

        //Credenciales acceso a Manto
    	parametersReturn.put(Constantes.paramUsrmanto, Constantes.userManto);
    	parametersReturn.put(Constantes.paramPasmanto, Constantes.passwordManto);
        if (params.getUrlManto()!=null) {
        	parametersReturn.put(Constantes.paramUrlmanto, params.getUrlManto());
        } else {
        	//parametersSuite.put(Constantes.paramUrlmanto, "https://ogiol-zfs-manto.dev.mango.com");
        	parametersReturn.put(Constantes.paramUrlmanto, "http://manto.pre.mango.com");
        }
        
        //Parámetros de acceso a Proxy
        parametersReturn.put("proxyHost", "");
        parametersReturn.put("proxyPort", "");
        parametersReturn.put("proxyUser", "");
        parametersReturn.put("proxyPassword", "");
        
        parametersReturn.put("accesoURLCountrys", "false");
        parametersReturn.put("prodInexistente", "91439153");
        parametersReturn.put("masProductos", ""); 
        parametersReturn.put("transporteExento", "30");
        
        //Importe del transporte en España (Península y Baleares)
        parametersReturn.put("importeTransporte", "2,95€");
        
        parametersReturn.put("categoriaProdExistente", "BOLSOS");
        parametersReturn.put("catProdInexistente", "Anchoas del Cantábrico");
        parametersReturn.put("userWithOnlinePurchases" ,"espana.test@mango.com");
        parametersReturn.put("passUserWithOnlinePurchases" ,"mango123");
        parametersReturn.put("userWithStorePurchases" ,"ticket_digital_es@mango.com");
        parametersReturn.put("passUserWithStorePurchases" ,"mango123");
        
        parametersReturn.put("userConDevolucionPeroSoloEnPRO" ,"robot.test@mango.com");
        parametersReturn.put("passwordUserConDevolucion" ,"sirjorge74");
        
//        CallBack callBack = params.getCallBack();
//        if (params.getCallBack()!=null) {
//        	parametersReturn.put(Constantes.paramCallBackMethod, callBack.getCallBackMethod());
//        	parametersReturn.put(Constantes.paramCallBackResource, callBack.getCallBackResource());
//        	parametersReturn.put(Constantes.paramCallBackSchema, callBack.getCallBackSchema());  
//        	parametersReturn.put(Constantes.paramCallBackParams, callBack.getCallBackParams());
//        	parametersReturn.put(Constantes.paramCallBackUser, callBack.getCallBackUser());
//        	parametersReturn.put(Constantes.paramCallBackPassword, callBack.getCallBackPassword());
//        }
        
        return parametersReturn;
    }
    
    public static boolean isBrowserStack(WebDriverType WebDriverType) {
    	return (WebDriverType==WebDriverType.browserstack);
    }
    
    public static List<TestRunMaker> getTestRunsForBrowserStack(String suiteName, Channel channel, List<Class<?>> listClasses) {
    	List<TestRunMaker> listTestsRun = new ArrayList<>();
    	switch (channel) {
    	case desktop:
			TestRunMaker testRunOSX = TestRunMaker.from(suiteName + "_" + macOSMojave_Safari, listClasses);
			testRunOSX.setBrowserStackDesktop(macOSMojave_Safari);
			listTestsRun.add(testRunOSX);
			TestRunMaker testRunWin8 = TestRunMaker.from(suiteName + Win8_Firefox68, listClasses);
			testRunWin8.setBrowserStackDesktop(Win8_Firefox68);
			listTestsRun.add(testRunWin8);
			break;
	    case movil_web:
			TestRunMaker testRunAndroid = TestRunMaker.from(suiteName + SamsungGalaxyS9plus_Android9, listClasses);
			testRunAndroid.setBrowserStackMobil(SamsungGalaxyS9plus_Android9);
			listTestsRun.add(testRunAndroid);
			TestRunMaker testRuniOS = TestRunMaker.from(suiteName + IPhoneXS_iOS12, listClasses);
			testRuniOS.setBrowserStackMobil(IPhoneXS_iOS12);
			listTestsRun.add(testRuniOS);
    	}
    	
    	return listTestsRun;
    }
}
