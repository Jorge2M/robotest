package com.mng.sapfiori.access.test.testcase.generic.webobject.modals;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.mng.sapfiori.access.test.testcase.generic.webobject.elements.inputs.select.SelectEstandardWithoutLabel;
import com.mng.sapfiori.access.test.testcase.generic.webobject.makers.StandarElementsMaker;
import com.mng.sapfiori.access.test.testcase.generic.webobject.utils.PageObject;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class ModalSelectConditions extends PageObject {

	public enum Block {Incluir, Excluir}
	
	public enum ConditionInclude {
		contiene,
		igual_que,
		entre,
		comienza_por,
		acaba_en,
		menor_que,
		menor_o_igual_que,
		mayor_que,
		mayor_o_igual_que,
		vacío;
		
		@Override
		public String toString() {
			return (name().replace('_', ' '));
		}
	}
	
	public enum ConditionExclude {
		igual_que,
		vacío;
		
		@Override
		public String toString() {
			return (name().replace('_', ' '));
		}
	}
	
	//Elementos
	private final SelectEstandardWithoutLabel selectIncluir;
	private final SelectEstandardWithoutLabel selectExcluir;
	
	//XPaths
	private static final String TagBlock = "@TagBlock";
	private static final String XPathBlockWrapperWithTag = "//h2[text()='" + TagBlock + "']/../..";
	private static final String XPathDivSelectRelative =  "//div[@id[contains(.,'_select')]]";
	private static final String XPathArrowToUnfoldRelative = "//span[@id[contains(.,'CollapsedImg')]]";
	private static final String XPathInputTextRelative = "//input[@class='sapMInputBaseInner']";
	private static final String XPathButtonOk = "//button[@id[contains(.,'-ok')]]";
	
	private ModalSelectConditions(WebDriver driver) {
		super(driver);
		StandarElementsMaker maker = StandarElementsMaker.getNew(driver);
		int idSelectInclude = getIdSelect(Block.Incluir);
		selectIncluir = maker.getSelectEstandardWithoutLabel(idSelectInclude);
		int idSelectExclude = getIdSelect(Block.Excluir);
		selectExcluir = maker.getSelectEstandardWithoutLabel(idSelectExclude);
	}
	
	public static ModalSelectConditions getNew(WebDriver driver) {
		return new ModalSelectConditions(driver);
	}
	
	private String getXPathBlockWrapper(Block block) {
		return XPathBlockWrapperWithTag.replace(TagBlock, block.name());
	}
	
	private String getXPathSelect(Block block) {
		String xpathBlock = getXPathBlockWrapper(block);
		return (xpathBlock + XPathDivSelectRelative);
	}
	
	private String getXPathArrowToUnflod(Block block) {
		String xpathBlock = getXPathBlockWrapper(block);
		return (xpathBlock + XPathArrowToUnfoldRelative);
	}
	
	private String getXPathInputText(Block block) {
		String xpathBlock = getXPathBlockWrapper(block);
		return (xpathBlock + XPathInputTextRelative);
	}
	
	private int getIdSelect(Block block) {
		String xpathSelect = getXPathSelect(block);
		state(Present, By.xpath(xpathSelect)).wait(1).check();
		String id = driver.findElement(By.xpath(xpathSelect)).getAttribute("id");
		
		Pattern pattern = Pattern.compile(".*(\\d+).*");
		Matcher matcher = pattern.matcher(id);
		if (matcher.matches()) {
			return Integer.valueOf(matcher.group(1));
		}
		return -1;
	}
	
	public void include(ConditionInclude condition, String text) {
		unfold(Block.Incluir);
		selectIncluir.selectByValue(condition.toString());
		sendTextToInput(Block.Incluir, text);
	}
	
	public void exclude(ConditionExclude condition, String text) {
		unfold(Block.Excluir);
		selectExcluir.selectByValue(condition.toString());
		sendTextToInput(Block.Excluir, text);
	}
	
	private void sendTextToInput(Block block, String text) {
		By byInput = By.xpath(getXPathInputText(block));
		driver.findElement(byInput).sendKeys(text);
	}
	
	public void clickOk() {
		click(By.xpath(XPathButtonOk)).exec();
	}
	
	private void unfold(Block block) {
		if (!isBlockUnfold(block)) {
			String xpathArrow = getXPathArrowToUnflod(block);
			driver.findElement(By.xpath(xpathArrow)).click();
			//El bloque se despliega lentamente. No nos ha quedado más remedio que incluir un delay
			waitMillis(300);
		}
	}
	
	private boolean isBlockUnfold(Block block) {
		By byArrow = By.xpath(getXPathArrowToUnflod(block));
		WebElement arrowElem = driver.findElement(byArrow);
		return (arrowElem.getAttribute("class").contains("IconExpanded"));
	}
}
