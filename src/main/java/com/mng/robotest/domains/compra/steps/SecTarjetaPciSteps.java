package com.mng.robotest.domains.compra.steps;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.domains.compra.pageobject.PageCheckoutWrapper;
import com.mng.robotest.domains.compra.pageobject.pci.SecTarjetaPci;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.beans.Pago;
import com.mng.robotest.test.beans.Pago.TypePago;

public class SecTarjetaPciSteps extends StepBase {
	
	private final PageCheckoutWrapper pageCheckoutSteps = new PageCheckoutWrapper();
	private final SecTarjetaPci secTarjetaPci = pageCheckoutSteps.getSecTarjetaPci();
	
	@Validation
	public ChecksTM validateIsSectionOk(Pago pago) {
		ChecksTM checks = ChecksTM.getNew();
		if (channel==Channel.desktop && pago.getTypePago()!=TypePago.KrediKarti) {
			int seconds = 5;
		 	checks.add(
				"Aparece el bloque correspondiente a la introducción de los datos del método de pago " + pago.getNombre(channel, app) + 
				" (lo esperamos hasta " + seconds + " segundo)",
				secTarjetaPci.isVisiblePanelPagoUntil(pago.getNombre(channel, app), seconds), 
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