package com.mng.robotest.tests.domains.compranew.pageobjects;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.testslegacy.beans.Pago;
import com.mng.robotest.testslegacy.beans.TypePago;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import org.openqa.selenium.NoSuchSessionException;

public class SecPaymentMethod extends PageBase {

	private static final String XP_PAYMENT_METHOD_BLOCK = "//*[@data-testid='checkout.payment.paymentMethodsList']";
	private static final String XP_SAVE_CARD = "//*[@data-testid='checkout.payment.save.checkbox']";
	private static final String XP_SAVED_CARD = "//*[@data-testid='checkout.paymentMethod.storedCard']";
	private static final String XP_DELETE_SAVED_CARD = "//*[@data-testid='checkout.paymentMethod.storedCard.action']";
	private static final String XP_NEW_CARD = "//*[@data-testid='payment-method-CREDIT_CARD.title']";
	private static final String XP_CARD_HOLDER_INPUT = "//input[@data-testid='checkout.payment.cardHolder']";
	private static final String XP_CARD_NUMBER_INPUT = "//input[@data-testid='checkout.payment.cardNumber']";
	private static final String XP_CARD_EXPIRATION_INPUT = "//input[@data-testid='checkout.payment.expirationDate']";
	private static final String XP_CARD_CVC_INPUT = "//input[@data-testid='checkout.payment.cvv']";

	private String getXPathOptionPayment(TypePago typePago) {
		return "//*[@data-testid[contains(.,'checkout.payment.paymentMethodsList." + typePago.getCode() + "')]]";
	}
	
	private String getXPathStoredCard(String tipoTarjeta) {
		return XP_SAVED_CARD + "//*[@data-testid[contains(.,'" + tipoTarjeta.toLowerCase() + "')]]";
	}
	
	public boolean isVisible(int seconds) {
		//Workarround for selenium bug
		for (int i=0; i<seconds; i++) {
			if (isVisible()) {
				return true;
			}
			waitMillis(1000);
		}
		return false;
	}
	private boolean isVisible() {
		try {
			if (state(VISIBLE, XP_PAYMENT_METHOD_BLOCK).check()) {
				return true;
			}
		} catch (NoSuchSessionException e) {
			return false;
		}
		return false;
	}
	
	public void selectSaveCard() {
		goToIframeInputsCard();
		click(XP_SAVE_CARD).exec();
		leaveIframe();
	}
	
	public boolean isSavedCard(String tipoTarjeta) {
		return state(VISIBLE, getXPathStoredCard(tipoTarjeta)).check();
	}
	
	public void removeSavedCard() {
		click(XP_DELETE_SAVED_CARD).exec();
	}
	
	public void selectSavedCard() {
		click(XP_SAVED_CARD).waitLink(2).exec();
	}
	
	public void unfoldCardFormulary() {
		if (!isVisibleInputsCard(1)) {
			clickNewCardIfVisible();
			clickOptionCreditCardIfVisible();
		}
	}
	
	private void clickNewCardIfVisible() {
		if (state(VISIBLE, XP_NEW_CARD).check() || 
			state(VISIBLE, XP_SAVED_CARD).check()) {
			click(XP_NEW_CARD).exec();
			isVisibleInputsCard(1);
		}
	}
	
	private void clickOptionCreditCardIfVisible() {
		var typePago = dataTest.getDataPago().getPago().getTypePago();
		String xpathOptionPago = getXPathOptionPayment(typePago);
		if (state(VISIBLE, xpathOptionPago).check()) {
			moveToElement(xpathOptionPago);
			click(xpathOptionPago).exec();
			if (!isVisibleInputsCard(1)) {
				keyDown(5);
				click(xpathOptionPago).exec();
				isVisibleInputsCard(1);
			}
		}
	}
	
	private boolean isVisibleInputsCard(int seconds) {
		if (goToIframeInputsCard(seconds)) {
			var visible = state(VISIBLE, XP_CARD_NUMBER_INPUT + "/..").wait(seconds).check();
			leaveIframe();
			return visible;
		}
		return false;
	}
	
	public void inputCard(Pago payment) {
		goToIframeInputsCard();
		inputClearAndSendKeys(XP_CARD_HOLDER_INPUT, payment.getTitular());
		inputClearAndSendKeys(XP_CARD_NUMBER_INPUT, payment.getNumtarj());
		
		String year = payment.getAnycad();
		String yearAA = year.substring(year.length() - 2);		
		inputClearAndSendKeys(XP_CARD_EXPIRATION_INPUT, payment.getMescad() + yearAA);
		inputClearAndSendKeys(XP_CARD_CVC_INPUT, payment.getCvc());
		leaveIframe();
	}
	
	private boolean goToIframeInputsCard() {
		return goToIframeInputsCard(0);
	}
	
	private boolean goToIframeInputsCard(int seconds) {
		for (int i=0; i<=seconds; i++) {
			if (goToIframe("checkout.paymentMethod.iframeWrapper.CREDIT_CARD")) {
				return true;
			}
			waitMillis(1000);
		}
		return false;
	}

}
