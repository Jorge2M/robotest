package com.mng.robotest.domains.micuenta.pageobjects;

import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageMiCuenta extends PageBase {
	
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
	public boolean isPageUntil(int seconds) {
		return state(Visible, Link.MIS_DATOS.getXPath()).wait(seconds).check();
	}
	
	public void click(Link link) {
		click(link.getXPath()).exec();
		if (!state(Invisible, link.getXPath()).wait(1).check()) {
			click(link.getXPath()).exec();
		}
	}
}
