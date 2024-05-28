package com.mng.robotest.tests.domains.ficha.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.testslegacy.pageobject.shop.modales.buscarentienda.ModalBuscarEnTienda;

import static com.github.jorge2m.testmaker.conf.State.*;

public class ModalBuscarEnTiendaSteps extends StepBase {

	private final ModalBuscarEnTienda modalBuscarEnTienda = ModalBuscarEnTienda.make(app, dataTest.getPais());
	
	@Validation
	public ChecksTM checkBusquedaConResultados() {
		var checks = ChecksTM.getNew();
		int seconds = 5;
	 	checks.add(
			"La capa de búsqueda es visible<br> " + getLitSecondsWait(seconds),
			modalBuscarEnTienda.isVisible(seconds), WARN);
	 	
	 	var state = isPRO()? DEFECT:WARN;
	 	checks.add(
			"Se ha localizado alguna tienda " + getLitSecondsWait(seconds),
			modalBuscarEnTienda.isPresentAnyTiendaUntil(seconds), state);
	 	
		return checks;
	}
	
	@Step (
		description="Cerramos la capa correspondiente al resultado del buscador", 
		expected="La capa correspondiente a la búsqueda desaparece")
	public void close() {
		modalBuscarEnTienda.close();
		checkModalSearchInvisible();
	}
	
	@Validation (description="La capa correspondiente a la búsqueda desaparece")
	private boolean checkModalSearchInvisible() {
		return modalBuscarEnTienda.isInvisible(1);
	}
}
