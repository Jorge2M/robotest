package com.mng.robotest.test80.mango.test.stpv.shop.menus;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.TypeContentMobil;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Sublinea.SublineaNinosType;
import com.mng.robotest.test80.mango.test.pageobject.shop.bannersNew.ManagerBannersScreen;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.PageGaleria;
import com.mng.robotest.test80.mango.test.pageobject.shop.landing.PageLanding;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.Menu1rstLevel;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.SecMenusWrap;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.mobil.SecMenuLateralMobil;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.mobil.SecMenuLateralMobil.TypeLocator;
import com.mng.robotest.test80.mango.test.pageobject.shop.modales.ModalCambioPais;
import com.mng.robotest.test80.mango.test.stpv.shop.AllPagesStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.galeria.PageGaleriaStpV;

@SuppressWarnings("javadoc")
public class SecMenuLateralMobilStpV {
    
    public static SecMenusUserStpV secMenusUser;
    
    /**
     * Selección de un menú lateral de 1er nivel con un catálogo de artículos asociado (p.e. vestidos, camisas, etc.)
     */
    public static datosStep selectMenuLateral1rstLevelTypeCatalog(Menu1rstLevel menu1rstLevel, DataCtxShop dCtxSh, DataFmwkTest dFTest) 
    throws Exception {
        //Step
        datosStep datosStep = new datosStep     (
            "Seleccionar el menú lateral de 1er nivel <b>" + menu1rstLevel + "</b>", 
            "Aparece la galería de productos asociada al menú");
        datosStep.setGrabNettrafic(dFTest.ctx);
        try {
            SecMenuLateralMobil.clickMenuLateral1rstLevel(TypeLocator.dataGaLabelPortion, menu1rstLevel, dCtxSh.pais, dFTest.driver);

            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        //Validaciones
        SecMenusWrapperStpV.validaSelecMenu(menu1rstLevel, dCtxSh, datosStep, dFTest);
        
        return datosStep;
    }
    
    /**
     * Selección de las líneas de Móvil con 'Carrusels' (básicamente las líneas 'Nuevo' y 'Rebajas')
     */
    public static void navClickLineaAndCarrusels(LineaType lineaConCarrusels, Pais pais, AppEcom app, DataFmwkTest dFTest) throws Exception {
        //Step
        datosStep datosStep = new datosStep(
            "Realizar click sobre la línea <b>" + lineaConCarrusels + "</b>",
            "Aparecen los sublinks de " + lineaConCarrusels + " correspondientes según el país");
        try {
            SecMenusWrap.selecLinea(pais, lineaConCarrusels, app, Channel.movil_web, dFTest.driver); 
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        } 
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        validaSelecLinea(pais, lineaConCarrusels, null/*sublinea*/, app, datosStep, dFTest);
        navSelectCarrusels(lineaConCarrusels, pais, app, dFTest);
    }
    
    /**
     * Seleccionamos todos los sublinks de las líneas de móvil con 'carrusels' (nuevo u ofertas de momento)
     */
    public static void navSelectCarrusels(LineaType lineaConCarrusels, Pais pais, AppEcom app, DataFmwkTest dFTest) throws Exception {
        for (Linea linea : pais.getShoponline().getLineasToTest(app)) {
            LineaType lineaDelPais = linea.getType();
            switch (lineaConCarrusels) {
            case rebajas:
                if (SecMenuLateralMobil.isSublineaRebajasAssociated(lineaDelPais))
                    selectSublineaRebajas(pais.getShoponline().getLinea(LineaType.rebajas), lineaDelPais, app, dFTest);
                break;
            case nuevo:
                if (SecMenuLateralMobil.isCarruselNuevoAssociated(lineaDelPais))
                    selectCarruselNuevo(pais.getShoponline().getLinea(LineaType.nuevo), lineaDelPais, app, dFTest);
                break;            
            default:
                break;
            }
        }
    }
    
    public static datosStep selectCarruselNuevo(Linea lineaNuevo, LineaType lineaType, AppEcom appE, DataFmwkTest dFTest) 
    throws Exception {
        //Step
        datosStep datosStep = new datosStep(
            "Seleccionar el carrusel \"nuevo\" asociado a la línea " + lineaType,
            "Aparece la página de nuevo asociada a la línea " + lineaType);
        try {
            SecMenuLateralMobil.clickCarruselNuevo(lineaNuevo, lineaType, appE, dFTest.driver);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        } 
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        //Validaciones. Aparece la galería de nuevo correcta
        PageGaleria pageGaleria = PageGaleria.getInstance(Channel.desktop, appE, dFTest.driver);
        String descripValidac = 
            "1) Aparece algún artículo (esperamos 3 segundos)<br>" +
            "2) El 1er artículo es de tipo " + LineaType.nuevo;
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!pageGaleria.isVisibleArticleUntil(1/*numArticulo*/, 3/*seconds*/))
                fmwkTest.addValidation(1, State.Warn, listVals);
            //2)
            if (!pageGaleria.isFirstArticleOfType(LineaType.nuevo))
                fmwkTest.addValidation(2, State.Warn, listVals);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        } 
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }        
        
        return datosStep;
    }
    
    public static datosStep selectSublineaRebajas(Linea lineaRebajas, LineaType lineaType, AppEcom appE, DataFmwkTest dFTest) throws Exception {
        //Step
        datosStep datosStep = new datosStep(
            "Seleccionar la sublínea de \"rebajas\" <b>" + lineaType + "</b>",
            "Aparece la capa de menús asociada a la sublínea " + lineaType);
        try {
            SecMenuLateralMobil.clickSublineaRebajas(lineaRebajas, lineaType, appE, dFTest.driver);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        } 
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        //Validaciones
        String descripValidac = 
            "1) Se hace visible una capa de submenús asociada a " + lineaType;
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!SecMenuLateralMobil.isVisibleMenuSublineaRebajas(lineaType, dFTest.driver))
                fmwkTest.addValidation(1, State.Defect, listVals);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        } 
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }        
        
        return datosStep;
    }
    
    public static datosStep seleccionLinea(LineaType lineaType, Pais pais, AppEcom app, DataFmwkTest dFTest) 
    throws Exception {
        //Step
        String nombreLinea = "<b style=\"color:brown;\">\"" + lineaType.name().toUpperCase() + "\"</b>";
        datosStep datosStep = new datosStep(
            "Si no lo está, seleccionar la <b style=\"color:chocolate\">Línea</b> " + nombreLinea,
            "Aparece la página correcta asociada a la línea " + lineaType.name().toUpperCase());
        try {
            SecMenuLateralMobil.selecLineaIfNotSelected(pais.getShoponline().getLinea(lineaType), app, dFTest.driver);

            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        } 
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }            

        validaSelecLinea(pais, lineaType, null/*sublinea*/, app, datosStep, dFTest);
        
        return datosStep;
    }    
    
    /**
     * Selecciona una línea (he, she, he...) o sublínea (p.e. bebe_nino)
     */
    public static datosStep seleccionSublineaNinos(LineaType lineaType, SublineaNinosType sublineaType, DataCtxShop dCtxSh, DataFmwkTest dFTest) 
    throws Exception {
        //Step. Selección de una sublínea
        String nombreLineaSublinea = "<b style=\"color:brown;\">\"" + lineaType.name() + " / " + sublineaType.name().toUpperCase() + "\"</b>";
        datosStep datosStep = new datosStep(
            "Si no lo está, seleccionar la línea / <b style=\"color:chocolate\">Sublínea</b> " + nombreLineaSublinea,
            "Aparece la página correcta asociada a la línea/sublínea");
        try {
            SecMenuLateralMobil.selecSublineaNinosIfNotSelected(dCtxSh.pais.getShoponline().getLinea(lineaType), sublineaType, dCtxSh.appE, dFTest.driver);

            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        } 
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }

        validaSelecLinea(dCtxSh.pais, lineaType, sublineaType, dCtxSh.appE, datosStep, dFTest);
        
        return datosStep;
    }
    
    /**
     * Validamos el resultado esperado después de seleccionar una línea (she, he, kids...) en Móbil
     */
    public static void validaSelecLinea(Pais pais, LineaType lineaType, SublineaNinosType sublineaType, AppEcom app, datosStep datosStep, DataFmwkTest dFTest) 
    throws Exception {
        Linea linea = pais.getShoponline().getLinea(lineaType);
        TypeContentMobil typeContent = linea.getContentMobilType();
        if (sublineaType!=null)
            typeContent = linea.getSublineaNinos(sublineaType).getContentMobilType();
        
        switch (typeContent) {
        case bloquesnuevo:
            validaSelectLineaNuevoWithCarrusels(pais, app, datosStep, dFTest);
            break;
        case bloquesrebaj:
            validaSelectLineaRebajasWithSublineas(pais, app, datosStep, dFTest);
            break;
        case menus2:
            validaSelecLineaWithMenus2onLevelAssociated(lineaType, sublineaType, app, datosStep, dFTest);
            break;
        case sublineas:            
            validaSelecLineaNinosWithSublineas(lineaType, app, datosStep, dFTest);
            break;
        case articulos:
            PageGaleriaStpV pageGaleriaStpV = PageGaleriaStpV.getInstance(Channel.movil_web, app, dFTest);
            pageGaleriaStpV.validaArtEnContenido(datosStep);
            break;
        default:
            break;
        }
    }
    
    public static void validaSelectLineaNuevoWithCarrusels(Pais pais, AppEcom app, datosStep datosStep, DataFmwkTest dFTest) {
        String tagCarrusels = "@TAG_CARRUSELS";
        String descripValidac = 
            "1) Aparecen los carrusels asociados a la linea de " + LineaType.nuevo + " (<b>" + tagCarrusels + "</b>)";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            String listCarrusels = "";
            for (Linea linea : pais.getShoponline().getLineasToTest(app)) {
                if (SecMenuLateralMobil.isCarruselNuevoAssociated(linea.getType())) {
                    listCarrusels+=(linea.getType() + " ");
                    if (!SecMenuLateralMobil.isCarruselNuevoVisible(linea.getType(), dFTest.driver))
                        fmwkTest.addValidation(1, State.Warn, listVals);      
                }
            }
            
            descripValidac = descripValidac.replace(tagCarrusels, listCarrusels);
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        } 
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
        
    public static void validaSelectLineaRebajasWithSublineas(Pais pais, AppEcom app, datosStep datosStep, DataFmwkTest dFTest) {
        String tagSublineas = "@TAG_SUBLINEAS";
        String descripValidac = 
            "1) Aparecen las sublíneas asociados a la linea de " + LineaType.rebajas + "(<b>" + tagSublineas + "</b>)";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            String listSublineas = "";
            for (Linea linea : pais.getShoponline().getLineasToTest(app)) {
                if (SecMenuLateralMobil.isSublineaRebajasAssociated(linea.getType())) {
                    listSublineas+=(linea.getType() + " ");
                    if (!SecMenuLateralMobil.isSublineaRebajasVisible(linea.getType(), dFTest.driver))
                        fmwkTest.addValidation(1, State.Warn, listVals);      
                }
            }
            
            descripValidac.replace(tagSublineas, listSublineas);
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        } 
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
     
    /**
     * Validaciones asociadas a la selección de una línea con submenús en móvil (línea ninos básicamente)
     */
    public static void validaSelecLineaNinosWithSublineas(LineaType lineaNinosType, AppEcom appE, datosStep datosStep, DataFmwkTest dFTest) {
        String descripValidac =
            "1) Está seleccionada la línea <b>" + lineaNinosType + "</b><br>" + 
            "2) Es visible el bloque con las sublíneas de " + lineaNinosType;
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!SecMenuLateralMobil.isSelectedLinea(lineaNinosType, appE, dFTest.driver))
                fmwkTest.addValidation(1, State.Warn, listVals);
            //2)
            if (!SecMenuLateralMobil.isVisibleBlockSublineasNinos(lineaNinosType, appE, dFTest.driver))
                fmwkTest.addValidation(2, State.Warn, listVals);            
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        } 
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }        
    }
    
    /**
     * Validaciones asociadas a la selección de una línea con submenús en móvil
     */
    public static void validaSelecLineaWithMenus2onLevelAssociated(LineaType lineaType, SublineaNinosType sublineaType, AppEcom appE, datosStep datosStep, DataFmwkTest dFTest) {
        String descripValidac = 
            "1) Está seleccionada la línea <b>" + lineaType + "</b><br>" +
            "2) Son visibles links de Menú de 2o nivel";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!SecMenuLateralMobil.isSelectedLinea(lineaType, appE, dFTest.driver))
                fmwkTest.addValidation(1, State.Warn, listVals);
            //2)
            if (!SecMenuLateralMobil.isMenus2onLevelDisplayed(sublineaType, appE, dFTest.driver))
                fmwkTest.addValidation(2, State.Warn, listVals);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        } 
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }        
    }    
    
    public static void stepClickMenu1rstLevel(Menu1rstLevel menu1rstLevel, Pais pais, AppEcom app, DataFmwkTest dFTest) 
    throws Exception {
        //Step
        String tagMenu = "[UnknownText]";
        datosStep datosStep = new datosStep     (
        	"Selección del menú <b>" + tagMenu + "</b> (data-ga-label contains " + menu1rstLevel.getDataGaLabelMenuSuperiorDesktop() + ")", 
            "El menú se ejecuta correctamente");
        try {
            SecMenuLateralMobil.clickMenuLateral1rstLevel(TypeLocator.dataGaLabelPortion, menu1rstLevel, pais, dFTest.driver);
            datosStep.setDescripcion(datosStep.getDescripcion().replace(tagMenu, menu1rstLevel.getNombre()));
            ModalCambioPais.closeModalIfVisible(dFTest.driver);
                
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        //Validaciones
        validaPaginaResultMenu2onLevel(app, datosStep, dFTest);
    }    
    
    public static void validaPaginaResultMenu2onLevel(AppEcom app, datosStep datosStep, DataFmwkTest dFTest) throws Exception {
    	PageGaleria pageGaleria = PageGaleria.getInstance(Channel.movil_web, app, dFTest.driver);
        String descripValidac = 
            "1) Aparecen artículos, banners, frames, maps o Sliders";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!pageGaleria.isVisibleArticleUntil(1/*numArticulo*/, 3/*seconds*/) &&
                !PageLanding.hayIframes(dFTest.driver) &&
                !PageLanding.hayMaps(dFTest.driver) &&
                !PageLanding.haySliders(dFTest.driver) &&
                !ManagerBannersScreen.existBanners(dFTest.driver))
                fmwkTest.addValidation(1, State.Warn, listVals);
                
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
       }
       finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
        
       //Validaciones estándar. 
       AllPagesStpV.validacionesEstandar(true/*validaSEO*/, true/*validaJS*/, true/*validaImgBroken*/, datosStep, dFTest);
    }
}
