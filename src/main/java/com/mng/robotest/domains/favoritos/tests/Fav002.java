package com.mng.robotest.domains.favoritos.tests;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.bolsa.steps.SecBolsaSteps;
import com.mng.robotest.domains.favoritos.steps.PageFavoritosSteps;
import com.mng.robotest.domains.ficha.steps.PageFichaSteps;
import com.mng.robotest.domains.galeria.steps.LocationArticle;
import com.mng.robotest.domains.galeria.steps.PageGaleriaSteps;
import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.domains.transversal.menus.pageobjects.MenuWeb;
import com.mng.robotest.domains.transversal.menus.steps.SecMenusUserSteps;
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test.steps.shop.AccesoSteps;

import static com.mng.robotest.domains.transversal.menus.pageobjects.LineaWeb.LineaType.*;
import static com.mng.robotest.domains.transversal.menus.pageobjects.GroupWeb.GroupType.*;

public class Fav002 extends TestBase {

	private final PageFavoritosSteps pageFavoritosSteps = new PageFavoritosSteps();
	private final SecBolsaSteps secBolsaSteps = new SecBolsaSteps();
	private final PageGaleriaSteps pageGaleriaSteps = new PageGaleriaSteps();
	
	public Fav002(Pais pais, IdiomaPais idioma) {
		super();
		dataTest.setPais(pais);
		dataTest.setIdioma(idioma);
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
		dataTest.setUserRegistered(false);
		access();
		secBolsaSteps.clear();
		pageFavoritosSteps.clearAll();
	}	

	private void goToGaleryAndSelectArticle() {
		if (app==AppEcom.outlet) {
			clickMenu("Vestidos");
		} else {
			clickMenu(new MenuWeb
					.Builder("Albornoces")
					.linea(home)
					.group(BANO).build());
		}
		LocationArticle article1 = LocationArticle.getInstanceInCatalog(1);
		pageGaleriaSteps.selectArticulo(article1);
	}	

	private void clickFavoritesInFicha() {
		PageFichaSteps pageFichaArtStpv = new PageFichaSteps();
		pageFichaArtStpv.selectAnadirAFavoritos();
	}	
	
	private void login() throws Exception {
		new AccesoSteps().identificacionEnMango();
		secBolsaSteps.clear();
	}	
	
	private void goToFavoritesAndCheckSharedFavorites() {
		new SecMenusUserSteps().selectFavoritos();
		pageFavoritosSteps.clickShareIsOk();
		pageFavoritosSteps.closeShareModal();
	}	
	
	private void selectFirstFavoriteAndAddBolsa() {
		ArticuloScreen firstFavorite = dataTest.getDataFavoritos().getArticulo(0);
		pageFavoritosSteps.clickArticuloImg(firstFavorite);
		
		var pageFichaSteps = new PageFichaSteps();
		pageFichaSteps.selectFirstTallaAvailable();
		pageFichaSteps.selectAnadirALaBolsaStep();
	}

	private void clearFirstFavoriteFromFavorites() {
		ArticuloScreen firstFavorite = dataTest.getDataFavoritos().getArticulo(0);
		new SecMenusUserSteps().selectFavoritos();
		pageFavoritosSteps.clear(firstFavorite);
	}	
}
