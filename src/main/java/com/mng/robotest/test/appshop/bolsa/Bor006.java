package com.mng.robotest.test.appshop.bolsa;

import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.getdata.usuarios.GestorUsersShop;
import com.mng.robotest.test.getdata.usuarios.UserShop;

public class Bor006 extends TestBase {

	@Override
	public void execute() throws Exception {
		UserShop userShop = GestorUsersShop.checkoutBestUserForNewTestCase();
		dataTest.userConnected = userShop.user;
		dataTest.passwordUser = userShop.password;
		dataTest.userRegistered = true;
		new BolsaCommons().checkBolsa();		
	}

}
