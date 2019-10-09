package com.mng.robotest.test80.mango.test.stpv.shop.checkout.d3d;

import org.openqa.selenium.WebDriver;
import com.mng.testmaker.utils.State;
import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.d3d.PageD3DLogin;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

public class PageD3DLoginStpV {
    
	@Validation (
		description="Aparece la página de identificación D3D (la esperamos hasta #{maxSecondsWait} segundos)",
		level=State.Info,
		avoidEvidences=true)
    public static boolean validateIsD3D(int maxSecondsWait, WebDriver driver) {
		return (PageD3DLogin.isPageUntil(maxSecondsWait, driver));
    }
	
	@Validation (
		description="Es visible el importe total de la operación #{importeTotal}",
		level=State.Warn)
    public static boolean isImporteVisible(String importeTotal, String codPais, WebDriver driver) {
		return (ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, driver));
    }
    
	@Step (
		description="Autenticarse en D3D con #{user} / #{password}", 
        expected="Aparece la página de resultado OK")
    public static void loginAndClickSubmit(String user, String password, WebDriver driver) throws Exception {
        PageD3DLogin.inputUserPassword(user, password, driver);
        PageD3DLogin.clickButtonSubmit(driver);
    }
}
