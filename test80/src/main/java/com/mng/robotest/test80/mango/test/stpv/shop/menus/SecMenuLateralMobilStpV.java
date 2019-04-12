package com.mng.robotest.test80.mango.test.stpv.shop.menus;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.TestCaseData;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.ChecksResult;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
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
import com.mng.robotest.test80.mango.test.stpv.shop.StdValidationFlags;
import com.mng.robotest.test80.mango.test.stpv.shop.galeria.PageGaleriaStpV;

public class SecMenuLateralMobilStpV {
    
    public static SecMenusUserStpV secMenusUser;
    
    @Step (
    	description="Seleccionar el menú lateral de 1er nivel <b>#{menu1rstLevel}</b>", 
        expected="Aparece la galería de productos asociada al menú",
        saveNettraffic=SaveWhen.Always)
    public static void selectMenuLateral1rstLevelTypeCatalog(Menu1rstLevel menu1rstLevel, DataCtxShop dCtxSh, WebDriver driver) 
    throws Exception {
        SecMenuLateralMobil.clickMenuLateral1rstLevel(TypeLocator.dataGaLabelPortion, menu1rstLevel, dCtxSh.pais, driver);
        SecMenusWrapperStpV.validaSelecMenu(menu1rstLevel, dCtxSh, driver);
    }
    
    /**
     * Selección de las líneas de Móvil con 'Carrusels' (básicamente las líneas 'Nuevo' y 'Rebajas')
     */
    @Step (
    	description="Realizar click sobre la línea <b>#{lineaConCarrusels}</b>",
        expected="Aparecen los sublinks de #{lineaConCarrusels} correspondientes según el país")
    public static void navClickLineaAndCarrusels(LineaType lineaConCarrusels, Pais pais, AppEcom app, WebDriver driver) 
    throws Exception {
        SecMenusWrap.selecLinea(pais, lineaConCarrusels, app, Channel.movil_web, driver); 
        validaSelecLinea(pais, lineaConCarrusels, null/*sublinea*/, app, driver);
        navSelectCarrusels(lineaConCarrusels, pais, app, driver);
    }
    
    /**
     * Seleccionamos todos los sublinks de las líneas de móvil con 'carrusels' (nuevo u ofertas de momento)
     */
    public static void navSelectCarrusels(LineaType lineaConCarrusels, Pais pais, AppEcom app, WebDriver driver) 
    throws Exception {
        for (Linea linea : pais.getShoponline().getLineasToTest(app)) {
            LineaType lineaDelPais = linea.getType();
            switch (lineaConCarrusels) {
            case rebajas:
                if (SecMenuLateralMobil.isSublineaRebajasAssociated(lineaDelPais)) {
                    selectSublineaRebajas(pais.getShoponline().getLinea(LineaType.rebajas), lineaDelPais, app, driver);
                }
                break;
            case nuevo:
                if (SecMenuLateralMobil.isCarruselNuevoAssociated(lineaDelPais)) {
                    selectCarruselNuevo(pais.getShoponline().getLinea(LineaType.nuevo), lineaDelPais, app, driver);
                }
                break;            
            default:
                break;
            }
        }
    }
    
    @Step (
    	description="Seleccionar el carrusel \"nuevo\" asociado a la línea #{lineaType}",
        expected="Aparece la página de nuevo asociada a la línea #{lineaType}")
    public static void selectCarruselNuevo(Linea lineaNuevo, LineaType lineaType, AppEcom appE, WebDriver driver) 
    throws Exception {
        SecMenuLateralMobil.clickCarruselNuevo(lineaNuevo, lineaType, appE, driver);
        checkGaleriaAfterSelectNuevo(appE, driver);
    }
    
    @Validation
    private static ChecksResult checkGaleriaAfterSelectNuevo(AppEcom app, WebDriver driver) throws Exception {
    	ChecksResult validations = ChecksResult.getNew();
	    PageGaleria pageGaleria = PageGaleria.getInstance(Channel.desktop, app, driver);
	    int maxSecondsWait = 3;
	 	validations.add(
			"Aparece algún artículo (esperamos " + maxSecondsWait + " segundos)",
			pageGaleria.isVisibleArticleUntil(1, maxSecondsWait), State.Warn);
	 	validations.add(
			"El 1er artículo es de tipo " + LineaType.nuevo,
			pageGaleria.isFirstArticleOfType(LineaType.nuevo), State.Warn);
	 	
    	return validations;   
    }
    
    @Step (
    	description="Seleccionar la sublínea de \"rebajas\" <b>#{lineaType}</b>",
        expected="Aparece la capa de menús asociada a la sublínea #{lineaType}")
    public static void selectSublineaRebajas(Linea lineaRebajas, LineaType lineaType, AppEcom appE, WebDriver driver) 
    throws Exception {
        SecMenuLateralMobil.clickSublineaRebajas(lineaRebajas, lineaType, appE, driver);
        checkIsVisibleSubmenusLinea(lineaType, driver);
    }
    
    @Validation (
    	description="Se hace visible una capa de submenús asociada a #{lineaType}",
    	level=State.Defect)
    private static boolean checkIsVisibleSubmenusLinea(LineaType lineaType, WebDriver driver) {
	    return (SecMenuLateralMobil.isVisibleMenuSublineaRebajas(lineaType, driver));
    }
    
    @Step (
    	description=
    		"Seleccionar la <b style=\"color:chocolate\">Línea</b> " + 
    		"<b style=\"color:brown;\">#{lineaType.getNameUpper()}</b>",
        expected="Aparece la página correcta asociada a la línea #{lineaType.getNameUpper()}")
    public static void seleccionLinea(LineaType lineaType, Pais pais, AppEcom app, WebDriver driver) 
    throws Exception {
        SecMenuLateralMobil.selecLinea(pais.getShoponline().getLinea(lineaType), app, driver); 
        validaSelecLinea(pais, lineaType, null, app, driver);
    }    
    
    @Step (
    	description=
    		"Seleccionar la línea / <b style=\"color:chocolate\">Sublínea</b> " + 
    		"<b style=\"color:brown;\">#{lineaType.name()} / #{sublineaType.getNameUpper()}</b>",
        expected="Aparece la página correcta asociada a la línea/sublínea")
    public static void seleccionSublineaNinos(LineaType lineaType, SublineaNinosType sublineaType, DataCtxShop dCtxSh, WebDriver driver) 
    throws Exception {
        SecMenuLateralMobil.selecSublineaNinosIfNotSelected(dCtxSh.pais.getShoponline().getLinea(lineaType), sublineaType, dCtxSh.appE, driver);
        validaSelecLinea(dCtxSh.pais, lineaType, sublineaType, dCtxSh.appE, driver);
    }
    
    /**
     * Validamos el resultado esperado después de seleccionar una línea (she, he, kids...) en Móbil
     */
    public static void validaSelecLinea(Pais pais, LineaType lineaType, SublineaNinosType sublineaType, AppEcom app, WebDriver driver) 
    throws Exception {
        Linea linea = pais.getShoponline().getLinea(lineaType);
        TypeContentMobil typeContent = linea.getContentMobilType();
        if (sublineaType!=null) {
            typeContent = linea.getSublineaNinos(sublineaType).getContentMobilType();
        }
        
        switch (typeContent) {
        case bloquesnuevo:
            validaSelectLineaNuevoWithCarrusels(pais, app, driver);
            break;
        case bloquesrebaj:
            validaSelectLineaRebajasWithSublineas(pais, app, driver);
            break;
        case menus2:
            validaSelecLineaWithMenus2onLevelAssociated(lineaType, sublineaType, app, driver);
            break;
        case sublineas:            
            validaSelecLineaNinosWithSublineas(lineaType, app, driver);
            break;
        case articulos:
            PageGaleriaStpV pageGaleriaStpV = PageGaleriaStpV.getInstance(Channel.movil_web, app, driver);
            int maxSecondsWait = 3;
            pageGaleriaStpV.validaArtEnContenido(maxSecondsWait);
            break;
        default:
            break;
        }
    }
    
    @Validation
    public static ChecksResult validaSelectLineaNuevoWithCarrusels(Pais pais, AppEcom app, WebDriver driver) {
    	ChecksResult validations = ChecksResult.getNew();
    	
        String listCarrusels = "";
        boolean carruselsOk = true;
        for (Linea linea : pais.getShoponline().getLineasToTest(app)) {
            if (SecMenuLateralMobil.isCarruselNuevoAssociated(linea.getType())) {
                listCarrusels+=(linea.getType() + " ");
                if (!SecMenuLateralMobil.isCarruselNuevoVisible(linea.getType(), driver)) {
                    carruselsOk=false;     
                }
            }
        }
	 	validations.add(
			"Aparecen los carrusels asociados a la linea de " + LineaType.nuevo + " (<b>" + listCarrusels + "</b>)",
			carruselsOk, State.Warn);  
	 	
	 	return validations;
    }
        
    @Validation
    public static ChecksResult validaSelectLineaRebajasWithSublineas(Pais pais, AppEcom app, WebDriver driver) {
    	ChecksResult validations = ChecksResult.getNew();

        String listSublineas = "";
        boolean isSublineasOk = true;
        for (Linea linea : pais.getShoponline().getLineasToTest(app)) {
            if (SecMenuLateralMobil.isSublineaRebajasAssociated(linea.getType())) {
                listSublineas+=(linea.getType() + " ");
                if (!SecMenuLateralMobil.isSublineaRebajasVisible(linea.getType(), driver)) {
                    isSublineasOk = false;      
                }
            }
        }
	 	validations.add(
			"Aparecen las sublíneas asociados a la linea de " + LineaType.rebajas + "(<b>" + listSublineas + "</b>)",
			isSublineasOk, State.Warn);
    	
	 	return validations;
    }
     
    @Validation
    public static ChecksResult validaSelecLineaNinosWithSublineas(LineaType lineaNinosType, AppEcom appE, WebDriver driver) {
    	ChecksResult validations = ChecksResult.getNew();
	 	validations.add(
			"Está seleccionada la línea <b>" + lineaNinosType + "</b>",
			SecMenuLateralMobil.isSelectedLinea(lineaNinosType, appE, driver), State.Warn);
	 	validations.add(
			"Es visible el bloque con las sublíneas de " + lineaNinosType,
			SecMenuLateralMobil.isVisibleBlockSublineasNinos(lineaNinosType, driver), State.Warn);
	 	return validations;
    }
    
    @Validation
    public static ChecksResult validaSelecLineaWithMenus2onLevelAssociated(LineaType lineaType, SublineaNinosType sublineaType, 
    																			   AppEcom appE, WebDriver driver) {
    	ChecksResult validations = ChecksResult.getNew();
	 	validations.add(
			"Está seleccionada la línea <b>" + lineaType + "</b>",
			SecMenuLateralMobil.isSelectedLinea(lineaType, appE, driver), State.Warn);
	 	validations.add(
			"Son visibles links de Menú de 2o nivel",
			SecMenuLateralMobil.isMenus2onLevelDisplayed(sublineaType, driver), State.Warn);
	 	return validations;
    }    
    
    final static String tagTextMenu = "@TagTextMenu";
    @Step (
    	description="Selección del menú <b>" + tagTextMenu + "</b> (data-ga-label contains #{menu1rstLevel.getDataGaLabelMenuSuperiorDesktop()})", 
        expected="El menú se ejecuta correctamente")
    public static void stepClickMenu1rstLevel(Menu1rstLevel menu1rstLevel, Pais pais, AppEcom app, WebDriver driver) 
    throws Exception {
        SecMenuLateralMobil.clickMenuLateral1rstLevel(TypeLocator.dataGaLabelPortion, menu1rstLevel, pais, driver);
        TestCaseData.getDatosCurrentStep().replaceInDescription(tagTextMenu, menu1rstLevel.getNombre());
        ModalCambioPais.closeModalIfVisible(driver);
        validaPaginaResultMenu2onLevel(app, driver);
    }    
    
    public static void validaPaginaResultMenu2onLevel(AppEcom app, WebDriver driver) throws Exception {
    	checkElementsAfterClickMenu2onLevel(app, driver);
        StdValidationFlags flagsVal = StdValidationFlags.newOne();
        flagsVal.validaSEO = true;
        flagsVal.validaJS = true;
        flagsVal.validaImgBroken = true;
        AllPagesStpV.validacionesEstandar(flagsVal, driver);
    }
    
    @Validation (
    	description="Aparecen artículos, banners, frames, maps o Sliders",
    	level=State.Warn)
    private static boolean checkElementsAfterClickMenu2onLevel(AppEcom app, WebDriver driver) throws Exception {
    	PageGaleria pageGaleria = PageGaleria.getInstance(Channel.movil_web, app, driver);
        return (
        	pageGaleria.isVisibleArticleUntil(1, 3) ||
            PageLanding.hayIframes(driver) ||
            PageLanding.hayMaps(driver) ||
            PageLanding.haySliders(driver) ||
            ManagerBannersScreen.existBanners(driver));
    }
}
