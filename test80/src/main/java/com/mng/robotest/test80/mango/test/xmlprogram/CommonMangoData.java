package com.mng.robotest.test80.mango.test.xmlprogram;

import java.util.HashMap;
import java.util.Map;

import com.mng.robotest.test80.arq.listeners.CallBack;
import com.mng.robotest.test80.arq.utils.otras.Constantes;
import com.mng.robotest.test80.arq.utils.webdriver.BStackDataDesktop;
import com.mng.robotest.test80.arq.utils.webdriver.BStackDataMovil;
import com.mng.robotest.test80.arq.utils.webdriver.EnumsBrowserStack.PlatformDesktopBS;
import com.mng.robotest.test80.arq.utils.webdriver.EnumsBrowserStack.PlatformMobilBS;
import com.mng.robotest.test80.arq.xmlprogram.ParamsBean;

public class CommonMangoData {
    
    public static BStackDataMovil bsMovilAndroid = new BStackDataMovil(PlatformMobilBS.Android.getValueaAPI(), "7.0", "Samsung Galaxy S8", "true", "chrome");
    public static BStackDataMovil bsMovilIOS = new BStackDataMovil(PlatformMobilBS.iOS.getValueaAPI(), "11.0", "iPhone 8", "true", "safari"); 
    
    public static BStackDataDesktop bsDktopWin10Explorer = new BStackDataDesktop(PlatformDesktopBS.Windows.getValueaAPI(), "10", "Edge", "16.0", "1920x1080");
    public static BStackDataDesktop bsDktopWin8Firefox = new BStackDataDesktop(PlatformDesktopBS.Windows.getValueaAPI(), "10", "Firefox", "62.0", "1920x1080");
    public static BStackDataDesktop bsDktopOSXSafari = new BStackDataDesktop(PlatformDesktopBS.OSX.getValueaAPI(), "High Sierra", "Safari", "11.0", "1920x1080");
    
    public static Map<String,String> getParametersSuiteShop(ParamsBean params) {
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
        
        //Parámetro para el caso de prueba REG002 que indica si posteriormente al registro es preciso loginarse + validar datos + logoff
        parametersReturn.put("loginAfterRegister" ,"true");
        parametersReturn.put("register", "true");
        
        //Credenciales para la identificación en el servicio de BrowserStack
        parametersReturn.put(Constantes.UserBStack, "equipoqa1");
        parametersReturn.put(Constantes.PassBStack, "qp3dr5VJbFMAxPsT4k1b");
        
        CallBack callBack = params.getCallBack();
        if (params.getCallBack()!=null) {
        	parametersReturn.put(Constantes.paramCallBackMethod, callBack.getCallBackMethod());
        	parametersReturn.put(Constantes.paramCallBackResource, callBack.getCallBackResource());
        	parametersReturn.put(Constantes.paramCallBackSchema, callBack.getCallBackSchema());  
        	parametersReturn.put(Constantes.paramCallBackParams, callBack.getCallBackParams());
        	parametersReturn.put(Constantes.paramCallBackUser, callBack.getCallBackUser());
        	parametersReturn.put(Constantes.paramCallBackPassword, callBack.getCallBackPassword());
        }
        
        return parametersReturn;
    }
}
