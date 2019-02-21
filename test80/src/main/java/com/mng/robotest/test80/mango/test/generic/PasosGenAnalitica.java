package com.mng.robotest.test80.mango.test.generic;

import java.io.FileNotFoundException;
import java.util.EnumSet;
import java.util.Iterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
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
    public static void validaHTTPAnalytics(AppEcom app, LineaType lineaId, DataFmwkTest dFTest) throws Exception {
        //Por defecto aplicaremos todas las avalidaciones (Google Analytics, Criteo, NetTraffic y DataLayer)
        EnumSet<Constantes.AnalyticsVal> analyticSet = EnumSet.of(Constantes.AnalyticsVal.GoogleAnalytics,
                                                                  Constantes.AnalyticsVal.NetTraffic, 
                                                                  Constantes.AnalyticsVal.Criteo,
                                                                  Constantes.AnalyticsVal.Bing,
                                                                  Constantes.AnalyticsVal.DataLayer);
        
        validaHTTPAnalytics(app, lineaId, analyticSet, dFTest);
    }
    
    public static void validaHTTPAnalytics(AppEcom app, LineaType lineaId, EnumSet<Constantes.AnalyticsVal> analyticSet, DataFmwkTest dFTest) 
    throws Exception {
        validaHTTPAnalytics(app, lineaId, null/*DataProcessPago*/, analyticSet, dFTest);
    }
    
    public static void validaHTTPAnalytics(AppEcom app, LineaType lineaId, DataPedido dataPedido, EnumSet<Constantes.AnalyticsVal> analyticSet, DataFmwkTest dFTest) 
    throws Exception {
    	DatosStep datosStep = TestCaseData.getDatosLastStep();
        SaveWhen whenSaveNettraffic = datosStep.getSaveNettrafic();
        if (whenSaveNettraffic == SaveWhen.Always &&
            dFTest.driver.toString().toLowerCase().contains("firefox")) {

            //Instanciamos el gestor de los datos HAR
            gestorDatosHarJSON gestorHAR = null;
            try {
                gestorHAR = new gestorDatosHarJSON(datosStep.getStepNumber(), dFTest.ctx, dFTest.meth);
            }
            catch (FileNotFoundException e) {
                //Capturamos la excepción para que no se produzca error (las posteriores validaciones generarán un warning para este caso)
                pLogger.warn(". Not located file HAR associated to method {}, step {}", dFTest.meth.getName(), Integer.valueOf(datosStep.getStepNumber()), e);
            }
            
            //Aplicamos todas las validaciones indicadas en analyticsSet
            Iterator<Constantes.AnalyticsVal> it = analyticSet.iterator();
            while (it.hasNext()) { 
                switch (it.next()) {
                    case GoogleAnalytics:
                        //Validaciones a nivel de la petición de Google Analytics
                        validaGoogleAnalytics(datosStep, gestorHAR, app);
                        break;
                    case Criteo:
                        //Validaciones a nivel de la petición de Criteo
                        validaCriteo(datosStep, gestorHAR, lineaId);
                        break;
                    case Bing:
                        //Validaciones a nivel de la petición de Bing
                        validaBing(datosStep, gestorHAR, app, dFTest);
                        break;
                    case Polyvore:
                        //Validaciones a nivel del tráfico de Polyvore (sólo USA)
                        if (dataPedido!=null && dataPedido.getCodigoPais().compareTo("400")==0)
                            validaPolyvore(datosStep, gestorHAR, dataPedido);
                        break;                            
                    case NetTraffic:
                        //Validaciones a nivel del tráfico de red
                        validaNetTraffic(datosStep, gestorHAR);                            
                        break;
                    case DataLayer:
                        //Validaciones a nivel del tag datalayer (realmente no es una validación a nivel de HTTP)
                        validaDatalayer(datosStep, dFTest);                            
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public static void validaPolyvore(DatosStep datosStep, gestorDatosHarJSON gestorHAR, DataPedido dataPedido)
    throws Exception {
        //VALIDACIONES POLYVORE PARA USA (sólo en el "Confirmar Compra")
        String urlPolyvore = "://www.polyvore.com/conversion/beacon.gif?";
        String paramPolyvore = "adv=mango.com";

        //Obtenemos la lista con las referencias de artículo en formato String
        String listaArtsStr = "";
        Iterator<ArticuloScreen> it = dataPedido.getDataBag().getListArticulos().iterator();
        while (it.hasNext()) {
            listaArtsStr = listaArtsStr + it.next().getReferencia();
            if (it.hasNext()) listaArtsStr = listaArtsStr + ", ";
        }
        
        String descripValidac = 
            "1) Está lanzándose 1 petición que contiene <b>" + urlPolyvore + "</b> y el parámetro <b>\"" + paramPolyvore + "\"</b><br>" +
            "2) La petición es de tipo <b>\"GET\"</b><br>" +
            "3) El response status de la petición es <b>2xx</b> o <b>3xx</b><br>" +
            "4) En la petición figura el parámetro <b>\"atm\"</b> y contiene el importe total del pedido <b>" + dataPedido.getImporteTotal() + "\"</b><br>" +
            "5) En la petición figura el parámetro <b>\"oid\"</b> y contiene <b>\"MNG\"</b><br>" +
            "6) En la petición figura el parámetro <b>\"skus\"</b> y contiene los artículos del pedido <b>" + listaArtsStr + "</b>";
        datosStep.setNOKstateByDefault();
    	ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            JSONArray listEntriesFiltered = gestorHAR.getListEntriesFilterURL(urlPolyvore, paramPolyvore);
            if (listEntriesFiltered.size()!=1) {
                listVals.add(1, State.Warn);
            }
            else {
                JSONObject entrieJSON = (JSONObject)listEntriesFiltered.get(0);
                JSONObject requestJSON = (JSONObject)entrieJSON.get("request");
                     
                //Obtenemos el parámetro 'atm' existente en el request de Polyvore (en el queryString)
                JSONObject paramAtm = gestorHAR.getParameterFromRequestQuery(requestJSON, "amt");
                     
                //Obtenemos el parámetro 'oid' existente en el request de Polyvore (en el queryString)
                JSONObject paramOid = gestorHAR.getParameterFromRequestQuery(requestJSON, "oid");
                     
                //Obtenemos el parámetro 'skus' existente en el request de Polyvore (en el queryString)
                JSONObject paramSkus = gestorHAR.getParameterFromRequestQuery(requestJSON, "skus");                     
                     
                JSONObject responseJSON = (JSONObject)entrieJSON.get("response");
                if (requestJSON.get("method").toString().compareTo("GET")!=0) {
                    listVals.add(2, State.Warn);
                }
                if (!responseJSON.get("status").toString().matches("[2|3]\\d\\d")) {
                    listVals.add(3, State.Warn);
                }
                if (paramAtm==null || !((String)paramAtm.get("value")).contains(dataPedido.getImporteTotal())) {
                    listVals.add(4, State.Warn);
                }
                if (paramOid==null || !((String)paramOid.get("value")).contains("MNG")) {
                    listVals.add(5, State.Warn);
                }
                if (paramSkus==null) {
                    listVals.add(6, State.Warn);
                }
                else {
                    Iterator<ArticuloScreen> it2 = dataPedido.getDataBag().getListArticulos().iterator();
                    while (it2.hasNext()) {
                        String refPedido = it2.next().getReferencia();
                        if (!((String)paramSkus.get("value")).contains(refPedido)) {
                            listVals.add(6,State.Warn);
                        }
                    }
                }
            }

            datosStep.setListResultValidations(listVals);
        }
        catch (Exception e) {
            //Tratamos una excepción en los datos del JSON como un warning
            datosStep.setExcepExists(false);
            datosStep.setResultSteps(State.Warn);
            pLogger.warn("Problem in validations of tracking Polyvore", e);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }    
    
    @SuppressWarnings("rawtypes")
    public static void validaCriteo(DatosStep datosStep, gestorDatosHarJSON gestorHAR, LineaType lineaId) 
    throws Exception {
        //En el caso de violeta no está activado Criteo
        if (lineaId!=LineaType.violeta) {
            String tagReferrer = "<REFERRER_1A_REQ>";
            String urlCriteo = ".criteo.com/dis/dis.aspx?";
            String paramCriteo = "sc_r=1920x1080";
            
            //VALIDACIONES CRITEO
            String descripValidac = 
                "1) Está lanzándose 1 petición que contiene <b>" + urlCriteo + "</b> y el parámetro <b>\"" + paramCriteo + "\"</b><br>" +
                "2) La petición es de tipo <b>\"GET\"</b><br>" +
                "3) El response status de la petición es de tipo <b>2xx</b> o <b>3xx</b><br>" +
                "4) En la petición figura el parámetro <b>\"ref\"</b> y contiene el referer de la 1a request (prioridad a las de estado 2xx) \"" + tagReferrer + "\"</b>";
            datosStep.setNOKstateByDefault();  
            ListResultValidation listVals = ListResultValidation.getNew(datosStep);
            try {
                //Obtenemos la 1a request de tipo "text/html"con estado 2xx 
                JSONArray petsTextHtml = gestorHAR.getListEntriesOfMimeType("text/html");
                JSONObject primeraRequest = null;
                boolean requestEncontrada = false;

                JSONArray listEntriesFiltered = null;
                String referer1aRequest = "undefined";
                if (petsTextHtml.size()>0) {
                    Iterator it = petsTextHtml.iterator();
                    while (it.hasNext() && !requestEncontrada) {
                        JSONObject requestTmp = (JSONObject)it.next();
                        JSONObject responseJSON = (JSONObject)requestTmp.get("response");
                        if (responseJSON.get("status").toString().matches("2\\d\\d")) {
                            primeraRequest = requestTmp;
                            requestEncontrada = true;
                        }
                    }
                    
                    //Si no hemos encontrado ninguna nos quedamos con la 1a
                    if (primeraRequest == null)
                        primeraRequest = (JSONObject)petsTextHtml.get(0);
                    
                    //Buscamos el atributo 'Referer' en las cabeceras de la 1a petición
                    JSONObject refererPrimeraReqJSON = gestorHAR.getParameterFromRequestCabe(primeraRequest, "Referer");
                    if (refererPrimeraReqJSON!=null)
                        referer1aRequest = refererPrimeraReqJSON.get("value").toString();
                    
                    descripValidac = descripValidac.replace(tagReferrer, referer1aRequest);
                    
                    //Obtenemos las peticiones de Criteo
                    listEntriesFiltered = gestorHAR.getListEntriesFilterURL(urlCriteo, paramCriteo);
                }
                
                if (listEntriesFiltered==null || listEntriesFiltered.size()!=1) {
                    listVals.add(1,State.Info);
                }
                else {
                    JSONObject entrieJSON = (JSONObject)listEntriesFiltered.get(0);
                    JSONObject requestJSON = (JSONObject)entrieJSON.get("request");
                    
                    //Obtenemos el parámetro href existente en el request de criteo (en el queryString)
                    JSONObject paramRef = gestorHAR.getParameterFromRequestQuery(requestJSON, "ref");
                    JSONObject responseJSON = (JSONObject)entrieJSON.get("response");
                    if (requestJSON.get("method").toString().compareTo("GET")!=0) {
                        listVals.add(2,State.Warn);
                    }
                    if (!responseJSON.get("status").toString().matches("[2|3]\\d\\d")) {
                        listVals.add(3,State.Info);
                    }
                    if (paramRef==null || !((String)paramRef.get("value")).contains(referer1aRequest)) {
                        listVals.add(4,State.Warn);
                    }
                }

                datosStep.setListResultValidations(listVals);
            }
            catch (Exception e) {
                //Tratamos una excepción en los datos del JSON como un warning
                datosStep.setExcepExists(false);
                datosStep.setResultSteps(State.Warn);
                pLogger.warn("Problem in validations of tracking Criteo", e);
            }
            finally { listVals.checkAndStoreValidations(descripValidac); }
        }
    }
    
    public static void validaBing(DatosStep datosStep, gestorDatosHarJSON gestorHAR, AppEcom app, DataFmwkTest dFTest) throws Exception {
        //El tracking de Bing sólo se encuentra activo en PRO
        if (UtilsMangoTest.isEntornoPRO(app, dFTest)) {
            //Validaciones
            String tagUrl1aReq = "<URL_1A_REQUEST>";
            String urlBing = "://bat.bing.com/action/0?";
            String paramBing = "ti=5039068";
    
            String descripValidac = 
                "1) Está lanzándose 1 petición que contiene <b>" + urlBing + "</b> y el parámetro <b>\"" + paramBing + "\"</b><br>" +
                "2) La petición es de tipo <b>\"GET\"</b><br>" +
                "3) El response status de la petición es de tipo <b>2xx</b><br>" +
                "4) En la petición figura el parámetro <b>\"p\"</b> y contiene la URL de la 1a request " + tagUrl1aReq + "\"</b>";
            datosStep.setNOKstateByDefault();   
            ListResultValidation listVals = ListResultValidation.getNew(datosStep);
            try { 
                //Obtenemos la URL de la 1a URL de type "text/html" que se ha lanzado
                JSONArray petsTextHtml = gestorHAR.getListEntriesOfMimeType("text/html");
                JSONObject primeraRequest = (JSONObject)petsTextHtml.get(0);
                
                //Buscamos la URL entre los parametros del "request"
                String url1aRequest = ((JSONObject)primeraRequest.get("request")).get("url").toString();
                descripValidac = descripValidac.replace(tagUrl1aReq, url1aRequest);
                
                //Obtenemos las peticiones de Bing
                JSONArray listEntriesFiltered = gestorHAR.getListEntriesFilterURL(urlBing, paramBing);
                if (listEntriesFiltered.size()!=1) {
                    listVals.add(1, State.Warn);
                }
                else {
                     JSONObject entrieJSON = (JSONObject)listEntriesFiltered.get(0);
                     JSONObject requestJSON = (JSONObject)entrieJSON.get("request");
                     
                     //Obtenemos el parámetro 'p' existente en el request de criteo (en el queryString)
                     JSONObject paramP = gestorHAR.getParameterFromRequestQuery(requestJSON, "p");
                     JSONObject responseJSON = (JSONObject)entrieJSON.get("response");
                     if (requestJSON.get("method").toString().compareTo("GET")!=0) {
                         listVals.add(2, State.Warn);
                     }
                     if (!responseJSON.get("status").toString().matches("2\\d\\d")) {
                         listVals.add(3, State.Warn);
                     }
                     if (paramP==null || !((String)paramP.get("value")).contains(url1aRequest)) {
                         listVals.add(4, State.Warn);
                     }
                }

                datosStep.setListResultValidations(listVals);
            }
            catch (Exception e) {
                //Tratamos una excepción en los datos del JSON como un warning
                datosStep.setExcepExists(false);
                datosStep.setResultSteps(State.Warn);
                pLogger.warn("Problem in validations of tracking Bing", e);
            }
            finally { listVals.checkAndStoreValidations(descripValidac); }
        }
    }
    
    public static void validaGoogleAnalytics(DatosStep datosStep, gestorDatosHarJSON gestorHAR, AppEcom app) {
        //TODO esta validación es temporal. Actualmente hay activados 2 formas de lanzar Google Analytics con lo que es normal que en algún
        //caso se ejecuten 2 peticiones. Cuando dejen sólo una forma habrá que restaurar la validación original
        String valueTid1 = "UA-855910-26";
        String valueTid2 = "UA-855910-3";
        String valueTid3 = "UA-855910-34";
        if (app==AppEcom.outlet) {
            valueTid1 = "UA-855910-5";
            valueTid2 = "UA-855910-5";
            valueTid2 = "UA-855910-5";
        }
        
        String urlGoogleAnalytics = "://www.google-analytics.com/collect";
        String descripValidac = 
            "1) Está lanzándose 1 petición que contiene <b>" + urlGoogleAnalytics + "</b> y el parámetro <b>\"t=pageview\"</b><br>" +
            "2) La petición es de tipo <b>\"GET\"</b><br>" +
            "3) El response status de la petición es de tipo <b>2xx</b><br>" +
            "4) En la petición figura el parámetro <b>\"tid=" + valueTid1 + "\" o \"tid=" + valueTid2 + "\" o o \"tid=" + valueTid3 + "\"</b>";
        datosStep.setNOKstateByDefault();    
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            JSONArray listEntriesFilteredPage = gestorHAR.getListEntriesFilterURL("://www.google-analytics.com/collect","t=pageview");
            int numLineas = listEntriesFilteredPage.size(); 
                    
	        if (numLineas==0) {
	            listVals.add(1, State.Warn);
	        }
	        else {
	            JSONObject entrieJSON = (JSONObject)listEntriesFilteredPage.get(0);
	            JSONObject requestJSON = (JSONObject)entrieJSON.get("request");
	            JSONObject paramTid = gestorHAR.getParameterFromRequestQuery(requestJSON, "tid");
	            JSONObject responseJSON = (JSONObject)entrieJSON.get("response");
	            if (requestJSON.get("method").toString().compareTo("GET")!=0) {
	                listVals.add(2, State.Warn);
	            }
	            if (!responseJSON.get("status").toString().matches("2\\d\\d")) {
	                listVals.add(3, State.Warn);
	            }
	            if (paramTid==null || 
	               ( ((String)paramTid.get("value")).compareTo(valueTid1)!=0 && 
	               ((String)paramTid.get("value")).compareTo(valueTid2)!=0 &&
	               ((String)paramTid.get("value")).compareTo(valueTid3)!=0) ) {
	                listVals.add(4, State.Warn);
	            }
	        }

            datosStep.setListResultValidations(listVals);            
        }
        catch (Exception e) {
            //Tratamos una excepción en los datos del JSON como un warning
            datosStep.setExcepExists(false);
            datosStep.setResultSteps(State.Warn);
            pLogger.warn("Problem in validations of tracking Google Analytics", e);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
    
    public static void validaDatalayer(DatosStep datosStep, DataFmwkTest dFTest) {
        String firstLineDataLayerFunction = "(function(w,d,s,l,i){w[l]=w[l]||[];w[l].push({'gtm.start':"; 
        String descripValidac = 
            "1) Figura el código JavaScript del tag <b>dataLayer</b>. Validamos la existencia de la 1a línea de la función: " + firstLineDataLayerFunction;
        datosStep.setNOKstateByDefault();  
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!dFTest.driver.getPageSource().contains(firstLineDataLayerFunction))
                listVals.add(1, State.Warn);
            
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }        
    }
    
    @SuppressWarnings({ "rawtypes" })
    public static void validaNetTraffic(DatosStep datosStep, gestorDatosHarJSON gestorHAR) {
        String descripValidac = 
            "1) En el tráfico de red no existe ninguna sin respuesta o con status KO";
        datosStep.setNOKstateByDefault();     
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            //Obtenemos todas las entradas sin filtrar por URL
            JSONArray listEntriesTotal = gestorHAR.getListEntriesFilterURL("","");
            boolean petKOencontrada = false;
            Iterator entriesJSON = listEntriesTotal.iterator();
            while (entriesJSON.hasNext() && !petKOencontrada) {
                JSONObject entrieJSON = (JSONObject)entriesJSON.next();
                JSONObject responseJSON = (JSONObject)entrieJSON.get("response");
                JSONObject requestJSON = (JSONObject)entrieJSON.get("request");
                if (responseJSON==null) {
                    listVals.add(1, State.Warn);
                    descripValidac+="<br><b style=\"color:" + State.Warn.getColorCss() + "\">Warning!</b>: hay peticiones sin respuesta, por ejemplo la <a href=\"" + (String)requestJSON.get("url") + "\">" + (String)requestJSON.get("url") + "</a>";
                    petKOencontrada = true;
                }
                else {
                    long statusResponse = (long)responseJSON.get("status");
                    if (statusResponse >= 400) {
                        listVals.add(1, State.Warn);
                        descripValidac+="<br><b style=\"color:" + State.Warn.getColorCss() + "\">Warning!</b>: hay peticiones con status KO, por ejemplo la <a href=\"" + (String)requestJSON.get("url") + "\">" + (String)requestJSON.get("url") + "</a> ( " + statusResponse + ")";
                        petKOencontrada = true;
                    }
                }
            }

            datosStep.setListResultValidations(listVals);
        }
        catch (Exception e) {
            //Tratamos una excepción en los datos del JSON como un warning
            datosStep.setExcepExists(false);
            datosStep.setResultSteps(State.Warn);
            pLogger.warn("Problem in validations of NetTraffic", e);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }        
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
