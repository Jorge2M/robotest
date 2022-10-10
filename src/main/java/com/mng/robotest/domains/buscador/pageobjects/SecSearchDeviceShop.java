package com.mng.robotest.domains.buscador.pageobjects;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class SecSearchDeviceShop extends PageBase implements SecSearch {
	
	private static final String XPATH_INPUT_BUSCADOR_OUTLET = "//div[@class='search-component']//form[not(@class)]/input[@class[contains(.,'search-input')]]";
	private static final String XPATH_INPUT_BUSCADOR_SHOP = "//*[@data-testid='header.search.input']";
	private static final String XPATH_CANCELAR_LINK = "//div[@class[contains(.,'search-cancel')]]";
	
	private String getXPathInputBuscador() {
		if (app==AppEcom.outlet /*||
		   (new NavigationBase().isPRO() && UtilsTest.dateBeforeToday("2022-10-04"))*/) {
		    return XPATH_INPUT_BUSCADOR_OUTLET;
		} else {
			return XPATH_INPUT_BUSCADOR_SHOP;
		}
	}
	
	@Override
	public void search(String text) {
		String xpathInput = getXPathInputBuscador();
		state(Visible, xpathInput).wait(2).check();
		WebElement input = getElement(xpathInput);
		input.clear();
		input.sendKeys(text);
		input.sendKeys(Keys.RETURN);
	}
	
	@Override
	public void close() {
		click(XPATH_CANCELAR_LINK).exec();
	}
}
