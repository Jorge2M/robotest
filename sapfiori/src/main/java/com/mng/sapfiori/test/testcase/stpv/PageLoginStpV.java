package com.mng.sapfiori.test.testcase.stpv;

import org.openqa.selenium.WebDriver;

import com.mng.sapfiori.test.testcase.pageobject.PageLogin;
import com.mng.testmaker.boundary.aspects.step.Step;

public class PageLoginStpV {

	private final PageLogin pageLogin;
	private final WebDriver driver;
	
	private PageLoginStpV(WebDriver driver) {
		this.pageLogin = PageLogin.getNew(driver);
		this.driver = driver;
	}
	
	public static PageLoginStpV getNew(WebDriver driver) {
		return new PageLoginStpV(driver);
	}
	
    @Step (
    	description=
    		"Introducir el usuario / password<br>" + 
    		"seleccionar el idioma <b>Español</b><br>" + 
    		"Pulsar el botón <b>Acceder al sistema</b>", 
        expected=
    		"Se accede a la aplicación SAP")
    public void inputCredentialsAndEnter(String login, String password) throws Exception {
    	pageLogin.inputCredentials(login, password);
    	String codeSpanish = "ES";
    	pageLogin.selectIdioma(codeSpanish);
    	pageLogin.clickAccederAlSistema();
    	PageInitialStpV.getNew(driver).checkIsInitialPageSpanish();
    }
}
