package com.mng.robotest.tests.domains.otros.tests;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.otros.steps.GoogleSteps;

public class Otr002 extends TestBase {

	@Override
	public void execute() throws Exception {
		var googleSteps = new GoogleSteps();
		googleSteps.accessGoogleAndSearchMango();
		googleSteps.selectFirstLinkSinPublicidad();		
	}
	
}
