package ${package}.test.testcase.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ResultsGooglePage extends PageObject {

	private final static String XPathLinkSiguiente = "//a[@id='pnnext']";
	private final WebDriver driver;
	
	private ResultsGooglePage(WebDriver driver) {
		this.driver = driver;
	}
	public static ResultsGooglePage getNew(WebDriver driver) {
		return new ResultsGooglePage(driver);
	}
	
	public boolean checkAreManyPages() {
		return (isElementVisible(driver, By.xpath(XPathLinkSiguiente)));
	}
	
}
