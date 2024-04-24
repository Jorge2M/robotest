package com.mng.robotest.tests.domains.ficha.pageobjects.genesis;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.CLICKABLE;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.VISIBLE;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.ficha.pageobjects.commons.ColorType;

public class SecColorsGenesis extends PageBase {

	private static final String XP_COLORS_SELECTOR = "//*[@data-testid='pdp.productInfo.colorSelector']";
	private static final String XP_COLOR_ITEM = XP_COLORS_SELECTOR + "//li";
	private static final String XP_COLOR_SELECTED = XP_COLOR_ITEM + "/div[@style[contains(.,'color-item')]]/..";
	private static final String XP_COLOR_AVAILABLE = XP_COLOR_ITEM + "/a[@data-testid[@contains(.,'colorSelector')]]/div/img[not(@class[contains(.,'disabled'))]//..//..//..";
	private static final String XP_COLOR_UNAVAILABLE = XP_COLOR_ITEM + "/a[@data-testid[@contains(.,'colorSelector')]]/div/img[@class[contains(.,'disabled')]//..//..//..";
	private static final String XP_COLOR_LAST = XP_COLOR_ITEM + "[last()]";
	
	private String getXPathColorItem(String colorCode) {
		//TODO reclamado (19-abril) a Kaliope un data-testid con el código de color		
		return XP_COLOR_ITEM + "//img[@src[contains(.,'_" + colorCode + "_')]]//ancestor::li";
	}
	private String getXPathColorItem(int posColor) {
		return "(" + XP_COLOR_ITEM + ")[" + posColor + "]";  
	}
	
	private String getXPath(ColorType colorType) {
		switch (colorType) {
		case SELECTED:
			return XP_COLOR_SELECTED;
		case LAST:
			return XP_COLOR_LAST;
		case AVAILABLE:
			return XP_COLOR_AVAILABLE;
		case UNAVAILABLE: 
		default:
			return XP_COLOR_UNAVAILABLE;			
		}
	}
	
	private String getXPathImgColor(ColorType colorType) {
		return getXPath(colorType) + "//img";
	}
	
	public boolean isClickableColor(String colorCode) { //Tested
		return state(CLICKABLE, getXPathColorItem(colorCode)).check();
	}

	public void selectColor(String colorCode) { //Tested
		click(getXPathColorItem(colorCode)).exec();
	}

	public void clickColor(int posColor) {
		click(getXPathColorItem(posColor)).exec();
	}

	public void selectColorWaitingForAvailability(String colorCode) { //Tested
		state(VISIBLE, getXPathColorItem(colorCode)).wait(3).check();
		selectColor(colorCode);
	}

	public String getNombreColorSelected() { //Tested
		String xpColorImg = getXPathImgColor(ColorType.SELECTED);
		return getElement(xpColorImg).getAttribute("alt");
	}

	public String getCodeColor(ColorType colorType) {
		var colorItem = getColorItem(colorType);
		var codeColorOpt = getCodeFromColorItem(colorItem);
		if (codeColorOpt.isPresent()) {
			return codeColorOpt.get();
		}
		throw new NoSuchElementException(String.format("Not found code color %s in color item", colorType));
	}

	public boolean isPresentColor(ColorType colorType) {
		//TODO implementar cuando hayan añadido el código de color en el data-testid
		return true;
	}

	public List<String> getColorsGarment() {
		//TODO modificar cuando hayan añadido el código de color en el data-testid
		var colors = new ArrayList<String>();
		for (var colorItem : getElements(XP_COLOR_ITEM)) {
			var codeColorOpt = getCodeFromColorItem(colorItem);
			if (codeColorOpt.isPresent()) {
				colors.add(codeColorOpt.get());
			}
		}
		return colors;
	}
	
	public int getNumColors() {
		return getElements(XP_COLOR_ITEM).size();
	}	
	
	private WebElement getColorItem(ColorType colorType) {
		return getElement(getXPath(colorType));
	}
	
	private Optional<String> getCodeFromColorItem(WebElement colorItem) {
		var pattern = Pattern.compile("(?:_(\\d+))?_C\\.png");
		var srcColor = colorItem.findElement(By.xpath(".//img")).getAttribute("src");
		var matcher = pattern.matcher(srcColor);
		if (matcher.find()) {
			return Optional.of(matcher.group(1));
		}		
		return Optional.empty();
	}
	
}
