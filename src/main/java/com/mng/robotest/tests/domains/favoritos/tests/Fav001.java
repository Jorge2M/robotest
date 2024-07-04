package com.mng.robotest.tests.domains.favoritos.tests;

import static com.mng.robotest.tests.domains.galeria.steps.GaleriaSteps.TypeActionFav.*;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.bolsa.steps.SecBolsaSteps;
import com.mng.robotest.tests.domains.favoritos.steps.PageFavoritosSteps;
import com.mng.robotest.tests.domains.galeria.pageobjects.nogenesis.PageGaleriaDesktopBaseNoGenesis.NumColumnas;
import com.mng.robotest.tests.domains.galeria.steps.GaleriaSteps;
import com.mng.robotest.tests.domains.menus.steps.MenusUserSteps;
import com.mng.robotest.testslegacy.beans.IdiomaPais;
import com.mng.robotest.testslegacy.beans.Pais;

public class Fav001 extends TestBase {

	private final PageFavoritosSteps pFavoritosSteps = new PageFavoritosSteps();
	private final GaleriaSteps galeriaSteps = new GaleriaSteps();
	private final SecBolsaSteps secBolsaSteps = new SecBolsaSteps();
	
	public Fav001(Pais pais, IdiomaPais idioma) {
		super();
		dataTest.setPais(pais);
		dataTest.setIdioma(idioma);
	}
	
	@Override
	public void execute() throws Exception {
		loginAndClearData();
		goToVestidosGalery();
		clickFavoritesInGalery();
		goToFavorites();
		//addFirstFavoriteToBag();
		clearFirstFavorite();
		clearAllFavorites();
	}
	
	private void loginAndClearData() throws Exception {
		accessAndLogin();
		secBolsaSteps.clear();
		pFavoritosSteps.clearAll();
	}	
	
	private void goToVestidosGalery() {
		clickMenu("Vestidos");
		if (isDesktop()) {
			galeriaSteps.selectListadoXColumnasDesktop(NumColumnas.CUATRO);
		}
	}	
	
	private void clickFavoritesInGalery() throws Exception {
		galeriaSteps.clickArticlesHearthIcons(MARCAR, 2, 3, 4);
		galeriaSteps.clickArticlesHearthIcons(DESMARCAR, 3);
	}	

	private void goToFavorites() {
		new MenusUserSteps().selectFavoritos();
	}	

	private void clearFirstFavorite() {
		var firstFavorite = dataTest.getDataFavoritos().getArticulo(0);
		pFavoritosSteps.clear(firstFavorite);
	}
	
	private void clearAllFavorites() {
		pFavoritosSteps.clearAll();		
	}
	
}
