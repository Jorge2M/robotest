package com.mng.robotest.tests.domains.compra.payments.eps.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class PageEpsSimulador extends PageBase {

	public enum TypeDelay {
		ONE_MINUTES(1), 
		FIVE_MINUTES(5), 
		FIFTEEN_MINUTES(15), 
		SIXTY_MINUTES(60);
		
		private final int minutes;
		private TypeDelay(int minutes) {
			this.minutes = minutes;
		}
		
		public int getMinutes() {
			return minutes;
		}
	}
	
	private static final String XP_LOGO_EPS = "//img[@class='paymentMethodLogo' and @src[contains(.,'eps.png')]]";
	private static final String XP_CONTINUE_BUTTON = "//button[@value='pendingauthorised']";
	private static final String XP_SELECT_DELAY_AUTHORISED = "//select[@name='delaySelector']";
	
	private String getXPathOptionDelayAuthorised(TypeDelay typeDelay) {
		return (XP_SELECT_DELAY_AUTHORISED + "//option[text()[contains(.,'" + typeDelay.minutes + " minute')]]");
	}
	
	public boolean isPage() {
		return state(VISIBLE, XP_LOGO_EPS).check();
	}
	
	public void selectDelayAuthorised(TypeDelay typeDelay) {
		String xpathOption = getXPathOptionDelayAuthorised(typeDelay);
		getElement(xpathOption).click();
	}

	public void clickButtonContinue() {
		click(XP_CONTINUE_BUTTON).exec();
	}
}
