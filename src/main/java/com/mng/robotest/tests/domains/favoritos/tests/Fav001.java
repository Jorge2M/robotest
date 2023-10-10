package com.mng.robotest.tests.domains.favoritos.tests;

import static com.mng.robotest.tests.domains.galeria.steps.PageGaleriaSteps.TypeActionFav.*;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.bolsa.steps.SecBolsaSteps;
import com.mng.robotest.tests.domains.favoritos.steps.PageFavoritosSteps;
import com.mng.robotest.tests.domains.galeria.pageobjects.PageGaleriaDesktop.NumColumnas;
import com.mng.robotest.tests.domains.galeria.steps.PageGaleriaSteps;
import com.mng.robotest.tests.domains.transversal.menus.steps.SecMenusUserSteps;
import com.mng.robotest.testslegacy.beans.IdiomaPais;
import com.mng.robotest.testslegacy.beans.Pais;

public class Fav001 extends TestBase {

	private final PageFavoritosSteps pFavoritosSteps = new PageFavoritosSteps();
	private final PageGaleriaSteps pGaleriaSteps = new PageGaleriaSteps();
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
		if (channel==Channel.desktop) {
			pGaleriaSteps.selectListadoXColumnasDesktop(NumColumnas.CUATRO);
		}
	}	
	
	private void clickFavoritesInGalery() throws Exception {
		pGaleriaSteps.clickArticlesHearthIcons(MARCAR, 2, 3, 4);
		pGaleriaSteps.clickArticlesHearthIcons(DESMARCAR, 3);
	}	

	private void goToFavorites() {
		new SecMenusUserSteps().selectFavoritos();
	}	

	private void clearFirstFavorite() {
		var firstFavorite = dataTest.getDataFavoritos().getArticulo(0);
		pFavoritosSteps.clear(firstFavorite);
	}
	
	private void clearAllFavorites() {
		pFavoritosSteps.clearAll();		
	}
	
}