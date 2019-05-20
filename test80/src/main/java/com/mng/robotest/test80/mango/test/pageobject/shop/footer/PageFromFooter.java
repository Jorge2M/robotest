package com.mng.robotest.test80.mango.test.pageobject.shop.footer;

import org.openqa.selenium.WebDriver;

public interface PageFromFooter {
	
	public String getName();
	public boolean isPageCorrectUntil(int maxSecondsWait, WebDriver driver);
}
