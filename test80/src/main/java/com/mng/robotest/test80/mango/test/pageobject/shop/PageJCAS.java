package com.mng.robotest.test80.mango.test.pageobject.shop;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.utils.awssecrets.GetterSecrets;
import com.mng.robotest.test80.mango.test.utils.awssecrets.Secret;
import com.mng.robotest.test80.mango.test.utils.awssecrets.GetterSecrets.SecretType;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


/**
 * Clase que define la automatización de las diferentes funcionalidades de la página de autentificación de 
 * Jasig CAS que aparece en los entornos de test cuando se accede desde fuera 
 * @author jorge.munoz
 *
 */
public class PageJCAS {

	private static final String XPathInputUser = "//input[@id='username']";
	private static final String XPathInputPass = "//input[@id='password']";
	private static final String XPathIframeCaptcha = "//iframe[@src[contains(.,'recaptcha')]]";
	private static final String XPathRadioCaptchaWithinIframe = "//span[@id='recaptcha-anchor']/div";
	private static final String XPathRecaptchaCheckedWithinIframe = "//span[@class[contains(.,'recaptcha-checkbox-checked')]]";
	private static final String XPathButtonLogin = "//input[@value='INICIAR SESIÓN' or @value='LOGIN']";

	/**
	 * Función que nos indica si la página actualmente mostrada es la de autentificación mediante JasigCAS
	 * @return indicador de si es o no la página de Jasig CAS
	 */
	public static boolean thisPageIsShown(WebDriver driver) {
		return (driver.getTitle().contains("Central Authentication Service"));
	}
	
	public static void identJCASifExists(WebDriver driver) {
		waitForPageLoaded(driver);
		if (thisPageIsShown(driver)) {
			Secret secret = GetterSecrets.factory().getCredentials(SecretType.MANTO_USER);
			identication(driver, secret.getUser(), secret.getPassword());
		}
	}  

	/**
	 * Realiza el proceso de identificación en la página de Jasig CAS
	 */
	public static void identication(WebDriver driver, String usuario, String password) {
		inputCredenciales(usuario, password, driver);
		clickCaptchaIfPresent(driver);
		clickButtonLogin(driver);
	}

	public static void inputCredenciales(String usuario, String password, WebDriver driver) {
		driver.findElement(By.xpath(XPathInputUser)).sendKeys(usuario);
		driver.findElement(By.xpath(XPathInputPass)).sendKeys(password);
	}
	
	public static void clickCaptchaIfPresent(WebDriver driver) {
		By byIframe = By.xpath(XPathIframeCaptcha);
		if (state(Present, byIframe, driver).check()) {
			try {
				driver.switchTo().frame(driver.findElement(byIframe));
				click(By.xpath(XPathRadioCaptchaWithinIframe), driver).exec();
				state(Visible, By.xpath(XPathRecaptchaCheckedWithinIframe), driver).wait(5).check();
			}
			finally {
				driver.switchTo().defaultContent();
			}
		}
	}

	public static void clickButtonLogin(WebDriver driver) {
		click(By.xpath(XPathButtonLogin), driver).exec();
	}
}
