package com.mng.robotest.domains.manto.tests;

import com.mng.robotest.domains.base.TestMantoBase;
import com.mng.robotest.domains.manto.pageobjects.PageOrdenacionDePrendasSteps;
import com.mng.robotest.domains.manto.steps.PageMenusMantoSteps;

public class Man008 extends TestMantoBase {

	@Override
	public void execute() {
		accesoAlmacenEspanya();
		goToOrdenadorDePrendas();
		checkOrdenadorDePrendas();
	}
	
	private void goToOrdenadorDePrendas() {
		new PageMenusMantoSteps().goToOrdenadorDePrendas();		
	}
	
	private void checkOrdenadorDePrendas() {
		var pageOrdenacionDePrendasSteps = new PageOrdenacionDePrendasSteps();
		pageOrdenacionDePrendasSteps.mantoOrdenacionInicio();
		pageOrdenacionDePrendasSteps.mantoSeccionPrendas();
		pageOrdenacionDePrendasSteps.ordenacionModal();		
	}

}
