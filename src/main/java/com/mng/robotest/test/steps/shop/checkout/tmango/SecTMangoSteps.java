package com.mng.robotest.test.steps.shop.checkout.tmango;

import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.service.TestMaker;

import com.mng.robotest.test.pageobject.shop.checkout.tmango.SecTMango;
import com.mng.robotest.test.pageobject.shop.checkout.tmango.SecTMango.TipoPago;

public class SecTMangoSteps {

	private final SecTMango secTMango = new SecTMango();
	
	@Validation
	public ChecksTM validateIsSectionOk() {
		ChecksTM checks = ChecksTM.getNew();
	 	checks.add(
			"Aparece el bloque de selección de la forma de pago",
			secTMango.isVisibleUntil(0), State.Defect); 
	 	
	 	checks.add(
			"Aparece disponible la modalidad de pago:<br>" + secTMango.getDescripcionTipoPago(TipoPago.PAGO_HABITUAL), 
			secTMango.isModalidadDisponible(SecTMango.TipoPago.PAGO_HABITUAL), State.Defect);
	 	
	 	return checks;
	}
	
	/**
	 * @param literalTipoPago contiene uno de los valores de SecTMango.pagoHabitual, SecTMango.tresMeses...
	 */
	static final String tagDescrPago = "@tagTipoPago";
	@Step (
		description="Seleccionar la forma de pago \"" + tagDescrPago + "\"", 
		expected="El resultado es correcto")
	public void clickTipoPago(TipoPago tipoPago) {
		TestMaker.getCurrentStepInExecution().replaceInDescription(tagDescrPago, secTMango.getDescripcionTipoPago(tipoPago));
		secTMango.clickModalidad(tipoPago);
	}
}
