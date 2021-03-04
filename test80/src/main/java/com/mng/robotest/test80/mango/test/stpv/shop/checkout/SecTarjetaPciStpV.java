package com.mng.robotest.test80.mango.test.stpv.shop.checkout;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago.TypePago;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.PageCheckoutWrapper;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.pci.SecTarjetaPci;

public class SecTarjetaPciStpV {
	
	final WebDriver driver;
	final Channel channel;
	final private SecTarjetaPci secTarjetaPci;
	
	public SecTarjetaPciStpV(Channel channel, WebDriver driver) {
		this.driver = driver;
		this.channel = channel;
		PageCheckoutWrapper pageCheckoutStpV = new PageCheckoutWrapper(channel, driver);
		this.secTarjetaPci = pageCheckoutStpV.getSecTarjetaPci();
	}
    
	@Validation
    public ChecksTM validateIsSectionOk(Pago pago, Pais pais) {
    	ChecksTM validations = ChecksTM.getNew();
    	if (channel==Channel.desktop && pago.getTypePago()!=TypePago.KrediKarti) {
            int maxSeconds = 5;
		 	validations.add(
				"Aparece el bloque correspondiente a la introducción de los datos del método de pago " + pago.getNombre(channel) + 
				" (lo esperamos hasta " + maxSeconds + " segundo)",
				secTarjetaPci.isVisiblePanelPagoUntil(pago.getNombre(channel), maxSeconds), 
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
