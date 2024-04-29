package com.mng.robotest.tests.domains.micuenta.pageobjects;

public class PageMiCuentaOld extends PageMiCuenta {
	
	private static final String XP_LOYALTY_POINTS = "//*[@data-testid='loyaltyMyAccount.MyAccountInfo.info']//p[2]";
	
	@Override
	String getXPath(LinkMiCuenta link) {
		switch (link) {
			case MIS_DATOS: 
				return "//a[@data-event-category='mis-datos']";
			case MIS_COMPRAS:
				return "//a[@data-event-category='mis-compras']";
			case MIS_DIRECCIONES:
				return "//*[@data-testid[contains(.,'myAddresses.link')]]";
			case SUSCRIPCIONES: 
				return "//a[@data-event-category='suscripciones']";
			case DEVOLUCIONES: 
				return "//a[@data-event-category='devoluciones']";
			case REEMBOLSOS:
				return "//a[@data-event-category='mi-cuenta-reembolsos']";
			default:
				throw new UnsupportedOperationException();
		}
	}
	
	@Override
	String getXPathNumberPoints() {
		return XP_LOYALTY_POINTS;
	}
	
}
