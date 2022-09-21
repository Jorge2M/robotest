package com.mng.robotest.domains.compra.payments.sepa.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.conf.StoreType;
import com.github.jorge2m.testmaker.domain.suitetree.Check;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.domain.suitetree.StepTM;
import com.mng.robotest.domains.compra.payments.sepa.pageobjects.PageSepa1rst;
import com.mng.robotest.test.utils.ImporteScreen;


public class PageSepa1rstSteps {
	
	private final PageSepa1rst pageSepa1rst = new PageSepa1rst();
	
	@Validation
	public ChecksTM validateIsPage(String nombrePago, String importeTotal, String codPais, Channel channel) {
		ChecksTM checks = ChecksTM.getNew();
		checks.add(
			"Figura el bloque correspondiente al pago <b>" + nombrePago + "</b>",
			pageSepa1rst.isPresentIconoSepa(channel), State.Warn);
		
		State stateVal = State.Warn;
		StoreType store = StoreType.Evidences;
		if (channel.isDevice()) {
			stateVal = State.Info;
			store = StoreType.None;
		}
		checks.add(
		    Check.make(
			    "Aparece el importe de la compra: " + importeTotal,
			    ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, pageSepa1rst.driver), stateVal)
		    .store(store).build());
		
		checks.add(
			"Aparece la cabecera indicando la 'etapa' del pago",
			pageSepa1rst.isPresentCabeceraStep(), State.Warn);		
		
		if (channel==Channel.desktop) {
			checks.add(
				"Figura el campo de introducción del titular",
				pageSepa1rst.isPresentInputTitular(), State.Warn);
			checks.add(
				"Figura el campo de introducción del la cuenta",
				pageSepa1rst.isPresentInputCuenta(), State.Warn);
			checks.add(
				"Figura un botón de pago",
				pageSepa1rst.isPresentButtonPagoDesktop(), State.Defect);
		}
		
		return checks;
	}
	
	@Step (
		description=
			"Introducimos los datos:<br>" +
			"  - Titular: <b>#{titular}</b><br>" +
			"  - Cuenta: <b>#{iban}</b></br>" +
			"Y pulsamos el botón <b>Pay</b>",
		expected="Aparece la página de resultado de pago OK de Mango")
	public void inputDataAndclickPay(String iban, String titular, String importeTotal, String codPais, Channel channel) {
		if (channel.isDevice()) {
			StepTM step = TestMaker.getCurrentStepInExecution();
			step.setDescripcion("Seleccionamos el icono de SEPA. " + step.getDescripcion());
			pageSepa1rst.clickIconoSepa(channel);
		}
 
		pageSepa1rst.inputTitular(titular);
		pageSepa1rst.inputCuenta(iban);
		pageSepa1rst.clickAcepto();
		pageSepa1rst.clickButtonContinuePago(channel);
		
		//En el caso de móvil aparece una página de resultado específica de SEPA
		if (channel.isDevice()) {
			new PageSepaResultMobilSteps().validateIsPage(importeTotal, codPais);
		}
	}
}
