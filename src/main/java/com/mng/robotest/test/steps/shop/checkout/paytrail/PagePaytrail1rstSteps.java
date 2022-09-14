package com.mng.robotest.test.steps.shop.checkout.paytrail;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.pageobject.shop.checkout.paytrail.PagePaytrail1rst;
import com.mng.robotest.test.utils.ImporteScreen;

public class PagePaytrail1rstSteps extends StepBase {
	
	private final PagePaytrail1rst pagePaytrail1rst = new PagePaytrail1rst();
	
	@Validation
	public ChecksTM validateIsPage(String importeTotal, String codPais) {
		ChecksTM checks = ChecksTM.getNew();
		String nombrePagoCabecera = "Finnish E-Banking";
		int seconds = 2;
		checks.add(
			"Figura el bloque correspondiente al pago <b>" + nombrePagoCabecera + "</b>",
			pagePaytrail1rst.isPresentEntradaPago(nombrePagoCabecera), State.Warn);
		
		State stateVal = State.Warn;
		if (channel.isDevice()) {
			stateVal = State.Info;
		}
		checks.add(
			"Aparece el importe de la compra: \" + importeTotal",
			ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, driver), stateVal);  
		
		if (channel==Channel.desktop) {
			checks.add(
				"Es visible el desplegable de bancos (lo esperamos hasta " + seconds + " seconds)",
				pagePaytrail1rst.isVisibleSelectBancosUntil(seconds), State.Warn);
			
			checks.add(
				"Figura un botón de pago",
				pagePaytrail1rst.isPresentButtonPago(), State.Defect);
		}
		
		return checks;
	}

	@Step (
		description="Seleccionar el banco <b>Nordea</b> del desplegable y pulsar el botón \"Continue\"", 
		expected="Aparece la página de identificación de E-payment")
	public void selectBancoAndContinue() {
		pagePaytrail1rst.selectBanco("Nordea");
		pagePaytrail1rst.clickButtonContinue();
		new PagePaytrailEpaymentSteps().validateIsPage();
	}
}