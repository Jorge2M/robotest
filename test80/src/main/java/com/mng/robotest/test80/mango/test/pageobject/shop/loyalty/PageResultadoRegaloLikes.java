package com.mng.robotest.test80.mango.test.pageobject.shop.loyalty;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.service.webdriver.pageobject.PageObjTM;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageResultadoRegaloLikes extends PageObjTM {
	
	private final static String XPathDoneIcon = "//img[@class='done-icon']";
	
	public PageResultadoRegaloLikes(WebDriver driver) {
		super(driver);
	}
	
	public boolean isEnvioOk(int maxSeconds) {
		return (state(Visible, By.xpath(XPathDoneIcon)).wait(maxSeconds).check());
	}
	
}
