package com.mng.robotest.tests.domains.bolsa.tests;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.bolsa.steps.BolsaSteps;
import com.mng.robotest.tests.domains.compra.beans.ConfigCheckout;
import com.mng.robotest.tests.domains.favoritos.steps.FavoritosSteps;
import com.mng.robotest.tests.domains.menus.steps.MenusUserSteps;
import com.mng.robotest.testslegacy.steps.navigations.shop.CheckoutFlow;
import com.mng.robotest.testslegacy.steps.navigations.shop.CheckoutFlow.From;

public class Bor002 extends TestBase {

	public Bor002() {
		dataTest.setUserRegistered(true);
	}
	
	@Override
	public void execute() throws Exception {
		accessAndSelectMenuVestidos();
		addBagArticleWithColors();
		if (!isOutlet()) {
			addFromBagToFavorites();
			addBagArticleWithColors();
		}
		navigateToCheckout();		
	}

	private void accessAndSelectMenuVestidos() throws Exception {
		loginAndClearData();
		clickMenu("Vestidos");
	}
	
	private void loginAndClearData() throws Exception {
		accessAndLogin();
		new BolsaSteps().clear();
		new FavoritosSteps().clearAll();
	}
	
	private void addBagArticleWithColors() throws Exception {
		new BolsaSteps().addArticlesWithColors(1);
	}
	
	private void addFromBagToFavorites() {
		var bolsaSteps = new BolsaSteps();
		bolsaSteps.addArticleToFavorites();
		bolsaSteps.checkBolsaIsVoid();
		goToFavorites();
	}
	
	private void goToFavorites() {
		new MenusUserSteps().selectFavoritos();
	}	
	
	private void navigateToCheckout() throws Exception {
		var configCheckout = ConfigCheckout.config().emaiExists().build();
		
		dataTest.setDataPago(configCheckout);
		new CheckoutFlow.BuilderCheckout(dataTest.getDataPago()).build()
			.checkout(From.BOLSA);
	}

}
