package com.mng.robotest.test80.mango.test.appshop;

import com.mng.robotest.test80.mango.test.stpv.shop.galeria.ModalArticleNotAvailableStpV;
import org.testng.ITestContext;
import org.testng.annotations.*;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.TestCaseData;
import com.mng.robotest.test80.arq.utils.controlTest.mango.*;
import com.mng.robotest.test80.arq.utils.otras.*;
import com.mng.robotest.test80.mango.test.data.Color;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.IdiomaPais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;
import com.mng.robotest.test80.mango.test.getdata.usuarios.GestorUsersShop;
import com.mng.robotest.test80.mango.test.getdata.usuarios.UserShop;
import com.mng.robotest.test80.mango.test.pageobject.shop.filtros.FilterOrdenacion;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.PageGaleria;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.PageGaleriaDesktop;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.PageGaleriaDesktop.NumColumnas;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.PageGaleriaDesktop.TypeSlider;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.KeyMenu1rstLevel;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.Menu1rstLevel;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.Menu2onLevel;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.MenuTreeApp;
import com.mng.robotest.test80.mango.test.pageobject.utils.DataScroll;
import com.mng.robotest.test80.mango.test.pageobject.utils.NombreYRefList;
import com.mng.robotest.test80.mango.test.stpv.shop.AccesoStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.AllPagesStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.SecFiltrosStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.galeria.DataForScrollStep;
import com.mng.robotest.test80.mango.test.stpv.shop.galeria.LocationArticle;
import com.mng.robotest.test80.mango.test.stpv.shop.galeria.PageGaleriaStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.menus.SecMenusDesktopStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.menus.SecMenusWrapperStpV;

import org.openqa.selenium.WebDriver;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings({"static-access"})
public class GaleriaProducto extends GestorWebDriver {
    Pais españa = null;
    IdiomaPais castellano = null;    
    String baseUrl;
    boolean acceptNextAlert = true;
    StringBuffer verificationErrors = new StringBuffer();
    int maxArtScroll = 600;
        
    public GaleriaProducto() {}         
      
    @BeforeMethod (groups={"GaleriaProducto", "Canal:all_App:all"})
    @Parameters({"brwsr-path", "urlBase", "AppEcom", "Channel"})
    public void login(String bpath, String urlAcceso, String appEcom, String channel, ITestContext context, Method method)
    throws Exception {
        //Recopilación de parámetros
        DataCtxShop dCtxSh = new DataCtxShop();
        dCtxSh.setAppEcom(appEcom);
        dCtxSh.setChannel(channel);
        dCtxSh.urlAcceso = urlAcceso;
        if (this.españa==null) {
            Integer codEspanya = Integer.valueOf(1);
            List<Pais> listaPaises = UtilsMangoTest.listaPaisesXML(new ArrayList<>(Arrays.asList(codEspanya)));
            this.españa = UtilsMangoTest.getPaisFromCodigo("001", listaPaises);
            this.castellano = this.españa.getListIdiomas().get(0);
        }
        
        dCtxSh.pais = this.españa;
        dCtxSh.idioma = this.castellano;
        
        //Almacenamiento final a nivel de Thread (para disponer de 1 x cada @Test)
        TestCaseData.storeInThread(dCtxSh); 
        TestCaseData.getAndStoreDataFmwk(bpath, dCtxSh.urlAcceso, "", dCtxSh.channel, context, method);
    }
    
    @SuppressWarnings("unused")
    @AfterMethod (groups={"GaleriaProducto", "Canal:all_App:all"}, alwaysRun = true)
    public void logout(ITestContext context, Method method) throws Exception {
        WebDriver driver = TestCaseData.getWebDriver();
        super.quitWebDriver(driver, context);
    }               
    
    @Test (
        groups={"GaleriaProducto", "Canal:movil_web_App:all"}, alwaysRun=true, 
        description="[Usuario registrado] Acceder a galería camisas. Filtros y ordenación. Seleccionar producto y color")
    public void GPO001_Galeria_Camisas() throws Exception {
    	DataFmwkTest dFTest = TestCaseData.getdFTest();
        DataCtxShop dCtxSh = TestCaseData.getdCtxSh();
        UserShop userShop = GestorUsersShop.checkoutBestUserForNewTestCase();
        dCtxSh.userConnected = userShop.user;
        dCtxSh.passwordUser = userShop.password;
        dCtxSh.userRegistered = true;
        String tipoPrendasGaleria = "camisa";
            
        AccesoStpV.accesoAplicacionEnUnPaso(dCtxSh, false/*clearArticulos*/, dFTest.driver);
        Menu1rstLevel menuCamisas = MenuTreeApp.getMenuLevel1From(dCtxSh.appE, KeyMenu1rstLevel.from(LineaType.she, null, "camisas"));
        SecMenusWrapperStpV.selectMenu1rstLevelTypeCatalog(menuCamisas, dCtxSh, dFTest.driver);
        
        List<Color> colorsToFilter = new ArrayList<>();
        colorsToFilter.add(Color.Blanco);
        colorsToFilter.add(Color.Azul);
        SecFiltrosStpV.selectFiltroColoresStep(dCtxSh.appE, dCtxSh.channel, true/*validaciones*/, "Camisas", colorsToFilter, dFTest.driver);

        //Pruebas a nivel del cambio de galería de 2<->4 columnas
        PageGaleriaStpV pageGaleriaStpV = PageGaleriaStpV.getInstance(dCtxSh.channel, dCtxSh.appE, dFTest.driver);
        if (dCtxSh.channel==Channel.desktop && dCtxSh.appE!=AppEcom.outlet) {
        	PageGaleriaDesktop pageGaleria = (PageGaleriaDesktop)PageGaleria.getInstance(Channel.desktop, dCtxSh.appE, dFTest.driver);
            NombreYRefList listArticlesGaleria2Cols = pageGaleria.getListaNombreYRefArticulos();
            listArticlesGaleria2Cols = pageGaleriaStpV.selectListadoXColumnasDesktop(NumColumnas.cuatro, listArticlesGaleria2Cols);
            pageGaleriaStpV.selectListadoXColumnasDesktop(NumColumnas.dos, listArticlesGaleria2Cols);
        }
        
        DataForScrollStep dataScroll = new DataForScrollStep();
        dataScroll.numPageToScroll = PageGaleriaDesktop.maxPageToScroll;
        dataScroll.ordenacionExpected = FilterOrdenacion.NOordenado;
        dataScroll.validateArticlesExpected = false;
        dataScroll.validaImgBroken = true;
        DataScroll datosScrollFinalGaleria = pageGaleriaStpV.scrollFromFirstPage(dataScroll, dCtxSh);
        
        if (dCtxSh.channel==Channel.movil_web)
            pageGaleriaStpV.backTo1erArticleMobilStep(dCtxSh);
            
        int numArticulosPantalla = 
        	pageGaleriaStpV.seleccionaOrdenacionGaleria(FilterOrdenacion.PrecioDesc, tipoPrendasGaleria, -1/*numArticulosValidar*/, dCtxSh);
        
        dataScroll.validateArticlesExpected = true;
        dataScroll.numArticlesExpected = datosScrollFinalGaleria.articulosTotalesPagina;
        pageGaleriaStpV.scrollFromFirstPage(dataScroll, dCtxSh);    
        
        pageGaleriaStpV.seleccionaOrdenacionGaleria(FilterOrdenacion.PrecioAsc, tipoPrendasGaleria, numArticulosPantalla, dCtxSh);
        pageGaleriaStpV.scrollFromFirstPage(dataScroll, dCtxSh);
        pageGaleriaStpV.selecColorFromArtGaleriaStep(1/*numArtConColores*/, 2/*posColor*/);
        pageGaleriaStpV.selecArticuloGaleriaStep(1/*numArtConColores*/);        
    }
    
    @Test (
        groups={"GaleriaProducto", "Canal:desktop_App:all"}, alwaysRun=true, 
        description="[Usuario no registrado][Chrome] Acceder a galería camisas. Filtro color. Scroll")
    public void GPO004_Navega_Galeria() throws Exception {
    	DataFmwkTest dFTest = TestCaseData.getdFTest();
        DataCtxShop dCtxSh = TestCaseData.getdCtxSh();
        dCtxSh.userRegistered = false;
            
        AccesoStpV.accesoAplicacionEnUnPaso(dCtxSh, false/*clearArticulos*/, dFTest.driver);
        Menu1rstLevel menuCamisas = MenuTreeApp.getMenuLevel1From(dCtxSh.appE, KeyMenu1rstLevel.from(LineaType.she, null, "camisas"));
        SecMenusWrapperStpV.selectMenu1rstLevelTypeCatalog(menuCamisas, dCtxSh, dFTest.driver);
                        
        List<Color> colorsToFilter = new ArrayList<>();
        colorsToFilter.add(Color.Blanco);
        colorsToFilter.add(Color.Negro);
        SecFiltrosStpV.selectFiltroColoresStep(dCtxSh.appE, dCtxSh.channel, false/*validaciones*/, "Camisas", colorsToFilter, dFTest.driver);
            
        //Scrollar hasta la 3a página
        PageGaleriaStpV pageGaleriaStpV = PageGaleriaStpV.getInstance(dCtxSh.channel, dCtxSh.appE, dFTest.driver);
        DataForScrollStep dataScroll = new DataForScrollStep();
        dataScroll.numPageToScroll = 3;
        dataScroll.ordenacionExpected = FilterOrdenacion.NOordenado;
        dataScroll.validateArticlesExpected = false;
        dataScroll.validaImgBroken = true;
        pageGaleriaStpV.scrollFromFirstPage(dataScroll, dCtxSh);
        LocationArticle loc1rsArticle1rstPage = LocationArticle.getInstanceInPage(2, 1);
        pageGaleriaStpV.selectArticuloEnPestanyaAndBack(loc1rsArticle1rstPage);
        
        //Scrollar hasta el final de la Galería
        dataScroll.numPageToScroll = PageGaleriaDesktop.maxPageToScroll;
        DataScroll datosScrollFinalGaleria = pageGaleriaStpV.scrollFromFirstPage(dataScroll, dCtxSh);
            
        LocationArticle loc1rsArticleLastPage = LocationArticle.getInstanceInPage(datosScrollFinalGaleria.paginaFinal, 1);
        pageGaleriaStpV.selectArticulo(loc1rsArticleLastPage, dCtxSh);
        AllPagesStpV.backNagegador(dFTest.driver);

        //Scrollar hasta el final de la Galería (comprobaremos que el número de artículos es el mismo que en el anterior scroll hasta el final)
        dataScroll.validateArticlesExpected = true;
        dataScroll.numArticlesExpected = datosScrollFinalGaleria.articulosTotalesPagina;
        dataScroll.validaImgBroken = false;
        pageGaleriaStpV.scrollFromFirstPage(dataScroll, dCtxSh);
    }
    
    @Test (
        groups={"GaleriaProducto", "Canal:desktop_App:all"}, alwaysRun=true, 
        description="[Usuario registrado] Acceder a galería. Navegación menú lateral de primer y segundo nivel. Selector de precios")
    public void GPO005_Galeria_Menu_Lateral() throws Exception {
    	DataFmwkTest dFTest = TestCaseData.getdFTest();
        DataCtxShop dCtxSh = TestCaseData.getdCtxSh();
        UserShop userShop = GestorUsersShop.checkoutBestUserForNewTestCase();
        dCtxSh.userConnected = userShop.user;
        dCtxSh.passwordUser = userShop.password;
        dCtxSh.userRegistered = true;
    
        AccesoStpV.accesoAplicacionEnUnPaso(dCtxSh, false/*clearArticulos*/, dFTest.driver);
        PageGaleriaStpV pageGaleriaStpV = PageGaleriaStpV.getInstance(dCtxSh.channel, dCtxSh.appE, dFTest.driver);
        Menu1rstLevel menuCamisas = MenuTreeApp.getMenuLevel1From(dCtxSh.appE, KeyMenu1rstLevel.from(LineaType.she, null, "camisas"));
        if (dCtxSh.appE==AppEcom.outlet)
            SecMenusWrapperStpV.selectMenu1rstLevelTypeCatalog(menuCamisas, dCtxSh, dFTest.driver);
        else {
        	Menu1rstLevel menuNuevo = MenuTreeApp.getMenuLevel1From(dCtxSh.appE, KeyMenu1rstLevel.from(LineaType.she, null, "New Now"));
            SecMenusWrapperStpV.selectMenu1rstLevelTypeCatalog(menuNuevo, dCtxSh, dFTest.driver);
            PageGaleriaStpV.secCrossSelling.validaIsCorrect(LineaType.she, dCtxSh.appE, dFTest);
            pageGaleriaStpV.hayPanoramicasEnGaleriaDesktop(Constantes.PORC_PANORAMICAS);
        }
        
        PageGaleriaStpV.secSelectorPrecios.seleccionaIntervalo(dCtxSh.appE, dFTest.driver);
        if (dCtxSh.appE!=AppEcom.outlet) {
            PageGaleriaStpV.secCrossSelling.validaIsCorrect(LineaType.she, dCtxSh.appE, dFTest);
            pageGaleriaStpV.hayPanoramicasEnGaleriaDesktop(Constantes.PORC_PANORAMICAS);
        }    
                
        Menu1rstLevel menuVestidos = MenuTreeApp.getMenuLevel1From(dCtxSh.appE, KeyMenu1rstLevel.from(LineaType.she, null, "vestidos"));
        SecMenusWrapperStpV.selectMenu1rstLevelTypeCatalog(menuVestidos, dCtxSh, dFTest.driver);
        
        SecMenusWrapperStpV.selectMenuLateral1erLevelTypeCatalog(menuCamisas, dCtxSh, dFTest.driver);
        Menu2onLevel menuCamisasTops = MenuTreeApp.getMenuLevel2From(menuCamisas, "tops");
        SecMenusDesktopStpV.selectMenuLateral2oLevel(menuCamisasTops, dCtxSh, dFTest.driver);
    }
    
    @Test (
        groups={"GaleriaProducto", "Canal:desktop_App:all"}, alwaysRun=true, 
        description="Acceder a galería y testear el slider. Testeamos secuencias de sliders en ambas direcciones y " + 
    				"finalmente las combinamos con cambios de color")
    public void GPO006_SliderInDesktop() throws Exception {
    	DataFmwkTest dFTest = TestCaseData.getdFTest();
        DataCtxShop dCtxSh = TestCaseData.getdCtxSh();
        dCtxSh.userRegistered = false;
    
        //Ini script
        AccesoStpV.accesoAplicacionEnUnPaso(dCtxSh, false/*clearArticulos*/, dFTest.driver);
        Menu1rstLevel menuCamisas = MenuTreeApp.getMenuLevel1From(dCtxSh.appE, KeyMenu1rstLevel.from(LineaType.she, null, "vestidos"));
        SecMenusWrapperStpV.selectMenu1rstLevelTypeCatalog(menuCamisas, dCtxSh, dFTest.driver);
                
        PageGaleriaStpV pageGaleriaStpV = PageGaleriaStpV.getInstance(dCtxSh.channel, dCtxSh.appE, dFTest.driver);
        ArrayList<TypeSlider> typeSliderList = new ArrayList<>();
        typeSliderList.add(TypeSlider.next);
        String src2onImage = pageGaleriaStpV.clicksSliderArticuloConColores(1/*numArtConColores*/, typeSliderList);
        
        typeSliderList.clear();
        typeSliderList.add(TypeSlider.prev);
        int numArtConColores = 1;
        pageGaleriaStpV.clicksSliderArticuloConColores(numArtConColores, typeSliderList);
        
        typeSliderList.clear();
        typeSliderList.add(TypeSlider.next);
        pageGaleriaStpV.clicksSliderArticuloConColores(numArtConColores, typeSliderList, src2onImage);
        
        //Seleccionar el 2o color del artículo
        String srcImgAfterClickColor = pageGaleriaStpV.selecColorFromArtGaleriaStep(numArtConColores, 2/*posColor*/);
        
        typeSliderList.clear();
        typeSliderList.add(TypeSlider.next);
        typeSliderList.add(TypeSlider.next);
        pageGaleriaStpV.clicksSliderArticuloConColores(numArtConColores, typeSliderList);
        
        typeSliderList.clear();
        typeSliderList.add(TypeSlider.prev);
        typeSliderList.add(TypeSlider.prev);
        pageGaleriaStpV.clicksSliderArticuloConColores(numArtConColores, typeSliderList, srcImgAfterClickColor);
    }
    @Test (
            groups={"GaleriaProducto", "Canal:desktop_App:shop"}, alwaysRun=true,
            description="[Usuario registrado] Acceder a galería camisas. Forzar caso avisame en listado")
    public void GPO007_Galeria_Camisas() throws Exception {
        DataFmwkTest dFTest = TestCaseData.getdFTest();
        DataCtxShop dCtxSh = TestCaseData.getdCtxSh();
        UserShop userShop = GestorUsersShop.checkoutBestUserForNewTestCase();
        dCtxSh.userConnected = userShop.user;
        dCtxSh.passwordUser = userShop.password;
        dCtxSh.userRegistered = true;
        String tipoPrendasGaleria = "camisa";

        // Abrir listado de mujer camisas
        AccesoStpV.accesoAplicacionEnUnPaso(dCtxSh, false/*clearArticulos*/, dFTest.driver);
        Menu1rstLevel menuCamisas = MenuTreeApp.getMenuLevel1From(dCtxSh.appE, KeyMenu1rstLevel.from(LineaType.she, null, "camisas"));
        SecMenusWrapperStpV.selectMenu1rstLevelTypeCatalog(menuCamisas, dCtxSh, dFTest.driver);

        // Abrir avisame desde el listado buscando primera talla sin stock y comprobar que se abierto y que contiene texto RGPD
        PageGaleriaStpV pageGaleriaStpV = PageGaleriaStpV.getInstance(dCtxSh.channel, dCtxSh.appE, dFTest.driver);
        pageGaleriaStpV.selectTallaNoDisponibleArticuloDesktop();
        ModalArticleNotAvailableStpV modalArticleNotAvailableStpV = ModalArticleNotAvailableStpV.getInstance(dCtxSh.channel, dCtxSh.appE, dFTest.driver);
        modalArticleNotAvailableStpV.checkVisibleAvisame();

    }
}