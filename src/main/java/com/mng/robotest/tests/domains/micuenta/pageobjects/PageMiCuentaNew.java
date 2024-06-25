package com.mng.robotest.tests.domains.micuenta.pageobjects;

import java.util.Optional;

import com.mng.robotest.tests.domains.micuenta.repository.PurchasesRepositoryClient;

public class PageMiCuentaNew extends PageMiCuenta {

	private static final String XP_MYACCOUNT_LINKS = "//*[@data-testid='myAccount.links']";
	private static final String XP_LOYALTY_POINTS = "//a[@href[contains(.,'mango-likes-you')]]/div/p[2]";
	private static final String XP_PURCHASE_LINE = "//div[@data-testid[contains(.,'MNG')]]";	
	
	@Override
	String getXPath(LinkMiCuenta link) {
		switch (link) {
		case MIS_COMPRAS:
			return XP_MYACCOUNT_LINKS + "//a[@href[contains(.,'mypurchases')]]";
		case DEVOLUCIONES:
			return XP_MYACCOUNT_LINKS + "//a[@href[contains(.,'my-returns')] or @href[contains(.,'/myreturns')]]";
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
	public boolean isPurchase(String idOrder, int seconds) {
		for (int i=0; i<seconds; i++) {
			if (isPurchaseCheck(idOrder)) {
				return true;
			}
			waitMillis(1000);
		}
		return false;
	}

	private boolean isPurchaseCheck(String idOrder) {
		var idPurchaseScreenOpt = getCodeFirstPurchase();
		if (idPurchaseScreenOpt.isEmpty()) {
			return false;
		}
		
		var idPurchaseScreen = idPurchaseScreenOpt.get();
		var idFirstOrderServiceOpt = getCodeFirstOrder(idPurchaseScreen);
		if (idFirstOrderServiceOpt.isEmpty()) {
			return false;
		}
		
		return idOrder.compareTo(idFirstOrderServiceOpt.get())==0;
	}
	
	private Optional<String> getCodeFirstPurchase() {
		var purchaseOpt = findElement(XP_PURCHASE_LINE);
		if (purchaseOpt.isEmpty()) {
			return Optional.empty();
		}
		var purchaseId = purchaseOpt.get().getAttribute("data-testid");
		return Optional.of(purchaseId);
	}
	
	private Optional<String> getCodeFirstOrder(String idPurchase) {
		var purchasesClient = new PurchasesRepositoryClient(
				inputParamsSuite.getUrlBase(),
				dataTest.getUserConnected(),
				dataTest.getPasswordUser());
		
		var purchaseOpt = purchasesClient.getPurchase(idPurchase);
		if (purchaseOpt.isEmpty()) {
			return Optional.empty();
		}
		
		return Optional.of(purchaseOpt.get().getFirstOrderId()); 
	}
	
}
