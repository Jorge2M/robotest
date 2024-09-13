package com.mng.robotest.tests.domains.compra.pageobjects.mobile;

import com.mng.robotest.tests.domains.base.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

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
		state(VISIBLE, XP_LINK_VER_BOLSA).wait(2).check();
		click(XP_LINK_VER_BOLSA).exec();
	}

}
