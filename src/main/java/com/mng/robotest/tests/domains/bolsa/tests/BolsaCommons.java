package com.mng.robotest.tests.domains.bolsa.tests;

import static com.mng.robotest.tests.domains.bolsa.pageobjects.SecBolsaBase.StateBolsa.*;

import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.bolsa.steps.BolsaSteps;
import com.mng.robotest.tests.domains.transversal.acceso.steps.AccesoSteps;

public class BolsaCommons extends StepBase {

	public void checkBolsa() throws Exception {
		new AccesoSteps().oneStep(dataTest.isUserRegistered());
		
		var secBolsaSteps = new BolsaSteps();
		secBolsaSteps.addArticlesWithColors(2);
		secBolsaSteps.forceStateBolsaTo(CLOSED);
		secBolsaSteps.forceStateBolsaTo(OPEN); 
		secBolsaSteps.clear1erArticuloBolsa();								
		secBolsaSteps.addArticlesWithColors(1);
		secBolsaSteps.click1erArticuloBolsa();
	}
	
}
