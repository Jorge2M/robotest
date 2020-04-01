package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.d3d;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import static com.mng.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageD3DJPTestSelectOption {
    
    public enum  OptionD3D {Card_Default, Successful, User_Authentication_Failed, PARes_Validation_failed, Signature_error, Technical_Error_in_3D_system}
    static String XPathSelectOption = "//select[@id='returnCode']";
    static String XPathButtonSubmit = "//input[@type='submit' and @name='B2']";
    static String XPathButtonReset = "//input[@type='submit' and @name='B3']";
    
    public static boolean isPageUntil(int maxSeconds, WebDriver driver) {
    	return (state(Visible, By.xpath(XPathSelectOption), driver)
    			.wait(maxSeconds).check());
    }
    
    public static void selectOption(OptionD3D option, WebDriver driver) {
        new Select(driver.findElement(By.xpath(XPathSelectOption))).selectByVisibleText(option.toString().replace("_", ""));
    }

	public static void clickSubmitButton(WebDriver driver) {
		click(By.xpath(XPathButtonSubmit), driver).exec();
	}

	public static void clickResetButton(WebDriver driver) {
		click(By.xpath(XPathButtonReset), driver).exec();
	}
}
