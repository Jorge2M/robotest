package com.mng.robotest.tests.domains.footer.pageobjects;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.testslegacy.pageobject.shop.modales.ModalsSubscriptions;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class SecNewsLetter extends PageBase {

	private static final String XP_CAPA_NEWS_LETTER_OLD = "//micro-frontend[@name[contains(.,'newsletterSubscriptionFooter')]]";
	private static final String XP_NEWS_LETTER_MSG_OLD = XP_CAPA_NEWS_LETTER_OLD + "//p[@class[contains(.,'sg-text-action')]]";
	private static final String XP_INPUT_EMAIL_OLD = XP_CAPA_NEWS_LETTER_OLD + "//input[@name='mail' or @name='email']";
	
	private static final String XP_INPUT_EMAIL_GENESIS = "//*[@data-testid='newsletter.subscription.emailInput.text']";
	private static final String XP_NEWS_LETTER_MSG_GENESIS = XP_INPUT_EMAIL_GENESIS + "/ancestor::h2";
	
	private String getXPathInputEmail() {
		return "(" + XP_INPUT_EMAIL_OLD + " | " + XP_INPUT_EMAIL_GENESIS + ")"; 
	}
	
	private String getXPathNewsLetterMsg() {
		return "(" + XP_NEWS_LETTER_MSG_OLD + " | " + XP_NEWS_LETTER_MSG_GENESIS + ")";
	}
	
	public String getNewsLetterMsgText() {
		var xpathMessage = getXPathNewsLetterMsg();
		var titleNwsOpt = findElement(xpathMessage);
		if (titleNwsOpt.isPresent()) {
			return titleNwsOpt.get().getText();
		} else {
			return "";
		}
	}
	
	public boolean newsLetterMsgContains(String literal) {
		return getNewsLetterMsgText().contains(literal);
	}

	public void clickFooterSuscripcion() {
		new ModalsSubscriptions().closeAllIfVisible();
		new SecFooter().moveTo();
		
		String xpathLink = getXPathInputEmail();
		state(VISIBLE, xpathLink).wait(2).check();
		click(xpathLink).exec();
	}

}
