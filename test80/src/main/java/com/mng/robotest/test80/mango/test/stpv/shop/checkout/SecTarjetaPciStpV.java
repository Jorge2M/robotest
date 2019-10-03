package com.mng.robotest.test80.mango.test.stpv.shop.checkout;

import org.openqa.selenium.WebDriver;
import com.mng.testmaker.utils.State;
import com.mng.testmaker.utils.otras.Channel;
import com.mng.testmaker.annotations.validation.ChecksResult;
import com.mng.testmaker.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago.TypePago;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.PageCheckoutWrapper;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.pci.SecTarjetaPci;

public class SecTarjetaPciStpV {
	
	final WebDriver driver;
	final Channel channel;
	final private SecTarjetaPci secTarjetaPci;
	
	private SecTarjetaPciStpV(Channel channel, WebDriver driver) {
		this.driver = driver;
		this.channel = channel;
		PageCheckoutWrapper pageCheckoutStpV = new PageCheckoutWrapper();
		this.secTarjetaPci = pageCheckoutStpV.getSecTarjetaPci(channel, driver);
	}
	
	public static SecTarjetaPciStpV getNew(Channel channel, WebDriver driver) {
		return (new SecTarjetaPciStpV(channel, driver));
	}
    
	@Validation
    public ChecksResult validateIsSectionOk(Pago pago, Pais pais) {
    	ChecksResult validations = ChecksResult.getNew();
    	
    	if (channel==Channel.desktop && pago.getTypePago()!=TypePago.KrediKarti) {
            int maxSecondsWait = 5;
		 	validations.add(
				"Aparece el bloque correspondiente a la introducción de los datos del método de pago " + pago.getNombre(channel) + 
				" (lo esperamos hasta " + maxSecondsWait + " segundo)",
				secTarjetaPci.isVisiblePanelPagoUntil(pago.getNombre(channel), maxSecondsWait), 
				State.Warn);    
    	}
    	
	 	validations.add(
			"Aparecen los 4 campos <b>Número, Titular, Mes, Año</b> para la introducción de los datos de la tarjeta",
			secTarjetaPci.isPresentInputNumberUntil(1) &&
			secTarjetaPci.isPresentInputTitular() &&
			secTarjetaPci.isPresentSelectMes() &&
			secTarjetaPci.isPresentSelectAny(), State.Defect);  
	 	
	 	if (pago.getTypePago()!=TypePago.Bancontact) {
		 	validations.add(
				"Aparece también el campo <b>CVC</b>",
				secTarjetaPci.isPresentInputCvc(), State.Defect); 
	 	}
        if (pago.getDni()!=null && "".compareTo(pago.getDni())!=0) {
		 	validations.add(
				"Aparece también el campo <b>DNI(C.C)</b>",
				secTarjetaPci.isPresentInputDni(), State.Defect); 
        }
        return validations;
    }
}
