package com.mng.robotest.test80.mango.test.generic;

import java.io.FileNotFoundException;
import java.util.EnumSet;
import java.util.Iterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.annotations.validation.ChecksResult;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.TestCaseData;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep.SaveWhen;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.arq.utils.otras.Constantes;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test80.mango.test.getdata.json.gestorDatosHarJSON;


public class PasosGenAnalitica {
    static Logger pLogger = LogManager.getLogger(fmwkTest.log4jLogger);
    
    /**
     * Aplica las validaciones estándar a nivel de Analítica
     */
    public static void validaHTTPAnalytics(AppEcom app, LineaType lineaId, WebDriver driver) throws Exception {
        //Por defecto aplicaremos todas las avalidaciones (Google Analytics, Criteo, NetTraffic y DataLayer)
        EnumSet<Constantes.AnalyticsVal> analyticSet = EnumSet.of(Constantes.AnalyticsVal.GoogleAnalytics,
                                                                  Constantes.AnalyticsVal.NetTraffic, 
                                                                  Constantes.AnalyticsVal.Criteo,
                                                                  Constantes.AnalyticsVal.Bing,
                                                                  Constantes.AnalyticsVal.DataLayer);
        
        validaHTTPAnalytics(app, lineaId, analyticSet, driver);
    }
    
    public static void validaHTTPAnalytics(AppEcom app, LineaType lineaId, EnumSet<Constantes.AnalyticsVal> analyticSet, WebDriver driver) 
    throws Exception {
        validaHTTPAnalytics(app, lineaId, null/*DataProcessPago*/, analyticSet, driver);
    }
    
    public static void validaHTTPAnalytics(AppEcom app, LineaType lineaId, DataPedido dataPedido, EnumSet<Constantes.AnalyticsVal> analyticSet, WebDriver driver) 
    throws Exception {
    	DataFmwkTest dFTest = TestCaseData.getdFTest();
    	DatosStep datosStep = TestCaseData.getDatosLastStep();
        SaveWhen whenSaveNettraffic = datosStep.getSaveNettrafic();
        if (whenSaveNettraffic == SaveWhen.Always &&
            driver.toString().toLowerCase().contains("firefox")) {

            //Instanciamos el gestor de los datos HAR
            gestorDatosHarJSON gestorHAR = null;
            try {
                gestorHAR = new gestorDatosHarJSON(datosStep.getStepNumber(), dFTest.ctx, dFTest.meth);
            }
            catch (FileNotFoundException e) {
                //Capturamos la excepción para que no se produzca error (las posteriores validaciones generarán un warning para este caso)
                pLogger.warn(". Not located file HAR associated to method {}, step {}", dFTest.meth.getName(), Integer.valueOf(datosStep.getStepNumber()), e);
            }
            
            if (gestorHAR!=null) {
	            //Aplicamos todas las validaciones indicadas en analyticsSet
	            Iterator<Constantes.AnalyticsVal> it = analyticSet.iterator();
	            while (it.hasNext()) { 
	                switch (it.next()) {
	                    case GoogleAnalytics:
	                        //Validaciones a nivel de la petición de Google Analytics
	                        validaGoogleAnalytics(gestorHAR, app);
	                        break;
	                    case Criteo:
	                        //Validaciones a nivel de la petición de Criteo
	                        validaCriteo(gestorHAR, lineaId);
	                        break;
	                    case Bing:
	                        //Validaciones a nivel de la petición de Bing
	                        validaBing(gestorHAR, app, dFTest.driver);
	                        break;
	                    case Polyvore:
	                        //Validaciones a nivel del tráfico de Polyvore (sólo USA)
	                        if (dataPedido!=null && dataPedido.getCodigoPais().compareTo("400")==0) {
	                            validaPolyvore(gestorHAR, dataPedido);
	                        }
	                        break;                            
	                    case NetTraffic:
	                        //Validaciones a nivel del tráfico de red
	                        validaNetTraffic(gestorHAR);                            
	                        break;
	                    case DataLayer:
	                        //Validaciones a nivel del tag datalayer (realmente no es una validación a nivel de HTTP)
	                        validaDatalayer(dFTest.driver);                            
	                        break;
	                    default:
	                        break;
	                }
	            }
            }
        }
    }

    @Validation
    public static ChecksResult validaPolyvore(gestorDatosHarJSON gestorHAR, DataPedido dataPedido) throws Exception {
    	ChecksResult validations = ChecksResult.getNew();
    	
        String urlPolyvore = "://www.polyvore.com/conversion/beacon.gif?";
        String paramPolyvore = "adv=mango.com";
        JSONArray listEntriesFiltered = gestorHAR.getListEntriesFilterURL(urlPolyvore, paramPolyvore);
	 	validations.add(
			"Está lanzándose 1 petición que contiene <b>" + urlPolyvore + "</b> y el parámetro <b>" + paramPolyvore + "</b>",
			listEntriesFiltered.size()==1, State.Warn);   
	 	
        if (listEntriesFiltered.size()==1) {
            JSONObject entrieJSON = (JSONObject)listEntriesFiltered.get(0);
            JSONObject requestJSON = (JSONObject)entrieJSON.get("request");
            JSONObject paramAtm = gestorHAR.getParameterFromRequestQuery(requestJSON, "amt");
            JSONObject paramOid = gestorHAR.getParameterFromRequestQuery(requestJSON, "oid");
            JSONObject paramSkus = gestorHAR.getParameterFromRequestQuery(requestJSON, "skus");  
            JSONObject responseJSON = (JSONObject)entrieJSON.get("response");
    	 	validations.add(
				"La petición es de tipo <b>\"GET\"</b>",
				requestJSON.get("method").toString().compareTo("GET")==0, State.Warn);   
    	 	validations.add(
				"El response status de la petición es <b>2xx</b> o <b>3xx</b>",
				responseJSON.get("status").toString().matches("[2|3]\\d\\d"), State.Warn);   
    	 	validations.add(
				"En la petición figura el parámetro <b>\"atm\"</b> y contiene el importe total del pedido <b>" + dataPedido.getImporteTotal() + "\"</b>",
				(paramAtm!=null && ((String)paramAtm.get("value")).contains(dataPedido.getImporteTotal())), State.Warn); 
    	 	validations.add(
				"En la petición figura el parámetro <b>\"oid\"</b> y contiene <b>\"MNG\"</b>",
				(paramOid!=null && ((String)paramOid.get("value")).contains("MNG")), State.Warn);
    	 	
    	 	boolean isParamSkus = true;
            if (paramSkus==null) {
            	isParamSkus = false;
            }
            else {
                Iterator<ArticuloScreen> it2 = dataPedido.getDataBag().getListArticulos().iterator();
                while (it2.hasNext()) {
                    String refPedido = it2.next().getReferencia();
                    if (!((String)paramSkus.get("value")).contains(refPedido)) {
                    	isParamSkus = false;
                    }
                }
            }
            //Obtenemos la lista con las referencias de artículo en formato String
            String listaArtsStr = "";
            Iterator<ArticuloScreen> it = dataPedido.getDataBag().getListArticulos().iterator();
            while (it.hasNext()) {
                listaArtsStr = listaArtsStr + it.next().getReferencia();
                if (it.hasNext()) listaArtsStr = listaArtsStr + ", ";
            }
    	 	validations.add(
				"En la petición figura el parámetro <b>\"skus\"</b> y contiene los artículos del pedido <b>" + listaArtsStr + "</b>",
				isParamSkus, State.Warn);
        }
        
        return validations;
    }    
    
    @SuppressWarnings("rawtypes")
    @Validation
    public static ChecksResult validaCriteo(gestorDatosHarJSON gestorHAR, LineaType lineaId) throws Exception {
    	ChecksResult validations = ChecksResult.getNew();
        if (lineaId!=LineaType.violeta) {
            String urlCriteo = ".criteo.com/dis/dis.aspx?";
            String paramCriteo = "sc_r=1920x1080";
            String referer1aRequest = getReferer1rstRequestWithState2xx(gestorHAR);
            
            JSONArray listEntriesFiltered = gestorHAR.getListEntriesFilterURL(urlCriteo, paramCriteo);
            boolean isArequestWithParamCriteo = listEntriesFiltered!=null && listEntriesFiltered.size()==1;
		 	validations.add(
				"Está lanzándose 1 petición que contiene <b>" + urlCriteo + "</b> y el parámetro <b>\"" + paramCriteo + "\"</b>",
				isArequestWithParamCriteo, State.Info); 
		 	
		 	if (isArequestWithParamCriteo) {
                JSONObject entrieJSON = (JSONObject)listEntriesFiltered.get(0);
                JSONObject requestJSON = (JSONObject)entrieJSON.get("request");
                JSONObject paramRef = gestorHAR.getParameterFromRequestQuery(requestJSON, "ref");
                JSONObject responseJSON = (JSONObject)entrieJSON.get("response");
			 	validations.add(
					"La petición es de tipo <b>\"GET\"</b>",
					requestJSON.get("method").toString().compareTo("GET")==0, State.Warn); 
			 	validations.add(
					"El response status de la petición es de tipo <b>2xx</b> o <b>3xx</b>",
					responseJSON.get("status").toString().matches("[2|3]\\d\\d"), State.Info); 
			 	validations.add(
					"En la petición figura el parámetro <b>\"ref\"</b> y contiene el referer de la 1a request (prioridad a las de estado 2xx) \"" + referer1aRequest + "\"</b>",
					paramRef!=null && ((String)paramRef.get("value")).contains(referer1aRequest), State.Warn); 
		 	}
        }
        
        return validations;
    }

	private static String getReferer1rstRequestWithState2xx(gestorDatosHarJSON gestorHAR) throws Exception {
        JSONArray petsTextHtml = gestorHAR.getListEntriesOfMimeType("text/html");
        JSONObject primeraRequest = null;
        boolean requestEncontrada = false;
        String referer1aRequest = "undefined";
        
        if (petsTextHtml.size()>0) {
			Iterator<JSONObject> it = petsTextHtml.iterator();
            while (it.hasNext() && !requestEncontrada) {
                JSONObject requestTmp = it.next();
                JSONObject responseJSON = (JSONObject)requestTmp.get("response");
                if (responseJSON.get("status").toString().matches("2\\d\\d")) {
                    primeraRequest = requestTmp;
                    requestEncontrada = true;
                }
            }
            
            //Si no hemos encontrado ninguna nos quedamos con la 1a
            if (primeraRequest == null) {
                primeraRequest = (JSONObject)petsTextHtml.get(0);
            }
            
            //Buscamos el atributo 'Referer' en las cabeceras de la 1a petición
            JSONObject refererPrimeraReqJSON = gestorHAR.getParameterFromRequestCabe(primeraRequest, "Referer");
            if (refererPrimeraReqJSON!=null) {
                referer1aRequest = refererPrimeraReqJSON.get("value").toString();
            }
        }
        
        return referer1aRequest;
	}
        
    @Validation
    public static ChecksResult validaBing(gestorDatosHarJSON gestorHAR, AppEcom app, WebDriver driver) throws Exception {
    	ChecksResult validations = ChecksResult.getNew();
        if (UtilsMangoTest.isEntornoPRO(app, driver)) { //El tracking de Bing sólo se encuentra activo en PRO
            String urlBing = "://bat.bing.com/action/0?";
            String paramBing = "ti=5039068";
    
            JSONArray petsTextHtml = gestorHAR.getListEntriesOfMimeType("text/html");
            JSONObject primeraRequest = (JSONObject)petsTextHtml.get(0);
            String url1aRequest = ((JSONObject)primeraRequest.get("request")).get("url").toString();
            JSONArray listEntriesFiltered = gestorHAR.getListEntriesFilterURL(urlBing, paramBing);
		 	validations.add(
				"Está lanzándose 1 petición que contiene <b>" + urlBing + "</b> y el parámetro <b>\"" + paramBing + "\"</b>",
				listEntriesFiltered.size()==1, State.Warn); 
		 	
		 	if (listEntriesFiltered.size()==1) {
                JSONObject entrieJSON = (JSONObject)listEntriesFiltered.get(0);
                JSONObject requestJSON = (JSONObject)entrieJSON.get("request");
                JSONObject paramP = gestorHAR.getParameterFromRequestQuery(requestJSON, "p");
                JSONObject responseJSON = (JSONObject)entrieJSON.get("response");
			 	validations.add(
					"La petición es de tipo <b>\"GET\"</b>",
					requestJSON.get("method").toString().compareTo("GET")==0, State.Warn); 
			 	validations.add(
					"El response status de la petición es de tipo <b>2xx</b>",
					responseJSON.get("status").toString().matches("2\\d\\d"), State.Warn); 
			 	validations.add(
					"En la petición figura el parámetro <b>\"p\"</b> y contiene la URL de la 1a request " + url1aRequest + "\"</b>",
					paramP!=null && ((String)paramP.get("value")).contains(url1aRequest), State.Warn); 
		 	}
        }
        
	 	return validations;
    }
    
    @Validation
    public static ChecksResult validaGoogleAnalytics(gestorDatosHarJSON gestorHAR, AppEcom app) throws Exception {
        //TODO esta validación es temporal. Actualmente hay activados 2 formas de lanzar Google Analytics con lo que es normal que en algún
        //caso se ejecuten 2 peticiones. Cuando dejen sólo una forma habrá que restaurar la validación original
    	ChecksResult validations = ChecksResult.getNew();
    	
        JSONArray listEntriesFilteredPage = gestorHAR.getListEntriesFilterURL("://www.google-analytics.com/collect","t=pageview");
        int numLineas = listEntriesFilteredPage.size(); 
        String urlGoogleAnalytics = "://www.google-analytics.com/collect";
	 	validations.add(
			"Está lanzándose 1 petición que contiene <b>" + urlGoogleAnalytics + "</b> y el parámetro <b>\"t=pageview\"</b>",
			numLineas==0, State.Warn);
	 	
	 	if (numLineas!=0) {
            JSONObject entrieJSON = (JSONObject)listEntriesFilteredPage.get(0);
            JSONObject requestJSON = (JSONObject)entrieJSON.get("request");
            JSONObject paramTid = gestorHAR.getParameterFromRequestQuery(requestJSON, "tid");
            JSONObject responseJSON = (JSONObject)entrieJSON.get("response");
		 	validations.add(
				"La petición es de tipo <b>\"GET\"</b>",
				requestJSON.get("method").toString().compareTo("GET")!=0, State.Warn);
		 	validations.add(
				"El response status de la petición es de tipo <b>2xx</b>",
				responseJSON.get("status").toString().matches("2\\d\\d"), State.Warn);
		 	
	        String valueTid1 = "UA-855910-26";
	        String valueTid2 = "UA-855910-3";
	        String valueTid3 = "UA-855910-34";
	        if (app==AppEcom.outlet) {
	            valueTid1 = "UA-855910-5";
	            valueTid2 = "UA-855910-5";
	            valueTid2 = "UA-855910-5";
	        }
		 	validations.add(
				"En la petición figura el parámetro <b>\"tid=" + valueTid1 + "\" o \"tid=" + valueTid2 + "\" o o \"tid=" + valueTid3 + "\"</b>",
				paramTid!=null && 
	            (((String)paramTid.get("value")).compareTo(valueTid1)==0 || 
	             ((String)paramTid.get("value")).compareTo(valueTid2)==0 ||
	             ((String)paramTid.get("value")).compareTo(valueTid3)==0), State.Warn);
	 	}
	 	
	 	return validations;
    }
    
    @Validation
    public static ChecksResult validaDatalayer(WebDriver driver) {
    	ChecksResult validations = ChecksResult.getNew();
        String firstLineDataLayerFunction = "(function(w,d,s,l,i){w[l]=w[l]||[];w[l].push({'gtm.start':"; 
	 	validations.add(
			"Figura el código JavaScript del tag <b>dataLayer</b>. Validamos la existencia de la 1a línea de la función: " + firstLineDataLayerFunction,
			driver.getPageSource().contains(firstLineDataLayerFunction), State.Warn);
	 	return validations;
    }
    
    @SuppressWarnings({ "rawtypes" })
    @Validation
    public static ChecksResult validaNetTraffic(gestorDatosHarJSON gestorHAR) throws Exception {
    	ChecksResult validations = ChecksResult.getNew();
    	boolean peticionesOk = true;
    	String infoWarnings = "";
        JSONArray listEntriesTotal = gestorHAR.getListEntriesFilterURL("","");
        Iterator entriesJSON = listEntriesTotal.iterator();
        while (entriesJSON.hasNext() && peticionesOk) {
            JSONObject entrieJSON = (JSONObject)entriesJSON.next();
            JSONObject responseJSON = (JSONObject)entrieJSON.get("response");
            JSONObject requestJSON = (JSONObject)entrieJSON.get("request");
            if (responseJSON==null) {
            	infoWarnings+="<br><b style=\"color:" + State.Warn.getColorCss() + "\">Warning!</b>: hay peticiones sin respuesta, por ejemplo la <a href=\"" + (String)requestJSON.get("url") + "\">" + (String)requestJSON.get("url") + "</a>";
                peticionesOk = false;
            }
            else {
                long statusResponse = (long)responseJSON.get("status");
                if (statusResponse >= 400) {
                	infoWarnings+="<br><b style=\"color:" + State.Warn.getColorCss() + "\">Warning!</b>: hay peticiones con status KO, por ejemplo la <a href=\"" + (String)requestJSON.get("url") + "\">" + (String)requestJSON.get("url") + "</a> ( " + statusResponse + ")";
                    peticionesOk = false;
                }
            }
        }
	 	validations.add(
			"En el tráfico de red no existe ninguna sin respuesta o con status KO" + infoWarnings,
			peticionesOk, State.Warn);
    	
	 	return validations;
    }
    
   
    /**
     * Método que extrae el tiempo correspondiente a la primera petición de tipo 'text/html'.
     * @param type: en nuestro caso le pasaremos en el type 'text/html'
     */
    public static float getTime1rstPet(gestorDatosHarJSON gestorHAR, String type) {
        String tiempo = "";
        try {                
            //Obtenemos la 1a URL con type="text/html"
            JSONArray petsTextHtml = gestorHAR.getListEntriesOfMimeType(type);
            JSONObject primeraRequest = (JSONObject)petsTextHtml.get(0);
                
            //Buscamos el tiempo en los datos del JSON.            
            tiempo = primeraRequest.get("time").toString();
        }
        catch (Exception e) {
            pLogger.warn("Problem in getTime1rstPet", e);
        }

        return (Float.valueOf(tiempo).floatValue());
    }    
}
