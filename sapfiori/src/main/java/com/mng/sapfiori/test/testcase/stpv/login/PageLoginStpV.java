package com.mng.sapfiori.test.testcase.stpv.login;

import org.openqa.selenium.WebDriver;

import com.mng.sapfiori.test.testcase.stpv.iconsmenu.PageIconsMenuStpV;
import com.mng.sapfiori.test.testcase.webobject.login.PageLogin;
import com.mng.testmaker.boundary.aspects.step.Step;

public class PageLoginStpV {

	private final PageLogin pageLogin;
	
	private PageLoginStpV(WebDriver driver) {
		this.pageLogin = PageLogin.getNew(driver);
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
    public PageIconsMenuStpV inputCredentialsAndEnter(String login, String password) throws Exception {
    	pageLogin.inputCredentials(login, password);
    	String codeSpanish = "ES";
    	pageLogin.selectIdioma(codeSpanish);
    	PageIconsMenuStpV pageInitialStpV = PageIconsMenuStpV.getNew(
    		pageLogin.clickAccederAlSistema());
    	
    	pageInitialStpV.checkIsInitialPageSpanish(2);
    	return pageInitialStpV;
    }
}
