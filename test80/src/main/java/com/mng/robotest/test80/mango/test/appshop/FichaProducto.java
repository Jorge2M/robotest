package com.mng.robotest.test80.mango.test.appshop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.lang.reflect.Method;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.*;

import com.mng.robotest.test80.arq.access.InputParamsTestMaker;
import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.TestCaseData;
import com.mng.robotest.test80.arq.utils.controlTest.mango.*;
import com.mng.robotest.test80.mango.test.data.Constantes;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.conftestmaker.Utils;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.factoryes.Utilidades;
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
import com.mng.robotest.test80.mango.test.pageobject.shop.filtros.FilterCollection;
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

public class FichaProducto extends GestorWebDriver {
    Pais españa = null;
    IdiomaPais castellano = null;
    String baseUrl;
    boolean acceptNextAlert = true;
    StringBuffer verificationErrors = new StringBuffer();
	    
    @BeforeMethod (groups={"FichaProducto", "Canal:all_App:all"})
    public void login(ITestContext context, Method method) 
    throws Exception {
        InputParamsTestMaker inputData = TestCaseData.getInputDataTestMaker(context);
        DataCtxShop dCtxSh = new DataCtxShop();
        dCtxSh.setAppEcom((AppEcom)inputData.getApp());
        dCtxSh.setChannel(inputData.getChannel());
        dCtxSh.urlAcceso = inputData.getUrlBase();
        
        if (this.españa==null) {
            Integer codEspanya = Integer.valueOf(1);
            List<Pais> listaPaises = Utilidades.getListCountrysFiltered(new ArrayList<>(Arrays.asList(codEspanya)));
            this.españa = UtilsMangoTest.getPaisFromCodigo("001", listaPaises);
            this.castellano = this.españa.getListIdiomas().get(0);
        }
        
        Utils.storeDataShopForTestMaker(inputData.getTypeWebDriver(), "", dCtxSh, context, method);
    }
	
    @SuppressWarnings("unused")
    @AfterMethod (groups={"FichaProducto", "Canal:all_App:all"}, alwaysRun = true)
    public void logout(ITestContext context, Method method) throws Exception {
        WebDriver driver = TestCaseData.getWebDriver();
        super.quitWebDriver(driver, context);
    }		
	
    @Test (
        groups={"FichaProducto", "Canal:desktop_App:all"}, alwaysRun=true, 
        description="[Usuario registrado] Se testean las features principales de una ficha con origen el buscador: añadir a la bolsa, selección color/talla, buscar en tienda, añadir a favoritos")
    public void FIC001_FichaFromSearch_PrimaryFeatures_Reg() throws Exception {
    	DataFmwkTest dFTest = TestCaseData.getdFTest();
        DataCtxShop dCtxSh = (DataCtxShop)TestCaseData.getData(Constantes.idCtxSh);
        dCtxSh.pais=this.españa;
        dCtxSh.idioma=this.castellano;
        UserShop userShop = GestorUsersShop.checkoutBestUserForNewTestCase();
        dCtxSh.userConnected = userShop.user;
        dCtxSh.passwordUser = userShop.password;
        dCtxSh.userRegistered=true;
        
        AccesoStpV.accesoAplicacionEnUnPaso(dCtxSh, true, dFTest.driver);
        ArticleStock articleWithColors = ManagerArticlesStock.getArticleStock(TypeArticleStock.articlesWithMoreOneColour, dCtxSh);
        SecBuscadorStpV.searchArticuloAndValidateBasic(articleWithColors, dCtxSh, dFTest.driver);
        
        PageFichaArtStpV pageFichaStpv = new PageFichaArtStpV(dCtxSh.appE, dCtxSh.channel);
        boolean isTallaUnica = pageFichaStpv.selectAnadirALaBolsaTallaPrevNoSelected();
        
        ArticuloScreen articulo = new ArticuloScreen(articleWithColors);
        pageFichaStpv.selectColorAndSaveData(articulo);
        pageFichaStpv.selectTallaAndSaveData(articulo);
        
        //Si es talla única -> Significa que lo dimos de alta en la bolsa cuando seleccionamos el click "Añadir a la bolsa"
        //-> Lo damos de baja
        if (isTallaUnica) {
            SecBolsaStpV.clear(dCtxSh, dFTest.driver);
        }
        
        articulo = pageFichaStpv.getFicha().getArticuloObject();
        if (dCtxSh.appE==AppEcom.shop) { //"Buscar en Tienda" y "Favoritos" no existen en Outlet ni Votf
            pageFichaStpv.selectBuscarEnTiendaButton();
            ModalBuscadorTiendasStpV.close(dFTest.driver);
            pageFichaStpv.selectAnadirAFavoritos();
            pageFichaStpv.changeColorGarment();
            pageFichaStpv.selectRemoveFromFavoritos();
        }
        
        pageFichaStpv.selectAnadirALaBolsaTallaPrevSiSelected(articulo, dCtxSh);
    }

    @SuppressWarnings("static-access")
    @Test (
        groups={"FichaProducto", "Canal:desktop_App:all"}, alwaysRun=true, 
        description="[Usuario no registrado] Se testean las features secundarias de una ficha con origen el buscador: guía de tallas, carrusel imágenes, imagen central, panel de opciones")
    public void FIC002_FichaFromSearch_SecondaryFeatures_NoReg() throws Exception {
    	DataFmwkTest dFTest = TestCaseData.getdFTest();
        DataCtxShop dCtxSh = (DataCtxShop)TestCaseData.getData(Constantes.idCtxSh);
        dCtxSh.userRegistered = false;
        dCtxSh.pais=this.españa;
        dCtxSh.idioma=this.castellano;

        AccesoStpV.accesoAplicacionEnUnPaso(dCtxSh, false, dFTest.driver);
        SecBuscadorStpV.searchArticuloAndValidateBasic(TypeArticleStock.articlesWithTotalLook, dCtxSh, dFTest.driver);
        
        PageFichaArtStpV pageFichaStpV = new PageFichaArtStpV(dCtxSh.appE, dCtxSh.channel);
        if (pageFichaStpV.getFicha().getTypeFicha()==TypeFicha.Old) {
            pageFichaStpV.validaExistsImgsCarruselIzqFichaOld();
            pageFichaStpV.secProductDescOld.validateAreInStateInitial(dCtxSh.appE, dFTest.driver);
            PageFicha pageFicha = PageFicha.newInstance(dCtxSh.channel, dCtxSh.appE, dFTest.driver);
            if (((PageFichaArtOld)pageFicha).getNumImgsCarruselIzq() > 2) {
                pageFichaStpV.selectImgCarruselIzqFichaOld(2);
            }
            pageFichaStpV.selectImagenCentralFichaOld();
            if (TypePanel.Description.getListApps().contains(dCtxSh.appE)) {
                pageFichaStpV.secProductDescOld.selectPanel(TypePanel.Description, dFTest.driver);
            }
            if (TypePanel.Composition.getListApps().contains(dCtxSh.appE)) {
                pageFichaStpV.secProductDescOld.selectPanel(TypePanel.Composition, dFTest.driver);
            }
            if (TypePanel.Returns.getListApps().contains(dCtxSh.appE)) {
                pageFichaStpV.secProductDescOld.selectPanel(TypePanel.Returns, dFTest.driver);
            }
            if (TypePanel.Shipment.getListApps().contains(dCtxSh.appE)) {
                pageFichaStpV.secProductDescOld.selectPanel(TypePanel.Shipment, dFTest.driver);  
            }
        } else {
            boolean isFichaAccesorio = pageFichaStpV.getFicha().isFichaAccesorio(); 
            pageFichaStpV.secFotosNew.validaLayoutFotosNew(isFichaAccesorio, dFTest.driver);
            pageFichaStpV.secBolsaButtonAndLinksNew.selectEnvioYDevoluciones(dFTest.driver);
            pageFichaStpV.modEnvioYdevol.clickAspaForClose(dFTest.driver);
            pageFichaStpV.secBolsaButtonAndLinksNew.selectDetalleDelProducto(dCtxSh.appE, LineaType.she, dFTest.driver);
            pageFichaStpV.secBolsaButtonAndLinksNew.selectLinkCompartir(dCtxSh.pais.getCodigo_pais(), dFTest.driver);
        }
            
        pageFichaStpV.selectGuiaDeTallas(dCtxSh.appE);
        if (dCtxSh.appE==AppEcom.shop) {
            pageFichaStpV.validateSliderIfExists(Slider.ElegidoParaTi);
        }
        
        if (dCtxSh.appE!=AppEcom.outlet) {
            pageFichaStpV.validateSliderIfExists(Slider.CompletaTuLook);
        }
    }
    
    @SuppressWarnings("static-access")
    @Test (
        groups={"FichaProducto", "Canal:desktop_App:shop"}, alwaysRun=true, 
        description="[Usuario no registrado] Desde Corea/coreano, se testea una ficha con origen la Galería validando el panel KcSafety")
    public void FIC003_FichaFromGalery_CheckKcSafety() throws Exception {
    	DataFmwkTest dFTest = TestCaseData.getdFTest();
        DataCtxShop dCtxSh = (DataCtxShop)TestCaseData.getData(Constantes.idCtxSh);
        dCtxSh.userRegistered = false;
        Integer codCorea = Integer.valueOf(728);
        List<Pais> listaPaises = Utilidades.getListCountrysFiltered(new ArrayList<>(Arrays.asList(codCorea)));
        this.castellano = this.españa.getListIdiomas().get(0);
        dCtxSh.pais = UtilsMangoTest.getPaisFromCodigo("728", listaPaises); //Corea
        dCtxSh.idioma = dCtxSh.pais.getListIdiomas().get(0); //Coreano
        
        AccesoStpV.accesoAplicacionEnUnPaso(dCtxSh, false/*clearArticulos*/, dFTest.driver);
        Menu1rstLevel menuPantalones = MenuTreeApp.getMenuLevel1From(dCtxSh.appE, KeyMenu1rstLevel.from(LineaType.nina, null, "pantalones"));
        SecMenusWrapperStpV secMenusStpV = SecMenusWrapperStpV.getNew(dCtxSh, dFTest.driver);
        secMenusStpV.selectMenu1rstLevelTypeCatalog(menuPantalones, dCtxSh);

        PageGaleriaStpV pageGaleriaStpV = PageGaleriaStpV.getInstance(dCtxSh.channel, dCtxSh.appE, dFTest.driver);
        LocationArticle location1rstArticle = LocationArticle.getInstanceInCatalog(1);
        DataFichaArt dataArtOrigin = pageGaleriaStpV.selectArticulo(location1rstArticle, dCtxSh);
        
        PageFichaArtStpV pageFichaStpV = new PageFichaArtStpV(dCtxSh.appE, dCtxSh.channel);
        if (pageFichaStpV.getFicha().getTypeFicha()==TypeFicha.Old) {
            if (TypePanel.KcSafety.getListApps().contains(dCtxSh.appE)) {
                pageFichaStpV.secProductDescOld.selectPanel(TypePanel.KcSafety, dFTest.driver);
            }
        } else {
            if (TypePanel.KcSafety.getListApps().contains(dCtxSh.appE)) {
                pageFichaStpV.secBolsaButtonAndLinksNew.selectDetalleDelProducto(dCtxSh.appE, LineaType.nina, dFTest.driver);
            }
        }
        
        pageFichaStpV.selectLinkNavigation(ProductNav.Next, dCtxSh, dataArtOrigin.getReferencia());
        pageFichaStpV.selectLinkNavigation(ProductNav.Prev, dCtxSh, dataArtOrigin.getReferencia());
    }
    
    @Test (
        groups={"FichaProducto", "Canal:desktop_App:shop,outlet"}, 
        alwaysRun=true, description="[Usario no registrado] Testeo ficha con artículo con color y tallas no disponibles")
    public void FIC004_Articulo_NoStock_Noreg() throws Exception {
    	DataFmwkTest dFTest = TestCaseData.getdFTest();
        DataCtxShop dCtxSh = (DataCtxShop)TestCaseData.getData(Constantes.idCtxSh);
        dCtxSh.pais=this.españa;
        dCtxSh.idioma=this.castellano;
        dCtxSh.userRegistered = false;
                    
        AccesoStpV.accesoAplicacionEnUnPaso(dCtxSh, false, dFTest.driver);
        ArticleStock articulo = ManagerArticlesStock.getArticleStock(TypeArticleStock.articlesWithoutStock, dCtxSh);
        SecBuscadorStpV.searchArticuloAndValidateBasic(articulo, dCtxSh, dFTest.driver);
        PageFichaArtStpV pageFichaStpV = new PageFichaArtStpV(dCtxSh.appE, dCtxSh.channel);
        pageFichaStpV.selectColorAndTallaNoDisponible(articulo);
    }
    
    @Test (
        groups={"FichaProducto", "Canal:all_App:shop"}, 
        alwaysRun=true, description="[Usario no registrado] Testeo Personalización bordados")
    public void FIC005_Articulo_Personalizable_Noreg() throws Exception {
    	DataFmwkTest dFTest = TestCaseData.getdFTest();
        DataCtxShop dCtxSh = (DataCtxShop)TestCaseData.getData(Constantes.idCtxSh);
        dCtxSh.pais=this.españa;
        dCtxSh.idioma=this.castellano;
        dCtxSh.userRegistered = false;
        AccesoStpV.accesoAplicacionEnUnPaso(dCtxSh, false, dFTest.driver);
        
		PageGaleriaStpV pageGaleriaStpV = PageGaleriaStpV.getInstance(dCtxSh.channel, dCtxSh.appE, dFTest.driver);
		Menu1rstLevel menuPersonalizacion = MenuTreeApp.getMenuLevel1From(dCtxSh.appE, KeyMenu1rstLevel.from(LineaType.he, null, "personalizacion"));
        SecMenusWrapperStpV secMenusStpV = SecMenusWrapperStpV.getNew(dCtxSh, dFTest.driver);
        secMenusStpV.selectMenu1rstLevelTypeCatalog(menuPersonalizacion, dCtxSh);
        secMenusStpV.selectFiltroCollectionIfExists(FilterCollection.nextSeason);
		LocationArticle articleNum = LocationArticle.getInstanceInCatalog(1);
		pageGaleriaStpV.selectArticulo(articleNum, dCtxSh);
        SecModalPersonalizacionStpV modalPersonalizacionStpV = SecModalPersonalizacionStpV.getNewOne(dCtxSh, dFTest.driver); 
        modalPersonalizacionStpV.checkAreArticleCustomizable();
        
        PageFichaArtStpV pageFichaStpv = new PageFichaArtStpV(dCtxSh.appE, dCtxSh.channel);
        pageFichaStpv.selectFirstTallaAvailable();
        modalPersonalizacionStpV.selectLinkPersonalizacion();
        modalPersonalizacionStpV.startCustomization();
        modalPersonalizacionStpV.selectIconCustomization();
        modalPersonalizacionStpV.selectFirstIcon();

    	modalPersonalizacionStpV.validateIconSelectedDesktop();
        modalPersonalizacionStpV.selectConfirmarButton();
        modalPersonalizacionStpV.validateCabeceraStep(2);
        modalPersonalizacionStpV.validateWhereDesktop();
        modalPersonalizacionStpV.selectConfirmarButton();
    	modalPersonalizacionStpV.validateCabeceraStep(3);
    	modalPersonalizacionStpV.validateSelectionColor();
 
        modalPersonalizacionStpV.selectSize();
        modalPersonalizacionStpV.confirmCustomization();
        modalPersonalizacionStpV.checkCustomizationProof();
    }
}