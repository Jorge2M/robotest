package com.mng.robotest.domains.ficha.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.test.pageobject.shop.modales.ModalBuscadorTiendas;

import static com.github.jorge2m.testmaker.conf.State.*;

public class ModalBuscadorTiendasSteps extends StepBase {

	private final ModalBuscadorTiendas modalBuscadorTiendas = new ModalBuscadorTiendas();
	
	@Validation
	public ChecksTM validaBusquedaConResultados() {
		var checks = ChecksTM.getNew();
		int seconds = 3;
	 	checks.add(
			"La capa de búsqueda es visible<br> " + getLitSecondsWait(seconds),
			modalBuscadorTiendas.isVisible(seconds), Warn);
	 	
	 	var state = isPRO()? Defect:Warn;
	 	checks.add(
			"Se ha localizado alguna tienda " + getLitSecondsWait(seconds),
			modalBuscadorTiendas.isPresentAnyTiendaUntil(seconds), state);
	 	
		return checks;
	}
	
	@Step (
		description="Cerramos la capa correspondiente al resultado del buscador", 
		expected="La capa correspondiente a la búsqueda desaparece")
	public void close() {
		modalBuscadorTiendas.close();
		checkModalSearchInvisible();
	}
	
	@Validation (description="La capa correspondiente a la búsqueda desaparece")
	private boolean checkModalSearchInvisible() {
		return modalBuscadorTiendas.isInvisible(1);
	}
}
