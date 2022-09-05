package com.mng.robotest.test.appshop.bolsa;

import com.mng.robotest.domains.transversal.TestBase;

public class Bor005 extends TestBase {

	@Override
	public void execute() throws Exception {
		dataTest.userRegistered = false;
		new BolsaCommons().checkBolsa();		
	}

}
