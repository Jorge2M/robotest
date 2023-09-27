package com.mng.robotest.domains.bolsa.tests;

import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.domains.bolsa.steps.SecBolsaSteps;
import com.mng.robotest.domains.transversal.acceso.steps.AccesoSteps;

import static com.mng.robotest.domains.bolsa.pageobjects.SecBolsaCommon.StateBolsa.*;

public class BolsaCommons extends StepBase {

	public void checkBolsa() throws Exception {
		new AccesoSteps().oneStep(dataTest.isUserRegistered());
		
		var secBolsaSteps = new SecBolsaSteps();
		secBolsaSteps.altaArticlosConColores(2);
		secBolsaSteps.forceStateBolsaTo(CLOSED);
		secBolsaSteps.forceStateBolsaTo(OPEN); 
		secBolsaSteps.clear1erArticuloBolsa();								
		secBolsaSteps.altaArticlosConColores(1);
		secBolsaSteps.click1erArticuloBolsa();
	}
	
}
