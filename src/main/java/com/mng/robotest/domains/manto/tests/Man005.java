package com.mng.robotest.domains.manto.tests;

import com.mng.robotest.domains.base.TestMantoBase;
import com.mng.robotest.domains.manto.steps.PageMenusMantoSteps;
import com.mng.robotest.domains.manto.steps.PageOrdenacionDePrendasSteps;

public class Man005 extends TestMantoBase {

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
		pageOrdenacionDePrendasSteps.mantoSeccionNuevo();
		pageOrdenacionDePrendasSteps.ordenacionModal();		
	}

}
