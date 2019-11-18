package com.mng.sapfiori.test.testcase.generic.webobject.modals;

import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import com.mng.sapfiori.test.testcase.generic.webobject.elements.inputs.buscar.InputBuscador;
import com.mng.sapfiori.test.testcase.generic.webobject.inputs.withmodal.InputWithIconBase;
import com.mng.sapfiori.test.testcase.generic.webobject.makers.StandarElementsMaker;
import com.mng.sapfiori.test.testcase.generic.webobject.utils.PageObject;
import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;

public abstract class ModalSelectFromListBase extends PageObject {

	final String label;
	
	final StandarElementsMaker standarMaker;
	final InputBuscador inputBuscador;
	
	private final static String XPathHeaderBar = "//div[@class[contains(.,'sapMBarPH')]]";
	private final static String TagLabel = "@TabLabel";
	private final static String XPathTitleModalWithTag = XPathHeaderBar + "/span[@title='Seleccionar: " + TagLabel + "']";
	
	private final static String XPathInputField = "//input[@id[contains(.,'-inner')]]";
	private final static String XPathOkButton = "//button[@id[contains(.,'-ok')]]";
	
	private final static String XPathElementLine = "//table[@id[contains(.,'table-table')]]/tbody//tr";
	private final static String XPathForSelectLineRelative = "/td[@tabindex]";

	
	ModalSelectFromListBase(String label, WebDriver driver) {
		super(driver);
		this.label = label;
		this.standarMaker = StandarElementsMaker.getNew(driver);
		this.inputBuscador = standarMaker.getInputBuscador();
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
	
	public boolean isModalVisible() {
		String xpathTitle = getXPathTitleModal();
		return WebdrvWrapp.isElementVisible(driver, By.xpath(xpathTitle));
	}

	public void clickEnterToShowInitialElements() throws Exception {
		waitForPageFinished();
		By byInputField = By.xpath(XPathInputField);
		driver.findElement(byInputField).click();
		driver.findElement(byInputField).sendKeys(Keys.RETURN);
		waitForPageFinished();
	}

	public boolean isElementListPresent(int maxSeconds) {
		return (WebdrvWrapp.isElementPresentUntil(driver, By.xpath(XPathElementLine), maxSeconds));
	}
	
	public void selectElementByPosition(int posElement) throws Exception {
		By byElem = By.xpath(getXPathForSelectElementByIndex(posElement));
		WebdrvWrapp.clickAndWaitLoad(driver, byElem);
	}
	
	public void findByBuscarAndSelectElement(String valueToSearch, String valueToSelectInTable) throws Exception {
		waitForPageFinished();
		inputBuscador.sendText(valueToSearch);
		inputBuscador.clickLupaForSearch();
		selectElementInTable(valueToSelectInTable);
	}
	
	public void findByFiltersAndSelectElement(Map<String,String> inputLabelAndValues, String valueToSelectInTable) 
	throws Exception {
		waitForPageFinished();
		for (Map.Entry<String,String> labelAndValue : inputLabelAndValues.entrySet()) {
			InputWithIconBase inputWeb = standarMaker.getInputWithIconForSelectItem(labelAndValue.getKey());
			inputWeb.sendText(labelAndValue.getValue());
		}
		clickEnterToShowInitialElements();
		selectElementInTable(valueToSelectInTable);
	}
	
	public void selectElementInTable(String valueToSelect) throws Exception {
		String xpath = getXPathForSelectElementByValue(valueToSelect);
		WebdrvWrapp.isElementVisibleUntil(driver, By.xpath(xpath), 3);
		WebdrvWrapp.clickAndWaitLoad(driver, By.xpath(xpath));
	}
	
	public void clickOkButton() throws Exception {
		WebdrvWrapp.clickAndWaitLoad(driver, By.xpath(XPathOkButton));
	}
}
