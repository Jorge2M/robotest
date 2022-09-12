package com.mng.robotest.domains.bolsa.tests;

import com.mng.robotest.domains.bolsa.pageobjects.SecBolsa.StateBolsa;
import com.mng.robotest.domains.bolsa.steps.SecBolsaSteps;
import com.mng.robotest.domains.transversal.NavigationBase;
import com.mng.robotest.test.datastored.DataBag;
import com.mng.robotest.test.steps.shop.AccesoSteps;

public class BolsaCommons extends NavigationBase {

	public void checkBolsa() throws Exception {
		new AccesoSteps().oneStep(dataTest.userRegistered);
		
		SecBolsaSteps secBolsaSteps = new SecBolsaSteps();
		DataBag dataBag = secBolsaSteps.altaArticlosConColores(2);
		secBolsaSteps.forceStateBolsaTo(StateBolsa.CLOSED);
		secBolsaSteps.forceStateBolsaTo(StateBolsa.OPEN); 
		secBolsaSteps.clear1erArticuloBolsa(dataBag);								
		dataBag = secBolsaSteps.altaArticlosConColores(1);
		secBolsaSteps.click1erArticuloBolsa(dataBag);
	}
	
}
