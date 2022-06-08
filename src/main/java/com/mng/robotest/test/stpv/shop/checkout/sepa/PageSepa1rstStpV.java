package com.mng.robotest.test.stpv.shop.checkout.sepa;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.conf.StoreType;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.domain.suitetree.StepTM;
import com.mng.robotest.test.pageobject.shop.checkout.sepa.PageSepa1rst;
import com.mng.robotest.test.utils.ImporteScreen;


public class PageSepa1rstStpV {
	
	@Validation
	public static ChecksTM validateIsPage(String nombrePago, String importeTotal, String codPais, Channel channel, WebDriver driver) {
		ChecksTM validations = ChecksTM.getNew();
		validations.add(
			"Figura el bloque correspondiente al pago <b>" + nombrePago + "</b>",
			PageSepa1rst.isPresentIconoSepa(channel, driver), State.Warn);
		
		State stateVal = State.Warn;
		StoreType store = StoreType.Evidences;
		if (channel.isDevice()) {
			stateVal = State.Info;
			store = StoreType.None;
		}
		validations.add(
			"Aparece el importe de la compra: " + importeTotal,
			ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, driver), stateVal, store);		
		validations.add(
			"Aparece la cabecera indicando la 'etapa' del pago",
			PageSepa1rst.isPresentCabeceraStep(driver), State.Warn);		
		if (channel==Channel.desktop) {
			validations.add(
				"Figura el campo de introducción del titular",
				PageSepa1rst.isPresentInputTitular(driver), State.Warn);
			validations.add(
				"Figura el campo de introducción del la cuenta",
				PageSepa1rst.isPresentInputCuenta(driver), State.Warn);
			validations.add(
				"Figura un botón de pago",
				PageSepa1rst.isPresentButtonPagoDesktop(driver), State.Defect);
		}
		
		return validations;
	}
	
	@Step (
		description=
			"Introducimos los datos:<br>" +
			"  - Titular: <b>#{titular}</b><br>" +
			"  - Cuenta: <b>#{iban}</b></br>" +
			"Y pulsamos el botón <b>Pay</b>",
		expected="Aparece la página de resultado de pago OK de Mango")
	public static void inputDataAndclickPay(String iban, String titular, String importeTotal, String codPais, Channel channel, WebDriver driver) {
		if (channel.isDevice()) {
			StepTM step = TestMaker.getCurrentStepInExecution();
			step.setDescripcion("Seleccionamos el icono de SEPA. " + step.getDescripcion());
			PageSepa1rst.clickIconoSepa(channel, driver);
		}
 
		PageSepa1rst.inputTitular(titular, driver);
		PageSepa1rst.inputCuenta(iban, driver);
		PageSepa1rst.clickAcepto(driver);
		PageSepa1rst.clickButtonContinuePago(channel, driver);
		
		//En el caso de móvil aparece una página de resultado específica de SEPA
		if (channel.isDevice()) {
			PageSepaResultMobilStpV.validateIsPage(importeTotal, codPais, driver);
		}
	}
}
