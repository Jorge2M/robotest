package com.mng.robotest.test80.mango.test.stpv.shop.checkout.tmango;

import org.openqa.selenium.WebDriver;
import com.mng.testmaker.utils.State;
import com.mng.testmaker.utils.TestCaseData;
import com.mng.testmaker.utils.otras.Channel;
import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.ChecksResult;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.tmango.SecTMango;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.tmango.SecTMango.TipoPago;

public class SecTMangoStpV {

	@Validation
    public static ChecksResult validateIsSectionOk(Channel channel, WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
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
		TestCaseData.getDatosCurrentStep().replaceInDescription(tagDescrPago, SecTMango.getDescripcionTipoPago(tipoPago));
        SecTMango.clickModalidad(driver, tipoPago, channel);
    }
}
