package com.mng.robotest.domains.favoritos.tests;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.bolsa.steps.SecBolsaSteps;
import com.mng.robotest.domains.favoritos.steps.PageFavoritosSteps;
import com.mng.robotest.domains.ficha.steps.PageFichaSteps;
import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.beans.Linea.LineaType;
import com.mng.robotest.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test.getdata.usuarios.GestorUsersShop;
import com.mng.robotest.test.getdata.usuarios.UserShop;
import com.mng.robotest.test.steps.shop.AccesoSteps;
import com.mng.robotest.test.steps.shop.galeria.LocationArticle;
import com.mng.robotest.test.steps.shop.galeria.PageGaleriaSteps;
import com.mng.robotest.test.steps.shop.menus.SecMenusWrapperSteps;

public class Fav002 extends TestBase {

	private final PageFavoritosSteps pageFavoritosSteps = new PageFavoritosSteps();
	private final SecBolsaSteps secBolsaSteps = new SecBolsaSteps();
	private final PageGaleriaSteps pageGaleriaSteps = new PageGaleriaSteps();
	private final SecMenusWrapperSteps secMenusSteps = new SecMenusWrapperSteps();
	
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
		selectFirstFavoriteAndAddBolsa();
		clearFirstFavoriteFromFavorites();		
	}
	
	private void accessWithoutLoginAndClearData() throws Exception {
		dataTest.userRegistered=false;
		access();
		secBolsaSteps.clear();
		pageFavoritosSteps.clearAll();
	}	

	private void goToGaleryAndSelectArticle() throws Exception {
		if (app==AppEcom.outlet) {
			clickMenu("Vestidos");
		} else {
			clickMenu(LineaType.home, null, "Albornoces");
		}
		LocationArticle article1 = LocationArticle.getInstanceInCatalog(1);
		pageGaleriaSteps.selectArticulo(article1);
	}	

	private void clickFavoritesInFicha() throws Exception {
		PageFichaSteps pageFichaArtStpv = new PageFichaSteps();
		pageFichaArtStpv.selectAnadirAFavoritos();
	}	
	
	private void login() throws Exception {
		UserShop userShop = GestorUsersShop.checkoutBestUserForNewTestCase();
		dataTest.userConnected = userShop.user;
		dataTest.passwordUser = userShop.password;
		new AccesoSteps().identificacionEnMango();
		secBolsaSteps.clear();
	}	
	
	private void goToFavoritesAndCheckSharedFavorites() throws Exception {
		secMenusSteps.getMenusUser().selectFavoritos();
		pageFavoritosSteps.clickShareIsOk();
		pageFavoritosSteps.closeShareModal();
	}	
	
	private void selectFirstFavoriteAndAddBolsa() throws Exception {
		ArticuloScreen firstFavorite = dataTest.dataFavoritos.getArticulo(0);
		pageFavoritosSteps.clickArticuloImg(firstFavorite);
		new PageFichaSteps().selectAnadirALaBolsaStep();
	}

	private void clearFirstFavoriteFromFavorites() throws Exception {
		ArticuloScreen firstFavorite = dataTest.dataFavoritos.getArticulo(0);
		secMenusSteps.getMenusUser().selectFavoritos();
		pageFavoritosSteps.clear(firstFavorite);
	}	
}
