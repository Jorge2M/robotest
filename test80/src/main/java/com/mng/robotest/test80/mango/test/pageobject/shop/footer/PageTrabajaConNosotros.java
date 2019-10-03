package com.mng.robotest.test80.mango.test.pageobject.shop.footer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.utils.controlTest.fmwkTest;
import com.mng.testmaker.webdriverwrapper.WebdrvWrapp;

public class PageTrabajaConNosotros extends WebdrvWrapp implements PageFromFooter {
	
    static Logger pLogger = LogManager.getLogger(fmwkTest.log4jLogger);
    
	final String XPathIdFrame = "//iframe[@id='bodyFrame']";
	final String XPathForIdPage = "//section[@id='all-jobs-link-section']";
	
	@Override
	public String getName() {
		return "Diseña tu futuro en Mango";
	}
	
	@Override
	public boolean isPageCorrectUntil(int maxSecondsWait, WebDriver driver) {
		try {
			boolean isFramePresent = isElementPresentUntil(driver, By.xpath(XPathIdFrame), maxSecondsWait);
			if (isFramePresent) {
				driver.switchTo().frame(driver.findElement(By.xpath(XPathIdFrame)));
				return (isElementPresentUntil(driver, By.xpath(XPathForIdPage), maxSecondsWait));
			}
			return false;
		}
		catch (Exception e) {
			pLogger.warn("Problem in switch to bodyFrame", e);
			return false;
		}
	}
}
