package com.mng.robotest.tests.domains.loyalty.tests;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.landings.steps.LandingSteps;
import com.mng.robotest.tests.domains.transversal.browser.LocalStorageMango;

public class Loy006 extends TestBase {

	@Override
	public void execute() throws Exception {
		access();
		setInitialModalsOn();
		checkNoMangoLikesYouElements();
	}

	private void setInitialModalsOn() {
		new LocalStorageMango().setInitialModalsOn();
		refreshPage();		
	}
	
	private void checkNoMangoLikesYouElements() {
		var pageLandingSteps = new LandingSteps(); 
		pageLandingSteps.isInvisibleCommsHeaderBannerLoyalty(5);
		pageLandingSteps.isInvisibleAnyElementLoyalty();
	}

}
