package com.mng.robotest.tests.domains.footer.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import static com.mng.robotest.tests.domains.legal.legaltexts.FactoryLegalTexts.PageLegalTexts.MANGO_CARD_LEGAL_TEXTS;

import com.mng.robotest.tests.domains.base.PageBase;

public class PageMangoCard extends PageBase implements PageFromFooter {
	
	private static final String XPATH_GO_MANGO_CARD_BUTTON = "//span[@class='menu-link-button']";
	private static final String XPATH_GO_MANGO_CARD_BUTTON_MOBILE = "//a[@id='getCardLink']";
	private static final String XPATH_LINK_SOL_MANGOCARD_PAGE1 = "//button[@class[contains(.,'form-submit')]]";
	private static final String XPATH_NAME_FIELD = "//input[@id='datNombre']";
	private static final String XPATH_FIRST_SURNAME_FIELD = "//input[@id='datApellido1']";
	private static final String XPATH_SECOND_SURNAME_FIELD = "//input[@id='datApellido2']";
	private static final String XPATH_MOBILE_FIELD = "//input[@id='datTelMovil']";
	private static final String XPATH_MAIL_FIELD = "//input[@id='datEmail']";
	private static final String XPATH_FOR_IDPAGE = "//h2[@class='section-title']";
	
	public PageMangoCard() {
		super(MANGO_CARD_LEGAL_TEXTS);
	}
	
	@Override
	public String getName() {
		return "Nueva Mango Card";
	}
	
	@Override
	public boolean isPageCorrectUntil(int seconds) {
		return state(Present, XPATH_FOR_IDPAGE).wait(seconds).check();
	}
	
	public void clickOnWantMangoCardNow() {
		if (channel.isDevice()) {
			click(XPATH_GO_MANGO_CARD_BUTTON_MOBILE).exec();
		} else {
			click(XPATH_GO_MANGO_CARD_BUTTON).exec();
		}
	}
	
	public void clickToGoSecondMangoCardPage() {
		click(XPATH_LINK_SOL_MANGOCARD_PAGE1).exec();
	}

	public boolean isPresentNameField() {
		return state(Present, XPATH_NAME_FIELD).check();
	}
	
	public boolean isPresentFirstSurnameField() {
		return state(Present, XPATH_FIRST_SURNAME_FIELD).check();
	}
	
	public boolean isPresentSecondSurnameField() {
		return state(Present, XPATH_SECOND_SURNAME_FIELD).check();
	}
	
	public boolean isPresentMobileField() {
		return state(Present, XPATH_MOBILE_FIELD).check();
	}
	
	public boolean isPresentMailField() {
		return state(Present, XPATH_MAIL_FIELD).check();
	}
	
	public boolean isPresentButtonSolMangoCardNow() {
		return state(Present, XPATH_LINK_SOL_MANGOCARD_PAGE1).check();
	}
}