package com.mng.robotest.tests.domains.compranew.pageobjects;

import java.math.BigDecimal;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.testslegacy.beans.Pago;
import com.mng.robotest.testslegacy.utils.ImporteScreen;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageCheckoutPayment extends PageBase {

	private final SecDeliveryMethod sDeliveryMethod = new SecDeliveryMethod();
	private final SecPaymentMethod sPaymentMethod = new SecPaymentMethod();
	private final SecPromotion sPromotion = new SecPromotion();
	
	private static final String XP_TOTAL_IMPORT = "//*[@data-testid='checkout.cart.totals.total']";
	private static final String XP_IMPORT_GIFT_CARD = "//*[@data-testid='checkout.cart.totals.giftVoucher.value']";	
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
	
	public void inputChequeRegalo(String id, String cvc) {
		sPromotion.inputChequeRegalo(id, cvc);
	}
	
	public void removeChequeRegalo() {
		sPromotion.removeChequeRegalo();
	}	
	
	public boolean isDiscountChequeRegalo(BigDecimal amountCheque, int seconds) {
		if (!state(VISIBLE, XP_IMPORT_GIFT_CARD).wait(seconds).check()) {
			return false;
		}
		String giftCardDiscount = getElement(XP_IMPORT_GIFT_CARD).getText();
		var discountFloat = ImporteScreen.getFloatFromImporteMangoScreen(giftCardDiscount);
		var discount = new BigDecimal(Float.toString(discountFloat));
		
		return amountCheque.compareTo(discount)==0;
	}
	
	public boolean isNotDiscountChequeRegalo(int seconds) {
		return state(INVISIBLE, XP_IMPORT_GIFT_CARD).wait(seconds).check();
	}
	
	public float getTotalImport() {
		String precioTotal = getElement(XP_TOTAL_IMPORT).getText();
		return ImporteScreen.getFloatFromImporteMangoScreen(precioTotal);
	}

}
