package com.mng.robotest.domains.ficha.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.pageobject.shop.modales.ModalBuscadorTiendas;


public class ModalBuscadorTiendasSteps extends StepBase {

	private final ModalBuscadorTiendas modalBuscadorTiendas;
	
	public ModalBuscadorTiendasSteps() {
		modalBuscadorTiendas = new ModalBuscadorTiendas(channel, app, driver);
	}
	
	@Validation
	public ChecksTM validaBusquedaConResultados() {
		ChecksTM checks = ChecksTM.getNew();
		int maxSeconds = 5;
	 	checks.add(
			"La capa de búsqueda es visible<br>" +
			"Incidencia PRO (https://jira.mango.com/browse/CLAV-3853)",
			modalBuscadorTiendas.isVisible(1), State.Warn);
	 	checks.add(
			"Se ha localizado alguna tienda (la esperamos hasta " + maxSeconds + " segundos)",
			modalBuscadorTiendas.isPresentAnyTiendaUntil(maxSeconds), State.Warn);
		return checks;
	}
	
	@Step (
		description="Cerramos la capa correspondiente al resultado del buscador", 
		expected="La capa correspondiente a la búsqueda desaparece")
	public void close() {
		modalBuscadorTiendas.clickAspaForClose();
		checkModalSearchInvisible();
	}
	
	@Validation (
		description="La capa correspondiente a la búsqueda desaparece",
		level=State.Defect)
	private boolean checkModalSearchInvisible() {
		return (!modalBuscadorTiendas.isVisible());
	}
}
