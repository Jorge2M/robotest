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
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;

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
		return (state(Visible, By.xpath(xpathTitle)).check());
	}

	public void clickEnterToShowInitialElements() {
		waitForPageFinished();
		By byInputField = By.xpath(XPathInputField);
		driver.findElement(byInputField).click();
		driver.findElement(byInputField).sendKeys(Keys.RETURN);
		waitForPageFinished();
	}

	public boolean isElementListPresent(int maxSeconds) {
		return (state(Present, By.xpath(XPathElementLine)).wait(maxSeconds).check());
	}
	
	public void selectElementByPosition(int posElement) {
		By byElem = By.xpath(getXPathForSelectElementByIndex(posElement));
		click(byElem).exec();
	}
	
	public boolean findAndSelectElement(String valueToSearch, String valueToSelectInTable) {
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
	
	private boolean findByBuscarAndSelectElement(String valueToSearch, String valueToSelectInTable) {
		waitForPageFinished();
		inputBuscador.clearAndSendText(valueToSearch);
		inputBuscador.clickLupaForSearch();
		return (selectElementInTable(valueToSelectInTable));
	}
	
	private boolean findByInputLabelAndSelectElement(String valueToSearch, String valueToSelectInTable) {
		waitForPageFinished();
		inputDataLabel.clearAndSendText(valueToSearch);
		inputDataLabel.sendText(Keys.RETURN);
		return (selectElementInTable(valueToSelectInTable));
	}
	
	public void findByFiltersAndSelectElement(Map<String,String> inputLabelAndValues, String valueToSelectInTable) {
		waitForPageFinished();
		for (Map.Entry<String,String> labelAndValue : inputLabelAndValues.entrySet()) {
			InputWithIconBase inputWeb = standarMaker.getInputWithIconForSelectItem(labelAndValue.getKey());
			inputWeb.sendText(labelAndValue.getValue());
		}
		clickEnterToShowInitialElements();
		selectElementInTable(valueToSelectInTable);
	}
	
	public boolean selectElementInTable(String valueToSelect) {
		String xpath = getXPathForSelectElementByValue(valueToSelect);
		if (state(Visible, By.xpath(xpath)).wait(3).check()) {
			click(By.xpath(xpath)).exec();
			return true;
		}
		return false;
	}
	
	public void clickOkButton() {
		click(By.xpath(XPathOkButton)).exec();
	}
}
