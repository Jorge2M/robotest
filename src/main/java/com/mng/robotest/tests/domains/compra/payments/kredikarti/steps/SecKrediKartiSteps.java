package com.mng.robotest.tests.domains.compra.payments.kredikarti.steps;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.compra.payments.kredikarti.pageobjects.SecKrediKarti;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;

public class SecKrediKartiSteps extends StepBase {

	private final SecKrediKarti secKrediKarti = new SecKrediKarti(); 
	
	@Step (
		description="Introducimos el número de cuenta #{numTarjeta}",
		expected="Aparece la capa correspondiente a las opciones a plazo")
	public void inputNumTarjeta(String numTarjeta) {
		secKrediKarti.inputCardNumberAndTab(numTarjeta);
		isVisibleCapaPagoAplazo(channel, 5);
	}
	
	@Validation (
		description="Se carga la capa correspondiente al pago a plazos " + SECONDS_WAIT)
	private boolean isVisibleCapaPagoAplazo(Channel channel, int seconds) {
		return secKrediKarti.isVisiblePagoAPlazoUntil(seconds);
	}
	
	@Step (
		description="Seleccionamos la #{numOpcion}a de las opciones de pago a plazo", 
		expected="La opción se selecciona correctamente")
	public void clickOpcionPagoAPlazo(int numOpcion) {
		secKrediKarti.clickRadioPagoAPlazo(numOpcion);
	}
}
