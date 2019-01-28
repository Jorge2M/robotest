package com.mng.robotest.test80.mango.test.appshop;

import org.testng.ITestContext;
import org.testng.annotations.*;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.controlTest.*;
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

@SuppressWarnings({"javadoc","static-access"})
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
        dCtxSh = new DataCtxShop();
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
        this.clonePerThreadCtx();    
        
        //Creamos el WebDriver con el que ejecutaremos el Test
        createDriverInThread(bpath, dCtxSh.urlAcceso, "", dCtxSh.channel, context, method);
    }
    
    @SuppressWarnings("unused")
    @AfterMethod (groups={"GaleriaProducto", "Canal:all_App:all"}, alwaysRun = true)
    public void logout(ITestContext context, Method method) throws Exception {
        WebDriver driver = getDriver().driver;
        super.quitWebDriver(driver, context);
    }               
    
    @Test (
        groups={"GaleriaProducto", "Canal:movil_web_App:all"}, alwaysRun=true, 
        description="[Usuario registrado] Acceder a galería camisas. Filtros y ordenación. Seleccionar producto y color")
    public void GPO001_Galeria_Camisas(ITestContext context, Method method) throws Exception {
        DataFmwkTest dFTest = new DataFmwkTest(getDriver(), method, context);
        DataCtxShop dCtxSh = this.dCtsShThread.get();
        UserShop userShop = GestorUsersShop.checkoutBestUserForNewTestCase();
        dCtxSh.userConnected = userShop.user;
        dCtxSh.passwordUser = userShop.password;
        dCtxSh.userRegistered = true;
        String tipoPrendasGaleria = "camisa";
            
        AccesoStpV.accesoAplicacionEnUnPaso(dCtxSh, false/*clearArticulos*/, dFTest);
        Menu1rstLevel menuCamisas = MenuTreeApp.getMenuLevel1From(dCtxSh.appE, KeyMenu1rstLevel.from(LineaType.she, null, "camisas"));
        SecMenusWrapperStpV.selectMenu1rstLevelTypeCatalog(menuCamisas, dCtxSh, dFTest);
        
        List<Color> colorsToFilter = new ArrayList<>();
        colorsToFilter.add(Color.Blanco);
        colorsToFilter.add(Color.Azul);
        SecFiltrosStpV.selectFiltroColoresStep(dCtxSh.appE, dCtxSh.channel, true/*validaciones*/, "Camisas", colorsToFilter, dFTest);

        //Pruebas a nivel del cambio de galería de 2<->4 columnas
        PageGaleriaStpV pageGaleriaStpV = PageGaleriaStpV.getInstance(dCtxSh.channel, dCtxSh.appE, dFTest);
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
    public void GPO004_Navega_Galeria(ITestContext context, Method method) throws Exception {
        DataFmwkTest dFTest = new DataFmwkTest(getDriver(), method, context);
        DataCtxShop dCtxSh = this.dCtsShThread.get();
        dCtxSh.userRegistered = false;
            
        AccesoStpV.accesoAplicacionEnUnPaso(dCtxSh, false/*clearArticulos*/, dFTest);
        Menu1rstLevel menuCamisas = MenuTreeApp.getMenuLevel1From(dCtxSh.appE, KeyMenu1rstLevel.from(LineaType.she, null, "camisas"));
        SecMenusWrapperStpV.selectMenu1rstLevelTypeCatalog(menuCamisas, dCtxSh, dFTest);
                        
        List<Color> colorsToFilter = new ArrayList<>();
        colorsToFilter.add(Color.Blanco);
        colorsToFilter.add(Color.Negro);
        SecFiltrosStpV.selectFiltroColoresStep(dCtxSh.appE, dCtxSh.channel, false/*validaciones*/, "Camisas", colorsToFilter, dFTest);
            
        //Scrollar hasta la 3a página
        PageGaleriaStpV pageGaleriaStpV = PageGaleriaStpV.getInstance(dCtxSh.channel, dCtxSh.appE, dFTest);
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
        AllPagesStpV.backNagegador(dFTest);

        //Scrollar hasta el final de la Galería (comprobaremos que el número de artículos es el mismo que en el anterior scroll hasta el final)
        dataScroll.validateArticlesExpected = true;
        dataScroll.numArticlesExpected = datosScrollFinalGaleria.articulosTotalesPagina;
        dataScroll.validaImgBroken = false;
        pageGaleriaStpV.scrollFromFirstPage(dataScroll, dCtxSh);
    }
    
    @Test (
        groups={"GaleriaProducto", "Canal:desktop_App:all"}, alwaysRun=true, 
        description="[Usuario registrado] Acceder a galería. Navegación menú lateral de primer y segundo nivel. Selector de precios")
    public void GPO005_Galeria_Menu_Lateral(ITestContext context, Method method) throws Exception {
        DataFmwkTest dFTest = new DataFmwkTest(getDriver(), method, context);
        DataCtxShop dCtxSh = this.dCtsShThread.get();
        UserShop userShop = GestorUsersShop.checkoutBestUserForNewTestCase();
        dCtxSh.userConnected = userShop.user;
        dCtxSh.passwordUser = userShop.password;
        dCtxSh.userRegistered = true;
        DatosStep datosStep = null;
    
        AccesoStpV.accesoAplicacionEnUnPaso(dCtxSh, false/*clearArticulos*/, dFTest);
        PageGaleriaStpV pageGaleriaStpV = PageGaleriaStpV.getInstance(dCtxSh.channel, dCtxSh.appE, dFTest);
        Menu1rstLevel menuCamisas = MenuTreeApp.getMenuLevel1From(dCtxSh.appE, KeyMenu1rstLevel.from(LineaType.she, null, "camisas"));
        if (dCtxSh.appE==AppEcom.outlet)
            datosStep = SecMenusWrapperStpV.selectMenu1rstLevelTypeCatalog(menuCamisas, dCtxSh, dFTest);
        else {
        	Menu1rstLevel menuNuevo = MenuTreeApp.getMenuLevel1From(dCtxSh.appE, KeyMenu1rstLevel.from(LineaType.she, null, "New Now"));
            datosStep = SecMenusWrapperStpV.selectMenu1rstLevelTypeCatalog(menuNuevo, dCtxSh, dFTest);
            PageGaleriaStpV.secCrossSelling.validaIsCorrect(LineaType.she, dCtxSh.appE, datosStep, dFTest);
            pageGaleriaStpV.hayPanoramicasEnGaleriaDesktop(Constantes.PORC_PANORAMICAS, datosStep);
        }
        
        datosStep = PageGaleriaStpV.secSelectorPrecios.seleccionaIntervalo(dCtxSh.appE, dFTest);
        if (dCtxSh.appE!=AppEcom.outlet) {
            PageGaleriaStpV.secCrossSelling.validaIsCorrect(LineaType.she, dCtxSh.appE, datosStep, dFTest);
            pageGaleriaStpV.hayPanoramicasEnGaleriaDesktop(Constantes.PORC_PANORAMICAS, datosStep);
        }    
                
        Menu1rstLevel menuVestidos = MenuTreeApp.getMenuLevel1From(dCtxSh.appE, KeyMenu1rstLevel.from(LineaType.she, null, "vestidos"));
        datosStep = SecMenusWrapperStpV.selectMenu1rstLevelTypeCatalog(menuVestidos, dCtxSh, dFTest);
        
        SecMenusWrapperStpV.selectMenuLateral1erLevelTypeCatalog(menuCamisas, dCtxSh, dFTest);
        Menu2onLevel menuCamisasTops = MenuTreeApp.getMenuLevel2From(menuCamisas, "tops");
        SecMenusDesktopStpV.selectMenuLateral2oLevel(menuCamisasTops, dCtxSh, dFTest);
    }
    
    @Test (
        groups={"GaleriaProducto", "Canal:desktop_App:all"}, alwaysRun=true, 
        description="Acceder a galería y testear el slider. Testeamos secuencias de sliders en ambas direcciones y " + 
    				"finalmente las combinamos con cambios de color")
    public void GPO006_SliderInDesktop(ITestContext context, Method method) throws Exception {
        DataFmwkTest dFTest = new DataFmwkTest(getDriver(), method, context);
        DataCtxShop dCtxSh = this.dCtsShThread.get();
        dCtxSh.userRegistered = false;
    
        //Ini script
        AccesoStpV.accesoAplicacionEnUnPaso(dCtxSh, false/*clearArticulos*/, dFTest);
        Menu1rstLevel menuCamisas = MenuTreeApp.getMenuLevel1From(dCtxSh.appE, KeyMenu1rstLevel.from(LineaType.she, null, "vestidos"));
        SecMenusWrapperStpV.selectMenu1rstLevelTypeCatalog(menuCamisas, dCtxSh, dFTest);
                
        PageGaleriaStpV pageGaleriaStpV = PageGaleriaStpV.getInstance(dCtxSh.channel, dCtxSh.appE, dFTest);
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
}