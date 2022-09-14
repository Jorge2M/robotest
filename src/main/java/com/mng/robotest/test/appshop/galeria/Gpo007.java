package com.mng.robotest.test.appshop.galeria;

import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.getdata.usuarios.GestorUsersShop;
import com.mng.robotest.test.getdata.usuarios.UserShop;
import com.mng.robotest.test.steps.shop.galeria.ModalArticleNotAvailableSteps;
import com.mng.robotest.test.steps.shop.galeria.PageGaleriaSteps;

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
