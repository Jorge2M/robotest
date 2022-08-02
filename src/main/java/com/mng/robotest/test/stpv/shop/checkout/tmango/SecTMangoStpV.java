package com.mng.robotest.test.stpv.shop.checkout.tmango;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.service.TestMaker;

import com.mng.robotest.test.pageobject.shop.checkout.tmango.SecTMango;
import com.mng.robotest.test.pageobject.shop.checkout.tmango.SecTMango.TipoPago;

public class SecTMangoStpV {

	private final SecTMango secTMango;
	
	public SecTMangoStpV(Channel channel, WebDriver driver) {
		secTMango = new SecTMango(channel, driver);
	}
	
	@Validation
	public ChecksTM validateIsSectionOk() {
		ChecksTM validations = ChecksTM.getNew();
	 	validations.add(
			"Aparece el bloque de selección de la forma de pago",
			secTMango.isVisibleUntil(0), State.Defect); 
	 	validations.add(
			"Aparece disponible la modalidad de pago:<br>" + secTMango.getDescripcionTipoPago(TipoPago.PAGO_HABITUAL), 
			secTMango.isModalidadDisponible(SecTMango.TipoPago.PAGO_HABITUAL), State.Defect); 
	 	return validations;
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
