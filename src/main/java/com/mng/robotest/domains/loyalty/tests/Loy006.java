package com.mng.robotest.domains.loyalty.tests;

import com.mng.robotest.domains.base.TestBase;
import com.mng.robotest.domains.transversal.browser.LocalStorageMango;
import com.mng.robotest.domains.transversal.home.steps.PageLandingSteps;

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
		var pageLandingSteps = new PageLandingSteps(); 
		pageLandingSteps.isInvisibleCommsHeaderBannerLoyalty(5);
		pageLandingSteps.isInvisibleAnyElementLoyalty();
	}

}
