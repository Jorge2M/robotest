package com.mng.robotest.tests.domains.compra.steps;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.compra.pageobjects.PageCheckoutWrapper;
import com.mng.robotest.tests.domains.compra.pageobjects.pci.SecTarjetaPci;
import com.mng.robotest.testslegacy.beans.Pago;
import com.mng.robotest.testslegacy.beans.TypePago;

import static com.github.jorge2m.testmaker.conf.State.*;

public class SecTarjetaPciSteps extends StepBase {
	
	private final PageCheckoutWrapper pgCheckoutSteps = new PageCheckoutWrapper();
	private final SecTarjetaPci secTarjetaPci = pgCheckoutSteps.getSecTarjetaPci();
	
	@Validation
	public ChecksTM validateIsSectionOk(Pago pago) {
		var checks = ChecksTM.getNew();
		if (channel==Channel.desktop && pago.getTypePago()!=TypePago.KREDI_KARTI) {
			int seconds = 5;
		 	checks.add(
				"Aparece el bloque correspondiente a la introducción de los datos del método de pago " + pago.getNombre(channel, app) + 
				getLitSecondsWait(seconds),
				secTarjetaPci.isVisiblePanelPagoUntil(pago.getNombre(channel, app), seconds), WARN);	
		}
		
	 	checks.add(
			"Aparecen los 4 campos <b>Número, Titular, Mes, Año</b> para la introducción de los datos de la tarjeta",
			secTarjetaPci.isPresentInputNumberUntil(1) &&
			secTarjetaPci.isPresentInputTitular() &&
			secTarjetaPci.isPresentSelectMes() &&
			secTarjetaPci.isPresentSelectAny());  
	 	
	 	if (pago.getTypePago()!=TypePago.BANCONTACT) {
		 	checks.add(
				"Aparece también el campo <b>CVC</b>",
				secTarjetaPci.isPresentInputCvc()); 
	 	}
		if (pago.getDni()!=null && "".compareTo(pago.getDni())!=0) {
		 	checks.add(
				"Aparece también el campo <b>DNI(C.C)</b>",
				secTarjetaPci.isPresentInputDni()); 
		}
		return checks;
	}
}
