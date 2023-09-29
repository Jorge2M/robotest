package com.mng.robotest.tests.domains.compra.payments.d3d.pageobjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.mng.robotest.tests.domains.base.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageD3DJPTestSelectOption extends PageBase {
	
	public enum  OptionD3D {
		CARD_DEFAULT, SUCCESSFUL, 
		USER_AUTHENTICATION_FAILED, 
		PARES_VALIDATION_FAILED, 
		SIGNATURE_ERROR, 
		TECHNICAL_ERROR_IN_3D_SYSTEM
	}
	
	private static final String XPATH_SELECT_OPTION = "//select[@id='returnCode']";
	private static final String XPATH_BUTTON_SUBMIT = "//input[@type='submit' and @name='B2']";
	private static final String XPATH_BUTTON_RESET = "//input[@type='submit' and @name='B3']";
	
	public boolean isPageUntil(int seconds) {
		return state(Visible, XPATH_SELECT_OPTION).wait(seconds).check();
	}
	
	public void selectOption(OptionD3D option) {
		WebElement selectElement = getElement(XPATH_SELECT_OPTION);
		new Select(selectElement).selectByVisibleText(option.toString().replace("_", ""));
	}

	public void clickSubmitButton() {
		click(XPATH_BUTTON_SUBMIT).exec();
	}

	public void clickResetButton() {
		click(XPATH_BUTTON_RESET).exec();
	}
}
