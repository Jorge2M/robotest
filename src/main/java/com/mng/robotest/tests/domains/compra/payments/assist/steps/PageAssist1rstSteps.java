package com.mng.robotest.tests.domains.compra.payments.assist.steps;

import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.compra.pageobjects.PageCheckoutWrapper;
import com.mng.robotest.tests.domains.compra.payments.assist.pageobjects.PageAssist1rst;
import com.mng.robotest.tests.domains.compra.payments.assist.pageobjects.PageAssistLast;
import com.mng.robotest.testslegacy.beans.Pago;
import com.mng.robotest.testslegacy.utils.ImporteScreen;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PageAssist1rstSteps extends StepBase {
	
	private final PageAssist1rst pgAssist1rst = new PageAssist1rst();
	private final PageAssistLast pgAssistLast = new PageAssistLast();
	
	@Validation
	public ChecksTM validateIsPage(String importeTotal) {
		var checks = ChecksTM.getNew();
	 	checks.add(
			"Está presente el logo de Assist",
			pgAssist1rst.isPresentLogoAssist(), WARN);
	 	
	 	checks.add(
			"En la página resultante figura el importe total de la compra (" + importeTotal + ")",
			ImporteScreen.isPresentImporteInScreen(importeTotal, dataTest.getCodigoPais(), pgAssist1rst.driver), WARN);
	 	
	 	checks.add(
			"No se trata de la página de precompra (no aparece los logos de formas de pago)",
			!new PageCheckoutWrapper().isPresentMetodosPago());
	 	
	 	boolean inputsTrjOk = pgAssist1rst.isPresentInputsForTrjData();
		if (channel.isDevice()) {
		 	checks.add(
				"Figuran 5 campos de input para los datos de la tarjeta: 1 para el número de tarjeta, 2 para la fecha de caducidad, 1 para el titular y 1 para el CVC",
				inputsTrjOk, WARN);
		} else {
		 	checks.add(
				"Figuran 5 campos de input para los datos de la tarjeta: 4 para el número de tarjeta, 2 para la fecha de caducidad, 1 para el titular y 1 para el CVC",
				inputsTrjOk, WARN);
		}
		
		return checks;
	}
	
	@Step (
		description="Introducimos los datos de la tarjeta y pulsamos el botón de pago", 
		expected="Aparece la página de resultado de Mango")
	public void inputDataTarjAndPay(Pago pago) {
		pgAssist1rst.inputDataPagoAndWaitSubmitAvailable(pago);
		pgAssist1rst.clickBotonPago();
		checkAfterClickPayButton();
	}
	
	@Validation
	private ChecksTM checkAfterClickPayButton() {
		var checks = ChecksTM.getNew();
		int seconds = 10;
	 	checks.add(
			"Desaparece la página con el botón de pago " + getLitSecondsWait(seconds),
			pgAssist1rst.invisibilityBotonPagoUntil(seconds), WARN);
	 	
	 	checks.add(
			"Aparece una página intermedia con un botón de submit",
			pgAssistLast.isPage(), WARN);
	 	
	 	return checks;
	}
}