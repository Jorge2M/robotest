package com.mng.robotest.test80.mango.test.appshop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.lang.reflect.Method;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.*;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.controlTest.*;
import com.mng.robotest.test80.arq.utils.controlTest.mango.*;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.IdiomaPais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;
import com.mng.robotest.test80.mango.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test80.mango.test.getdata.productos.ArticleStock;
import com.mng.robotest.test80.mango.test.getdata.productos.ManagerArticlesStock;
import com.mng.robotest.test80.mango.test.getdata.productos.ManagerArticlesStock.TypeArticleStock;
import com.mng.robotest.test80.mango.test.getdata.usuarios.GestorUsersShop;
import com.mng.robotest.test80.mango.test.getdata.usuarios.UserShop;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.PageFicha;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.PageFichaArtOld;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.Slider;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.PageFicha.TypeFicha;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.SecDataProduct.ProductNav;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.SecProductDescrOld.TypePanel;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.KeyMenu1rstLevel;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.Menu1rstLevel;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.MenuTreeApp;
import com.mng.robotest.test80.mango.test.pageobject.utils.DataFichaArt;
import com.mng.robotest.test80.mango.test.stpv.shop.AccesoStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.SecBolsaStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.buscador.SecBuscadorStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.ficha.PageFichaArtStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.ficha.SecModalPersonalizacionStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.galeria.LocationArticle;
import com.mng.robotest.test80.mango.test.stpv.shop.galeria.PageGaleriaStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.menus.SecMenusWrapperStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.modales.ModalBuscadorTiendasStpV;

@SuppressWarnings("javadoc")
public class FichaProducto extends GestorWebDriver {
    Pais españa = null;
    IdiomaPais castellano = null;
    String baseUrl;
    boolean acceptNextAlert = true;
    StringBuffer verificationErrors = new StringBuffer();
	    
    @BeforeMethod (groups={"FichaProducto", "Canal:all_App:all"})
    @Parameters({"brwsr-path", "urlBase", "AppEcom", "Channel"})
    public void login(String bpath, String urlAcceso, String appEcom, String channel, ITestContext context, Method method) 
    throws Exception {
        //Recopilación de parámetros
        dCtxSh = new DataCtxShop();
        dCtxSh.setAppEcom(appEcom);
        dCtxSh.setChannel(channel);
        dCtxSh.urlAcceso = urlAcceso;
        
        //Si no existe, obtenemos el país España
        if (this.españa==null) {
            Integer codEspanya = Integer.valueOf(1);
            List<Pais> listaPaises = UtilsMangoTest.listaPaisesXML(new ArrayList<>(Arrays.asList(codEspanya)));
            this.españa = UtilsMangoTest.getPaisFromCodigo("001", listaPaises);
            this.castellano = this.españa.getListIdiomas().get(0);
        }
        
        //Almacenamiento final a nivel de Thread (para disponer de 1 x cada @Test)
        this.clonePerThreadCtx();
        
        //Creamos el WebDriver con el que ejecutaremos el Test
        createDriverInThread(bpath, dCtxSh.urlAcceso, "", dCtxSh.channel, context, method);
    }
	
    @SuppressWarnings("unused")
    @AfterMethod (groups={"FichaProducto", "Canal:all_App:all"}, alwaysRun = true)
    public void logout(ITestContext context, Method method) throws Exception {
        WebDriver driver = getDriver().driver;
        super.quitWebDriver(driver, context);
    }		
	
    @Test (
        groups={"FichaProducto", "Canal:desktop_App:all"}, alwaysRun=true, 
        description="[Usuario registrado] Se testean las features principales de una ficha con origen el buscador: añadir a la bolsa, selección color/talla, buscar en tienda, añadir a favoritos")
    public void FIC001_FichaFromSearch_PrimaryFeatures_Reg(ITestContext context, Method method) throws Exception {
        DataFmwkTest dFTest = new DataFmwkTest(getDriver(), method, context);
        DataCtxShop dCtxSh = this.dCtsShThread.get();
        dCtxSh.pais=this.españa;
        dCtxSh.idioma=this.castellano;
        UserShop userShop = GestorUsersShop.checkoutBestUserForNewTestCase();
        dCtxSh.userConnected = userShop.user;
        dCtxSh.passwordUser = userShop.password;
        dCtxSh.userRegistered=true;
        
        //TestAB.activateTestABiconoBolsaDesktop(0, dCtxSh, dFTest.driver);
        AccesoStpV.accesoAplicacionEnUnPaso(dCtxSh, true/*clearArticulos*/, dFTest);
        ArticleStock articleWithColors = ManagerArticlesStock.getArticleStock(TypeArticleStock.articlesWithMoreOneColour, dCtxSh);
        SecBuscadorStpV.searchArticuloAndValidateBasic(articleWithColors, dCtxSh, dFTest);
        
        PageFichaArtStpV pageFichaStpv = new PageFichaArtStpV(dCtxSh.appE, dCtxSh.channel, dFTest);
        boolean isTallaUnica = pageFichaStpv.selectAnadirALaBolsaTallaPrevNoSelected();
        
        ArticuloScreen articulo = new ArticuloScreen(articleWithColors);
        pageFichaStpv.selectColorAndSaveData(articulo);
        pageFichaStpv.selectTallaAndSaveData(articulo);
        
        //Si es talla única -> Significa que lo dimos de alta en la bolsa cuando seleccionamos el click "Añadir a la bolsa"
        //-> Lo damos de baja
        if (isTallaUnica)
            SecBolsaStpV.clear(dCtxSh, dFTest);
        
        articulo = pageFichaStpv.getFicha().getArticuloObject();
        if (dCtxSh.appE==AppEcom.shop) { //"Buscar en Tienda" y "Favoritos" no existen en Outlet ni Votf
            pageFichaStpv.selectBuscarEnTiendaButton();
            ModalBuscadorTiendasStpV.close(dFTest);
            pageFichaStpv.selectAnadirAFavoritos();
            pageFichaStpv.selectRemoveFromFavoritos();
        }
        
        pageFichaStpv.selectAnadirALaBolsaTallaPrevSiSelected(articulo, dCtxSh);
    }

    @SuppressWarnings("static-access")
    @Test (
        groups={"FichaProducto", "Canal:desktop_App:all"}, alwaysRun=true, 
        description="[Usuario no registrado] Se testean las features secundarias de una ficha con origen el buscador: guía de tallas, carrusel imágenes, imagen central, panel de opciones")
    public void FIC002_FichaFromSearch_SecondaryFeatures_NoReg(ITestContext context, Method method) throws Exception {
        DataFmwkTest dFTest = new DataFmwkTest(getDriver(), method, context);
        DataCtxShop dCtxSh = this.dCtsShThread.get();
        dCtxSh.userRegistered = false;
        dCtxSh.pais=this.españa;
        dCtxSh.idioma=this.castellano;

        AccesoStpV.accesoAplicacionEnUnPaso(dCtxSh, false/*clearArticulos*/, dFTest);
        datosStep datosStep = SecBuscadorStpV.searchArticuloAndValidateBasic(TypeArticleStock.articlesWithTotalLook, dCtxSh, dFTest);
        
        PageFichaArtStpV pageFichaStpV = new PageFichaArtStpV(dCtxSh.appE, dCtxSh.channel, dFTest);
        if (pageFichaStpV.getFicha().getTypeFicha()==TypeFicha.Old) {
            pageFichaStpV.validaExistsImgsCarruselIzqFichaOld(datosStep);
            pageFichaStpV.secProductDescOld.validateAreInStateInitial(dCtxSh.appE, datosStep, dFTest);
            PageFicha pageFicha = PageFicha.newInstance(dCtxSh.appE, dCtxSh.channel, dFTest.driver);
            if (((PageFichaArtOld)pageFicha).getNumImgsCarruselIzq() > 2) 
                pageFichaStpV.selectImgCarruselIzqFichaOld(2/*numImagen*/);
                
            pageFichaStpV.selectImagenCentralFichaOld();
            if (TypePanel.Description.getListApps().contains(dCtxSh.appE))
                pageFichaStpV.secProductDescOld.selectPanel(TypePanel.Description, dFTest);
            
            if (TypePanel.Composition.getListApps().contains(dCtxSh.appE))
                pageFichaStpV.secProductDescOld.selectPanel(TypePanel.Composition, dFTest);
            
            if (TypePanel.Returns.getListApps().contains(dCtxSh.appE))
                pageFichaStpV.secProductDescOld.selectPanel(TypePanel.Returns, dFTest);
            
            if (TypePanel.Shipment.getListApps().contains(dCtxSh.appE))
                pageFichaStpV.secProductDescOld.selectPanel(TypePanel.Shipment, dFTest);            
        }
        else {
            boolean isFichaAccesorio = pageFichaStpV.getFicha().isFichaAccesorio(); 
            pageFichaStpV.secFotosNew.validaLayoutFotosNew(isFichaAccesorio, datosStep, dFTest);
            pageFichaStpV.secBolsaButtonAndLinksNew.selectEnvioYDevoluciones(dFTest);
            pageFichaStpV.modEnvioYdevol.clickAspaForClose(dFTest);
            pageFichaStpV.secBolsaButtonAndLinksNew.selectDetalleDelProducto(dCtxSh.appE, LineaType.she, dFTest);
            pageFichaStpV.secBolsaButtonAndLinksNew.selectLinkCompartir(dCtxSh.pais.getCodigo_pais(), dFTest);
        }
            
        datosStep = pageFichaStpV.selectGuiaDeTallas();
        if (dCtxSh.appE==AppEcom.shop)
            pageFichaStpV.validateSliderIfExists(Slider.ElegidoParaTi, datosStep);
        
        if (dCtxSh.appE!=AppEcom.outlet)
            pageFichaStpV.validateSliderIfExists(Slider.CompletaTuLook, datosStep);
    }
    
    @SuppressWarnings("static-access")
    @Test (
        groups={"FichaProducto", "Canal:desktop_App:shop"}, alwaysRun=true, 
        description="[Usuario no registrado] Desde Corea/coreano, se testea una ficha con origen la Galería validando el panel KcSafety")
    public void FIC003_FichaFromGalery_CheckKcSafety(ITestContext context, Method method) throws Exception {
        DataFmwkTest dFTest = new DataFmwkTest(getDriver(), method, context);
        DataCtxShop dCtxSh = this.dCtsShThread.get();
        dCtxSh.userRegistered = false;
        Integer codCorea = Integer.valueOf(728);
        List<Pais> listaPaises = UtilsMangoTest.listaPaisesXML(new ArrayList<>(Arrays.asList(codCorea)));
        this.castellano = this.españa.getListIdiomas().get(0);
        dCtxSh.pais = UtilsMangoTest.getPaisFromCodigo("728", listaPaises); //Corea
        dCtxSh.idioma = dCtxSh.pais.getListIdiomas().get(0); //Coreano
        
        AccesoStpV.accesoAplicacionEnUnPaso(dCtxSh, false/*clearArticulos*/, dFTest);
        Menu1rstLevel menuPantalones = MenuTreeApp.getMenuLevel1From(dCtxSh.appE, KeyMenu1rstLevel.from(LineaType.nina, null, "pantalones"));
        SecMenusWrapperStpV.selectMenu1rstLevelTypeCatalog(menuPantalones, dCtxSh, dFTest);

        PageGaleriaStpV pageGaleriaStpV = PageGaleriaStpV.getInstance(dCtxSh.channel, dCtxSh.appE, dFTest);
        LocationArticle location1rstArticle = LocationArticle.getInstanceInCatalog(1);
        DataFichaArt dataArtOrigin = pageGaleriaStpV.selectArticulo(location1rstArticle, dCtxSh).dataFichaArticulo;
        
        PageFichaArtStpV pageFichaStpV = new PageFichaArtStpV(dCtxSh.appE, dCtxSh.channel, dFTest);
        if (pageFichaStpV.getFicha().getTypeFicha()==TypeFicha.Old) {
            if (TypePanel.KcSafety.getListApps().contains(dCtxSh.appE))
                pageFichaStpV.secProductDescOld.selectPanel(TypePanel.KcSafety, dFTest);
        }
        else 
            if (TypePanel.KcSafety.getListApps().contains(dCtxSh.appE))
                pageFichaStpV.secBolsaButtonAndLinksNew.selectDetalleDelProducto(dCtxSh.appE, LineaType.nina, dFTest);
        
        pageFichaStpV.selectLinkNavigation(ProductNav.Next, dCtxSh, dataArtOrigin.getReferencia());
        pageFichaStpV.selectLinkNavigation(ProductNav.Prev, dCtxSh, dataArtOrigin.getReferencia());
    }
    
    @Test (
        groups={"FichaProducto", "Canal:desktop_App:shop", "Canal:desktop_App:outlet"}, 
        alwaysRun=true, description="[Usario no registrado] Testeo ficha con artículo con color y tallas no disponibles")
    public void FIC004_Articulo_NoStock_Noreg(ITestContext context, Method method) throws Exception {
        DataFmwkTest dFTest = new DataFmwkTest(getDriver(), method, context);
        DataCtxShop dCtxSh = this.dCtsShThread.get();
        dCtxSh.pais=this.españa;
        dCtxSh.idioma=this.castellano;
        dCtxSh.userRegistered = false;
                    
        //Step
        AccesoStpV.accesoAplicacionEnUnPaso(dCtxSh, false/*clearArticulos*/, dFTest);
        
        //Step
        ArticleStock articulo = ManagerArticlesStock.getArticleStock(TypeArticleStock.articlesWithoutStock, dCtxSh);
        SecBuscadorStpV.searchArticuloAndValidateBasic(articulo, dCtxSh, dFTest);
        
        //Step
        PageFichaArtStpV pageFichaStpV = new PageFichaArtStpV(dCtxSh.appE, dCtxSh.channel, dFTest);
        pageFichaStpV.selectColorAndTallaNoDisponible(articulo, dCtxSh.appE);
    }
    
    @Test (
        groups={"FichaProducto", "Canal:all_App:shop"}, 
        alwaysRun=true, description="[Usario no registrado] Testeo Personalización bordados")
    public void FIC005_Articulo_Personalizable_Noreg(ITestContext context, Method method) throws Exception {
        DataFmwkTest dFTest = new DataFmwkTest(getDriver(), method, context);
        DataCtxShop dCtxSh = this.dCtsShThread.get();
        dCtxSh.pais=this.españa;
        dCtxSh.idioma=this.castellano;
        dCtxSh.userRegistered = false;
        SecModalPersonalizacionStpV modalPersonalizacionStpV = new SecModalPersonalizacionStpV(dCtxSh.appE, dCtxSh.channel, dFTest);                
        
        //Step
        AccesoStpV.accesoAplicacionEnUnPaso(dCtxSh, false/*clearArticulos*/, dFTest);
        modalPersonalizacionStpV.searchForCustomization(dCtxSh.channel, dFTest, dCtxSh);
        
        //Step
        PageFichaArtStpV pageFichaStpv = new PageFichaArtStpV(dCtxSh.appE, dCtxSh.channel, dFTest);
        pageFichaStpv.selectTalla(1);
        
        //Steps propios del modal de personalizacion
 
        modalPersonalizacionStpV.startCustomizationProcces(dCtxSh.channel, dFTest);
        modalPersonalizacionStpV.customizationProcces(dCtxSh.channel, dFTest);
        modalPersonalizacionStpV.endAndCheckCustomization(dCtxSh.channel, dFTest);
    }
}