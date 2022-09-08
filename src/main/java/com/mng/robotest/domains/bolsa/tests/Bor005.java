package com.mng.robotest.domains.bolsa.tests;

import com.mng.robotest.domains.transversal.TestBase;

public class Bor005 extends TestBase {

	@Override
	public void execute() throws Exception {
		new BolsaCommons().checkBolsa();		
	}

}
