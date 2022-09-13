package com.mng.robotest.domains.identification.tests;

import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.getdata.usuarios.GestorUsersShop;
import com.mng.robotest.test.getdata.usuarios.UserShop;
import com.mng.robotest.test.steps.shop.AccesoSteps;

public class Ses002 extends TestBase {

	public void execute() throws Exception {
		UserShop userShop = GestorUsersShop.checkoutBestUserForNewTestCase();
		dataTest.userConnected = userShop.user;
		dataTest.passwordUser = userShop.password;
		dataTest.userRegistered = true;
		new AccesoSteps().manySteps();
	}

}
