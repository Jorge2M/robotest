package com.mng.robotest.test.steps.shop.checkout;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.pageobject.shop.checkout.SecKrediKarti;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;


public class SecKrediKartiSteps extends StepBase {

	private final SecKrediKarti secKrediKarti = new SecKrediKarti(); 
	
	@Step (
		description="Introducimos el número de cuenta #{numTarjeta}",
		expected="Aparece la capa correspondiente a las opciones a plazo")
	public void inputNumTarjeta(String numTarjeta) {
		secKrediKarti.inputCardNumberAndTab(numTarjeta);
		isVisibleCapaPagoAplazo(channel, 5, driver);
	}
	
	@Validation (
		description="Se carga la capa correspondiente al pago a plazos (en menos de #{maxSeconds} segundos)",
		level=State.Defect)
	private boolean isVisibleCapaPagoAplazo(Channel channel, int maxSeconds, WebDriver driver) {
		return (secKrediKarti.isVisiblePagoAPlazoUntil(maxSeconds));
	}
	
	@Step (
		description="Seleccionamos la #{numOpcion}a de las opciones de pago a plazo", 
		expected="La opción se selecciona correctamente")
	public void clickOpcionPagoAPlazo(int numOpcion) throws Exception {
		secKrediKarti.clickRadioPagoAPlazo(numOpcion);
	}
}
