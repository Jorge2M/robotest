package com.mng.robotest.test.suites;

import java.util.HashMap;
import java.util.Map;

import com.github.jorge2m.testmaker.service.webdriver.maker.FactoryWebdriverMaker.EmbeddedDriver;
import com.mng.robotest.access.InputParamsMango;
import com.mng.robotest.test.data.Constantes;
import com.mng.robotest.test.utils.awssecrets.GetterSecrets;
import com.mng.robotest.test.utils.awssecrets.Secret;
import com.mng.robotest.test.utils.awssecrets.GetterSecrets.SecretType;


public class SuiteMakerResources {
	
	private SuiteMakerResources() {}
	
	public static Map<String,String> getParametersSuiteShop(InputParamsMango params) {
		Map<String,String> parametersReturn = new HashMap<>();
		
		parametersReturn.put(Constantes.PARAM_COUNTRYS, params.getListaPaisesCommaSeparated());
		parametersReturn.put(Constantes.PARAM_LINEAS, params.getListaLineasCommaSeparated());
		parametersReturn.put(Constantes.PARAM_PAYMENTS, params.getListaPaymentsCommaSeparated());
		parametersReturn.put(Constantes.PARAM_CATALOGS, params.getListaCatalogsCommaSeparated());

		//Credenciales acceso a Manto
		Secret secret = GetterSecrets.factory().getCredentials(SecretType.MANTO_USER);
		parametersReturn.put(Constantes.PARAM_USR_MANTO, secret.getUser());
		parametersReturn.put(Constantes.PARAM_PAS_MANTO, secret.getPassword());
		if (params.getUrlManto()!=null) {
			parametersReturn.put(Constantes.PARAM_URL_MANTO, params.getUrlManto());
		} else {
			//parametersSuite.put(Constantes.paramUrlmanto, "https://ogiol-zfs-manto.dev.mango.com");
			parametersReturn.put(Constantes.PARAM_URL_MANTO, "http://manto.pre.mango.com");
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
		
		String PASSWORD_STANDARD = 
				GetterSecrets.factory()
					.getCredentials(SecretType.SHOP_STANDARD_USER)
					.getPassword();
		parametersReturn.put("userWithOnlinePurchases" ,"espana.test@mango.com");
		parametersReturn.put("passUserWithOnlinePurchases", PASSWORD_STANDARD);
		
		parametersReturn.put("userWithStorePurchases" ,"ticket_digital_es@mango.com");
		parametersReturn.put("passUserWithStorePurchases", PASSWORD_STANDARD);
		
		String PASSWORD_ROBOT = 
				GetterSecrets.factory()
					.getCredentials(SecretType.SHOP_ROBOT_USER)
					.getPassword();
		parametersReturn.put("userConDevolucionPeroSoloEnPRO", "robot.test@mango.com");
		parametersReturn.put("passwordUserConDevolucion", PASSWORD_ROBOT);
		
//		CallBack callBack = params.getCallBack();
//		if (params.getCallBack()!=null) {
//			parametersReturn.put(Constantes.paramCallBackMethod, callBack.getCallBackMethod());
//			parametersReturn.put(Constantes.paramCallBackResource, callBack.getCallBackResource());
//			parametersReturn.put(Constantes.paramCallBackSchema, callBack.getCallBackSchema());  
//			parametersReturn.put(Constantes.paramCallBackParams, callBack.getCallBackParams());
//			parametersReturn.put(Constantes.paramCallBackUser, callBack.getCallBackUser());
//			parametersReturn.put(Constantes.paramCallBackPassword, callBack.getCallBackPassword());
//		}
		
		return parametersReturn;
	}
	
	public static boolean isBrowserStack(String browser) {
		return (browser.compareTo(EmbeddedDriver.browserstack.name())==0);
	}
}
