package com.mng.robotest.domains.compra.payments.paysecureqiwi.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.domains.compra.payments.paysecureqiwi.pageobjects.PageQiwiInputTlfn;

public class PageQiwiInputTlfnSteps {
				 
	private final PageQiwiInputTlfn pageQiwiInputTlfn = new PageQiwiInputTlfn();
	
	@Validation (
		description="Aparece una página con el campo de introducción del Qiwi Mobile Phone",
		level=State.Warn)
	public boolean validateIsPage() { 
		return pageQiwiInputTlfn.isPresentInputPhone();
	}
	
	@Step (
		description="Introducimos el Qiwi Mobile Phone #{tlfnQiwi} y pulsamos el botón \"Aceptar\"", 
		expected="Aparece la página de confirmación de Qiwi o la de resultado del pago de Mango")
	public void inputTelefono(String tlfnQiwi) {
		pageQiwiInputTlfn.inputQiwiPhone(tlfnQiwi);
		checkIsVisibleAceptarButton();
	}
	
	@Validation (
		description="Aparece el link de Aceptar")
	private boolean checkIsVisibleAceptarButton() {
		return pageQiwiInputTlfn.isVisibleLinkAceptar(1);
	}	
	
	@Step (
		description="Seleccionar el link <b>Aceptar</b>",
		expected="Aparece la página de confirmación del pago-Qiwi")
	public void clickConfirmarButton() { 
		pageQiwiInputTlfn.clickLinkAceptar();
	}
}
