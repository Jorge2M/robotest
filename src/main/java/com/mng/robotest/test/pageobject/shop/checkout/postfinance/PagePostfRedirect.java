package com.mng.robotest.test.pageobject.shop.checkout.postfinance;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PagePostfRedirect extends PageObjTM {

	private final static String XPathButtonOK = "//form/input[@type='button' and @value[contains(.,'OK')]]";
	
	public PagePostfRedirect(WebDriver driver) {
		super(driver);
	}
	
	/**
	 * @return si estamos en la página de redirección con el botón OK que aparece después de introducir el código de seguridad y pulsar "Continuar"
	 */
	public boolean isPresentButtonOk() {
		return (state(Present, By.xpath(XPathButtonOK)).check());
	}
	
	/**
	 * @return si es invisible (ha desaparecido) la capa de redirección con el botón OK que aparece después de introducir el código de seguridad y pulsar "Continuar"
	 */
	public boolean isInvisibleButtonOkUntil(int seconds) {
		boolean invisibility = false;
		try { 
			new WebDriverWait(driver, seconds).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(XPathButtonOK)));
			invisibility = true;
		}
		catch (Exception e) {
			/*
			 * Continuamos pues existe la posibilidad de que el botón esté en el estado deseado
			 */
		}
		
		return invisibility;
	}
}
