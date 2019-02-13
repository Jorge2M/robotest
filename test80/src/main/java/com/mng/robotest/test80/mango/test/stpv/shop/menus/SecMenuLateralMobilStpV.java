package com.mng.robotest.test80.mango.test.stpv.shop.menus;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep.SaveWhen;
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
    public static DatosStep selectMenuLateral1rstLevelTypeCatalog(Menu1rstLevel menu1rstLevel, DataCtxShop dCtxSh, DataFmwkTest dFTest) 
    throws Exception {
        //Step
        DatosStep datosStep = new DatosStep     (
            "Seleccionar el menú lateral de 1er nivel <b>" + menu1rstLevel + "</b>", 
            "Aparece la galería de productos asociada al menú");
        datosStep.setSaveNettrafic(SaveWhen.Always, dFTest.ctx);
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
        DatosStep datosStep = new DatosStep(
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
    
    public static DatosStep selectCarruselNuevo(Linea lineaNuevo, LineaType lineaType, AppEcom appE, DataFmwkTest dFTest) 
    throws Exception {
        //Step
        DatosStep datosStep = new DatosStep(
            "Seleccionar el carrusel \"nuevo\" asociado a la línea " + lineaType,
            "Aparece la página de nuevo asociada a la línea " + lineaType);
        try {
            SecMenuLateralMobil.clickCarruselNuevo(lineaNuevo, lineaType, appE, dFTest.driver);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        } 
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        //Validaciones. Aparece la galería de nuevo correcta
        PageGaleria pageGaleria = PageGaleria.getInstance(Channel.desktop, appE, dFTest.driver);
        int maxSecondsWait = 3;
        String descripValidac = 
            "1) Aparece algún artículo (esperamos " + maxSecondsWait + " segundos)<br>" +
            "2) El 1er artículo es de tipo " + LineaType.nuevo;
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!pageGaleria.isVisibleArticleUntil(1, maxSecondsWait)) {
                listVals.add(1, State.Warn);
            }
            if (!pageGaleria.isFirstArticleOfType(LineaType.nuevo)) {
                listVals.add(2, State.Warn);
            }
            
            datosStep.setListResultValidations(listVals);
        } 
        finally { listVals.checkAndStoreValidations(descripValidac); }        
        
        return datosStep;
    }
    
    public static DatosStep selectSublineaRebajas(Linea lineaRebajas, LineaType lineaType, AppEcom appE, DataFmwkTest dFTest) throws Exception {
        //Step
        DatosStep datosStep = new DatosStep(
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
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!SecMenuLateralMobil.isVisibleMenuSublineaRebajas(lineaType, dFTest.driver)) {
                listVals.add(1, State.Defect);
            }
            
            datosStep.setListResultValidations(listVals);
        } 
        finally { listVals.checkAndStoreValidations(descripValidac); }        
        
        return datosStep;
    }
    
    public static DatosStep seleccionLinea(LineaType lineaType, Pais pais, AppEcom app, DataFmwkTest dFTest) 
    throws Exception {
        //Step
        String nombreLinea = "<b style=\"color:brown;\">\"" + lineaType.name().toUpperCase() + "\"</b>";
        DatosStep datosStep = new DatosStep(
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
    public static DatosStep seleccionSublineaNinos(LineaType lineaType, SublineaNinosType sublineaType, DataCtxShop dCtxSh, DataFmwkTest dFTest) 
    throws Exception {
        //Step. Selección de una sublínea
        String nombreLineaSublinea = "<b style=\"color:brown;\">\"" + lineaType.name() + " / " + sublineaType.name().toUpperCase() + "\"</b>";
        DatosStep datosStep = new DatosStep(
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
    public static void validaSelecLinea(Pais pais, LineaType lineaType, SublineaNinosType sublineaType, AppEcom app, DatosStep datosStep, DataFmwkTest dFTest) 
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
    
    public static void validaSelectLineaNuevoWithCarrusels(Pais pais, AppEcom app, DatosStep datosStep, DataFmwkTest dFTest) {
        String tagCarrusels = "@TAG_CARRUSELS";
        String descripValidac = 
            "1) Aparecen los carrusels asociados a la linea de " + LineaType.nuevo + " (<b>" + tagCarrusels + "</b>)";
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            String listCarrusels = "";
            for (Linea linea : pais.getShoponline().getLineasToTest(app)) {
                if (SecMenuLateralMobil.isCarruselNuevoAssociated(linea.getType())) {
                    listCarrusels+=(linea.getType() + " ");
                    if (!SecMenuLateralMobil.isCarruselNuevoVisible(linea.getType(), dFTest.driver)) {
                        listVals.add(1, State.Warn);      
                    }
                }
            }
            
            descripValidac = descripValidac.replace(tagCarrusels, listCarrusels);
            datosStep.setListResultValidations(listVals);
        } 
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
        
    public static void validaSelectLineaRebajasWithSublineas(Pais pais, AppEcom app, DatosStep datosStep, DataFmwkTest dFTest) {
        String tagSublineas = "@TAG_SUBLINEAS";
        String descripValidac = 
            "1) Aparecen las sublíneas asociados a la linea de " + LineaType.rebajas + "(<b>" + tagSublineas + "</b>)";
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            String listSublineas = "";
            for (Linea linea : pais.getShoponline().getLineasToTest(app)) {
                if (SecMenuLateralMobil.isSublineaRebajasAssociated(linea.getType())) {
                    listSublineas+=(linea.getType() + " ");
                    if (!SecMenuLateralMobil.isSublineaRebajasVisible(linea.getType(), dFTest.driver)) {
                        listVals.add(1, State.Warn);      
                    }
                }
            }
            
            descripValidac.replace(tagSublineas, listSublineas);
            datosStep.setListResultValidations(listVals);
        } 
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
     
    /**
     * Validaciones asociadas a la selección de una línea con submenús en móvil (línea ninos básicamente)
     */
    public static void validaSelecLineaNinosWithSublineas(LineaType lineaNinosType, AppEcom appE, DatosStep datosStep, DataFmwkTest dFTest) {
        String descripValidac =
            "1) Está seleccionada la línea <b>" + lineaNinosType + "</b><br>" + 
            "2) Es visible el bloque con las sublíneas de " + lineaNinosType;
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!SecMenuLateralMobil.isSelectedLinea(lineaNinosType, appE, dFTest.driver)) {
                listVals.add(1, State.Warn);
            }
            if (!SecMenuLateralMobil.isVisibleBlockSublineasNinos(lineaNinosType, appE, dFTest.driver)) {
                listVals.add(2, State.Warn);
            }
            
            datosStep.setListResultValidations(listVals);
        } 
        finally { listVals.checkAndStoreValidations(descripValidac); }        
    }
    
    /**
     * Validaciones asociadas a la selección de una línea con submenús en móvil
     */
    public static void validaSelecLineaWithMenus2onLevelAssociated(LineaType lineaType, SublineaNinosType sublineaType, AppEcom appE, DatosStep datosStep, DataFmwkTest dFTest) {
        String descripValidac = 
            "1) Está seleccionada la línea <b>" + lineaType + "</b><br>" +
            "2) Son visibles links de Menú de 2o nivel";
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!SecMenuLateralMobil.isSelectedLinea(lineaType, appE, dFTest.driver)) {
                listVals.add(1, State.Warn);
            }
            if (!SecMenuLateralMobil.isMenus2onLevelDisplayed(sublineaType, appE, dFTest.driver)) {
                listVals.add(2, State.Warn);
            }
            
            datosStep.setListResultValidations(listVals);
        } 
        finally { listVals.checkAndStoreValidations(descripValidac); }        
    }    
    
    public static void stepClickMenu1rstLevel(Menu1rstLevel menu1rstLevel, Pais pais, AppEcom app, DataFmwkTest dFTest) 
    throws Exception {
        //Step
        String tagMenu = "[UnknownText]";
        DatosStep datosStep = new DatosStep     (
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
    
    public static void validaPaginaResultMenu2onLevel(AppEcom app, DatosStep datosStep, DataFmwkTest dFTest) throws Exception {
    	PageGaleria pageGaleria = PageGaleria.getInstance(Channel.movil_web, app, dFTest.driver);
        String descripValidac = 
            "1) Aparecen artículos, banners, frames, maps o Sliders";
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!pageGaleria.isVisibleArticleUntil(1/*numArticulo*/, 3/*seconds*/) &&
                !PageLanding.hayIframes(dFTest.driver) &&
                !PageLanding.hayMaps(dFTest.driver) &&
                !PageLanding.haySliders(dFTest.driver) &&
                !ManagerBannersScreen.existBanners(dFTest.driver)) {
                listVals.add(1, State.Warn);
            }
                
            datosStep.setListResultValidations(listVals);
       }
       finally { listVals.checkAndStoreValidations(descripValidac); }
        
       //Validaciones estándar. 
       AllPagesStpV.validacionesEstandar(true/*validaSEO*/, true/*validaJS*/, true/*validaImgBroken*/, datosStep, dFTest);
    }
}
