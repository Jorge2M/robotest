package com.mng.robotest.tests.domains.compranew.pageobjects;

import com.mng.robotest.tests.domains.base.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageIdentCheckoutNew extends PageBase {

	private static final String XP_INPUT_MAIL = "//*[@data-testid='login.emailInput.text']";
	private static final String XP_INPUT_PASSWORD = "//*[@data-testid='login.passwordInput.text']";
	private static final String XP_SIGNIN_BUTTON = "//*[@data-testid='login.loginButton.login']";
	private static final String XP_STAY_SIGNED_RADIO = "//*[@data-testid='login.stayLoggedInCheckbox.loginPersists']";
	
	private static final String XP_FORGOT_PASSWORD_LINK = "//*[@data-testid='login.recoveryPasswordLink.recoveryPassword']";
	private static final String XP_CREATE_ACCOUNT_BUTTON = "//a[@data-testid='purchaseAuthentication.createAccountLink']";
	private static final String XP_CONTINUE_AS_GUEST_BUTTON = "//a[@data-testid='purchaseAuthentication.guest.link.toDelivery']";
	
	public boolean isPage(int seconds) {
		return state(VISIBLE, XP_INPUT_MAIL).wait(seconds).check();
	}
	
	public void login(String mail, String password) {
		inputClearAndSendKeys(XP_INPUT_MAIL, mail);
		inputClearAndSendKeys(XP_INPUT_PASSWORD, password);
		click(XP_SIGNIN_BUTTON).exec();
	}
	
	public void clickStaySignedRadio() {
		click(XP_STAY_SIGNED_RADIO).exec();
	}
	
	public void clickForgotPasswordLink() {
		click(XP_FORGOT_PASSWORD_LINK).exec();
	}
	
	public void clickCreateAccountButton() {
		click(XP_CREATE_ACCOUNT_BUTTON).exec();
	}
	
	public void clickContinueAccountButton() {
		click(XP_CONTINUE_AS_GUEST_BUTTON).exec();
	}

}
