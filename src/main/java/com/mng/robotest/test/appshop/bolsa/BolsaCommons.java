package com.mng.robotest.test.appshop.bolsa;

import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.datastored.DataBag;
import com.mng.robotest.test.pageobject.shop.bolsa.SecBolsa.StateBolsa;
import com.mng.robotest.test.steps.shop.AccesoSteps;
import com.mng.robotest.test.steps.shop.SecBolsaSteps;

public class BolsaCommons extends StepBase {

	public void checkBolsa() throws Exception {
		SecBolsaSteps secBolsaSteps = new SecBolsaSteps();
		DataBag dataBag = new DataBag();
		
		new AccesoSteps().oneStep(dataTest.userRegistered);
		secBolsaSteps.altaArticlosConColores(2, dataBag);
		secBolsaSteps.forceStateBolsaTo(StateBolsa.CLOSED);
		secBolsaSteps.forceStateBolsaTo(StateBolsa.OPEN); 
		secBolsaSteps.clear1erArticuloBolsa(dataBag);								
		secBolsaSteps.altaArticlosConColores(1, dataBag);
		secBolsaSteps.click1erArticuloBolsa(dataBag);
	}
	
}
