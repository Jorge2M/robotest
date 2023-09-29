package com.mng.robotest.tests.domains.ficha.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.ficha.pageobjects.ModEnvioYdevolNew;

import static com.github.jorge2m.testmaker.conf.State.*;

public class ModEnvioYdevolNewSteps extends StepBase {

	private final ModEnvioYdevolNew modEnvioYdev = new ModEnvioYdevolNew();
	
	@Validation (
		description="Aparece el modal con los datos a nivel de envío y devolución " + SECONDS_WAIT)
	public boolean checkIsVisible(int seconds) {
		return modEnvioYdev.isVisible(seconds);
	}

	@Step (
		description="Seleccionar el aspa para cerrar el modal de \"Envío y devolución\"",
		expected="Desaparece el modal")
	public void clickAspaForClose() {
		modEnvioYdev.clickAspaForClose();
		checkIsVisibleModalDatosEnvio();
	}
	
	@Validation (
		description="No es visible el modal con los datos a nivel de envío y devolución",
		level=Warn)
	private boolean checkIsVisibleModalDatosEnvio() {
		return !modEnvioYdev.isVisible(1);
	}
}
