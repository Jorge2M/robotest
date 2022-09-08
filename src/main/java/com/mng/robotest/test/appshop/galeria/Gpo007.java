package com.mng.robotest.test.appshop.galeria;

import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.beans.Linea.LineaType;
import com.mng.robotest.test.getdata.usuarios.GestorUsersShop;
import com.mng.robotest.test.getdata.usuarios.UserShop;
import com.mng.robotest.test.pageobject.shop.menus.KeyMenu1rstLevel;
import com.mng.robotest.test.pageobject.shop.menus.Menu1rstLevel;
import com.mng.robotest.test.pageobject.shop.menus.MenuTreeApp;
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

		access();
		clickMenu("camisas");
		
		new PageGaleriaSteps().selectTallaNoDisponibleArticulo();
		ModalArticleNotAvailableSteps modalArticleNotAvailableSteps = new ModalArticleNotAvailableSteps();
		modalArticleNotAvailableSteps.checkVisibleAvisame();		
	}

}
