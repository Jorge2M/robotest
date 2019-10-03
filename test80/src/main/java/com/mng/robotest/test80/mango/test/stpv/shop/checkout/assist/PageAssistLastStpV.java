package com.mng.robotest.test80.mango.test.stpv.shop.checkout.assist;

import org.openqa.selenium.WebDriver;

import com.mng.testmaker.annotations.step.Step;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.assist.PageAssistLast;

public class PageAssistLastStpV {

	@Step (
		description="Seleccionar el botón de Submit", 
        expected="Aparece la página de resultado de Mango")
    public static void clickSubmit(WebDriver driver) throws Exception {
		PageAssistLast.clickButtonSubmit(driver);
    }
}
