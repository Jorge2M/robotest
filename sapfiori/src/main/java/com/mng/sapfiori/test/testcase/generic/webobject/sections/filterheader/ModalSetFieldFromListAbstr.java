package com.mng.sapfiori.test.testcase.generic.webobject.sections.filterheader;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;

public abstract class ModalSetFieldFromListAbstr implements ModalSetFieldFromListI {

	final String label;
	final WebDriver driver;
	
	private final static String XPathHeaderBar = "//div[@class[contains(.,'sapMBarPH')]]";
	private final static String TagLabel = "@TabLabel";
	private final static String XPathTitleModalWithTag = XPathHeaderBar + "/span[@title='Seleccionar: " + TagLabel + "']";
	//private final static String XPathInputField = "//div[@id[contains(.,'NmbrSchm') and not(@id[contains(.,'NmbrSchm')])]]//input";
	private final static String XPathInputField = "//input[@id[contains(.,'-inner')]]";
	private final static String XPathOkButton = "//button[@id[contains(.,'-ok')]]";
	
	private final static String XPathElementLine = "//table[@id[contains(.,'table-table')]]/tbody//tr";
	private final static String XPathForSelectLineRelative = "/td[@tabindex]";

	
	ModalSetFieldFromListAbstr(String label, WebDriver driver) {
		this.label = label;
		this.driver = driver;
	}
	
	private String getXPathTitleModal() {
		return XPathTitleModalWithTag.replace(TagLabel, label);
	}
	
	private String getXPathForSelectElementByIndex(int posElement) {
		return ("(" + XPathElementLine + ")[" + posElement + "]" + XPathForSelectLineRelative);
	}
	
	private String getXPathForSelectElementByValue(String value) {
		return (XPathElementLine + XPathForSelectLineRelative + "//self::*[text()='" + value + "']");
	}
	
	@Override
	public boolean isModalVisible() {
		String xpathTitle = getXPathTitleModal();
		return WebdrvWrapp.isElementVisible(driver, By.xpath(xpathTitle));
	}

	@Override
	public void clickEnterToShowInitialElements() throws Exception {
		WebdrvWrapp.waitForPageLoaded(driver);
		By byInputField = By.xpath(XPathInputField);
		driver.findElement(byInputField).click();
		driver.findElement(byInputField).sendKeys(Keys.RETURN);
		WebdrvWrapp.waitForPageLoaded(driver);
	}

	@Override
	public boolean isElementListPresent(int maxSeconds) {
		return (WebdrvWrapp.isElementPresentUntil(driver, By.xpath(XPathElementLine), maxSeconds));
	}
	
	@Override
	public void selectElementByPosition(int posElement) throws Exception {
		By byElem = By.xpath(getXPathForSelectElementByIndex(posElement));
		WebdrvWrapp.clickAndWaitLoad(driver, byElem);
	}
	
	@Override
	public void selectElementByValue(String valueElement) throws Exception {
		//1o hemos de buscar para que aparezca una lista con 1 sólo elemento (los elementos no visibles no se pueden seleccionar)
		searchElementByValue(valueElement); 
		String xpath = getXPathForSelectElementByValue(valueElement);
		WebdrvWrapp.isElementVisibleUntil(driver, By.xpath(xpath), 3);
		WebdrvWrapp.clickAndWaitLoad(driver, By.xpath(xpath));
	}
	
	private void searchElementByValue(String valueElement) throws Exception {
		//WebElement inputElement = driver.findElement(By.xpath(XPathInputField));
		//WebdrvWrapp.sendKeysWithRetry(2, inputElement, valueElement);
		WebdrvWrapp.waitForPageLoaded(driver);
		By byInputField = By.xpath(XPathInputField);
		driver.findElement(byInputField).sendKeys(valueElement);
		clickEnterToShowInitialElements();
	}
	
	@Override
	public void clickOkButton() throws Exception {
		WebdrvWrapp.clickAndWaitLoad(driver, By.xpath(XPathOkButton));
	}
}
