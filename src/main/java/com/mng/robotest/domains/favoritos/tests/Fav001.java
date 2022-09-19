package com.mng.robotest.domains.favoritos.tests;

import java.util.Arrays;
import java.util.List;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.domains.bolsa.steps.SecBolsaSteps;
import com.mng.robotest.domains.favoritos.steps.PageFavoritosSteps;
import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test.getdata.usuarios.GestorUsersShop;
import com.mng.robotest.test.getdata.usuarios.UserShop;
import com.mng.robotest.test.pageobject.shop.galeria.PageGaleriaDesktop.NumColumnas;
import com.mng.robotest.test.steps.shop.galeria.PageGaleriaSteps;
import com.mng.robotest.test.steps.shop.galeria.PageGaleriaSteps.TypeActionFav;
import com.mng.robotest.test.steps.shop.menus.SecMenusWrapperSteps;

public class Fav001 extends TestBase {

	private final PageFavoritosSteps pageFavoritosSteps = new PageFavoritosSteps();
	private final PageGaleriaSteps pageGaleriaSteps = new PageGaleriaSteps();
	private final SecBolsaSteps secBolsaSteps = new SecBolsaSteps();
	private final SecMenusWrapperSteps secMenusSteps = new SecMenusWrapperSteps();
	
	public Fav001(Pais pais, IdiomaPais idioma) throws Exception {
		super();
		dataTest.pais = pais;
		dataTest.idioma = idioma;
		
		UserShop userShop = GestorUsersShop.checkoutBestUserForNewTestCase();
		dataTest.userConnected = userShop.user;
		dataTest.passwordUser = userShop.password;
		dataTest.userRegistered=true;
	}
	
	@Override
	public void execute() throws Exception {
		loginAndClearData();
		goToVestidosGalery();
		clickFavoritesInGalery();
		goToFavorites();
		addFirstFavoriteToBag();
		clearFirstFavorite();		
	}
	
	private void loginAndClearData() throws Exception {
		access();
		secBolsaSteps.clear();
		pageFavoritosSteps.clearAll();
	}	
	
	private void goToVestidosGalery() throws Exception {
		clickMenu("Vestidos");
		if (channel==Channel.desktop) {
			pageGaleriaSteps.selectListadoXColumnasDesktop(NumColumnas.CUATRO);
		}
	}	
	
	private void clickFavoritesInGalery() throws Exception {
		List<Integer> iconsToMark = Arrays.asList(2, 3, 5);  
		pageGaleriaSteps.clickArticlesHearthIcons(iconsToMark, TypeActionFav.MARCAR);

		List<Integer> iconsToUnmark = Arrays.asList(3);
		pageGaleriaSteps.clickArticlesHearthIcons(iconsToUnmark, TypeActionFav.DESMARCAR);
	}	

	private void goToFavorites() throws Exception {
		secMenusSteps.getMenusUser().selectFavoritos();
	}	

	private void addFirstFavoriteToBag() throws Exception {
		ArticuloScreen firstFavorite = dataTest.dataFavoritos.getArticulo(0);
		pageFavoritosSteps.addArticuloToBag(firstFavorite);
		if (channel.isDevice()) {
			secBolsaSteps.closeInMobil();
			pageFavoritosSteps.validaIsPageOK();
		}
	}	
	
	private void clearFirstFavorite() throws Exception {
		ArticuloScreen firstFavorite = dataTest.dataFavoritos.getArticulo(0);
		pageFavoritosSteps.clear(firstFavorite);
		pageFavoritosSteps.clearAll();
	}
}
