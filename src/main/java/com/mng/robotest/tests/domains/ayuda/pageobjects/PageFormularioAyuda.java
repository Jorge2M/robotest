package com.mng.robotest.tests.domains.ayuda.pageobjects;

import org.openqa.selenium.support.ui.Select;

import com.mng.robotest.tests.domains.base.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import java.util.List;

public class PageFormularioAyuda extends PageBase {

	private static final String XP_SELECTOR = "//*[@data-testid[contains(.,'contactForm.selectCategory.selector')]]";
	
	private String getXPathSelector(int level) {
		return XP_SELECTOR + "//self::*[@data-testid[contains(.,'level" + level + "')]]";
	}
	
	public boolean isPage(int seconds) {
		return state(VISIBLE, XP_SELECTOR).wait(seconds).check();
	}
	
	public void inputInSelectors(List<String> inputData) {
		int level = 1;
		for (String input : inputData) {
			inputInSelector(level, input);
			level+=1;
		}
	}
	
	public void inputInSelector(int level, String inputData) {
		String xpathSelector = getXPathSelector(level);
		state(VISIBLE, xpathSelector).wait(1).check();
		new Select(getElement(xpathSelector)).selectByVisibleText(inputData);
	}

}
