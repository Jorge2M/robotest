package com.mng.robotest.tests.domains.loyalty.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.VISIBLE;

import com.mng.robotest.tests.domains.base.PageBase;

public class PageMLYUnirmeAlClub extends PageBase {

	private static final String XP_BUTTON_UNIRME = "//button[@data-testid='landingMangoLikesYou.signUp.modalButton.open']";
	private static final String XP_CAPA_CREAR_CUENTA = "//*[@data-testid='modal']";
	private static final String XP_INPUT_EMAIL = "//input[@data-testid='registry.emailInput.text']";
	private static final String XP_INPUT_PASSWORD = "//input[@data-testid='registry.passwordInput.text']";
	private static final String XP_INPUT_MOBIL = "//input[@data-testid='registry.phoneInput.text']";
	private static final String XP_CREAR_CUENTA_BUTTON = "//button[@data-testid='registry.registryButton.registry']";
	
	public boolean isPage(int seconds) {
		return state(VISIBLE, XP_BUTTON_UNIRME).wait(seconds).check();
	}
	
	public void selectUnirmeAlClub() {
		click(XP_BUTTON_UNIRME).exec();
	}
	
	public boolean isVisibleCrearCuenta(int seconds) {
		return state(VISIBLE, XP_CAPA_CREAR_CUENTA).wait(seconds).check();
	}
	
	public void inputEmail(String email) {
		getElement(XP_INPUT_EMAIL).sendKeys(email);
	}
	
	public void inputPassword(String password) {
		getElement(XP_INPUT_PASSWORD).sendKeys(password);
	}
	
	public void inputMobil(String mobil) {
		getElement(XP_INPUT_MOBIL).sendKeys(mobil);
	}

	public void clickCrearCuenta() {
		click(XP_CREAR_CUENTA_BUTTON).exec();
	}
	
}
