package com.mng.robotest.test80.mango.test.pageobject.shop.loyalty;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PageHomePurchaseWithDiscount extends PageObjTM {

	String idBlockLoyalty = "loyaltyLoyaltySpace";
	String xpathButtonPurchaseNow = "//a[text()='Comprar ahora']";

	private PageHomePurchaseWithDiscount(WebDriver driver) {
		super(driver);
	}
	
	public static PageHomePurchaseWithDiscount getNewInstance(WebDriver driver) {
		return (new PageHomePurchaseWithDiscount(driver));
	}
	
	public boolean checkIsPage() {
		return (state(Visible, By.id(idBlockLoyalty)).check());
	}

	public boolean areVisibleButtonPurchaseNow() {
		return (state(Visible, By.xpath(xpathButtonPurchaseNow)).wait(2).check());
	}
}