package com.mng.robotest.domains.otros.tests;

import com.mng.robotest.domains.base.TestBase;
import com.mng.robotest.domains.otros.steps.GoogleSteps;

public class Otr002 extends TestBase {

	@Override
	public void execute() throws Exception {
		GoogleSteps googleSteps = new GoogleSteps();
		googleSteps.accessGoogleAndSearchMango();
		googleSteps.selectFirstLinkSinPublicidad();		
	}
	
}
