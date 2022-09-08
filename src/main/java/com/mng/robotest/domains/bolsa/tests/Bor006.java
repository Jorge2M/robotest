package com.mng.robotest.domains.bolsa.tests;

import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.getdata.usuarios.GestorUsersShop;
import com.mng.robotest.test.getdata.usuarios.UserShop;

public class Bor006 extends TestBase {

	public Bor006() {
		UserShop userShop = GestorUsersShop.checkoutBestUserForNewTestCase();
		dataTest.userConnected = userShop.user;
		dataTest.passwordUser = userShop.password;
		dataTest.userRegistered = true;
	}
	
	@Override
	public void execute() throws Exception {
		new BolsaCommons().checkBolsa();		
	}

}