package com.mng.robotest.domains.compra.payments.dotpay.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.domains.compra.payments.dotpay.pageobjects.PageDotpayPaymentChannel;
import com.mng.robotest.test.utils.ImporteScreen;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PageDotpayPaymentChannelSteps extends StepBase {

	private final PageDotpayPaymentChannel pageDotpayPaymentChannel = new PageDotpayPaymentChannel();
	
	@Validation
	public ChecksTM validateIsPage(String importeTotal, String codPais) {
		var checks = ChecksTM.getNew();
	  	checks.add(
			"Aparece la página de Dotpay para la selección del banco",
			pageDotpayPaymentChannel.isPage(), Warn);
	  	
	  	checks.add(
			"Aparece el importe de la compra: " + importeTotal,
			ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, driver), Warn);
		return checks;
	}
	
	@Step (
		description="Seleccionar el <b>#{numPayment}o</b> de los Canales de Pago", 
		expected="Se scrolla y se hace visible el bloque de introducción del nombre")
	public void selectPayment(int numPayment) {
		pageDotpayPaymentChannel.clickPayment(numPayment);
		isVisibleBlockInputNombre(1);
	}
	
	@Validation (
		description="Es visible el bloque de introducción del nombre " + SECONDS_WAIT,
		level=Warn)
	private boolean isVisibleBlockInputNombre(int seconds) {
		return pageDotpayPaymentChannel.isVisibleBlockInputDataUntil(seconds);
	}
	
	@Step (
		description="Introducir el nombre <b>#{nameFirst} / #{nameSecond}</b> y seleccionar el botón para Confirmar", 
		expected="Aparece la página de pago")
	public void inputNameAndConfirm(String nameFirst, String nameSecond) {
		pageDotpayPaymentChannel.sendInputNombre(nameFirst, nameSecond);
		pageDotpayPaymentChannel.clickButtonConfirm();
		new PageDotpayAcceptSimulationSteps().validateIsPage(5);
	}
}
