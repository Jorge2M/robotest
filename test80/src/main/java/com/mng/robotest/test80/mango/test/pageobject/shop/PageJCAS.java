package com.mng.robotest.test80.mango.test.pageobject.shop;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.mng.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;
import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;


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

	/**
	 * Realiza el proceso de identificación en la página de Jasig CAS
	 */
	public static void identication(WebDriver driver, String usuario, String password) throws Exception {
		inputCredenciales(usuario, password, driver);
		clickCaptchaIfPresent(driver);
		clickButtonLogin(driver);
	}

	public static void inputCredenciales(String usuario, String password, WebDriver driver) {
		driver.findElement(By.xpath(XPathInputUser)).sendKeys(usuario);
		driver.findElement(By.xpath(XPathInputPass)).sendKeys(password);
	}
	
	public static void clickCaptchaIfPresent(WebDriver driver) throws Exception {
		By byIframe = By.xpath(XPathIframeCaptcha);
		if (state(Present, byIframe, driver).check()) {
			try {
				driver.switchTo().frame(driver.findElement(byIframe));
				WebdrvWrapp.clickAndWaitLoad(driver, By.xpath(XPathRadioCaptchaWithinIframe));
				state(Visible, By.xpath(XPathRecaptchaCheckedWithinIframe), driver).wait(5).check();
			}
			finally {
				driver.switchTo().defaultContent();
			}
		}
	}

	public static void clickButtonLogin(WebDriver driver) throws Exception {
		clickAndWaitLoad(driver, By.xpath(XPathButtonLogin));
	}
}
