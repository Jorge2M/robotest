package com.mng.robotest.test80.mango.test.appshop;

import org.testng.annotations.*;

import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;
import com.mng.robotest.test80.access.InputParamsMango;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.data.PaisShop;
import com.mng.robotest.test80.mango.test.datastored.DataBag;
import com.mng.robotest.test80.mango.test.datastored.DataFavoritos;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.IdiomaPais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test80.mango.test.getdata.usuarios.GestorUsersShop;
import com.mng.robotest.test80.mango.test.getdata.usuarios.UserShop;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.KeyMenu1rstLevel;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.Menu1rstLevel;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.MenuTreeApp;
import com.mng.robotest.test80.mango.test.stpv.shop.AccesoStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.SecBolsaStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.favoritos.PageFavoritosStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.ficha.PageFichaArtStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.galeria.LocationArticle;
import com.mng.robotest.test80.mango.test.stpv.shop.galeria.PageGaleriaStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.galeria.PageGaleriaStpV.TypeActionFav;
import com.mng.robotest.test80.mango.test.stpv.shop.menus.SecMenusWrapperStpV;
import com.mng.robotest.test80.mango.test.utils.PaisGetter;

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
	private final static Pais españa = PaisGetter.get(PaisShop.España);

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
		InputParamsMango inputParamsSuite = (InputParamsMango)TestMaker.getTestCase().getInputParamsSuite();
		DataCtxShop dCtxSh = new DataCtxShop();
		dCtxSh.setAppEcom((AppEcom)inputParamsSuite.getApp());
		dCtxSh.setChannel(inputParamsSuite.getChannel());
		//dCtxSh.urlAcceso = inputParamsSuite.getUrlBase();

		//Si el acceso es normal (no es desde una @Factory) utilizaremos el España/Castellano
		if (this.paisFactory==null) {
			dCtxSh.pais = españa;
			dCtxSh.idioma = españa.getListIdiomas().get(0);
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

		AccesoStpV.oneStep(dCtxSh, false, driver);
    	SecBolsaStpV secBolsaStpV = new SecBolsaStpV(dCtxSh, driver);
		secBolsaStpV.clear();

		PageFavoritosStpV pageFavoritosStpV = PageFavoritosStpV.getNew(driver);
		pageFavoritosStpV.clearAll(dataFavoritos, dCtxSh);

		Menu1rstLevel menuVestidos = MenuTreeApp.getMenuLevel1From(dCtxSh.appE, KeyMenu1rstLevel.from(LineaType.she, null, "Vestidos"));
		SecMenusWrapperStpV secMenusStpV = SecMenusWrapperStpV.getNew(dCtxSh, driver);
		secMenusStpV.selectMenu1rstLevelTypeCatalog(menuVestidos, dCtxSh);

		PageGaleriaStpV pageGaleriaStpV = PageGaleriaStpV.getInstance(dCtxSh.channel, dCtxSh.appE, driver);
		List<Integer> iconsToMark = Arrays.asList(2, 3, 5);  
		pageGaleriaStpV.clickArticlesHearthIcons(iconsToMark, TypeActionFav.Marcar, dataFavoritos);

		List<Integer> iconsToUnmark = Arrays.asList(3);
		pageGaleriaStpV.clickArticlesHearthIcons(iconsToUnmark, TypeActionFav.Desmarcar, dataFavoritos);

		secMenusStpV.getMenusUser().selectFavoritos(dataFavoritos);
		ArticuloScreen artToPlay = dataFavoritos.getArticulo(0);
		pageFavoritosStpV.addArticuloToBag(artToPlay, dataBolsa, dCtxSh.channel, dCtxSh.appE, dCtxSh.pais);
		if (dCtxSh.channel.isDevice()) {
			secBolsaStpV.clickAspaForCloseMobil();
			pageFavoritosStpV.validaIsPageOK(dataFavoritos);
		}

		pageFavoritosStpV.clear(artToPlay, dataFavoritos);
		pageFavoritosStpV.clearAll(dataFavoritos, dCtxSh);
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

		AccesoStpV.oneStep(dCtxSh, false, driver);
		
		SecBolsaStpV secBolsaStpV = new SecBolsaStpV(dCtxSh, driver);
		secBolsaStpV.clear();

		PageFavoritosStpV pageFavoritosStpV = PageFavoritosStpV.getNew(driver);
		pageFavoritosStpV.clearAll(dataFavoritos, dCtxSh);


		SecMenusWrapperStpV secMenusStpV = SecMenusWrapperStpV.getNew(dCtxSh, driver);
		secMenusStpV.selectMenu1rstLevelTypeCatalog(getMenu(dCtxSh.appE), dCtxSh);
		LocationArticle article1 = LocationArticle.getInstanceInCatalog(1);

		PageGaleriaStpV pageGaleriaStpV = PageGaleriaStpV.getInstance(dCtxSh.channel, dCtxSh.appE, driver);
		pageGaleriaStpV.selectArticulo(article1, dCtxSh);

		PageFichaArtStpV pageFichaArtStpv = new PageFichaArtStpV(dCtxSh.appE, dCtxSh.channel, dCtxSh.pais);
		pageFichaArtStpv.selectAnadirAFavoritos(dataFavoritos);

		UserShop userShop = GestorUsersShop.checkoutBestUserForNewTestCase();
		dCtxSh.userConnected = userShop.user;
		dCtxSh.passwordUser = userShop.password;
		AccesoStpV.identificacionEnMango(dCtxSh, driver);
		//TestAB.activateTestABiconoBolsaDesktop(0, dCtxSh, dFTest.driver);
		
		secBolsaStpV.clear();
		secMenusStpV.getMenusUser().selectFavoritos(dataFavoritos);

		pageFavoritosStpV.clickShareIsOk();
		pageFavoritosStpV.closeShareModal();

		ArticuloScreen artToPlay = dataFavoritos.getArticulo(0);
		pageFavoritosStpV.clickArticuloImg(artToPlay);
		pageFavoritosStpV
			.getModalFichaFavoritosStpV()
			.addArticuloToBag(artToPlay, dataBolsa, dCtxSh.channel, dCtxSh.appE, dCtxSh.pais);
		
		if (dCtxSh.channel.isDevice()) {
			pageFavoritosStpV.validaIsPageOK(dataFavoritos);
		} else {
			pageFavoritosStpV.getModalFichaFavoritosStpV().closeFicha(artToPlay);
		}

		pageFavoritosStpV.clear(artToPlay, dataFavoritos);
	}
	
	private Menu1rstLevel getMenu(AppEcom app) {
		if (app==AppEcom.outlet) {
			return MenuTreeApp.getMenuLevel1From(app, KeyMenu1rstLevel.from(LineaType.she, null, "Vestidos"));
		}
		return MenuTreeApp.getMenuLevel1From(app, KeyMenu1rstLevel.from(LineaType.home, null, "Albornoces"));
	}
}