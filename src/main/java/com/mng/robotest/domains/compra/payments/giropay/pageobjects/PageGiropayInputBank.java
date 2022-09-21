package com.mng.robotest.domains.compra.payments.giropay.pageobjects;

import org.openqa.selenium.WebElement;

import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageGiropayInputBank extends PageBase {

	private static final String XPATH_INPUT_BANK = "//input[@placeholder[contains(.,'Bankname')]]";
	private static final String XPATH_LINK_BAK_OPTION = "//a[@class='ui-menu-item-wrapper']";
	private static final String XPATH_BUTTON_CONFIRM = "//div[@id='idtoGiropayDiv']/input";
	
	public boolean checkIsPage() {
		return state(Visible, XPATH_INPUT_BANK).check();
	}
	
	public void inputBank(String idBank) {
		WebElement input = getElement(XPATH_INPUT_BANK);
		input.clear();
		input.sendKeys(idBank);
		state(Visible, XPATH_LINK_BAK_OPTION).wait(1).check();
		click(XPATH_LINK_BAK_OPTION).exec();
	}
	
	public void confirm() {
		click(XPATH_BUTTON_CONFIRM).exec();
	}
}
