package com.mng.sapfiori.access.test.testcase.generic.webobject.inputs.withmodal;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import com.github.jorge2m.testmaker.conf.Log4jTM;

import com.mng.sapfiori.access.test.testcase.generic.webobject.utils.PageObject;

public class InputWithIconBase extends InputLabel {
	
	private final static String XPathIconSetFilterRelativeLabel = "/following::div[@class='sapMInputBaseIconContainer']";

	public InputWithIconBase(String label, WebDriver driver) {
		super(label, driver);
	}
	
	void clickIconBase() {
		for (int i=0; i<3; i++) {
			try {
				waitForPageFinished();
				String xpathIcon = getXPathLabel() + XPathIconSetFilterRelativeLabel;
				driver.findElement(By.xpath(xpathIcon)).click();
				break;
			}
			catch (StaleElementReferenceException e) {
				PageObject.waitMillis(100);
				Log4jTM.getLogger().info(e);
			}
		}
	}
	
}
