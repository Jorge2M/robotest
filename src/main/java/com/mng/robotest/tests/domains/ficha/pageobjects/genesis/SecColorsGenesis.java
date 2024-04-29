package com.mng.robotest.tests.domains.ficha.pageobjects.genesis;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.ficha.pageobjects.commons.ColorType;

public class SecColorsGenesis extends PageBase {

	private static final String XP_COLOR_ITEM = "//li[@data-testid[contains(.,'pdp.colorSelector')]]";
	private static final String XP_COLOR_SELECTED = XP_COLOR_ITEM + "/div[@style[contains(.,'color-item')]]/..";
	private static final String XP_COLOR_AVAILABLE = XP_COLOR_ITEM + "/a/div/img[not(@class[contains(.,'disabled'))]//..//..//..";
	private static final String XP_COLOR_UNAVAILABLE = XP_COLOR_ITEM + "/a/div/img[@class[contains(.,'disabled')]//..//..//..";
	private static final String XP_COLOR_LAST = XP_COLOR_ITEM + "[last()]";
	
	private String getXPathColorItem(String colorCode) {
		return XP_COLOR_ITEM + "//self::*[@data-testid[contains(.,'." + colorCode + "')]]";
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
	
	public boolean isClickableColor(String colorCode) {
		return state(CLICKABLE, getXPathColorItem(colorCode)).check();
	}

	public void selectColor(String colorCode) {
		click(getXPathColorItem(colorCode)).exec();
	}

	public void clickColor(int posColor) {
		click(getXPathColorItem(posColor)).exec();
	}

	public void selectColorWaitingForAvailability(String colorCode) {
		state(VISIBLE, getXPathColorItem(colorCode)).wait(3).check();
		selectColor(colorCode);
	}

	public String getNombreColorSelected() {
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
		return state(PRESENT, getXPath(colorType)).check();
	}

	public List<String> getColorsGarment() {
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
		var pattern = Pattern.compile("\\.([a-zA-Z0-9]{2})$");
		var colorTid = colorItem.getAttribute("data-testid");
		var matcher = pattern.matcher(colorTid);
		if (matcher.find()) {
			return Optional.of(matcher.group(1));
		}		
		return Optional.empty();
	}
	
}
