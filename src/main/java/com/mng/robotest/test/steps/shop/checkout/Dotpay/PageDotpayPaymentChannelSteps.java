package com.mng.robotest.test.steps.shop.checkout.Dotpay;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.pageobject.shop.checkout.dotpay.PageDotpayPaymentChannel;
import com.mng.robotest.test.utils.ImporteScreen;


public class PageDotpayPaymentChannelSteps extends StepBase {

	PageDotpayPaymentChannel pageDotpayPaymentChannel = new PageDotpayPaymentChannel();
	
	@Validation
	public ChecksTM validateIsPage(String importeTotal, String codPais) {
		ChecksTM checks = ChecksTM.getNew();
	  	checks.add(
			"Aparece la página de Dotpay para la selección del banco",
			pageDotpayPaymentChannel.isPage(), State.Warn);
	  	
	  	checks.add(
			"Aparece el importe de la compra: " + importeTotal,
			ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, driver), State.Warn);
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
		description="Es visible el bloque de introducción del nombre (lo esperamos hasta #{maxSeconds} segundos)",
		level=State.Warn)
	private boolean isVisibleBlockInputNombre(int maxSeconds) {
		return pageDotpayPaymentChannel.isVisibleBlockInputDataUntil(maxSeconds);
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
