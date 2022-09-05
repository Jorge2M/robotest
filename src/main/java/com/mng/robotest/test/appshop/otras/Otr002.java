package com.mng.robotest.test.appshop.otras;

import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.steps.otras.GoogleSteps;

public class Otr002 extends TestBase {

	@Override
	public void execute() throws Exception {
		GoogleSteps googleSteps = new GoogleSteps();
		googleSteps.accessGoogleAndSearchMango();
		googleSteps.selectFirstLinkSinPublicidad();		
	}
	
}
