package com.mng.robotest.domains.compra.payments.paypal.steps;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.StoreType;
import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.domains.compra.payments.paypal.pageobjects.ModalPreloaderSpinner;

import static com.github.jorge2m.testmaker.conf.State.*;

public class ModalPreloaderSppinerSteps extends StepBase {
	
	private final ModalPreloaderSpinner modalPreloaderSpinner = new ModalPreloaderSpinner();
	
	public void validateAppearsAndDisappears() {
		validateIsVisible(2);
		validateIsVanished(10);
	}
	
	@Validation (
		description="Aparece el icono del candado de \"Cargando\" (lo esperamos un máximo de #{seconds})",
		level=Info,
		store=StoreType.None)
	public boolean validateIsVisible(int seconds) {
		return (modalPreloaderSpinner.isVisibleUntil(seconds));
	}
	
	@Validation (
		description="Desaparece el icono del candado de \"Cargando\" (lo esperamos un máximo de #{seconds})",
		level=Info,
		store=StoreType.None)
	public boolean validateIsVanished(int seconds) {
		return (modalPreloaderSpinner.isNotVisibleUntil(seconds));
	}
}
