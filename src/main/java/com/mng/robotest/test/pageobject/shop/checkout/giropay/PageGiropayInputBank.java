package com.mng.robotest.test.pageobject.shop.checkout.giropay;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageGiropayInputBank extends PageObjTM {

	private static final String XPathInputBank = "//input[@placeholder[contains(.,'Bankname')]]";
	private static final String XPathLinkBakOption = "//a[@class='ui-menu-item-wrapper']";
	private static final String XPathButtonConfirm = "//div[@id='idtoGiropayDiv']/input";
	
	public boolean checkIsPage() {
		return (state(Visible, By.xpath(XPathInputBank)).check());
	}
	
	public void inputBank(String idBank) {
		WebElement input = driver.findElement(By.xpath(XPathInputBank));
		input.clear();
		input.sendKeys(idBank);
		state(Visible, By.xpath(XPathLinkBakOption)).wait(1).check();
		click(By.xpath(XPathLinkBakOption), driver).exec();
	}
	
	public void confirm() {
		click(By.xpath(XPathButtonConfirm)).exec();
	}
}
