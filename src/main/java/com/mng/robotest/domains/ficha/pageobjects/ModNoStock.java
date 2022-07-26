package com.mng.robotest.domains.ficha.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

/**
 * Modal que aparece al seleccionar el link "Envío y devoluciones" existente en la nueva Ficha
 * @author jorge.munoz
 *
 */

public class ModNoStock {

	private static final String XPATH_MODAL_NO_STOCK = "//div[@class='modalNoStock show']";
	
	public static boolean isModalNoStockVisibleFichaNew(int maxSeconds, WebDriver driver) {
		return (state(Visible, By.xpath(XPATH_MODAL_NO_STOCK), driver)
				.wait(maxSeconds).check());
	}
	
}
