package com.mng.robotest.tests.domains.bolsa.tests;

import com.mng.robotest.tests.domains.base.TestBase;

public class Bor005 extends TestBase {

	@Override
	public void execute() throws Exception {
		new BolsaCommons().checkBolsa();		
	}

}
