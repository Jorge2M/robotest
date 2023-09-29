package com.mng.robotest.testslegacy.steps.navigations.shop;

import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.transversal.cabecera.steps.SecCabeceraSteps;
import com.mng.robotest.tests.domains.transversal.home.steps.PageLandingSteps;

public class NavigationsSteps extends StepBase {

	public void gotoPortada() {
		var secCabeceraSteps =	new SecCabeceraSteps();
		int i=0;
		while (!secCabeceraSteps.getSecCabecera().isPresentLogoMango(1) && i<5) {
			back();
			i+=1;
		}
		
		secCabeceraSteps.selecLogo();
		secCabeceraSteps.selecLogo();
		(new PageLandingSteps()).checkIsPage(5);
	}
	
}
