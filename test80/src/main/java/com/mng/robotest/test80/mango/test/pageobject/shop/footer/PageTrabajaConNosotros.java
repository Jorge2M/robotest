package com.mng.robotest.test80.mango.test.pageobject.shop.footer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.conf.Log4jConfig;
import com.mng.testmaker.service.webdriver.pageobject.PageObjTM;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageTrabajaConNosotros extends PageObjTM implements PageFromFooter {
	
    static Logger pLogger = LogManager.getLogger(Log4jConfig.log4jLogger);
    
	final String XPathIdFrame = "//iframe[@id='bodyFrame']";
	final String XPathForIdPage = "//section[@id='all-jobs-link-section']";
	
	public PageTrabajaConNosotros(WebDriver driver) {
		super(driver);
	}
	
	@Override
	public String getName() {
		return "Diseña tu futuro en Mango";
	}
	
	@Override
	public boolean isPageCorrectUntil(int maxSeconds) {
		try {
			boolean isFramePresent = state(Present, By.xpath(XPathIdFrame)).wait(maxSeconds).check();
			if (isFramePresent) {
				driver.switchTo().frame(driver.findElement(By.xpath(XPathIdFrame)));
				return (state(Present, By.xpath(XPathForIdPage)).wait(maxSeconds).check());
			}
			return false;
		}
		catch (Exception e) {
			pLogger.warn("Problem in switch to bodyFrame", e);
			return false;
		}
	}
}
