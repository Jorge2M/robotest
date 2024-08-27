package com.mng.robotest.tests.domains.loyalty.tests.testslegacy;

import static com.mng.robotest.testslegacy.data.PaisShop.USA;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.landings.steps.LandingSteps;
import com.mng.robotest.tests.domains.transversal.browser.LocalStorageMango;
import com.mng.robotest.testslegacy.utils.PaisGetter;

public class Loy906 extends TestBase {

	public Loy906() {
		dataTest.setPais(PaisGetter.from(USA));
	}
	
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
