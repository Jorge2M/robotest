package com.mng.robotest.domains.compra.payments.eps.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.domains.transversal.PageBase;

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
	
	private static final String XPATH_LOGO_EPS = "//img[@class='paymentMethodLogo' and @src[contains(.,'eps.png')]]";
	private static final String XPATH_CONTINUE_BUTTON = "//button[@value='pendingauthorised']";
	private static final String XPATH_SELECT_DELAY_AUTHORISED = "//select[@name='delaySelector']";
	
	private String getXPathOptionDelayAuthorised(TypeDelay typeDelay) {
		return (XPATH_SELECT_DELAY_AUTHORISED + "//option[text()[contains(.,'" + typeDelay.minutes + " minute')]]");
	}
	
	public boolean isPage() {
		return state(Visible, XPATH_LOGO_EPS).check();
	}
	
	public void selectDelayAuthorised(TypeDelay typeDelay) {
		String xpathOption = getXPathOptionDelayAuthorised(typeDelay);
		getElement(xpathOption).click();
	}

	public void clickButtonContinue() {
		click(XPATH_CONTINUE_BUTTON).exec();
	}
}
