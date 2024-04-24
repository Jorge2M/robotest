package com.mng.robotest.tests.domains.favoritos.tests;

import static com.mng.robotest.tests.domains.menus.pageobjects.GroupWeb.GroupType.*;
import static com.mng.robotest.tests.domains.menus.pageobjects.LineaWeb.LineaType.*;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.bolsa.steps.SecBolsaSteps;
import com.mng.robotest.tests.domains.favoritos.steps.PageFavoritosSteps;
import com.mng.robotest.tests.domains.ficha.steps.FichaSteps;
import com.mng.robotest.tests.domains.galeria.steps.GaleriaSteps;
import com.mng.robotest.tests.domains.menus.pageobjects.MenuWeb;
import com.mng.robotest.tests.domains.menus.steps.SecMenusUserSteps;
import com.mng.robotest.tests.domains.transversal.acceso.steps.AccesoSteps;
import com.mng.robotest.testslegacy.beans.IdiomaPais;
import com.mng.robotest.testslegacy.beans.Pais;

public class Fav002 extends TestBase {

	private final PageFavoritosSteps pageFavoritosSteps = new PageFavoritosSteps();
	private final SecBolsaSteps secBolsaSteps = new SecBolsaSteps();
	private final GaleriaSteps galeriaSteps = new GaleriaSteps();
	
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
		if (isOutlet()) {
			clickMenu("Vestidos");
		} else {
			clickMenu(new MenuWeb
				.Builder("Toallas")
				.linea(HOME)
				.group(BANO).build());
		}
		galeriaSteps.selectArticulo(1);
	}	

	private void clickFavoritesInFicha() {
		new FichaSteps().selectAnadirAFavoritos();
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
		var firstFavorite = dataTest.getDataFavoritos().getArticulo(0);
		pageFavoritosSteps.clickArticuloImg(firstFavorite);
		
		var fichaSteps = new FichaSteps();
		fichaSteps.selectFirstTallaAvailable();
		fichaSteps.selectAnadirALaBolsaStep();
	}

	private void clearFirstFavoriteFromFavorites() {
		var firstFavorite = dataTest.getDataFavoritos().getArticulo(0);
		new SecMenusUserSteps().selectFavoritos();
		pageFavoritosSteps.clear(firstFavorite);
	}	
}
