package com.mng.robotest.tests.domains.micuenta.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.loyalty.pageobjects.utils.UtilsLoyaltyPage;

public class PageMiCuenta extends PageBase {
	
	private static final String XP_LOYALTY_POINTS = "//*[@data-testid='loyaltyMyAccount.MyAccountInfo.info']//p[2]";
	
	public enum Link {
		MIS_DATOS("//a[@href[contains(.,'account/personalinfo')]]"),
		MIS_COMPRAS("//a[@href[contains(.,'/mypurchases')]]"),
		MIS_DIRECCIONES("//*[@data-testid[contains(.,'myAddresses.link')]]"),
		SUSCRIPCIONES("//a[@href[contains(.,'account/suscriptions')]]"),
		DEVOLUCIONES("//span[@data-event-category='devoluciones']"),
		REEMBOLSOS("//a[@data-event-category='mi-cuenta-reembolsos']");
	
		private String xpath;
		private Link(String xpath) {
			this.xpath = xpath;
		}
		public String getXPath() {
			return xpath;
		}
		
	}
	public boolean isPage(int seconds) {
		return state(VISIBLE, Link.MIS_DATOS.getXPath()).wait(seconds).check();
	}
	
	public void click(Link link) {
		click(link.getXPath()).exec();
		if (!state(INVISIBLE, link.getXPath()).wait(1).check()) {
			click(link.getXPath()).exec();
		}
	}
	
	public int getNumberPoints() {
		if (state(VISIBLE, XP_LOYALTY_POINTS).wait(2).check()) {
			String textPoints = getElement(XP_LOYALTY_POINTS).getText();
			return UtilsLoyaltyPage.getPointsFromLiteral(textPoints);
		}
		return 0;
	}
	
}
