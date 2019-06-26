package com.mng.robotest.test80.mango.test.stpv.shop.checkout.ideal;

import org.openqa.selenium.WebDriver;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.ideal.SecIdeal;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.ideal.SecIdeal.BancoSeleccionado;

public class SecIdealStpV {

	@Validation (
		description="Aparece el bloque de selección del banco",
		level=State.Defect)
    public static boolean validateIsSectionOk(Channel channel, WebDriver driver) {
		int maxSecondsWait = 1;
		return (SecIdeal.isVisibleSelectorOfBank(channel, maxSecondsWait, driver));
    }
    
    /**
     * @param el valor de las opciones del banco a seleccionar contiene el "value" del listBox...
     */
	@Step (
		description="Seleccionar el banco \"#{bancoSeleccionado}\"", 
        expected="El resultado es correcto")
    public static void clickBanco(BancoSeleccionado bancoSeleccionado, Channel channel, WebDriver driver) {
		SecIdeal.clickBancoByValue(driver, channel, bancoSeleccionado);
    }
}