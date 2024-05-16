package com.mng.robotest.tests.domains.compranew.pageobjects;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.testslegacy.beans.Pago;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class SecPaymentMethod extends PageBase {

	private static final String XP_PAYMENT_METHOD_BLOCK = "//*[@data-testid='checkout.payment.paymentMethodsList']";
	private static final String XP_SAVE_CARD = "//*[@data-testid='checkout.payment.save.checkbox']";
	private static final String XP_SAVED_CARD = "//*[@data-testid='checkout.paymentMethod.storedCard']";
	private static final String XP_NEW_CARD = "//*[@data-testid='checkout.payment.card.options-NEW_CARD']";
	private static final String XP_CARD_HOLDER_INPUT = "//input[@data-testid='checkout.payment.cardHolder']";
	private static final String XP_CARD_NUMBER_INPUT = "//input[@data-testid='checkout.payment.cardNumber']";
	private static final String XP_CARD_EXPIRATION_INPUT = "//input[@data-testid='checkout.payment.expirationDate']";
	private static final String XP_CARD_CVC_INPUT = "//input[@data-testid='checkout.payment.cvv']";
	
	public boolean isVisible(int seconds) {
		return state(VISIBLE, XP_PAYMENT_METHOD_BLOCK).wait(seconds).check();
	}
	
	public void selectSaveCard() {
		click(XP_SAVE_CARD).exec();
	}
	
	public void selectSavedCard() {
		click(XP_SAVED_CARD).exec();
	}
	
	public void clickNewCardIfVisible() {
		if (state(VISIBLE, XP_NEW_CARD).check() || 
			state(VISIBLE, XP_SAVED_CARD).check() ||
			!isVisibleInputsCard(1)) {
			click(XP_NEW_CARD).exec();
			isVisibleInputsCard(1);
		}
	}
	
	private boolean isVisibleInputsCard(int seconds) {
		if (goToIframeInputsCard()) {
			return state(VISIBLE, XP_CARD_NUMBER_INPUT).wait(seconds).check();
		}
		return false;
	}
	
	public void inputCard(Pago payment) {
		goToIframeInputsCard();
		inputClearAndSendKeys(XP_CARD_HOLDER_INPUT, payment.getTitular());
		inputClearAndSendKeys(XP_CARD_NUMBER_INPUT, payment.getNumtarj());
		inputClearAndSendKeys(XP_CARD_EXPIRATION_INPUT, payment.getAnycad());
		inputClearAndSendKeys(XP_CARD_CVC_INPUT, payment.getCvc());
		leaveIframe();
	}
	
	private boolean goToIframeInputsCard() {
		return goToIframe("cardFormIframe");
	}

}
