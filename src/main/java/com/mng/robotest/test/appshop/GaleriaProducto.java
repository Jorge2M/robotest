package com.mng.robotest.test.appshop;

import org.testng.annotations.*;

import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.access.InputParamsMango;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.beans.Linea.LineaType;
import com.mng.robotest.test.data.Color;
import com.mng.robotest.test.data.Constantes;
import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.data.PaisShop;
import com.mng.robotest.test.getdata.usuarios.GestorUsersShop;
import com.mng.robotest.test.getdata.usuarios.UserShop;
import com.mng.robotest.test.pageobject.shop.filtros.FilterOrdenacion;
import com.mng.robotest.test.pageobject.shop.galeria.PageGaleria;
import com.mng.robotest.test.pageobject.shop.galeria.PageGaleriaDesktop;
import com.mng.robotest.test.pageobject.shop.galeria.PageGaleria.From;
import com.mng.robotest.test.pageobject.shop.galeria.PageGaleriaDesktop.NumColumnas;
import com.mng.robotest.test.pageobject.shop.galeria.PageGaleriaDesktop.TypeSlider;
import com.mng.robotest.test.pageobject.shop.menus.KeyMenu1rstLevel;
import com.mng.robotest.test.pageobject.shop.menus.Menu1rstLevel;
import com.mng.robotest.test.pageobject.shop.menus.Menu2onLevel;
import com.mng.robotest.test.pageobject.shop.menus.MenuTreeApp;
import com.mng.robotest.test.pageobject.utils.DataScroll;
import com.mng.robotest.test.pageobject.utils.ListDataArticleGalery;
import com.mng.robotest.test.steps.shop.AccesoSteps;
import com.mng.robotest.test.steps.shop.AllPagesSteps;
import com.mng.robotest.test.steps.shop.SecFiltrosSteps;
import com.mng.robotest.test.steps.shop.galeria.DataForScrollStep;
import com.mng.robotest.test.steps.shop.galeria.LocationArticle;
import com.mng.robotest.test.steps.shop.galeria.ModalArticleNotAvailableSteps;
import com.mng.robotest.test.steps.shop.galeria.PageGaleriaSteps;
import com.mng.robotest.test.steps.shop.menus.SecMenusWrapperSteps;
import com.mng.robotest.test.utils.PaisGetter;

import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"static-access"})
public class GaleriaProducto {
	
	private static final Pais espana = PaisGetter.get(PaisShop.ESPANA);
	private static final IdiomaPais castellano = espana.getListIdiomas().get(0);
		
	public GaleriaProducto() {}

	
	@Test (
		groups={"GaleriaProducto", "Canal:mobile,tablet_App:all"}, alwaysRun=true, 
		description="[Usuario registrado] Acceder a galería camisas. Filtros y ordenación. Seleccionar producto y color")
	public void GPO001_Galeria_Camisas() throws Exception {
		WebDriver driver = TestMaker.getDriverTestCase();
		DataCtxShop dCtxSh = getCtxShForTest();
		UserShop userShop = GestorUsersShop.checkoutBestUserForNewTestCase();
		dCtxSh.userConnected = userShop.user;
		dCtxSh.passwordUser = userShop.password;
		dCtxSh.userRegistered = true;
		String tipoPrendasGaleria = "camisa";
			
		AccesoSteps.oneStep(dCtxSh, false/*clearArticulos*/, driver);
		Menu1rstLevel menuCamisas = MenuTreeApp.getMenuLevel1From(dCtxSh.appE, KeyMenu1rstLevel.from(LineaType.she, null, "camisas"));
		SecMenusWrapperSteps secMenusSteps = SecMenusWrapperSteps.getNew(dCtxSh);
		secMenusSteps.selectMenu1rstLevelTypeCatalog(menuCamisas, dCtxSh);
		
		List<Color> colorsToFilter = new ArrayList<>();
		colorsToFilter.add(Color.Blanco);
		//En outlet/movil tenemos el antiguo filtro que sólo permite seleccionar un color
		if (!(dCtxSh.appE==AppEcom.outlet && dCtxSh.channel.isDevice())) {
			colorsToFilter.add(Color.Azul);
		}
		SecFiltrosSteps.selectFiltroColoresStep(dCtxSh.appE, dCtxSh.channel, true, "Camisas", colorsToFilter, driver);

		//Pruebas a nivel del cambio de galería de 2<->4 columnas
		PageGaleriaSteps pageGaleriaSteps = PageGaleriaSteps.getInstance(dCtxSh.channel, dCtxSh.appE, driver);
		if (dCtxSh.channel==Channel.desktop && dCtxSh.appE!=AppEcom.outlet) {
			PageGaleriaDesktop pageGaleria = (PageGaleriaDesktop)PageGaleria.getNew(From.MENU, Channel.desktop, dCtxSh.appE, driver);
			ListDataArticleGalery listArticlesGaleria2Cols = pageGaleria.getListDataArticles();
			listArticlesGaleria2Cols = pageGaleriaSteps.selectListadoXColumnasDesktop(NumColumnas.cuatro, listArticlesGaleria2Cols);
			pageGaleriaSteps.selectListadoXColumnasDesktop(NumColumnas.dos, listArticlesGaleria2Cols);
		}
		
		DataForScrollStep dataScroll = new DataForScrollStep();
		dataScroll.numPageToScroll = PageGaleriaDesktop.MAX_PAGE_TO_SCROLL;
		dataScroll.ordenacionExpected = FilterOrdenacion.NOordenado;
		dataScroll.validateArticlesExpected = false;
		dataScroll.validaImgBroken = true;
		DataScroll datosScrollFinalGaleria = pageGaleriaSteps.scrollFromFirstPage(dataScroll, dCtxSh);
		
		if (dCtxSh.channel.isDevice()) {
			pageGaleriaSteps.backTo1erArticleMobilStep(dCtxSh);
		}
		int numArticulosPantalla = 
			pageGaleriaSteps.seleccionaOrdenacionGaleria(FilterOrdenacion.PrecioDesc, tipoPrendasGaleria, -1, dCtxSh);
		
		dataScroll.validateArticlesExpected = true;
		dataScroll.numArticlesExpected = datosScrollFinalGaleria.articulosTotalesPagina;
		pageGaleriaSteps.scrollFromFirstPage(dataScroll, dCtxSh);	
		
		pageGaleriaSteps.seleccionaOrdenacionGaleria(FilterOrdenacion.PrecioAsc, tipoPrendasGaleria, numArticulosPantalla, dCtxSh);
		pageGaleriaSteps.scrollFromFirstPage(dataScroll, dCtxSh);
		pageGaleriaSteps.selecColorFromArtGaleriaStep(1/*numArtConColores*/, 2/*posColor*/);
		pageGaleriaSteps.selecArticuloGaleriaStep(1/*numArtConColores*/);
	}

	@Test (
		groups={"GaleriaProducto", "Canal:all_App:all"}, alwaysRun=true, 
		description="[Usuario no registrado][Chrome] Acceder a galería camisas. Filtro color. Scroll")
	public void GPO004_Navega_Galeria() throws Exception {
		WebDriver driver = TestMaker.getDriverTestCase();
		DataCtxShop dCtxSh = getCtxShForTest();
		dCtxSh.userRegistered = false;

		AccesoSteps.oneStep(dCtxSh, false, driver);
		Menu1rstLevel menuCamisas = MenuTreeApp.getMenuLevel1From(dCtxSh.appE, KeyMenu1rstLevel.from(LineaType.she, null, "camisas"));
		SecMenusWrapperSteps secMenusSteps = SecMenusWrapperSteps.getNew(dCtxSh);
		secMenusSteps.selectMenu1rstLevelTypeCatalog(menuCamisas, dCtxSh);

		List<Color> colorsToFilter = new ArrayList<>();
		colorsToFilter.add(Color.Blanco);
		//En outlet/movil tenemos el antiguo filtro que sólo permite seleccionar un color
		if (!(dCtxSh.appE!=AppEcom.outlet && dCtxSh.channel.isDevice())) {
			colorsToFilter.add(Color.Negro);
			colorsToFilter.add(Color.Azul);
		}
		SecFiltrosSteps.selectFiltroColoresStep(dCtxSh.appE, dCtxSh.channel, false, "Camisas", colorsToFilter, driver);
			
		//Scrollar hasta la 3a página
		PageGaleriaSteps pageGaleriaSteps = PageGaleriaSteps.getInstance(dCtxSh.channel, dCtxSh.appE, driver);
		DataForScrollStep dataScroll = new DataForScrollStep();
		dataScroll.numPageToScroll = 3;
		dataScroll.ordenacionExpected = FilterOrdenacion.NOordenado;
		dataScroll.validateArticlesExpected = false;
		dataScroll.validaImgBroken = true;
		pageGaleriaSteps.scrollFromFirstPage(dataScroll, dCtxSh);
		LocationArticle loc1rsArticle1rstPage = LocationArticle.getInstanceInPage(2, 1);
		pageGaleriaSteps.selectArticuloEnPestanyaAndBack(loc1rsArticle1rstPage, dCtxSh.pais);
		
		//Scrollar hasta el final de la Galería
		dataScroll.numPageToScroll = PageGaleriaDesktop.MAX_PAGE_TO_SCROLL;
		DataScroll datosScrollFinalGaleria = pageGaleriaSteps.scrollFromFirstPage(dataScroll, dCtxSh);
			
		LocationArticle loc1rsArticleLastPage = LocationArticle.getInstanceInPage(datosScrollFinalGaleria.paginaFinal, 1);
		pageGaleriaSteps.selectArticulo(loc1rsArticleLastPage, dCtxSh);
		AllPagesSteps.backNagegador(driver);

		//Scrollar hasta el final de la Galería (comprobaremos que el número de artículos es el mismo que en el anterior scroll hasta el final)
		dataScroll.validateArticlesExpected = true;
		dataScroll.numArticlesExpected = datosScrollFinalGaleria.articulosTotalesPagina;
		dataScroll.validaImgBroken = false;
		pageGaleriaSteps.scrollFromFirstPage(dataScroll, dCtxSh);
	}
	
	@Test (
		groups={"GaleriaProducto", "Canal:all_App:all"}, alwaysRun=true, 
		description="[Usuario registrado] Acceder a galería. Navegación menú lateral de primer y segundo nivel. Selector de precios")
	public void GPO005_Galeria_Menu_Lateral() throws Exception {
		WebDriver driver = TestMaker.getDriverTestCase();
		DataCtxShop dCtxSh = getCtxShForTest();
		UserShop userShop = GestorUsersShop.checkoutBestUserForNewTestCase();
		dCtxSh.userConnected = userShop.user;
		dCtxSh.passwordUser = userShop.password;
		dCtxSh.userRegistered = true;
	
		AccesoSteps.oneStep(dCtxSh, false, driver);
		PageGaleriaSteps pageGaleriaSteps = PageGaleriaSteps.getInstance(dCtxSh.channel, dCtxSh.appE, driver);
		Menu1rstLevel menuCamisas = MenuTreeApp.getMenuLevel1From(dCtxSh.appE, KeyMenu1rstLevel.from(LineaType.she, null, "camisas"));
		Menu1rstLevel menuCardigans = MenuTreeApp.getMenuLevel1From(dCtxSh.appE, KeyMenu1rstLevel.from(LineaType.she, null, "cardigans-y-jerseis"));
		SecMenusWrapperSteps secMenusSteps = SecMenusWrapperSteps.getNew(dCtxSh);
		if (dCtxSh.appE==AppEcom.outlet || dCtxSh.channel.isDevice()) {
			secMenusSteps.selectMenu1rstLevelTypeCatalog(menuCardigans, dCtxSh);
		} else {
			Menu1rstLevel menuNuevo = MenuTreeApp.getMenuLevel1From(dCtxSh.appE, KeyMenu1rstLevel.from(LineaType.she, null, "New Now"));
			secMenusSteps.selectMenu1rstLevelTypeCatalog(menuNuevo, dCtxSh);
			pageGaleriaSteps.secCrossSellingSteps.validaIsCorrect(LineaType.she, null);
			pageGaleriaSteps.hayPanoramicasEnGaleriaDesktop(Constantes.PORC_PANORAMICAS);
		}
		
		if (!dCtxSh.channel.isDevice()) {
			pageGaleriaSteps.getSecSelectorPreciosSteps().seleccionaIntervalo();
		}

		if (dCtxSh.appE!=AppEcom.outlet && !dCtxSh.channel.isDevice()) {
			pageGaleriaSteps.secCrossSellingSteps.validaIsCorrect(LineaType.she, null);
			pageGaleriaSteps.hayPanoramicasEnGaleriaDesktop(Constantes.PORC_PANORAMICAS);
		}	
		
		selectMenuVestidos(secMenusSteps, dCtxSh);
		
		if (dCtxSh.appE==AppEcom.outlet || dCtxSh.channel.isDevice()) {
			secMenusSteps.selectMenuLateral1erLevelTypeCatalog(menuCardigans, dCtxSh);
			Menu2onLevel menuCardigansJerseis = MenuTreeApp.getMenuLevel2From(menuCardigans, "jerseis");
			secMenusSteps.selectMenu2onLevel(menuCardigansJerseis, dCtxSh);
		} else {
			secMenusSteps.selectMenuLateral1erLevelTypeCatalog(menuCamisas, dCtxSh);
			Menu2onLevel menuCamisasBasicas = MenuTreeApp.getMenuLevel2From(menuCamisas, "básicas");
			secMenusSteps.selectMenu2onLevel(menuCamisasBasicas, dCtxSh);
		}
	}
	
	private void selectMenuVestidos(SecMenusWrapperSteps secMenusSteps, DataCtxShop dCtxSh) throws Exception {
		String menuToClick = "vestidos";
//		if (dCtxSh.appE==AppEcom.outlet) {
//			menuToClick = "vestidos";
//		}
		Menu1rstLevel menuVestidos = MenuTreeApp.getMenuLevel1From(dCtxSh.appE, KeyMenu1rstLevel.from(LineaType.she, null, menuToClick));
		secMenusSteps.selectMenu1rstLevelTypeCatalog(menuVestidos, dCtxSh);
	}
	
	@Test (
		groups={"GaleriaProducto", "Canal:desktop_App:shop,outlet"}, alwaysRun=true, 
		description=
			"Acceder a galería y testear el slider. Testeamos secuencias de sliders en ambas direcciones y " + 
			"finalmente las combinamos con cambios de color")
	public void GPO006_SliderInDesktop() throws Exception {
		WebDriver driver = TestMaker.getDriverTestCase();
		DataCtxShop dCtxSh = getCtxShForTest();
		dCtxSh.userRegistered = false;
	
		//Ini script
		AccesoSteps.oneStep(dCtxSh, false, driver);
		Menu1rstLevel menuCamisas = MenuTreeApp.getMenuLevel1From(dCtxSh.appE, KeyMenu1rstLevel.from(LineaType.she, null, "vestidos"));
		SecMenusWrapperSteps secMenusSteps = SecMenusWrapperSteps.getNew(dCtxSh);
		secMenusSteps.selectMenu1rstLevelTypeCatalog(menuCamisas, dCtxSh);
				
		PageGaleriaSteps pageGaleriaSteps = PageGaleriaSteps.getInstance(dCtxSh.channel, dCtxSh.appE, driver);
		ArrayList<TypeSlider> typeSliderList = new ArrayList<>();
		typeSliderList.add(TypeSlider.next);
		String src2onImage = pageGaleriaSteps.clicksSliderArticuloConColores(1, typeSliderList);
		
		typeSliderList.clear();
		typeSliderList.add(TypeSlider.prev);
		int numArtConColores = 1;
		pageGaleriaSteps.clicksSliderArticuloConColores(numArtConColores, typeSliderList);
		
		typeSliderList.clear();
		typeSliderList.add(TypeSlider.next);
		pageGaleriaSteps.clicksSliderArticuloConColores(numArtConColores, typeSliderList, src2onImage);
		
		//Seleccionar el 2o color del artículo
		String srcImgAfterClickColor = pageGaleriaSteps.selecColorFromArtGaleriaStep(numArtConColores, 2);
		
		typeSliderList.clear();
		typeSliderList.add(TypeSlider.next);
		typeSliderList.add(TypeSlider.next);
		pageGaleriaSteps.clicksSliderArticuloConColores(numArtConColores, typeSliderList);
		
		typeSliderList.clear();
		typeSliderList.add(TypeSlider.prev);
		typeSliderList.add(TypeSlider.prev);
		pageGaleriaSteps.clicksSliderArticuloConColores(numArtConColores, typeSliderList, srcImgAfterClickColor);
	}

	@Test (
		groups={"GaleriaProducto", "Canal:desktop_App:shop"}, alwaysRun=true,
		description="[Usuario registrado] Acceder a galería camisas. Forzar caso avisame en listado")
	public void GPO007_Galeria_Camisas() throws Exception {
		WebDriver driver = TestMaker.getDriverTestCase();
		DataCtxShop dCtxSh = getCtxShForTest();
		UserShop userShop = GestorUsersShop.checkoutBestUserForNewTestCase();
		dCtxSh.userConnected = userShop.user;
		dCtxSh.passwordUser = userShop.password;
		dCtxSh.userRegistered = true;

		// Abrir listado de mujer camisas
		AccesoSteps.oneStep(dCtxSh, false/*clearArticulos*/, driver);
		Menu1rstLevel menuCamisas = MenuTreeApp.getMenuLevel1From(dCtxSh.appE, KeyMenu1rstLevel.from(LineaType.she, null, "camisas"));
		SecMenusWrapperSteps secMenusSteps = SecMenusWrapperSteps.getNew(dCtxSh);
		secMenusSteps.selectMenu1rstLevelTypeCatalog(menuCamisas, dCtxSh);

		// Abrir avisame desde el listado buscando primera talla sin stock y comprobar que se abierto y que contiene texto RGPD
		PageGaleriaSteps pageGaleriaSteps = PageGaleriaSteps.getInstance(dCtxSh.channel, dCtxSh.appE, driver);
		pageGaleriaSteps.selectTallaNoDisponibleArticulo();
		ModalArticleNotAvailableSteps modalArticleNotAvailableSteps = ModalArticleNotAvailableSteps.getInstance(dCtxSh.channel, dCtxSh.appE, driver);
		modalArticleNotAvailableSteps.checkVisibleAvisame();
	}
	
	private DataCtxShop getCtxShForTest() throws Exception {
		InputParamsMango inputParamsSuite = (InputParamsMango)TestMaker.getInputParamsSuite();
		DataCtxShop dCtxSh = new DataCtxShop();
		dCtxSh.setAppEcom((AppEcom)inputParamsSuite.getApp());
		dCtxSh.setChannel(inputParamsSuite.getChannel());
		dCtxSh.pais = this.espana;
		dCtxSh.idioma = this.castellano;
		return dCtxSh;
	}
}