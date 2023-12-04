package com.mng.robotest.testslegacy.pageobject.shop.modales;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import java.util.Arrays;
import java.util.List;

import com.mng.robotest.tests.domains.base.PageBase;

public class ModalsSubscriptions extends PageBase {

	public enum InitialModal {
		MANGO_LIKES_YOU(
			"//div[@class='superfan-modal']/div[@class='modal-container']",
			"//span[@class='modal-close-icon']"),
		
		NEWSLETTER(
			"//micro-frontend[@id='newsletterSubscriptionModal']",
			"//button[@data-testid='newsletterSubscriptionModal.nonModal.close']");
		
		private final String xpath;
		private final String xpathClose;
		private InitialModal(String xpath, String xpathClose) {
			this.xpath = xpath;
			this.xpathClose = xpathClose;
		}
		
		public String getXPath() {
			return xpath;
		}
		public String getXPathClose() {
			return xpathClose;
		}
	}
	
	public List<InitialModal> getModalsCollection() {
		return Arrays.asList(InitialModal.values());
	}
	
	public boolean isVisible(InitialModal modal) {
		return state(VISIBLE, modal.getXPath()).check();
	}
	
	public void close(InitialModal modal) {
		click(modal.getXPathClose()).exec();
	}
	
	public void closeAllIfVisible() {
		for (InitialModal modal : getModalsCollection()) { 
			if (isVisible(modal)) {
				close(modal);
			}
		}
	}
}
