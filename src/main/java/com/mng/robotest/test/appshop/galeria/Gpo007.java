package com.mng.robotest.test.appshop.galeria;

import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.beans.Linea.LineaType;
import com.mng.robotest.test.getdata.usuarios.GestorUsersShop;
import com.mng.robotest.test.getdata.usuarios.UserShop;
import com.mng.robotest.test.pageobject.shop.menus.KeyMenu1rstLevel;
import com.mng.robotest.test.pageobject.shop.menus.Menu1rstLevel;
import com.mng.robotest.test.pageobject.shop.menus.MenuTreeApp;
import com.mng.robotest.test.steps.shop.AccesoSteps;
import com.mng.robotest.test.steps.shop.galeria.ModalArticleNotAvailableSteps;
import com.mng.robotest.test.steps.shop.galeria.PageGaleriaSteps;
import com.mng.robotest.test.steps.shop.menus.SecMenusWrapperSteps;

public class Gpo007 extends TestBase {

	@Override
	public void execute() throws Exception {
		UserShop userShop = GestorUsersShop.checkoutBestUserForNewTestCase();
		dataTest.userConnected = userShop.user;
		dataTest.passwordUser = userShop.password;
		dataTest.userRegistered = true;

		// Abrir listado de mujer camisas
		new AccesoSteps().oneStep(false);
		Menu1rstLevel menuCamisas = MenuTreeApp.getMenuLevel1From(app, KeyMenu1rstLevel.from(LineaType.she, null, "camisas"));
		SecMenusWrapperSteps secMenusSteps = new SecMenusWrapperSteps();
		secMenusSteps.selectMenu1rstLevelTypeCatalog(menuCamisas);

		// Abrir avisame desde el listado buscando primera talla sin stock y comprobar que se abierto y que contiene texto RGPD
		new PageGaleriaSteps().selectTallaNoDisponibleArticulo();
		ModalArticleNotAvailableSteps modalArticleNotAvailableSteps = ModalArticleNotAvailableSteps.getInstance(channel, app, driver);
		modalArticleNotAvailableSteps.checkVisibleAvisame();		
	}

}
