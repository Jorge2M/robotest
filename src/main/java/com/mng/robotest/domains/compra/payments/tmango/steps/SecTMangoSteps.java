package com.mng.robotest.domains.compra.payments.tmango.steps;

import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.domains.compra.payments.tmango.pageobjects.SecTMango;
import com.mng.robotest.domains.compra.payments.tmango.pageobjects.SecTMango.TipoPago;

import static com.github.jorge2m.testmaker.conf.State.*;

public class SecTMangoSteps {

	private final SecTMango secTMango = new SecTMango();
	
	@Validation
	public ChecksTM validateIsSectionOk() {
		var checks = ChecksTM.getNew();
	 	checks.add(
			"Aparece el bloque de selección de la forma de pago",
			secTMango.isVisibleUntil(0), Defect); 
	 	
	 	checks.add(
			"Aparece disponible la modalidad de pago:<br>" + secTMango.getDescripcionTipoPago(TipoPago.PAGO_HABITUAL), 
			secTMango.isModalidadDisponible(SecTMango.TipoPago.PAGO_HABITUAL), Defect);
	 	
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
