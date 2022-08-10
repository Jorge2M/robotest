package com.mng.robotest.test.steps.shop.checkout.paypal;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.conf.StoreType;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.pageobject.shop.checkout.paypal.ModalPreloaderSpinner;


public class ModalPreloaderSppinerSteps extends StepBase {
	
	private final ModalPreloaderSpinner modalPreloaderSpinner = new ModalPreloaderSpinner();
	
	public void validateAppearsAndDisappears() {
		validateIsVisible(2);
		validateIsVanished(10);
	}
	
	@Validation (
		description="Aparece el icono del candado de \"Cargando\" (lo esperamos un máximo de #{maxSeconds})",
		level=State.Info,
		store=StoreType.None)
	public boolean validateIsVisible(int maxSeconds) {
		return (modalPreloaderSpinner.isVisibleUntil(maxSeconds));
	}
	
	@Validation (
		description="Desaparece el icono del candado de \"Cargando\" (lo esperamos un máximo de #{maxSeconds})",
		level=State.Info,
		store=StoreType.None)
	public boolean validateIsVanished(int maxSeconds) {
		return (modalPreloaderSpinner.isNotVisibleUntil(maxSeconds));
	}
}
