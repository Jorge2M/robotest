package com.mng.robotest.tests.domains.transversal.prehome.pageobjects;

import org.openqa.selenium.By;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.repository.secrets.GetterSecrets;
import com.mng.robotest.tests.repository.secrets.GetterSecrets.SecretType;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageJCAS extends PageBase {
	
	private static final String XP_INPUT_USER = "//input[@id='username']";
	private static final String XP_INPUT_PASS = "//input[@id='password']";
	private static final String XP_IFRAME_CAPTCHA = "//iframe[@src[contains(.,'recaptcha')]]";
	private static final String XP_RADIO_CAPTCHA_WITHIN_IFRAME = "//span[@id='recaptcha-anchor']/div";
	private static final String XP_RECAPTCHA_CHECKED_WITHIN_IFRAME = "//span[@class[contains(.,'recaptcha-checkbox-checked')]]";
	private static final String XP_BUTTON_LOGIN = "//input[@value='INICIAR SESIÓN' or @value='LOGIN']";

	public boolean thisPageIsShown() {
		return (driver.getTitle().contains("Central Authentication Service"));
	}
	
	public void identJCASifExists() {
		waitLoadPage();
		if (thisPageIsShown()) {
			var secret = GetterSecrets.factory().getCredentials(SecretType.MANTO_USER);
			identication(secret.getUser(), secret.getPassword());
		}
	}  

	public void identication(String usuario, String password) {
		inputCredenciales(usuario, password);
		clickCaptchaIfPresent();
		clickButtonLogin();
	}

	public void inputCredenciales(String usuario, String password) {
		getElement(XP_INPUT_USER).sendKeys(usuario);
		getElement(XP_INPUT_PASS).sendKeys(password);
	}
	
	public void clickCaptchaIfPresent() {
		By byIframe = By.xpath(XP_IFRAME_CAPTCHA);
		if (state(Present, byIframe).check()) {
			try {
				driver.switchTo().frame(driver.findElement(byIframe));
				click(XP_RADIO_CAPTCHA_WITHIN_IFRAME).exec();
				state(Visible, XP_RECAPTCHA_CHECKED_WITHIN_IFRAME).wait(5).check();
			}
			finally {
				driver.switchTo().defaultContent();
			}
		}
	}

	public void clickButtonLogin() {
		click(XP_BUTTON_LOGIN).exec();
	}
	
}
