package com.mng.robotest.test80.mango.test.suites;

import java.util.HashMap;
import java.util.Map;

import com.github.jorge2m.testmaker.service.webdriver.maker.FactoryWebdriverMaker.EmbeddedDriver;
//import com.github.jorge2m.testmaker.listeners.CallBack;
import com.mng.robotest.test80.access.InputParamsMango;
import com.mng.robotest.test80.mango.test.data.Constantes;

public class SuiteMakerResources {
    
    public static Map<String,String> getParametersSuiteShop(InputParamsMango params) {
    	Map<String,String> parametersReturn = new HashMap<>();
    	
    	parametersReturn.put(Constantes.paramCountrys, params.getListaPaisesCommaSeparated());
    	parametersReturn.put(Constantes.paramLineas, params.getListaLineasCommaSeparated());
    	parametersReturn.put(Constantes.paramPayments, params.getListaPaymentsCommaSeparated());

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
    
    public static boolean isBrowserStack(String browser) {
    	return (browser.compareTo(EmbeddedDriver.browserstack.name())==0);
    }
}
