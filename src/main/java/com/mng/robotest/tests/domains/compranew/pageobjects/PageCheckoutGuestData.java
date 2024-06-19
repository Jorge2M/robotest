package com.mng.robotest.tests.domains.compranew.pageobjects;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.compranew.pageobjects.beans.DeliveryData;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageCheckoutGuestData extends PageBase {

	private static final String XP_NAME_INPUT = "//*[@data-testid='checkout.addressForm.firstName']";
	private static final String XP_SURNAME_INPUT = "//*[@data-testid='checkout.addressForm.lastName']";
	private static final String XP_ADDRESS_INPUT = "//*[@data-testid='checkout.addressForm.address']";
	private static final String XP_POSTCODE_INPUT = "//*[@data-testid='checkout.addressForm.postalCode']";
	private static final String XP_CITY_INPUT = "//*[@data-testid='checkout.addressForm.city']";
	private static final String XP_PROVINCE = "//*[@data-testid='checkout.addressForm.provinceName']";
	private static final String XP_COUNTRY_LIST = "//*[@data-testid='checkout.addressForm.provinceId']";
	private static final String XP_COUNTRY_ITEM = "//*[@data-testid='checkout.addressForm.provinceId.listbox']/div[@role='option']";
	private static final String XP_EMAIL_INPUT = "//*[@data-testid='checkout.addressForm.email']";
	private static final String XP_MOBILE_INPUT = "//*[@data-testid='address.form.number']";
	private static final String XP_TIN_INPUT = "//*[@data-testid='checkout.addressForm.tin']";
	
	private static final String XP_CONTINUE_TO_PAYMENT_BUTTON = "//*[@data-testid='checkout.step1.button.toPayment']";
	
	public boolean isPage(int seconds) {
		return state(VISIBLE, XP_NAME_INPUT + "/..").wait(seconds).check();
	}
	
	public void inputData(DeliveryData delivery) {
		inputClearAndSendKeys(XP_NAME_INPUT, delivery.getName());
		inputClearAndSendKeys(XP_SURNAME_INPUT, delivery.getSurname());
		inputClearAndSendKeys(XP_ADDRESS_INPUT, delivery.getAddress());
		inputClearAndSendKeys(XP_POSTCODE_INPUT, delivery.getPostcode());
		inputClearAndSendKeys(XP_CITY_INPUT, delivery.getCity());
		inputClearAndSendKeys(XP_EMAIL_INPUT, delivery.getEmail());
		inputClearAndSendKeys(XP_MOBILE_INPUT, delivery.getMobile());
		inputClearAndSendKeys(XP_PROVINCE, delivery.getProvince());
		
		inputTinIfExists(delivery.getDni());
		selectCountryIfExists(delivery.getCountry());
	}
	
	private void inputTinIfExists(String dni) {
		if (dni!=null && state(PRESENT, XP_TIN_INPUT).check()) {
			inputClearAndSendKeys(XP_TIN_INPUT, dni);
		}
	}
	
	private void selectCountryIfExists(String country) {
		if (isCountryInteractable()) {
			click(XP_COUNTRY_LIST).exec(); //Unfold list
			String xpathItemCountry = XP_COUNTRY_ITEM + "/p[text()='" + country + "']/.."; 
			if (country!=null && state(VISIBLE, xpathItemCountry).check()) {
				click(xpathItemCountry).exec();
			} else {
				click(XP_COUNTRY_ITEM).exec(); //Select first
			}
		}		
	}
	
	private boolean isCountryInteractable() {
		if (state(VISIBLE, XP_COUNTRY_LIST).check()) {
			var countryList = getElement(XP_COUNTRY_LIST); 
			if (!countryList.getAttribute("class").contains("disabled")) {
				return true;
			}
		}
		return false;
	}
	
	public void clickContinueToPayment() {
		click(XP_CONTINUE_TO_PAYMENT_BUTTON).exec();
		if (!state(INVISIBLE, XP_CONTINUE_TO_PAYMENT_BUTTON).wait(3).check()) {
			click(XP_CONTINUE_TO_PAYMENT_BUTTON).exec();
		}
	}

}
