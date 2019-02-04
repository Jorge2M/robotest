package com.mng.robotest.test80.mango.test.stpv.shop.favoritos;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
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
    
    public static void validaIsPageOK(DataFavoritos dataFavoritos, DatosStep datosStep, DataFmwkTest dFTest) {
        //Validaciones
        int maxSecondsToWaitCapa = 3;
        int maxSecondsToWaitArticles = 1;
        String descripValidac = 
            "1) Está visible la capa de favoritos con artículos (la esperamos hasta " + maxSecondsToWaitCapa + " segundos)<br>" +
            "2) Aparecen los artículos (los esperamos hasta " + maxSecondsToWaitArticles + " segundos): <br>" +
            	dataFavoritos.getListArtDescHTML();
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageFavoritos.isSectionArticlesVisibleUntil(maxSecondsToWaitCapa, dFTest.driver)) 
                fmwkTest.addValidation(1, State.Defect, listVals);
            //2) 
            if (!PageFavoritos.areVisibleArticlesUntil(dataFavoritos, maxSecondsToWaitArticles, dFTest.driver))
                fmwkTest.addValidation(2, State.Defect, listVals);
                        
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }        
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
    	
    	} finally { stepShareOk.setStepNumber(fmwkTest.grabStep(stepShareOk, dFTest)) ;}
    	
    	checkShareIsOk(stepShareOk, dFTest);
       	
       	return stepShareOk;
    }
    
    public static void checkShareIsOk(DatosStep datosStep, DataFmwkTest dFTest) {
    	//Validacion
    	int secondsToWait = 5;
        String descripValidac = 
                "1) Aparece el modal de favoritos compartidos <br>" +
                "2) Aparece el boton de compartir por Telegram <br>" + 
                "3) Aparece el boton de compartir por WhatsApp <br>" +
                "4) Aparece la url para copiarla y compartir como texto";
        try {
        	
        	List<SimpleValidation> listVals = new ArrayList<>();
        	//1
        	if(!PageFavoritos.checkShareModalUntill(secondsToWait, dFTest.driver)) {
        		fmwkTest.addValidation(1, State.Defect, listVals);
        	};
            //2
        	if(!PageFavoritos.isShareTelegramFavoritesVisible(dFTest.driver)) {
        		fmwkTest.addValidation(1, State.Defect, listVals);
        	};
            //3
            if(!PageFavoritos.isShareWhatsappFavoritesVisible(dFTest.driver)) {
            	fmwkTest.addValidation(1, State.Defect, listVals);
            };
            //4
            if(!PageFavoritos.isShareUrlFavoritesVisible(dFTest.driver)) {
            	fmwkTest.addValidation(1, State.Defect, listVals);
            };
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        
        } finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
        
    }
    
    
    public static DatosStep closeShareModal(DataFmwkTest dFTest) throws Exception {
       	//Step
    	DatosStep stepShareClose = new DatosStep(
    		"Cerramos el modal de favoritos compartidos. ",
    		"El modal de favoritos compartidos desaparece correctamente");
    	try {
       		PageFavoritos.closeShareModal(dFTest.driver);
       		
       		stepShareClose.setExcepExists(false); stepShareClose.setResultSteps(State.Ok);
       		
       	} finally { stepShareClose.setStepNumber(fmwkTest.grabStep(stepShareClose, dFTest)) ;};
       	
    	checkShareIsClosed(stepShareClose, dFTest);
    	return stepShareClose;
    }
    
    public static void checkShareIsClosed(DatosStep datosStep, DataFmwkTest dFTest) {
        //Validaciones
        int maxSecondsToWait = 2;
        String descripValidac = 
        	"1) Desaparece el modal de favoritos compartidos (lo esperamos hasta " + maxSecondsToWait + " segundos)";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageFavoritos.checkShareModalInvisible(dFTest.driver, maxSecondsToWait)) 
                fmwkTest.addValidation(1, State.Warn, listVals);
                        
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }        
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

        //Validaciones
        int maxSecondsToWait = 5;
        String descripValidac = 
            "1) Desaparece de Favoritos el artículo con referencia <b>" + articulo.getRefProducto() + "</b> (lo esperamos hasta " + maxSecondsToWait + " segundos)";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageFavoritos.isInvisibleArticleUntil(articulo, maxSecondsToWait, dFTest.driver)) 
                fmwkTest.addValidation(1, State.Defect, listVals);
                        
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }        
        
        return datosStep;
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

        //Validaciones
        String descripValidac = 
            "1) No queda ningún artículo en Favoritos<br>" +
            "2) Aparece el botón \"Inspírate con lo último\"";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (PageFavoritos.hayArticulos(dFTest.driver)) 
                fmwkTest.addValidation(1, State.Defect, listVals);
            //2) 
            if (!PageFavoritos.isVisibleButtonEmpty(dFTest.driver))
                fmwkTest.addValidation(2, State.Warn, listVals);
                        
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }        
        
        return datosStep;
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
        modalFichaFavoritos.validaIsVisibleFicha(artToPlay, datosStep, dFTest);
        
        return datosStep;
    }
}
