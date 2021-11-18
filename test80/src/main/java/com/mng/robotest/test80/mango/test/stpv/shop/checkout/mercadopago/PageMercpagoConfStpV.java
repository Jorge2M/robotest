package com.mng.robotest.test80.mango.test.stpv.shop.checkout.mercadopago;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.test80.mango.test.beans.Pago.TypePago;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.mercadopago.PageMercpagoConf;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.PageResultPagoStpV;

public class PageMercpagoConfStpV {

	@Validation (
		description="Estamos en la página de confirmación del pago (la esperamos hasta #{maxSeconds} segundos)",
		level=State.Defect)
	public static boolean validaIsPageUntil(int maxSeconds, Channel channel, WebDriver driver) {  
		return (PageMercpagoConf.isPageUntil(channel, maxSeconds, driver));
	}
	
	@Step (
		description="Seleccionar el botón \"Pagar\"", 
		expected="Aparece la página de resultado")
	public static void clickPagar(Channel channel, WebDriver driver) {
		PageMercpagoConf.clickPagar(driver);
		(new PageResultPagoStpV(TypePago.Mercadopago, channel, driver)).validaIsPageUntil(30);
	}
}