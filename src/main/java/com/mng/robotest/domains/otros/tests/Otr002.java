package com.mng.robotest.domains.otros.tests;

import com.mng.robotest.domains.otros.steps.GoogleSteps;
import com.mng.robotest.domains.transversal.TestBase;

public class Otr002 extends TestBase {

	@Override
	public void execute() throws Exception {
		GoogleSteps googleSteps = new GoogleSteps();
		googleSteps.accessGoogleAndSearchMango();
		googleSteps.selectFirstLinkSinPublicidad();		
	}
	
}
