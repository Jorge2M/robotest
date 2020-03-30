package org.mng.testgoogle.test.testcase.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class ResultsGooglePage extends PageObject {

	private final static String XPathLinkSiguiente = "//a[@id='pnnext']";
	
	private ResultsGooglePage(WebDriver driver) {
		super(driver);
	}
	public static ResultsGooglePage getNew(WebDriver driver) {
		return new ResultsGooglePage(driver);
	}
	
	public boolean checkAreManyPages() {
		return (state(Visible, By.xpath(XPathLinkSiguiente)).check());
	}
	
}
