package com.mng.robotest.tests.domains.compra.pageobjects.mobile;

import com.mng.robotest.tests.domains.base.PageBase;

public class SecCabeceraCheckoutMobil extends PageBase {

	private SecBolsaCheckoutMobil secBolsa = new SecBolsaCheckoutMobil();
	
	private static final String XP_LINK_VER_BOLSA = "//*[@data-testid='button.open.checkout.bag']";
	
	public void showBolsa() {
		if (!secBolsa.isVisible()) {
			clickVerBolsa();
		}
	}
	
	public void unshowBolsa() {
		if (secBolsa.isVisible()) {
			secBolsa.close();
		}
	}
	
	private void clickVerBolsa() {
		click(XP_LINK_VER_BOLSA).exec();
	}

}
