package com.mng.robotest.test80.mango.test.stpv.shop.checkout.d3d;

import org.openqa.selenium.WebDriver;
import com.mng.testmaker.utils.State;
import com.mng.testmaker.annotations.step.Step;
import com.mng.testmaker.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.d3d.PageD3DJPTestSelectOption;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.d3d.PageD3DJPTestSelectOption.OptionD3D;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;


public class PageD3DJPTestSelectOptionStpV {
    
	@Validation (
		description="Aparece la página de Test correspondiente al D3D de JPMorgan (la esperamos hasta #{maxSecondsWait} segundos)",
		level=State.Warn)
    public static boolean validateIsD3D(int maxSecondsWait, WebDriver driver) {
		return (PageD3DJPTestSelectOption.isPageUntil(maxSecondsWait, driver));
    }	
	
	@Validation (
		description="Aparece el importe #{importeTotal} de la operación",
		level=State.Warn)
    public static boolean isImporteVisible(String importeTotal, String codPais, WebDriver driver) {
		return (ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, driver));
    }
    
	@Step (
		description="Seleccionamos la opción \"Successful\" y clickamos en el botón \"Submit\"", 
        expected="Aparece la página de resultado OK")
    public static void clickSubmitButton(WebDriver driver) throws Exception {
        PageD3DJPTestSelectOption.selectOption(OptionD3D.Successful, driver);
        PageD3DJPTestSelectOption.clickSubmitButton(driver);
    }
}
