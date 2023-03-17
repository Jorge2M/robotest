package com.mng.robotest.domains.compra.payments.ideal.steps;

import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.domains.compra.payments.ideal.pageobjects.SecIdeal;
import com.mng.robotest.domains.compra.payments.ideal.pageobjects.SecIdeal.BancoSeleccionado;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;

public class SecIdealSteps extends StepBase {

	private final SecIdeal secIdeal = new SecIdeal();
	
	@Validation (
		description="Aparece el bloque de selección del banco",
		level=State.Defect)
	public boolean validateIsSectionOk() {
		int seconds = 1;
		return (secIdeal.isVisibleSelectorOfBank(seconds));
	}
	
	/**
	 * @param el valor de las opciones del banco a seleccionar contiene el "value" del listBox...
	 */
	@Step (
		description="Seleccionar el banco \"#{bancoSeleccionado}\"", 
		expected="El resultado es correcto")
	public void clickBanco(BancoSeleccionado bancoSeleccionado) {
		secIdeal.clickBancoByValue(bancoSeleccionado);
	}
}