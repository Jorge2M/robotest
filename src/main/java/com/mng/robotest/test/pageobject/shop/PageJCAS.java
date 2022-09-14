package com.mng.robotest.test.pageobject.shop;

import org.openqa.selenium.By;

import com.mng.robotest.domains.transversal.PageBase;
import com.mng.robotest.test.utils.awssecrets.GetterSecrets;
import com.mng.robotest.test.utils.awssecrets.Secret;
import com.mng.robotest.test.utils.awssecrets.GetterSecrets.SecretType;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageJCAS extends PageBase {
	
	private static final String XPATH_INPUT_USER = "//input[@id='username']";
	private static final String XPATH_INPUT_PASS = "//input[@id='password']";
	private static final String XPATH_IFRAME_CAPTCHA = "//iframe[@src[contains(.,'recaptcha')]]";
	private static final String XPATH_RADIO_CAPTCHA_WITHIN_IFRAME = "//span[@id='recaptcha-anchor']/div";
	private static final String XPATH_RECAPTCHA_CHECKED_WITHIN_IFRAME = "//span[@class[contains(.,'recaptcha-checkbox-checked')]]";
	private static final String XPATH_BUTTON_LOGIN = "//input[@value='INICIAR SESIÓN' or @value='LOGIN']";

	public boolean thisPageIsShown() {
		return (driver.getTitle().contains("Central Authentication Service"));
	}
	
	public void identJCASifExists() {
		waitLoadPage();
		if (thisPageIsShown()) {
			Secret secret = GetterSecrets.factory().getCredentials(SecretType.MANTO_USER);
			identication(secret.getUser(), secret.getPassword());
		}
	}  

	public void identication(String usuario, String password) {
		inputCredenciales(usuario, password);
		clickCaptchaIfPresent();
		clickButtonLogin();
	}

	public void inputCredenciales(String usuario, String password) {
		getElement(XPATH_INPUT_USER).sendKeys(usuario);
		getElement(XPATH_INPUT_PASS).sendKeys(password);
	}
	
	public void clickCaptchaIfPresent() {
		By byIframe = By.xpath(XPATH_IFRAME_CAPTCHA);
		if (state(Present, byIframe).check()) {
			try {
				driver.switchTo().frame(driver.findElement(byIframe));
				click(XPATH_RADIO_CAPTCHA_WITHIN_IFRAME).exec();
				state(Visible, XPATH_RECAPTCHA_CHECKED_WITHIN_IFRAME).wait(5).check();
			}
			finally {
				driver.switchTo().defaultContent();
			}
		}
	}

	public void clickButtonLogin() {
		click(XPATH_BUTTON_LOGIN).exec();
	}
}
