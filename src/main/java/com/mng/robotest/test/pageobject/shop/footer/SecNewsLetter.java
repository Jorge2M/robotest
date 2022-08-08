package com.mng.robotest.test.pageobject.shop.footer;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.pageobject.shop.modales.ModalClubMangoLikes;


public class SecNewsLetter extends PageObjTM {

	private final AppEcom app;
	
	private static final String XPATH_CAPA_NEWS_LETTER = "//micro-frontend[@name[contains(.,'newsletterSubscriptionFooter')]]";
	private static final String XPATH_NEWS_LETTER_MSG = XPATH_CAPA_NEWS_LETTER + "//p[@class[contains(.,'sg-text-action')]]";
	private static final String XPATH_TEXT_AREA_MAIL_SUSCRIPTION = XPATH_CAPA_NEWS_LETTER + "//input[@name='mail' or @name='email']";
	
	public SecNewsLetter(AppEcom app) {
		this.app = app;
	}
	
	public String getNewsLetterMsgText() {
		By byMsg = By.xpath(XPATH_NEWS_LETTER_MSG);
		try {
			WebElement titleNws = driver.findElement(byMsg);
			if (titleNws!=null) {
				return driver.findElement(byMsg).getText();
			}
		}
		catch (Exception e) {
			//Retornamos ""
		}
		return "";
	}
	
	public boolean newsLetterMsgContains(String literal) {
		return (getNewsLetterMsgText().contains(literal));
	}

	public void clickFooterSuscripcion() throws Exception {
		ModalClubMangoLikes.closeModalIfVisible(driver);
		SecFooter secFooter = new SecFooter(app);
		secFooter.moveTo();
		
		By byLink = By.xpath(XPATH_TEXT_AREA_MAIL_SUSCRIPTION);
		state(State.Visible, byLink).wait(2).check();
		click(byLink).exec();
	}

}
