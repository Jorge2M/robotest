package com.mng.robotest.test.appshop;

import org.testng.annotations.*;

import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;
import com.mng.robotest.access.InputParamsMango;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.ficha.steps.PageFichaArtSteps;
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.beans.Linea.LineaType;
import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.data.PaisShop;
import com.mng.robotest.test.datastored.DataBag;
import com.mng.robotest.test.datastored.DataFavoritos;
import com.mng.robotest.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test.getdata.usuarios.GestorUsersShop;
import com.mng.robotest.test.getdata.usuarios.UserShop;
import com.mng.robotest.test.pageobject.shop.galeria.PageGaleriaDesktop.NumColumnas;
import com.mng.robotest.test.pageobject.shop.menus.KeyMenu1rstLevel;
import com.mng.robotest.test.pageobject.shop.menus.Menu1rstLevel;
import com.mng.robotest.test.pageobject.shop.menus.MenuTreeApp;
import com.mng.robotest.test.steps.shop.AccesoSteps;
import com.mng.robotest.test.steps.shop.SecBolsaSteps;
import com.mng.robotest.test.steps.shop.favoritos.PageFavoritosSteps;
import com.mng.robotest.test.steps.shop.galeria.LocationArticle;
import com.mng.robotest.test.steps.shop.galeria.PageGaleriaSteps;
import com.mng.robotest.test.steps.shop.galeria.PageGaleriaSteps.TypeActionFav;
import com.mng.robotest.test.steps.shop.menus.SecMenusWrapperSteps;
import com.mng.robotest.test.utils.PaisGetter;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebDriver;

@SuppressWarnings({ "static-access" })

public class Favoritos implements Serializable {

	private static final long serialVersionUID = -3932978752450813757L;
	
	public int prioridad;
	private String index_fact = "";
	private Pais paisFactory = null;
	private IdiomaPais idiomaFactory = null;
	private static final Pais espana = PaisGetter.get(PaisShop.ESPANA);

	//Si añadimos un constructor para el @Factory hemos de añadir este constructor para la invocación desde SmokeTest
	public Favoritos() {}

	/**
	 * Constructor para invocación desde @Factory
	 */
	public Favoritos(Pais pais, IdiomaPais idioma, int prioridad) {
		this.paisFactory = pais;
		this.idiomaFactory = idioma;
		this.index_fact = pais.getNombre_pais() + " (" + pais.getCodigo_pais() + ") " + "-" + idioma.getCodigo().getLiteral();
		this.prioridad = prioridad;
	} 

	private DataCtxShop getCtxShForTest() throws Exception {
		InputParamsMango inputParamsSuite = (InputParamsMango)TestMaker.getInputParamsSuite();
		DataCtxShop dCtxSh = new DataCtxShop();
		dCtxSh.setAppEcom((AppEcom)inputParamsSuite.getApp());
		dCtxSh.setChannel(inputParamsSuite.getChannel());

		//Si el acceso es normal (no es desde una @Factory) utilizaremos el España/Castellano
		if (this.paisFactory==null) {
			dCtxSh.pais = espana;
			dCtxSh.idioma = espana.getListIdiomas().get(0);
		} else {
			dCtxSh.pais = paisFactory;
			dCtxSh.idioma = idiomaFactory;
		}
		return dCtxSh;
	}

	@Test(
		groups={"Favoritos", "Canal:desktop,mobile_App:shop", "SupportsFactoryCountrys"}, alwaysRun=true, 
		description="[Usuario registrado] Alta favoritos desde la galería")
	public void FAV001_AltaFavoritosDesdeGaleria() throws Exception {
		TestCaseTM.addNameSufix(this.index_fact);
		WebDriver driver = TestMaker.getDriverTestCase();
		DataCtxShop dCtxSh = getCtxShForTest();

		UserShop userShop = GestorUsersShop.checkoutBestUserForNewTestCase();
		dCtxSh.userConnected = userShop.user;
		dCtxSh.passwordUser = userShop.password;
		dCtxSh.userRegistered=true;
		DataFavoritos dataFavoritos = new DataFavoritos();
		DataBag dataBolsa = new DataBag();

		AccesoSteps.oneStep(dCtxSh, false, driver);
		SecBolsaSteps secBolsaSteps = new SecBolsaSteps(dCtxSh, driver);
		secBolsaSteps.clear();

		PageFavoritosSteps pageFavoritosSteps = PageFavoritosSteps.getNew(driver);
		pageFavoritosSteps.clearAll(dataFavoritos, dCtxSh);

		Menu1rstLevel menuVestidos = MenuTreeApp.getMenuLevel1From(dCtxSh.appE, KeyMenu1rstLevel.from(LineaType.she, null, "Vestidos"));
		SecMenusWrapperSteps secMenusSteps = SecMenusWrapperSteps.getNew(dCtxSh, driver);
		secMenusSteps.selectMenu1rstLevelTypeCatalog(menuVestidos, dCtxSh);

		PageGaleriaSteps pageGaleriaSteps = PageGaleriaSteps.getInstance(dCtxSh.channel, dCtxSh.appE, driver);
		if (dCtxSh.channel==Channel.desktop) {
			pageGaleriaSteps.selectListadoXColumnasDesktop(NumColumnas.cuatro);
		}
		
		List<Integer> iconsToMark = Arrays.asList(2, 3, 5);  
		pageGaleriaSteps.clickArticlesHearthIcons(iconsToMark, TypeActionFav.MARCAR, dataFavoritos);

		List<Integer> iconsToUnmark = Arrays.asList(3);
		pageGaleriaSteps.clickArticlesHearthIcons(iconsToUnmark, TypeActionFav.DESMARCAR, dataFavoritos);

		secMenusSteps.getMenusUser().selectFavoritos(dataFavoritos);
		ArticuloScreen artToPlay = dataFavoritos.getArticulo(0);
		pageFavoritosSteps.addArticuloToBag(artToPlay, dataBolsa, dCtxSh.channel, dCtxSh.appE, dCtxSh.pais);
		if (dCtxSh.channel.isDevice()) {
			secBolsaSteps.clickAspaForCloseMobil();
			pageFavoritosSteps.validaIsPageOK(dataFavoritos);
		}

		pageFavoritosSteps.clear(artToPlay, dataFavoritos);
		pageFavoritosSteps.clearAll(dataFavoritos, dCtxSh);
	}

	@Test(
		groups={"Favoritos", "Canal:desktop,mobile_App:shop", "SupportsFactoryCountrys"}, alwaysRun=true, 
		description="[Usuario no registrado] Alta favoritos desde la galería Mango-Home y posterior identificación")
	public void FAV002_AltaFavoritosDesdeFicha() throws Exception {
		TestCaseTM.addNameSufix(this.index_fact);
		WebDriver driver = TestMaker.getDriverTestCase();
		DataCtxShop dCtxSh = getCtxShForTest();
		dCtxSh.userRegistered=false;

		DataFavoritos dataFavoritos = new DataFavoritos();
		DataBag dataBolsa = new DataBag();

		AccesoSteps.oneStep(dCtxSh, false, driver);
		
		SecBolsaSteps secBolsaSteps = new SecBolsaSteps(dCtxSh, driver);
		secBolsaSteps.clear();

		PageFavoritosSteps pageFavoritosSteps = PageFavoritosSteps.getNew(driver);
		pageFavoritosSteps.clearAll(dataFavoritos, dCtxSh);


		SecMenusWrapperSteps secMenusSteps = SecMenusWrapperSteps.getNew(dCtxSh, driver);
		secMenusSteps.selectMenu1rstLevelTypeCatalog(getMenu(dCtxSh.appE), dCtxSh);
		LocationArticle article1 = LocationArticle.getInstanceInCatalog(1);

		PageGaleriaSteps pageGaleriaSteps = PageGaleriaSteps.getInstance(dCtxSh.channel, dCtxSh.appE, driver);
		pageGaleriaSteps.selectArticulo(article1, dCtxSh);

		PageFichaArtSteps pageFichaArtStpv = new PageFichaArtSteps(dCtxSh.appE, dCtxSh.channel, dCtxSh.pais);
		pageFichaArtStpv.selectAnadirAFavoritos(dataFavoritos);

		UserShop userShop = GestorUsersShop.checkoutBestUserForNewTestCase();
		dCtxSh.userConnected = userShop.user;
		dCtxSh.passwordUser = userShop.password;
		AccesoSteps.identificacionEnMango(dCtxSh, driver);
		//TestAB.activateTestABiconoBolsaDesktop(0, dCtxSh, dFTest.driver);
		
		secBolsaSteps.clear();
		secMenusSteps.getMenusUser().selectFavoritos(dataFavoritos);

		pageFavoritosSteps.clickShareIsOk();
		pageFavoritosSteps.closeShareModal();

		ArticuloScreen artToPlay = dataFavoritos.getArticulo(0);
		pageFavoritosSteps.clickArticuloImg(artToPlay);
		pageFavoritosSteps
			.getModalFichaFavoritosSteps()
			.addArticuloToBag(artToPlay, dataBolsa, dCtxSh.channel, dCtxSh.appE, dCtxSh.pais);
		
		if (dCtxSh.channel.isDevice()) {
			pageFavoritosSteps.validaIsPageOK(dataFavoritos);
		} else {
			pageFavoritosSteps.getModalFichaFavoritosSteps().closeFicha(artToPlay);
		}

		pageFavoritosSteps.clear(artToPlay, dataFavoritos);
	}
	
	private Menu1rstLevel getMenu(AppEcom app) {
		if (app==AppEcom.outlet) {
			return MenuTreeApp.getMenuLevel1From(app, KeyMenu1rstLevel.from(LineaType.she, null, "Vestidos"));
		}
		return MenuTreeApp.getMenuLevel1From(app, KeyMenu1rstLevel.from(LineaType.home, null, "Albornoces"));
	}
}