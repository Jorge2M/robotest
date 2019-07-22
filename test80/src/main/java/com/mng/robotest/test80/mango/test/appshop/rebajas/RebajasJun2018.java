package com.mng.robotest.test80.mango.test.appshop.rebajas;

import org.testng.ITestContext;
import java.lang.reflect.Method;
import org.testng.annotations.*;
import java.util.List;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.TestCaseData;
import com.mng.robotest.test80.arq.utils.controlTest.mango.*;
import com.mng.robotest.test80.mango.test.data.Constantes;
import com.mng.robotest.test80.mango.conftestmaker.Utils;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.*;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Sublinea.SublineaNinosType;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;
import com.mng.robotest.test80.mango.test.jdbc.dao.RebajasPaisDAO;
import com.mng.robotest.test80.mango.test.pageobject.shop.filtros.FilterCollection;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.PageGaleriaDesktop.TypeArticle;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.KeyMenu1rstLevel;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.Menu1rstLevel;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.MenuTreeApp;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.SecMenusWrap;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.SecMenusWrap.bloqueMenu;
import com.mng.robotest.test80.mango.test.stpv.shop.PagePrehomeStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.banner.SecBannersStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.galeria.PageGaleriaStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.menus.SecMenusDesktopStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.menus.SecMenusUserStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.menus.SecMenusWrapperStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.registro.PageRegistroIniStpV;

public class RebajasJun2018 extends GestorWebDriver /*Funcionalidades genéricas propias de MANGO*/ {

    String baseUrl;
    boolean acceptNextAlert = true;
    private String index_fact;
    public int prioridad;
    Pais paisFactory = null;
    IdiomaPais idiomaFactory = null;
    List<Linea> lineasAprobar = null;
    
    //Si añadimos un constructor para el @Factory hemos de añadir este constructor para la invocación desde SmokeTest
    public RebajasJun2018() {}
    
    //Constructor para invocación desde @Factory
    public RebajasJun2018(Pais pais, IdiomaPais idioma, List<Linea> lineasAprobar, int prioridad) {
        this.index_fact = pais.getNombre_pais() + " (" + pais.getCodigo_pais() + ") " + "-" + idioma.getCodigo().getLiteral();
        this.paisFactory = pais;
        this.idiomaFactory = idioma;
        this.prioridad = prioridad;
        this.lineasAprobar = lineasAprobar;
    }
	  
    @BeforeMethod (groups={"RebajasDic2018", "Canal:desktop_App:shop", "SupportsFactoryCountrys"})
    @Parameters({"brwsr-path", "urlBase", "AppEcom", "Channel"})
    public void login(String bpath, String urlAcceso, String appEcom, String channel, ITestContext context, Method method) throws Exception {
        //Recopilación de parámetros
        DataCtxShop dCtxSh = new DataCtxShop();
        dCtxSh.setAppEcom(appEcom);
        dCtxSh.setChannel(channel);
        dCtxSh.pais = this.paisFactory;
        dCtxSh.idioma = this.idiomaFactory;
        dCtxSh.urlAcceso = urlAcceso;
        
        Utils.storeDataShopForTestMaker(bpath, this.index_fact, dCtxSh, context, method);
    }
	
    @SuppressWarnings("unused")
    @AfterMethod (groups={"RebajasDic2018", "Canal:desktop_App:shop", "SupportsFactoryCountrys"}, alwaysRun = true)
    public void logout(ITestContext context, Method method) throws Exception {
        WebDriver driver = TestCaseData.getWebDriver();
        super.quitWebDriver(driver, context);
    }	
	
    @SuppressWarnings("static-access")
    @Test (groups={"RebajasDic201", "Canal:desktop_App:shop", "SupportsFactoryCountrys"}, 
    	   description="Validaciones específicas correspondientes a las Rebajas de Diciembre-2017")
    public void REB001_RebajasDic2018() throws Exception {
    	DataFmwkTest dFTest = TestCaseData.getdFTest();
        DataCtxShop dCtxSh = (DataCtxShop)TestCaseData.getData(Constantes.idCtxSh);
        int numLineasPais = dCtxSh.pais.getShoponline().getNumLineasTiendas(dCtxSh.appE);
    	boolean salesOnInCountry = RebajasPaisDAO.isRebajasEnabledPais(dCtxSh.pais.getCodigo_pais());
            
        PagePrehomeStpV.seleccionPaisIdiomaAndEnter(dCtxSh, dFTest.driver);
        if (numLineasPais==1) {
            return;
        }
        
        SecMenusWrapperStpV secMenusStpV = SecMenusWrapperStpV.getNew(dCtxSh, dFTest.driver);
        secMenusStpV.checkLineaRebajas(salesOnInCountry, dCtxSh);
    	//PageHomeMarcasStpV.bannerRebajas2018.checkBanner(salesOnInCountry, TypeHome.Multimarca, dCtxSh, dFTest.driver);
    	//checkMsgNewsletterFooter(salesOnInCountry, dCtxSh.idioma, driver);
        
        if (salesOnInCountry && dCtxSh.pais.isVentaOnline()) {
	        //Step&Validation
        	int maxBannersToLoad = 1;
        	int posBannerToTest = 1;
        	boolean applyValidations = true;
        	SecBannersStpV secBannersStpV = new SecBannersStpV(maxBannersToLoad, dFTest.driver);
	        secBannersStpV.seleccionarBanner(posBannerToTest, applyValidations, dCtxSh.appE, dCtxSh.channel);
	        PageGaleriaStpV pageGaleriaStpV = PageGaleriaStpV.getInstance(dCtxSh.channel, dCtxSh.appE, dFTest.driver);
	        pageGaleriaStpV.validaRebajasJun2018Desktop(salesOnInCountry, true/*isGaleriaSale*/, dCtxSh.pais, dCtxSh.idioma, LineaType.she, bloqueMenu.prendas);
	        PageGaleriaStpV.clickMoreInfoBannerRebajasJun2018(dFTest.driver);
	        
	        SecMenusDesktopStpV secMenusDesktopStpV = SecMenusDesktopStpV.getNew(dCtxSh.pais, dCtxSh.appE, dFTest.driver);
	        secMenusDesktopStpV.stepValidaCarrusels(LineaType.rebajas);
        }
        
        if (salesOnInCountry) {
        	SecMenusUserStpV userMenusStpV = SecMenusUserStpV.getNew(dCtxSh.channel, dCtxSh.appE, dFTest.driver);
        	userMenusStpV.selectRegistrate(dCtxSh);
	        PageRegistroIniStpV.validaRebajasJun2018(dCtxSh.idioma, dFTest);
        }
        
        //Aplicamos el test a las líneas/sublíneas
        for (Linea linea : this.lineasAprobar) {
            if (UtilsMangoTest.validarLinea(dCtxSh.pais, linea, dCtxSh.appE) &&
                linea.getType()!=LineaType.edits) {
                validaLinea(salesOnInCountry, linea, null, ("banners".compareTo(linea.getContentDesk())==0), dCtxSh, dFTest);
                for (Sublinea sublinea : linea.getListSublineas()) {
                    validaLinea(salesOnInCountry, linea, sublinea, ("banners".compareTo(sublinea.getContentDesk())==0), dCtxSh, dFTest);
                }
            }
        }
    }
    
    private void validaLinea(boolean salesOnInCountry, Linea linea, Sublinea sublinea, boolean areBanners, DataCtxShop dCtxSh, DataFmwkTest dFTest) 
    throws Exception {
        //Obtenemos el tipo de línea/sublínea
        LineaType lineaType = linea.getType();
        SublineaNinosType sublineaType = null;
        if (sublinea!=null) {
            sublineaType = sublinea.getTypeSublinea();
        }
        
        //Selección de la línea/sublínea
        SecMenusWrapperStpV secMenusStpV = SecMenusWrapperStpV.getNew(dCtxSh, dFTest.driver);
        secMenusStpV.seleccionLinea(lineaType, sublineaType, dCtxSh);
        secMenusStpV.checkLineaRebajas(salesOnInCountry, dCtxSh);
    	if (areBanners) {
    		//PageHomeMarcasStpV.bannerRebajas2018.checkBanner(salesOnInCountry, TypeHome.Multimarca, dCtxSh, dFTest.driver);
    	}
    	//checkMsgNewsletterFooter(salesOnInCountry, dCtxSh.idioma, driver);
    	SecMenusWrap secMenus = SecMenusWrap.getNew(dCtxSh.channel, dCtxSh.appE, dFTest.driver);
        if (secMenus.canClickMenuArticles(dCtxSh.pais, linea, sublinea)) {
            PageGaleriaStpV pageGaleriaStpV = PageGaleriaStpV.getInstance(dCtxSh.channel, dCtxSh.appE, dFTest.driver);
            
//        	  Menu1rstLevel menuPantalones = MenuTreeApp.getMenuLevel1From(dCtxSh.appE, KeyMenu1rstLevel.from(lineaType, sublineaType, "pantalones"));
//            SecMenusWrapperStpV.selectMenu1rstLevelTypeCatalog(menuPantalones, dCtxSh, dFTest.driver);
//            boolean isGaleriaSale = true;
//            pageGaleriaStpV.validaRebajasJun2018Desktop(salesOnInCountry, !isGaleriaSale, dCtxSh.pais, dCtxSh.idioma, linea.getType(), bloqueMenu.prendas);
//            
//            Menu1rstLevel menuZapatos = MenuTreeApp.getMenuLevel1From(dCtxSh.appE, KeyMenu1rstLevel.from(lineaType, sublineaType, "zapatos"));
//            SecMenusWrapperStpV.selectMenu1rstLevelTypeCatalog(menuZapatos, dCtxSh, dFTest.driver);
//            pageGaleriaStpV.validaRebajasJun2018Desktop(salesOnInCountry, !isGaleriaSale, dCtxSh.pais, dCtxSh.idioma, linea.getType(), bloqueMenu.accesorios);
            
            Menu1rstLevel menuRebajas = MenuTreeApp.getMenuLevel1From(dCtxSh.appE, KeyMenu1rstLevel.from(lineaType, sublineaType, "promoción"));
            menuRebajas.setDataGaLabel("rebajas");
            SecMenusDesktopStpV secMenusDesktopStpV = SecMenusDesktopStpV.getNew(dCtxSh.pais, dCtxSh.appE, dFTest.driver);
            if (salesOnInCountry) {
	            //Click filtros laterales de rebajas
            	secMenusStpV.selectFiltroCollection(FilterCollection.sale);
            	secMenusStpV.selectFiltroCollectionIfExists(FilterCollection.nextSeason);
	            
	            Menu1rstLevel menuNuevaTemp = MenuTreeApp.getMenuLevel1From(dCtxSh.appE, KeyMenu1rstLevel.from(lineaType, sublineaType, "nueva temporada"));
	            String dataGaMenuNuevaTemporada = getDataGaLabelNuevaTemporada(dCtxSh.pais.getCodigo_pais(), sublineaType);
	            menuNuevaTemp.setDataGaLabel(dataGaMenuNuevaTemporada);
	            secMenusDesktopStpV.stepEntradaMenuDesktop(menuNuevaTemp, "");
	            pageGaleriaStpV.validaNotArticlesOfTypeDesktop(TypeArticle.rebajado, State.Defect, false);
	            //PageGaleriaStpV.validaArticlesOfTemporadas(tempArticlesNextSeason, validaNotNewArticles, datosStep, dFTest);
            
	            secMenusStpV.selectMenu1rstLevelTypeCatalog(menuRebajas, dCtxSh);
	            pageGaleriaStpV.validaRebajasJun2018Desktop(salesOnInCountry, true, dCtxSh.pais, dCtxSh.idioma, linea.getType(), bloqueMenu.prendas);
	            PageGaleriaStpV.clickMoreInfoBannerRebajasJun2018(dFTest.driver);
	            
	            int maxRebajas = RebajasPaisDAO.getMaxRebajas(dCtxSh.pais.getCodigo_pais());
	            if (maxRebajas==70) {
	            	pageGaleriaStpV.validaRebajasHasta70Jun2018(dCtxSh.idioma);
	            }
            } else {
                Menu1rstLevel menuPromocion = MenuTreeApp.getMenuLevel1From(dCtxSh.appE, KeyMenu1rstLevel.from(lineaType, sublineaType, "promocion"));
                menuPromocion.setDataGaLabel("promocion");
                secMenusDesktopStpV.isPresentMenuSuperior(menuPromocion);
                secMenusDesktopStpV.isNotPresentMenuSuperior(menuRebajas);
            }
        }
    }
    
    private String getDataGaLabelNuevaTemporada(String codigoPais, SublineaNinosType sublineaType) {
        if ("075".compareTo(codigoPais)==0 && sublineaType==SublineaNinosType.nino) {
        	return ("nueva_coleccion");
        }
        return "nueva_temporada";
    }
}