package com.mng.robotest.tests.domains.compranew.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.PRESENT;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.VISIBLE;

import com.mng.robotest.tests.domains.base.PageBase;

public class SecPromotion extends PageBase {

	private static final String XP_ACCORDION_PROMOTIONAL_CODE = "//*[@data-testid='promotional-code-accordion']";
	private static final String XP_INPUT_PROMOTIONAL_CODE = "//*[@data-testid='checkout.promocode.input']";
	private static final String XP_BUTTON_APPLY_CODE = "//*[@data-testid='checkout.promocode.apply.button']";	
	private static final String XP_INPUT_CVC_CHEQUE = "//*[@data-testid='checkout.giftVoucher.input.cvv']";
	private static final String XP_BUTTON_VERIFY_CODE = "//*[@data-testid='checkout.giftVoucher.apply.button']";
	private static final String XP_BUTTON_REMOVE_CHEQUE = "//*[@data-testid='checkout.voucher.remove.button']";
	
	public void inputChequeRegalo(String id, String cvc) {
		unfold();
		inputChequeData(id, cvc);
		clickVerifyButton();
	}
	
	public void removeChequeRegalo() {
		state(VISIBLE, XP_BUTTON_REMOVE_CHEQUE).wait(1).check();
		click(XP_BUTTON_REMOVE_CHEQUE).exec();
	}	
	
	private void unfold() {
		click(XP_ACCORDION_PROMOTIONAL_CODE).exec();
		state(VISIBLE, XP_INPUT_PROMOTIONAL_CODE + "/..").wait(1).check();		
	}
	
	private void inputChequeData(String id, String cvc) {
		state(VISIBLE, XP_INPUT_PROMOTIONAL_CODE).wait(1).check();
		inputClearAndSendKeys(XP_INPUT_PROMOTIONAL_CODE, id);
		click(XP_BUTTON_APPLY_CODE).exec();
		state(PRESENT, XP_INPUT_CVC_CHEQUE).wait(1).check();
		inputClearAndSendKeys(XP_INPUT_CVC_CHEQUE, cvc);
		state(VISIBLE, XP_BUTTON_VERIFY_CODE).wait(1).check();
	}
	
	private void clickVerifyButton() {
		click(XP_BUTTON_VERIFY_CODE).exec();
	}

}
