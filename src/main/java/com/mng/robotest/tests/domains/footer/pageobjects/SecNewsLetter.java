package com.mng.robotest.tests.domains.footer.pageobjects;

import org.openqa.selenium.WebElement;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.testslegacy.pageobject.shop.modales.ModalsSubscriptions;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class SecNewsLetter extends PageBase {

	private static final String XP_CAPA_NEWS_LETTER = "//micro-frontend[@name[contains(.,'newsletterSubscriptionFooter')]]";
	private static final String XP_NEWS_LETTER_MSG = XP_CAPA_NEWS_LETTER + "//p[@class[contains(.,'sg-text-action')]]";
	private static final String XP_TEXT_AREA_MAIL_SUSCRIPTION = XP_CAPA_NEWS_LETTER + "//input[@name='mail' or @name='email']";
	
	public String getNewsLetterMsgText() {
		try {
			WebElement titleNws = getElement(XP_NEWS_LETTER_MSG);
			if (titleNws!=null) {
				return getElement(XP_NEWS_LETTER_MSG).getText();
			}
		}
		catch (Exception e) {
			//Retornamos ""
		}
		return "";
	}
	
	public boolean newsLetterMsgContains(String literal) {
		return getNewsLetterMsgText().contains(literal);
	}

	public void clickFooterSuscripcion() {
		new ModalsSubscriptions().closeAllIfVisible();
		new SecFooter().moveTo();
		
		String xpathLink = XP_TEXT_AREA_MAIL_SUSCRIPTION;
		state(Visible, xpathLink).wait(2).check();
		click(xpathLink).exec();
	}

}
