package com.mng.robotest.test80.mango.test.appshop.rebajas;

import org.testng.annotations.*;
import java.util.List;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.conf.State;
import com.mng.testmaker.service.TestMaker;
import com.mng.robotest.test80.access.InputParamsMango;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
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

public class RebajasJun2018 {

    public int prioridad;
	private InputParamsMango inputParamsSuite = null;
    private Pais paisFactory = null;
    private IdiomaPais idiomaFactory = null;
    private List<Linea> lineasAprobar = null;
    private String index_fact;
    
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

    public void setInputParamsSuite() throws Exception {
    	if (inputParamsSuite==null) {
    		inputParamsSuite = (InputParamsMango)TestMaker.getTestCase().getInputParamsSuite();
    	}
    }
    
    private DataCtxShop getCtxShForTest() {
	    DataCtxShop dCtxSh = new DataCtxShop();
	    dCtxSh.setAppEcom((AppEcom)inputParamsSuite.getApp());
	    dCtxSh.setChannel(inputParamsSuite.getChannel());
	    dCtxSh.pais = this.paisFactory;
	    dCtxSh.idioma = this.idiomaFactory;
	    //dCtxSh.urlAcceso = inputParamsSuite.getUrlBase();
	    return dCtxSh;
    }
	
    @SuppressWarnings("static-access")
    @Test (groups={"RebajasDic201", "Canal:desktop_App:shop", "SupportsFactoryCountrys"}, 
    	   description="Validaciones específicas correspondientes a las Rebajas de Diciembre-2017")
    public void REB001_RebajasDic2018() throws Exception {
    	setInputParamsSuite();
    	TestMaker.getTestCase().setSpecificInputData(index_fact);
        DataCtxShop dCtxSh = getCtxShForTest();
    	WebDriver driver = TestMaker.getDriverTestCase();
        int numLineasPais = dCtxSh.pais.getShoponline().getNumLineasTiendas(dCtxSh.appE);
        RebajasPaisDAO rebajasDAO = new RebajasPaisDAO();
    	boolean salesOnInCountry = rebajasDAO.isRebajasEnabledPais(dCtxSh.pais.getCodigo_pais());

        PagePrehomeStpV.seleccionPaisIdiomaAndEnter(dCtxSh, driver);
        if (numLineasPais==1) {
            return;
        }
        
        SecMenusWrapperStpV secMenusStpV = SecMenusWrapperStpV.getNew(dCtxSh, driver);
        secMenusStpV.checkLineaRebajas(salesOnInCountry, dCtxSh);
    	//PageHomeMarcasStpV.bannerRebajas2018.checkBanner(salesOnInCountry, TypeHome.Multimarca, dCtxSh, dFTest.driver);
    	//checkMsgNewsletterFooter(salesOnInCountry, dCtxSh.idioma, driver);
        
        if (salesOnInCountry && dCtxSh.pais.isVentaOnline()) {
	        //Step&Validation
        	int maxBannersToLoad = 1;
        	int posBannerToTest = 1;
        	boolean applyValidations = true;
        	SecBannersStpV secBannersStpV = new SecBannersStpV(maxBannersToLoad, driver);
	        secBannersStpV.seleccionarBanner(posBannerToTest, applyValidations, dCtxSh.appE, dCtxSh.channel);
	        PageGaleriaStpV pageGaleriaStpV = PageGaleriaStpV.getInstance(dCtxSh.channel, dCtxSh.appE, driver);
	        pageGaleriaStpV.validaRebajasJun2018Desktop(salesOnInCountry, true, dCtxSh.pais, dCtxSh.idioma, LineaType.she, bloqueMenu.prendas);
	        PageGaleriaStpV.clickMoreInfoBannerRebajasJun2018(driver);
	        
	        SecMenusDesktopStpV secMenusDesktopStpV = SecMenusDesktopStpV.getNew(dCtxSh.pais, dCtxSh.appE, driver);
	        secMenusDesktopStpV.stepValidaCarrusels(LineaType.rebajas);
        }
        
        if (salesOnInCountry) {
        	SecMenusUserStpV userMenusStpV = SecMenusUserStpV.getNew(dCtxSh.channel, dCtxSh.appE, driver);
        	userMenusStpV.selectRegistrate(dCtxSh);
        	PageRegistroIniStpV pageRegistroIniStpV = PageRegistroIniStpV.getNew(driver);
        	pageRegistroIniStpV.validaRebajasJun2018(dCtxSh.idioma);
        }
        
        //Aplicamos el test a las líneas/sublíneas
        for (Linea linea : this.lineasAprobar) {
            if (UtilsMangoTest.validarLinea(dCtxSh.pais, linea, dCtxSh.appE) &&
                linea.getType()!=LineaType.edits) {
                validaLinea(salesOnInCountry, linea, null, ("banners".compareTo(linea.getContentDesk())==0), dCtxSh, driver);
                for (Sublinea sublinea : linea.getListSublineas()) {
                    validaLinea(salesOnInCountry, linea, sublinea, ("banners".compareTo(sublinea.getContentDesk())==0), dCtxSh, driver);
                }
            }
        }
    }
    
    private void validaLinea(boolean salesOnInCountry, Linea linea, Sublinea sublinea, boolean areBanners, DataCtxShop dCtxSh, WebDriver driver) 
    throws Exception {
        //Obtenemos el tipo de línea/sublínea
        LineaType lineaType = linea.getType();
        SublineaNinosType sublineaType = null;
        if (sublinea!=null) {
            sublineaType = sublinea.getTypeSublinea();
        }
        
        //Selección de la línea/sublínea
        SecMenusWrapperStpV secMenusStpV = SecMenusWrapperStpV.getNew(dCtxSh, driver);
        secMenusStpV.seleccionLinea(lineaType, sublineaType, dCtxSh);
        secMenusStpV.checkLineaRebajas(salesOnInCountry, dCtxSh);
    	if (areBanners) {
    		//PageHomeMarcasStpV.bannerRebajas2018.checkBanner(salesOnInCountry, TypeHome.Multimarca, dCtxSh, dFTest.driver);
    	}
    	//checkMsgNewsletterFooter(salesOnInCountry, dCtxSh.idioma, driver);
    	SecMenusWrap secMenus = SecMenusWrap.getNew(dCtxSh.channel, dCtxSh.appE, driver);
        if (secMenus.canClickMenuArticles(dCtxSh.pais, linea, sublinea)) {
            PageGaleriaStpV pageGaleriaStpV = PageGaleriaStpV.getInstance(dCtxSh.channel, dCtxSh.appE, driver);
            
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
            SecMenusDesktopStpV secMenusDesktopStpV = SecMenusDesktopStpV.getNew(dCtxSh.pais, dCtxSh.appE, driver);
            if (salesOnInCountry) {
	            //Click filtros laterales de rebajas
            	secMenusStpV.selectFiltroCollection(FilterCollection.sale);
            	secMenusStpV.selectFiltroCollectionIfExists(FilterCollection.nextSeason);
	            
	            Menu1rstLevel menuNuevaTemp = MenuTreeApp.getMenuLevel1From(dCtxSh.appE, KeyMenu1rstLevel.from(lineaType, sublineaType, "nueva temporada"));
	            String dataGaMenuNuevaTemporada = getDataGaLabelNuevaTemporada(dCtxSh.pais.getCodigo_pais(), sublineaType);
	            menuNuevaTemp.setDataGaLabel(dataGaMenuNuevaTemporada);
	            secMenusDesktopStpV.stepEntradaMenuDesktop(menuNuevaTemp, "");
	            pageGaleriaStpV.validaNotArticlesOfTypeDesktop(TypeArticle.rebajado, State.Defect, false);
	            //PageGaleriaStpV.validaArticlesOfTemporadas(tempArticlesNextSeason, validaNotNewArticles, StepTestMaker, dFTest);
            
	            secMenusStpV.selectMenu1rstLevelTypeCatalog(menuRebajas, dCtxSh);
	            pageGaleriaStpV.validaRebajasJun2018Desktop(salesOnInCountry, true, dCtxSh.pais, dCtxSh.idioma, linea.getType(), bloqueMenu.prendas);
	            PageGaleriaStpV.clickMoreInfoBannerRebajasJun2018(driver);
	            
	            RebajasPaisDAO rebajasDAO = new RebajasPaisDAO();
	            int maxRebajas = rebajasDAO.getMaxRebajas(dCtxSh.pais.getCodigo_pais());
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