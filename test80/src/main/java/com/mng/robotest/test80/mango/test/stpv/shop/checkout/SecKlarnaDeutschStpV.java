package com.mng.robotest.test80.mango.test.stpv.shop.checkout;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.SecKlarnaDeutsch;

public class SecKlarnaDeutschStpV {
    
	private final SecKlarnaDeutsch secKlarnaDeutsch;
	
	public SecKlarnaDeutschStpV(Channel channel, WebDriver driver) {
		this.secKlarnaDeutsch = new SecKlarnaDeutsch(channel, driver);
	}
	
	@Validation (
		description="Aparece el selector de la fecha de nacimiento (lo esperamos hasta un máximo de #{maxSecondsToWait} segundos)",
		level=State.Warn)
    public boolean validateIsSection(int maxSeconds) {
        return (secKlarnaDeutsch.isVisibleSelectDiaNacimientoUntil(maxSeconds));
    }
    
    /**
     * @param fechaNaci en formato "dd-mm-aaaa"
     */
	@Step (
		description="Introducimos la fecha de nacimiento<b>#{fechaNaci}</b> y marcamos el radio de <b>\"Acepto\"</b>", 
        expected="Los datos se informan correctamente")
    public void inputData(String fechaNaci) {
        secKlarnaDeutsch.selectFechaNacimiento(fechaNaci);
        secKlarnaDeutsch.clickAcepto();
    }
}
