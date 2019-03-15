package com.mng.robotest.test80.mango.test.stpv.shop.checkout;

import org.openqa.selenium.WebDriver;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ChecksResult;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago.TypePago;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.PageCheckoutWrapper;

public class SecTarjetaPciStpV {
    
	@Validation
    public static ChecksResult validateIsSectionOk(Pago pago, Pais pais, Channel channel, WebDriver driver) {
    	ChecksResult validations = ChecksResult.getNew();
    	PageCheckoutWrapper pageCheckoutStpV = new PageCheckoutWrapper();
    	if (channel==Channel.desktop && !pais.isPagoPSP()) {
            int maxSecondsWait = 5;
		 	validations.add(
				"Aparece el bloque correspondiente a la introducción de los datos del método de pago " + pago.getNombre(channel) + 
				" (lo esperamos hasta " + maxSecondsWait + " segundo)<br>",
				pageCheckoutStpV.getSecTarjetaPci(channel, driver).isVisiblePanelPagoUntil(pago.getNombre(channel), maxSecondsWait, driver), 
				State.Warn);    
    	}
    	
	 	validations.add(
			"Aparecen los 4 campos <b>Número, Titular, Mes, Año</b> para la introducción de los datos de la tarjeta<br>",
			pageCheckoutStpV.getSecTarjetaPci(channel, driver).isPresentInputNumberUntil(1, driver) &&
			pageCheckoutStpV.getSecTarjetaPci(channel, driver).isPresentInputTitular(driver) &&
			pageCheckoutStpV.getSecTarjetaPci(channel, driver).isPresentSelectMes(driver) &&
			pageCheckoutStpV.getSecTarjetaPci(channel, driver).isPresentSelectAny(driver), State.Defect);  
	 	
	 	if (pago.getTypePago()!=TypePago.Bancontact) {
		 	validations.add(
				"Aparece también el campo <b>CVC</b>",
				pageCheckoutStpV.getSecTarjetaPci(channel, driver).isPresentInputCvc(driver), State.Defect); 
	 	}
        if (pago.getDni()!=null && "".compareTo(pago.getDni())!=0) {
		 	validations.add(
				"Aparece también el campo <b>DNI(C.C)</b>",
				pageCheckoutStpV.getSecTarjetaPci(channel, driver).isPresentInputDni(driver), State.Defect); 
        }
        return validations;
    }
}
