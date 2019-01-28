package com.mng.robotest.test80.mango.test.stpv.shop.ficha;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import com.mng.robotest.test80.arq.annotations.Validation;
import com.mng.robotest.test80.arq.annotations.ValidationResult;
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
import com.mng.robotest.test80.mango.test.getdata.productos.ArticleStock;
import com.mng.robotest.test80.mango.test.pageobject.ElementPageFunctions.StateElem;
import com.mng.robotest.test80.mango.test.pageobject.shop.PageErrorBusqueda;
import com.mng.robotest.test80.mango.test.pageobject.shop.bolsa.SecBolsa;
import com.mng.robotest.test80.mango.test.pageobject.shop.bolsa.SecBolsa.StateBolsa;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.PageFicha;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.PageFichaArtOld;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.SecBreadcrumbFichaOld;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.Slider;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.PageFicha.TypeFicha;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.SecBolsaButtonAndLinksNew.ActionFavButton;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.SecBreadcrumbFichaOld.ItemBCrumb;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.SecDataProduct.ColorType;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.SecDataProduct.ProductNav;
import com.mng.robotest.test80.mango.test.pageobject.utils.DataFichaArt;
import com.mng.robotest.test80.mango.test.stpv.shop.AllPagesStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.SecBolsaStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.galeria.LocationArticle;
import com.mng.robotest.test80.mango.test.stpv.shop.modales.ModalBuscadorTiendasStpV;

@SuppressWarnings({"javadoc", "static-access"})
public class PageFichaArtStpV {
    
    DataFmwkTest dFTest;
    Channel channel;
    AppEcom app;
    PageFicha pageFicha;
    
    public static SecProductDescrOldStpV secProductDescOld;
    public static SecBolsaButtonAndLinksNewStpV secBolsaButtonAndLinksNew;
    public static ModEnvioYdevolNewStpV modEnvioYdevol;
    public static SecFotosNewStpV secFotosNew;
    public static SecFitFinderStpV secFitFinder;
    
    public PageFichaArtStpV(AppEcom appE, Channel channel, DataFmwkTest dFTest) {
        this.dFTest = dFTest;
        this.channel = channel;
        this.app = appE;
        this.pageFicha = PageFicha.newInstance(appE, channel, dFTest.driver);
    }
    
    public PageFicha getFicha() {
        return this.pageFicha;
    }
    
    public void validateIsFichaAccordingTypeProduct(ArticleStock articulo, DatosStep datosStep) 
    throws Exception {
        switch (articulo.getType()) {
        case articlesNotExistent:
            validateIsFichaArtNoDisponible(articulo.getReference(), datosStep);
            break;
        case articlesWithoutStock:
        	validateIsArticleNotAvailable(articulo, datosStep);
            break;
        case articlesWithMoreOneColour:
        case articlesWithTotalLook: 
        case articlesOnlyInThisStore:
        default:            
            validateIsFichaArtDisponible(articulo.getReference(), datosStep);
        }
        
        //Validaciones estándar. 
        AllPagesStpV.validacionesEstandar(true/*validaSEO*/, true/*validaJS*/, true/*validaImgBroken*/, datosStep, this.dFTest);
    }
    
    public void validateIsFichaArtDisponible(String refArticulo, DatosStep datosStep) { 
        //Validaciones
    	int maxSecondsToWait = 3;
        String descripValidac = 
            "1) Aparece la página correspondiente a la ficha del artículo " + refArticulo + 
            " (La esperamos hasta " + maxSecondsToWait + " segundos)"; 
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!pageFicha.isFichaArticuloUntil(refArticulo, maxSecondsToWait))
                fmwkTest.addValidation(1, State.Defect, listVals);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, this.dFTest); }
    }
    
    public void validateIsFichaArtNoDisponible(String refArticulo, DatosStep datosStep) {
        //Validaciones
        String descripValidac = 
            "1) Aparece la página de resultado de una búsqueda KO<br>" +
            "2) En el texto de resultado de la búsqueda aparece la referencia " + refArticulo;
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageErrorBusqueda.isPage(this.dFTest.driver))
                fmwkTest.addValidation(1, State.Warn, listVals);
            //2)
            if (!PageErrorBusqueda.isCabeceraResBusqueda(this.dFTest.driver, refArticulo))
                fmwkTest.addValidation(2, State.Warn, listVals);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, this.dFTest); }
    }
    
    public void validateIsArticleNotAvailable(ArticleStock article, DatosStep datosStep) {
        //Validaciones
    	int maxSecondsToWait = 2;
        String descripValidac = 
            "1) Aparece la página correspondiente a la ficha del artículo " + article.getReference() + 
                " (la esperamos hasta " + maxSecondsToWait + " segundos)<br>" +
            "2) No está disponible La talla <b>" + article.getSize() + "</b> del color <b>" + article.getColourCode() + "</b>"; 
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!pageFicha.isFichaArticuloUntil(article.getReference(), maxSecondsToWait))
                fmwkTest.addValidation(1, State.Defect, listVals);
            //2)
            if (pageFicha.secDataProduct.isTallaAvailable(article.getSize(), pageFicha.getTypeFicha(), dFTest.driver))
                fmwkTest.addValidation(2, State.Warn, listVals);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, this.dFTest); }
    }        
    
    public void validateIsFichaArtAlgunoColorNoDisponible(String refArticulo, DatosStep datosStep) {
        //Validaciones
        String descripValidac = 
            "1) Aparece la página correspondiente a la ficha del artículo " + refArticulo + "<br>" +
            "2) Aparece algún color no disponible";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!pageFicha.isFichaArticuloUntil(refArticulo, 0/*maxSecondsToWait*/))
                fmwkTest.addValidation(1, State.Defect, listVals);                
            //2)
            if (!pageFicha.secDataProduct.isElementInState(ColorType.Unavailable, StateElem.Present, dFTest.driver))
                fmwkTest.addValidation(2, State.Defect, listVals);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, this.dFTest); }
    }
    
    public void validateIsFichaCualquierArticulo(DatosStep datosStep) {
        //Validaciones
        String descripValidac = 
            "1) Aparece la página de Ficha";
        datosStep.setExcepExists(false); datosStep.setResultSteps(State.Nok);
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!pageFicha.isPageUntil(0))
                fmwkTest.addValidation(1, State.Warn, listVals);

            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
            
        } 
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }        
    }
    
    public void validaDetallesProducto(DataFichaArt datosArticulo, DatosStep datosStep) {
        String validacion1 = "";
        String validacion2 = "";
        int maxSecondsToWait = 3;
        if (datosArticulo.availableReferencia())
            validacion1=
            "1) Aparece la página con los datos de la ficha del producto " + datosArticulo.getReferencia() + 
            " (la esperamos hasta " + maxSecondsToWait + " segundos)<br>";
            
        if (datosArticulo.availableNombre()) {
            validacion2 =
            "2) Como nombre del artículo aparece el seleccionado: " + datosArticulo.getNombre();
        }
            
        String descripValidac = 
             validacion1 +
             validacion2;
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (datosArticulo.availableReferencia()) {
                if (!pageFicha.isFichaArticuloUntil(datosArticulo.getReferencia(), maxSecondsToWait))                  
                    fmwkTest.addValidation(1, State.Defect, listVals);
            }
            
            //2)
            if (datosArticulo.availableNombre()) {
                String nombreArtFicha = pageFicha.secDataProduct.getTituloArt(this.channel, this.dFTest.driver).trim();
                if (datosArticulo.getNombre().toLowerCase().compareTo(nombreArtFicha.toLowerCase())!=0) 
                    fmwkTest.addValidation(2, State.Warn, listVals);
            }
        
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, this.dFTest); }
    }

    public DatosStep selectColorAndSaveData(ArticuloScreen articulo) throws Exception {
        DatosStep datosStep = selectColor(articulo.getCodigoColor());
        articulo.setColor(pageFicha.secDataProduct.getNombreColorSelected(this.channel, this.dFTest.driver));
        return datosStep;
    }
    
    public DatosStep selectColor(String codigoColor) throws Exception {
        //Step. 
        DatosStep datosStep = new DatosStep(
            "Seleccionar el color con código <b>" + codigoColor + "</b>", 
            "Se muestra la ficha correspondiente al color seleccionado");
        try {
            if (pageFicha.secDataProduct.isClickableColor(codigoColor, this.dFTest.driver))
                pageFicha.secDataProduct.selectColorWaitingForAvailability(codigoColor, this.dFTest.driver);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, this.dFTest)); }

        //Validaciones
        String descripValidac = 
        "1) Está seleccionado el color con código <b>" + codigoColor + "<b>";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            String codigoColorPage = pageFicha.secDataProduct.getCodeColor(ColorType.Selected, dFTest.driver); 
            if (!codigoColorPage.contains(codigoColor))
                fmwkTest.addValidation(1, State.Defect, listVals);
                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }  
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, this.dFTest); }
        
        return datosStep;
    }
    
    public DatosStep selectTallaAndSaveData(ArticuloScreen articulo) throws Exception {
        DatosStep datosStep = selectTalla(articulo.getTallaNum());
        articulo.setTallaAlf(pageFicha.getTallaAlfSelected());
        articulo.setTallaNum(pageFicha.getTallaNumSelected());
        return datosStep;
    }
    
    public DatosStep selectTalla(int positionTalla) throws Exception {
    	String tallaCodNum = pageFicha.getTallaCodNum(positionTalla);
    	return (selectTalla(tallaCodNum));
	}
    
    public DatosStep selectTalla(String tallaCodNum) throws Exception {
        //Step. 
        DatosStep datosStep = new DatosStep(
            "Seleccionar la talla con código <b>" + tallaCodNum + "</b> (previamente, si está abierta, cerramos la capa de la bolsa)", 
            "Se cambia la talla correctamente");
        try {
        	SecBolsa.setBolsaToStateIfNotYet(StateBolsa.Closed, channel, app, dFTest.driver);
            pageFicha.selectTallaByValue(tallaCodNum);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, this.dFTest)); }
        
        //Validaciones
        String descripValidac = 
        "1) Queda seleccionada la talla <b>" + tallaCodNum + "<b>";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            String tallaSelected = pageFicha.getTallaNumSelected(); 
            if (tallaSelected.compareTo(tallaCodNum)!=0)
                fmwkTest.addValidation(1, State.Defect, listVals);
                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }  
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, this.dFTest); }
        
        return datosStep;
    }
    
    /**
     * Precondición: estamos en una ficha correspondiente a un artículo con algún color no disponible 
     * Selección de un color no disponible + posterior selección de una talla (que necesariamente ha de estar no disponible)
     */
    public void selectColorAndTallaNoDisponible(ArticleStock article, AppEcom app) throws Exception {
        //Step. 
        DatosStep datosStep = new DatosStep(
            "Seleccionar el color <b>" + article.getColourCode() + "<b>", 
            "La talla " + article.getSize() + " no está disponible");
        try {
            pageFicha.secDataProduct.selectColorWaitingForAvailability(article.getColourCode(), dFTest.driver);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, this.dFTest)); }                  
            
        //Step
        datosStep = new DatosStep       (
            "Seleccionar la talla <b>" + article.getSize() + "</b>", 
            "Aparece una capa de introducción email para aviso");
        try {
        	pageFicha.secDataProduct.selectTallaByValue(article.getSize(), pageFicha.getTypeFicha(), dFTest.driver);
                                                                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, this.dFTest)); }
        
        //Validaciones
        String descripValidac = 
            "1) No aparece el botón \"COMPRAR\"<br>" +
            "2) Aparece la capa de introducción de avísame";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (SecBolsa.isVisibleBotonComprar(Channel.desktop, this.dFTest.driver))
                fmwkTest.addValidation(1, State.Defect, listVals);
            //2)
            boolean isVisibleAvisame = pageFicha.secDataProduct.isVisibleCapaAvisame(this.dFTest.driver);
            if (!isVisibleAvisame)
                fmwkTest.addValidation(2, State.Defect, listVals);
                            
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, this.dFTest); }
    }
    
    public DatosStep selectAnadirALaBolsaStep() throws Exception {
        //Step
        DatosStep datosStep = new DatosStep       (
            "Seleccionar el botón <b>\"Añadir a la bolsa\"</b>", 
            "El comportamiento es el esperado... :-)");
        try {
            pageFicha.clickAnadirBolsaButtonAndWait();
                        
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, this.dFTest)); }
        
        return datosStep;
    }
    
    /**
     * Selección del botón "Añadir a la bolsa" en un contexto en el que previamente NO se ha seleccionado una talla
     * @return si el artículo seleccionado tenía talla única
     */
    public boolean selectAnadirALaBolsaTallaPrevNoSelected() throws Exception {
        //Step
        DatosStep datosStep = selectAnadirALaBolsaStep();
        
        //Validation
        boolean isTallaUnica = pageFicha.isTallaUnica();
        TypeFicha typeFichaAct = pageFicha.getTypeFicha();
        String strApareceAviso = "SÍ";
        if (isTallaUnica || typeFichaAct==TypeFicha.New)
            strApareceAviso = "NO";
        String descripValidac = 
            "1) " + strApareceAviso + " Aparece un aviso indicando que hay que seleccionar la talla";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            boolean isVisibleAviso = pageFicha.secDataProduct.isVisibleAvisoSeleccionTalla(this.dFTest.driver);
            if (isTallaUnica || typeFichaAct==TypeFicha.New) {
                if (isVisibleAviso)
                    fmwkTest.addValidation(1, State.Warn, listVals);
            }
            else {
                if (!isVisibleAviso)
                    fmwkTest.addValidation(1, State.Warn, listVals);
            }
                
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }  
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, this.dFTest); }
        
        if (!isTallaUnica && typeFichaAct==TypeFicha.New) {
            descripValidac = 
                "1) Se hace visible la lista de tallas";
            datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
            try {
                List<SimpleValidation> listVals = new ArrayList<>();
                //1)
                if (!pageFicha.secDataProduct.secSelTallasNew.isVisibleListTallasForSelectUntil(0, this.dFTest.driver))
                    fmwkTest.addValidation(1, State.Warn, listVals);
        
                datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
            }  
            finally { fmwkTest.grabStepValidation(datosStep, descripValidac, this.dFTest); }            
        }
            
        return isTallaUnica;
    }
    
    /**
     * Selección del botón "Añadir a la bolsa" en un contexto en el que previamente SÍ se ha seleccionado una talla
     */
    public DatosStep selectAnadirALaBolsaTallaPrevSiSelected(ArticuloScreen articulo, DataCtxShop dCtxSh) 
    throws Exception {
        //Step, Validation
        DatosStep datosStep = selectAnadirALaBolsaStep();
        DataBag dataBag = new DataBag();
        dataBag.addArticulo(articulo);
        SecBolsaStpV.validaAltaArtBolsa(datosStep, dataBag, dCtxSh.channel, dCtxSh.appE, this.dFTest);
        return datosStep;
    }    

    public DatosStep selectAnadirAFavoritos() throws Exception {
        DataFavoritos dataFavoritos = new DataFavoritos();
        return (selectAnadirAFavoritos(dataFavoritos));
    }
    
    public DatosStep selectAnadirAFavoritos(DataFavoritos dataFavoritos) throws Exception {
        //Step
        DatosStep datosStep = new DatosStep   (
            "Seleccionar el botón <b>\"Añadir a Favoritos\"</b>", 
            "El artículo se añade a Favoritos");
        try {
            pageFicha.selectAnadirAFavoritosButton();
            ArticuloScreen articulo = pageFicha.getArticuloObject();
            dataFavoritos.addArticulo(articulo);
                            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, this.dFTest)); }               

        int maxSecondsToWait1 = 2;
        int maxSecondsToWait2 = 3;
        String descripValidac = 
            "1) Aparece una capa superior de \"Añadiendo artículo a favoritos...\" (lo esperamos hasta " + maxSecondsToWait1 + " segundos)<br>" +
            "2) La capa superior acaba desapareciendo (lo esperamos hasta " + maxSecondsToWait2 + " segundos)";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);           
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!pageFicha.isVisibleDivAnadiendoAFavoritosUntil(maxSecondsToWait1))
                fmwkTest.addValidation(1, State.Info, listVals);
            if (!pageFicha.isInvisibleDivAnadiendoAFavoritosUntil(maxSecondsToWait2))
                fmwkTest.addValidation(2, State.Warn, listVals);
                            
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, this.dFTest); }
        
        //Validaciones
        validateVisibleButtonFavoritos(ActionFavButton.Remove, datosStep);
        
        return datosStep;
    }
    
    public DatosStep selectRemoveFromFavoritos() throws Exception {
        //Step
        DatosStep datosStep = new DatosStep   (
            "Seleccionar el botón <b>\"Eliminar de Favoritos\"</b>", 
            "El artículo se elimina de Favoritos");
        try {
            pageFicha.selectRemoveFromFavoritosButton();
                            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, this.dFTest)); }               

        //Validaciones
        validateVisibleButtonFavoritos(ActionFavButton.Add, datosStep);
        
        return datosStep;
    }    
    
    public void validateVisibleButtonFavoritos(ActionFavButton buttonType, DatosStep datosStep) {
        //Validaciones
        String descripValidac = 
            "1) Aparece el botón de " + buttonType + " a Favoritos";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);           
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            switch (buttonType) {
            case Remove:
                if (!pageFicha.isVisibleButtonElimFavoritos())
                    fmwkTest.addValidation(1, State.Defect, listVals);
                break;
            case Add:
            default:                
                if (!pageFicha.isVisibleButtonAnadirFavoritos())
                    fmwkTest.addValidation(1, State.Defect, listVals);
            }
                            
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, this.dFTest); }        
    }
    
    public DatosStep selectBuscarEnTiendaButton() throws Exception {
        //Step
        DatosStep datosStep = new DatosStep   (
            "Seleccionar <b>" + pageFicha.getNameLinkBuscarEnTienda() + "</b>", 
            "Aparece un resultado de la búsqueda correcta");
        try {
            pageFicha.selectBuscarEnTiendaLink();
                            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, this.dFTest)); }               

        //Validaciones
        ModalBuscadorTiendasStpV.validaBusquedaConResultados(datosStep, this.dFTest);
        
        return datosStep;
    }
    
    public DatosStep selectGuiaDeTallas() throws Exception {
        //Step.
    	boolean isVisible = false;
        DatosStep datosStep = new DatosStep   (
            "Si está visible, Seleccionar el link \"<b>Guía de tallas</b>\"", 
            "Aparece el Fit Finder");
        try {
            isVisible = 
            	pageFicha.secDataProduct.selectGuiaDeTallasIfVisible(this.dFTest.driver);
                            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, this.dFTest)); }               

        if (isVisible)
            secFitFinder.validateIsOkAndClose(datosStep, this.dFTest);
        
        return datosStep;
    }
    
    public void validateSliderIfExists(Slider typeSlider, DatosStep datosStep) {
        //Validaciones
        boolean existsBlock = true;
        String descripValidac = 
            "1) Es visible el slider de artículos de tipo <b>" + typeSlider + "</b>";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!pageFicha.isVisibleSlider(typeSlider)) {
                fmwkTest.addValidation(1, State.Info_NoHardcopy, listVals);
                existsBlock = false;
            }
                            
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, this.dFTest); }
        
        if (existsBlock) {
            int numArtMin = 1;
            descripValidac = 
                "1) El número de artículos del slider de tipo <b>" + typeSlider + "</b> es > " + numArtMin;
            datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
            try {
                List<SimpleValidation> listVals = new ArrayList<>();
                //1)
                if (pageFicha.getNumArtVisiblesSlider(typeSlider) <= numArtMin) {
                    fmwkTest.addValidation(1, State.Warn, listVals);
                    existsBlock = false;
                }
                                
                datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
            }
            finally { fmwkTest.grabStepValidation(datosStep, descripValidac, this.dFTest); }            
        }
    }
    
    public void validaPrevNext(LocationArticle locationArt, DataCtxShop dCtxSh, DatosStep datosStep) {
        String statePrevText = "Es visible";
        if (locationArt.isFirstInGalery()) 
            statePrevText = "No es visible";
        
        int maxSecondsToWait = 5;
        String descripValidac = 
            "1) " + statePrevText + " el link <b>Prev</b> (lo esperamos hasta " + maxSecondsToWait + " segundos)";          
        if (dCtxSh.appE==AppEcom.outlet || dCtxSh.channel==Channel.desktop)
        	descripValidac+="<br>2) Es visible el link <b>Next</b>";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (locationArt.isFirstInGalery()) {
                if (pageFicha.secDataProduct.isVisiblePrevNextUntil(ProductNav.Prev, maxSecondsToWait, this.dFTest.driver))
                    fmwkTest.addValidation(1, State.Warn, listVals);
            }
            else {
                if (!pageFicha.secDataProduct.isVisiblePrevNextUntil(ProductNav.Prev, maxSecondsToWait, this.dFTest.driver))
                    fmwkTest.addValidation(1, State.Warn, listVals);
            }
            //2)
            if (app==AppEcom.outlet || dCtxSh.channel==Channel.desktop) {
            	descripValidac = descripValidac + "2) Es visible el link <b>Next</b>";
	            if (!pageFicha.secDataProduct.isVisiblePrevNextUntil(ProductNav.Next, 0, this.dFTest.driver))
	                fmwkTest.addValidation(2, State.Warn, listVals);            
            }            
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);            
        }
        
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, this.dFTest); }
    }
    
    public DatosStep selectLinkNavigation(ProductNav productNav, DataCtxShop dCtxSh, String refProductOrigin) 
    throws Exception {
        //Step. 
        DatosStep datosStep = new DatosStep(
            "Seleccionamos el link <b>" + productNav + "</b>", 
            "Aparece una página de ficha correcta");
        try {
            pageFicha.secDataProduct.selectLinkNavigation(productNav, this.dFTest.driver);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, this.dFTest)); }
        
        if (productNav==ProductNav.Prev)
            validateIsFichaArtDisponible(refProductOrigin, datosStep);
        
        if (productNav==ProductNav.Next) {
        	LocationArticle location2onArticle = LocationArticle.getInstanceInCatalog(2);
        	validaPrevNext(location2onArticle, dCtxSh, datosStep);
        }
        
        return datosStep;
    }
    
    //------------------------------------------------------------------------
    //Específic Ficha Old
    public void validaExistsImgsCarruselIzqFichaOld(DatosStep datosStep) {
        //Validaciones
        String descripValidac = 
            "1) Existe más de una imagen de carrusel a la izquierda de la imagen principal";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (((PageFichaArtOld)pageFicha).getNumImgsCarruselIzq() < 2)
                fmwkTest.addValidation(1, State.Warn, listVals);
    
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);                    
        }  
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, this.dFTest);}
    }
    
    public DatosStep selectImgCarruselIzqFichaOld(int numImagen) throws Exception { 
        //Step.
        String pngImagen = "";
        DatosStep datosStep = new DatosStep   (
            "Seleccionar la " + numImagen + "a imagen del carrusel izquierdo", 
            "La imagen se carga aumentada en la imagen central");
        try {
            pngImagen = ((PageFichaArtOld)pageFicha).getSrcImgCarruselIzq(numImagen);
            ((PageFichaArtOld)pageFicha).clickImgCarruselIzq(numImagen);
                            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, this.dFTest)); }               
                    
        //Validaciones
        String descripValidac = 
            "1) La imagen central se corresponde con la imagen del carrusel seleccionada (<b>" + pngImagen + "</b>)";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);           
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!((PageFichaArtOld)pageFicha).srcImagenCentralCorrespondsToImgCarrusel(pngImagen))
                fmwkTest.addValidation(1, State.Defect, listVals);
                            
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, this.dFTest); }
        
        return datosStep;
    }
    
    public void selectImagenCentralFichaOld() throws Exception {
        //Step
        String pngImgCentralOriginal = ((PageFichaArtOld)pageFicha).getSrcImagenCentral();
        DatosStep datosStep = new DatosStep (
            "Seleccionar la imagen/ficha central", 
            "Se produce un zoom sobre la imagen");
        try {
        	((PageFichaArtOld)pageFicha).clickImagenFichaCentral();
                            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, this.dFTest)); }         
                    
        //Validaciones
        String descripValidac = 
            "1) Se aplica un Zoom sobre la imagen central<br>" +
            "2) La imagen central con Zoom sigue conteniendo la imagen original: " + pngImgCentralOriginal;
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!((PageFichaArtOld)pageFicha).isVisibleFichaConZoom())
                fmwkTest.addValidation(1, State.Defect, listVals);
            //2)
            if (!((PageFichaArtOld)pageFicha).srcImagenCentralConZoomContains(pngImgCentralOriginal))
                fmwkTest.addValidation(2, State.Defect, listVals);
                            
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, this.dFTest); }
    }    
    
    public void validaBreadCrumbFichaOld(String urlGaleryOrigin, DatosStep datosStep) {
        String descripValidac = 
            "1) Existen el bloque correspondiente a las <b>BreadCrumb</b><br>" + 
            "2) El link correspondiente a la Galería del artículo linca a la URL " + urlGaleryOrigin;
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!SecBreadcrumbFichaOld.isVisibleBreadCrumb(this.dFTest.driver))
                fmwkTest.addValidation(1, State.Defect, listVals);
            //2)
            String urlGaleryBC = SecBreadcrumbFichaOld.getUrlItemBreadCrumb(ItemBCrumb.Galery, this.dFTest.driver);
            if (!urlGaleryOrigin.contains(urlGaleryBC))
                fmwkTest.addValidation(2, State.Warn, listVals);
                            
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, this.dFTest); }
    }
    
    //TODO pending refactors:
    // 1) Remove datosStep
    // 2) Reduce variable assignations
    @Validation
    public ValidationResult ValidateVisibilityOkAvisoSelectTalla(TypeFicha typeFichaAct, boolean isTallaUnica, State levelError, DatosStep datosStep, DataFmwkTest dFTest) {
        ValidationResult valResult = new ValidationResult();
        valResult.datosStep = datosStep;
        valResult.dFTest = dFTest;
        boolean isVisibleAviso = pageFicha.secDataProduct.isVisibleAvisoSeleccionTalla(dFTest.driver);
        String apareceAvisoStr = "Aparece un aviso indicando que hay que seleccionar la talla";
        if (isTallaUnica || typeFichaAct==TypeFicha.New) {
            valResult.validation = "NO " + apareceAvisoStr;
            valResult.resultOk = !isVisibleAviso;
            return valResult;
        }

        valResult.validation = "SÍ " + apareceAvisoStr;
        valResult.resultOk = isVisibleAviso;
        valResult.levelError = levelError;        
        return valResult;
    }
    
    @Validation (
        description="Prueba de test",
        level=State.Warn)
    public void ValidateInConstruction(DataFmwkTest dFTest) {
    	
    }
}