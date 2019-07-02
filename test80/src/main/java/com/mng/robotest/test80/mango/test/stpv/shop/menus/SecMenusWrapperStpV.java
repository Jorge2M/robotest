package com.mng.robotest.test80.mango.test.stpv.shop.menus;

import java.util.EnumSet;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

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
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
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
	
	private final Channel channel;
	private final AppEcom app;
	private final Pais pais;
	private final WebDriver driver;
	private final SecMenusUserStpV secMenusUserStpV;
    private final SecMenuLateralMobilStpV secMenuLateralMobilStpV;
    private final SecMenusDesktopStpV secMenusDesktopStpV;
    private final SecMenusWrap secMenusWrap;
    
    private SecMenusWrapperStpV(Channel channel, AppEcom app, Pais pais, WebDriver driver) {
    	this.channel = channel;
    	this.app = app;
    	this.pais = pais;
    	this.driver = driver;
    	this.secMenusUserStpV = SecMenusUserStpV.getNew(channel, app, driver);
    	this.secMenuLateralMobilStpV = SecMenuLateralMobilStpV.getNew(app, driver);
    	this.secMenusDesktopStpV = SecMenusDesktopStpV.getNew(pais, app, driver);
    	this.secMenusWrap = SecMenusWrap.getNew(channel, app, driver);
    }
    
    public static SecMenusWrapperStpV getNew(Channel channel, AppEcom app, Pais pais, WebDriver driver) {
    	return (new SecMenusWrapperStpV(channel, app, pais, driver));
    }
    
    public static SecMenusWrapperStpV getNew(DataCtxShop dCtxSh, WebDriver driver) {
    	return (getNew(dCtxSh.channel, dCtxSh.appE, dCtxSh.pais, driver));
    }
    
    public SecMenusUserStpV getMenusUser() {
    	return this.secMenusUserStpV;
    }
    
    @Validation
    public ChecksResult validateLineas(Pais pais) throws Exception {
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
                
                boolean isLineaPresent = secMenusWrap.isLineaPresent(lineaType);
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
    public ChecksResult checkLineaRebajas(boolean salesOnInCountry, DataCtxShop dCtxSh) {
        ChecksResult validations = ChecksResult.getNew();
        int maxSeconds = 3;
        boolean isPresentLinRebajas = secMenusWrap.isLineaPresentUntil(LineaType.rebajas, maxSeconds);
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
    public void stepsMenusLinea(LineaType lineaType, SublineaNinosType sublineaType) throws Exception {
        String paginaLinea = driver.getCurrentUrl();
        
        //Obtenemos la lista de menús de la línea
        Linea linea = pais.getShoponline().getLinea(lineaType);
        List<String> listMenusLabel = secMenusWrap.getListDataLabelsMenus(linea, sublineaType);
        //for (int i=0; i<5; i++) {
        for (int i=0; i<listMenusLabel.size(); i++) {
            try {
            	//Creamos un menú con el nombre=dataGaLabel (pues todavía no lo conocemos)
            	Menu1rstLevel menu1rstLevel = MenuTreeApp.getMenuLevel1From(app, KeyMenu1rstLevel.from(lineaType, sublineaType, listMenusLabel.get(i)));
            	menu1rstLevel.setDataGaLabel(listMenusLabel.get(i));
                if (channel==Channel.movil_web) {
                    secMenuLateralMobilStpV.stepClickMenu1rstLevel(menu1rstLevel, pais);
                } else {
                    secMenusDesktopStpV.stepEntradaMenuDesktop(menu1rstLevel, paginaLinea);
                }
            }
            catch (Exception e) {
                //En caso de excepción no queremos que el caso de prueba pare
            	pLogger.warn("Problem in selection of menu " + lineaType + " / " + sublineaType + " / " + listMenusLabel.get(i), e);
            }        
        }
    }
    
    public void navSeleccionaCarruselsLinea(Pais pais, LineaType lineaNuevoOReb) throws Exception {
        if (channel==Channel.movil_web) {
            secMenuLateralMobilStpV.navClickLineaAndCarrusels(lineaNuevoOReb, pais);
        } else {
            secMenusDesktopStpV.stepValidaCarrusels(lineaNuevoOReb);
        }
    }
    
    @Step (
    	description="Seleccionar el menú <b>#{menu1rstLevel}</b>",
        expected="Se obtiene el catálogo de artículos asociados al menú")
    public void accesoMenuXRef(Menu1rstLevel menu1rstLevel, DataCtxShop dCtxSh) throws Exception {
    	secMenusWrap.seleccionarMenuXHref(menu1rstLevel, dCtxSh.pais);
        checkIsVisibleAarticle(dCtxSh, 3);
        
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
    private boolean checkIsVisibleAarticle(DataCtxShop dCtxSh, int maxSecondsWait) throws Exception {
        PageGaleria pageGaleria = PageGaleria.getInstance(dCtxSh.channel, dCtxSh.appE, driver);
        return (pageGaleria.isVisibleArticuloUntil(1, maxSecondsWait));
    }
    
    public void selectMenu1rstLevelTypeCatalog(Menu1rstLevel menu1rstLevel, DataCtxShop dCtxSh) throws Exception {
        if (dCtxSh.channel==Channel.movil_web) {
            secMenuLateralMobilStpV.selectMenuLateral1rstLevelTypeCatalog(menu1rstLevel, dCtxSh);
        } else {	
        	secMenusDesktopStpV.selectMenuSuperiorTypeCatalog(menu1rstLevel, dCtxSh);
        }
    }
    
    public void selectMenuLateral1erLevelTypeCatalog(Menu1rstLevel menu1rstLevel, DataCtxShop dCtxSh) throws Exception {
        if (dCtxSh.channel==Channel.movil_web) {
            secMenuLateralMobilStpV.selectMenuLateral1rstLevelTypeCatalog(menu1rstLevel, dCtxSh); 
        } else {
        	secMenusDesktopStpV.selectMenuLateral1rstLevelTypeCatalog(menu1rstLevel, dCtxSh);        
        }
    }
    
    /**
     * Validación de la selección de un menú lateral de 1er o 2o nivel 
     */
	public void validaSelecMenu(MenuLateralDesktop menu, DataCtxShop dCtxSh) throws Exception {
		validateGaleriaAfeterSelectMenu(dCtxSh);
        if (dCtxSh.channel==Channel.desktop) {
            secMenusDesktopStpV.validationsSelecMenuEspecificDesktop(menu);
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
    public ChecksResult validateGaleriaAfeterSelectMenu(DataCtxShop dCtxSh) throws Exception {
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
    
    public DatosStep seleccionLinea(LineaType lineaType, SublineaNinosType sublineaType, DataCtxShop dCtxSh) throws Exception {
        if (sublineaType==null) {
            return seleccionLinea(lineaType);
        }
        
        return seleccionSublinea(lineaType, sublineaType, dCtxSh);
    }
    
    public DatosStep seleccionLinea(LineaType lineaType) throws Exception {
        if (channel==Channel.movil_web) {
            secMenuLateralMobilStpV.seleccionLinea(lineaType, pais);
            return TestCaseData.getDatosLastStep();
        }
        
        secMenusDesktopStpV.seleccionLinea(lineaType);
        return TestCaseData.getDatosLastStep();
    }
    
    public DatosStep seleccionSublinea(LineaType lineaType, SublineaNinosType sublineaType, DataCtxShop dCtxSh)
    throws Exception {
        if (dCtxSh.channel==Channel.movil_web) {
            secMenuLateralMobilStpV.seleccionSublineaNinos(lineaType, sublineaType, pais);
            return TestCaseData.getDatosLastStep();
        }
        
        secMenusDesktopStpV.seleccionSublinea(lineaType, sublineaType);
        return TestCaseData.getDatosLastStep();
    }
    
    public void selectFiltroCollectionIfExists(FilterCollection typeMenu) throws Exception {
    	SecMenusFiltroCollection filtrosCollection = SecMenusFiltroCollection.make(channel, app, driver);
    	if (filtrosCollection.isVisibleMenu(FilterCollection.nextSeason)) {
    		selectFiltroCollection(typeMenu);
    	}
    }
    
    @Step (
    	description="Seleccionar filtro de colecciones <b>#{typeMenu}</b>", 
        expected="Aparece una galería con artículos de temporadas#{typeMenu.getListTempArticles()}")
    public void selectFiltroCollection(FilterCollection typeMenu) throws Exception {
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
