package com.mng.robotest.test.steps.shop.checkout;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.beans.Pago;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.beans.Pago.TypePago;
import com.mng.robotest.test.pageobject.shop.checkout.PageCheckoutWrapper;
import com.mng.robotest.test.pageobject.shop.checkout.pci.SecTarjetaPci;

public class SecTarjetaPciSteps {
	
	final WebDriver driver;
	final Channel channel;
	final AppEcom app;
	final private SecTarjetaPci secTarjetaPci;
	
	public SecTarjetaPciSteps(Channel channel, AppEcom app, WebDriver driver) {
		this.driver = driver;
		this.channel = channel;
		this.app = app;
		PageCheckoutWrapper pageCheckoutSteps = new PageCheckoutWrapper(channel, app, driver);
		this.secTarjetaPci = pageCheckoutSteps.getSecTarjetaPci();
	}
	
	@Validation
	public ChecksTM validateIsSectionOk(Pago pago, Pais pais) {
		ChecksTM checks = ChecksTM.getNew();
		if (channel==Channel.desktop && pago.getTypePago()!=TypePago.KrediKarti) {
			int maxSeconds = 5;
		 	checks.add(
				"Aparece el bloque correspondiente a la introducción de los datos del método de pago " + pago.getNombre(channel, app) + 
				" (lo esperamos hasta " + maxSeconds + " segundo)",
				secTarjetaPci.isVisiblePanelPagoUntil(pago.getNombre(channel, app), maxSeconds), 
				State.Warn);	
		}
		
	 	checks.add(
			"Aparecen los 4 campos <b>Número, Titular, Mes, Año</b> para la introducción de los datos de la tarjeta",
			secTarjetaPci.isPresentInputNumberUntil(1) &&
			secTarjetaPci.isPresentInputTitular() &&
			secTarjetaPci.isPresentSelectMes() &&
			secTarjetaPci.isPresentSelectAny(), State.Defect);  
	 	
	 	if (pago.getTypePago()!=TypePago.Bancontact) {
		 	checks.add(
				"Aparece también el campo <b>CVC</b>",
				secTarjetaPci.isPresentInputCvc(), State.Defect); 
	 	}
		if (pago.getDni()!=null && "".compareTo(pago.getDni())!=0) {
		 	checks.add(
				"Aparece también el campo <b>DNI(C.C)</b>",
				secTarjetaPci.isPresentInputDni(), State.Defect); 
		}
		return checks;
	}
}
