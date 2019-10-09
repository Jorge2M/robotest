package com.mng.robotest.test80.mango.test.stpv.shop.checkout.mercadopago;

import org.openqa.selenium.WebDriver;
import com.mng.testmaker.utils.State;
import com.mng.testmaker.utils.otras.Channel;
import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.mercadopago.PageMercpagoConf;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.PageResultPagoStpV;

public class PageMercpagoConfStpV {

	@Validation (
		description="Estamos en la página de confirmación del pago (la esperamos hasta #{maxSecondsWait} segundos)",
		level=State.Defect)
    public static boolean validaIsPageUntil(int maxSecondsWait, Channel channel, WebDriver driver) {  
		return (PageMercpagoConf.isPageUntil(channel, maxSecondsWait, driver));
    }
    
	@Step (
		description="Seleccionar el botón \"Pagar\"", 
        expected="Aparece la página de resultado")
    public static void clickPagar(Channel channel, WebDriver driver) throws Exception {
        PageMercpagoConf.clickPagar(driver);
        PageResultPagoStpV.validaIsPageUntil(30, channel, driver);
    }
}