package com.mng.robotest.domains.favoritos.tests;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.favoritos.steps.PageFavoritosSteps;
import com.mng.robotest.domains.ficha.steps.PageFichaArtSteps;
import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.beans.Linea.LineaType;
import com.mng.robotest.test.datastored.DataBag;
import com.mng.robotest.test.datastored.DataFavoritos;
import com.mng.robotest.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test.getdata.usuarios.GestorUsersShop;
import com.mng.robotest.test.getdata.usuarios.UserShop;
import com.mng.robotest.test.pageobject.shop.menus.KeyMenu1rstLevel;
import com.mng.robotest.test.pageobject.shop.menus.Menu1rstLevel;
import com.mng.robotest.test.pageobject.shop.menus.MenuTreeApp;
import com.mng.robotest.test.steps.shop.AccesoSteps;
import com.mng.robotest.test.steps.shop.SecBolsaSteps;
import com.mng.robotest.test.steps.shop.galeria.LocationArticle;
import com.mng.robotest.test.steps.shop.galeria.PageGaleriaSteps;
import com.mng.robotest.test.steps.shop.menus.SecMenusWrapperSteps;


public class Fav002 extends TestBase {

	private final PageFavoritosSteps pageFavoritosSteps = new PageFavoritosSteps();
	private final SecBolsaSteps secBolsaSteps = new SecBolsaSteps(dataTest.pais);
	private final SecMenusWrapperSteps secMenusSteps = SecMenusWrapperSteps.getNew(dataTest);
	private final PageGaleriaSteps pageGaleriaSteps = PageGaleriaSteps.getInstance(channel, app, driver);
	
	private final DataFavoritos dataFavoritos = new DataFavoritos();
	private final DataBag dataBolsa = new DataBag();
	
	public Fav002(Pais pais, IdiomaPais idioma) throws Exception {
		super();
		dataTest.pais = pais;
		dataTest.idioma = idioma;
	}

	@Override
	public void execute() throws Exception {
		accessWithoutLoginAndClearData();
		goToGaleryAndSelectArticle();
		clickFavoritesInFicha();
		login();
		goToFavoritesAndCheckSharedFavorites();
		
		ArticuloScreen artToPlay = dataFavoritos.getArticulo(0);
		favoritesSelectionCheck(artToPlay);
		clearArticlesFromFavorites(artToPlay);		
	}

	private void clearArticlesFromFavorites(ArticuloScreen artToPlay) throws Exception {
		pageFavoritosSteps.clear(artToPlay, dataFavoritos);
	}

	private void favoritesSelectionCheck(ArticuloScreen artToPlay) throws Exception {
		pageFavoritosSteps.clickArticuloImg(artToPlay);
		pageFavoritosSteps
			.getModalFichaFavoritosSteps()
			.addArticuloToBag(artToPlay, dataBolsa, dataTest.pais);
		
		if (channel.isDevice()) {
			pageFavoritosSteps.validaIsPageOK(dataFavoritos);
		} else {
			pageFavoritosSteps.getModalFichaFavoritosSteps().closeFicha(artToPlay);
		}
	}

	private void goToFavoritesAndCheckSharedFavorites() throws Exception {
		secMenusSteps.getMenusUser().selectFavoritos(dataFavoritos);
		pageFavoritosSteps.clickShareIsOk();
		pageFavoritosSteps.closeShareModal();
	}

	private void login() throws Exception {
		UserShop userShop = GestorUsersShop.checkoutBestUserForNewTestCase();
		dataTest.userConnected = userShop.user;
		dataTest.passwordUser = userShop.password;
		new AccesoSteps().identificacionEnMango(dataTest);
		secBolsaSteps.clear();
	}

	private void clickFavoritesInFicha() throws Exception {
		PageFichaArtSteps pageFichaArtStpv = new PageFichaArtSteps(dataTest.pais);
		pageFichaArtStpv.selectAnadirAFavoritos(dataFavoritos);
	}

	private void goToGaleryAndSelectArticle() throws Exception {
		secMenusSteps.selectMenu1rstLevelTypeCatalog(getMenu(app), dataTest);
		LocationArticle article1 = LocationArticle.getInstanceInCatalog(1);
		pageGaleriaSteps.selectArticulo(article1, dataTest);
	}

	private void accessWithoutLoginAndClearData() throws Exception {
		dataTest.userRegistered=false;
		new AccesoSteps().oneStep(dataTest, false);
		secBolsaSteps.clear();
		pageFavoritosSteps.clearAll(dataFavoritos);
	}
	
	private Menu1rstLevel getMenu(AppEcom app) {
		if (app==AppEcom.outlet) {
			return MenuTreeApp.getMenuLevel1From(app, KeyMenu1rstLevel.from(LineaType.she, null, "Vestidos"));
		}
		return MenuTreeApp.getMenuLevel1From(app, KeyMenu1rstLevel.from(LineaType.home, null, "Albornoces"));
	}
	
}
