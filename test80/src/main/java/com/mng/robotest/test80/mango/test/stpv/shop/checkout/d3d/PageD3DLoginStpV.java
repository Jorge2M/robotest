package com.mng.robotest.test80.mango.test.stpv.shop.checkout.d3d;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.d3d.PageD3DLogin;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

public class PageD3DLoginStpV {

	private final WebDriver driver;
	private final PageD3DLogin pageD3DLogin;
	
	public PageD3DLoginStpV(WebDriver driver) {
		this.driver = driver;
		this.pageD3DLogin = new PageD3DLogin(driver);
	}
	
	@Validation (
		description="Aparece la página de identificación D3D (la esperamos hasta #{maxSeconds} segundos)",
		level=State.Info,		
		avoidEvidences=true)
	public boolean validateIsD3D(int maxSeconds) {
		return (pageD3DLogin.isPageUntil(maxSeconds));
	}
	
	@Validation (
		description="Es visible el importe total de la operación #{importeTotal}",
		level=State.Warn)
    public boolean isImporteVisible(String importeTotal, String codPais) {
		return (ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, driver));
    }
    
	@Step (
		description="Autenticarse en D3D con #{user} / #{password}", 
        expected="Aparece la página de resultado OK")
    public void loginAndClickSubmit(String user, String password) {
        pageD3DLogin.inputUserPassword(user, password);
        pageD3DLogin.clickButtonSubmit();
    }
}
