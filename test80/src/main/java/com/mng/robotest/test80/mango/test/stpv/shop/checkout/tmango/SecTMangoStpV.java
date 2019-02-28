package com.mng.robotest.test80.mango.test.stpv.shop.checkout.tmango;

import org.openqa.selenium.WebDriver;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.TestCaseData;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.tmango.SecTMango;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.tmango.SecTMango.TipoPago;

public class SecTMangoStpV {

	@Validation
    public static ListResultValidation validateIsSectionOk(Channel channel, WebDriver driver) {
		ListResultValidation validations = ListResultValidation.getNew();
	 	validations.add(
			"Aparece el bloque de selección de la forma de pago<br>",
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
