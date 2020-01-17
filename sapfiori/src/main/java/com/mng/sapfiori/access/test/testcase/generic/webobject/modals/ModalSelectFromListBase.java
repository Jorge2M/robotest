package com.mng.sapfiori.access.test.testcase.generic.webobject.modals;

import java.util.Map;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import com.mng.sapfiori.access.test.testcase.generic.webobject.elements.inputs.buscar.InputBuscador;
import com.mng.sapfiori.access.test.testcase.generic.webobject.inputs.withmodal.InputWithIconBase;
import com.mng.sapfiori.access.test.testcase.generic.webobject.inputs.withmodal.InputWithIconForDefineConditions;
import com.mng.sapfiori.access.test.testcase.generic.webobject.makers.StandarElementsMaker;
import com.mng.sapfiori.access.test.testcase.generic.webobject.utils.PageObject;
import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;

public abstract class ModalSelectFromListBase extends PageObject {

	final String label;
	
	final StandarElementsMaker standarMaker;
	final InputBuscador inputBuscador;
	final InputWithIconForDefineConditions inputDataLabel;
	
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
		this.inputDataLabel = standarMaker.getInputWithIconForDefineConditions(label);
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
	
	public boolean findAndSelectElement(String valueToSearch, String valueToSelectInTable) throws Exception {
		if (inputBuscador.isVisible()) {
			if (findByBuscarAndSelectElement(valueToSearch, valueToSelectInTable)) {
				return true;
			}
		}
		if (inputDataLabel.isVisible()) {
			inputDataLabel.clear();
			if (findByBuscarAndSelectElement(valueToSearch, valueToSelectInTable)) {
				return true;
			}
		}
		if (!findByInputLabelAndSelectElement(valueToSearch, valueToSelectInTable)) {
			throw new NoSuchElementException("Not found " + valueToSelectInTable + " searching for " + valueToSearch);
		}
		return true;
	}
	
	private boolean findByBuscarAndSelectElement(String valueToSearch, String valueToSelectInTable) throws Exception {
		waitForPageFinished();
		inputBuscador.clearAndSendText(valueToSearch);
		inputBuscador.clickLupaForSearch();
		return (selectElementInTable(valueToSelectInTable));
	}
	
	private boolean findByInputLabelAndSelectElement(String valueToSearch, String valueToSelectInTable) throws Exception {
		waitForPageFinished();
		inputDataLabel.clearAndSendText(valueToSearch);
		inputDataLabel.sendText(Keys.RETURN);
		return (selectElementInTable(valueToSelectInTable));
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
	
	public boolean selectElementInTable(String valueToSelect) throws Exception {
		String xpath = getXPathForSelectElementByValue(valueToSelect);
		if (isElementVisibleUntil(driver, By.xpath(xpath), 3)) {
			clickAndWaitLoad(driver, By.xpath(xpath));
			return true;
		}
		return false;
	}
	
	public void clickOkButton() throws Exception {
		WebdrvWrapp.clickAndWaitLoad(driver, By.xpath(XPathOkButton));
	}
}
