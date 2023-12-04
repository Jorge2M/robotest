package com.mng.robotest.tests.domains.compra.payments.paypal.steps;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.compra.payments.paypal.pageobjects.ModalPreloaderSpinner;

import static com.github.jorge2m.testmaker.conf.State.*;
import static com.github.jorge2m.testmaker.conf.StoreType.*;

public class ModalPreloaderSppinerSteps extends StepBase {
	
	private final ModalPreloaderSpinner mdPreloaderSpinner = new ModalPreloaderSpinner();
	
	public void validateAppearsAndDisappears() {
		validateIsVisible(2);
		validateIsVanished(10);
	}
	
	@Validation (
		description="Aparece el icono del candado de \"Cargando\" " + SECONDS_WAIT,
		level=INFO, store=NONE)
	public boolean validateIsVisible(int seconds) {
		return (mdPreloaderSpinner.isVisibleUntil(seconds));
	}
	
	@Validation (
		description="Desaparece el icono del candado de \"Cargando\" " + SECONDS_WAIT,
		level=INFO, store=NONE)
	public boolean validateIsVanished(int seconds) {
		return (mdPreloaderSpinner.isNotVisibleUntil(seconds));
	}
}
