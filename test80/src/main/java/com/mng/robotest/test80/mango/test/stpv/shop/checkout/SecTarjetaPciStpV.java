package com.mng.robotest.test80.mango.test.stpv.shop.checkout;

import org.openqa.selenium.WebDriver;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago.TypePago;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.PageCheckoutWrapper;

public class SecTarjetaPciStpV {
    
	@Validation
    public static ListResultValidation validateIsSectionOk(Pago pago, Pais pais, Channel channel, WebDriver driver) {
    	ListResultValidation validations = ListResultValidation.getNew();
    	if (channel==Channel.desktop && !pais.isPagoPSP()) {
            int maxSecondsWait = 5;
		 	validations.add(
				"Aparece el bloque correspondiente a la introducción de los datos del método de pago " + pago.getNombre(channel) + 
				" (lo esperamos hasta " + maxSecondsWait + " segundo)<br>",
				PageCheckoutWrapper.getSecTarjetaPci(channel).isVisiblePanelPagoUntil(pago.getNombre(channel), maxSecondsWait, driver), 
				State.Warn);    
    	}
    	
	 	validations.add(
			"Aparecen los 4 campos <b>Número, Titular, Mes, Año</b> para la introducción de los datos de la tarjeta",
			PageCheckoutWrapper.getSecTarjetaPci(channel).isPresentInputNumberUntil(1, driver) &&
            PageCheckoutWrapper.getSecTarjetaPci(channel).isPresentInputTitular(driver) &&
            PageCheckoutWrapper.getSecTarjetaPci(channel).isPresentSelectMes(driver) &&
            PageCheckoutWrapper.getSecTarjetaPci(channel).isPresentSelectAny(driver), State.Defect);  
	 	
	 	if (pago.getTypePago()!=TypePago.Bancontact) {
		 	validations.add(
				"Aparece también el campo <b>CVC</b>",
				PageCheckoutWrapper.getSecTarjetaPci(channel).isPresentInputCvc(driver), State.Defect); 
	 	}
        if (pago.getDni()!=null && "".compareTo(pago.getDni())!=0) {
		 	validations.add(
				"Aparece también el campo <b>DNI(C.C)</b>",
				PageCheckoutWrapper.getSecTarjetaPci(channel).isPresentInputDni(driver), State.Defect); 
        }
        return validations;
    }
}
