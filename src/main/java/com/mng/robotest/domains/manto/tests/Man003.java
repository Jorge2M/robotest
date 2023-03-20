package com.mng.robotest.domains.manto.tests;

import com.mng.robotest.domains.base.TestMantoBase;
import com.mng.robotest.domains.manto.steps.PageGestorChequesSteps;
import com.mng.robotest.domains.manto.steps.PageMenusMantoSteps;

public class Man003 extends TestMantoBase {

	private static final String MAIL = "esther.esteve@mango.com";
	private static final String CHEQUE = "204028046151"; 
	
	@Override
	public void execute() {
		accesoAlmacenEspanya();
		goToGestorCheques();
		checkCheques();
	}
	
	private void goToGestorCheques() {
		new PageMenusMantoSteps().goToGestorCheques();		
	}
	
	private void checkCheques() {
		var pageGestChecksSteps = new PageGestorChequesSteps();
		pageGestChecksSteps.inputMailAndClickCorreoCliente(MAIL);
		pageGestChecksSteps.clickPedido(10, MAIL);
		pageGestChecksSteps.volverCheques();
		pageGestChecksSteps.inputCheque(CHEQUE);
		pageGestChecksSteps.chequeDetails();
		pageGestChecksSteps.volverCheques();
	}

}
