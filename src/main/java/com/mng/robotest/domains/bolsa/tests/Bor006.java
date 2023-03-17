package com.mng.robotest.domains.bolsa.tests;

import com.mng.robotest.domains.base.TestBase;

public class Bor006 extends TestBase {

	public Bor006() {
		dataTest.setUserRegistered(true);
	}
	
	@Override
	public void execute() throws Exception {
		new BolsaCommons().checkBolsa();		
	}

}
