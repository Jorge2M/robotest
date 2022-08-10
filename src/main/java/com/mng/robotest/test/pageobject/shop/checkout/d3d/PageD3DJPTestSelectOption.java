package com.mng.robotest.test.pageobject.shop.checkout.d3d;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.mng.robotest.domains.transversal.PageBase;

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
	
	public boolean isPageUntil(int maxSeconds) {
		return (state(Visible, By.xpath(XPATH_SELECT_OPTION)).wait(maxSeconds).check());
	}
	
	public void selectOption(OptionD3D option) {
		WebElement selectElement = driver.findElement(By.xpath(XPATH_SELECT_OPTION));
		new Select(selectElement).selectByVisibleText(option.toString().replace("_", ""));
	}

	public void clickSubmitButton() {
		click(By.xpath(XPATH_BUTTON_SUBMIT)).exec();
	}

	public static void clickResetButton(WebDriver driver) {
		click(By.xpath(XPATH_BUTTON_RESET), driver).exec();
	}
}
