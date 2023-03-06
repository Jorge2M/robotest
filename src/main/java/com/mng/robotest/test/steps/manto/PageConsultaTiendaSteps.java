package com.mng.robotest.test.steps.manto;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.pageobject.manto.PageConsultaTienda;
import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;


public class PageConsultaTiendaSteps extends StepBase {

	private final PageConsultaTienda pageConsultaTienda = new PageConsultaTienda();
	
	@Validation (
		description="Es visible el input para la introducción de la tienda",
		level=State.Defect)
	public boolean validateIsPage() {
		return pageConsultaTienda.isVisibleInputTienda();
	}

	@Step (
		description="Introducimos tienda #{tiendaNoExistente}</br>",
		expected="No debe ser válida",
		saveErrorData=SaveWhen.Never)
	public void consultaTiendaInexistente(String tiendaNoExistente) {
		pageConsultaTienda.introducirTienda(tiendaNoExistente);
		checkIsVisibleMessageTiendaNotExits();
	}
	
	@Validation (
		description="Aparece el mensaje La tienda no existe",
		level=State.Defect)
	private boolean checkIsVisibleMessageTiendaNotExits() {
		return pageConsultaTienda.apareceMensajeTiendaNoExiste();
	}

	@Step (
		description="Introducimos tienda <b>#{tiendaExistente}</b>",
		expected="No debe ser válida",
		saveErrorData=SaveWhen.Never)
	public void consultaTiendaExistente(String tiendaExistente) {
		pageConsultaTienda.introducirTienda(tiendaExistente);
		checkAfterInputTienda();
	}
	
	@Validation
	private ChecksTM checkAfterInputTienda() {
		var checks = ChecksTM.getNew();
	 	checks.add(
			"Aparece la información de la tienda",
			pageConsultaTienda.apareceInformacionTienda(), State.Defect);
	 	
	 	checks.add(
			"No aparece el mensaje de tienda no existe",
			!pageConsultaTienda.apareceMensajeTiendaNoExiste(), State.Defect);
	 	
	 	return checks;
	}
}
