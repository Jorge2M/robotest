package com.mng.robotest.test80.mango.test.stpv.shop.checkout;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;

public class Page1IdentCheckoutStpV {
    
    public static SecSoyNuevoStpV secSoyNuevo;
    
    @SuppressWarnings("static-access")
    @Validation (
    	description="Aparece el formulario correspondiente a la identificación (lo esperamos hasta #{maxSeconds} segs)",
    	level=State.Defect)
    public static boolean validateIsPage(int maxSeconds, WebDriver driver) {
        return (Page1IdentCheckout.secSoyNuevo.isFormIdentUntil(driver, maxSeconds));
    }
}
