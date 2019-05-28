package com.mng.robotest.test80.mango.test.stpv.shop.menus;

import java.util.EnumSet;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.TestCaseData;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.ChecksResult;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import static com.mng.robotest.test80.arq.utils.otras.Constantes.PrefixRebajas;

import com.mng.robotest.test80.arq.utils.otras.Constantes;
import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.arq.utils.otras.Constantes.ThreeState;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.data.AppEcom;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Sublinea.SublineaNinosType;
import com.mng.robotest.test80.mango.test.generic.PasosGenAnalitica;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.filtros.FilterCollection;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.PageGaleria;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.PageGaleriaDesktop.TypeArticle;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.KeyMenu1rstLevel;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.Menu1rstLevel;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.MenuLateralDesktop;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.MenuTreeApp;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.SecMenusFiltroCollection;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.SecMenusWrap;
import com.mng.robotest.test80.mango.test.stpv.shop.AllPagesStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.StdValidationFlags;
import com.mng.robotest.test80.mango.test.stpv.shop.galeria.PageGaleriaStpV;

public class SecMenusWrapperStpV {
	static Logger pLogger = LogManager.getLogger(fmwkTest.log4jLogger);
	
    public static SecMenusUserStpV secMenuUser;
    
    @Validation
    public static ChecksResult validateLineas(Pais pais, AppEcom app, Channel channel, WebDriver driver) 
    throws Exception {
        ChecksResult validations = ChecksResult.getNew();
        LineaType[] lineasToTest = Linea.LineaType.values();
        for (LineaType lineaType : lineasToTest) {
            ThreeState stateLinea = pais.getShoponline().stateLinea(lineaType, app);
            if ( stateLinea!=ThreeState.UNKNOWN &&
                (lineaType!=LineaType.rebajas || UtilsMangoTest.validarLineaRebajas(pais))) {
                ThreeState apareceLinea = stateLinea;
                
                //Caso especial de un país con una sóla línea de she -> No ha de aparecer la línea de she
                if (lineaType==LineaType.she && app!=AppEcom.outlet && pais.getShoponline().getNumLineasTiendas(app)==1) {
                    apareceLinea = ThreeState.FALSE;
                }
                
                boolean isLineaPresent = SecMenusWrap.isLineaPresent(lineaType, app, channel, driver);
                if (apareceLinea==ThreeState.TRUE) {
            		validations.add (
        				"<b>Sí</b> aparece el link de la línea <b>" + lineaType + "</b>",
        				isLineaPresent, State.Warn);
                } else {
            		validations.add (
            			"<b>No</b> aparece el link de la línea <b>" + lineaType + "</b>",
            			!isLineaPresent, State.Warn);
                }
            }
        }
            
        return validations;
    }
    
    @Validation
    public static ChecksResult checkLineaRebajas(boolean salesOnInCountry, DataCtxShop dCtxSh, WebDriver driver) {
        ChecksResult validations = ChecksResult.getNew();
        int maxSeconds = 3;
        boolean isPresentLinRebajas = SecMenusWrap.isLineaPresentUntil(LineaType.rebajas, dCtxSh.appE, dCtxSh.channel, maxSeconds, driver);
        if (salesOnInCountry && dCtxSh.pais.isVentaOnline()) {
        	validations.add(
        		PrefixRebajas + "Aparece la línea \"Rebajas\" (lo esperamos hasta " + maxSeconds + " segundos)",
	    		isPresentLinRebajas, State.Defect);
        } else {
        	validations.add(
	    		PrefixRebajas + "No aparece la línea \"Rebajas\"",
	    		!isPresentLinRebajas, State.Defect);
        }
       
        return validations;
    }
    
    /**
     * Recorre todos los menús existentes en la página y crea un step por cada uno de ellos
     */
    public static void stepsMenusLinea(LineaType lineaType, SublineaNinosType sublineaType, DataCtxShop dCtxSh, DataFmwkTest dFTest) 
    throws Exception {
        String paginaLinea = dFTest.driver.getCurrentUrl();
        
        //Obtenemos la lista de menús de la línea
        Linea linea = dCtxSh.pais.getShoponline().getLinea(lineaType);
        List<String> listMenusLabel = SecMenusWrap.getListDataLabelsMenus(linea, sublineaType, dCtxSh.channel, dCtxSh.appE, dFTest.driver);
        //for (int i=0; i<5; i++) {
        for (int i=0; i<listMenusLabel.size(); i++) {
            try {
            	//Creamos un menú con el nombre=dataGaLabel (pues todavía no lo conocemos)
            	Menu1rstLevel menu1rstLevel = MenuTreeApp.getMenuLevel1From(dCtxSh.appE, KeyMenu1rstLevel.from(lineaType, sublineaType, listMenusLabel.get(i)));
            	menu1rstLevel.setDataGaLabel(listMenusLabel.get(i));
                if (dCtxSh.channel==Channel.movil_web) {
                    SecMenuLateralMobilStpV.stepClickMenu1rstLevel(menu1rstLevel, dCtxSh.pais, dCtxSh.appE, dFTest.driver);
                } else {
                    SecMenusDesktopStpV.stepEntradaMenuDesktop(menu1rstLevel, paginaLinea, dCtxSh, dFTest.driver);
                }
            }
            catch (Exception e) {
                //En caso de excepción no queremos que el caso de prueba pare
            	pLogger.warn("Problem in selection of menu " + lineaType + " / " + sublineaType + " / " + listMenusLabel.get(i), e);
            }        
        }
    }
    
    public static void navSeleccionaCarruselsLinea(Pais pais, LineaType lineaNuevoOReb, AppEcom app, Channel channel, DataFmwkTest dFTest) throws Exception {
        if (channel==Channel.movil_web) {
            SecMenuLateralMobilStpV.navClickLineaAndCarrusels(lineaNuevoOReb, pais, app, dFTest.driver);
        } else {
            SecMenusDesktopStpV.stepValidaCarrusels(pais, lineaNuevoOReb, app, dFTest.driver);
        }
    }
    
    @Step (
    	description="Seleccionar el menú <b>#{menu1rstLevel}</b>",
        expected="Se obtiene el catálogo de artículos asociados al menú")
    public static void accesoMenuXRef(Menu1rstLevel menu1rstLevel, DataCtxShop dCtxSh, WebDriver driver) 
    throws Exception {
        SecMenusWrap.seleccionarMenuXHref(menu1rstLevel, dCtxSh.pais, dCtxSh.channel, driver);
        checkIsVisibleAarticle(dCtxSh, 3, driver);
        
        StdValidationFlags flagsVal = StdValidationFlags.newOne();
        flagsVal.validaSEO = true;
        flagsVal.validaJS = true;
        flagsVal.validaImgBroken = true;
        AllPagesStpV.validacionesEstandar(flagsVal, driver);
    }
    
    @Validation (
    	description="Como mínimo se obtiene 1 artículo (lo esperamos un máximo de #{maxSecondsWait} segundos)",
    	level=State.Warn,
    	avoidEvidences=true)
    private static boolean checkIsVisibleAarticle(DataCtxShop dCtxSh, int maxSecondsWait, WebDriver driver) throws Exception {
        PageGaleria pageGaleria = PageGaleria.getInstance(dCtxSh.channel, dCtxSh.appE, driver);
        return (pageGaleria.isVisibleArticuloUntil(1, maxSecondsWait));
    }
    
    public static void selectMenu1rstLevelTypeCatalog(Menu1rstLevel menu1rstLevel, DataCtxShop dCtxSh, WebDriver driver) 
    throws Exception {
        if (dCtxSh.channel==Channel.movil_web) {
            SecMenuLateralMobilStpV.selectMenuLateral1rstLevelTypeCatalog(menu1rstLevel, dCtxSh, driver);
        } else {	
        	SecMenusDesktopStpV.selectMenuSuperiorTypeCatalog(menu1rstLevel, dCtxSh, driver);
        }
    }
    
    public static void selectMenuLateral1erLevelTypeCatalog(Menu1rstLevel menu1rstLevel, DataCtxShop dCtxSh, WebDriver driver) 
    throws Exception {
        if (dCtxSh.channel==Channel.movil_web) {
            SecMenuLateralMobilStpV.selectMenuLateral1rstLevelTypeCatalog(menu1rstLevel, dCtxSh, driver); 
        } else {
        	SecMenusDesktopStpV.selectMenuLateral1rstLevelTypeCatalog(menu1rstLevel, dCtxSh, driver);        
        }
    }
    
    /**
     * Validación de la selección de un menú lateral de 1er o 2o nivel 
     */
	public static void validaSelecMenu(MenuLateralDesktop menu, DataCtxShop dCtxSh, WebDriver driver)
    throws Exception {
		validateGaleriaAfeterSelectMenu(dCtxSh, driver);
        if (dCtxSh.channel==Channel.desktop) {
            SecMenusDesktopStpV.validationsSelecMenuEspecificDesktop(menu, dCtxSh.channel, dCtxSh.appE, driver);
        }
       
        //Validaciones estándar. 
        StdValidationFlags flagsVal = StdValidationFlags.newOne();
        flagsVal.validaSEO = true;
        flagsVal.validaJS = true;
        flagsVal.validaImgBroken = false;
        AllPagesStpV.validacionesEstandar(flagsVal, driver);
        
        //Por defecto aplicaremos todas las avalidaciones (Google Analytics, Criteo, NetTraffic y DataLayer)
        EnumSet<Constantes.AnalyticsVal> analyticSet = EnumSet.of(Constantes.AnalyticsVal.GoogleAnalytics,
                                                                  Constantes.AnalyticsVal.NetTraffic, 
                                                                  Constantes.AnalyticsVal.Criteo,
                                                                  Constantes.AnalyticsVal.DataLayer);
        
        PasosGenAnalitica.validaHTTPAnalytics(dCtxSh.appE, menu.getLinea(), analyticSet, driver);
    }
    
	@Validation
    public static ChecksResult validateGaleriaAfeterSelectMenu(DataCtxShop dCtxSh, WebDriver driver) 
	throws Exception {
		ChecksResult validations = ChecksResult.getNew();
		PageGaleria pageGaleria = PageGaleria.getInstance(dCtxSh.channel, dCtxSh.appE, driver);
		int maxSecondsToWaitArticle = 3;
		int maxSecondsToWaitIcon = 2;
		validations.add (
			"Como mínimo se obtiene un artículo (lo esperamos hasta " + maxSecondsToWaitArticle + " segundos)",
			pageGaleria.isVisibleArticleUntil(1/*numArticulo*/, maxSecondsToWaitArticle), State.Warn);
		if (dCtxSh.appE==AppEcom.shop) {
			validations.add (
				"El 1er artículo tiene 1 icono de favorito asociado (lo esperamos hasta " + maxSecondsToWaitIcon + " segundos)",
				pageGaleria.isArticleWithHearthIconPresentUntil(1, maxSecondsToWaitIcon), State.Defect);
			validations.add (
				"Cada artículo tiene 1 icono de favoritos asociado",
				pageGaleria.eachArticlesHasOneFavoriteIcon(), State.Info, true);
		} else {
			validations.add (
				"No aparece ningún icono de favoritos asociado a ningún artículo",
				pageGaleria.getNumFavoritoIcons() == 0, State.Defect);
		}
		
		return validations;
    }
    
    public static DatosStep seleccionLinea(LineaType lineaType, SublineaNinosType sublineaType, DataCtxShop dCtxSh, WebDriver driver) 
    throws Exception {
        if (sublineaType==null) {
            return seleccionLinea(lineaType, dCtxSh, driver);
        }
        
        return seleccionSublinea(lineaType, sublineaType, dCtxSh, driver);
    }
    
    public static DatosStep seleccionLinea(LineaType lineaType, DataCtxShop dCtxSh, WebDriver driver) throws Exception {
        if (dCtxSh.channel==Channel.movil_web) {
            SecMenuLateralMobilStpV.seleccionLinea(lineaType, dCtxSh.pais, dCtxSh.appE, driver);
            return TestCaseData.getDatosLastStep();
        }
        
        SecMenusDesktopStpV.seleccionLinea(lineaType, dCtxSh, driver);
        return TestCaseData.getDatosLastStep();
    }
    
    public static DatosStep seleccionSublinea(LineaType lineaType, SublineaNinosType sublineaType, DataCtxShop dCtxSh, WebDriver driver)
    throws Exception {
        if (dCtxSh.channel==Channel.movil_web) {
            SecMenuLateralMobilStpV.seleccionSublineaNinos(lineaType, sublineaType, dCtxSh, driver);
            return TestCaseData.getDatosLastStep();
        }
        
        SecMenusDesktopStpV.seleccionSublinea(lineaType, sublineaType, dCtxSh, driver);
        return TestCaseData.getDatosLastStep();
    }
    
    public static void selectFiltroCollectionIfExists(FilterCollection typeMenu, Channel channel, AppEcom app, WebDriver driver) 
    throws Exception {
    	SecMenusFiltroCollection filtrosCollection = SecMenusFiltroCollection.make(channel, app, driver);
    	if (filtrosCollection.isVisibleMenu(FilterCollection.nextSeason)) {
    		selectFiltroCollection(typeMenu, channel, app, driver);
    	}
    }
    
    @Step (
    	description="Seleccionar filtro de colecciones <b>#{typeMenu}</b>", 
        expected="Aparece una galería con artículos de temporadas#{typeMenu.getListTempArticles()}")
    public static void selectFiltroCollection(FilterCollection typeMenu, Channel channel, AppEcom app, WebDriver driver) 
    throws Exception {
    	SecMenusFiltroCollection filtrosCollection = SecMenusFiltroCollection.make(channel, app, driver);
    	filtrosCollection.click(typeMenu);        
        if (channel==Channel.desktop) {
	        PageGaleriaStpV pageGaleriaStpV = PageGaleriaStpV.getInstance(channel, app, driver);
	        if (typeMenu == FilterCollection.sale) {
	            pageGaleriaStpV.validaArticlesOfTemporadas(typeMenu.getListTempArticles());
	            pageGaleriaStpV.validaNotArticlesOfTypeDesktop(TypeArticle.norebajado, State.Warn, false);
	        }
	        
	        if (typeMenu == FilterCollection.nextSeason) {
	        	pageGaleriaStpV.validaNotArticlesOfTypeDesktop(TypeArticle.rebajado, State.Info, true);
	        	pageGaleriaStpV.validaArticlesOfTemporadas(typeMenu.getListTempArticles(), State.Info, true);
	        }
        }
    }    
}
