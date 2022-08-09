package com.mng.robotest.domains.favoritos.tests;

import java.util.Arrays;
import java.util.List;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.domains.favoritos.steps.PageFavoritosSteps;
import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.beans.Linea.LineaType;
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
import com.mng.robotest.test.steps.shop.galeria.PageGaleriaSteps;
import com.mng.robotest.test.steps.shop.galeria.PageGaleriaSteps.TypeActionFav;
import com.mng.robotest.test.steps.shop.menus.SecMenusWrapperSteps;


public class Fav001 extends TestBase {

	private final PageFavoritosSteps pageFavoritosSteps = new PageFavoritosSteps();
	private final PageGaleriaSteps pageGaleriaSteps = PageGaleriaSteps.getInstance(channel, app, driver);
	private final SecBolsaSteps secBolsaSteps;
	private final SecMenusWrapperSteps secMenusSteps;
	
	private final DataFavoritos dataFavoritos = new DataFavoritos();
	private final DataBag dataBolsa = new DataBag();
	
	public Fav001(Pais pais, IdiomaPais idioma) throws Exception {
		super();
		dataTest.pais = pais;
		dataTest.idioma = idioma;
		
		UserShop userShop = GestorUsersShop.checkoutBestUserForNewTestCase();
		dataTest.userConnected = userShop.user;
		dataTest.passwordUser = userShop.password;
		dataTest.userRegistered=true;
		
		secBolsaSteps = new SecBolsaSteps(dataTest);
		secMenusSteps = SecMenusWrapperSteps.getNew(dataTest);
	}
	
	@Override
	public void execute() throws Exception {
		loginAndClearData();
		goToVestidosGalery();
		clickFavoritesInGalery();
		goToFavorites();
		ArticuloScreen artToPlay = dataFavoritos.getArticulo(0);
		addFavoriteToBag(artToPlay);
		clearArticlesFromFavorites(artToPlay);		
	}

	private void clearArticlesFromFavorites(ArticuloScreen artToPlay) throws Exception {
		pageFavoritosSteps.clear(artToPlay, dataFavoritos);
		pageFavoritosSteps.clearAll(dataFavoritos);
	}

	private void addFavoriteToBag(ArticuloScreen artToPlay) throws Exception {
		pageFavoritosSteps.addArticuloToBag(artToPlay, dataBolsa, dataTest.pais);
		if (channel.isDevice()) {
			secBolsaSteps.clickAspaForCloseMobil();
			pageFavoritosSteps.validaIsPageOK(dataFavoritos);
		}
	}

	private void goToFavorites() throws Exception {
		secMenusSteps.getMenusUser().selectFavoritos(dataFavoritos);
	}

	private void clickFavoritesInGalery() throws Exception {
		List<Integer> iconsToMark = Arrays.asList(2, 3, 5);  
		pageGaleriaSteps.clickArticlesHearthIcons(iconsToMark, TypeActionFav.MARCAR, dataFavoritos);

		List<Integer> iconsToUnmark = Arrays.asList(3);
		pageGaleriaSteps.clickArticlesHearthIcons(iconsToUnmark, TypeActionFav.DESMARCAR, dataFavoritos);
	}

	private void goToVestidosGalery() throws Exception {
		Menu1rstLevel menuVestidos = MenuTreeApp.getMenuLevel1From(app, KeyMenu1rstLevel.from(LineaType.she, null, "Vestidos"));
		secMenusSteps.selectMenu1rstLevelTypeCatalog(menuVestidos, dataTest);
		if (channel==Channel.desktop) {
			pageGaleriaSteps.selectListadoXColumnasDesktop(NumColumnas.CUATRO);
		}
	}

	private void loginAndClearData() throws Exception {
		AccesoSteps.oneStep(dataTest, false, driver);
		secBolsaSteps.clear();
		pageFavoritosSteps.clearAll(dataFavoritos);
	}

}
