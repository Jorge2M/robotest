package com.mng.robotest.test80.mango.test.stpv.shop;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.arq.utils.otras.Constantes;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.datastored.DataBag;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.generic.PasosGenAnalitica;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;
import com.mng.robotest.test80.mango.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test80.mango.test.getdata.productos.ArticleStock;
import com.mng.robotest.test80.mango.test.getdata.productos.ManagerArticlesStock;
import com.mng.robotest.test80.mango.test.getdata.productos.ManagerArticlesStock.TypeArticleStock;
import com.mng.robotest.test80.mango.test.pageobject.shop.bolsa.SecBolsa;
import com.mng.robotest.test80.mango.test.pageobject.shop.bolsa.ValidatorContentBolsa;
import com.mng.robotest.test80.mango.test.pageobject.shop.bolsa.LineasArticuloBolsa.DataArtBolsa;
import com.mng.robotest.test80.mango.test.pageobject.shop.bolsa.SecBolsa.StateBolsa;
import com.mng.robotest.test80.mango.test.pageobject.shop.cabecera.SecCabecera;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.Page1IdentCheckoutStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.PageCheckoutWrapperStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.ficha.PageFichaArtStpV;

@SuppressWarnings("javadoc")
public class SecBolsaStpV {
    
    public static datosStep clear(DataCtxShop dCtxSh, DataFmwkTest dFTest) throws Exception {
        //Step. Vaciamos la bolsa
        datosStep datosStep = new datosStep(
            "Eliminamos los posibles artículos existentes en la Bolsa",
            "La bolsa queda vacía");
        try {
            if (SecCabecera.getNew(dCtxSh.channel, dCtxSh.appE, dFTest.driver).hayArticulosBolsa())
                SecBolsa.clearArticulos(dCtxSh, dFTest.driver);

            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        } 
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }

        return datosStep;
    }
    
    public static datosStep close(Channel channel, AppEcom app, DataFmwkTest dFTest) 
    throws Exception {
        if (channel==Channel.movil_web)
            return (clickAspaForCloseMobil(dFTest));
        
        return (forceStateBolsaTo(StateBolsa.Closed, app, channel, dFTest));
    }
    
    public static datosStep clickAspaForCloseMobil(DataFmwkTest dFTest) throws Exception {
        //Step. 
        datosStep datosStep = new datosStep (
            "Click en el aspa para cerrar la bolsa", 
            "Se cierra la bolsa");
        try {
            SecBolsa.clickAspaMobil(dFTest.driver);
                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); } 
    
        //Validaciones
        int maxSecondsToWait = 3;
        String descripValidac = 
            "1) Desaparece la bolsa (lo esperamos hasta " + maxSecondsToWait + " segundos)";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!SecBolsa.isInStateUntil(StateBolsa.Closed, Channel.movil_web, maxSecondsToWait, dFTest.driver))
                fmwkTest.addValidation(1, State.Defect, listVals);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
        
        return datosStep;
    }    
    
    /**
     * Seleccionamos el icono superior de la bolsa para abrirla/cerrar
     * @param forOpen indica si lo clicamos para abrir (porque está cerrada) o para cerrar (porque está abierta)
     */
    public static datosStep forceStateBolsaTo(StateBolsa stateBolsaExpected, AppEcom app, Channel channel, DataFmwkTest dFTest) 
    throws Exception {
        //Step. Click icono para abrir/cerra la bolsa
        datosStep datosStep = new datosStep       (
            "Mediante click o hover conseguir que la bolsa quede en estado " + stateBolsaExpected, 
            "La bolsa queda en estado " + stateBolsaExpected);
        try {
        	SecBolsa.setBolsaToStateIfNotYet(stateBolsaExpected, channel, app, dFTest.driver);
                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); } 
    
        //Validaciones
        validateBolsaInState(stateBolsaExpected, channel, datosStep, dFTest);
        return datosStep;
    }
    
    static void validateBolsaInState(StateBolsa stateBolsaExpected, Channel channel, datosStep datosStep, DataFmwkTest dFTest) {
        int maxSecondsToWait = 5;
        String descripValidac = 
            "1) La bolsa queda en estado " + stateBolsaExpected + " (lo esperamos hasta " + maxSecondsToWait + " segundos)";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!SecBolsa.isInStateUntil(stateBolsaExpected, channel, 0, dFTest.driver))
                fmwkTest.addValidation(1, State.Defect, listVals);
                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
    
    public static void altaArticlosConColores(int numArticulos, DataBag dataBag, DataCtxShop dCtxSh, DataFmwkTest dFTest) 
    throws Exception {
    	ManagerArticlesStock managerArticles = new ManagerArticlesStock(dCtxSh.appE, dCtxSh.urlAcceso, numArticulos);
    	List<ArticleStock> listArticles = managerArticles.getArticles(dCtxSh.pais.getCodigo_pais(), TypeArticleStock.articlesWithMoreOneColour);
        List<ArticleStock> listParaAlta = listArticles.subList(0, numArticulos);
        altaListaArticulosEnBolsa(listParaAlta, dataBag, dCtxSh, dFTest);
    }
    
    /**
     * Define los pasos/validaciones para dar de alta una lista de artículos en la bolsa
     * @param listParaAlta lista de artículos que hay que dar de alta
     * @param listArtEnBolsa lista total de artículos que hay en la bolsa (y en la que se añadirán los nuevos)
     */
    public static datosStep altaListaArticulosEnBolsa(List<ArticleStock> listArticlesForAdd, DataBag dataBag, DataCtxShop dCtxSh, DataFmwkTest dFTest) 
    throws Exception {
        datosStep datosStep = null;
        if (listArticlesForAdd!=null && !listArticlesForAdd.isEmpty()) {
            datosStep = altaBolsaArticulos(listArticlesForAdd, dataBag, dCtxSh, dFTest);
        
            //Validación
            validaAltaArtBolsa(datosStep, dataBag, dCtxSh.channel, dCtxSh.appE, dFTest);
        }
        
        //Almacenamos el importe SubTotal y el de Transporte
        dataBag.setImporteTotal(SecBolsa.getPrecioSubTotal(dCtxSh.channel, dFTest.driver));
        dataBag.setImporteTransp(SecBolsa.getPrecioTransporte(dFTest.driver, dCtxSh.channel));
        return datosStep;
    }
    
    public static datosStep altaBolsaArticulos(List<ArticleStock> listParaAlta, DataBag dataBag, DataCtxShop dCtxSh, DataFmwkTest dFTest) throws Exception {
        //Obtener el literal con la lista de artículos a dar de alta en la bolsa
        String listaArtStr = "";
        for (int i=0; i<listParaAlta.size(); i++) {
            ArticleStock artTmp = listParaAlta.get(i);
            listaArtStr = listaArtStr + artTmp.getReference();
            if (artTmp.isVale())
                listaArtStr = listaArtStr + " (le aplica el vale " + artTmp.getValePais().getCodigoVale() + ")";
            
            //Si no es el último artículo le añadimos una coma
            if (i < (listParaAlta.size() - 1))
                listaArtStr = listaArtStr + "<br>";
        }
        
        //Step
        datosStep datosStep = new datosStep (
            "Buscar y dar de alta los siguientes productos en la bolsa:<br>" + listaArtStr, 
            "Los productos se dan de alta en la bolsa correctamente");
        datosStep.setGrabHTML(true);
        datosStep.setGrabNettrafic(dFTest.ctx);
        try {
        	//Damos de alta la lista de productos en la bolsa
            for (int i=0; i<listParaAlta.size(); i++) {
                ArticleStock artTmp = listParaAlta.get(i);
                ArticuloScreen articulo = UtilsMangoTest.addArticuloBolsa(artTmp, dCtxSh.appE, dCtxSh.channel, dFTest.driver);
                
                //En caso de tener vale asociado lo añadimos también
                if (artTmp.isVale())
                    articulo.setVale(artTmp.getValePais());
                
                dataBag.addArticulo(articulo);
            }
            
            if (dCtxSh.channel==Channel.desktop) {
            	int maxSecondsToWait = 10;
            	SecBolsa.isInStateUntil(StateBolsa.Open, dCtxSh.channel, maxSecondsToWait, dFTest.driver);
            }
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        return datosStep;
    }
    
    /**
     * Validaciones posteriores al alta de una lista de artículos en la bolsa
     * @param listArtEnBolsa lista total de artículos dados de alta a la bolsa
     */
    public static void validaAltaArtBolsa(datosStep datosStep, DataBag dataBag, Channel channel, AppEcom app, DataFmwkTest dFTest) 
    throws Exception {
        //Validaciones
        validaNumArtEnBolsa(dataBag, channel, app, datosStep, dFTest);
        if (channel==Channel.desktop) {
            int maxSecondsToWait = 1;
            String descripValidac =
                "1) Es visible la capa/página correspondiente a la bolsa (la esperamos hasta " + maxSecondsToWait + " segundos)<br>" +
                "2) Aparece el botón \"Comprar\" (lo esperamos hasta " + maxSecondsToWait + " segundos)";
            datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
            try {
                List<SimpleValidation> listVals = new ArrayList<>();
                //1)
                if (!SecBolsa.isInStateUntil(StateBolsa.Open, channel, maxSecondsToWait, dFTest.driver))
                    fmwkTest.addValidation(1, State.Defect, listVals);
                //2)
                if (!SecBolsa.isVisibleBotonComprarUntil(dFTest.driver, channel, maxSecondsToWait))
                    fmwkTest.addValidation(2, State.Defect, listVals);
                
                datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
            } 
            finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }        
        }
        
        //Validaciones. Cuadran los artículos en la volsa
        validaCuadranArticulosBolsa(dataBag, app, channel, datosStep, dFTest);
        
        //Validaciones para analytics (sólo para firefox y NetAnalysis)
        EnumSet<Constantes.AnalyticsVal> analyticSet = EnumSet.of(Constantes.AnalyticsVal.GoogleAnalytics,
                                                                  Constantes.AnalyticsVal.Criteo,
                                                                  Constantes.AnalyticsVal.NetTraffic, 
                                                                  Constantes.AnalyticsVal.DataLayer);
        PasosGenAnalitica.validaHTTPAnalytics(app, LineaType.she, analyticSet, datosStep, dFTest);
    }
    
    public static void validaNumArtEnBolsa(DataBag dataBag, Channel channel, AppEcom app, datosStep datosStep, DataFmwkTest dFTest) 
    throws Exception {
        int maxSecondsToWait = 2;
        String descripValidac =
            "1) Existen " + dataBag.getListArticulos().size() + 
            " elementos dados de alta en la bolsa (los esperamos hasta " + 
            maxSecondsToWait + " segundos)"; 
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            String itemsSaved = String.valueOf(dataBag.getListArticulos().size());
            if (!SecBolsa.numberItemsIsUntil(itemsSaved, channel, app, maxSecondsToWait, dFTest.driver))
                fmwkTest.addValidation(1, State.Warn, listVals);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
    
    public static void validaCuadranArticulosBolsa(DataBag dataBag, AppEcom app, Channel channel, datosStep datosStep, DataFmwkTest dFTest) 
    throws Exception {
        //Validaciones
        String descripValidac = 
            "1) Cuadra el número de artículos existentes en la bolsa<br>" +
            "2) Cuadran las referencias de los artículso existentes en la bolsa<br>" +
            "3) Cuadran los nombres de los artículos existentes en la bolsa<br>" +
            "4) Cuadran los colores de los artículos existentes en la bolsa<br>" +
            "5) Cuadran las tallas de los artículos existentes en la bolsa<br>" +
            "6) Cuadran los precios de los artículos existentes en la bolsa";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            ValidatorContentBolsa validatorBolsa = new ValidatorContentBolsa(dataBag, app, channel, dFTest.driver);
            //1)
            if (!validatorBolsa.numArticlesIsCorrect())
            	fmwkTest.addValidation(1, State.Warn, listVals);
            //2)
            ArrayList<DataArtBolsa> listDataToValidate = new ArrayList<>();
            listDataToValidate.add(DataArtBolsa.Referencia);
            if (!validatorBolsa.allArticlesExpectedDataAreInScreen(listDataToValidate))
                fmwkTest.addValidation(2, State.Warn, listVals);
            
            listDataToValidate.clear();
            listDataToValidate.add(DataArtBolsa.Nombre);
            if (!validatorBolsa.allArticlesExpectedDataAreInScreen(listDataToValidate))
                fmwkTest.addValidation(3, State.Warn, listVals);
            
            listDataToValidate.clear();
            listDataToValidate.add(DataArtBolsa.Color);
            if (!validatorBolsa.allArticlesExpectedDataAreInScreen(listDataToValidate))
                fmwkTest.addValidation(4, State.Warn, listVals);
            
            listDataToValidate.clear();
            listDataToValidate.add(DataArtBolsa.TallaNum);
            boolean tallaNumOk = validatorBolsa.allArticlesExpectedDataAreInScreen(listDataToValidate);
            listDataToValidate.clear();
            listDataToValidate.add(DataArtBolsa.TallaAlf);
            boolean tallaAlfOk = validatorBolsa.allArticlesExpectedDataAreInScreen(listDataToValidate);
            if (!tallaNumOk && !tallaAlfOk)
                fmwkTest.addValidation(5, State.Warn, listVals);
            
            listDataToValidate.clear();
            listDataToValidate.add(DataArtBolsa.PrecioTotal);
            if (!validatorBolsa.allArticlesExpectedDataAreInScreen(listDataToValidate))
                fmwkTest.addValidation(6, State.Warn, listVals);
                
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
    
    public static void clear1erArticuloBolsa(DataBag dataBag, AppEcom app, Channel channel, DataFmwkTest dFTest) 
    throws Exception {
        //Step
        ArticuloScreen articulo1 = dataBag.getArticulo(0);
        String importeTotal = "";
        datosStep datosStep = new datosStep       (
            "Eliminar el artículo-1 (" + articulo1.getReferencia() + ") de la bolsa", 
            "El artículo se elimina correctamente");
        try {
            importeTotal = SecBolsa.getPrecioSubtotalTextPant(channel, dFTest.driver);
            SecBolsa.clearArticuloAndWait(channel, articulo1.getReferencia(), dFTest.driver);
            dataBag.removeArticulo(0);
                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); } 

        //Validaciones
        int maxSecondsToWait = 5;
        String descripValidac = 
            "1) El importe total se acaba modificando (lo esperamos hasta " + maxSecondsToWait + " segundos)";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!SecBolsa.isNotThisImporteTotalUntil(importeTotal, channel, maxSecondsToWait, dFTest.driver)) 
                fmwkTest.addValidation(1, State.Warn, listVals);
                
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
        
        //Validaciones. Cuadran los artículos en la volsa
        validaCuadranArticulosBolsa(dataBag, app, channel, datosStep, dFTest);
    }
    
    /**
     * Seleccionamos el botón "Comprar de la bolsa" (previamente, si la bolsa no está visible la abrimos)
     */
    @SuppressWarnings("static-access")
	public static datosStep selectButtonComprar(DataBag dataBag, DataCtxShop dCtxSh, DataFmwkTest dFTest) throws Exception {
        //Step
        datosStep datosStep = new datosStep     (
            "Se selecciona el botón \"COMPRAR\" de la bolsa", 
            "Se muestra la página de identificación");
        datosStep.setGrabNettrafic(dFTest.ctx);
        try {
        	//SecBolsa.setBolsaToStateIfNotYet(StateBolsa.Open, dCtxSh.channel, dCtxSh.appE, dFTest.driver);
            SecBolsa.clickBotonComprar(dFTest.driver, dCtxSh.channel, 10/*secondsWait*/);
                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        //Validaciones
        validaSelectButtonComprar(datosStep, dataBag, dCtxSh, dFTest);
        
        //Validaciones RGPD
        if(!dCtxSh.userRegistered)
        	Page1IdentCheckoutStpV.secSoyNuevo.validaRGPDText(datosStep, dCtxSh, dFTest);
        
        return datosStep;
    }
    
    /**
     * Validaciones resultado de seleccionar el botón "Comprar" de la bolsa
     * @param accUsrReg si la operación se está realizando con un usuario registrado
     */
    public static datosStep validaSelectButtonComprar(datosStep datosStep, DataBag dataBag, DataCtxShop dCtxSh, DataFmwkTest dFTest) 
    throws Exception {
        if (dCtxSh.userRegistered)
            PageCheckoutWrapperStpV.validateIsFirstPage(dCtxSh.userRegistered, dataBag, dCtxSh.channel, dCtxSh.appE, datosStep, dFTest);
        else
            Page1IdentCheckoutStpV.validateIsPage(datosStep, dFTest);
        
        return datosStep;
    }
    
    public static void click1erArticuloBolsa(DataBag dataBag, AppEcom app, Channel channel, DataFmwkTest dFTest) throws Exception {
        //Step.
        ArticuloScreen articuloClickado = dataBag.getArticulo(0);
        datosStep datosStep = new datosStep       (
            "Lincar con el 1er articulo existente en la bolsa" + " (" + articuloClickado.getReferencia() + ")", 
            "El link al artículo es correcto");
        try {
        	SecBolsa.setBolsaToStateIfNotYet(StateBolsa.Open, channel, app, dFTest.driver);
            SecBolsa.click1erArticuloBolsa(dFTest.driver);
                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); } 
    
        //Validaciones.
        String refArticulo = articuloClickado.getReferencia();
        PageFichaArtStpV pageFichaStpv = new PageFichaArtStpV(app, channel, dFTest);
        pageFichaStpv.validateIsFichaArtDisponible(refArticulo, datosStep);
    }
}
