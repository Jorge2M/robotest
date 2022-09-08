package com.mng.robotest.domains.bolsa.tests;

import com.mng.robotest.domains.bolsa.pageobjects.SecBolsa.StateBolsa;
import com.mng.robotest.domains.bolsa.steps.SecBolsaSteps;
import com.mng.robotest.domains.transversal.NavigationBase;
import com.mng.robotest.test.datastored.DataBag;
import com.mng.robotest.test.steps.shop.AccesoSteps;

public class BolsaCommons extends NavigationBase {

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
