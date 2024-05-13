package com.mng.robotest.tests.domains.micuenta.pageobjects;

import com.mng.robotest.testslegacy.datastored.DataPedido;

public class PageMiCuentaNew extends PageMiCuenta {

	private static final String XP_MYACCOUNT_LINKS = "//*[@data-testid='myAccount.links']";
	private static final String XP_LOYALTY_POINTS = "//a[@href[contains(.,'mango-likes-you')]]/div/p[2]";
	
	@Override
	String getXPath(LinkMiCuenta link) {
		switch (link) {
		case MIS_COMPRAS:
			return XP_MYACCOUNT_LINKS + "//a[@href[contains(.,'mypurchases')]]";
		case DEVOLUCIONES:
			return XP_MYACCOUNT_LINKS + "//a[@href[contains(.,'myreturns')]]";
		case REEMBOLSOS:
			return XP_MYACCOUNT_LINKS + "//a[@href[contains(.,'refunds')]]";
		case CLUB_MLY:
			return XP_MYACCOUNT_LINKS + "//a[@href[contains(.,'mangolikesyou')]]";
		case MIS_DATOS:
			return XP_MYACCOUNT_LINKS + "//a[@href[contains(.,'personalinfo')]]";
		case MIS_DIRECCIONES:
			return XP_MYACCOUNT_LINKS + "//a[@href[contains(.,'myaddresses')]]";
		case SUSCRIPCIONES:
			return XP_MYACCOUNT_LINKS + "//a[@href[contains(.,'suscriptions')]]";
		case AYUDA: 
			return XP_MYACCOUNT_LINKS + "//a[@href[contains(.,'help')]]";
		case FAVORITOS:
			return XP_MYACCOUNT_LINKS + "//a[@href[contains(.,'favorites')]]";
		default:
			throw new UnsupportedOperationException();
		}
	}
	
	@Override
	String getXPathNumberPoints() {
		return XP_LOYALTY_POINTS;
	}
	
	@Override
	public String checkIsPedido(DataPedido dataPedido) {
		throw new UnsupportedOperationException();
	}
	
}
