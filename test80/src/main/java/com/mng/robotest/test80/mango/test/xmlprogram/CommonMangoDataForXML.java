package com.mng.robotest.test80.mango.test.xmlprogram;

import java.util.Map;

import com.mng.robotest.test80.arq.utils.otras.Constantes;
import com.mng.robotest.test80.arq.utils.webdriver.BStackDataDesktop;
import com.mng.robotest.test80.arq.utils.webdriver.BStackDataMovil;
import com.mng.robotest.test80.arq.utils.webdriver.EnumsBrowserStack.PlatformDesktopBS;
import com.mng.robotest.test80.arq.utils.webdriver.EnumsBrowserStack.PlatformMobilBS;
import com.mng.robotest.test80.arq.xmlprogram.ParamsBean;
import com.mng.robotest.test80.arq.xmlprogram.CommonsXML;

public class CommonMangoDataForXML {
    
    public static BStackDataMovil bsMovilAndroid = new BStackDataMovil(PlatformMobilBS.Android.getValueaAPI(), "7.0", "Samsung Galaxy S8", "true", "chrome");
    public static BStackDataMovil bsMovilIOS = new BStackDataMovil(PlatformMobilBS.iOS.getValueaAPI(), "11.0", "iPhone 8", "true", "safari"); 
    
    public static BStackDataDesktop bsDktopWin10Explorer = new BStackDataDesktop(PlatformDesktopBS.Windows.getValueaAPI(), "10", "Edge", "16.0", "1920x1080");
    public static BStackDataDesktop bsDktopWin8Firefox = new BStackDataDesktop(PlatformDesktopBS.Windows.getValueaAPI(), "10", "Firefox", "62.0", "1920x1080");
    public static BStackDataDesktop bsDktopOSXSafari = new BStackDataDesktop(PlatformDesktopBS.OSX.getValueaAPI(), "High Sierra", "Safari", "11.0", "1920x1080");
    
    /**
     * Establecemos los parámetros genéricos a nivel de la Suite (válidos para todos los casos de prueba)
     * @param parametersSuite
     * @param params
     */
    public static void setCommonsParamsSuite(Map<String, String> parametersSuite, ParamsBean params) {
    	CommonsXML.setCommonsParamsSuite(parametersSuite, params);
    	
        //Lista de países (de tipo "001,030,...")
        parametersSuite.put(Constantes.paramCountrys, params.getListaPaisesStr());
        
        //Filtro de líneas (de tipo "she,he,ninos,...")
        parametersSuite.put(Constantes.paramLineas, params.getListaLineasStr());
        
        //Filtro de pagos (de tipo el valor del nombre del pago en el XML de países, p.e. "VISA, TARJETA MANGO, AMERICAN EXPRESS...")
        parametersSuite.put(Constantes.paramPayments, params.getListaPaymentsStr());        

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
}
