package com.mng.robotest.test80.mango.test.stpv.shop.checkout.tmango;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.tmango.SecTMango;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.tmango.SecTMango.TipoPago;

public class SecTMangoStpV {

	@Validation
    public static ChecksTM validateIsSectionOk(Channel channel, WebDriver driver) {
		ChecksTM validations = ChecksTM.getNew();
	 	validations.add(
			"Aparece el bloque de selección de la forma de pago",
			SecTMango.isVisibleUntil(channel, 0, driver), State.Defect); 
	 	validations.add(
			"Aparece disponible la modalidad de pago:<br>" + SecTMango.getDescripcionTipoPago(TipoPago.pagoHabitual), 
			SecTMango.isModalidadDisponible(driver, SecTMango.TipoPago.pagoHabitual, channel), State.Defect); 
	 	return validations;
    }
    
    /**
     * @param literalTipoPago contiene uno de los valores de SecTMango.pagoHabitual, SecTMango.tresMeses...
     */
	final static String tagDescrPago = "@tagTipoPago";
	@Step (
		description="Seleccionar la forma de pago \"" + tagDescrPago + "\"", 
        expected="El resultado es correcto")
    public static void clickTipoPago(TipoPago tipoPago, Channel channel, WebDriver driver) {
		TestMaker.getCurrentStepInExecution().replaceInDescription(tagDescrPago, SecTMango.getDescripcionTipoPago(tipoPago));
        SecTMango.clickModalidad(driver, tipoPago, channel);
    }
}
