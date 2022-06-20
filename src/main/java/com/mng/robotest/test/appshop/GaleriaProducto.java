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
import com.mng.robotest.test.stpv.shop.AccesoStpV;
import com.mng.robotest.test.stpv.shop.AllPagesStpV;
import com.mng.robotest.test.stpv.shop.SecFiltrosStpV;
import com.mng.robotest.test.stpv.shop.galeria.DataForScrollStep;
import com.mng.robotest.test.stpv.shop.galeria.LocationArticle;
import com.mng.robotest.test.stpv.shop.galeria.ModalArticleNotAvailableStpV;
import com.mng.robotest.test.stpv.shop.galeria.PageGaleriaStpV;
import com.mng.robotest.test.stpv.shop.menus.SecMenusWrapperStpV;
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
			
		AccesoStpV.oneStep(dCtxSh, false/*clearArticulos*/, driver);
		Menu1rstLevel menuCamisas = MenuTreeApp.getMenuLevel1From(dCtxSh.appE, KeyMenu1rstLevel.from(LineaType.she, null, "camisas"));
		SecMenusWrapperStpV secMenusStpV = SecMenusWrapperStpV.getNew(dCtxSh, driver);
		secMenusStpV.selectMenu1rstLevelTypeCatalog(menuCamisas, dCtxSh);
		
		List<Color> colorsToFilter = new ArrayList<>();
		colorsToFilter.add(Color.Blanco);
		//En outlet/movil tenemos el antiguo filtro que sólo permite seleccionar un color
		if (!(dCtxSh.appE==AppEcom.outlet && dCtxSh.channel.isDevice())) {
			colorsToFilter.add(Color.Azul);
		}
		SecFiltrosStpV.selectFiltroColoresStep(dCtxSh.appE, dCtxSh.channel, true, "Camisas", colorsToFilter, driver);

		//Pruebas a nivel del cambio de galería de 2<->4 columnas
		PageGaleriaStpV pageGaleriaStpV = PageGaleriaStpV.getInstance(dCtxSh.channel, dCtxSh.appE, driver);
		if (dCtxSh.channel==Channel.desktop && dCtxSh.appE!=AppEcom.outlet) {
			PageGaleriaDesktop pageGaleria = (PageGaleriaDesktop)PageGaleria.getNew(From.MENU, Channel.desktop, dCtxSh.appE, driver);
			ListDataArticleGalery listArticlesGaleria2Cols = pageGaleria.getListDataArticles();
			listArticlesGaleria2Cols = pageGaleriaStpV.selectListadoXColumnasDesktop(NumColumnas.cuatro, listArticlesGaleria2Cols);
			pageGaleriaStpV.selectListadoXColumnasDesktop(NumColumnas.dos, listArticlesGaleria2Cols);
		}
		
		DataForScrollStep dataScroll = new DataForScrollStep();
		dataScroll.numPageToScroll = PageGaleriaDesktop.MAX_PAGE_TO_SCROLL;
		dataScroll.ordenacionExpected = FilterOrdenacion.NOordenado;
		dataScroll.validateArticlesExpected = false;
		dataScroll.validaImgBroken = true;
		DataScroll datosScrollFinalGaleria = pageGaleriaStpV.scrollFromFirstPage(dataScroll, dCtxSh);
		
		if (dCtxSh.channel.isDevice()) {
			pageGaleriaStpV.backTo1erArticleMobilStep(dCtxSh);
		}
		int numArticulosPantalla = 
			pageGaleriaStpV.seleccionaOrdenacionGaleria(FilterOrdenacion.PrecioDesc, tipoPrendasGaleria, -1, dCtxSh);
		
		dataScroll.validateArticlesExpected = true;
		dataScroll.numArticlesExpected = datosScrollFinalGaleria.articulosTotalesPagina;
		pageGaleriaStpV.scrollFromFirstPage(dataScroll, dCtxSh);	
		
		pageGaleriaStpV.seleccionaOrdenacionGaleria(FilterOrdenacion.PrecioAsc, tipoPrendasGaleria, numArticulosPantalla, dCtxSh);
		pageGaleriaStpV.scrollFromFirstPage(dataScroll, dCtxSh);
		pageGaleriaStpV.selecColorFromArtGaleriaStep(1/*numArtConColores*/, 2/*posColor*/);
		pageGaleriaStpV.selecArticuloGaleriaStep(1/*numArtConColores*/);
	}

	@Test (
		groups={"GaleriaProducto", "Canal:all_App:all"}, alwaysRun=true, 
		description="[Usuario no registrado][Chrome] Acceder a galería camisas. Filtro color. Scroll")
	public void GPO004_Navega_Galeria() throws Exception {
		WebDriver driver = TestMaker.getDriverTestCase();
		DataCtxShop dCtxSh = getCtxShForTest();
		dCtxSh.userRegistered = false;

		AccesoStpV.oneStep(dCtxSh, false, driver);
		Menu1rstLevel menuCamisas = MenuTreeApp.getMenuLevel1From(dCtxSh.appE, KeyMenu1rstLevel.from(LineaType.she, null, "camisas"));
		SecMenusWrapperStpV secMenusStpV = SecMenusWrapperStpV.getNew(dCtxSh, driver);
		secMenusStpV.selectMenu1rstLevelTypeCatalog(menuCamisas, dCtxSh);

		List<Color> colorsToFilter = new ArrayList<>();
		colorsToFilter.add(Color.Blanco);
		//En outlet/movil tenemos el antiguo filtro que sólo permite seleccionar un color
		if (!(dCtxSh.appE!=AppEcom.outlet && dCtxSh.channel.isDevice())) {
			colorsToFilter.add(Color.Negro);
			colorsToFilter.add(Color.Azul);
		}
		SecFiltrosStpV.selectFiltroColoresStep(dCtxSh.appE, dCtxSh.channel, false, "Camisas", colorsToFilter, driver);
			
		//Scrollar hasta la 3a página
		PageGaleriaStpV pageGaleriaStpV = PageGaleriaStpV.getInstance(dCtxSh.channel, dCtxSh.appE, driver);
		DataForScrollStep dataScroll = new DataForScrollStep();
		dataScroll.numPageToScroll = 3;
		dataScroll.ordenacionExpected = FilterOrdenacion.NOordenado;
		dataScroll.validateArticlesExpected = false;
		dataScroll.validaImgBroken = true;
		pageGaleriaStpV.scrollFromFirstPage(dataScroll, dCtxSh);
		LocationArticle loc1rsArticle1rstPage = LocationArticle.getInstanceInPage(2, 1);
		pageGaleriaStpV.selectArticuloEnPestanyaAndBack(loc1rsArticle1rstPage, dCtxSh.pais);
		
		//Scrollar hasta el final de la Galería
		dataScroll.numPageToScroll = PageGaleriaDesktop.MAX_PAGE_TO_SCROLL;
		DataScroll datosScrollFinalGaleria = pageGaleriaStpV.scrollFromFirstPage(dataScroll, dCtxSh);
			
		LocationArticle loc1rsArticleLastPage = LocationArticle.getInstanceInPage(datosScrollFinalGaleria.paginaFinal, 1);
		pageGaleriaStpV.selectArticulo(loc1rsArticleLastPage, dCtxSh);
		AllPagesStpV.backNagegador(driver);

		//Scrollar hasta el final de la Galería (comprobaremos que el número de artículos es el mismo que en el anterior scroll hasta el final)
		dataScroll.validateArticlesExpected = true;
		dataScroll.numArticlesExpected = datosScrollFinalGaleria.articulosTotalesPagina;
		dataScroll.validaImgBroken = false;
		pageGaleriaStpV.scrollFromFirstPage(dataScroll, dCtxSh);
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
	
		AccesoStpV.oneStep(dCtxSh, false, driver);
		PageGaleriaStpV pageGaleriaStpV = PageGaleriaStpV.getInstance(dCtxSh.channel, dCtxSh.appE, driver);
		Menu1rstLevel menuCamisas = MenuTreeApp.getMenuLevel1From(dCtxSh.appE, KeyMenu1rstLevel.from(LineaType.she, null, "camisas"));
		Menu1rstLevel menuCardigans = MenuTreeApp.getMenuLevel1From(dCtxSh.appE, KeyMenu1rstLevel.from(LineaType.she, null, "cardigans-y-jerseis"));
		SecMenusWrapperStpV secMenusStpV = SecMenusWrapperStpV.getNew(dCtxSh, driver);
		if (dCtxSh.appE==AppEcom.outlet || dCtxSh.channel.isDevice()) {
			secMenusStpV.selectMenu1rstLevelTypeCatalog(menuCardigans, dCtxSh);
		} else {
			Menu1rstLevel menuNuevo = MenuTreeApp.getMenuLevel1From(dCtxSh.appE, KeyMenu1rstLevel.from(LineaType.she, null, "New Now"));
			secMenusStpV.selectMenu1rstLevelTypeCatalog(menuNuevo, dCtxSh);
			pageGaleriaStpV.secCrossSellingStpV.validaIsCorrect(LineaType.she, null);
			pageGaleriaStpV.hayPanoramicasEnGaleriaDesktop(Constantes.PORC_PANORAMICAS);
		}
		
		if (!dCtxSh.channel.isDevice()) {
			pageGaleriaStpV.getSecSelectorPreciosStpV().seleccionaIntervalo();
		}

		if (dCtxSh.appE!=AppEcom.outlet && !dCtxSh.channel.isDevice()) {
			pageGaleriaStpV.secCrossSellingStpV.validaIsCorrect(LineaType.she, null);
			pageGaleriaStpV.hayPanoramicasEnGaleriaDesktop(Constantes.PORC_PANORAMICAS);
		}	
		
		selectMenuVestidos(secMenusStpV, dCtxSh);
		
		if (dCtxSh.appE==AppEcom.outlet || dCtxSh.channel.isDevice()) {
			secMenusStpV.selectMenuLateral1erLevelTypeCatalog(menuCardigans, dCtxSh);
			Menu2onLevel menuCardigansJerseis = MenuTreeApp.getMenuLevel2From(menuCardigans, "jerseis");
			secMenusStpV.selectMenu2onLevel(menuCardigansJerseis, dCtxSh);
		} else {
			secMenusStpV.selectMenuLateral1erLevelTypeCatalog(menuCamisas, dCtxSh);
			Menu2onLevel menuCamisasBasicas = MenuTreeApp.getMenuLevel2From(menuCamisas, "básicas");
			secMenusStpV.selectMenu2onLevel(menuCamisasBasicas, dCtxSh);
		}
	}
	
	private void selectMenuVestidos(SecMenusWrapperStpV secMenusStpV, DataCtxShop dCtxSh) throws Exception {
		String menuToClick = "vestidos";
//		if (dCtxSh.appE==AppEcom.outlet) {
//			menuToClick = "vestidos";
//		}
		Menu1rstLevel menuVestidos = MenuTreeApp.getMenuLevel1From(dCtxSh.appE, KeyMenu1rstLevel.from(LineaType.she, null, menuToClick));
		secMenusStpV.selectMenu1rstLevelTypeCatalog(menuVestidos, dCtxSh);
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
		AccesoStpV.oneStep(dCtxSh, false, driver);
		Menu1rstLevel menuCamisas = MenuTreeApp.getMenuLevel1From(dCtxSh.appE, KeyMenu1rstLevel.from(LineaType.she, null, "vestidos"));
		SecMenusWrapperStpV secMenusStpV = SecMenusWrapperStpV.getNew(dCtxSh, driver);
		secMenusStpV.selectMenu1rstLevelTypeCatalog(menuCamisas, dCtxSh);
				
		PageGaleriaStpV pageGaleriaStpV = PageGaleriaStpV.getInstance(dCtxSh.channel, dCtxSh.appE, driver);
		ArrayList<TypeSlider> typeSliderList = new ArrayList<>();
		typeSliderList.add(TypeSlider.next);
		String src2onImage = pageGaleriaStpV.clicksSliderArticuloConColores(1, typeSliderList);
		
		typeSliderList.clear();
		typeSliderList.add(TypeSlider.prev);
		int numArtConColores = 1;
		pageGaleriaStpV.clicksSliderArticuloConColores(numArtConColores, typeSliderList);
		
		typeSliderList.clear();
		typeSliderList.add(TypeSlider.next);
		pageGaleriaStpV.clicksSliderArticuloConColores(numArtConColores, typeSliderList, src2onImage);
		
		//Seleccionar el 2o color del artículo
		String srcImgAfterClickColor = pageGaleriaStpV.selecColorFromArtGaleriaStep(numArtConColores, 2);
		
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
		WebDriver driver = TestMaker.getDriverTestCase();
		DataCtxShop dCtxSh = getCtxShForTest();
		UserShop userShop = GestorUsersShop.checkoutBestUserForNewTestCase();
		dCtxSh.userConnected = userShop.user;
		dCtxSh.passwordUser = userShop.password;
		dCtxSh.userRegistered = true;

		// Abrir listado de mujer camisas
		AccesoStpV.oneStep(dCtxSh, false/*clearArticulos*/, driver);
		Menu1rstLevel menuCamisas = MenuTreeApp.getMenuLevel1From(dCtxSh.appE, KeyMenu1rstLevel.from(LineaType.she, null, "camisas"));
		SecMenusWrapperStpV secMenusStpV = SecMenusWrapperStpV.getNew(dCtxSh, driver);
		secMenusStpV.selectMenu1rstLevelTypeCatalog(menuCamisas, dCtxSh);

		// Abrir avisame desde el listado buscando primera talla sin stock y comprobar que se abierto y que contiene texto RGPD
		PageGaleriaStpV pageGaleriaStpV = PageGaleriaStpV.getInstance(dCtxSh.channel, dCtxSh.appE, driver);
		pageGaleriaStpV.selectTallaNoDisponibleArticulo();
		ModalArticleNotAvailableStpV modalArticleNotAvailableStpV = ModalArticleNotAvailableStpV.getInstance(dCtxSh.channel, dCtxSh.appE, driver);
		modalArticleNotAvailableStpV.checkVisibleAvisame();
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