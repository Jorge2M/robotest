package com.mng.robotest.test.pageobject.shop.checkout.paypal;

import org.openqa.selenium.By;
import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PagePaypalSelectPago extends PageBase {

	private static final String XPATH_CONTINUE_BUTTON = "//*[@data-testid='submit-button-initial']";
	private static final String XPATH_MET_PAGOS = "//section[@data-testid='pay-with']";

	public boolean isPageUntil(int maxSeconds) {
		return (state(Visible, By.xpath(XPATH_MET_PAGOS)).wait(maxSeconds).check());
	}

	public void clickContinuarButton() {
		click(By.xpath(XPATH_CONTINUE_BUTTON)).exec();
	}
}
