package com.mng.robotest.test80.mango.test.stpv.shop.favoritos;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.datastored.DataBag;
import com.mng.robotest.test80.mango.test.datastored.DataFavoritos;
import com.mng.robotest.test80.mango.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test80.mango.test.pageobject.shop.favoritos.PageFavoritos;
import com.mng.robotest.test80.mango.test.stpv.shop.SecBolsaStpV;

@SuppressWarnings({"javadoc", "static-access"})
public class PageFavoritosStpV {
    
    public static ModalFichaFavoritosStpV modalFichaFavoritos;
    
    @Validation
    public static ListResultValidation validaIsPageOK(DataFavoritos dataFavoritos, DatosStep datosStep, DataFmwkTest dFTest) {
    	ListResultValidation validations = ListResultValidation.getNew(datosStep);
        int maxSecondsToWaitCapa = 3;
        int maxSecondsToWaitArticles = 1;
    	validations.add(
    		"Está visible la capa de favoritos con artículos (la esperamos hasta " + maxSecondsToWaitCapa + " segundos)<br>",
    		PageFavoritos.isSectionArticlesVisibleUntil(maxSecondsToWaitCapa, dFTest.driver), State.Defect);
    	validations.add(
    		"Aparecen los artículos (los esperamos hasta " + maxSecondsToWaitArticles + " segundos): <br>" + dataFavoritos.getListArtDescHTML(),
    		PageFavoritos.areVisibleArticlesUntil(dataFavoritos, maxSecondsToWaitArticles, dFTest.driver), State.Defect);
    	return validations;
    }
    
    public static DatosStep clearAll(DataFavoritos dataFavoritos, DataCtxShop dCtxSh, DataFmwkTest dFTest) throws Exception {
        dataFavoritos.clear();
        return (clearAll(dCtxSh, dFTest));
    }
    
    public static DatosStep clear(ArticuloScreen articulo, DataFavoritos dataFavoritos, DataFmwkTest dFTest) throws Exception {
        dataFavoritos.removeArticulo(articulo);
        return (clear(articulo, dFTest));
    }
    
    public static DatosStep clickShareIsOk(DataFmwkTest dFTest) {
    	//Step
    	DatosStep stepShareOk = new DatosStep("Seleccionar el link de favoritos compartidos. ",
    		"El modal de favoritos compartidos aparece correctamente");
    	try {
    		PageFavoritos.openShareModal(dFTest.driver);
       	
    		stepShareOk.setExcepExists(false); stepShareOk.setResultSteps(State.Ok);
    	} 
    	finally { stepShareOk.setStepNumber(fmwkTest.grabStep(stepShareOk, dFTest)) ;}
    	
    	checkShareIsOk(stepShareOk, dFTest.driver);
       	
       	return stepShareOk;
    }
    
    @Validation
    public static ListResultValidation checkShareIsOk(DatosStep datosStep, WebDriver driver) {
    	ListResultValidation validations = ListResultValidation.getNew(datosStep);
    	int secondsToWait = 5;
    	validations.add(
    		"Aparece el modal de favoritos compartidos <br>",
    		PageFavoritos.checkShareModalUntill(secondsToWait, driver), State.Defect);
    	validations.add(
            "Aparece el boton de compartir por Telegram <br>",
            PageFavoritos.isShareTelegramFavoritesVisible(driver), State.Defect);
        validations.add(
            "Aparece el boton de compartir por WhatsApp <br>",
            PageFavoritos.isShareWhatsappFavoritesVisible(driver), State.Defect);
        validations.add(
            "Aparece la url para copiarla y compartir como texto", 
            PageFavoritos.isShareUrlFavoritesVisible(driver), State.Defect);
        return validations;
    }
    
    public static DatosStep closeShareModal(DataFmwkTest dFTest) throws Exception {
       	//Step
    	DatosStep datosStep = new DatosStep(
    		"Cerramos el modal de favoritos compartidos. ",
    		"El modal de favoritos compartidos desaparece correctamente");
    	try {
       		PageFavoritos.closeShareModal(dFTest.driver);
       		
       		datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
       	} 
    	finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)) ;};
       	
    	int maxSecondsWait = 2;
    	checkShareIsClosedUntil(maxSecondsWait, datosStep, dFTest.driver);
    	
    	return datosStep;
    }
    
    @Validation (
        description="Desaparece el modal de favoritos compartidos (lo esperamos hasta #{maxSecondsWait} segundos)",
        level=State.Warn)
    public static boolean checkShareIsClosedUntil(int maxSecondsWait, DatosStep datosStep, WebDriver driver) {
    	return (PageFavoritos.checkShareModalInvisible(driver, maxSecondsWait));
    }
    
    public static DatosStep clear(ArticuloScreen articulo, DataFmwkTest dFTest) throws Exception {
        //Step
        DatosStep datosStep = new DatosStep(
            "Eliminamos de Favoritos el artículo con referencia<b>: " + articulo.getRefProducto() + "</b>",
            "El artículo desaparece de Favoritos");
        try {
            PageFavoritos.clearArticuloAndWait(articulo, dFTest.driver);

            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        } 
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }

        int maxSecondsWait = 5;
        checkArticleDisappearsFromFavoritesUntil(articulo.getReferencia(), articulo.getCodigoColor(), maxSecondsWait, datosStep, dFTest.driver);
        
        return datosStep;
    }
    
    @Validation (
    	description="Desaparece de Favoritos el artículo con referencia <b>#{refArticle}</b> y código de color <b>#{codColor}</b> (lo esperamos hasta #{maxSecondsWait} segundos)",
        level=State.Defect)
    public static boolean checkArticleDisappearsFromFavoritesUntil(String refArticle, String codColor, int maxSecondsWait, DatosStep datosStep, WebDriver driver) {
    	return (PageFavoritos.isInvisibleArticleUntil(refArticle, codColor, maxSecondsWait, driver));
    }
    
    public static DatosStep clearAll(DataCtxShop dCtxSh, DataFmwkTest dFTest) throws Exception {
        //Step
        DatosStep datosStep = new DatosStep(
            "Eliminamos de Favoritos los posibles artículos existentes",
            "No queda ningún artículo en Favoritos");
        try {
            PageFavoritos.clearAllArticulos(dCtxSh.channel, dCtxSh.appE, dFTest.driver);

            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        } 
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }

        checkFavoritosWithoutArticles(datosStep, dFTest.driver);
        
        return datosStep;
    }
    
    @Validation
    public static ListResultValidation checkFavoritosWithoutArticles(DatosStep datosStep, WebDriver driver) {
    	ListResultValidation validations = ListResultValidation.getNew(datosStep);
    	validations.add(
    		"No queda ningún artículo en Favoritos<br>",
    		!PageFavoritos.hayArticulos(driver), State.Defect);
    	validations.add(
            "Aparece el botón \"Inspírate con lo último\"",
            PageFavoritos.isVisibleButtonEmpty(driver), State.Warn);
        return validations;
    }  
    
    public static DatosStep addArticuloToBag(ArticuloScreen artToAddBolsa, DataBag dataBolsa, Channel channel, DataFmwkTest dFTest) 
    throws Exception {
        String refProductoToAdd = artToAddBolsa.getRefProducto();
        String codigoColor = artToAddBolsa.getCodigoColor();
        
        //Step
        DatosStep datosStep = new DatosStep(
            "Desde Favoritos añadimos el artículo: " + refProductoToAdd + " (1a talla disponible) a la bolsa",
            "El artículo aparece en la bolsa");
        try {
            String tallaSelected = PageFavoritos.addArticleToBag(refProductoToAdd, codigoColor, 1/*posicionTalla*/, dFTest.driver);
            artToAddBolsa.setTallaAlf(tallaSelected);
            dataBolsa.addArticulo(artToAddBolsa);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        } 
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }

        //Validaciones
        SecBolsaStpV.validaAltaArtBolsa(datosStep, dataBolsa, channel, AppEcom.shop, dFTest);
        
        return datosStep;
    }
    
    public static DatosStep clickArticuloImg(ArticuloScreen artToPlay, DataFmwkTest dFTest) {
        String refProducto = artToPlay.getRefProducto();
        String codigoColor = artToPlay.getCodigoColor();
        
        //Step
        DatosStep datosStep = new DatosStep(
            "Desde Favoritos seleccionamos la imagen del artículo: " + refProducto,
            "Aparece el modal con la ficha del artículo");
        try {
            PageFavoritos.clickImgProducto(refProducto, codigoColor, dFTest.driver);

            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        } 
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }

        //Validaciones
        modalFichaFavoritos.validaIsVisibleFicha(artToPlay, datosStep, dFTest.driver);
        
        return datosStep;
    }
}
