package com.mng.robotest.test80.mango.test.stpv.shop.checkout;

import org.openqa.selenium.WebDriver;

import com.mng.testmaker.conf.Channel;
import com.mng.testmaker.conf.State;
import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.SecKrediKarti;


public class SecKrediKartiStpV {

	final private WebDriver driver;
	final private Channel channel;
	final private SecKrediKarti secKrediKarti;
	
	private SecKrediKartiStpV(Channel channel, WebDriver driver) {
		this.driver = driver;
		this.channel = channel;
		this.secKrediKarti = SecKrediKarti.getNew(channel, driver);
	}
	
	public static SecKrediKartiStpV getNew(Channel channel, WebDriver driver) {
		return (new SecKrediKartiStpV(channel, driver));
	}
	
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
