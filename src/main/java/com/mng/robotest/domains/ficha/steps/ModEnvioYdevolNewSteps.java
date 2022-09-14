package com.mng.robotest.domains.ficha.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.domains.ficha.pageobjects.ModEnvioYdevolNew;
import com.mng.robotest.domains.transversal.StepBase;


public class ModEnvioYdevolNewSteps extends StepBase {

	private final ModEnvioYdevolNew modEnvioYdev = new ModEnvioYdevolNew();
	
	@Validation (
		description="Aparece el modal con los datos a nivel de envío y devolución",
		level=State.Defect)
	public boolean checkIsVisible() {
		int secondsToWait = 1;
		return (modEnvioYdev.isVisibleUntil(secondsToWait));
	}

	@Step (
		description="Seleccionar el aspa para cerrar el modal de \"Envío y devolución\"",
		expected="Desaparece el modal")
	public void clickAspaForClose() throws Exception {
		modEnvioYdev.clickAspaForClose();
		checkIsVisibleModalDatosEnvio();
	}
	
	@Validation (
		description="No es visible el modal con los datos a nivel de envío y devolución",
		level=State.Warn)
	private boolean checkIsVisibleModalDatosEnvio() {
		int secondsToWait = 1;
		return (!modEnvioYdev.isVisibleUntil(secondsToWait));
	}
}
