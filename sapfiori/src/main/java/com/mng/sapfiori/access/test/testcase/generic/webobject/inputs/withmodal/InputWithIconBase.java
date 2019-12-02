package com.mng.sapfiori.access.test.testcase.generic.webobject.inputs.withmodal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;

import com.mng.sapfiori.access.test.testcase.generic.webobject.utils.PageObject;
import com.mng.testmaker.conf.Log4jConfig;

public class InputWithIconBase extends InputLabel {
	static Logger pLogger = LogManager.getLogger(Log4jConfig.log4jLogger);
	
	private final static String XPathIconSetFilterRelativeLabel = "/following::div[@class='sapMInputBaseIconContainer']";

	public InputWithIconBase(String label, WebDriver driver) {
		super(label, driver);
	}
	
	void clickIconBase() throws Exception {
		for (int i=0; i<3; i++) {
			try {
				waitForPageFinished();
				String xpathIcon = getXPathLabel() + XPathIconSetFilterRelativeLabel;
				driver.findElement(By.xpath(xpathIcon)).click();
				break;
			}
			catch (StaleElementReferenceException e) {
				PageObject.waitMillis(100);
				pLogger.info(e);
			}
		}
	}
	
}
