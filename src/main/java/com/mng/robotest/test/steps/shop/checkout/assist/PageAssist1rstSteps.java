package com.mng.robotest.test.steps.shop.checkout.assist;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.beans.Pago;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.pageobject.shop.checkout.PageCheckoutWrapper;
import com.mng.robotest.test.pageobject.shop.checkout.assist.PageAssist1rst;
import com.mng.robotest.test.pageobject.shop.checkout.assist.PageAssistLast;
import com.mng.robotest.test.utils.ImporteScreen;

public class PageAssist1rstSteps {
	
	private PageAssist1rst pageAssist1rst;
	private final Channel channel;
	private final AppEcom app;
	
	public PageAssist1rstSteps(Channel channel, AppEcom app, WebDriver driver) {
		this.pageAssist1rst = new PageAssist1rst(channel, driver);
		this.channel = channel;
		this.app = app;
	}
	
	@Validation
	public ChecksTM validateIsPage(String importeTotal, Pais pais) {
		ChecksTM validations = ChecksTM.getNew();
	 	validations.add(
			"Está presente el logo de Assist",
			pageAssist1rst.isPresentLogoAssist(), State.Warn);
	 	validations.add(
			"En la página resultante figura el importe total de la compra (" + importeTotal + ")",
			ImporteScreen.isPresentImporteInScreen(importeTotal, pais.getCodigo_pais(), pageAssist1rst.driver), State.Warn);
	 	validations.add(
			"No se trata de la página de precompra (no aparece los logos de formas de pago)",
			!new PageCheckoutWrapper(channel, app, pageAssist1rst.driver).isPresentMetodosPago(), State.Defect);
	 	
	 	boolean inputsTrjOk = pageAssist1rst.isPresentInputsForTrjData();
		if (channel.isDevice()) {
		 	validations.add(
				"Figuran 5 campos de input para los datos de la tarjeta: 1 para el número de tarjeta, 2 para la fecha de caducidad, 1 para el titular y 1 para el CVC",
				inputsTrjOk, State.Warn);
		} else {
		 	validations.add(
				"Figuran 5 campos de input para los datos de la tarjeta: 4 para el número de tarjeta, 2 para la fecha de caducidad, 1 para el titular y 1 para el CVC",
				inputsTrjOk, State.Warn);
		}
		
		return validations;
	}
	
	@Step (
		description="Introducimos los datos de la tarjeta y pulsamos el botón de pago", 
		expected="Aparece la página de resultado de Mango")
	public void inputDataTarjAndPay(Pago pago) throws Exception {
		pageAssist1rst.inputDataPagoAndWaitSubmitAvailable(pago);
		pageAssist1rst.clickBotonPago();
		checkAfterClickPayButton();
	}
	
	@Validation
	private ChecksTM checkAfterClickPayButton() {
		ChecksTM validations = ChecksTM.getNew();
		int maxSeconds = 10;
	 	validations.add(
			"Desaparece la página con el botón de pago (lo esperamos hasta " + maxSeconds + " segundos)",
			pageAssist1rst.invisibilityBotonPagoUntil(maxSeconds), State.Warn);
	 	validations.add(
			"Aparece una página intermedia con un botón de submit",
			PageAssistLast.isPage(pageAssist1rst.driver), State.Warn);
	 	return validations;
	}
}