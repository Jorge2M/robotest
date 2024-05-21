package com.mng.robotest.tests.domains.compranew.pageobjects;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.testslegacy.beans.Pago;

public class PageCheckoutPayment extends PageBase {

	private final SecDeliveryMethod sDeliveryMethod = new SecDeliveryMethod();
	private final SecPaymentMethod sPaymentMethod = new SecPaymentMethod();
	
	private static final String XP_PAYNOW_BUTTON = "//*[@data-testid='checkout.step2.button.confirmPayment']"; 
	
	public boolean isPage(int seconds) {
		return sPaymentMethod.isVisible(seconds);
	}
	
	public void selectSaveCard() {
		sPaymentMethod.selectSaveCard();
	}
	
	public void selectSavedCard() {
		sPaymentMethod.selectSavedCard();
	}
	
	public void clickPayNow() {
		click(XP_PAYNOW_BUTTON).exec();
	}
	
	public void unfoldCardFormulary() {
		sPaymentMethod.unfoldCardFormulary();
	}
	
	public void inputCard(Pago pago) {
		sPaymentMethod.inputCard(pago);
	}

}
