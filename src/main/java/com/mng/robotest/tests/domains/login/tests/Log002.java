package com.mng.robotest.tests.domains.login.tests;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.transversal.acceso.steps.AccesoSteps;

public class Log002 extends TestBase {

	public void execute() throws Exception {
		dataTest.setUserRegistered(true);
		new AccesoSteps().manySteps();
	}

}
