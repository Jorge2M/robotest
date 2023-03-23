package com.mng.robotest.domains.compra.payments.dotpay.steps;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.StoreType;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.Check;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.domains.compra.payments.dotpay.pageobjects.PageDotpay1rst;
import com.mng.robotest.test.utils.ImporteScreen;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PageDotpay1rstSteps extends StepBase {
	
	private final PageDotpay1rst pageDotpay1rst = new PageDotpay1rst();
	
	@Validation
	public ChecksTM validateIsPage(String nombrePago, String importeTotal, String codPais) {
		var checks = ChecksTM.getNew();
	  	checks.add(
			"Figura el bloque correspondiente al pago <b>" + nombrePago + "</b>",
			pageDotpay1rst.isPresentEntradaPago(nombrePago), Warn);
	  	
	  	State stateVal = Warn;
	  	StoreType store = StoreType.Evidences;
		if (channel.isDevice()) {
			stateVal = Info;
			store = StoreType.None;
		}
	  	checks.add(
	  		Check.make(
	  		    "Aparece el importe de la compra: " + importeTotal,
			    ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, driver), stateVal)
	  		.store(store).build());
	  	
	  	checks.add(
			"Aparece la cabecera indicando la 'etapa' del pago",
			pageDotpay1rst.isPresentCabeceraStep(nombrePago), Warn);
	  	
	  	if (channel==Channel.desktop) {
		  	checks.add(
				"Figura un botón de pago",
				pageDotpay1rst.isPresentButtonPago(), Defect);
	  	}
	  	
	  	return checks;
	}
	
	@Step (
		description="Seleccionar el link hacia el Pago", 
		expected="Aparece la página de selección del canal de pago")
	public void clickToPay(String importeTotal, String codPais) {
		pageDotpay1rst.clickToPay();
		new PageDotpayPaymentChannelSteps().validateIsPage(importeTotal, codPais);
	}
}
