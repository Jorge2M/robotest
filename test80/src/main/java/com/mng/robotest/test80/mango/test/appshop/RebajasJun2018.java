package com.mng.robotest.test80.mango.test.appshop;

import org.testng.ITestContext;
import java.lang.reflect.Method;
import org.testng.annotations.*;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.*;
import com.mng.robotest.test80.arq.utils.controlTest.mango.*;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
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
import com.mng.robotest.test80.mango.test.stpv.shop.PageHomeMarcasStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.PagePrehomeStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.PageHomeMarcasStpV.TypeHome;
import com.mng.robotest.test80.mango.test.stpv.shop.banner.SecBannersStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.galeria.PageGaleriaStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.menus.SecMenusDesktopStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.menus.SecMenusWrapperStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.registro.PageRegistroIniStpV;

import java.util.List;

import org.openqa.selenium.WebDriver;

@SuppressWarnings("javadoc")
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
        storeInThread(dCtxSh);
        getAndStoreDataFmwk(bpath, dCtxSh.urlAcceso, this.index_fact, dCtxSh.channel, context, method);
    }
	
    @SuppressWarnings("unused")
    @AfterMethod (groups={"RebajasDic2018", "Canal:desktop_App:shop", "SupportsFactoryCountrys"}, alwaysRun = true)
    public void logout(ITestContext context, Method method) throws Exception {
        WebDriver driver = getWebDriver();
        super.quitWebDriver(driver, context);
    }	
	
    @SuppressWarnings("static-access")
    @Test (groups={"RebajasDic201", "Canal:desktop_App:shop", "SupportsFactoryCountrys"}, 
    	   description="Validaciones específicas correspondientes a las Rebajas de Diciembre-2017")
    public void REB001_RebajasDic2018(ITestContext context, Method method) throws Exception {
    	DataFmwkTest dFTest = getdFTest();
        DataCtxShop dCtxSh = getdCtxSh();
        DatosStep datosStep = null;
        int numLineasPais = dCtxSh.pais.getShoponline().getNumLineasTiendas(dCtxSh.appE);
            
        //Step&Validation
        datosStep = PagePrehomeStpV.seleccionPaisIdiomaAndEnter(dCtxSh, dFTest);
        if (numLineasPais==1)
            return;
        
        PageHomeMarcasStpV.validaRebajasJun2018(TypeHome.Multimarca, true/*areBanners*/, dCtxSh, datosStep, dFTest);
        if (dCtxSh.pais.isVentaOnline()) {
	        //Step&Validation
        	int maxBannersToLoad = 1;
        	int posBannerToTest = 1;
        	boolean applyValidations = true;
        	SecBannersStpV secBannersStpV = new SecBannersStpV(maxBannersToLoad, dFTest.driver);
	        datosStep = secBannersStpV.seleccionarBanner(posBannerToTest, applyValidations, dCtxSh.appE, dCtxSh.channel, dFTest);
	        PageGaleriaStpV pageGaleriaStpV = PageGaleriaStpV.getInstance(dCtxSh.channel, dCtxSh.appE, dFTest);
	        pageGaleriaStpV.validaRebajasJun2018Desktop(true/*isGaleriaSale*/, dCtxSh.pais, dCtxSh.idioma, LineaType.she, bloqueMenu.prendas, datosStep);
	        PageGaleriaStpV.clickMoreInfoBannerRebajasJun2018(dFTest);
	        SecMenusDesktopStpV.stepValidaCarrusels(dCtxSh.pais, LineaType.rebajas, dCtxSh.appE, dFTest);
        }
        
        //Step&Validation
        datosStep = SecMenusWrapperStpV.secMenuUser.selectRegistrate(Channel.desktop, dCtxSh, dFTest);
        PageRegistroIniStpV.validaRebajasJun2018(dCtxSh.idioma, datosStep, dFTest);
        
        //Aplicamos el test a las líneas/sublíneas
        for (Linea linea : this.lineasAprobar) {
            if (UtilsMangoTest.validarLinea(dCtxSh.pais, linea, dCtxSh.appE) &&
                linea.getType()!=LineaType.edits) {
                validaLinea(linea, null/*sublinea*/, ("banners".compareTo(linea.getContentDesk())==0), dCtxSh, dFTest);
                for (Sublinea sublinea : linea.getListSublineas())
                    validaLinea(linea, sublinea, ("banners".compareTo(sublinea.getContentDesk())==0), dCtxSh, dFTest);
            }
        }
    }
    
    private void validaLinea(Linea linea, Sublinea sublinea, boolean areBanners, DataCtxShop dCtxSh, DataFmwkTest dFTest) 
    throws Exception {
        //Obtenemos el tipo de línea/sublínea
        LineaType lineaType = linea.getType();
        SublineaNinosType sublineaType = null;
        if (sublinea!=null)
            sublineaType = sublinea.getTypeSublinea();
        
        //Selección de la línea/sublínea
        DatosStep datosStep = SecMenusWrapperStpV.seleccionLinea(lineaType, sublineaType, dCtxSh, dFTest);
        PageHomeMarcasStpV.validaRebajasJun2018(TypeHome.PortadaLinea, areBanners, dCtxSh, datosStep, dFTest);
        if (SecMenusWrap.canClickMenuArticles(dCtxSh.pais, linea, sublinea)) {
            PageGaleriaStpV pageGaleriaStpV = PageGaleriaStpV.getInstance(dCtxSh.channel, dCtxSh.appE, dFTest);
            
        	Menu1rstLevel menuPantalones = MenuTreeApp.getMenuLevel1From(dCtxSh.appE, KeyMenu1rstLevel.from(lineaType, sublineaType, "pantalones"));
            datosStep = SecMenusWrapperStpV.selectMenu1rstLevelTypeCatalog(menuPantalones, dCtxSh, dFTest);
            pageGaleriaStpV.validaRebajasJun2018Desktop(false/*isGaleriaSale*/, dCtxSh.pais, dCtxSh.idioma, linea.getType(), bloqueMenu.prendas, datosStep);
            
            Menu1rstLevel menuZapatos = MenuTreeApp.getMenuLevel1From(dCtxSh.appE, KeyMenu1rstLevel.from(lineaType, sublineaType, "zapatos"));
            datosStep = SecMenusWrapperStpV.selectMenu1rstLevelTypeCatalog(menuZapatos, dCtxSh, dFTest);
            pageGaleriaStpV.validaRebajasJun2018Desktop(false/*isGaleriaSale*/, dCtxSh.pais, dCtxSh.idioma, linea.getType(), bloqueMenu.accesorios, datosStep);
            
            //Click filtros laterales de rebajas
            SecMenusWrapperStpV.selectFiltroCollection(FilterCollection.sale, dCtxSh.channel, dCtxSh.appE, dFTest);
            SecMenusWrapperStpV.selectFiltroCollectionIfExists(FilterCollection.nextSeason, dCtxSh.channel, dCtxSh.appE, dFTest);
            
            Menu1rstLevel menuNuevaTemp = MenuTreeApp.getMenuLevel1From(dCtxSh.appE, KeyMenu1rstLevel.from(lineaType, sublineaType, "nueva temporada"));
            String dataGaMenuNuevaTemporada = getDataGaLabelNuevaTemporada(dCtxSh.pais.getCodigo_pais(), sublineaType);
            menuNuevaTemp.setDataGaLabel(dataGaMenuNuevaTemporada);

            datosStep = SecMenusDesktopStpV.stepEntradaMenuDesktop(menuNuevaTemp, "", dCtxSh.channel, dCtxSh.appE, dFTest);
            pageGaleriaStpV.validaNotArticlesOfTypeDesktop(TypeArticle.rebajado, State.Defect, datosStep);
            //PageGaleriaStpV.validaArticlesOfTemporadas(tempArticlesNextSeason, validaNotNewArticles, datosStep, dFTest);
            
            Menu1rstLevel menuRebajas = MenuTreeApp.getMenuLevel1From(dCtxSh.appE, KeyMenu1rstLevel.from(lineaType, sublineaType, "rebajas"));
            menuRebajas.setDataGaLabel("rebajas");
            datosStep = SecMenusWrapperStpV.selectMenu1rstLevelTypeCatalog(menuRebajas, dCtxSh, dFTest);
            pageGaleriaStpV.validaRebajasJun2018Desktop(true/*isGaleriaSale*/, dCtxSh.pais, dCtxSh.idioma, linea.getType(), bloqueMenu.prendas, datosStep);
            PageGaleriaStpV.clickMoreInfoBannerRebajasJun2018(dFTest);
            
            int maxRebajas = RebajasPaisDAO.getMaxRebajas(dCtxSh.pais.getCodigo_pais());
            if (maxRebajas==70) {
            	pageGaleriaStpV.validaRebajasHasta70Jun2018(dCtxSh.idioma, datosStep);
            }
        }
    }
    
    private String getDataGaLabelNuevaTemporada(String codigoPais, SublineaNinosType sublineaType) {
        if ("075".compareTo(codigoPais)==0 && sublineaType==SublineaNinosType.nino)
        	return ("nueva_coleccion");
        
        return "nueva_temporada";
    }
}