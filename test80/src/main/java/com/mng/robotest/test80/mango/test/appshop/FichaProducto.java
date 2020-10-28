package com.mng.robotest.test80.mango.test.appshop;

import java.lang.reflect.Method;

import org.openqa.selenium.WebDriver;
import org.testng.ITestNGMethod;
import org.testng.annotations.*;

import com.mng.robotest.test80.access.InputParamsMango;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.data.PaisShop;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.IdiomaPais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Sublinea.SublineaNinosType;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test80.mango.test.getdata.products.GetterProducts;
import com.mng.robotest.test80.mango.test.getdata.products.data.Garment;
import com.mng.robotest.test80.mango.test.getdata.usuarios.GestorUsersShop;
import com.mng.robotest.test80.mango.test.getdata.usuarios.UserShop;
import com.mng.robotest.test80.mango.test.pageobject.shop.PagePrehome;
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
import com.mng.robotest.test80.mango.test.utils.PaisGetter;
import com.github.jorge2m.testmaker.domain.SuitesExecuted;
import com.github.jorge2m.testmaker.domain.suitetree.SuiteTM;
import com.github.jorge2m.testmaker.domain.suitetree.TestRunTM;
import com.github.jorge2m.testmaker.service.TestMaker;

public class FichaProducto {
	
	private final static Pais españa = PaisGetter.get(PaisShop.España);
	private final static IdiomaPais castellano = españa.getListIdiomas().get(0);

	private DataCtxShop getCtxShForTest() throws Exception {
		InputParamsMango inputParamsSuite = (InputParamsMango)TestMaker.getTestCase().getInputParamsSuite();
		DataCtxShop dCtxSh = new DataCtxShop();
		dCtxSh.setAppEcom((AppEcom)inputParamsSuite.getApp());
		dCtxSh.setChannel(inputParamsSuite.getChannel());
		//dCtxSh.urlAcceso = inputParamsSuite.getUrlBase();
		return dCtxSh;
	}
	
	@BeforeMethod (groups={"FichaProducto", "Canal:desktop_App:all"})
	public void before(Method method) {
		TestRunTM testRun = getTestRun(method);
		InputParamsMango inputParamsSuite = (InputParamsMango)testRun.getSuiteParent().getInputParams();
		System.out.println("Before Method. Test executing in remote: " + inputParamsSuite.isTestExecutingInRemote());
	}
	
	@AfterMethod (groups={"FichaProducto", "Canal:desktop_App:all"})
	public void after(Method method) {
		TestRunTM testRun = getTestRun(method);
		InputParamsMango inputParamsSuite = (InputParamsMango)testRun.getSuiteParent().getInputParams();
		System.out.println("After Method. Test executing in remote: " + inputParamsSuite.isTestExecutingInRemote());
	}
	
	private static TestRunTM getTestRun(Method method) {
		for (SuiteTM suite : SuitesExecuted.getSuitesExecuted()) {
			for (TestRunTM testRun : suite.getListTestRuns()) {
				for (ITestNGMethod testMethod : testRun.getTestNgContext().getAllTestMethods()) {
					if (testMethod.getConstructorOrMethod().getMethod()==method) {
						return testRun;
					}
				}
			}
		}
		return null;
	}
	
	@Test (
		groups={"FichaProducto", "Canal:desktop_App:all"}, alwaysRun=true, 
		description="[Usuario registrado] Se testean las features principales de una ficha con origen el buscador: añadir a la bolsa, selección color/talla, buscar en tienda, añadir a favoritos")
	public void FIC001_FichaFromSearch_PrimaryFeatures_Reg() throws Exception {
		WebDriver driver = TestMaker.getDriverTestCase();
		DataCtxShop dCtxSh = getCtxShForTest();
		dCtxSh.pais=españa;
		dCtxSh.idioma=castellano;
		UserShop userShop = GestorUsersShop.checkoutBestUserForNewTestCase();
		dCtxSh.userConnected = userShop.user;
		dCtxSh.passwordUser = userShop.password;
		dCtxSh.userRegistered=true;

		AccesoStpV.accesoAplicacionEnUnPaso(dCtxSh, true, driver);

		GetterProducts getterProducts = new GetterProducts.Builder(dCtxSh, driver).build();
		Garment articleWithColors = getterProducts.getWithManyColors().get(0);
		SecBuscadorStpV.searchArticulo(articleWithColors, dCtxSh, driver);

		PageFichaArtStpV pageFichaStpv = new PageFichaArtStpV(dCtxSh.appE, dCtxSh.channel);
		boolean isTallaUnica = pageFichaStpv.selectAnadirALaBolsaTallaPrevNoSelected();

		ArticuloScreen articulo = new ArticuloScreen(articleWithColors);
		pageFichaStpv.selectColorAndSaveData(articulo);
		pageFichaStpv.selectTallaAndSaveData(articulo);

        //Si es talla única -> Significa que lo dimos de alta en la bolsa cuando seleccionamos el click "Añadir a la bolsa"
        //-> Lo damos de baja
        if (isTallaUnica) {
            SecBolsaStpV.clear(dCtxSh, driver);
        }
        
        articulo = pageFichaStpv.getFicha().getArticuloObject(dCtxSh.appE);
        if (dCtxSh.appE==AppEcom.shop) { //"Buscar en Tienda" y "Favoritos" no existen en Outlet ni Votf
            pageFichaStpv.selectBuscarEnTiendaButton();
            ModalBuscadorTiendasStpV.close(driver);
            pageFichaStpv.selectAnadirAFavoritos();
            pageFichaStpv.changeColorGarment();
            pageFichaStpv.selectRemoveFromFavoritos();
        }
        
        pageFichaStpv.selectAnadirALaBolsaTallaPrevSiSelected(articulo, dCtxSh);
        
        Bolsa.checkCookies(driver);
    }

    @SuppressWarnings("static-access")
    @Test (
        groups={"FichaProducto", "Canal:desktop_App:all"}, alwaysRun=true, 
        description="[Usuario no registrado] Se testean las features secundarias de una ficha con origen el buscador: guía de tallas, carrusel imágenes, imagen central, panel de opciones, total look")
    public void FIC002_FichaFromSearch_SecondaryFeatures_NoReg() throws Exception {
    	WebDriver driver = TestMaker.getDriverTestCase();
        DataCtxShop dCtxSh = getCtxShForTest();
        dCtxSh.userRegistered = false;
        dCtxSh.pais=this.españa;
        dCtxSh.idioma=this.castellano;

        AccesoStpV.accesoAplicacionEnUnPaso(dCtxSh, false, driver);
        
		GetterProducts getterProducts = new GetterProducts.Builder(dCtxSh, driver).build();
        Garment articleWithTotalLook = getterProducts.getOneWithTotalLook();
        SecBuscadorStpV.searchArticulo(articleWithTotalLook, dCtxSh, driver);
        
        PageFichaArtStpV pageFichaStpV = new PageFichaArtStpV(dCtxSh.appE, dCtxSh.channel);
        if (pageFichaStpV.getFicha().getTypeFicha()==TypeFicha.Old) {
            pageFichaStpV.validaExistsImgsCarruselIzqFichaOld();
            pageFichaStpV.secProductDescOld.validateAreInStateInitial(dCtxSh.appE, driver);
            PageFicha pageFicha = PageFicha.newInstance(dCtxSh.channel, dCtxSh.appE, driver);
            if (((PageFichaArtOld)pageFicha).getNumImgsCarruselIzq() > 2) {
                pageFichaStpV.selectImgCarruselIzqFichaOld(2);
            }
            pageFichaStpV.selectImagenCentralFichaOld();
            if (TypePanel.Description.getListApps().contains(dCtxSh.appE)) {
                pageFichaStpV.secProductDescOld.selectPanel(TypePanel.Description, driver);
            }
            if (TypePanel.Composition.getListApps().contains(dCtxSh.appE)) {
                pageFichaStpV.secProductDescOld.selectPanel(TypePanel.Composition, driver);
            }
            if (TypePanel.Returns.getListApps().contains(dCtxSh.appE)) {
                pageFichaStpV.secProductDescOld.selectPanel(TypePanel.Returns, driver);
            }
            if (TypePanel.Shipment.getListApps().contains(dCtxSh.appE)) {
                pageFichaStpV.secProductDescOld.selectPanel(TypePanel.Shipment, driver);  
            }
        } else {
            boolean isFichaAccesorio = pageFichaStpV.getFicha().isFichaAccesorio(); 
            pageFichaStpV.secFotosNew.validaLayoutFotosNew(isFichaAccesorio, driver);
            pageFichaStpV.secTotalLook.checkIsVisible(driver);

            pageFichaStpV.secBolsaButtonAndLinksNew.selectEnvioYDevoluciones(driver);
            pageFichaStpV.getModEnvioYdevol().clickAspaForClose();
            
            pageFichaStpV.secBolsaButtonAndLinksNew.selectDetalleDelProducto(dCtxSh.appE, LineaType.she, driver);
            pageFichaStpV.secBolsaButtonAndLinksNew.selectLinkCompartir(dCtxSh.pais.getCodigo_pais(), driver);

        }
            
        pageFichaStpV.selectGuiaDeTallas(dCtxSh.appE);
        if (dCtxSh.appE==AppEcom.shop) {
            pageFichaStpV.validateSliderIfExists(Slider.ElegidoParaTi);
        }
        
        if (dCtxSh.appE!=AppEcom.outlet) {
            pageFichaStpV.validateSliderIfExists(Slider.CompletaTuLook);
        }
        
        Bolsa.checkCookies(driver);
    }
    
    @SuppressWarnings("static-access")
    @Test (
        groups={"FichaProducto", "Canal:desktop_App:shop"}, alwaysRun=true, 
        description="[Usuario no registrado] Desde Corea/coreano, se testea una ficha con origen la Galería validando el panel KcSafety")
    public void FIC003_FichaFromGalery_CheckKcSafety() throws Exception {
    	WebDriver driver = TestMaker.getDriverTestCase();
        DataCtxShop dCtxSh = getCtxShForTest();
        dCtxSh.userRegistered = false;
        dCtxSh.pais = PaisGetter.get(PaisShop.CoreaDelSur);
        dCtxSh.idioma = dCtxSh.pais.getListIdiomas().get(0); //Coreano
        
        AccesoStpV.accesoAplicacionEnUnPaso(dCtxSh, false, driver);
        
        //TODO en el acceso se ejecuta la función setInitialModalsOff para evitar la aparición de modales
        //pero en el caso de Corea se escapa el de Subscripción en la Newsletter
        PagePrehome.closeModalNewsLetterIfExists(driver);
        
        Menu1rstLevel menuPantalones = MenuTreeApp.getMenuLevel1From(
        	dCtxSh.appE, KeyMenu1rstLevel.from(
        		LineaType.nina, 
        		SublineaNinosType.teen_nina, "pantalones"));
        SecMenusWrapperStpV secMenusStpV = SecMenusWrapperStpV.getNew(dCtxSh, driver);
        secMenusStpV.selectMenu1rstLevelTypeCatalog(menuPantalones, dCtxSh);

        PageGaleriaStpV pageGaleriaStpV = PageGaleriaStpV.getInstance(dCtxSh.channel, dCtxSh.appE, driver);
        LocationArticle location1rstArticle = LocationArticle.getInstanceInCatalog(1);
        DataFichaArt dataArtOrigin = pageGaleriaStpV.selectArticulo(location1rstArticle, dCtxSh);
        
        PageFichaArtStpV pageFichaStpV = new PageFichaArtStpV(dCtxSh.appE, dCtxSh.channel);
        if (pageFichaStpV.getFicha().getTypeFicha()==TypeFicha.Old) {
            if (TypePanel.KcSafety.getListApps().contains(dCtxSh.appE)) {
                pageFichaStpV.secProductDescOld.selectPanel(TypePanel.KcSafety, driver);
            }
        } else {
            if (TypePanel.KcSafety.getListApps().contains(dCtxSh.appE)) {
                pageFichaStpV.secBolsaButtonAndLinksNew.selectDetalleDelProducto(dCtxSh.appE, LineaType.nina, driver);
            }
        }
        
        pageFichaStpV.selectLinkNavigation(ProductNav.Next, dCtxSh, dataArtOrigin.getReferencia());
        pageFichaStpV.selectLinkNavigation(ProductNav.Prev, dCtxSh, dataArtOrigin.getReferencia());
        
        Bolsa.checkCookies(driver);
    }
    
    @Test (
        groups={"FichaProducto", "Canal:all_App:shop"}, 
        alwaysRun=true, description="[Usario no registrado] Testeo Personalización bordados")
    public void FIC005_Articulo_Personalizable_Noreg() throws Exception {
    	WebDriver driver = TestMaker.getDriverTestCase();
        DataCtxShop dCtxSh = getCtxShForTest();
        dCtxSh.pais=españa;
        dCtxSh.idioma=castellano;
        dCtxSh.userRegistered = false;
        AccesoStpV.accesoAplicacionEnUnPaso(dCtxSh, false, driver);
        
		PageGaleriaStpV pageGaleriaStpV = PageGaleriaStpV.getInstance(dCtxSh.channel, dCtxSh.appE, driver);
		Menu1rstLevel menuPersonalizacion = MenuTreeApp.getMenuLevel1From(dCtxSh.appE, KeyMenu1rstLevel.from(LineaType.he, null, "personalizacion"));
        SecMenusWrapperStpV secMenusStpV = SecMenusWrapperStpV.getNew(dCtxSh, driver);
        
        //secMenusStpV.checkExistMenu1rstLevelTypeCatalog(menuPersonalizacion, dCtxSh);
        secMenusStpV.selectMenu1rstLevelTypeCatalog(menuPersonalizacion, dCtxSh);
        secMenusStpV.selectFiltroCollectionIfExists(FilterCollection.nextSeason);
		LocationArticle articleNum = LocationArticle.getInstanceInCatalog(1);
		pageGaleriaStpV.selectArticulo(articleNum, dCtxSh);
        SecModalPersonalizacionStpV modalPersonalizacionStpV = SecModalPersonalizacionStpV.getNewOne(dCtxSh, driver); 
        modalPersonalizacionStpV.checkAreArticleCustomizable();
        
        PageFichaArtStpV pageFichaStpv = new PageFichaArtStpV(dCtxSh.appE, dCtxSh.channel);
        pageFichaStpv.selectFirstTallaAvailable();
        modalPersonalizacionStpV.selectLinkPersonalizacion();
        //modalPersonalizacionStpV.startCustomization();
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
        
        Bolsa.checkCookies(driver);
    }
}