package com.mng.robotest.domains.compra.payments.kredikarti.steps;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.domains.compra.payments.kredikarti.pageobjects.SecKrediKarti;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;

import static com.github.jorge2m.testmaker.conf.State.*;

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
		description="Se carga la capa correspondiente al pago a plazos (en menos de #{seconds} segundos)",
		level=Defect)
	private boolean isVisibleCapaPagoAplazo(Channel channel, int seconds, WebDriver driver) {
		return (secKrediKarti.isVisiblePagoAPlazoUntil(seconds));
	}
	
	@Step (
		description="Seleccionamos la #{numOpcion}a de las opciones de pago a plazo", 
		expected="La opción se selecciona correctamente")
	public void clickOpcionPagoAPlazo(int numOpcion) {
		secKrediKarti.clickRadioPagoAPlazo(numOpcion);
	}
}
