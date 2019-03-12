package com.mng.robotest.test80.mango.test.stpv.shop.checkout;

import org.openqa.selenium.WebDriver;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.SecKrediKarti;


public class SecKrediKartiStpV {

	@Step (
		description="Introducimos el número de cuenta #{numTarjeta}",
        expected="Aparece la capa correspondiente a las opciones a plazo")
    public static void inputNumTarjeta(String numTarjeta, Channel channel, WebDriver driver) {
        SecKrediKarti.inputCardNumberAndTab(driver, numTarjeta);
        isVisibleCapaPagoAplazo(channel, 5, driver);
    }
	
	@Validation (
		description="Se carga la capa correspondiente al pago a plazos (en menos de #{maxSecondsWait} segundos)",
		level=State.Defect)
	private static boolean isVisibleCapaPagoAplazo(Channel channel, int maxSecondsWait, WebDriver driver) {
	    return (SecKrediKarti.isVisiblePagoAPlazoUntil(driver, channel, maxSecondsWait));
	}
    
	@Step (
		description="Seleccionamos la #{numOpcion}a de las opciones de pago a plazo", 
        expected="La opción se selecciona correctamente")
    public static void clickOpcionPagoAPlazo(int numOpcion, Channel channel, WebDriver driver) {
		SecKrediKarti.clickRadioPlazo(numOpcion, driver, channel);
    }
}
