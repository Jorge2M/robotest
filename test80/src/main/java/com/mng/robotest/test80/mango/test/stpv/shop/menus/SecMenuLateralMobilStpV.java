package com.mng.robotest.test80.mango.test.stpv.shop.menus;

import java.util.EnumSet;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.boundary.aspects.step.SaveWhen;
import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.testmaker.service.TestMaker;
import com.mng.testmaker.conf.Channel;
import com.mng.testmaker.conf.State;
import com.mng.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.test80.mango.test.data.Constantes;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.TypeContentMobil;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Sublinea.SublineaNinosType;
import com.mng.robotest.test80.mango.test.generic.PasosGenAnalitica;
import com.mng.robotest.test80.mango.test.pageobject.shop.bannersNew.ManagerBannersScreen;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.PageGaleria;
import com.mng.robotest.test80.mango.test.pageobject.shop.landing.PageLanding;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.Menu1rstLevel;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.MenuLateralDesktop;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.SecMenusWrap;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.mobil.SecMenuLateralMobil;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.mobil.SecMenuLateralMobil.TypeLocator;
import com.mng.robotest.test80.mango.test.pageobject.shop.modales.ModalCambioPais;
import com.mng.robotest.test80.mango.test.stpv.shop.AllPagesStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.StdValidationFlags;
import com.mng.robotest.test80.mango.test.stpv.shop.galeria.PageGaleriaStpV;

public class SecMenuLateralMobilStpV {
    
	private final AppEcom app;
	private final WebDriver driver;
	private final SecMenuLateralMobil secMenuLateral;
	
	private SecMenuLateralMobilStpV(AppEcom app, WebDriver driver) {
		this.app = app;
		this.driver = driver;
		secMenuLateral = SecMenuLateralMobil.getNew(app, driver);
	}
	
	public static SecMenuLateralMobilStpV getNew(AppEcom app, WebDriver driver) {
		return (new SecMenuLateralMobilStpV(app, driver));
	}
	
	@Step (
		description="Seleccionar el menú lateral de 1er nivel <b>#{menu1rstLevel}</b>", 
		expected="Aparece la galería de productos asociada al menú",
		saveNettraffic=SaveWhen.Always)
	public void selectMenuLateral1rstLevelTypeCatalog(Menu1rstLevel menu1rstLevel, DataCtxShop dCtxSh) throws Exception {
		secMenuLateral.clickMenuLateral1rstLevel(TypeLocator.dataGaLabelPortion, menu1rstLevel, dCtxSh.pais);
		validaSelecMenu(menu1rstLevel, dCtxSh);
	}

	public void validaSelecMenu(MenuLateralDesktop menu, DataCtxShop dCtxSh) throws Exception {
		PageGaleriaStpV pageGaleriaStpV = PageGaleriaStpV.getInstance(dCtxSh.channel, dCtxSh.appE, driver);
		pageGaleriaStpV.validateGaleriaAfeterSelectMenu(dCtxSh);

		//Validaciones estándar. 
		StdValidationFlags flagsVal = StdValidationFlags.newOne();
		flagsVal.validaSEO = true;
		flagsVal.validaJS = true;
		flagsVal.validaImgBroken = false;
		AllPagesStpV.validacionesEstandar(flagsVal, driver);

		//Por defecto aplicaremos todas las avalidaciones (Google Analytics, Criteo, NetTraffic y DataLayer)
		EnumSet<Constantes.AnalyticsVal> analyticSet = EnumSet.of(
				Constantes.AnalyticsVal.GoogleAnalytics,
				Constantes.AnalyticsVal.NetTraffic, 
				Constantes.AnalyticsVal.Criteo,
				Constantes.AnalyticsVal.DataLayer);

		PasosGenAnalitica.validaHTTPAnalytics(dCtxSh.appE, menu.getLinea(), analyticSet, driver);
	}

    /**
     * Selección de las líneas de Móvil con 'Carrusels' (básicamente las líneas 'Nuevo' y 'Rebajas')
     */
    @Step (
    	description="Realizar click sobre la línea <b>#{lineaConCarrusels}</b>",
        expected="Aparecen los sublinks de #{lineaConCarrusels} correspondientes según el país")
    public void navClickLineaAndCarrusels(LineaType lineaConCarrusels, Pais pais) throws Exception {
    	SecMenusWrap secMenus = SecMenusWrap.getNew(Channel.movil_web, app, driver);    	
        secMenus.selecLinea(pais, lineaConCarrusels); 
        validaSelecLinea(pais, lineaConCarrusels, null);
        navSelectCarrusels(lineaConCarrusels, pais);
    }
    
    /**
     * Seleccionamos todos los sublinks de las líneas de móvil con 'carrusels' (nuevo u ofertas de momento)
     */
    public void navSelectCarrusels(LineaType lineaConCarrusels, Pais pais) throws Exception {
        for (Linea linea : pais.getShoponline().getLineasToTest(app)) {
            LineaType lineaDelPais = linea.getType();
            switch (lineaConCarrusels) {
            case rebajas:
                if (secMenuLateral.isSublineaRebajasAssociated(lineaDelPais)) {
                    selectSublineaRebajas(pais.getShoponline().getLinea(LineaType.rebajas), lineaDelPais);
                }
                break;
            case nuevo:
                if (secMenuLateral.isCarruselNuevoAssociated(lineaDelPais)) {
                    selectCarruselNuevo(pais.getShoponline().getLinea(LineaType.nuevo), lineaDelPais);
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
    public void selectCarruselNuevo(Linea lineaNuevo, LineaType lineaType) throws Exception {
    	secMenuLateral.clickCarruselNuevo(lineaNuevo, lineaType);
        checkGaleriaAfterSelectNuevo();
    }

	@Validation
	private ChecksTM checkGaleriaAfterSelectNuevo() throws Exception {
		ChecksTM validations = ChecksTM.getNew();
		PageGaleria pageGaleria = PageGaleria.getNew(Channel.movil_web, app, driver);
		int maxSecondsWait = 3;
		validations.add(
			"Aparece algún artículo (esperamos " + maxSecondsWait + " segundos)",
			pageGaleria.isVisibleArticleUntil(1, maxSecondsWait), State.Warn);
//		validations.add(
//			"El 1er artículo es de tipo " + LineaType.nuevo,
//			pageGaleria.isFirstArticleOfType(LineaType.nuevo), State.Warn);

		return validations;   
	}

    @Step (
    	description="Seleccionar la sublínea de \"rebajas\" <b>#{lineaType}</b>",
        expected="Aparece la capa de menús asociada a la sublínea #{lineaType}")
    public void selectSublineaRebajas(Linea lineaRebajas, LineaType lineaType) throws Exception {
    	secMenuLateral.clickSublineaRebajas(lineaRebajas, lineaType);
        checkIsVisibleSubmenusLinea(lineaType);
    }
    
    @Validation (
    	description="Se hace visible una capa de submenús asociada a #{lineaType}",
    	level=State.Defect)
    private boolean checkIsVisibleSubmenusLinea(LineaType lineaType) {
	    return (secMenuLateral.isVisibleMenuSublineaRebajas(lineaType));
    }
    
    @Step (
    	description=
    		"Seleccionar la <b style=\"color:chocolate\">Línea</b> " + 
    		"<b style=\"color:brown;\">#{lineaType.getNameUpper()}</b>",
        expected="Aparece la página correcta asociada a la línea #{lineaType.getNameUpper()}")
    public void seleccionLinea(LineaType lineaType, Pais pais) throws Exception {
    	secMenuLateral.selecLinea(pais.getShoponline().getLinea(lineaType)); 
        validaSelecLinea(pais, lineaType, null);
    }    
    
    @Step (
    	description=
    		"Seleccionar la línea / <b style=\"color:chocolate\">Sublínea</b> " + 
    		"<b style=\"color:brown;\">#{lineaType.name()} / #{sublineaType.getNameUpper()}</b>",
        expected="Aparece la página correcta asociada a la línea/sublínea")
    public void seleccionSublineaNinos(LineaType lineaType, SublineaNinosType sublineaType, Pais pais) throws Exception {
    	secMenuLateral.selecSublineaNinosIfNotSelected(pais.getShoponline().getLinea(lineaType), sublineaType);
        validaSelecLinea(pais, lineaType, sublineaType);
    }
    
    /**
     * Validamos el resultado esperado después de seleccionar una línea (she, he, kids...) en Móbil
     */
    public void validaSelecLinea(Pais pais, LineaType lineaType, SublineaNinosType sublineaType) throws Exception {
        Linea linea = pais.getShoponline().getLinea(lineaType);
        TypeContentMobil typeContent = linea.getContentMobilType();
        if (sublineaType!=null) {
            typeContent = linea.getSublineaNinos(sublineaType).getContentMobilType();
        }
        
        switch (typeContent) {
        case bloquesnuevo:
            validaSelectLineaNuevoWithCarrusels(pais);
            break;
        case bloquesrebaj:
            validaSelectLineaRebajasWithSublineas(pais);
            break;
        case menus2:
            validaSelecLineaWithMenus2onLevelAssociated(lineaType, sublineaType);
            break;
        case sublineas:            
            validaSelecLineaNinosWithSublineas(lineaType);
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
    public ChecksTM validaSelectLineaNuevoWithCarrusels(Pais pais) {
    	ChecksTM validations = ChecksTM.getNew();
    	
        String listCarrusels = "";
        boolean carruselsOk = true;
        for (Linea linea : pais.getShoponline().getLineasToTest(app)) {
            if (secMenuLateral.isCarruselNuevoAssociated(linea.getType())) {
                listCarrusels+=(linea.getType() + " ");
                if (!secMenuLateral.isCarruselNuevoVisible(linea.getType())) {
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
    public ChecksTM validaSelectLineaRebajasWithSublineas(Pais pais) {
    	ChecksTM validations = ChecksTM.getNew();
        String listSublineas = "";
        boolean isSublineasOk = true;
        for (Linea linea : pais.getShoponline().getLineasToTest(app)) {
            if (secMenuLateral.isSublineaRebajasAssociated(linea.getType())) {
                listSublineas+=(linea.getType() + " ");
                if (!secMenuLateral.isSublineaRebajasVisible(linea.getType())) {
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
    public ChecksTM validaSelecLineaNinosWithSublineas(LineaType lineaNinosType) {
    	ChecksTM validations = ChecksTM.getNew();
	 	validations.add(
			"Está seleccionada la línea <b>" + lineaNinosType + "</b>",
			secMenuLateral.isSelectedLinea(lineaNinosType), State.Warn);
	 	validations.add(
			"Es visible el bloque con las sublíneas de " + lineaNinosType,
			secMenuLateral.isVisibleBlockSublineasNinos(lineaNinosType), State.Warn);
	 	return validations;
    }
    
    @Validation
    public ChecksTM validaSelecLineaWithMenus2onLevelAssociated(LineaType lineaType, SublineaNinosType sublineaType) {
    	ChecksTM validations = ChecksTM.getNew();
	 	validations.add(
			"Está seleccionada la línea <b>" + lineaType + "</b>",
			secMenuLateral.isSelectedLinea(lineaType), State.Warn);
	 	validations.add(
			"Son visibles links de Menú de 2o nivel",
			secMenuLateral.isMenus2onLevelDisplayed(sublineaType), State.Warn);
	 	return validations;
    }    
    
    final static String tagTextMenu = "@TagTextMenu";
    @Step (
    	description="Selección del menú <b>" + tagTextMenu + "</b> (data-ga-label contains #{menu1rstLevel.getDataGaLabelMenuSuperiorDesktop()})", 
        expected="El menú se ejecuta correctamente")
    public void stepClickMenu1rstLevel(Menu1rstLevel menu1rstLevel, Pais pais) throws Exception {
    	secMenuLateral.clickMenuLateral1rstLevel(TypeLocator.dataGaLabelPortion, menu1rstLevel, pais);
        TestMaker.getCurrentStepInExecution().replaceInDescription(tagTextMenu, menu1rstLevel.getNombre());
        ModalCambioPais.closeModalIfVisible(driver);
        validaPaginaResultMenu2onLevel();
    }    
    
    public void validaPaginaResultMenu2onLevel() throws Exception {
    	checkElementsAfterClickMenu2onLevel();
        StdValidationFlags flagsVal = StdValidationFlags.newOne();
        flagsVal.validaSEO = true;
        flagsVal.validaJS = true;
        flagsVal.validaImgBroken = true;
        AllPagesStpV.validacionesEstandar(flagsVal, driver);
    }
    
    @Validation (
    	description="Aparecen artículos, banners, frames, maps o Sliders",
    	level=State.Warn)
    private boolean checkElementsAfterClickMenu2onLevel() throws Exception {
    	PageGaleria pageGaleria = PageGaleria.getNew(Channel.movil_web, app, driver);
        return (
        	pageGaleria.isVisibleArticleUntil(1, 3) ||
            PageLanding.hayIframes(driver) ||
            PageLanding.hayMaps(driver) ||
            PageLanding.haySliders(driver) ||
            ManagerBannersScreen.existBanners(driver));
    }
}
