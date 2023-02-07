package com.mng.robotest.domains.login.tests;

import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.steps.shop.AccesoSteps;

public class Ses002 extends TestBase {

	public void execute() throws Exception {
		dataTest.setUserRegistered(true);
		new AccesoSteps().manySteps();
	}

}
