package com.mng.robotest.tests.domains.compranew.pageobjects;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.compranew.pageobjects.beans.DeliveryData;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageCheckoutGuestData extends PageBase {

	private static final String XP_DELIVERY_DETAILS_BLOCK = "//*[@data-testid='checkout.delivery.tab.home']";
	private static final String XP_NAME_INPUT = "//*[@data-testid='checkout.addressForm.firstName']";
	private static final String XP_SURNAME_INPUT = "//*[@data-testid='checkout.addressForm.lastName']";
	private static final String XP_ADDRESS_INPUT = "//*[@data-testid='checkout.addressForm.address']";
	private static final String XP_POSTCODE_INPUT = "//*[@data-testid='checkout.addressForm.postalCode']";
	private static final String XP_CITY_INPUT = "//*[@data-testid='checkout.addressForm.city']";
	private static final String XP_COUNTRY_LIST = "//*[@data-testid='checkout.addressForm.provinceId.listbox']";
	private static final String XP_COUNTRY_ITEM = XP_COUNTRY_LIST + "/div[@role='option']";
	private static final String XP_EMAIL_INPUT = "//*[@data-testid='checkout.addressForm.email']";
	private static final String XP_MOBILE_INPUT = "//*[@data-testid='address.form.number']";
	
	private static final String XP_CONTINUE_TO_PAYMENT_BUTTON = "//*[@data-testid='checkout.step1.button.toPayment']";
	
	public boolean isPage(int seconds) {
		return state(VISIBLE, XP_DELIVERY_DETAILS_BLOCK).wait(seconds).check();
	}
	
	public void inputData(DeliveryData delivery) {
		inputClearAndSendKeys(XP_NAME_INPUT, delivery.getName());
		inputClearAndSendKeys(XP_SURNAME_INPUT, delivery.getSurname());
		inputClearAndSendKeys(XP_ADDRESS_INPUT, delivery.getAddress());
		inputClearAndSendKeys(XP_POSTCODE_INPUT, delivery.getPostcode());
		inputClearAndSendKeys(XP_CITY_INPUT, delivery.getCity());
		inputClearAndSendKeys(XP_EMAIL_INPUT, delivery.getEmail());
		inputClearAndSendKeys(XP_MOBILE_INPUT, delivery.getMobile());
		selectCountryIfExists(delivery.getCountry());
	}
	
	private void selectCountryIfExists(String country) {
		if (state(VISIBLE, XP_COUNTRY_LIST).check()) {
			click(XP_COUNTRY_LIST).exec(); //Unfold list
			if (country==null) {
				click(XP_COUNTRY_ITEM).exec(); //Select first
			} else {
				click(XP_COUNTRY_ITEM + "/p[text()='" + country + "']/..").exec();
			}
		}		
	}
	
	public void clickContinueToPayment() {
		click(XP_CONTINUE_TO_PAYMENT_BUTTON).exec();
	}

}
