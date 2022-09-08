package com.mng.robotest.test.steps.navigations.shop;

import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.steps.shop.AllPagesSteps;
import com.mng.robotest.test.steps.shop.SecCabeceraSteps;
import com.mng.robotest.test.steps.shop.landing.PageLandingSteps;

public class NavigationsSteps extends StepBase {

	public void gotoPortada() throws Exception {
		SecCabeceraSteps secCabeceraSteps =	new SecCabeceraSteps();
		int i=0;
		while (!secCabeceraSteps.getSecCabecera().isPresentLogoMango(1) && i<5) {
			AllPagesSteps.backNagegador(driver);
			i+=1;
		}
		
		secCabeceraSteps.selecLogo();
		secCabeceraSteps.selecLogo();
		(new PageLandingSteps()).checkIsPage(5);
	}
	
}