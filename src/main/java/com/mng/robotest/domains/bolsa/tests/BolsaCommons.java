package com.mng.robotest.domains.bolsa.tests;

import com.mng.robotest.domains.bolsa.pageobjects.SecBolsaCommon.StateBolsa;
import com.mng.robotest.domains.bolsa.steps.SecBolsaSteps;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.steps.shop.AccesoSteps;

public class BolsaCommons extends StepBase {

	public void checkBolsa() throws Exception {
		new AccesoSteps().oneStep(dataTest.isUserRegistered());
		
		SecBolsaSteps secBolsaSteps = new SecBolsaSteps();
		secBolsaSteps.altaArticlosConColores(2);
		secBolsaSteps.forceStateBolsaTo(StateBolsa.CLOSED);
		secBolsaSteps.forceStateBolsaTo(StateBolsa.OPEN); 
		secBolsaSteps.clear1erArticuloBolsa();								
		secBolsaSteps.altaArticlosConColores(1);
		secBolsaSteps.click1erArticuloBolsa();
	}
	
}
