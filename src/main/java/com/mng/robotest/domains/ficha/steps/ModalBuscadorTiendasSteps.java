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
		int seconds = 5;
	 	checks.add(
			"La capa de búsqueda es visible<br>" +
			"Incidencia PRO (https://jira.mango.com/browse/CLAV-3853)",
			modalBuscadorTiendas.isVisible(1), Warn);
	 	
	 	checks.add(
			"Se ha localizado alguna tienda (la esperamos hasta " + seconds + " segundos)",
			modalBuscadorTiendas.isPresentAnyTiendaUntil(seconds), Warn);
	 	
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
		return (!modalBuscadorTiendas.isVisible());
	}
}
